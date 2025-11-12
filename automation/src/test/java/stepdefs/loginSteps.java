package stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import general.MainCall;


import static config.ConfigProperties.*;

public class loginSteps {
    @And("User arrives to Login Page")
    public void userArrivesToLoginPage() {
        MainCall.loginPage.arriveToLogin();
    }

    @Given("^I login into Application$")
    public void login() {
        MainCall.loginPage.arriveToLogin();
        MainCall.loginPage.clickLogin();
        MainCall.loginPage.enterUserName(username);
        MainCall.loginPage.enterPassword(password);
        MainCall.loginPage.clickLogin();
    }

    @And("I click on the login button")
    public void iClickOnTheLoginButton() {
        MainCall.loginPage.clickLogin();
    }
    @Given("^I am Testing Case : \"([^\"]*)\"$")
    public void iAmTestingCase(String id) throws Throwable {
        MainCall.caseID = id;
    }

    @And("I enter a valid username")
    public void iHaveEnterValidUsernameEmail() {
        MainCall.loginPage.enterUserName(username);
    }


    @And("I enter a valid password")
    public void iEnterAValidPassword() {
        MainCall.loginPage.enterPassword(password);
    }


    @Then("I verify login")
    public void iVerifyLogin() throws InterruptedException {
        MainCall.loginPage.verifyLogin();
        Thread.sleep(2000);
    }

    @And("I enter an invalid password")
    public void iHaveEnterInvalidPassword() {
        MainCall.loginPage.enterPassword("test");

    }

    @Then("I verify the Error message")
    public void iVerifyTheErrorMessage() {
        MainCall.loginPage.verifyErrorMsg();
    }

    @And("user clicks on the menu button")
    public void userClicksOnTheMenuButon() { MainCall.loginPage.clickOnMenuButton();}

    @And("user clicks on the logout button")
    public void userClicksOnTheLogoutButton() {
        MainCall.loginPage.clickOnLogoutBtn();
    }

    @Then("user is logged out")
    public void userIsLoggedOut() {
        MainCall.loginPage.verifyUserIsLoggedOut();
    }

}