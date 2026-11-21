package org.example.selenide;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartPersistenceSelenideTests extends BaseSelenideTest {

    /* --- 2.4. Корзина сохраняется после обновления страницы --- */

    @Test
    void cartIsPreservedAfterPageRefresh() {
        open(BASE_URL);

        // Добавляем товар и ждём, пока он реально попадёт в корзину
        firstAddToCartBtn.shouldBe(visible).click();
        cartCounter.shouldNotHave(exactText("0"));

        // 1. Перейти в корзину
        cartButton.shouldBe(visible).click();

        // 2. Запомнить количество товаров в корзине
        int countBefore = cartItemsCount(); // .cart-item уже shouldBe(visible) внутри ожидания счётчика

        // 3. Обновить страницу
        refresh();

        // Маркер готовности скриптов после refresh
        productCard.shouldBe(visible);

        // 4. Перейти в корзину
        cartButton.shouldBe(visible).click();

        // 5. Сравнить актуальное количество с запомненным
        assertEquals(countBefore, cartItemsCount(),
                "Количество товаров в корзине должно сохраниться после обновления страницы");
    }
}
