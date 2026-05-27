package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // --- Список элементов Главной страницы ---

    // Верхняя кнопка «Заказать»
    private final By topOrderButton = By.className("Button_Button__ra12g");

    // Нижняя кнопка «Заказать»
    private final By bottomOrderButton = By.xpath("//div[contains(@class, 'Home_ThirdPart')]//button[text()='Заказать']");

    // Динамическая строка для ID кнопок вопросов в FAQ
    private final String accordionHeadingId = "accordion__heading-%d";

    // Динамическая строка для ID панелей с текстом ответов в FAQ

    private final String accordionPanelId = "accordion__panel-%d";

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    // Нажать на стрелку в FAQ
    public void clickAccordionQuestion(int index) {
        By questionLocator = By.id(String.format(accordionHeadingId, index));

        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(questionLocator));


        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    // Получить текст ответа в FAQ по индексу (0-7)
    public String getAccordionAnswerText(int index) {
        By answerLocator = By.id(String.format(accordionPanelId, index));
        WebElement answerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(answerLocator));
        return answerElement.getText();
    }

    // Нажать верхнюю кнопку заказа
    public void clickTopOrderButton() {
        driver.findElement(topOrderButton).click();
    }

    // Нажать нижнюю кнопку заказа
    public void clickBottomOrderButton() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(bottomOrderButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}