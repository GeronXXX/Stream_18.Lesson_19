package data_faker;

import com.github.javafaker.Faker;

public class FakerTestDate {
    public static Faker faker = new Faker();

    public static String firstName = faker.name().firstName();
    public static String jobFaker = faker.job().title();
    public static String email = faker.internet().emailAddress();


}

