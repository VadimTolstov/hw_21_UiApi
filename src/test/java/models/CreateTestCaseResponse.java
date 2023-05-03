package models;

import lombok.Data;

//{
//        "id": 17969,
//        "name": "Carroll Schneider III",
//        "automated": false,
//        "external": false,
//        "createdDate": 1683024906551,
//        "statusName": "Draft",
//        "statusColor": "#abb8c3"
//        }
@Data
public class CreateTestCaseResponse {
    private Integer id;
    private String name,
            statusName,
            statusColor;
    private Long createdDate;
    private Boolean automated,
            external;
}
