package Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;

    private By loginIcon = By.xpath("//img[@alt='loginIcon']");
    private By usernameField = By.id("user_login");
    private By passwordField = By.id("user_pass");
    private By submitButton = By.id("loginform");


    private By errorMessage = By.id("login_error");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openLoginForm() {


            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));

            // Espera a que la página termine de cargar
            wait.until(driver1 -> ((JavascriptExecutor) driver1)
                    .executeScript("return document.readyState").equals("complete"));

            // Espera a que el elemento exista en el DOM
            wait.until(ExpectedConditions.presenceOfElementLocated(loginIcon));

            // Espera que sea visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(loginIcon));

            // Espera que sea clickeable
            wait.until(ExpectedConditions.elementToBeClickable(loginIcon)).click();
        
    }


    public void login(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(submitButton).submit();
    }

    //Metodo para obtener el mensaje de error 
    public String getLoginError() {
        try {
            return driver.findElement(errorMessage).getText();
        } catch (Exception e) {
            return null; 
        }
    }
}
