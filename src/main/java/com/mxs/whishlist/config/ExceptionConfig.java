package com.mxs.whishlist.config;

import com.mxs.whishlist.dto.ExceptionDto;
import com.mxs.whishlist.exception.NotFoundException;
import com.mxs.whishlist.type.ExceptionType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Class responsible for configuring the exceptions that will be thrown in the module.
 */
@ControllerAdvice
public class ExceptionConfig {

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionDto> throwBusinessException(NotFoundException notFoundException, WebRequest webRequest) {

        ExceptionDto exceptionDto =
                ExceptionDto
                        .builder()
                        .code(UUID.randomUUID().toString())
                        .message(notFoundException.getMessage())
                        .exceptionType(ExceptionType.BUSINESS)
                        .timestamp(LocalDateTime.now())
                        .build();

        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }
}
