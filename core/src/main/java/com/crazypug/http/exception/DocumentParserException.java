package com.crazypug.http.exception;

import org.jsoup.nodes.Document;

public class DocumentParserException extends RuntimeException {
    Document document;


    public DocumentParserException(String message, Throwable cause, Document document) {
        super(message, cause);
        this.document = document;
    }


    @Override
    public String getMessage() {
        return super.getMessage() + "\n" + document.outerHtml();
    }
}
