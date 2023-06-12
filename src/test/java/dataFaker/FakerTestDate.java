package dataFaker;

import com.github.javafaker.Faker;

public class FakerTestDate {
    public Faker faker = new Faker();
    public String firstName = faker.name().firstName();
    public String jobFaker = faker.job().title();
    public String email = faker.internet().emailAddress();

}

