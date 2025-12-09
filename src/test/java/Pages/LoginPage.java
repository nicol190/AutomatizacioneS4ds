package Pages;

import java.io.File;
import java.io.FileWriter;
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
    private By loginButton = By.id("btn_goToLogin");

    private By errorMessage = By.id("login_error");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

   
    public void openLoginForm() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));

        try {
            System.out.println("URL actual: " + driver.getCurrentUrl());
            System.out.println("Título de la página: " + driver.getTitle());

            int iframes = driver.findElements(By.tagName("iframe")).size();
            System.out.println("Iframes encontrados: " + iframes);

            System.out.println("Esperando que la página cargue...");
            wait.until(driver1 -> ((JavascriptExecutor) driver1)
                    .executeScript("return document.readyState").equals("complete"));

            System.out.println("Buscando icono login con XPath alt='loginIcon'...");
            wait.until(ExpectedConditions.presenceOfElementLocated(loginIcon));

            System.out.println("Visible...");
            wait.until(ExpectedConditions.visibilityOfElementLocated(loginIcon));

            System.out.println("Clickeable...");
            wait.until(ExpectedConditions.elementToBeClickable(loginIcon)).click();

            System.out.println("Click ejecutado correctamente");

        } catch (Exception e) {
            System.out.println("No se encontró el elemento loginIcon o falló el click");
            System.out.println("Error: " + e.getMessage());
            savePageSource(driver, "pagina_error");
            throw e;
        }
    }

    
    public static void savePageSource(WebDriver driver, String nombre) {
        try {
            String html = driver.getPageSource();
            File file = new File("reports/" + nombre + ".html");
            FileWriter fw = new FileWriter(file);
            fw.write(html);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
