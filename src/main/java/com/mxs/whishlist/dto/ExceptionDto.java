package com.mxs.whishlist.dto;

import com.mxs.whishlist.type.ExceptionType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ExceptionDto(
        String code,
        String message,
        ExceptionType exceptionType,
        LocalDateTime timestamp) {
}
