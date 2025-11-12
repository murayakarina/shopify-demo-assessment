package stepdefs;

import general.MainCall;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class checkoutSteps {
    @And("I add a product to the cart")
    public void iAddAProductToTheCart() {
        MainCall.checkoutFlow.clickOnAddToCart();
    }

    @And("I click on the cart")
    public void iClickOnTheCart() {
        MainCall.checkoutFlow.clickOnCartIcon();
    }

    @And("I click on checkout")
    public void iClickOnCheckout() {
        MainCall.checkoutFlow.clickOnCheckout();
    }

    @And("I enter the First Name")
    public void iEnterTheFirstName() {
        MainCall.checkoutFlow.enterFirstName();
    }


    @And("I enter the Last Name")
    public void iEnterTheLastName() {
        MainCall.checkoutFlow.enterLastName();
    }

    @And("I click on continue")
    public void iClickOnContinue() {
        MainCall.checkoutFlow.clickOnContinue();
    }

    @And("I enter a Postal Code")
    public void iEnterAPostalCode() {
        MainCall.checkoutFlow.enterPostalCode();
    }

    @Then("I click on Finish")
    public void iClickOnFinsih() {
        MainCall.checkoutFlow.clickOnFinish();
    }

    @And("I get a thank you message")
    public void iGetAThankYouMessage() {
        MainCall.checkoutFlow.verifyCheckoutComplete();
    }
}
