package ru.netology.delivery.data.ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


public class DeliveryTest {


    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }

    @Test
    public void cardDelivery() {

        var name = DataGenerator.generateName("ru");
        var address = DataGenerator.generateAddress("ru");
        var phone = DataGenerator.generatePhone("ru");
        var date1 = DataGenerator.generateDate(13);
        var date2 = DataGenerator.generateDate(20);

        $("[data-test-id=city] input").setValue(address);
        $("[data-test-id=date] input").sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date1);
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $(withText("Я соглашаюсь с условиями обработки и использования моих персональных данных")).click();
        $(byText("Запланировать")).click();
        $x("//div[@class='notification__content']")
                .should(matchText(date1), Duration.ofSeconds(15));

        $("[data-test-id=date] input").sendKeys(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date2);
        $(byText("Запланировать")).click();
        $("[data-test-id='success-notification']").should(visible, Duration.ofSeconds(15));
        $x("//button[@class='button button_view_extra button_size_s button_theme_alfa-on-white']").click();
        $("[data-test-id='success-notification']").should(matchText(date2), Duration.ofSeconds(15));



    }
}
