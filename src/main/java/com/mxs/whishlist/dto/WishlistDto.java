package com.mxs.whishlist.dto;

import lombok.Builder;

/**
 * Class responsible for representing the data of a wishlist.
 */
@Builder
public record WishlistDto(
        String category,
        String product,
        String link) {
}
