package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement username = $("#username");
    private final SelenideElement password = $("#password");
    private final SelenideElement submitBtn = $("button[type='submit']");

    @Step("Открыть страницу логина")
    public LoginPage openPage(String baseUrl) {
        Selenide.open(baseUrl + "/login");
        return this;
    }

    @Step("Ввести логин: {value}")
    public LoginPage enterUsername(String value) {
        username.setValue(value);
        return this;
    }

    @Step("Ввести пароль")
    public LoginPage enterPassword(String value) {
        password.setValue(value);
        return this;
    }

    @Step("Нажать «Sign in» и перейти в админку")
    public AdminPage clickSignIn() {
        submitBtn.click();
        return new AdminPage();
    }

    /* --- Базовые проверки внутри Page --- */

    @Step("Проверка: поле логина содержит «{expected}»")
    public LoginPage assertUsernameHasText(String expected) {
        username.shouldBe(Condition.visible)
                .shouldHave(Condition.value(expected));
        return this;
    }

    @Step("Проверка: поле пароля содержит «{expected}»")
    public LoginPage assertPasswordHasText(String expected) {
        password.shouldBe(Condition.visible)
                .shouldHave(Condition.value(expected));
        return this;
    }

    @Step("Проверка: кнопка «Sign in» видна")
    public LoginPage assertSubmitVisible() {
        submitBtn.shouldBe(Condition.visible);
        return this;
    }
}

