package org.example.selenide;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdminLoginSelenideTests extends BaseSelenideTest {

    /* --- 2.3. Попытка входа в админку с неверными учётными данными --- */

    @Test
    void loginWithWrongCredentials_accessIsDenied() {
        open(BASE_URL + "/login");

        $("#username").setValue("wrongUser");
        $("#password").setValue("wrongPassword");
        $("button[type='submit']").click();

        // Снова показана форма входа — доступ не предоставлен
        $("#username").shouldBe(visible);
        assertTrue(WebDriverRunner.url().contains("error"),
                "При неверных учётных данных должен произойти возврат на страницу входа с ошибкой");
    }
}
