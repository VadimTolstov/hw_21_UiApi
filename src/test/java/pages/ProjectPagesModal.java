package pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ProjectPagesModal {

    @Step("Проверяем, что проект {projectName} создан")
    public ProjectPagesModal verifyNewProject(String projectName) {
        $("[title='" + projectName + "']").shouldHave(visible).shouldHave(text(projectName));
        return this;
    }

    @Step("Удаляем проект")
    public void deleteProject() {
        $("[aria-label=Settings]").click();
        $(byText("Delete project")).click();
        $x("//button[contains(@class, 'ProjectSettings__confirm-button')]//span").click();

    }

}
