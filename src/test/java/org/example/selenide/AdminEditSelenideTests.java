package org.example.selenide;

import com.codeborne.selenide.Condition;
import org.example.goods.RestApiBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.ConfigParser;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class AdminEditSelenideTests extends BaseSelenideTest {

    private String goodName;
    private long goodId = 0;

    /* --- 3.5. Тестовые данные генерируются обращением к API --- */

    @BeforeEach
    void createGoodViaApi() {
        goodName = ConfigParser.getProperty("product.name") + " " + System.nanoTime();
        goodId = RestApiBuilder.createGood(goodName,
                Double.parseDouble(ConfigParser.getProperty("product.price")));
    }

    /* --- 3.4. Редактирование товара и проверка изменений --- */

    @Test
    void adminEditsGood_changesApplied() {
        String updatedName = goodName + "-upd";
        String updatedPrice = "99";

        open(BASE_URL + "/login");
        $("#username").setValue(ConfigParser.getProperty("admin.login"));
        $("#password").setValue(ConfigParser.getProperty("admin.password"));
        $("button[type='submit']").click();

        // Ищем инпут имени по его значению; из id инпута "nm-<id>" достаём id товара,
        // а строку получаем через closest("tr")
        var nameInput = $$("#tbody input[type='text']")
                .findBy(Condition.value(goodName));
        String id = nameInput.attr("id").substring(3);      // "nm-42" → "42"
        var row = nameInput.closest("tr");

        row.$("#nm-" + id).setValue(updatedName);
        row.$("#pr-" + id).setValue(updatedPrice);
        row.$(".btn-upd").shouldBe(Condition.visible).click();

        // Изменения применились: на витрине карточка с новым именем и новой ценой нет
        open(BASE_URL);

        $("#products-list .product-card[data-name='" + updatedName + "']")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.attribute("data-price", updatedPrice));

        $$("#products-list .product-card[data-name='" + goodName + "']")
                .shouldHave(size(0).because("Карточки со старым именем не должно быть на витрине"));
    }

    /* --- 3.6. Удаляем созданные данные --- */

    @AfterEach
    void cleanup() {
        if (goodId > 0) {
            RestApiBuilder.deleteGood(goodId);
        }
    }
}

