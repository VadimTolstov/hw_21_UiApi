package ui.pages;

import config.Auth;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthorizationUi {

    @Step("Авторизация пользователя в Allure")
    public void authorizationUi() {
        open("");
        $("[name='username']").setValue(Auth.config.userNameAllure());
        $("[name='password']").setValue(Auth.config.passwordAllure()).pressEnter();
    }
}
