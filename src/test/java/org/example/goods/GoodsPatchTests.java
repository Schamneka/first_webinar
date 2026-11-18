package org.example.goods;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.example.goods.RestApiBuilder.GOODS_BY_ID;
import static org.example.goods.RestApiBuilder.createGood;
import static org.example.goods.RestApiBuilder.uniqueName;

public class GoodsPatchTests extends BaseApiTest {

    @Test
    void patch_existingGood_returns200() {
        long id = createGood(uniqueName("До патча"), 100.0);

        given()
                .pathParam("id", id)
                .body(Map.of("name", uniqueName("После патча"), "price", 200.0))
                .patch(GOODS_BY_ID)
                .then()
                .statusCode(200);
    }

    /** PATCH проверяет уникальность имени — дубль должен давать 400. */
    @Test
    void patch_duplicateName_returns400() {
        String takenName = uniqueName("Занятое имя");
        createGood(takenName, 50.0);
        long otherId = createGood(uniqueName("Второй товар"), 60.0);

        given()
                .pathParam("id", otherId)
                .body(Map.of("name", takenName, "price", 60.0))
                .patch(GOODS_BY_ID)
                .then()
                .statusCode(400);
    }

    @Test
    void patch_nonExistentGood_returns404() {
        given()
                .pathParam("id", 999_999_999)
                .body(Map.of("name", uniqueName("Никто не найдёт"), "price", 10.0))
                .patch(GOODS_BY_ID)
                .then()
                .statusCode(404);
    }
}
