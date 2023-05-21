package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestCaseTagRequest {

    private String name;
}

//[{"id":166,"name":"API"}]
