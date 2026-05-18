package petstore;

import com.google.gson.Gson;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Collections;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetStoreTest {

    static String uri = "https://petstore.swagger.io/v2";
    static String ct = "application/json";
    static long petId = 999999;
    static long orderId = 777777;
    static long userId = 888888;
    static String username = "hermione_granger";

    @Test
    @Order(1)
    public void postPet() {
        Pet pet = new Pet();
        pet.id = petId;
        pet.name = "Bichento";
        pet.status = "available";

        Pet.Category cat = new Pet.Category();
        cat.id = 1;
        cat.name = "Cats";
        pet.category = cat;

        pet.photoUrls = Collections.singletonList("https://example.com/bichento.jpg");

        Pet.Tag tag = new Pet.Tag();
        tag.id = 1;
        tag.name = "gato";
        pet.tags = Collections.singletonList(tag);

        Gson gson = new Gson();
        String jsonBody = gson.toJson(pet);

        given()
            .contentType(ct)
            .log().all()
            .body(jsonBody)
        .when()
            .post(uri + "/pet")
        .then()
            .log().all()
            .statusCode(200)
            .body("name", is("Bichento"))
            .body("status", is("available"));
    }

    @Test
    @Order(2)
    public void postUser() {
        User user = new User();
        user.id = userId;
        user.username = username;
        user.firstName = "Hermione";
        user.lastName = "Granger";
        user.email = "hermione@hogwarts.com";
        user.password = "leviosa123";
        user.phone = "999999999";
        user.userStatus = 1;

        Gson gson = new Gson();
        String jsonBody = gson.toJson(user);

        given()
            .contentType(ct)
            .log().all()
            .body(jsonBody)
        .when()
            .post(uri + "/user")
        .then()
            .log().all()
            .statusCode(200)
            .body("code", is(200))
            .body("type", is("unknown"))
            .body("message", is(String.valueOf(userId)));
    }

    @Test
    @Order(3)
    public void postOrder() {
        StoreOrder order = new StoreOrder();
        order.id = orderId;
        order.petId = petId;
        order.quantity = 1;
        order.shipDate = "2026-05-18T00:00:00.000Z";
        order.status = "placed";
        order.complete = false;

        Gson gson = new Gson();
        String jsonBody = gson.toJson(order);

        given()
            .contentType(ct)
            .log().all()
            .body(jsonBody)
        .when()
            .post(uri + "/store/order")
        .then()
            .log().all()
            .statusCode(200)
            .body("status", is("placed"))
            .body("petId", is((int) petId));
    }

    @Test
    @Order(4)
    public void getPet() {
        given()
            .contentType(ct)
            .log().all()
        .when()
            .get(uri + "/pet/" + petId)
        .then()
            .log().all()
            .statusCode(200)
            .body("id", is((int) petId))
            .body("name", is("Bichento"))
            .body("status", is("available"));
    }

    @Test
    @Order(5)
    public void getOrder() {
        given()
            .contentType(ct)
            .log().all()
        .when()
            .get(uri + "/store/order/" + orderId)
        .then()
            .log().all()
            .statusCode(200)
            .body("id", is((int) orderId))
            .body("petId", is((int) petId))
            .body("status", is("placed"));
    }

    @Test
    @Order(6)
    public void analiseTransacao() {
        System.out.println("\n===== ANÁLISE DA TRANSAÇÃO (4.6) =====");

        String petStatus = given()
            .contentType(ct)
        .when()
            .get(uri + "/pet/" + petId)
        .then()
            .statusCode(200)
            .extract().path("status");

        String orderStatus = given()
            .contentType(ct)
        .when()
            .get(uri + "/store/order/" + orderId)
        .then()
            .statusCode(200)
            .extract().path("status");

        boolean orderComplete = given()
            .contentType(ct)
        .when()
            .get(uri + "/store/order/" + orderId)
        .then()
            .statusCode(200)
            .extract().path("complete");

        System.out.println("Status do Pet: " + petStatus);
        System.out.println("Status da Ordem: " + orderStatus);
        System.out.println("Ordem completa? " + orderComplete);

        org.junit.jupiter.api.Assertions.assertAll("Análise da transação de venda",
            () -> org.junit.jupiter.api.Assertions.assertEquals("available", petStatus,
                "ESTRANHO: O pet ainda está 'available' mesmo depois de vendido. " +
                "Em um sistema real, o status deveria mudar para 'sold'."),
            () -> org.junit.jupiter.api.Assertions.assertEquals("placed", orderStatus,
                "Status da ordem está correto"),
            () -> org.junit.jupiter.api.Assertions.assertFalse(orderComplete,
                "INCOMPLETO: A ordem está 'complete=false'. A transação não foi finalizada.")
        );

        System.out.println("===== FIM DA ANÁLISE =====\n");
    }
}