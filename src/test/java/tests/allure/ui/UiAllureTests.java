package tests.allure.ui;

import com.codeborne.selenide.Selenide;
import helpers.Regress;
import helpers.Web;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Ui tests Allure")
@Epic("UI")
@Owner("толстов вадим")
public class UiAllureTests extends UiTestBase {

    @BeforeEach
    @Epic("WEB")
    @Owner("толстов вадим")
    @DisplayName("Создаем проект")
    public void createProject() {
        String newProject = dataGenerator.getRandomSentence(1);

        userAuthorization.authorizationUi();

        projectsPagesModal
                .verifyProjectsPages()
                .openModalCreateNewProject()
                .createProject(newProject);

        projectPages.verifyNewProject(newProject);
    }

    @AfterEach
    @Epic("WEB")
    @Owner("толстов вадим")
    @DisplayName("Удаляем проект")
    public void deleteProject() {
        projectPages.deleteProject();
    }

    @Test
    @Epic("WEB")
    @Owner("толстов вадим")
    @Regress
    @Web
    @DisplayName("Создаем test case в проекте")
    public void createTestCase() {
        String nameTestCase = dataGenerator.getRandomSentence(2);

        testCasePages
                .openTestCasesPages()
                .createTestCase(nameTestCase)
                .verifyTestCaseName(nameTestCase);
    }

    @Test
    @Epic("WEB")
    @Owner("толстов вадим")
    @Regress
    @Web
    @DisplayName("Добавляем шаги в test case")
    public void createStepTestCase() {
        String nameTestCase = dataGenerator.getRandomSentence(3);
        String step1 = dataGenerator.getRandomSentence(1);
        String step2 = dataGenerator.getRandomSentence(2);
        String step3 = dataGenerator.getRandomSentence(3);

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
    @Epic("WEB")
    @Owner("толстов вадим")
    @Regress
    @Web
    @DisplayName("Добавляем описание в test case")
    public void createDescriptionTestCase() {
        String nameTestCase = dataGenerator.getRandomSentence(2);
        String DescriptionTestCase = dataGenerator.getRandomSentence(4);

        testCasePages
                .openTestCasesPages()
                .createTestCase(nameTestCase)
                .openTestCase(nameTestCase)
                .createDescriptionTestCase(DescriptionTestCase)
                .saveDataTestCase()
                .verifyDescriptionTestCase(DescriptionTestCase);
    }

    @Test
    @Epic("WEB")
    @Owner("толстов вадим")
    @Regress
    @Web
    @DisplayName("Добавляем tag в test case")
    public void createTagTestCase() {
        String nameTestCase = dataGenerator.getRandomSentence(3);
        String tag1Name = dataGenerator.getRandomSentence(1);
        String tag2Name = dataGenerator.getRandomSentence(2);

        testCasePages
                .openTestCasesPages()
                .createTestCase(nameTestCase)
                .openTestCase(nameTestCase)
                .openTagTestCase();
        Selenide.sleep(6000);

    }
}
