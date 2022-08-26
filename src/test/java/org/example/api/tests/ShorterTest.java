package org.example.api.tests;

import io.qameta.allure.Description;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.api.help.Specifications;
import org.example.api.help.Url;
import org.junit.jupiter.api.Assertions;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static io.restassured.RestAssured.given;

public class ShorterTest {
    private final static String URL = "https://cleanuri.com/";

    @Test
    @Description("Positive test - getting shortened links from testLinks.txt")
    public void checkShortenedLinkPositive() throws IOException {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecification200());
        List<String> fullLinks = FileUtils.readLines(new File("src/test/resources/testLinks.txt"));
        ArrayList<Url> fullUrls = new ArrayList<>();
        for (String fullLink:
             fullLinks) {
            fullUrls.add(new Url(fullLink));
        }
        ArrayList<String> shortLinks = new ArrayList<>();
        for (Url fullUrl:
             fullUrls) {
            Response response = given()
                    .body(fullUrl)
                    .when()
                    .post("api/v1/shorten")
                    .then().log().all()
                    .extract().response();
            JsonPath jsonPath = response.jsonPath();
            String result_url = jsonPath.get("result_url");
            Assertions.assertNotNull(result_url);
            shortLinks.add(result_url);
        }
    }

    @Test
    @Description("Negative test - checking errors when trying to use incorrect links from wrongLinks.txt")
    public void checkShortenedLinkWrongLink() throws IOException {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecError400());
        List<String> fullLinks = FileUtils.readLines(new File("src/test/resources/wrongLinks.txt"));
        ArrayList<Url> fullUrls = new ArrayList<>();
        for (String fullLink:
                fullLinks) {
            fullUrls.add(new Url(fullLink));
        }
        ArrayList<String> shortLinks = new ArrayList<>();
        for (Url fullUrl:
                fullUrls) {
            Response response = given()
                    .body(fullUrl)
                    .when()
                    .post("api/v1/shorten")
                    .then().log().all()
                    .extract().response();
            JsonPath jsonPath = response.jsonPath();
            String error = jsonPath.get("error");
            Assertions.assertNotNull(error);
            shortLinks.add(error);
        }

    }

}
