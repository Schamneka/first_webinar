package pages;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

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

    public AdminPage enterName(String name) {
        nameInput.setValue(name);
        return this;
    }

    public AdminPage enterPrice(String price) {
        priceInput.setValue(price);
        return this;
    }

    public AdminPage clickAdd() {
        addBtn.shouldBe(Condition.visible).click();
        return this;
    }

    /** Добавить товар одним действием: имя + цена + клик. */
    public AdminPage addGood(String name, String price) {
        return enterName(name).enterPrice(price).clickAdd();
    }

    /** Удалить товар по id кнопкой в строке таблицы. */
    public AdminPage deleteGood(long id) {
        $("button.btn-del[data-id='" + id + "']").shouldBe(Condition.visible).click();
        return this;
    }

    /* --- Получение данных --- */

    /** Все текстовые инпуты таблицы товаров (поля имён и цен). */
    public ElementsCollection tableInputs() {
        return goodsTable.$$("input[type='text']");
    }

    /** id товара, найденного по имени в таблице: из инпута "nm-42" достаётся 42. */
    public long goodIdFromTable(String name) {
        return Long.parseLong(
                tableInputs().findBy(Condition.value(name)).attr("id").substring(3));
    }

    /* --- Базовые проверки (внутри Page) --- */

    public AdminPage assertToastShown(String message) {
        toast.shouldBe(Condition.visible, Duration.ofSeconds(6))
                .shouldHave(Condition.text(message));
        return this;
    }

    public AdminPage assertNameInputHasText(String expected) {
        nameInput.shouldBe(Condition.visible)
                .shouldHave(Condition.value(expected));
        return this;
    }
}

