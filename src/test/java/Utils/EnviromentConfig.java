package Utils;

public class EnviromentConfig {

    public static String getEnvironment() {
        return System.getenv("ENVIRONMENT");
    }

    public static String getUrl() {

        String env = getEnvironment(); // TEST / UAT / CORE

        switch (env.toUpperCase()) {
            case "UAT":
                return "https://demouat.s4ds.com/";

            case "TEST":
                return "https://demotest.s4ds.com/";

            case "CORE":
                return "https://demo.s4ds.com/";

            default:
                throw new RuntimeException("Ambiente inválido: " + env);
        }
    }

    public static String getUser() {
        return System.getenv("USERNAME");
    }

    public static String getPassword() {
        return System.getenv("PASSWORD");
    }
}
