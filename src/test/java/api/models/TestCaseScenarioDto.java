package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestCaseScenarioDto {

    private List<Step> steps = new ArrayList<>();

    public TestCaseScenarioDto addStep(Step step) {
        steps.add(step);
        return this;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Step {
        private String name, keyword;

        public Step(String name, String keyword) {
            this.name = name;
            this.keyword = keyword;
        }
    }
}

//   --data-raw '{"steps":[{"name":"впвап","spacing":""},{"name":"вап","spacing":""},{"name":"вап","spacing":""}],"workPath":[2]}' \
