package Pages;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utils.UIActions;

public class OrderCreationPage {

    private WebDriver driver;
    private UIActions ui;

    //private By btnCreateOrder = By.id("optionCreate");
    private By popupConfirm = By.xpath("//body[@id='kt_body']/div[4]/div/div[3]/button");
    private By btnAddProductToOrder = By.id("createOrderBO");

    private By popupAccept = By.cssSelector("button.swal2-confirm"); 

    private By searchBox = By.id("productReferencesTextField");
    private By btnAddFirstResult = By.id("btnActionSku_2");

    private By btnNextStep = By.id("goToNextStepBody");
    
    By successPopup = By.cssSelector("div.swal2-popup.swal2-icon-success");
    By successBtn   = By.cssSelector("div.swal2-popup.swal2-icon-success button.swal2-confirm");

    public OrderCreationPage(WebDriver driver) {
        this.driver = driver;
        this.ui = new UIActions(driver);
    }
    
    public void acceptDiscountWarning() {
        ui.clickWithScroll(popupAccept);
    }


    public void searchProduct(String text) {
        ui.type(searchBox, text);
    }

  
    public void addFirstProduct() {

        // 1. Clic en el SKU (ya lo tienes)
        ui.clickWithScroll(btnAddFirstResult);

        // 2. Clic en el botón "Add to Order" (createOrderBO)
        ui.clickUntilVisible(btnAddProductToOrder);
        ui.clickWithScroll(btnAddProductToOrder);

        // 3. Si sale popup clásico, lo acepta
        if (ui.isElementPresent(popupConfirm)) {
            ui.clickWithScroll(popupConfirm);
        }

        // 4. Si sale popup SUCCESS de SweetAlert2, lo acepta

        if (ui.isElementPresent(successPopup)) {
            ui.clickWithScroll(successBtn);
        }
    }

    
    public void goNextStep() {
        ui.clickWithScroll(btnNextStep);
    }
    
    
   
    
    public void acceptDiscountAlertIfPresent() throws TimeoutException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        By swalPopup = By.cssSelector("div.swal2-popup.swal2-show");
        By btnAccept = By.cssSelector("button.swal2-confirm");

        try {
            // 1. Esperar popup
            WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(swalPopup));

            // 2. Scroll hacia el popup
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", popup);
            Thread.sleep(400);

            // 3. Esperar y buscar el botón
            WebElement acceptBtn = wait.until(ExpectedConditions.elementToBeClickable(btnAccept));

            // 4. Scroll hacia el botón también
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", acceptBtn);
            Thread.sleep(300);

            // 5. Intento de click normal
            try {
                acceptBtn.click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(swalPopup));
                System.out.println("Alerta de descuento aceptada con click normal.");
                return;
            } catch (Exception e1) {
                System.out.println("Click normal falló → intentando JS");
            }

            // 6. Fallback JS click
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", acceptBtn);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(swalPopup));
            System.out.println("Alerta aceptada con JS click.");

        } catch (Exception ex) {
            throw new RuntimeException("Error al aceptar la alerta de descuento", ex);
        }
    }
    
}
