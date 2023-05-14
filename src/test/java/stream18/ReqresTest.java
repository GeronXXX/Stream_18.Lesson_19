package stream18;

import models.*;
import org.junit.jupiter.api.Test;

import static data_faker.FakerTestDate.*;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.ReqresSpec.reqresRequestSpec;
import static specs.ReqresSpec.reqresResponseSpec;


public class  ReqresTest {
    @Test
    void checkSingleUserId() {
        SingleUserDataBody response = given(reqresRequestSpec)
                .when()
                .get("users/2")
                .then()
                .spec(reqresResponseSpec)
                .statusCode(200)
                .extract().as(SingleUserDataBody.class);

        assertEquals(2, response.getUser().getId());
        assertEquals("janet.weaver@reqres.in", response.getUser().getEmail());
    }

    @Test
    void checkLoginNotPassword() {
        LoginErrorBody body = new LoginErrorBody();
        body.setEmail(email);

        LoginErrorResponse response = given(reqresRequestSpec)
                .body(body)
                .when()
                .post("/login")
                .then()
                .spec(reqresResponseSpec)
                .statusCode(400)
                .extract().as(LoginErrorResponse.class);

        assertThat(response.getError()).isEqualTo("Missing password");
    }

    @Test
    void checkListResourceScheme() {

        given(reqresRequestSpec)
                .when()
                .get("/unknown")
                .then()
                .spec(reqresResponseSpec)
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/list_resource_schema"));
    }

    @Test
    void checkCreate() {

        CreateUserDataBody body = new CreateUserDataBody();
        body.setName(firstName);
        body.setJob(jobFaker);

        CreateUserDataResponse response = given(reqresRequestSpec)
                .body(body)
                .when()
                .post("/users")
                .then()
                .spec(reqresResponseSpec)
                .statusCode(201)
                .extract().as(CreateUserDataResponse.class);

        assertThat(response.getName()).isEqualTo(firstName);
        assertThat(response.getJob()).isEqualTo(jobFaker);
    }

    @Test
    void checkDelete() {

        given(reqresRequestSpec)
                .when()
                .delete("/users/2")
                .then()
                .spec(reqresResponseSpec)
                .statusCode(204);
    }

    @Test
    void checkListColor() {
        // @formatter:off
        given(reqresRequestSpec)
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
                        hasItems("15-4020", "17-2031", "19-1664", "14-4811", "17-1456", "15-5217"));
// @formatter:on
    }
}