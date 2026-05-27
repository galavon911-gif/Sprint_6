package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Форма «Для кого самокат»

    // Поле ввода «Имя»
    private final By nameField = By.xpath("//input[@placeholder='* Имя']");
    // Поле ввода «Фамилия»
    private final By surnameField = By.xpath("//input[@placeholder='* Фамилия']");
    // Поле ввода «Адрес»
    private final By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    // Поле выбора «Станция метро»
    private final By metroField = By.className("select-search__input");
    // Элемент выпадающего списка метро
    private final By metroOption = By.className("select-search__row");
    // Поле ввода «Телефон»
    private final By phoneField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    // Кнопка «Далее»
    private final By nextButton = By.xpath("//button[text()='Далее']");

    // Форма «Про аренду»

    // Поле «Когда привезти самокат»
    private final By dateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    // Выпадающий список «Срок аренды»
    private final By rentalPeriodDropdown = By.className("Dropdown-control");
    // Опция срока аренды «сутки»
    private final By rentalPeriodOptionDay = By.xpath("//div[@class='Dropdown-menu']/div[text()='сутки']");
    // Опция срока аренды «двое суток»
    private final By rentalPeriodOptionTwoDays = By.xpath("//div[@class='Dropdown-menu']/div[text()='двое суток']");
    // Чекбокс цвета «чёрный жемчуг»
    private final By blackColorCheckbox = By.id("black");
    // Чекбокс цвета «серая уточка»
    private final By greyColorCheckbox = By.id("grey");
    // Поле «Комментарий для курьера»
    private final By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    // Кнопка «Заказать» под формой
    private final By orderButton = By.xpath("//div[contains(@class, 'Order_Buttons')]/button[text()='Заказать']");

    // Окно подтверждения
    // Кнопка «Да»
    private final By confirmOrderButton = By.xpath("//button[text()='Да']");
    // Заголовок окна успешного заказа
    private final By orderCreatedHeader = By.className("Order_ModalHeader__3FDaJ");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    // Заполнение первого шага формы
    public void fillFirstForm(String name, String surname, String address, String metro, String phone) {
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(surnameField).sendKeys(surname);
        driver.findElement(addressField).sendKeys(address);

        driver.findElement(metroField).sendKeys(metro);
        wait.until(ExpectedConditions.elementToBeClickable(metroOption)).click();

        driver.findElement(phoneField).sendKeys(phone);
        driver.findElement(nextButton).click();
    }

    // Заполнение второго шага формы
    public void fillSecondForm(String date, boolean isBlackColor, boolean isOneDay, String comment) {

        driver.findElement(rentalPeriodDropdown).click();
        if (isOneDay) {
            wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodOptionDay)).click();
        } else {
            wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodOptionTwoDays)).click();
        }

        if (isBlackColor) {
            driver.findElement(blackColorCheckbox).click();
        } else {
            driver.findElement(greyColorCheckbox).click();
        }

        driver.findElement(commentField).sendKeys(comment);
        driver.findElement(orderButton).click();
    }

    // Клик «Да» в окне подтверждения
    public void confirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmOrderButton)).click();
    }

    // Проверка появления окна с успешным статусом
    public boolean isOrderCreatedStatusDisplayed() {
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(orderCreatedHeader)).getText();
        return text.contains("Заказ оформлен");
    }
}