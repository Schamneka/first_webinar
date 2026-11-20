package org.example.admin;

import pages.AdminPage;
import pages.LoginPage;
import org.example.selenide.BaseSelenideTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import utils.ConfigParser;

public class AdminNotificationPageObjectTests extends BaseSelenideTest {

    private final LoginPage loginPage = new LoginPage();
    private final AdminPage adminPage = new AdminPage();
    private final MainPage mainPage = new MainPage();

    private String createdName = "";
    private Long createdId = null;

    /* --- 2.3. Вход в админку, добавление товара, проверка уведомления --- */

    @Test
    void adminAddsGood_notificationShown() {
        createdName = ConfigParser.getProperty("product.name") + " " + System.nanoTime();

        createdId = loginPage.openPage(BASE_URL)
                .enterUsername(ConfigParser.getProperty("admin.login"))
                .enterPassword(ConfigParser.getProperty("admin.password"))
                .clickSignIn()
                .addGood(createdName, "77")
                .assertToastShown("Товар успешно добавлен!")
                .goodIdFromTable(createdName);
    }

    /* --- Удаляется добавленный товар --- */

    @AfterEach
    void cleanupCreatedGood() {
        if (createdId != null) {
            adminPage.deleteGood(createdId);
        }
    }
}
