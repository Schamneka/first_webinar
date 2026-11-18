package org.example.selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Tag;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Tag("autotests")
@Tag("selenide")
public abstract class BaseSelenideTest {

    protected static final String BASE_URL = "http://localhost:8080";

    /* --- Локаторы витрины и корзины --- */

    protected final SelenideElement productCard = $("#products-list .product-card");
    protected final SelenideElement firstAddToCartBtn =
            $("#products-list .product-card button[data-action='add-to-cart']");
    protected final SelenideElement cartButton = $("#open-cart-btn");
    protected final SelenideElement cartItemsContainer = $("#cart-items");
    protected final SelenideElement cartCounter = $("#cart-count");

    static {
        Configuration.timeout = 6000; // базовое ожидание для всех shouldBe
    }

    protected int cartItemsCount() {
        return $$("#cart-items .cart-item").size();
    }
}