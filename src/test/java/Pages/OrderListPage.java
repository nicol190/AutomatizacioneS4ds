package Pages;

import Utils.UIActions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderListPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private UIActions ui;

    public OrderListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.ui = new UIActions(driver);
    }

    // Botón principal de nueva orden
    private By btnNewOrder = By.xpath("//button[contains(.,'New order')]");

    // Modal de borradores → botón "Create New"
    private By btnConfirmDraft = By.id("optionCreate");

    /**
     * Hace click en New Order, haciendo scroll si es necesario,
     * y si hay pedidos en borrador confirma la creación.
     */
    
    public void clickNewOrder() {

        // ocultar chatbot
        ui.hideChatbotIfVisible();

        // intentar scroll y click
        ui.clickUntilVisible(btnNewOrder);

        // manejar modal de borradores
        try {
            WebElement modalBtn = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(btnConfirmDraft));
            modalBtn.click();
            System.out.println("Confirmado el cliente tiene otros pedidos en borrador.");
        } catch (TimeoutException e) {
            System.out.println("No hay pedidos en borrador.");
        }
    }

}
