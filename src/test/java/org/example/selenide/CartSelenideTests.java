package org.example.selenide;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;

public class CartSelenideTests extends BaseSelenideTest {

    /* --- 2.2. Добавить товар в корзину, проверить, что он отображается --- */

    @Test
    void addToCart_goodIsShownInCart() {
        open(BASE_URL);

        SelenideElement firstCard = productCard.shouldBe(visible);
        String goodName = firstCard.$("h4").getText();

        firstAddToCartBtn.shouldBe(visible).click();
        cartCounter.shouldNotHave(text("0"));

        cartButton.shouldBe(visible).click();

        cartItemsContainer.$(".cart-item").shouldBe(visible);
        cartItemsContainer.shouldHave(text(goodName));
    }
}
