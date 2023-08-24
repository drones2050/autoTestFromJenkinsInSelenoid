package core.extensions;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;

import java.util.logging.Level;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static core.TestsExecutionListener.initDriver;

public class ConfigExtension implements AfterEachCallback, BeforeEachCallback, BeforeAllCallback, InvocationInterceptor {
    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
       // new AllureSelenide().screenshots(true).savePageSource(true));
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.ALL);
        Configuration.browserCapabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        initDriver();
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        closeWebDriver();
    }
}
