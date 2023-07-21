Feature: Add a product to a wishlist
  Scenario: Add a product to wishlist successfully
    Given a product that I am interested in buying on e-commerce through the url www.ecommerce.com/sale/category/computers_accessories/product/9dd9edf8-241f-11ee-be56-0242ac120019
    When I access the service /api/v1/wishlists informing the product
    Then I get a return with http status 201 indicating that the product was successfully added to the wishlist
