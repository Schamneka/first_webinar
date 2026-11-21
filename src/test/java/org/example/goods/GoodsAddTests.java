package org.example.goods;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.example.goods.RestApiBuilder.GOODS_ADD;
import static org.example.goods.RestApiBuilder.uniqueName;
import static org.hamcrest.Matchers.notNullValue;

public class GoodsAddTests extends BaseApiTest {

    @Test
    void add_validGood_returns200AndId() {
        given()
                .body(Map.of("name", uniqueName("Ноутбук"), "price", 1500.0))
                .post(GOODS_ADD)
                .then()
                .statusCode(200)
                .body("data", notNullValue())
                .body("message", notNullValue());
    }

    @Test
    void add_missingName_returns400() {
        given()
                .body(Map.of("price", 100.0))
                .post(GOODS_ADD)
                .then()
                .statusCode(400);
    }

    /** price имеет ограничение minimum: 0 — отрицательная цена должна отклоняться. */
    @Test
    void add_negativePrice_returns400() {
        given()
                .body(Map.of("name", uniqueName("Отрицательный"), "price", -5.0))
                .post(GOODS_ADD)
                .then()
                .statusCode(400);
    }
}
