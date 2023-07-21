package com.mxs.whishlist.controller;

import com.mxs.whishlist.request.IncludeWishlistRequest;
import com.mxs.whishlist.response.CheckProductWishlistResponse;
import com.mxs.whishlist.response.GetWishlistResponse;
import com.mxs.whishlist.response.IncludeWishlistResponse;
import com.mxs.whishlist.service.WishlistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.mxs.whishlist.constant.UriConstant.*;

/**
 * Class responsible for representing the controlling layer of the wish list.
 */
@Controller
@RequestMapping(value = URI_BASE_V1 + WISHLISTS)
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    /**
     * Method responsible for adding a product to the wishlist.
     *
     * @param includeWishlistRequest input information about the product
     * @return output information about the product
     */
    @PostMapping
    public ResponseEntity<IncludeWishlistResponse> addToWishlist(
            @Valid @RequestBody IncludeWishlistRequest includeWishlistRequest) {
        var includeWishlistResponse = wishlistService.addToWishlist(includeWishlistRequest);
        return new ResponseEntity<>(includeWishlistResponse, HttpStatus.CREATED);
    }

    /**
     * Method responsible for getting the user's wish list.
     *
     * @param user identification code
     * @return user wishlist
     */
    @GetMapping(value = GET_WISHLIST_BY_USER)
    public ResponseEntity<GetWishlistResponse> getWishlistByUser(@PathVariable("user") String user) {
        var getWishlistResponse = wishlistService.getWishlistByUser(user);
        return new ResponseEntity<>(getWishlistResponse, HttpStatus.OK);
    }

    /**
     * Method responsible for checking the existence of a product in the wishlist.
     *
     * @param user identification code
     * @param product identification code
     * @return boolean indicating the existence or not of the product in the wish list
     */
    @GetMapping(value = CHECK_PRODUCT_IN_WISHLIST)
    public ResponseEntity<CheckProductWishlistResponse> checkProductInWishlist(
            @PathVariable("user") String user, @PathVariable("product") String product) {
        var checkProductWishlistResponse = wishlistService.checkProductInWishlist(user, product);
        return new ResponseEntity<>(checkProductWishlistResponse, HttpStatus.OK);
    }

    /**
     * Method responsible for removing a product from the wishlist.
     *
     * @param user identification code
     * @param product identification code
     * @return http status of wishlist product removal process
     */
    @DeleteMapping(value = REMOVE_PRODUCT_FROM_WISHLIST)
    public ResponseEntity<HttpStatus> removeProductFromWishlist(
            @PathVariable("user") String user, @PathVariable("product") String product) {
        wishlistService.removeProductFromWishlist(user, product);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
