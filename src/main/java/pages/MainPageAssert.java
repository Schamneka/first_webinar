package pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

public class MainPageAssert {

    private final MainPage page;

    public MainPageAssert(MainPage page) {
        this.page = page;
    }

    /** Заголовок виден и содержит имя магазина. */
    @Step("Проверка: заголовок содержит «{expectedPart}»")
    public MainPageAssert titleVisible(String expectedPart) {
        page.titleElement().shouldBe(Condition.visible)
                .shouldHave(Condition.text(expectedPart));
        return this;
    }

    /** Ссылка в админку видима. */
    @Step("Проверка: ссылка «Администрирование» видима")
    public MainPageAssert adminLinkVisible() {
        page.adminLinkElement().shouldBe(Condition.visible);
        return this;
    }

    /** Счётчик корзины равен ожидаемому значению. */
    @Step("Проверка: счётчик корзины равен {expected}")
    public MainPageAssert cartCounter(String expected) {
        page.cartCounterElement().shouldHave(Condition.exactText(expected));
        return this;
    }

    /** На витрине есть карточка с заданным именем и ценой. */
    @Step("Проверка: карточка «{name}» видна с ценой {price}")
    public MainPageAssert productCardVisible(String name, String price) {
        page.cardByDataName(name).shouldBe(Condition.visible)
                .shouldHave(Condition.attribute("data-price", price));
        return this;
    }

    /** Карточки с заданным именем на витрине нет. */
    @Step("Проверка: карточки «{name}» нет на витрине")
    public MainPageAssert productCardAbsent(String name) {
        page.cardByDataName(name).shouldNot(Condition.exist);
        return this;
    }

    /** Модалка корзины видима. */
    @Step("Проверка: модалка корзины видима")
    public MainPageAssert cartModalVisible() {
        page.cartModalElement().shouldBe(Condition.visible);
        return this;
    }

    /** Сумма в корзине равна ожидаемой. */
    @Step("Проверка: сумма в корзине равна {expected}")
    public MainPageAssert totalPrice(String expected) {
        page.totalPriceElement().shouldHave(Condition.exactText(expected));
        return this;
    }
}
