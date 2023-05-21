package data;

import com.github.javafaker.Faker;

public class TestCaseDataGenerator {

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

    public String getStepTestCaseThree() {
        return faker.name().name();
    }

    public String getTestDescription() {
        return faker.backToTheFuture().quote();
    }

    public String getComment() {
        return faker.address().fullAddress();
    }
}
