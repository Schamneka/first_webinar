package org.example.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartTests extends BaseSeleniumTest {

    /* --- 1.2. Добавить товар в корзину, проверить, что он отображается --- */

    @Test
    void addToCart_goodIsShownInCart() {
        driver.get(BASE_URL);

        String goodName = driver.findElement(productCard)
                .findElement(productCardName).getText();
        driver.findElement(firstAddToCartBtn).click();
        driver.findElement(cartButton).click();

        waitUpTo().until(ExpectedConditions.presenceOfAllElementsLocatedBy(cartItems));
        String cartText = driver.findElement(cartItemsContainer).getText();
        assertTrue(cartText.contains(goodName),
                "Добавленный товар должен отображаться в корзине");
    }
}