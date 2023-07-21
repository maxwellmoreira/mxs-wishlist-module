package com.mxs.whishlist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mxs.whishlist.dto.ExceptionDto;
import com.mxs.whishlist.request.IncludeWishlistRequest;
import com.mxs.whishlist.type.CategoryType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Random;
import java.util.UUID;

import static com.mxs.whishlist.constant.MessageConstant.PRODUCT_ALREADY_EXISTS_IN_WISHLIST;
import static com.mxs.whishlist.type.ExceptionType.BUSINESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class WishlistControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Add a product to wishlist successfully")
    void add_a_product_to_wishlist_successfully() throws Exception {

        var randomUser = UUID.randomUUID().toString();
        var randomProduct = UUID.randomUUID().toString();

        CategoryType[] categoryTypes = CategoryType.values();
        int randomIndex = new Random().nextInt(categoryTypes.length);
        CategoryType randomCategory = categoryTypes[randomIndex];

        var randomLink = "http://www.ecommerce.com/sale/category/"
                .concat(randomCategory.name().toUpperCase())
                .concat("/product/").concat(randomProduct);

        IncludeWishlistRequest includeWishlistRequest =
                IncludeWishlistRequest
                        .builder()
                        .user(randomUser)
                        .category(randomCategory.name().toUpperCase())
                        .product(randomProduct)
                        .link(randomLink)
                        .build();

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/wishlists")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(includeWishlistRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.user").value(randomUser))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value(randomCategory.name().toUpperCase()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.product").value(randomProduct))
                .andExpect(MockMvcResultMatchers.jsonPath("$.link").value(randomLink));
    }

    @Test
    @DisplayName("The product already exists in the wishlist")
    void the_product_already_exists_in_the_wishlist() throws Exception {

        var randomUser = UUID.randomUUID().toString();
        var randomProduct = UUID.randomUUID().toString();

        CategoryType[] categoryTypes = CategoryType.values();
        int randomIndex = new Random().nextInt(categoryTypes.length);
        CategoryType randomCategory = categoryTypes[randomIndex];

        var randomLink = "http://www.ecommerce.com/sale/category/"
                .concat(randomCategory.name().toUpperCase())
                .concat("/product/").concat(randomProduct);

        IncludeWishlistRequest includeWishlistRequest =
                IncludeWishlistRequest
                        .builder()
                        .user(randomUser)
                        .category(randomCategory.name().toUpperCase())
                        .product(randomProduct)
                        .link(randomLink)
                        .build();

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/wishlists")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(includeWishlistRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.user").value(randomUser))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value(randomCategory.name().toUpperCase()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.product").value(randomProduct))
                .andExpect(MockMvcResultMatchers.jsonPath("$.link").value(randomLink));

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/wishlists")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(includeWishlistRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(PRODUCT_ALREADY_EXISTS_IN_WISHLIST))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exceptionType").value(BUSINESS.name()));
    }

    @Test
    @DisplayName("Reached the limit of products in the wishlist")
    void reached_the_limit_of_products_in_the_wishlist() throws Exception {

        var randomUser = UUID.randomUUID().toString();

        CategoryType[] categoryTypes = CategoryType.values();
        int randomIndex = new Random().nextInt(categoryTypes.length);
        CategoryType randomCategory = categoryTypes[randomIndex];

        int i = 0;

        while (i <= 21) {

            var randomProduct = UUID.randomUUID().toString();
            var randomLink = "http://www.ecommerce.com/sale/category/"
                    .concat(randomCategory.name().toUpperCase())
                    .concat("/product/").concat(randomProduct);

            IncludeWishlistRequest includeWishlistRequest =
                    IncludeWishlistRequest
                            .builder()
                            .user(randomUser)
                            .category(randomCategory.name().toUpperCase())
                            .product(randomProduct)
                            .link(randomLink)
                            .build();

            var result = mockMvc.perform(
                    MockMvcRequestBuilders
                            .post("/api/v1/wishlists")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(includeWishlistRequest))).andReturn();

            if (i == 21) {
                var responseBody = result.getResponse().getContentAsString();
                var exceptionDto = objectMapper.readValue(responseBody, ExceptionDto.class);
                assertEquals("BUSINESS", exceptionDto.exceptionType().name());
                assertEquals("The wishlist has reached the maximum limit of 20 items", exceptionDto.message());
            }
            i++;
        }
    }
}
