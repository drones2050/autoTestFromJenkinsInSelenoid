package tests;

import com.codeborne.selenide.Selenide;
import core.extensions.ConfigExtension;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Selenide.$x;


@ExtendWith(ConfigExtension.class)


public class Group2Test {
    @Epic("Cloud Director")
    @Test
    @Owner("VMWare организация")
    @Description("sdfsdfsdfsdf")
    @DisplayName("sdfsdfsdf")
    public void goToYandex2() throws InterruptedException {
        Selenide.open("/");
        //$x("div[@id='5']").click();
        Thread.sleep(20000);
    }
}
