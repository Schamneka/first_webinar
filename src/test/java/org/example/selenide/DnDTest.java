package org.example.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.DragAndDropOptions;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.actions;
import static com.codeborne.selenide.Selenide.open;

public class DnDTest extends BaseSelenideTest {

    SelenideElement firstCard = $("#products-list .product-card");
    SelenideElement cartButton = $("#open-cart-btn");

    @BeforeEach
    void setup() {
        open(BASE_URL);
        firstCard.shouldBe(visible);
    }

    /* --- 1.1. Добавление товара в корзину методом Drag-and-Drop --- */

    //№1 — штатный Selenide drag-and-drop
    @Test
    void simpleDnD() {
        addToCartViaDnd(() -> firstCard.dragAndDrop(DragAndDropOptions.to(cartButton)));
    }

    //№2 — цепочка drag-and-drop через Actions
    @Test
    void anotherDnD() {
        addToCartViaDnd(() -> actions().moveToElement(firstCard)
                .clickAndHold()
                .pause(1000)
                .moveToElement(cartButton)
                .release()
                .pause(Duration.ofSeconds(2))
                .perform());
    }

    //№3 — «медленный» drag-and-drop: плавное движение по 50 шагов
    @Test
    void slowDnD() {
        int firstCardX = center(firstCard, true);
        int firstCardY = center(firstCard, false);
        int cartButtonX = center(cartButton, true);
        int cartButtonY = center(cartButton, false);

        int diffX = cartButtonX - firstCardX;
        int diffY = cartButtonY - firstCardY;

        addToCartViaDnd(() -> {
            Actions dndActions = actions().moveToElement(firstCard).clickAndHold();
            for (int i = 0; i < 50; i++) {
                dndActions = dndActions.moveByOffset(diffX / 50, diffY / 50);
            }
            dndActions.release().perform();
        });
    }

    /* --- 1.2 Удаление ранее добавленного товара из корзины --- */

    @Test
    void removeItemFromCart_itemDisappears() {
        addToCartViaDnd(() -> firstCard.dragAndDrop(DragAndDropOptions.to(cartButton)));

        cartButton.click();
        $("#cart-items").shouldBe(visible);

        String name = firstCard.attr("data-name");
        var cartItem = $$("#cart-items > div").findBy(Condition.text(name));
        cartItem.shouldBe(visible);

        cartItem.$$("button").findBy(Condition.exactText("✕"))
                .shouldBe(visible)
                .click();

        // Товара с этим именем в корзине больше нет, счётчик обнулился
        $("#cart-items").shouldNotHave(Condition.text(name));
        $("#cart-count").shouldHave(exactText("0"));
    }

    /* --- Общие помощники --- */

    /** Выполняет переданный вариант перетаскивания и проверяет, что товар попал в корзину. */
    private void addToCartViaDnd(Runnable drag) {
        drag.run();
        $("#cart-count").shouldHave(exactText("1"));
    }

    /** X (axis=true) или Y центр элемента в окне просмотра. */
    private static int center(SelenideElement element, boolean axisX) {
        return axisX
                ? element.getCoordinates().inViewPort().getX() + element.getSize().getWidth() / 2
                : element.getCoordinates().inViewPort().getY() + element.getSize().getHeight() / 2;
    }
}


