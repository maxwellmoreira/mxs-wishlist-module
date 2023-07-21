Feature: Remove product from wishlist
  Scenario: Remove product from wishlist successfully
    Given that I am no longer interested in an e-commerce product
    When I access the /api/v1/wishlists/user/{user}/product/{product} service informing the product I want to remove from the wishlist
    Then I get a return with http status 204 indicating that the product has been removed