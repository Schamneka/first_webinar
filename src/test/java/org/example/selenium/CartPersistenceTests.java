package org.example.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartPersistenceTests extends BaseSeleniumTest {

    /* --- 1.4. Корзина сохраняется после обновления страницы --- */

    @Test
    void cartIsPreservedAfterPageRefresh() {
        driver.get(BASE_URL);

        // Дожидаемся, пока витрина отрендерится: карточка товара с кнопкой "В корзину"
        waitUpTo().until(ExpectedConditions.presenceOfElementLocated(firstAddToCartBtn));

        // Добавляем товар и ждём, пока он реально попадёт в корзину на сервере
        driver.findElement(firstAddToCartBtn).click();
        waitUpTo().until(ExpectedConditions.not(
                ExpectedConditions.textToBe(By.id("cart-count"), "0")));

        // 1. Перейти в корзину
        driver.findElement(cartButton).click();

        // 2. Запомнить количество товаров в корзине
        waitUpTo().until(ExpectedConditions.presenceOfAllElementsLocatedBy(cartItems));
        int countBefore = driver.findElements(cartItems).size();

        // 3. Обновить страницу
        driver.navigate().refresh();

        // Дожидаемся, что скрипты страницы отработали: витрина снова отрендерена
        waitUpTo().until(ExpectedConditions.presenceOfElementLocated(productCard));

        // 4. Перейти в корзину
        driver.findElement(cartButton).click();

        // 5. Сравнить актуальное количество с запомненным
        waitUpTo().until(ExpectedConditions.presenceOfAllElementsLocatedBy(cartItems));
        int countAfter = driver.findElements(cartItems).size();

        assertEquals(countBefore, countAfter,
                "Количество товаров в корзине должно сохраниться после обновления страницы");
    }
}



