package github.com.tombessa.salesportfolio.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BusinessException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(BusinessException.class);
    private String message;

    public BusinessException(String message) {
        super(message);
        this. message = message;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        logger.error(cause.getMessage());
    }
}
