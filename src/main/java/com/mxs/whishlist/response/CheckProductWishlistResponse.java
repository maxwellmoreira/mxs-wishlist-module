package com.mxs.whishlist.response;

import lombok.Builder;

@Builder
public record CheckProductWishlistResponse(boolean exist) {
}
