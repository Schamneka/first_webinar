package pages;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class AdminPage {

    /* --- Элементы --- */

    private final SelenideElement nameInput = $("#n-name");
    private final SelenideElement priceInput = $("#n-price");
    private final SelenideElement addBtn = $("#add-btn");
    private final SelenideElement goodsTable = $("#tbody");
    private final SelenideElement toast = $(".toast");

    /* --- Методы взаимодействия --- */

    @Step("Открыть админку")
    public AdminPage openPage(String baseUrl) {
        Selenide.open(baseUrl + "/admin");
        Selenide.refresh();
        return this;
    }

    @Step("Ввести имя товара «{name}»")
    public AdminPage enterName(String name) {
        nameInput.setValue(name);
        return this;
    }

    @Step("Ввести цену товара: {price}")
    public AdminPage enterPrice(String price) {
        priceInput.setValue(price);
        return this;
    }

    @Step("Нажать «Добавить товар»")
    public AdminPage clickAdd() {
        addBtn.shouldBe(Condition.visible).click();
        return this;
    }

    /** Добавить товар одним действием: имя + цена + клик. */
    @Step("Добавить товар «{name}» с ценой {price}")
    public AdminPage addGood(String name, String price) {
        return enterName(name).enterPrice(price).clickAdd();
    }

    /** Удалить товар по id кнопкой в строке таблицы. */
    @Step("Удалить товар id={id} кнопкой в таблице")
    public AdminPage deleteGood(long id) {
        $("button.btn-del[data-id='" + id + "']").shouldBe(Condition.visible).click();
        return this;
    }

    /* --- Получение данных --- */

    /** Все текстовые инпуты таблицы товаров (поля имён и цен). */
    public ElementsCollection tableInputs() {
        return goodsTable.$$("input[type='text']");
    }

    /** id товара, найденного по имени в таблице */
    @Step("Найти id товара «{name}» в таблице админки")
    public long goodIdFromTable(String name) {
        return Long.parseLong(
                tableInputs().findBy(Condition.value(name)).attr("id").substring(3));
    }

    /* --- Базовые проверки (внутри Page) --- */

    @Step("Проверка: показан тост «{message}»")
    public AdminPage assertToastShown(String message) {
        toast.shouldBe(Condition.visible, Duration.ofSeconds(6))
                .shouldHave(Condition.text(message));
        return this;
    }

    @Step("Проверка: поле имени содержит «{expected}»")
    public AdminPage assertNameInputHasText(String expected) {
        nameInput.shouldBe(Condition.visible)
                .shouldHave(Condition.value(expected));
        return this;
    }
}

