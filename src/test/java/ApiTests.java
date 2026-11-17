import io.restassured.RestAssured;
import org.example.RestApiBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.withArgs;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;


@Tag("autotests")
public class ApiTests {

    @BeforeAll
    static void setup() {
        RestAssured.requestSpecification = RestApiBuilder.requestSpecification();
    }

    /** Хелпер: создаёт товар через POST /goods/add и проверяет код ответа. */
    private static void addGood(String name, double price) {
        given()
                .body(Map.of("name", name, "price", price))
                .post(RestApiBuilder.GOODS_ADD)
                .then()
                .statusCode(200);
    }

    /** 1. given/when/then: код ответа и пустое тело. */
    @Test
    void goodsListShouldBeEmpty_bdd() {
        given()
                .when()
                .get(RestApiBuilder.GOODS_LIST)
                .then()
                .statusCode(200)
                .body("goods", empty());
    }

    /** 2. То же самое, но с явной передачей RequestSpecification. */
    @Test
    void goodsListShouldBeEmpty_requestSpecification() {
        given(RestApiBuilder.requestSpecification())
                .when()
                .get(RestApiBuilder.GOODS_LIST)
                .then()
                .statusCode(200)
                .body("goods", empty())
        ;
    }

    /** 3. Создаём товар и проверяем его в списке встроенными проверками REST Assured. */
    @Test
    void addedGoodShouldAppearInList_restAssuredChecks() {
        String name = "Ноутбук " + System.currentTimeMillis();
        addGood(name, 1500.0);

        given()
                .when()
                .get(RestApiBuilder.GOODS_LIST)
                .then()
                .statusCode(200)
                .body("goods.name", hasItem(name))
                .body("goods.find { it.name == '%s' }.price", withArgs(name), equalTo(1500.0));
    }

    /** 4. Тот же сценарий, но проверки через AssertJ. */
    @Test
    void addedGoodShouldAppearInList_assertj() {
        String name = "Смартфон " + System.currentTimeMillis();
        addGood(name, 900.0);

        List<Map<String, Object>> goods = given()
                .when()
                .get(RestApiBuilder.GOODS_LIST)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("goods");

        Map<String, Object> created = goods.stream()
                .filter(g -> name.equals(g.get("name")))
                .findFirst()
                .orElse(null);

        assertThat(created)
                .as("товар '%s' должен быть в списке", name)
                .isNotNull()
                .containsEntry("name", name)
                .containsEntry("price", 900.0);
    }
}
