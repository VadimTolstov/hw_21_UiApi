package models.data;

import com.github.javafaker.Faker;

public class TestCaseApiDataGenerator {

    static final Faker faker = new Faker();

    public String getTestCaseName() {
        return faker.name().fullName();
    }

    public String getStepTestCaseOne() {
        return faker.name().fullName();
    }

    public String getStepTestCaseTwo() {
        return faker.name().firstName();
    }

    public String getTestDescription() {
        return faker.backToTheFuture().quote();
    }
}
