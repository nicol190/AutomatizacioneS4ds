package Base;

import java.time.Duration;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import Utils.EnviromentConfig;

public class BaseTest {

    protected WebDriver driver;
    protected String username;
    protected String password;

    @Before
    public void setUp() {

        // ============================
        // 1. ChromeDriver local
        // ============================
        if (System.getProperty("CI") == null) {  
            System.setProperty("webdriver.chrome.driver",
                "src/test/java/ChromeDriver/chromedriver-win64/chromedriver.exe");
        }

        // ============================
        // 2. Chrome Options
        // ============================
        ChromeOptions options = new ChromeOptions();

        // HEADLESS solo cuando se pase -Dheadless=true
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        if (isHeadless) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--window-size=1920,1080"); // obligatorio en headless
        }

        driver = new ChromeDriver(options);

        // ============================
        // 3. Maximizar SOLO si NO es headless
        // ============================
        if (!isHeadless) {
            driver.manage().window().maximize();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // ============================
        // 4. Variables de entorno
        // ============================
        username = EnviromentConfig.getUser();
        password = EnviromentConfig.getPassword();

        if (username == null || password == null) {
            throw new RuntimeException("ERROR: USERNAME o PASSWORD no configurados.");
        }

        // ============================
        // 5. Seleccionar url del ambiente
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
            driver.quit();
        }
    }

}
