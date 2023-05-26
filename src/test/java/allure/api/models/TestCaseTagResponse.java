package allure.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestCaseTagResponse {

    private Long id;
    private String name;
}

//[{"id":166,"name":"API"}]
