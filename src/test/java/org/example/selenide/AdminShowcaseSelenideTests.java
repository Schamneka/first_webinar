package org.example.selenide;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Condition.visible;

public class AdminShowcaseSelenideTests extends BaseSelenideTest {

    /* --- 2.1. Добавить товар через админку, проверить на витрине --- */

    @Test
    void addGoodViaAdmin_goodIsShownOnShowcase() {
        String name = "Селено-товар " + System.nanoTime();

        open(BASE_URL + "/login");
        $("#username").setValue("admin");
        $("#password").setValue("secret123");
        $("button[type='submit']").click();

        $("#n-name").setValue(name);
        $("#n-price").setValue("123");
        $("#add-btn").click();

        open(BASE_URL);
        $("#products-list .product-card[data-name='" + name + "']").shouldBe(visible);
    }
}
