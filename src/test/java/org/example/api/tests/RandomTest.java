package org.example.api.tests;

import io.qameta.allure.Description;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.api.help.Specifications;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RandomTest {
    private final static String URL = "https://randomuser.me/";

    @Test //Использовать Pojo-классы и распарсить полную структуру, если останется время
    @Description("Positive test - getting many users")
    //Here is the bug that some passwords doesn't fit the conditions
    public void checkManyFemaleUsersFromIslandsWithGoodPassPositive() throws IOException {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecification200());
        Response response = given()
                .when()
                .get("api/?results=5000&gender=female&nat=nz,au&password=upper,lower,number,15-30")
                .then().log().all()
                .body("info.results", equalTo(5000))
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        List<String> emails = jsonPath.getList("results.email");
        List<String> genders = jsonPath.getList("results.gender");
        List<String> nationalities = jsonPath.getList("results.nat");
        List<String> passes = jsonPath.getList("results.login.password");

        Assertions.assertTrue(emails.stream().count() == 5000);
        Assertions.assertTrue(genders.stream().allMatch(Predicate.isEqual("female")));
        Assertions.assertTrue(nationalities.stream().filter(Predicate.isEqual("AU")).count()
        + nationalities.stream().filter(Predicate.isEqual("NZ")).count() == 5000);
        Assertions.assertEquals(5000, passes.stream().filter(x -> x.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{15,30}")).count());

    }


    @Test
    @Description("Positive test - any-value in gender")
    public void checkAnyValueGenderPositive() throws IOException {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecification200());
        Response response = given()
                .when()
                .get("api/?gender=shark&results=300")
                .then()
                .body("info.results", equalTo(300))
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        List<String> genders = jsonPath.getList("results.gender");
        Assertions.assertEquals(300, genders.stream().filter(Predicate.isEqual("male")).count()
                + genders.stream().filter(Predicate.isEqual("female")).count());
    }

    @Test
    @Description("Positive test - password without special and number symbols")
    public void checkPassWithoutSpecialAndNumbersPositive() throws IOException {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecification200());
        Response response = given()
                .when()
                .get("api/?password=upper,lower,15-30&results=300")
                .then().log().all()
                .body("info.results", equalTo(300))
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        List<String> passes = jsonPath.getList("results.login.password");
        Assertions.assertEquals(300, passes.stream().filter(x -> x.matches("(?=.*[a-z])(?=.*[A-Z])[a-zA-Z]{15,30}")).count());
    }

    //Баг, что где-то в паролях отсутствуют числа, если их указать.
    @Test
    @Description("Positive test - password without special symbols")
    public void checkPassWithoutSpecialPositive() throws IOException {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecification200());
        Response response = given()
                .when()
                .get("api/?password=upper,lower,number,15-30&results=300")
                .then().log().all()
                .body("info.results", equalTo(300))
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        List<String> passes = jsonPath.getList("results.login.password");
        Assertions.assertEquals(300, passes.stream().filter(x -> x.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{15,30}")).count());
    }

    @Test
    @Description("Positive test - 2 same seeds gives the same result")
    public void checkTwoSeedsPositive() throws IOException {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecification200());
        Response response1 = given()
                .when()
                .get("api/?seed=foobar")
                .then().log().all()
                .body("info.results", equalTo(1))
                .extract().response();
        JsonPath jsonPath1 = response1.jsonPath();
        String id1 = jsonPath1.getString("results.login.uuid");
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecification200());
        Response response2 = given()
                .when()
                .get("api/?seed=foobar")
                .then().log().all()
                .body("info.results", equalTo(1))
                .extract().response();
        JsonPath jsonPath2 = response2.jsonPath();
        String id2 = jsonPath2.getString("results.login.uuid");
        Assertions.assertEquals(id1, id2);
    }

    @Test
    @Description("Positive test - 2 paginations without email but only with login structure gives the same result")
    public void checkPaginationAndExcludingEmailIncludingLoginPositive() throws IOException {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecification200());
        Response response1 = given()
                .when()
                .get("api/?page=3&results=10&seed=abc&inc=login&exc=email")
                .then().log().all()
                .body("info.results", equalTo(10))
                .extract().response();
        JsonPath jsonPath1 = response1.jsonPath();
        List<String> passes1 = jsonPath1.getList("results.login.password");
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecification200());
        Response response2 = given()
                .when()
                .get("api/?page=3&results=10&seed=abc&inc=login&exc=email")
                .then().log().all()
                .body("info.results", equalTo(10))
                .extract().response();
        JsonPath jsonPath2 = response2.jsonPath();
        List<String> passes2 = jsonPath2.getList("results.login.password");
        Assertions.assertEquals(passes1, passes2);
        Assertions.assertTrue(jsonPath2.getList("results.email").stream().allMatch(Objects::isNull));
        Assertions.assertTrue(jsonPath2.getList("results.name").stream().allMatch(Objects::isNull));
    }

    @Test
    @Description("Negative test - getting to much users")
    public void checkManyUsersNegative() throws IOException {
        Specifications.installSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecification200());
        Response response = given()
                .when()
                .get("api/?results=5001")
                .then().log().all()
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        List<String> emails = jsonPath.getList("results.email");
        Assertions.assertEquals(1, emails.stream().count());
    }
}
