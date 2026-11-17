package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public final class RestApiBuilder {

    public static final String BASE_URL = "http://localhost:8080";
    public static final String GOODS_LIST = "/goods/list";
    public static final String GOODS_ADD = "/goods/add";
    public static final String GOODS_BY_ID = "/goods/{id}";


    private static final String USER = "admin";
    private static final String PASSWORD = "secret123";

    private RestApiBuilder() {
    }

    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setAuth(RestAssured.preemptive().basic(USER, PASSWORD))
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    /** Создаёт товар и возвращает его id из ответа сервера. */
    public static long createGood(String name, double price) {
        Map<String, Object> data = given()
                .body(Map.of("name", name, "price", price))
                .post(GOODS_ADD)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getMap("data");

        return ((Number) data.values().iterator().next()).longValue();
    }

    public static void deleteGood(long id) {
        given()
                .pathParam("id", id)
                .delete(GOODS_BY_ID)
                .then()
                .statusCode(200);
    }

    /** Уникальное имя товара, чтобы тесты не мешали друг другу. */
    public static String uniqueName(String prefix) {
        return prefix + " " + System.nanoTime();
    }
}

