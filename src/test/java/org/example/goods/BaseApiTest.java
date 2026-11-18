package org.example.goods;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;

@Tag("autotests")
public abstract class BaseApiTest {

    @BeforeAll
    static void setup() {
        RestAssured.requestSpecification = RestApiBuilder.requestSpecification();
    }
}
