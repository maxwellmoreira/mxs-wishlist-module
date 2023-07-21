Feature: Get user's wishlist
  Scenario: Get user's wishlist successfully
    Given I am logged into my e-commerce account
    When I access the wishlist through the service /api/v1/wishlists/user/{user}
    Then I get a return with http status 200 with the wishlist
      | category | product | link |
      | computers_accessories | 9dd9edf8-241f-11ee-be56-0242ac120011 | www.ecommerce.com/sale/category/computers_accessories/product/9dd9edf8-241f-11ee-be56-0242ac120011 |
      | computers_accessories | 9dd9edf8-241f-11ee-be56-0242ac120012 | www.ecommerce.com/sale/category/computers_accessories/product/9dd9edf8-241f-11ee-be56-0242ac120012 |
      | computers_accessories | 9dd9edf8-241f-11ee-be56-0242ac120013 | www.ecommerce.com/sale/category/computers_accessories/product/9dd9edf8-241f-11ee-be56-0242ac120013 |