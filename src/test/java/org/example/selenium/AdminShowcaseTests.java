package org.example.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdminShowcaseTests extends BaseSeleniumTest {

    /* --- Локаторы админки --- */

    private final By loginUsername = By.id("username");
    private final By loginPassword = By.id("password");
    private final By loginSubmitBtn = By.cssSelector("button[type='submit']");

    private final By adminNameInput = By.id("n-name");
    private final By adminPriceInput = By.id("n-price");
    private final By adminAddBtn = By.id("add-btn");

    /* --- 1.1. Добавить товар через админку, проверить на витрине --- */

    @Test
    void addGoodViaAdmin_goodIsShownOnShowcase() {
        String name = "Товар " + System.nanoTime();

        driver.get(BASE_URL + "/login");
        adminLogin("admin", "secret123");
        adminAddGood(name, "123");

        driver.get(BASE_URL);
        By cardOfNewGood =
                By.cssSelector("#products-list .product-card[data-name='" + name + "']");

        waitUpTo().until(ExpectedConditions.presenceOfElementLocated(cardOfNewGood));
        assertTrue(driver.findElement(cardOfNewGood).isDisplayed(),
                "Товар, добавленный через админку, должен отображаться на витрине");
    }

    /* --- Хелперы админки --- */

    private void adminLogin(String login, String password) {
        driver.findElement(loginUsername).sendKeys(login);
        driver.findElement(loginPassword).sendKeys(password);
        driver.findElement(loginSubmitBtn).click();
    }

    private void adminAddGood(String name, String price) {
        sleep(1000);
        driver.findElement(adminNameInput).sendKeys(name);
        driver.findElement(adminPriceInput).sendKeys(price);
        driver.findElement(adminAddBtn).click();
    }
}
