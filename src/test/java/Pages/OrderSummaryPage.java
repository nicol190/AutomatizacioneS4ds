package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class OrderSummaryPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public OrderSummaryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Primary locators (used first)
    private By shippingAddress = By.xpath("//div[@id='shippingAddress-list']/div//label/span");
    private By paymentCashRadio = By.id("payWithCash"); // botón final que confirmaba cash
    private By paymentCashOption = By.xpath("//div[@id='payment-CASH']//label"); // alternativa
   // private By btnConfirmOrder = By.xpath("//form[@id='Pre-invoice_form']//button[contains(.,'Create') or contains(.,'Crear') or contains(@class,'swal2-confirm')]");
    private By btnConfirmOrderAlt = By.xpath("//form[@id='Pre-invoice_form']/div[7]//button");
    private By btnContinueCash = By.id("payWithCash");
    private By orderNumberLocator = By.xpath("//h6[contains(.,'Order Number')]//strong");
    private By btnGoToProfile = By.xpath("//button[contains(.,'Go to my profile')]");
    private By productName = By.cssSelector("p.product_name");
    private By productReference = By.cssSelector(".product_reference .reference_value");
    private By productQuantity = By.cssSelector("p.product_quantity");
    private By productPrice = By.id("discount-price");
    private By totalOrder = By.id("orderTotalOrderSpan");


    /**
     * Selecciona la primera dirección disponible (intenta hacer scroll y esperar elemento).
     */
    public void selectFirstShippingAddress() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(shippingAddress));
        List<WebElement> addresses = driver.findElements(shippingAddress);
        if (addresses.isEmpty()) {
            throw new RuntimeException("No shipping addresses were found on the summary page.");
        }
        WebElement first = addresses.get(0);
        wait.until(ExpectedConditions.elementToBeClickable(first)).click();
    }

    /**
     * Selecciona el método de pago en efectivo (Cash).
     * Intenta varias estrategias para cubrir distintos DOMs.
     */
    public void selectPaymentCash() {
        try {
            // Si existe un radio o input directo
            wait.until(ExpectedConditions.presenceOfElementLocated(paymentCashRadio));
            WebElement pay = driver.findElement(paymentCashRadio);
            if (!pay.isDisplayed()) throw new Exception("paymentCashRadio not visible");
            wait.until(ExpectedConditions.elementToBeClickable(pay)).click();
            return;
        } catch (Exception ignored) { /* intenta alternativa */ }

        // Alternativa: click en el contenedor de la opción CASH
        try {
            wait.until(ExpectedConditions.elementToBeClickable(paymentCashOption)).click();
            return;
        } catch (Exception ignored) { /* siguiente alternativa */ }

        throw new RuntimeException("Could not select Cash payment option (neither direct radio nor option found).");
    }

    /**
     * Confirma/crea el pedido desde el resumen.
     */
    public void confirmOrder() {
        // intenta el locator principal
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(btnContinueCash));
            btn.click();
            return;
        } catch (Exception ignored) { }

        // alternativa
        try {
            WebElement btnAlt = wait.until(ExpectedConditions.elementToBeClickable(btnConfirmOrderAlt));
            btnAlt.click();
            return;
        } catch (Exception e) {
            throw new RuntimeException("Confirm order button not found or not clickable.", e);
        }
    }

    /**
     * Método de ayuda: espera hasta que un mensaje de confirmación (toast/modal) aparezca.
     * Ajusta el selector según el comportamiento de la app (swal2, toast, alert...).
     */
    public String waitForConfirmationMessage() {
        // Ejemplos de selectores comunes
        By swalSelector = By.cssSelector(".swal2-container .swal2-html-container");
        By toastSelector = By.cssSelector(".toast-message, .toast-body, .alert-success");

        try {
            WebElement swal = wait.until(ExpectedConditions.visibilityOfElementLocated(swalSelector));
            return swal.getText();
        } catch (Exception ignored) { }

        try {
            WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(toastSelector));
            return toast.getText();
        } catch (Exception ignored) { }

        return null;
    }
    
    
    public String captureOrderNumber() {
        // Esperar el número
        WebElement numberElement = wait.until(ExpectedConditions.visibilityOfElementLocated(orderNumberLocator));

        // Scroll al número
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", numberElement);

        // Capturar el texto
        String orderNumber = numberElement.getText().trim();

        System.out.println("===== ORDER NUMBER GENERATED: " + orderNumber + " =====");

        return orderNumber;
    }
    
    public void clickGoToProfile() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(btnGoToProfile));

        // Scroll al botón antes de clic
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", btn);

        btn.click();
    }
    
    public String getProductName() {
        WebElement name = wait.until(ExpectedConditions.visibilityOfElementLocated(productName));
        return name.getText().trim();
    }

    public String getProductReference() {
        WebElement ref = wait.until(ExpectedConditions.visibilityOfElementLocated(productReference));
        return ref.getText().trim();
    }

    public String getProductQuantity() {
        WebElement qty = wait.until(ExpectedConditions.visibilityOfElementLocated(productQuantity));
        String raw = qty.getText();
        return raw.replace("Amount:", "").trim();
    }

    public String getProductPrice() {
        WebElement price = wait.until(ExpectedConditions.visibilityOfElementLocated(productPrice));
        return price.getText().trim();
    }

    public String getOrderTotal() {
        WebElement total = wait.until(ExpectedConditions.visibilityOfElementLocated(totalOrder));
        return total.getText().trim();
    }

    
    
}
