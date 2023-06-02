package ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ProjectPagesModal {

    private final SelenideElement openDeleteProjectPagesInput = $("[aria-label=Settings]"),
            openDeleteProjectInput = $(byText("Delete project")),
            deleteProjectInput = $x("//button[contains(@class, 'ProjectSettings__confirm-button')]//span");


    @Step("Проверяем, что проект {projectName} создан")
    public ProjectPagesModal verifyNewProject(String projectName) {
        $("[title='" + projectName + "']").shouldHave(visible).shouldHave(text(projectName));
        return this;
    }

    @Step("Удаляем проект")
    public void deleteProject() {
        openDeleteProjectPagesInput.click();
        openDeleteProjectInput.shouldHave(visible).click();
        deleteProjectInput.click();

    }

}
