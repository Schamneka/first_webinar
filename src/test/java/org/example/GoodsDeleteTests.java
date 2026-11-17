package org.example;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.example.RestApiBuilder.*;


public class GoodsDeleteTests extends BaseApiTest {

    @Test
    void delete_existingGood_returns200AndReallyDeletes() {
        long id = createGood(uniqueName("На удаление"), 30.0);

        given()
                .pathParam("id", id)
                .delete(GOODS_BY_ID)
                .then()
                .statusCode(200);

        // контроль: после удаления товар недоступен
        given()
                .pathParam("id", id)
                .get(GOODS_BY_ID)
                .then()
                .statusCode(404);
    }

    @Test
    void delete_nonExistentGood_returns404() {
        given()
                .pathParam("id", 999_999_999)
                .delete(GOODS_BY_ID)
                .then()
                .statusCode(404);
    }
}
