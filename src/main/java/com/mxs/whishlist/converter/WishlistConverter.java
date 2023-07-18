package com.mxs.whishlist.converter;

import com.mxs.whishlist.dto.WishlistDto;
import com.mxs.whishlist.model.WishlistModel;
import com.mxs.whishlist.request.IncludeWishlistRequest;
import com.mxs.whishlist.response.CheckProductWishlistResponse;
import com.mxs.whishlist.response.GetWishlistResponse;
import com.mxs.whishlist.response.IncludeWishlistResponse;
import com.mxs.whishlist.type.CategoryType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Class responsible for converting objects that are related to the wish list domain.
 */
@Component
public class WishlistConverter {

    public WishlistModel convertRequestToModel(IncludeWishlistRequest includeWishlistRequest) {
        WishlistModel wishlistModel = new WishlistModel();
        wishlistModel.setUser(UUID.fromString(includeWishlistRequest.user()));
        wishlistModel.setCategory(CategoryType.valueOf(includeWishlistRequest.category()));
        wishlistModel.setProduct(UUID.fromString(includeWishlistRequest.product()));
        wishlistModel.setLink(includeWishlistRequest.link());
        return wishlistModel;
    }

    public IncludeWishlistResponse convertModelToResponse(WishlistModel wishlistModel) {
        return IncludeWishlistResponse.builder()
                .user(wishlistModel.getUser().toString())
                .category(wishlistModel.getCategory().name())
                .product(wishlistModel.getProduct().toString())
                .link(wishlistModel.getLink())
                .build();
    }

    public GetWishlistResponse convertModelListToResponse(List<WishlistModel> wishlistModelList, String user) {
        List<WishlistDto> wishlistDtoList = wishlistModelList.stream().map(this::convertModelToDto).toList();
        return GetWishlistResponse
                .builder()
                .user(user)
                .wishlistDtoList(wishlistDtoList)
                .build();
    }

    private WishlistDto convertModelToDto(WishlistModel wishlistModel) {
        return WishlistDto
                .builder()
                .category(wishlistModel.getCategory().name())
                .product(wishlistModel.getProduct().toString())
                .link(wishlistModel.getLink())
                .build();
    }

    public CheckProductWishlistResponse convertBooleanToResponse(boolean exist) {
        return CheckProductWishlistResponse
                .builder()
                .exist(exist)
                .build();
    }
}
