package Test;

import org.junit.Test;
import Base.BaseTest;
import Pages.LoginPage;
import org.openqa.selenium.By;
import static org.junit.Assert.*;

public class TestLogin extends BaseTest {

    @Test
    public void testLoginValidation() throws InterruptedException {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.openLoginForm();
        loginPage.login(username, password); 

        Thread.sleep(2000);

        // Validar si hay error
        String error = loginPage.getLoginError();

        if (error != null && !error.isEmpty()) {

            System.out.println("LOGIN FAILED DETECTADO");
            System.out.println("Mensaje devuelto por la página:");
            System.out.println(error);

            if (error.contains("Login failed")) {
                System.out.println("Credenciales incorrectas (usuario o contraseña).");
            } else {
                System.out.println("Error inesperado.");
            }

            fail("❌ Login falló: " + error);
        }

        // Si no hay error → validar login exitoso
        assertTrue(
            "❌ No se encontró el menú principal después del login, pero no se mostró error.",
            driver.findElement(By.id("dropdown_OPERATIONS_DASHBOARD_menu")).isDisplayed()
        );

        System.out.println("Login exitoso en el ambiente.");
    }
}
