package Pages;

import Utils.UIActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderStepCustomerPage {

    private WebDriver driver;
    private UIActions ui;

    public OrderStepCustomerPage(WebDriver driver) {
        this.driver = driver;
        this.ui = new UIActions(driver);
    }

    // Localizador del botón NEXT
    private By btnNextStep = By.id("goToNextStep");

    /**
     * Realiza scroll y hace clic en el botón NEXT del nuevo paso.
     */
    public void clickNextStep() {
        //ui.hideChatbotIfVisible();          // ocultar chatbot
        ui.clickUntilVisible(btnNextStep);  // scroll + click automático
        System.out.println("Botón NEXT del paso de cliente clickeado exitosamente.");
    }
}
