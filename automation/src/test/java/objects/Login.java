package objects;

import com.mysql.cj.exceptions.AssertionFailedException;
import general.MainCall;
import org.openqa.selenium.By;

public class Login {

    public Login() {}

    public static By login_btn = By.xpath("//input[@id='login-button']");
    public static By login_UserName = By.xpath("//input[@id='user-name']");
    public static By login_password = By.xpath("//input[@id='password']");
    public static By loginVerification = By.xpath("//span[@class='title']");
    public static By errorMsg = By.xpath("//h3[@data-test='error']");
    public static By logoutButton = By.xpath("//a[@id='logout_sidebar_link']");
    public static By menuButton = By.xpath("//button[@id='react-burger-menu-btn']");
    public static By logoutVerification = By.xpath("//div[@class='login_wrapper-inner']");




    public static void arriveToLogin()
    {
        try {
            MainCall.webDriverFactory.getInstance();
        } catch (Exception exception) {
            throw new AssertionFailedException(exception.getMessage());
        }
    }
    public static void verifyUserIsLoggedOut () {
        MainCall.seleniumFunctions.isExist(logoutVerification);
    }
    public static void clickOnLogoutBtn () {
        MainCall.seleniumFunctions.ClickingOn(logoutButton);
    }

    public void clickOnMenuButton() {
        MainCall.seleniumFunctions.ClickingOn(menuButton);
    }

    public static void enterUserName(String userName)
    {
        MainCall.seleniumFunctions.IhaveGivenInput(login_UserName, userName);

    }
    public static void enterPassword(String password) {
        MainCall.seleniumFunctions.IhaveGivenInput(login_password, password);
    }

    public static void clickLogin() {
        MainCall.seleniumFunctions.PressButton(login_btn);
    }

    public static void verifyLogin() {
        MainCall.seleniumFunctions.isExist(loginVerification);
    }
    public static void verifyErrorMsg() {
        MainCall.seleniumFunctions.isExist(errorMsg);
    }

}