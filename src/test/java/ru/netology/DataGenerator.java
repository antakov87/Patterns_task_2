package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;
public class DataGenerator {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    static void sendRequest(RegistrationDto user) {

        given().spec(requestSpec).body(user).when().post("/api/system/users").then().statusCode(200);
    }

    public static String randomLogin() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().username();
    }

    public static String randomPass() {
        Faker faker = new Faker(new Locale("en"));
        return faker.internet().password();
    }

    public static RegistrationDto getNewUser(String status) {
        return new RegistrationDto(randomLogin(), randomPass(), status);
    }

    public static RegistrationDto getRegisteredUser(String status) {
        RegistrationDto registeredUser = getNewUser(status);
        sendRequest(registeredUser);
        return registeredUser;
    }


    @Value
    public static class RegistrationDto {
        String login;
        String password;
        String status;
    }
}
