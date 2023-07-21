package com.mxs.whishlist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mxs.whishlist.request.IncludeWishlistRequest;
import com.mxs.whishlist.response.GetWishlistResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class GetWishlistDefinition {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private String user = UUID.randomUUID().toString();
    private String category = "COMPUTERS_ACCESSORIES";
    private String product1 = "9dd9edf8-241f-11ee-be56-0242ac120011";
    private String product2 = "9dd9edf8-241f-11ee-be56-0242ac120012";
    private String product3 = "9dd9edf8-241f-11ee-be56-0242ac120013";
    private int actualStatusCode;
    private GetWishlistResponse actualWishlist;

    @Given(value = "I am logged into my e-commerce account")
    public void i_am_logged_into_my_ecommerce_account() throws Exception {

        List<String> products = List.of(product1, product2, product3);
        List<IncludeWishlistRequest> requests = new ArrayList<>();

        for (String product : products) {
            IncludeWishlistRequest includeWishlistRequest =
                    IncludeWishlistRequest.builder()
                            .user(user)
                            .category(category)
                            .product(product)
                            .link("www.ecommerce.com/sale/category/" + category + "/product/" + product)
                            .build();
            requests.add(includeWishlistRequest);
        }

        for (IncludeWishlistRequest request : requests) {
            mockMvc.perform(post("/api/v1/wishlists")
                    .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));
        }
    }

    @When("I access the wishlist through the service {word}")
    public void i_access_the_wishlist_through_the_service(String uri) throws Exception {
        var mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri, user).contentType(MediaType.APPLICATION_JSON)).andReturn();
        actualStatusCode = mvcResult.getResponse().getStatus();
        var responseBody = mvcResult.getResponse().getContentAsString();
        actualWishlist = objectMapper.readValue(responseBody, GetWishlistResponse.class);
    }

    @Then("I get a return with http status {int} with the wishlist")
    public void i_get_a_return_with_http_status(int expectedStatusCode, List<Map<String, String>> expectedWishlist) {
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedWishlist.size(), actualWishlist.wishlistDtoList().size());
        assertTrue(actualWishlist.wishlistDtoList().stream().anyMatch(
                wishlistDto -> wishlistDto.product().equalsIgnoreCase(product1)));
        assertTrue(actualWishlist.wishlistDtoList().stream().anyMatch(
                wishlistDto -> wishlistDto.product().equalsIgnoreCase(product2)));
        assertTrue(actualWishlist.wishlistDtoList().stream().anyMatch(
                wishlistDto -> wishlistDto.product().equalsIgnoreCase(product3)));
    }
}
