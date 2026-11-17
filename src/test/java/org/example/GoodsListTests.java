package org.example;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.example.RestApiBuilder.GOODS_LIST;
import static org.hamcrest.Matchers.notNullValue;

public class GoodsListTests extends BaseApiTest {

    @Test
    void list_withoutParams_returns200() {
        given()
                .get(GOODS_LIST)
                .then()
                .statusCode(200)
                .body("goods", notNullValue());
    }

    @Test
    void list_withPagination_returns200() {
        given()
                .queryParam("page", 0)
                .queryParam("size", 5)
                .get(GOODS_LIST)
                .then()
                .statusCode(200)
                .body("goods", notNullValue());
    }
}
