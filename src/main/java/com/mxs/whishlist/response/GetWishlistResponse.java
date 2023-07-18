package com.mxs.whishlist.response;

import com.mxs.whishlist.dto.WishlistDto;
import lombok.Builder;

import java.util.List;

/**
 * Class responsible for responding to the user's wish list.
 */
@Builder
public record GetWishlistResponse(
        String user,
        List<WishlistDto> wishlistDtoList) {
}
