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

	    // ChromeDriver path SOLO se usa localmente
		if (System.getProperty("CI") == null) {
		    System.setProperty("webdriver.chrome.driver",
		        "src/test/java/ChromeDriver/chromedriver-win64/chromedriver.exe");
		}


	    // ============================
	    // HEADLESS CONFIG (FUNCIONA EN CI/CD)
	    // ============================
	    ChromeOptions options = new ChromeOptions();
	    options.addArguments("--headless=new");
	    options.addArguments("--no-sandbox");
	    options.addArguments("--disable-dev-shm-usage");
	    options.addArguments("--disable-gpu");
	    options.addArguments("--remote-allow-origins=*");
	    options.addArguments("--window-size=1920,1080");

	    driver = new ChromeDriver(options);

	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	    username = EnviromentConfig.getUser();
	    password = EnviromentConfig.getPassword();

	    if (username == null || password == null) {
	        throw new RuntimeException("ERROR: USERNAME o PASSWORD no están configurados en variables de entorno.");
	    }

	    String url = EnviromentConfig.getUrl();
	    if (url == null) {
	        throw new RuntimeException("ERROR: No se pudo obtener la URL del ambiente desde EnvironmentConfig.");
	    }

	    driver.get(url);
	}
	
	@After
	public void tearDown() {
	    if (driver != null) {
	        driver.quit();
	    }
	}

	
	
	

  //  protected WebDriver driver;
  // protected String username;
   // protected String password;

   // @Before
   // public void setUp() {

       // System.setProperty("webdriver.chrome.driver",
      //          "src/test/java/ChromeDriver/chromedriver-win64/chromedriver.exe");

      //  driver = new ChromeDriver();
      //  driver.manage().window().maximize();
       // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // =========================
        // 1. LEER VARIABLES DE ENTORNO
        // =========================
       // username = EnviromentConfig.getUser();
       // password = EnviromentConfig.getPassword();

       // if (username == null || password == null) {
       //     throw new RuntimeException("ERROR: USERNAME o PASSWORD no están configurados en variables de entorno.");
      //  }

        // =========================
        // 2. SELECCIONAR AMBIENTE
        // =========================

      //  String url = EnviromentConfig.getUrl();

      //  if (url == null) {
      //      throw new RuntimeException("ERROR: No se pudo obtener la URL del ambiente desde EnvironmentConfig.");
     //  }
////
     //   // Abrir navegador en el ambiente correspondiente
      //  driver.get(url);
  //  }

   // @After
    //public void tearDown() {
       // if (driver != null) {
         //   driver.quit();
       // }
    //}
	
	
	
}












