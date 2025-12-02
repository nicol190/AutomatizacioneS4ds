package Utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UIActions {

    private WebDriver driver;
    private WebDriverWait wait;

    public UIActions(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    // =====================================
    // SCROLL NORMAL
    // =====================================
    public void scrollTo(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                element
        );
    }
    
    
    public boolean isElementPresent(By locator) {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
            boolean exists = !driver.findElements(locator).isEmpty();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            return exists;
        } catch (Exception e) {
            return false;
        }
    }
    
    

    // =====================================
    // SCROLL REPETITIVO (para elementos MUY abajo)
    // =====================================
    public WebElement scrollUntilVisible(By locator, int maxScrolls) {
        int attempts = 0;

        while (attempts < maxScrolls) {

            try {
                WebElement element = driver.findElement(locator);

                if (element.isDisplayed()) {
                    return element;
                }

            } catch (Exception ignored) {}

            // Scroll hacia abajo
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 600);");

            attempts++;

            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }

        throw new NoSuchElementException(
                "No se encontró el elemento después de " + maxScrolls + " scrolls: " + locator
        );
    }

    // =====================================
    // CLICK SEGURO + SCROLL NORMAL
    // =====================================
   
    public void clickWithScroll(By locator) {
        scrollTo(locator);

        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click(); // Intento normal
        } catch (Exception e) {
            System.out.println(" \"El clic tradicional no fue posible (elemento cubierto o no interactuable). \" +\r\n"
            		+ "    \"Se aplicó clic por JavaScript correctamente.\"Click en: " + locator.toString());
            WebElement element = driver.findElement(locator);

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }


    // =====================================
    // CLICK CON SCROLL REPETITIVO
    // =====================================
   
    public void clickUntilVisible(By locator) {
        WebElement element = scrollUntilVisible(locator, 7);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (Exception e) {
            System.out.println("ClickUntilVisible falló → usando JS");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }


    // =====================================
    // TYPE / sendKeys
    // =====================================
    public void type(By locator, String text) {
        scrollTo(locator);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }
    
    public void hideChatbotIfVisible() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                "var c = document.getElementById('open-assistant'); " +
                "if (c) { c.style.display='none'; }"
            );
            System.out.println("Chatbot ocultado.");
        } catch (Exception e) {
            System.out.println("No se pudo ocultar el chatbot (quizá no existe).");
        }
    }

}
