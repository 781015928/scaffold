package com.crazypug.http.interceptor;


import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

import okhttp3.*;
import okio.*;


public class CurlInterceptor implements Interceptor {

    private static final long DEFAULT_LIMIT = 1024L * 1024L;
    private static final String DEFAULT_DELIMITER = " ";

    protected final HttpLogger logger;
    protected final long limit;
    protected final List<HeaderModifier> headerModifiers = new ArrayList<>();
    protected final Options options;
    protected final String delimiter;

    public CurlInterceptor(Options options) {
        this(HttpLogger.default_logger, options);
    }

    public CurlInterceptor() {
        this(HttpLogger.default_logger);
    }

    /**
     * Interceptor responsible for printing curl logs
     * <p>
     * Logs are pushed to stdout with 1MB limit
     *
     * @param logger output of logging
     */

    public CurlInterceptor(HttpLogger logger) {
        this(logger, DEFAULT_LIMIT, Collections.emptyList(), Options.EMPTY, DEFAULT_DELIMITER);
    }

    /**
     * Interceptor responsible for printing curl logs
     * <p>
     * Logs are pushed to stdout with 1MB limit
     *
     * @param logger  output of logging
     * @param options list of curl options
     */
    public CurlInterceptor(HttpLogger logger, Options options) {
        this(logger, DEFAULT_LIMIT, Collections.emptyList(), options, DEFAULT_DELIMITER);
    }

    /**
     * Interceptor responsible for printing curl logs
     *
     * @param logger          output of logging
     * @param headerModifiers list of header modifiers
     */
    public CurlInterceptor(HttpLogger logger, List<HeaderModifier> headerModifiers) {
        this(logger, DEFAULT_LIMIT, headerModifiers, Options.EMPTY, DEFAULT_DELIMITER);
    }

    /**
     * Interceptor responsible for printing curl logs
     *
     * @param logger output of logging
     * @param limit  limit maximal bytes logged, if negative - non limited
     */
    public CurlInterceptor(HttpLogger logger, long limit) {
        this(logger, limit, Collections.emptyList(), Options.EMPTY, DEFAULT_DELIMITER);
    }

    /**
     * Interceptor responsible for printing curl logs
     *
     * @param logger          output of logging
     * @param limit           limit maximal bytes logged, if negative - non limited
     * @param headerModifiers list of header modifiers
     * @param options         list of curl options
     */
    public CurlInterceptor(HttpLogger logger, long limit, List<HeaderModifier> headerModifiers, Options options) {
        this(logger, limit, headerModifiers, options, DEFAULT_DELIMITER);
    }

    /**
     * Interceptor responsible for printing curl logs
     *
     * @param logger          output of logging
     * @param limit           limit maximal bytes logged, if negative - non limited
     * @param headerModifiers list of header modifiers
     * @param options         list of curl options
     * @param delimiter       string delimiter
     */
    public CurlInterceptor(HttpLogger logger, long limit, List<HeaderModifier> headerModifiers, Options options, String delimiter) {
        this.logger = logger;
        this.limit = limit;
        this.headerModifiers.addAll(headerModifiers);
        this.options = options;
        this.delimiter = delimiter;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request request = chain.request();

        final Request copy = request.newBuilder().build();
        final String curl = getCurlBuilder(copy).build();

        logger.log(curl);

        return chain.proceed(request);
    }

    protected CurlBuilder getCurlBuilder(Request copy) {
        return new CurlBuilder(copy, limit, headerModifiers, options, delimiter);
    }

    static class Options {

        public static final Options EMPTY = new Options(Collections.emptyList());

        private final List<String> options;

        public static Builder builder() {
            return new Builder();
        }

        private Options(Collection<String> options) {
            this.options = new ArrayList<>(options);
        }

        public List<String> list() {
            return options;
        }

        public static class Builder {

            private final Set<String> options = new HashSet<>();

            public Builder insecure() {
                options.add("--insecure");
                return this;
            }

            public Builder maxTime(int seconds) {
                options.add(String.format(Locale.getDefault(), "--max-time %d", seconds));
                return this;
            }

            public Builder connectTimeout(int seconds) {
                options.add(String.format(Locale.getDefault(), "--connect-timeout %d", seconds));
                return this;
            }

            public Builder retry(int num) {
                options.add(String.format(Locale.getDefault(), "--retry %d", num));
                return this;
            }

            public Builder compressed() {
                options.add("--compressed");
                return this;
            }

            public Builder location() {
                options.add("--location");
                return this;
            }

            public Options build() {
                return new Options(options);
            }
        }

    }

    public static class Header {

        private final String name;
        private final String value;

        public Header(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String name() {
            return name;
        }

        public String value() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Header header = (Header) o;

            if (name != null ? !name.equals(header.name) : header.name != null) return false;
            return value != null ? value.equals(header.value) : header.value == null;

        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (value != null ? value.hashCode() : 0);
            return result;
        }
    }


    public static class CurlBuilder {

        protected static final String FORMAT_HEADER = "\n-H \"%1$s:%2$s\" \\";
        protected static final String FORMAT_METHOD = "-X %1$s";
        protected static final String FORMAT_BODY = "\n -d '%1$s' \\";
        protected static final String FORMAT_URL = "\"%1$s\"";
        protected static final String CONTENT_TYPE = "Content-Type";

