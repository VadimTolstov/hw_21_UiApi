package models.specs;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestCaseDataResponseDto {
    private String name,
            description;
    private Long projectId,
            id;
}
