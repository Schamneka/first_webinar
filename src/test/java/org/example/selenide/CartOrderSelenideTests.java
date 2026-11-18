package org.example.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartOrderSelenideTests extends BaseSelenideTest {

    /* --- 3.1. Три единицы товара, оплата, уведомление об обработке --- */

    @Test
    void payThreeUnits_showsProcessingNotification() {
        open(BASE_URL);
        productCard.shouldBe(visible);

        ElementsCollection cards = $$("#products-list .product-card");

        // Минимальная цена
        int minPrice = cards.attributes("data-price").stream()
                .mapToInt(Integer::parseInt)
                .min()
                .orElseThrow();

        // Поиск карточки
        var cheapest = cards.findBy(Condition.attribute("data-price", String.valueOf(minPrice)));

        String id = cheapest.attr("data-id");
        assertTrue(3 * minPrice <= 300,
                "Три штуки самого дешёвого товара стоят " + 3 * minPrice + " ₽ — предусловие кейса не выполнено");

        // Выставляем количество 3 и добавляем одним кликом
        cheapest.$("#q-" + id).setValue("3");
        cheapest.$("button[data-action='add-to-cart']").shouldBe(visible).click();

        cartCounter.shouldHave(exactText("3"));

        cartButton.shouldBe(visible).click();
        cartItemsContainer.shouldBe(visible);
        $("#makeOrder").shouldBe(visible).click();

        $$(".toast").findBy(Condition.text("Заказ принят в обработку!"))
                .shouldBe(visible, Duration.ofSeconds(6));
    }

    /* --- 3.2. Несколько разных товаров, сумма считается корректно --- */

    @Test
    void differentGoods_cartTotalIsCorrect() {
        open(BASE_URL);
        productCard.shouldBe(visible);

        ElementsCollection cards = $$("#products-list .product-card");
        var first = cards.first();
        var second = cards.get(1);

        int expected = Integer.parseInt(first.attr("data-price"))
                + Integer.parseInt(second.attr("data-price"));

        first.$("button[data-action='add-to-cart']").shouldBe(visible).click();
        cartCounter.shouldHave(exactText("1"));
        second.$("button[data-action='add-to-cart']").shouldBe(visible).click();
        cartCounter.shouldHave(exactText("2"));

        cartButton.shouldBe(visible).click();
        cartItemsContainer.shouldBe(visible);

        // Итог в корзине
        $("#total-price").shouldHave(text(String.valueOf(expected)));
    }
}

