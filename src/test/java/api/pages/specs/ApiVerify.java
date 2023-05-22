package api.pages.specs;

import api.models.CreateTestCaseBody;
import api.models.CreateTestCaseResponse;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ApiVerify {
    public ApiVerify changingNameTestCase(CreateTestCaseResponse testCaseResponse, CreateTestCaseBody body){
        assertThat(testCaseResponse.getName()).isEqualTo(body.getName());
        return this;
    }
}
