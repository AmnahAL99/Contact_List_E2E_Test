package base_urls;

import io.cucumber.java.Before;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static utilites.Authentication.generateToken;

public class ContactListBaseUrl {

   public static RequestSpecification spec;
    @Before
    public static void setUp(){
        spec = new RequestSpecBuilder()
                .setBaseUri("https://thinking-tester-contact-list.herokuapp.com")
                .setContentType(ContentType.JSON)
                .addHeader("Authorization","Bearer "+ generateToken())
                .build();
    }
}
