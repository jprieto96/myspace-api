package com.example.myspace.e2eTests.steps.client;

import com.example.myspace.client.ClientDto;
import com.example.myspace.e2eTests.CucumberSpringConfiguration;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CucumberContextConfiguration
public class RegisterClientSteps extends CucumberSpringConfiguration {

    private ResponseEntity response;

    private String name;
    private String username;
    private String password;
    private String passwordSalt;
    private String email;

    @Given("Client with name {word}")
    public void client_with_name(String name) {
        this.name = name;
    }
    @Given("Client with username {word}")
    public void client_with_username(String username) {
        this.username = username;
    }
    @Given("Client with password {word}")
    public void client_with_password(String password) {
        this.password = password;
    }
    @Given("Client with password salt {word}")
    public void client_with_password_salt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }
    @Given("Client with email {word}")
    public void client_with_email(String email) {
        this.email = email;
    }
    @When("The client calls \\/register to register itself")
    public void the_client_calls_register_to_register_itself() {
        response = testRestTemplate.postForEntity(
                "/register", new ClientDto(name, username, password, passwordSalt, email), ClientDto.class);
    }
    @Then("The method response with the client that has been created")
    public void the_method_response_with_the_client_that_has_been_created() {
        ClientDto clientDto = (ClientDto) response.getBody();
        Assertions.assertNotNull(clientDto);
        Assertions.assertEquals(name, clientDto.getName());
        Assertions.assertEquals(username, clientDto.getUsername());
        Assertions.assertEquals(email, clientDto.getEmail());
        Assertions.assertEquals(passwordSalt, clientDto.getPasswordSalt());
    }
    @Then("The method response a 201 HTTP Code")
    public void the_method_response_a_http_code() {
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

}
