package tests.allure.api;

import api.pages.ApiVerify;
import data.DataGenerator;
import tests.TestBase;
import ui.pages.TestCasePagesModal;

public class ApiTestBase extends TestBase {
    DataGenerator dataGenerator = new DataGenerator();

    TestCasePagesModal testCasePages = new TestCasePagesModal();
    ApiVerify apiVerify = new ApiVerify();
}
