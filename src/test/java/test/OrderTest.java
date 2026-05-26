package test;

import pageobject.MainPage;
import pageobject.OrderPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderTest {
    private WebDriver driver;
    private MainPage mainPage;
    private OrderPage orderPage;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get("https://qa-scooter.education-services.ru/");

        mainPage = new MainPage(driver);
        orderPage = new OrderPage(driver);

    }

    @ParameterizedTest
    @MethodSource("orderDataProvider")
    void testScooterOrderingFlow(String buttonType, String name, String surname, String address,
                                 String metro, String phone, String date, boolean isBlack,
                                 boolean isOneDay, String comment) {


        if ("top".equalsIgnoreCase(buttonType)) {
            mainPage.clickTopOrderButton();
        } else {
            mainPage.clickBottomOrderButton();
        }

        orderPage.fillFirstForm(name, surname, address, metro, phone);

        orderPage.fillSecondForm(date, isBlack, isOneDay, comment);

        orderPage.confirmOrder();

        assertTrue(orderPage.isOrderCreatedStatusDisplayed(), "Окно успешного заказа не отобразилось!");
    }

    static Stream<Arguments> orderDataProvider() {
        return Stream.of(
                // Набор данных 1: Верхняя кнопка
                Arguments.of("top", "Петр", "Иванов", "ул. Правды, д. 2, кв. 4", "Сокол", "+79506347812", "03.06.2026", true, true, "Доставить к подъезду"),
                // Набор данных 2: Нижняя кнопка
                Arguments.of("botton", "Мария", "Колова", "Дмитровское шоссе, 9", "Тимирязевская", "+79112785491", "11.07.2026", false, false, "Позвонить за час")
        );
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
