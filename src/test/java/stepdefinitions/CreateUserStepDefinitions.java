package stepdefinitions;

import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import pages.ContactListAddUerPage;
import pages.ContactListLoginPage;
import pojo.UserResponse;
import utilites.Driver;
import utilites.ObjecyMapperUtilites;

import static base_urls.ContactListBaseUrl.spec;
import static io.restassured.RestAssured.given;
import static utilites.Authentication.generateToken;

public class CreateUserStepDefinitions {
  public static   String firstName;
   public static String lastName;
 public static    String email;
   public static String password;

    ContactListLoginPage loginPage =new ContactListLoginPage();
ContactListAddUerPage AdduserPage = new ContactListAddUerPage();
    @Given("user goes to {string}")
    public void userGoesTo(String url) {
        Driver.getDriver().get(url);

    }

    @When("user clicks on sign up button")
    public void userClicksOnSignUpButton() {
    loginPage.sinup.click();

    }
    @And("User enters firstname, lastname, email, password")
    public void userEntersFirstnameLastnameEmailPassword() {

        Faker faker = new Faker();
         firstName = faker.name().firstName();
          lastName = faker.name().lastName();
         email = faker.internet().emailAddress();
         password ="password123";

    AdduserPage.firstNameField.sendKeys(firstName);
    AdduserPage.lastNameField.sendKeys(lastName);
    AdduserPage.emailField.sendKeys(email);
    AdduserPage.passwordField.sendKeys(password);
    }

    @And("user clicks on submit button")
    public void userClicksOnSubmitButton() {
        AdduserPage.submitField.click();
    }

    @And("user closes browser")
    public void userClosesBrowser() {
        Driver.tearDown();
    }

    @Then("verify the user via API request")
    public void verifyTheUserViaAPIRequest() {
        //set url
// set expData
        String paylode = "{\n" +
                "  \"_id\": \"608b2db1add2691791c04c89\",\n" +
                "  \"firstName\": \"Ali\",\n" +
                "  \"lastName\": \"Can\",\n" +
                "  \"email\": \"alican@yahoo.com\",\n" +
                "  \"__v\": 1\n" +
                "}";
        UserResponse expData = ObjecyMapperUtilites.conversJsonToJava(paylode, UserResponse.class);

   spec.pathParams("1","users","2","me");
  //set response and get response
        Response response = given(spec)
       .when()
     .get("{1}/{2}");

     response.prettyPrint();


        UserResponse actualData = ObjecyMapperUtilites.conversJsonToJava(response.asString(),UserResponse.class);

        Assert.assertEquals(expData.getFirstName(),actualData.getFirstName());
        Assert.assertEquals(expData.getLastName(),actualData.getLastName());
        Assert.assertEquals(expData.getEmail(),actualData.getEmail());
      //  Assert.assertEquals(expData.getV(),actualData.getV());
    }
}
