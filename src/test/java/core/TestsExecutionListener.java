package core;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.config.Configure;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class TestsExecutionListener implements TestExecutionListener {
    private static final String URL = Configure.getProperty("base.url");
    private static final String PARAM_WEB_DRIVER = "webdriver.chrome.driver";

    @SneakyThrows
    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
    }

    /**
     * Конфигурация UI
     */
    @SneakyThrows
    public static void initDriver() {
        // конфигурация теста
        testConfiguration();

        // проверяем параметр запуска: true - удаленно, false - локально
        if (Boolean.parseBoolean(Configure.getProperty("webdriver.is.remote", "true"))) {
            RemoteWebDriver driver = new RemoteWebDriver(new java.net.URL(Configuration.remote), Configuration.browserCapabilities);
            driver.setFileDetector(new LocalFileDetector());
            WebDriverRunner.setWebDriver(driver);
        } else {
            if (Configure.getProperty("webdriver.path") != null) {
                System.setProperty(PARAM_WEB_DRIVER, new File(Configure.getProperty("webdriver.path")).getAbsolutePath());
            }
        }
    }

    /**
     * Конфигурация теста
     * @throws JsonProcessingException
     */
    private static void testConfiguration() throws JsonProcessingException {
        Configuration.baseUrl = URL;
        isRemote();
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 45000;
        Configuration.driverManagerEnabled = false;
        Configuration.browser = "chrome";
        Configuration.startMaximized = true;
        Configuration.pageLoadTimeout = 80000;
        ChromeOptions options = getChromeOptions();

        synchronized (TestsExecutionListener.class) {
            Configuration.browserCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
        }
    }

    /**
     * Подготовка опций для запуска браузера
     * @return опции браузера
     * @throws JsonProcessingException
     */
    @NotNull
    private static ChromeOptions getChromeOptions() throws JsonProcessingException {
        Map<String, Object> prefs = new HashMap<>();
        //разрешаем копирование из буфера
        prefs.put("profile.content_settings.exceptions.clipboard", getClipBoardSettingsMap());

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-external-pages");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-site-isolation-trials");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-browser-side-navigation");
        options.addArguments("--start-maximized");
        // options.addArguments("-–incognito");
        // options.addArguments("user-data-dir=C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default");

        options.setExperimentalOption("prefs", prefs);
        return options;
    }

    /**
     * Подготовка парамтеров, разрешающих копирование из буфера
     * @return список ключ->значение
     * @throws JsonProcessingException
     */
    private static Map<String, Object> getClipBoardSettingsMap() throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        map.put("last_modified", String.valueOf(System.currentTimeMillis()));
        map.put("setting", 1);
        Map<String, Object> cbPreference = new HashMap<>();
        cbPreference.put("[*.],*", map);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValueAsString(cbPreference);
        return cbPreference;
    }
/*
    public void loadSecretJson() {
        String secret = System.getProperty("secret");
        if (secret == null)
            secret = Configure.getProperty("secret");
        String file = Configure.getProperty("data.folder") + "/shareFolder/" + "secret.bin";
//        if (!Files.exists(Paths.get(file)) || secret == null)
//            return;
//        ObjectPoolService.loadEntities(Encrypt.Aes256Decode(Base64.getDecoder().decode(DataFileHelper.read(file)), secret));
    }*/

    public static void isRemote() {
        if (Boolean.parseBoolean(Configure.getProperty("webdriver.is.remote", "true"))) {
            Assertions.assertNotNull(Configure.getProperty("webdriver.remote.url"), "Не указан webdriver.remote.url");
            log.info("Ui Тесты стартовали на selenoid сервере");
            Configuration.remote = Configure.getProperty("webdriver.remote.url");
            Map<String, String> capabilitiesProp = Configure.getPropertyStartWidth("webdriver.capabilities.");
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilitiesProp.forEach((k, v) -> {
                String prop = k.replaceAll("webdriver.capabilities.", "");
                if (v.equals("true") || v.equals("false"))
                    capabilities.setCapability(prop, Boolean.parseBoolean(v));
                else
                    capabilities.setCapability(prop, v);
            });
            Configuration.browserCapabilities = capabilities;
        } else {
            log.info("Ui Тесты стартовали локально");
        }
    }


    @SneakyThrows
    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
//        if (Configure.isIntegrationTestIt())
//            RunningHandler.finishLaunch();
//        ObjectPoolService.saveEntities(Configure.getAppProp("data.folder") + "/shareFolder/logData.json");
       // new File(Configure.getProperty("allure.results")).mkdir();
        /*FileWriter fooWriter = new FileWriter(Configure.getProperty("allure.results") + "environment.properties", false);
        fooWriter.write("ENV=" + Configure.getProperty("env"));
        fooWriter.close();*/
    }
}
