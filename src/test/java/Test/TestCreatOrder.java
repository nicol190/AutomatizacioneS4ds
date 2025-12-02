package Test;

import java.util.concurrent.TimeoutException;

import org.junit.Test;

import Base.BaseTest;
import Pages.LoginPage;
import Pages.DashboardPage;
import Pages.OrderCreationPage;
import Pages.OrderSummaryPage;
import Utils.ReportGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import Pages.OrderListPage;

public class TestCreatOrder extends BaseTest {
	
	@Epic("Order Creation")
	@Feature("Order Process")
	@Story("Create order and capture order number")
	@Description("Creates an order, captures the order number and validates process")
	

	@Test
	public void testCrearPedido() throws InterruptedException, TimeoutException {

	    LoginPage login = new LoginPage(driver);
	    login.openLoginForm();
	    login.login(username, password);

	    DashboardPage dashboard = new DashboardPage(driver);
	    dashboard.openNewOrder();

	    // NUEVA PÁGINA: LISTA DE ÓRDENES
	    OrderListPage orderList = new OrderListPage(driver);
	    orderList.clickNewOrder();

	    // Crear la orden
	    OrderCreationPage order = new OrderCreationPage(driver);
	    
	    
	    order.acceptDiscountAlertIfPresent();

	    order.searchProduct("jeans");
	    order.addFirstProduct();
	    order.goNextStep();

	    OrderSummaryPage summary = new OrderSummaryPage(driver);
	    String productName = summary.getProductName();
	    String reference = summary.getProductReference();
	    String quantity = summary.getProductQuantity();
	    String price = summary.getProductPrice();
	    String total = summary.getOrderTotal();
	    summary.selectFirstShippingAddress();
	    summary.selectPaymentCash();
	    summary.confirmOrder();
	    String orderNumber = summary.captureOrderNumber();

	    System.out.println("Pedido creado exitosamente." + orderNumber);
	    
	    // Nuevas capturas
	 

	    // Generar reporte
	    ReportGenerator.generateOrderReport(orderNumber, productName, reference, quantity, price, total);
	}
	

}
