package stream18;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static specs.ReqresSpec.reqresRequestSpec;
import static specs.ReqresSpec.reqresResponseSpec;

public class CheckListColorTest {
    @DisplayName("Проверка значений цветов используя groovy")
    @Test
    void checkListColor() {
        step("Отправка запроса get и проверка в body значений цветов", () ->
                given(reqresRequestSpec)
                        .filter(withCustomTemplates())
                        .when()
                        .get("/unknown")
                        .then()
                        .spec(reqresResponseSpec)
                        .statusCode(200)
                        .body("data.color[3]",
                                equalTo("#7BC4C4"))
                        .and()
                        .body("data.findAll{it.name =~/./}.name.flatten()",
                                hasItem("aqua sky"))
                        .and()
                        .body("data.pantone_value.flatten()",
                                hasItems("15-4020", "17-2031", "19-1664", "14-4811", "17-1456", "15-5217")));
    }
}