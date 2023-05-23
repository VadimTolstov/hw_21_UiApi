package ui.pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class ProjectsPagesModal {

    @Step("Проверяем, что открыта страница с проектами")
    public void verifyProjectsPages(){
        $(".BreadCrumbs ").shouldHave(Condition.text("Projects"));
    }

}
