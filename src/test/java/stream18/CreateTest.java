package stream18;

import dataFaker.FakerTestDate;
import models.CreateUserDataBody;
import models.CreateUserDataResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static specs.ReqresSpec.reqresRequestSpec;
import static specs.ReqresSpec.reqresResponseSpec;

public class CreateTest {
    @DisplayName("Проверка POST запроса body = response")
    @Test
    void checkCreate() {
        FakerTestDate data = new FakerTestDate();
        CreateUserDataBody body = new CreateUserDataBody();
        step("Set firstName для post запроса", () -> body.setName(data.firstName));
        step("Set job для post запроса", () -> body.setJob(data.jobFaker));

        CreateUserDataResponse response = step("Отправка post запроса", () ->
                given(reqresRequestSpec)
                        .filter(withCustomTemplates())
                        .body(body)
                        .when()
                        .post("/users")
                        .then()
                        .spec(reqresResponseSpec)
                        .statusCode(201)
                        .extract().as(CreateUserDataResponse.class));

        step("Сверка firstName в body с firstName в response", () ->
                assertThat(response.getName()).isEqualTo(data.firstName));
        step("Сверка job в body с job в response", () ->
                assertThat(response.getJob()).isEqualTo(data.jobFaker));
    }
}
