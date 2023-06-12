package stream18;

import models.SingleUserDataBody;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.ReqresSpec.reqresRequestSpec;
import static specs.ReqresSpec.reqresResponseSpec;

public class СheckSingleUserIdTest {
    @DisplayName("Проверка данных пользователя id, email и статус код")
    @Test
    void checkSingleUserId() {
        SingleUserDataBody response = step("Отправка запроса на получение данных пользователя", () ->
                given(reqresRequestSpec)
                        .filter(withCustomTemplates())
                        .when()
                        .get("users/2")
                        .then()
                        .spec(reqresResponseSpec)
                        .statusCode(200)
                        .extract().as(SingleUserDataBody.class));

        step("Проверка id = 2", () ->
                assertEquals(2, response.getUser().getId()));
        step("Проверка email = janet.weaver@reqres.in", () ->
                assertEquals("janet.weaver@reqres.in", response.getUser().getEmail()));
    }
}
