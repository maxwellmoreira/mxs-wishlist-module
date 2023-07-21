# mxs-wishlist-module
Module responsible for maintaining the users' wish list in e-commerce.

## Services

| Operation | URI |
| ------ | ------ |
| Add a product to a wishlist | [POST] /api/v1/wishlists |
| Get user's wishlist | [GET] /api/v1/wishlists/user/{USER_CODE} |
| Check the existence of a product in the wishlist | [GET] /api/v1/wishlists/user/{USER_CODE}/product/{PRODUCT_CODE}/check |
| Remove product from wishlist | [DELETE] /api/v1/wishlists/user/{USER_CODE}/product/{PRODUCT_CODE} |
