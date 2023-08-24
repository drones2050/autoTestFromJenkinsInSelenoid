package core.config;

import lombok.extern.log4j.Log4j2;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Log4j2
public class Configure {
    private static final String APP_PROPERTIES = "config\\app.properties";
    private static Properties properties;

    static {
        properties = new Properties();
        try (InputStream is = Configure.class.getClassLoader().getResourceAsStream(APP_PROPERTIES)){
            properties.load(is);
        } catch(Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            log.error("Error parsing file configuration: {}", sw);
            throw new RuntimeException();
        }

    }

    /**
     * Получить значение параметра
     * @param name - название параметра
     * @return значение параметра
     */
    public static String getProperty(String name) {
        return properties.getProperty(name);
    }

    /**
     * Получить значение параметра, при отсутствии - значение по умолчанию
     * @param name - название параметра
     * @param defaultValue - название параметра
     * @return значение параметра
     */
    public static String getProperty(String name, String defaultValue) {
        return properties.getProperty(name, defaultValue);
    }

    /**
     * Добавить параметр со значением
     * @param name - название параметра
     * @param value - значение параметра
     */
    public void setProperty(String name, String value) {
        properties.setProperty(name, value);
    }

    /**
     * Получить набора парметров с назавние начинающимя с name
     * @param name - название парамтра
     * @return список парметров ключ->значение
     */
    public static Map<String, String> getPropertyStartWidth(String name) {
        return properties.stringPropertyNames().stream()
                .filter(p -> p.startsWith(name))
                .collect(Collectors.toMap(p -> p, Configure::getProperty));
    }


}
