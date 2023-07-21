package com.mxs.whishlist.dto;

import com.mxs.whishlist.type.ExceptionType;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Class responsible for representing the structure of an exception.
 */
@Builder
public record ExceptionDto(
        String code,
        String message,
        ExceptionType exceptionType,
        LocalDateTime timestamp) {
}
