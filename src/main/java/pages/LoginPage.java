package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement username = $("#username");
    private final SelenideElement password = $("#password");
    private final SelenideElement submitBtn = $("button[type='submit']");

    public LoginPage openPage(String baseUrl) {
        Selenide.open(baseUrl + "/login");
        return this;
    }

    public LoginPage enterUsername(String value) {
        username.setValue(value);
        return this;
    }

    public LoginPage enterPassword(String value) {
        password.setValue(value);
        return this;
    }

    public AdminPage clickSignIn() {
        submitBtn.click();
        return new AdminPage();
    }

    /* --- Базовые проверки внутри Page --- */

    public LoginPage assertUsernameHasText(String expected) {
        username.shouldBe(Condition.visible)
                .shouldHave(Condition.value(expected));
        return this;
    }

    public LoginPage assertPasswordHasText(String expected) {
        password.shouldBe(Condition.visible)
                .shouldHave(Condition.value(expected));
        return this;
    }

    public LoginPage assertSubmitVisible() {
        submitBtn.shouldBe(Condition.visible);
        return this;
    }
}

