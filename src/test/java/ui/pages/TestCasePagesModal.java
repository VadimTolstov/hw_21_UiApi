package ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class TestCasePagesModal {

    private final String ALLURE_URL_PROJECT = "https://allure.autotests.cloud/project/";

    private final SelenideElement
            testCaseInput = $(".TestCaseLayout__name"),
            testCaseDescriptionInput = $("[data-testid='section__description']"),
            testCaseTagInput = $("[data-testid='section__tags']"),
            testCaseCommentInput = $(".Comment__body");

    @Step("Открываем страницу проекта {projectId} с test case {testCaseId}")
    public TestCasePagesModal openPageTestCase(Long projectId, Long testCaseId) {
        Selenide.open(ALLURE_URL_PROJECT + projectId + "/test-cases/" + testCaseId);
        return this;
    }

    @Step("Открываем страницу с test cases")
    public TestCasePagesModal openTestCasesPages() {
        $("[name='test-cases-icon']").click();
        return this;
    }

    @Step("Создаем test case {name}")
    public TestCasePagesModal createTestCase(String name) {
        $("[data-testid='input__create_test_case']").setValue(name).pressEnter();
        return this;
    }

    @Step("Открываем test case {}")
    public TestCasePagesModal openTestCase(String nameTestCase) {
        $(byText(nameTestCase)).click();
        return this;
    }

    @Step("Открываем step в test case")
    public TestCasePagesModal openStepTestCase() {
        $x("//div[@class='ScenarioSection__name']/../..//div[@class='PaneSection__controls']").click();
        return this;
    }

    @Step("Добовляем step {step} в test case")
    public TestCasePagesModal createStepTestCase(String step) {
        $(".TestCaseScenarioStepEdit__textarea").setValue(step).pressEnter();
        return this;
    }

    @Step("Сохраняем данные в test case")
    public TestCasePagesModal saveDataTestCase() {
        $(byText("Submit")).click();
        return this;
    }

    @Step("Добовляем description {description} в test case")
    public TestCasePagesModal createDescriptionTestCase(String description) {
        $x("//section[@data-testid='section__description']/../..//div[@class='PaneSection__controls']").click();
        $("[name='description']").setValue(description);
        return this;
    }

    @Step("Открываем tag в test case")
    public TestCasePagesModal openTagTestCase() {
        $x("//section[@data-testid='section__tags']//button").click();
        return this;
    }

    @Step("Добовляем tag {tag} в test case")
    public TestCasePagesModal createTagTestCase(String tag) {
        $x("//label[@class='FormLabel ']").sendKeys(tag);
        $(byText("Create \"" + tag + "\"")).shouldHave(visible).click();
        return this;
    }

    @Step("Изменяем имя у test case")
    public TestCasePagesModal renameTestCase(String newNameTestCase) {
        $(".LeafStatus").hover();
        $(".LoadableTreeNodeView__controls").shouldHave(visible).click();
        $(".Form_inline").click();
        $(".AutosizeTextarea").setValue(newNameTestCase);
        $("[type='submit']").click();
        Selenide.refresh();
        return this;
    }

    @Step("Открываем вкладку attachments")
    public TestCasePagesModal openAttachmentTestCase() {
        $(byText("Attachments")).click();
        return this;
    }

    @Step("Загружаем файл {filePath}")
    public TestCasePagesModal setUploadAttachments(String filePath) {
        $("input[type='file']").uploadFromClasspath(filePath);
        return this;
    }

    @Step("Проверяем, что attachment {attachment} добавлен")
    public TestCasePagesModal verifyAddAttachment(String attachment) {
        $(".AttachmentRow__name").shouldHave(text(attachment)).shouldHave(visible);
        return this;
    }

    @Step("Проверяем, что у test case {name}{newName} изменилось имя")
    public TestCasePagesModal verifyNewNameTestCase(String name, String newName) {
        testCaseInput.shouldHave(text(name + newName)).shouldHave(visible);
        return this;
    }


    @Step("Проверяем имя {testCaseName} у test case")
    public TestCasePagesModal verifyNameTestCase(String testCaseName) {
        testCaseInput.shouldHave(text(testCaseName)).shouldHave(visible);
        return this;
    }

    @Step("Проверяем описание у test case")
    public TestCasePagesModal verifyDescriptionTestCase(String testCaseDescription) {
        testCaseDescriptionInput.shouldHave(text(testCaseDescription)).shouldHave(visible);
        return this;
    }

    @Step("Проверяем, что tag {tagName} добавлен в test case")
    public TestCasePagesModal verifyTagTestCase(String tagName) {
        testCaseTagInput.shouldHave(text(tagName)).shouldHave(visible);
        return this;
    }

    @Step("Проверяем, что коментарий {comment} добавлен к test case")
    public TestCasePagesModal verifyCommentTestCase(String comment) {
        testCaseCommentInput.shouldHave(text(comment)).shouldHave(visible);
        return this;
    }

    @Step("Проверяем, что test case {nameTestCase} создался")
    public TestCasePagesModal verifyTestCaseName(String nameTestCase) {
        $(byText(nameTestCase)).click();
        testCaseInput.shouldHave(text(nameTestCase)).shouldHave(visible);
        return this;
    }

    @Step("Проверяем, что step {step} добавлен в test case")
    public TestCasePagesModal verifyStepTestCase(String step) {
        $("[data-testid='section__scenario']").shouldHave(text(step)).shouldHave(visible);
        return this;
    }
}