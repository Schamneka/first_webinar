package org.example.goods;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utils.ConfigParser;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public final class RestApiBuilder {

    public static final String BASE_URL = ConfigParser.getProperty("base.url");
    public static final String GOODS_LIST = "/goods/list";
    public static final String GOODS_ADD = "/goods/add";
    public static final String GOODS_BY_ID = "/goods/{id}";
    public static final String LOGIN = "/login";

    private static final String USER = ConfigParser.getProperty("admin.login");
    private static final String PASSWORD = ConfigParser.getProperty("admin.password");

    private static volatile String sessionCookie;

    private RestApiBuilder() {
    }

    /** Логинится по форме один раз и кэширует сессионную куку. */
    @Step("API: авторизация на /login (один раз, куки кэшируются)")
    private static synchronized String session() {
        if (sessionCookie == null) {
            String cookie = given()
                    .baseUri(BASE_URL)
                    .redirects().follow(false)          // куку выдаёт сам редирект — не теряем её
                    .contentType(ContentType.URLENC)
                    .formParam("username", USER)
                    .formParam("password", PASSWORD)
                    .post(LOGIN)
                    .then()
                    .statusCode(302)                    // успешный form-логин = редирект на /admin
                    .extract().response()
                    .getCookie("JSESSIONID");

            if (cookie == null) {
                throw new IllegalStateException(
                        "Логин на /login не вернул JSESSIONID — проверьте поля формы и имя куки (F12 → Network)");
            }
            sessionCookie = cookie;
        }
        return sessionCookie;
    }

    @Step("API: подготовка спецификации запроса с сессией")
    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addCookie("JSESSIONID", session())     // вместо .setAuth(preemptive().basic(...))
                .addFilter(new AllureRestAssured())
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    /** Создаёт товар и возвращает его id из ответа сервера. */
    @Step("API: создание товара «{name}» с ценой {price}")
    public static long createGood(String name, double price) {
        Map<String, Object> data = given()
                .spec(requestSpecification())           // ← спека с сессией
                .body(Map.of("name", name, "price", price))
                .post(GOODS_ADD)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getMap("data");

        return ((Number) data.values().iterator().next()).longValue();
    }

    @Step("API: удаление товара id={id}")
    public static void deleteGood(long id) {
        given()
                .spec(requestSpecification())
                .pathParam("id", id)
                .delete(GOODS_BY_ID)
                .then()
                .statusCode(200);
    }

    /** Ищет id товара по имени; null, если не найден или ответ не удалось разобрать. */
    @Step("API: поиск id товара по имени «{name}»")
    public static Long findGoodId(String name) {
        try {
            List<Map<String, Object>> goods = given()
                    .spec(requestSpecification())
                    .get(GOODS_LIST)
                    .then()
                    .extract()
                    .jsonPath()
                    .getList("goods");

            for (Map<String, Object> g : goods) {
                if (name.equals(g.get("name"))) {
                    return ((Number) g.get("id")).longValue();
                }
            }
        } catch (Exception e) {
            System.out.println("findGoodId: не удалось получить список товаров: " + e.getMessage());
        }
        return null;
    }

    /** Уникальное имя товара, чтобы тесты не мешали друг другу. */
    public static String uniqueName(String prefix) {
        return prefix + " " + System.nanoTime();
    }

    /** Удаление всех товаров. */
    @Step("API: очистить список товаров (удалить все)")
    public static void deleteAllGoods() {
        List<Map<String, Object>> goods = given()
                .spec(requestSpecification())
                .get(GOODS_LIST)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("goods");

        for (Map<String, Object> good : goods) {
            deleteGood(((Number) good.get("id")).longValue());
        }
    }
}


