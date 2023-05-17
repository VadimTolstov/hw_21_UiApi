package models.specs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestCaseDataResponseDto {
    private String name,
            description;
    private Long projectId,
            id;
}
