package stream18;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static specs.ReqresSpec.reqresRequestSpec;
import static specs.ReqresSpec.reqresResponseSpec;

public class ListResourceSchemeTest {
    @DisplayName("Попытка ответа в соответствии со схемой body POJO")
    @Test
    void checkListResourceScheme() {

        step("Отправка get запроса и сверка со схемой ответа POJO", () ->
                given(reqresRequestSpec)
                        .filter(withCustomTemplates())
                        .when()
                        .get("/unknown")
                        .then()
                        .spec(reqresResponseSpec)
                        .statusCode(200)
                        .body(matchesJsonSchemaInClasspath("schemas/list_resource_schema")));
    }
}
