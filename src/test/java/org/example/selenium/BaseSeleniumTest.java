package org.example.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Tag("autotests")
@Tag("selenium")
public abstract class BaseSeleniumTest {

    protected static final String BASE_URL = "http://localhost:8080";

    protected WebDriver driver;

    /* --- Общие локаторы витрины и корзины (подтверждены разметкой) --- */

    protected final By productCard = By.cssSelector("#products-list .product-card");
    protected final By productCardName = By.tagName("h4");
    protected final By firstAddToCartBtn =
            By.cssSelector("#products-list .product-card button[data-action='add-to-cart']");
    protected final By cartButton = By.id("open-cart-btn");
    protected final By cartItems = By.cssSelector("#cart-items .cart-item");
    protected final By cartItemsContainer = By.id("cart-items");

    protected WebDriverWait waitUpTo() {
        return new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}