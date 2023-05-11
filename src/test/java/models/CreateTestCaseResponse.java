package models;

import lombok.Data;

@Data
public class CreateTestCaseResponse {

    private Integer id;
    private String name, statusName, statusColor;
    private Long createdDate;
    private Boolean automated, external;

}
