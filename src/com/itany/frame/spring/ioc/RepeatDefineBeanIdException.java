package com.itany.frame.spring.ioc;

public class RepeatDefineBeanIdException extends RuntimeException {
	
	private static final long serialVersionUID = -8966656966824906173L;

    public RepeatDefineBeanIdException() {
        super();
    }

    public RepeatDefineBeanIdException(String message) {
        super(message);
    }

  
    public RepeatDefineBeanIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepeatDefineBeanIdException(Throwable cause) {
        super(cause);
    }

    protected RepeatDefineBeanIdException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