        protected final String url;
        protected final String method;
        protected final String contentType;
        protected final String body;
        protected final List<String> options;
        protected final List<CurlInterceptor.Header> headers;
        protected final String delimiter;

        public CurlBuilder(Request request) {
            this(request, -1L, Collections.emptyList(), CurlInterceptor.Options.EMPTY);
        }

        public CurlBuilder(Request request, long limit, List<HeaderModifier> headerModifiers, CurlInterceptor.Options options) {
            this(request, limit, headerModifiers, options, " ");
        }

        public CurlBuilder(Request request, long limit, List<HeaderModifier> headerModifiers, CurlInterceptor.Options options, String delimiter) {
            this.url = request.url().toString();
            this.method = request.method();
            this.options = Collections.unmodifiableList(options.list());
            this.delimiter = delimiter;
            final RequestBody body = request.body();
            if (body != null) {
                this.contentType = getContentType(body);
                this.body = getBodyAsString(body, limit);
            } else {
                this.contentType = null;
                this.body = null;
            }

            final Headers headers = request.headers();
            final List<CurlInterceptor.Header> modifiableHeaders = new LinkedList<>();
            for (int i = 0; i < headers.size(); i++) {
                final CurlInterceptor.Header header = new CurlInterceptor.Header(headers.name(i), headers.value(i));
                final CurlInterceptor.Header modifiedHeader = modifyHeader(header, headerModifiers);
                if (modifiedHeader != null) {
                    modifiableHeaders.add(modifiedHeader);
                }
            }
            this.headers = Collections.unmodifiableList(modifiableHeaders);
        }

        private CurlInterceptor.Header modifyHeader(CurlInterceptor.Header header, List<HeaderModifier> headerModifiers) {
            for (HeaderModifier modifier : headerModifiers) {
                if (modifier.matches(header)) {
                    return modifier.modify(header);
                }
            }

            return header;
        }

        private String getContentType(RequestBody body) {
            final MediaType mediaType = body.contentType();
            if (mediaType != null) {
                return mediaType.toString();
            }

            return null;
        }

        private String getBodyAsString(RequestBody body, long limit) {
            try {
                final Buffer sink = new Buffer();

                final MediaType mediaType = body.contentType();
                final Charset charset = getCharset(mediaType);

                if (limit > 0) {
                    final BufferedSink buffer = Okio.buffer(new LimitedSink(sink, limit));
                    body.writeTo(buffer);
                    buffer.flush();
                } else {
                    body.writeTo(sink);
                }

                return sink.readString(charset);
            } catch (IOException e) {
                return "Error while reading body: " + e.toString();
            }
        }

        private Charset getCharset(MediaType mediaType) {
            if (mediaType != null) {
                return mediaType.charset(Charset.defaultCharset());
            }

            return Charset.defaultCharset();
        }

        public String build() {
            List<String> parts = new ArrayList<>();
            parts.add("curl");


            // parts.addAll(options);


            // parts.add(String.format(FORMAT_METHOD, method.toUpperCase()));

            parts.add(String.format("'%s' \\", url));

            for (CurlInterceptor.Header header : headers) {
                final String headerPart = String.format(FORMAT_HEADER, header.name(), header.value());
                parts.add(headerPart);
            }

            if (contentType != null && !containsName(CONTENT_TYPE, headers)) {
                parts.add(String.format(FORMAT_HEADER, CONTENT_TYPE, contentType));
            }

            if (body != null) {
                parts.add(String.format(FORMAT_BODY, body));
            }

            parts.add("\n --compressed \\");
            parts.add("\n --insecure");


            return join(delimiter, parts);
        }

        protected boolean containsName(String name, List<CurlInterceptor.Header> headers) {
            for (CurlInterceptor.Header header : headers) {
                if (header.name().equals(name)) {
                    return true;
                }
            }

            return false;
        }

        static class LimitedSink implements Sink {

            private final Buffer limited;
            private long total;

            public LimitedSink(Buffer limited, long limit) {
                if (limited == null) throw new NullPointerException("limited can not be null");
                if (limit <= 0) throw new IllegalArgumentException("limit has to be grater than 0");
                this.limited = limited;
                total = limit;
            }

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                if (total > 0) {
                    long toWrite = Math.min(total, byteCount);
                    limited.write(source, toWrite);
                    total -= toWrite;
                }
            }

            @Override
            public void flush() throws IOException {
                limited.flush();
            }

            @Override
            public Timeout timeout() {
                return Timeout.NONE;
            }

            @Override
            public void close() throws IOException {
                limited.close();
            }
        }
    }

    public static String join(CharSequence delimiter, Iterable tokens) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (Object token : tokens) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(token);
        }
        return sb.toString();
    }

    public interface HeaderModifier {

        /**
         * @param header the header to check
         * @return true if header should be modified and false otherwise.
         */
        boolean matches(CurlInterceptor.Header header);

        /**
         * @param header the header to modify
         * @return modified header or null to omit header in curl log
         */
        CurlInterceptor.Header modify(CurlInterceptor.Header header);
    }

}
