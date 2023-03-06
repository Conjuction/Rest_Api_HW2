import model.RegisterBodyModel;
import model.RegisterResponseModel;
import model.UserBodyModel;
import model.UserResponseModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;

public class ReqresTests {
    public static final String BASE_URL = "https://reqres.in/api";

    @Test
    void checkIdTest() {

        given()
                .log().uri()
                .when()
                .get(BASE_URL + "/unknown")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", hasItems(1, 2, 3, 4, 5, 6));
    }

    @Test
    void checkIdNameTest() {

        given()
                .log().uri()
                .when()
                .get(BASE_URL + "/unknown/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.name", is("fuchsia rose"));
    }

    @Test
    void createUserTest() {
        UserBodyModel createUser = new UserBodyModel();
        createUser.setName("morpheus");
        createUser.setJob("leader");

        UserResponseModel createResponse = given()
                .log().uri()
                .contentType(JSON)
                .body(createUser)
                .when()
                .post(BASE_URL + "/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().as(UserResponseModel.class);
        assertThat(createResponse.getName()).isEqualTo("morpheus");
        assertThat(createResponse.getJob()).isEqualTo("leader");
    }

    @Test
    void updateUserTest() {
        UserBodyModel updateUser = new UserBodyModel();
        updateUser.setName("morpheus");
        updateUser.setJob("zion resident");

        UserResponseModel updateResponse = given()
                .log().uri()
                .contentType(JSON)
                .body(updateUser)
                .when()
                .put(BASE_URL + "/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(UserResponseModel.class);
        assertThat(updateResponse.getName()).isEqualTo("morpheus");
        assertThat(updateResponse.getJob()).isEqualTo("zion resident");
    }

    @Test
    void registerUserTest() {
        RegisterBodyModel registerBody = new RegisterBodyModel();
        registerBody.setEmail("eve.holt@reqres.in");
        registerBody.setPassword("pistol");

        RegisterResponseModel registerResponse = given()
                .log().uri()
                .contentType(JSON)
                .body(registerBody)
                .when()
                .post(BASE_URL + "/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(RegisterResponseModel.class);
        assertThat(registerResponse.getId()).isEqualTo(4);
        assertThat(registerResponse.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }
}
