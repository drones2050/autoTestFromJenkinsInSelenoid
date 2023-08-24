package core.tests;

import com.codeborne.selenide.Selenide;
import core.extensions.ConfigExtension;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(ConfigExtension.class)


public class Group2Test {
    @Epic("Cloud Director")
    @Test
    @Owner("VMWare организация")
    @Description("sdfsdfsdfsdf")
    @DisplayName("sdfsdfsdf")
    public void goToYandex2() throws InterruptedException {
        Selenide.open("/");
        Thread.sleep(2000);
    }
}
