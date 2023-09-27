package com.crazypug.web.aop;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogIgnore {


    IgnoreLevel ignorelevel() default  IgnoreLevel.ALL;



    public static enum IgnoreLevel {

        NONE(false,false,false),

        ONLY_IN(true,false,false),

        ONLY_OUT(false,true,false),

        ALL(true,true,true),

        ;

        public boolean ignoreInArgs;

        public boolean ignoreOutArgs;

        public boolean ignoreRequest;

        IgnoreLevel(boolean ignoreInArgs,
                    boolean ignoreOutArgs,
                    boolean ignoreRequest) {
            this.ignoreInArgs=ignoreInArgs;
            this.ignoreOutArgs=ignoreOutArgs;
            this.ignoreRequest=ignoreRequest;

        }


        }
}
