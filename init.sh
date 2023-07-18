#!/bin/bash

docker exec -it cassandra cqlsh -e "CREATE KEYSPACE ecommerce WITH REPLICATION = {'class':'SimpleStrategy', 'replication_factor':1};"

docker exec -it cassandra cqlsh -e "USE ecommerce; CREATE TABLE wishlist (user UUID, category VARCHAR, creation TIMESTAMP, removal TIMESTAMP, product UUID, link TEXT, status BOOLEAN, PRIMARY KEY ((user), category, creation, product));"
