package allure.api.models;

import lombok.Data;

@Data
public class CreateTestCaseResponse {

    private Long id;
    private String name, statusName, statusColor;
    private Long createdDate;
    private Boolean automated, external;

}
