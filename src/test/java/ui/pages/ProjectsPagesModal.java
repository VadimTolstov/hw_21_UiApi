package ui.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class ProjectsPagesModal {

    @Step("Проверяем, что открыта страница с проектами")
    public ProjectsPagesModal verifyProjectsPages() {
        $(".BreadCrumbs ").shouldHave(visible).shouldHave(text("Projects"));
        return this;
    }

    @Step("Открываем модальное окно создание проекта")
    public ProjectsPagesModal openModalCreateNewProject() {
        $(byText("Create new project")).click();
        return this;
    }

    @Step("Создаем новый проект {name}")
    public void createProject(String name) {
        $("[name=name]").setValue(name);
        $("[name=submit]").click();
    }
}
