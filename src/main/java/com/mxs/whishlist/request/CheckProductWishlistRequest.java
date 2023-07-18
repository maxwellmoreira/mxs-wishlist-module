package com.mxs.whishlist.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CheckProductWishlistRequest(
        @NotBlank(message = "user is a required field")
        String user,
        @NotBlank(message = "product is a required field")
        String product) {
}
