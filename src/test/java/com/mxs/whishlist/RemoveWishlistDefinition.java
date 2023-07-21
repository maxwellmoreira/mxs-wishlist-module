package com.mxs.whishlist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mxs.whishlist.request.IncludeWishlistRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class RemoveWishlistDefinition {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private String user = UUID.randomUUID().toString();
    private String category = "COMPUTERS_ACCESSORIES";
    private String product = UUID.randomUUID().toString();
    private int actualStatusCode;

    @Given(value = "that I am no longer interested in an e-commerce product")
    public void i_am_no_longer_interested() throws Exception {

        IncludeWishlistRequest includeWishlistRequest =
                IncludeWishlistRequest.builder()
                        .user(user)
                        .category(category)
                        .product(product)
                        .link("www.ecommerce.com/sale/category/" + category + "/product/" + product)
                        .build();

        mockMvc.perform(post("/api/v1/wishlists")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(includeWishlistRequest)));

        mockMvc.perform(get("/api/v1/wishlists/user/" + user + "/product/" + product + "/check")
                .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.exist").value("true"));
    }

    @When(value = "I access the {word} service informing the product I want to remove from the wishlist")
    public void i_access_the_service_informing_the_product_i_want_to_remove(String uri) throws Exception {
        actualStatusCode = mockMvc.perform(MockMvcRequestBuilders.delete(uri, user, product)
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getStatus();
    }

    @Then(value = "I get a return with http status {int} indicating that the product has been removed")
    public void i_get_a_return_with_http_status(int expectedStatusCode) throws Exception {

        assertEquals(expectedStatusCode, actualStatusCode);

        mockMvc.perform(get("/api/v1/wishlists/user/" + user + "/product/" + product + "/check")
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.exist").value("false"));
    }
}
