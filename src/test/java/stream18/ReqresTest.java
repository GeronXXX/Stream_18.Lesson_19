package stream18;

import dataFaker.FakerTestDate;
import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.ReqresSpec.reqresRequestSpec;
import static specs.ReqresSpec.reqresResponseSpec;

public class  ReqresTest {
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

    @DisplayName("Проверка значений цветов используя groovy")
    @Test
    void checkListColor() {
        // @formatter:off
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
// @formatter:on
    }
}