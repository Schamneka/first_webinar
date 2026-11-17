package org.example;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.example.RestApiBuilder.*;


public class GoodsGetTests extends BaseApiTest {

    @Test
    void get_existingGood_returns200WithSameName() {
        String name = uniqueName("Товар для GET");
        long id = createGood(name, 100.0);

        String body = given()
                .pathParam("id", id)
                .get(GOODS_BY_ID)
                .then()
                .statusCode(200)
                .extract()
                .asString();

        assertThat(body).contains(name);
    }

    @Test
    void get_nonExistentGood_returns404() {
        given()
                .pathParam("id", 999_999_999)
                .get(GOODS_BY_ID)
                .then()
                .statusCode(404);
    }
}
