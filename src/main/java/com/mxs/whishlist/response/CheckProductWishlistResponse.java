package com.mxs.whishlist.response;

import lombok.Builder;

/**
 * Class responsible for representing the response of a check on the existence of a product in the wish list.
 */
@Builder
public record CheckProductWishlistResponse(boolean exist) {
}
