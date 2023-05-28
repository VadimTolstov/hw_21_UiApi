package tests;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.AuthorizationUi;
import pages.ProjectPagesModal;
import pages.ProjectsPagesModal;
import pages.TestCasePagesModal;

@DisplayName("Ui tests Allure")
@Epic("UI")
@Owner("толстов вадим")
public class UiAllureTests extends TestBase {
    AuthorizationUi userAuthorization = new AuthorizationUi();
    ProjectsPagesModal projectsPagesModal = new ProjectsPagesModal();
    ProjectPagesModal projectPages = new ProjectPagesModal();
    TestCasePagesModal testCasePages = new TestCasePagesModal();

    @BeforeEach
    @Epic("Ui")
    @Owner("толстов вадим")
    @DisplayName("Создаем проект")
    public void createProject() {
        String newProject = dataGenerator.getNewProject();

        userAuthorization.authorizationUi();

        projectsPagesModal
                .verifyProjectsPages()
                .openModalCreateNewProject()
                .createProject(newProject);

        projectPages.verifyNewProject(newProject);
    }

    @AfterEach
    @Epic("Ui")
    @Owner("толстов вадим")
    @DisplayName("Удаляем проект")
    public void deleteProject() {
        projectPages.deleteProject();
    }

    @Test
    @Epic("Ui")
    @Owner("толстов вадим")
    @DisplayName("Создаем test case в проекте")
    public void createTestCase() {
        String nameTestCase = dataGenerator.getTestCaseName();

        testCasePages
                .openTestCasesPages()
                .createTestCase(nameTestCase)
                .verifyTestCaseName(nameTestCase);
    }

    @Test
    @Epic("Ui")
    @Owner("толстов вадим")
    @DisplayName("Добавляем шаги в test case")
    public void createStepTestCase() {
        String nameTestCase = dataGenerator.getTestCaseName();
        String step1 = dataGenerator.getStepTestCaseOne();
        String step2 = dataGenerator.getStepTestCaseTwo();
        String step3 = dataGenerator.getStepTestCaseThree();

        testCasePages
                .openTestCasesPages()
                .createTestCase(nameTestCase)
                .openTestCase(nameTestCase)
                .openStepTestCase()
                .createStepTestCase(step1)
                .createStepTestCase(step2)
                .createStepTestCase(step3)
                .saveDataTestCase()
                .verifyStepTestCase(step1)
                .verifyStepTestCase(step2)
                .verifyStepTestCase(step3);
    }

    @Test
    @Epic("Ui")
    @Owner("толстов вадим")
    @DisplayName("Добавляем описание в test case")
    public void createDescriptionTestCase() {
        String nameTestCase = dataGenerator.getTestCaseName();
        String DescriptionTestCase = dataGenerator.getTestDescription();

        testCasePages
                .openTestCasesPages()
                .createTestCase(nameTestCase)
                .openTestCase(nameTestCase)
                .createDescriptionTestCase(DescriptionTestCase)
                .saveDataTestCase()
                .verifyDescriptionTestCase(DescriptionTestCase);
    }

    @Test
    @Epic("Ui")
    @Owner("толстов вадим")
    @DisplayName("Добавляем tag в test case")
    public void createTagTestCase() {
        String nameTestCase = dataGenerator.getTestCaseName();
        String tag1Name = dataGenerator.getStepTestCaseThree();
        String tag2Name = dataGenerator.getStepTestCaseTwo();

        testCasePages
                .openTestCasesPages()
                .createTestCase(nameTestCase)
                .openTestCase(nameTestCase)
                .openTagTestCase();
        Selenide.sleep(6000);

    }
}
