package org.example.selenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import utils.ConfigParser;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class AdminNotificationSelenideTests extends BaseSelenideTest {

    private String createdName = "";
    private Long createdId = null;   // id добавленного товара

    /* --- 3.3. Вход в админку, добавление товара, проверка уведомления --- */

    @Test
    void adminAddsGood_notificationShown() {
        createdName = "Товар " + System.nanoTime();

        open(BASE_URL + "/login");
        $("#username").setValue(ConfigParser.getProperty("admin.login"));
        $("#password").setValue(ConfigParser.getProperty("admin.password"));
        $("button[type='submit']").click();

        $("#n-name").setValue(createdName);
        $("#n-price").setValue("77");
        $("#add-btn").shouldBe(visible).click();

        // Уведомление
        $(".toast").shouldBe(visible, Duration.ofSeconds(6))
                .shouldHave(Condition.text("Товар успешно добавлен!"));

        // запоминание id товара
        createdId = Long.parseLong(
                $$("#tbody input[type='text']")
                        .findBy(Condition.value(createdName))
                        .attr("id")
                        .substring(3));
    }

    /* --- 3.6. Удаляется добавленный товар --- */

    @AfterEach
    void cleanupCreatedGood() {
        if (createdId != null) {
            $("button.btn-del[data-id='" + createdId + "']").click();
        }
    }
}


