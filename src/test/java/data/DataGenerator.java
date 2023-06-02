package data;

import com.github.javafaker.Faker;

public class DataGenerator {

    static final Faker faker = new Faker();

    public String getRandomSentence(int length) {
        return faker.lorem().sentence(length);
    }
}
