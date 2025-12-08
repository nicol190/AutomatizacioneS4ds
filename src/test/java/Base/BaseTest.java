package Base;

import java.io.File;
import java.time.Duration;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.google.common.io.Files;

import Utils.EnviromentConfig;

public class BaseTest {

    protected WebDriver driver;
    protected String username;
    protected String password;

    @Before
    public void setUp() {

        // Detectar si estamos en CI (GitHub Actions)
        boolean isCI = System.getenv("CI") != null;

        ChromeOptions options = new ChromeOptions();

        // Modo headless si se pasa -Dheadless=true
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        if (isCI) {
            // ============================
            // MODO CI (GitHub Actions - Linux)
            // ============================
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--window-size=1920,1080");
        } else {
            // ============================
            // MODO LOCAL (Windows)
            // ============================
            System.setProperty("webdriver.chrome.driver",
                    "src/test/java/ChromeDriver/chromedriver-win64/chromedriver.exe");

            if (isHeadless) {
                options.addArguments("--headless=new");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");
            }
        }

        driver = new ChromeDriver(options);

        // Maximizar solo si no es headless
        if (!isHeadless) {
            driver.manage().window().maximize();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // ============================
        // Cargar variables de entorno
        // ============================
        username = EnviromentConfig.getUser();
        password = EnviromentConfig.getPassword();

        if (username == null || password == null) {
            throw new RuntimeException("ERROR: USERNAME o PASSWORD no configurados.");
        }

        // ============================
        // Seleccionar URL del ambiente
        // ============================
        String url = EnviromentConfig.getUrl();

        if (url == null) {
            throw new RuntimeException("ERROR: URL del ambiente no encontrada.");
        }

        driver.get(url);
    }

  
    @After
    public void tearDown() {
        if (driver != null) {
            try {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

                // Crear carpeta "reports" por si no existe
                new File("reports").mkdirs();

                // Guardar screenshot
                File destino = new File("reports/ERROR_" + System.currentTimeMillis() + ".png");
                org.openqa.selenium.io.FileHandler.copy(screenshot, destino);

                System.out.println("✔ Screenshot guardado en: " + destino.getAbsolutePath());
            } catch (Exception e) {
                System.out.println("⚠ No se pudo guardar screenshot: " + e.getMessage());
            }

            driver.quit();
        }
    }

    
}
