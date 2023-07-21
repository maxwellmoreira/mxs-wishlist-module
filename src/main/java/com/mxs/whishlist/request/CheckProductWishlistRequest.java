package com.mxs.whishlist.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * Class responsible for representing a request to check the existence of a product in the wishlist.
 */
@Builder
public record CheckProductWishlistRequest(
        @NotBlank(message = "user is a required field")
        String user,
        @NotBlank(message = "product is a required field")
        String product) {
}
