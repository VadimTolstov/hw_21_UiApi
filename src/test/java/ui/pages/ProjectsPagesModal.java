package ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class ProjectsPagesModal {

    private final SelenideElement verifyProjectsPagesInput = $(".BreadCrumbs "),
            openModalCreateNewProjectInput = $(byText("Create new project")),
            createProjectInput = $("[name=name]"),
            saveProject = $("[name=submit]");

    @Step("Проверяем, что открыта страница с проектами")
    public ProjectsPagesModal verifyProjectsPages() {
        verifyProjectsPagesInput.shouldHave(visible).shouldHave(text("Projects"));
        return this;
    }

    @Step("Открываем модальное окно создание проекта")
    public ProjectsPagesModal openModalCreateNewProject() {
        openModalCreateNewProjectInput.click();
        return this;
    }

    @Step("Создаем новый проект {name}")
    public void createProject(String name) {
        createProjectInput.setValue(name);
        saveProject.click();
    }
}
