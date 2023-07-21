package com.mxs.whishlist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mxs.whishlist.request.IncludeWishlistRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@CucumberContextConfiguration
public class IncludeWishlistDefinition {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private String user;
    private String category;
    private String product;
    private String link;
    private int actualStatusCode;

    @Given(value = "a product that I am interested in buying on e-commerce through the url {word}")
    public void a_product_that_i_am_interested(String link) {
        String[] parts = link.split("/");
        this.user = UUID.randomUUID().toString();
        this.category = parts[3].toUpperCase();
        this.product = parts[5];
        this.link = link;
    }

    @When("I access the service {word} informing the product")
    public void i_access_the_service_informing_the_product(String uri) throws Exception {

        IncludeWishlistRequest includeWishlistRequest =
                IncludeWishlistRequest
                        .builder()
                        .user(user)
                        .category(category)
                        .product(product)
                        .link(link)
                        .build();

        this.actualStatusCode = mockMvc.perform(post("/api/v1/wishlists")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(includeWishlistRequest)))
                .andReturn().getResponse().getStatus();
    }

    @Then("I get a return with http status {int} indicating that the product was successfully added to the wishlist")
    public void i_get_a_return_with_http_status(int expectedStatusCode) {
        assertEquals(expectedStatusCode, this.actualStatusCode);
    }
}
