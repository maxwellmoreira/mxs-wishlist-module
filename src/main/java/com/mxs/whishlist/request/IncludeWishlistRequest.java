package com.mxs.whishlist.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * Class responsible for requesting the inclusion of a product in a wishlist.
 */
@Builder
public record IncludeWishlistRequest(
        @NotBlank(message = "user is a required field")
        String user,
        @NotBlank(message = "category is a required field")
        String category,
        @NotBlank(message = "product is a required field")
        String product,
        @NotBlank(message = "link is a required field")
        String link) {
}