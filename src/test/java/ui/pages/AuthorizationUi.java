package ui.pages;

import com.codeborne.selenide.SelenideElement;
import config.Auth;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthorizationUi {

    private final SelenideElement userNameInput = $("[name='username']"),
            passwordInput = $("[name='password']");

    @Step("Авторизация пользователя в Allure")
    public void authorizationUi() {
        open("");
        userNameInput.setValue(Auth.config.userNameAllure());
        passwordInput.setValue(Auth.config.passwordAllure()).pressEnter();
    }
}
