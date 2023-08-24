package core.tests;

import com.codeborne.selenide.Selenide;
import core.extensions.ConfigExtension;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Selenide.*;

@ExtendWith(ConfigExtension.class)
@Epic(value = "Cloud Director")
@Feature(value = "VMWare организация")
@Tags({@Tag(value = "ui_cloud_director")})
public class TestGroup1 {
    @Test
    public void goToYandex() throws InterruptedException {
        Selenide.open("/");
        Thread.sleep(2000);
    }
}
