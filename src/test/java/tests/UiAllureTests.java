package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.pages.AuthorizationUi;
import ui.pages.ProjectPagesModal;
import ui.pages.ProjectsPagesModal;
import ui.pages.TestCasePagesModal;

@DisplayName("Ui tests Aller")
public class UiAllureTests extends TestBase {
    AuthorizationUi userAuthorization = new AuthorizationUi();
    ProjectsPagesModal projectsPagesModal = new ProjectsPagesModal();
    ProjectPagesModal projectPages = new ProjectPagesModal();
    TestCasePagesModal testCasePages = new TestCasePagesModal();

    @BeforeEach
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
    @DisplayName("Удаляем проект")
    public void deleteProject() {
        projectPages.deleteProject();
    }

    @Test
    @DisplayName("Создаем test case в проекте")
    public void createTestCase() {
        String nameTestCase = dataGenerator.getTestCaseName();

        testCasePages
                .openTestCasesPages()
                .createTestCase(nameTestCase)
                .verifyTestCaseName(nameTestCase);
    }

    @Test
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
    @DisplayName("Добавляем")
}
