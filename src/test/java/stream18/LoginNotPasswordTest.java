package stream18;

import dataFaker.FakerTestDate;
import models.LoginErrorBody;
import models.LoginErrorResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static specs.ReqresSpec.reqresRequestSpec;
import static specs.ReqresSpec.reqresResponseSpec;

public class LoginNotPasswordTest {
    @DisplayName("Попытка залогиниться без пароля")
    @Test
    void checkLoginNotPassword() {
        FakerTestDate data = new FakerTestDate();
        LoginErrorBody body = new LoginErrorBody();
        step("Set email для post запроса", () -> body.setEmail(data.email));

        LoginErrorResponse response = step("Отправка post запроса", () ->
                given(reqresRequestSpec)
                        .filter(withCustomTemplates())
                        .body(body)
                        .when()
                        .post("/login")
                        .then()
                        .spec(reqresResponseSpec)
                        .statusCode(400)
                        .extract().as(LoginErrorResponse.class));

        step("Проверка ответа Missing password", () ->
                assertThat(response.getError()).isEqualTo("Missing password"));
    }
}
