package models.data;

import com.github.javafaker.Faker;

public class ApiData {

    Faker faker = new Faker();

    private String testCaseName = faker.name().fullName(),
            stepTestCaseOne = faker.name().fullName(),
            stepTestCaseTwo = faker.name().firstName();


    public String getTestCaseName() {
        return testCaseName;
    }

    public String getStepTestCaseOne() {
        return stepTestCaseOne;
    }

    public String getStepTestCaseTwo() {
        return stepTestCaseTwo;
    }

}
