package api.pages;

import api.models.*;
import io.qameta.allure.Step;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiVerify {

    @Step("Api verify  changing in response")
    public void verifyChangingNameTestCase(CreateTestCaseResponse testCaseResponse, CreateTestCaseBody body) {
        assertThat(testCaseResponse.getName()).isEqualTo(body.getName());
    }

    @Step("Api verify  description in response")
    public void verifyDescriptionTestCase(TestCaseDataResponseDto testCaseDataResponseDto, DescriptionTestCaseDto descriptionTestCaseDto) {
        assertThat(testCaseDataResponseDto.getDescription()).isEqualTo(descriptionTestCaseDto.getDescription());
    }

    @Step("Api verify  tag {tagName} in response ")
    public ApiVerify verifyAddendumTagTestCase(String tagName, TestCaseTagRequest tag) {
        assertThat(tagName).as("Ошибка с именем tag").isEqualTo(tag.getName());
        return this;
    }

    @Step("Api verify  step {step} in response ")
    public void verifyStepTagTestCase(String step1, String step2, String step3, TestCaseScenarioDto responseTestCaseScenario) {
        assertEquals(step1, responseTestCaseScenario.getSteps().get(0).getName());
        assertEquals(step2, responseTestCaseScenario.getSteps().get(1).getName());
        assertEquals(step3, responseTestCaseScenario.getSteps().get(2).getName());
    }
}
