package com.mxs.whishlist.service;

import com.mxs.whishlist.converter.WishlistConverter;
import com.mxs.whishlist.exception.NotFoundException;
import com.mxs.whishlist.model.WishlistModel;
import com.mxs.whishlist.repository.WishlistRepository;
import com.mxs.whishlist.request.IncludeWishlistRequest;
import com.mxs.whishlist.response.CheckProductWishlistResponse;
import com.mxs.whishlist.response.GetWishlistResponse;
import com.mxs.whishlist.response.IncludeWishlistResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

import static com.mxs.whishlist.constant.MessageConstant.SEARCH_NOT_FOUND;
import static com.mxs.whishlist.constant.MessageConstant.PRODUCT_NOT_FOUND;
import static com.mxs.whishlist.constant.MessageConstant.MAXIMUM_LIMIT_WISHLIST;

/**
 * Class responsible for representing the wish list service layer.
 */
@Service
public class WishlistService {

    @Autowired
    private WishlistConverter wishlistConverter;
    @Autowired
    private WishlistRepository wishlistRepository;

    /**
     * Method responsible for adding a product to the wishlist.
     *
     * @param includeWishlistRequest input information about the product
     * @return output information about the product
     */
    public IncludeWishlistResponse addToWishlist(IncludeWishlistRequest includeWishlistRequest) {

        var wishList = wishlistRepository.findByUser(UUID.fromString(includeWishlistRequest.user()))
                .stream().filter(WishlistModel::isStatus).toList();

        if (wishList.size() == 20) {
            throw new NotFoundException(MAXIMUM_LIMIT_WISHLIST);
        }

        var wishlistModel = wishlistConverter.convertRequestToModel(includeWishlistRequest);
        var savedWishlist = wishlistRepository.save(wishlistModel);

        return wishlistConverter.convertModelToResponse(savedWishlist);
    }

    /**
     * Method responsible for getting the user's wish list.
     *
     * @param user identification code
     * @return user wishlist
     * @throws NotFoundException search did not return record
     */
    public GetWishlistResponse getWishlistByUser(String user) {

        var wishlistModelList =
                wishlistRepository.findByUser(UUID.fromString(user))
                        .stream().filter(WishlistModel::isStatus).collect(Collectors.toList());

        if (wishlistModelList.isEmpty()) {
            throw new NotFoundException(SEARCH_NOT_FOUND);
        }

        return wishlistConverter.convertModelListToResponse(wishlistModelList, user);
    }

    /**
     * Method responsible for checking the existence of a product in the wishlist.
     *
     * @param user    identification code
     * @param product identification code
     * @return boolean indicating the existence or not of the product in the wish list
     */
    public CheckProductWishlistResponse checkProductInWishlist(String user, String product) {

        var wishlistModelList =
                wishlistRepository.findByUser(UUID.fromString(user));

        var exist = wishlistModelList.stream().anyMatch(
                wishlistModel -> wishlistModel.getProduct().equals(UUID.fromString(product)) && wishlistModel.isStatus());

        return wishlistConverter.convertBooleanToResponse(exist);
    }

    /**
     * Method responsible for removing a product from the wishlist.
     *
     * @param user    identification code
     * @param product identification code
     * @throws NotFoundException the product you were looking for was not found
     */
    public void removeProductFromWishlist(String user, String product) {

        var wishlistModelList = wishlistRepository.findByUser(UUID.fromString(user));

        var wishlistModel = wishlistModelList.stream().filter(
                        wishlist -> wishlist.getProduct().equals(UUID.fromString(product)) && wishlist.isStatus())
                .findFirst().orElseThrow(() -> new NotFoundException(PRODUCT_NOT_FOUND));

        wishlistModel.inactivateRecord();
        wishlistRepository.save(wishlistModel);
    }
}
