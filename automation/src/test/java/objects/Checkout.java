package objects;

import general.MainCall;
import org.openqa.selenium.By;

public class Checkout {

   public Checkout() {}

    public static By firstNameField = By.xpath("//input[@id='first-name']");
    public static By lastNameField = By.xpath("//input[@id='last-name']");
    public static By postalCodeField = By.xpath("//input[@id='postal-code']");
    public static By addToCartButton = By.xpath("//button[@id='add-to-cart-sauce-labs-backpack']");
    public static By continueButton = By.xpath("//input[@id='continue']");
    public static By cartIcon = By.xpath("//span[@class='shopping_cart_badge']");
    public static By checkoutButton = By.xpath("//button[@id='checkout']");
    public static By finishButton = By.xpath("//button[@id='finish']");
    public static By verifyCheckoutComplete = By.xpath("//h2[@data-test='complete-header']");




    public  static void enterFirstName() {
        MainCall.seleniumFunctions.IhaveGivenInput(firstNameField, "Brian");
    }

    public static void enterLastName() {
        MainCall.seleniumFunctions.IhaveGivenInput(lastNameField, "Karina");
    }
    public static void enterPostalCode() {
        MainCall.seleniumFunctions.IhaveGivenInput(postalCodeField,"00100");
    }

    public static void  clickOnAddToCart () {
        MainCall.seleniumFunctions.ClickingOn(addToCartButton);
    }

    public static void  clickOnContinue () {
        MainCall.seleniumFunctions.ClickingOn(continueButton);
    }

    public static void  clickOnCartIcon () {
        MainCall.seleniumFunctions.ClickingOn(cartIcon);
    }

    public static void  clickOnCheckout () {
        MainCall.seleniumFunctions.ClickingOn(checkoutButton);
    }

    public static void  clickOnFinish () {
        MainCall.seleniumFunctions.ClickingOn(finishButton);
    }

    public static void verifyCheckoutComplete () {
        MainCall.seleniumFunctions.isExist(verifyCheckoutComplete);
    }
}
