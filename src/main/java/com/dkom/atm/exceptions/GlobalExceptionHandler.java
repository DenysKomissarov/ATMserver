package com.dkom.atm.exceptions;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.NotAcceptableStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Authentication not valid")
    @ExceptionHandler(ServiceException.class)
    public void handleUnauthorized(){
        logger.info("Authentication not valid");
    }

    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Not acceptable")
    @ExceptionHandler(NotAcceptableStatusException.class)
    public void handleNotAcceptable(){
        logger.info("Not acceptable");
    }
}
