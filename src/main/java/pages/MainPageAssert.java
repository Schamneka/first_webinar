package pages;

import com.codeborne.selenide.Condition;

public class MainPageAssert {

    private final MainPage page;

    public MainPageAssert(MainPage page) {
        this.page = page;
    }

    /** Заголовок виден и содержит имя магазина. */
    public MainPageAssert titleVisible(String expectedPart) {
        page.titleElement().shouldBe(Condition.visible)
                .shouldHave(Condition.text(expectedPart));
        return this;
    }

    /** Ссылка в админку видима. */
    public MainPageAssert adminLinkVisible() {
        page.adminLinkElement().shouldBe(Condition.visible);
        return this;
    }

    /** Счётчик корзины равен ожидаемому значению. */
    public MainPageAssert cartCounter(String expected) {
        page.cartCounterElement().shouldHave(Condition.exactText(expected));
        return this;
    }

    /** На витрине есть карточка с заданным именем и ценой. */
    public MainPageAssert productCardVisible(String name, String price) {
        page.cardByDataName(name).shouldBe(Condition.visible)
                .shouldHave(Condition.attribute("data-price", price));
        return this;
    }

    /** Карточки с заданным именем на витрине нет. */
    public MainPageAssert productCardAbsent(String name) {
        page.cardByDataName(name).shouldNot(Condition.exist);
        return this;
    }

    /** Модалка корзины видима. */
    public MainPageAssert cartModalVisible() {
        page.cartModalElement().shouldBe(Condition.visible);
        return this;
    }

    /** Сумма в корзине равна ожидаемой. */
    public MainPageAssert totalPrice(String expected) {
        page.totalPriceElement().shouldHave(Condition.exactText(expected));
        return this;
    }
}
