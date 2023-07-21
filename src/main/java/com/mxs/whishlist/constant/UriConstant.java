package com.mxs.whishlist.constant;

/**
 * Class responsible for the module's URI definitions.
 */
public class UriConstant {
    public static final String URI_BASE_V1 = "api/v1/";
    public static final String WISHLISTS = "wishlists";
    public static final String GET_WISHLIST_BY_USER = "/user/{user}";
    public static final String CHECK_PRODUCT_IN_WISHLIST = "/user/{user}/product/{product}/check";
    public static final String REMOVE_PRODUCT_FROM_WISHLIST = "/user/{user}/product/{product}";
}
