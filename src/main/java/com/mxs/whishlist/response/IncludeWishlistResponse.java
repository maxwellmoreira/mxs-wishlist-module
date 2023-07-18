package com.mxs.whishlist.response;

import lombok.Builder;

/**
 * Class responsible for responding a product insertion to a wish list.
 */
@Builder
public record IncludeWishlistResponse(
        String user,
        String category,
        String product,
        String link) {
}
