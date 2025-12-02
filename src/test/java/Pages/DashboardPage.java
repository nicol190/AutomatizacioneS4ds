package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import Utils.UIActions;

public class DashboardPage {

    private WebDriver driver;
    private UIActions ui;

    private By menuOperations = By.id("dropdown_OPERATIONS_DASHBOARD_menu");
    private By menuNewOrder = By.id("pageId_ORDER_CONSULT_NEW_FRAMEWORK_menu");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.ui = new UIActions(driver);
    }

    public void openNewOrder() {

        // Scroll + click en OPERACIONES
        ui.clickWithScroll(menuOperations);

        // Scroll + click en NUEVA ORDEN
        ui.clickWithScroll(menuNewOrder);
    }
}
