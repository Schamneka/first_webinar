package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.DragAndDropOptions;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;
import java.util.Comparator;

import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    /* --- Элементы --- */

    private final SelenideElement title = $("#main-title");
    private final SelenideElement adminLink = $("a.btn-outline[href='/admin']");
    private final SelenideElement cartButton = $("#open-cart-btn");
    private final SelenideElement cartCounter = $("#cart-count");
    private final SelenideElement productsList = $("#products-list");

    private final SelenideElement firstCard = productsList.$(".product-card");
    private final SelenideElement firstQtyInput = firstCard.$("input.qty-input");
    private final SelenideElement firstMinusBtn = firstCard.$("button[data-step='-1']");
    private final SelenideElement firstPlusBtn = firstCard.$("button[data-step='1']");
    private final SelenideElement firstAddToCartBtn = firstCard.$("button[data-action='add-to-cart']");

    private final SelenideElement cartModal = $("#cartModal");
    private final SelenideElement closeModal = $("#close-modal");
    private final SelenideElement totalPrice = $("#total-price");
    private final SelenideElement makeOrderBtn = $("#makeOrder");

    /* --- Методы взаимодействия --- */

    @Step("Открыть витрину")
    public MainPage openPage(String baseUrl) {
        Selenide.open(baseUrl);
        return this;
    }

    // клик по ссылке «Администрирование»
    @Step("Клик по ссылке «Администрирование»")
    public void clickAdminLink() {
        adminLink.click();
    }

    // клик по кнопке корзины — открывает модалку
    @Step("Клик по кнопке корзины")
    public MainPage clickCartButton() {
        cartButton.click();
        return this;
    }

    // клик «В корзину» на первой карточке
    @Step("Клик «В корзину» на первой карточке")
    public MainPage clickAddToCart() {
        firstAddToCartBtn.click();
        return this;
    }

    // инпут количества первой карточки: ввести значение с клавиатуры (sendKeys)
    @Step("Ввести количество {qty} в инпут первой карточки")
    public MainPage setQty(String qty) {
        firstQtyInput.setValue(qty);
        firstQtyInput.pressEnter();
        return this;
    }

    // клики по «−» и «+» первой карточки
    @Step("Клик «−» на первой карточке")
    public MainPage clickMinus() {
        firstMinusBtn.click();
        return this;
    }

    @Step("Клик «+» на первой карточке")
    public MainPage clickPlus() {
        firstPlusBtn.click();
        return this;
    }

    // закрыть модалку корзины крестиком
    @Step("Закрыть модалку корзины крестиком")
    public MainPage closeModal() {
        closeModal.click();
        return this;
    }

    // оформить заказ
    @Step("Нажать «Оформить заказ»")
    public MainPage clickMakeOrder() {
        makeOrderBtn.click();
        return this;
    }

    // drag-and-drop первой карточки на кнопку корзины (кейсы 1.1 из DnD-тестов)
    @Step("Перетащить первую карточку на кнопку корзины")
    public MainPage dragFirstCardToCart() {
        firstCard.dragAndDrop(DragAndDropOptions.to(cartButton));
        return this;
    }

    /* --- Работа с коллекцией карточек --- */

    /** Все карточки витрины. */
    public ElementsCollection cards() {
        return productsList.$$(".product-card");
    }

    /** Самый дешёвый товар на витрине. */
    @Step("Найти самый дешёвый товар на витрине")
    public SelenideElement cheapestCard() {
        return cards().stream()
                .min(Comparator.comparingInt(c -> Integer.parseInt(c.attr("data-price"))))
                .orElseThrow();
    }

    /* --- Взаимодействие с произвольной карточкой --- */

    /** Ввести количество в инпут конкретной карточки. */
    @Step("Ввести количество {qty} в инпут выбранной карточки")
    public MainPage setQtyOnCard(SelenideElement card, String qty) {
        SelenideElement qtyInput = card.$("input.qty-input");
        qtyInput.setValue(qty);
        qtyInput.pressEnter();          // чтобы сработал обработчик change
        return this;
    }

    /** Клик «В корзину» на конкретной карточке. */
    @Step("Клик «В корзину» на выбранной карточке")
    public MainPage addToCartOnCard(SelenideElement card) {
        card.$("button[data-action='add-to-cart']").shouldBe(Condition.visible).click();
        return this;
    }

    /** Открыть корзину и дождаться видимости её содержимого. */
    @Step("Открыть корзину и дождаться её содержимого")
    public MainPage openCart() {
        cartButton.click();
        cartModal.shouldBe(Condition.visible);
        return this;
    }

    /** Тост с заданным текстом появился и виден. */
    @Step("Проверка: показан тост «{message}»")
    public MainPage assertToastShown(String message) {
        Selenide.$$(".toast").findBy(Condition.text(message))
                .shouldBe(Condition.visible, Duration.ofSeconds(6));
        return this;
    }

    /* --- Геттеры для Assert-класса и проверок в тестах --- */

    public SelenideElement titleElement()       { return title; }
    public SelenideElement adminLinkElement()   { return adminLink; }
    public SelenideElement cartCounterElement() { return cartCounter; }
    public SelenideElement cartModalElement()   { return cartModal; }
    public SelenideElement totalPriceElement()  { return totalPrice; }

    public SelenideElement cardByDataName(String name) {
        return productsList.$(".product-card[data-name='" + name + "']");
    }

    /* --- Встроенные базовые проверки --- */

    @Step("Проверка: заголовок витрины виден")
    public MainPage assertTitleVisible() {
        title.shouldBe(Condition.visible);
        return this;
    }

    @Step("Проверка: счётчик корзины равен {expected}")
    public MainPage assertCounter(String expected) {
        cartCounter.shouldHave(Condition.exactText(expected));
        return this;
    }

    /** Мост к Assert-классу */
    public MainPageAssert assertThat() {
        return new MainPageAssert(this);
    }
}
