package org.example.cart;

import com.codeborne.selenide.SelenideElement;
import pages.MainPage;
import org.example.selenide.BaseSelenideTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartOrderPageObjectTests extends BaseSelenideTest {

    private final MainPage main = new MainPage();

    @BeforeEach
    void openShop() {
        main.openPage(BASE_URL)
                .assertTitleVisible();
    }

    /* --- 2.1. Три единицы товара, оплата, уведомление об обработке --- */

    @Test
    void payThreeUnits_showsProcessingNotification() {
        SelenideElement cheapest = main.cheapestCard();
        int minPrice = Integer.parseInt(cheapest.attr("data-price"));
        String id = cheapest.attr("data-id");

        assertTrue(3 * minPrice <= 300,
                "Три штуки самого дешёвого товара стоят " + 3 * minPrice + " ₽ — предусловие кейса не выполнено");

        main.setQtyOnCard(cheapest, "3")
                .addToCartOnCard(cheapest)
                .assertCounter("3")
                .openCart()
                .clickMakeOrder()
                .assertToastShown("Заказ принят в обработку!");
    }

    /* --- 2.2. Несколько разных товаров, сумма считается корректно --- */

    @Test
    void differentGoods_cartTotalIsCorrect() {
        SelenideElement first = main.cards().first();
        SelenideElement second = main.cards().get(1);

        int expected = Integer.parseInt(first.attr("data-price"))
                + Integer.parseInt(second.attr("data-price"));

        main.addToCartOnCard(first)
                .assertCounter("1")
                .addToCartOnCard(second)
                .assertCounter("2")
                .openCart()
                .assertThat()
                .totalPrice(String.valueOf(expected));

    }
}
