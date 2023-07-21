# mxs-wishlist-module
Module responsible for maintaining the users' wish list in e-commerce.

# Overview
This project was initially developed in the Java language using the Spring Boot Framework.
The module uses Apache Cassandra NoSQL as a persistence mechanism through Spring Data.

## Services

| Operation | URI |
| ------ | ------ |
| Add a product to a wishlist | [POST] /api/v1/wishlists |
| Get user's wishlist | [GET] /api/v1/wishlists/user/{USER_CODE} |
| Check the existence of a product in the wishlist | [GET] /api/v1/wishlists/user/{USER_CODE}/product/{PRODUCT_CODE}/check |
| Remove product from wishlist | [DELETE] /api/v1/wishlists/user/{USER_CODE}/product/{PRODUCT_CODE} |

## To start the application
Before running the application, start the Apache Cassandra container located in the "docker-compose.yml" file.
It takes approximately 1 minute for the database to start.
Then, run the "init.sh" file located in the project's root directory to create the keyspace and table used in the module.
