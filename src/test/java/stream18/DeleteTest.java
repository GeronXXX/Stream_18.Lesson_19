package stream18;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.ReqresSpec.reqresRequestSpec;
import static specs.ReqresSpec.reqresResponseSpec;

public class DeleteTest {
    @DisplayName("Проверка DELETE запроса")
    @Test
    void checkDelete() {

        step("Проверка статус кода 204 на запрос delete", () ->
                given(reqresRequestSpec)
                        .filter(withCustomTemplates())
                        .when()
                        .delete("/users/2")
                        .then()
                        .spec(reqresResponseSpec)
                        .statusCode(204));
    }
}
