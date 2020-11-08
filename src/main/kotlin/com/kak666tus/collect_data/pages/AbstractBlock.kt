package com.kak666tus.collect_data.pages

import net.serenitybdd.core.annotations.findby.By
import net.serenitybdd.core.annotations.findby.FindBy
import net.serenitybdd.core.pages.WebElementFacade
import net.serenitybdd.screenplay.Actor
import net.serenitybdd.screenplay.actions.*
import net.thucydides.core.annotations.Step
import net.thucydides.core.pages.PageObject
import org.assertj.core.api.Assertions.assertThat
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Suppress("UNCHECKED_CAST")
abstract class AbstractBlock<T> : PageObject() {

    init {
        this.waitForAngularRequestsToFinish()
    }

    lateinit var actor: Actor

    var notificationXpath = "//div[@class='notification']//div[@class='notification-message']"

    @FindBy(xpath = "//div[@class='notification']//div[@class='notification-message']")
    lateinit var notification: WebElementFacade

    @FindBy(css = "[data-test-id='spinner']")
    lateinit var spinner: WebElementFacade

    @FindBy(css = "[data-test-id='page403']")
    lateinit var page403: WebElementFacade

    @FindBy(className = "modal-content")
    lateinit var modalContent: WebElementFacade

    @FindBy(xpath = "//*[@class='modal-content']//*[contains(@class,'modal-body')]")
    lateinit var modalBody: WebElementFacade

    @FindBy(xpath = "//*[text()='×']")
    lateinit var closeButton: WebElementFacade

    @FindBy(xpath = "//*[contains(@class,'modal-body')]//*[contains(@class,'alert-danger')]")
    lateinit var errorAlert: WebElementFacade

    @FindBy(xpath = "//*[contains(@class,'ps__rail-y')]")
    lateinit var scroll: WebElementFacade

    @FindBy(css = "[class='form-spinner']")
    lateinit var form_spinner: WebElementFacade

    @FindBy(xpath = "//*[@class='modal-content']//*[@type='submit']")
    lateinit var buttonSubmitInModal: WebElementFacade

    @FindBy(xpath = "//*[@class='modal-body-container']")
    lateinit var textInModal: WebElementFacade

    @FindBy(xpath = "//*[@class='modal-header']/*[@class='modal-title']")
    lateinit var modalTitle: WebElementFacade

    @FindBy(xpath = "//*[@class='modal-content']//*[@class='fa fa-trash-o']")
    lateinit var buttonRemoveInModal: WebElementFacade

    @FindBy(xpath = "//*[@class='modal-content']//*[text()='Отмена']")
    lateinit var buttonCancelInModal: WebElementFacade

    fun selector(name: String): WebElementFacade {
        return element(By.xpath("//div[contains(@id,'react-select') and text()='$name']"))
    }

    fun waitAndClick(element: WebElementFacade): T {
        element.waitUntilPresent<WebElementFacade>()
        actor.attemptsTo(Scroll.to(element))
        actor.attemptsTo(Click.on(element))
        return this as T
    }

    fun jsClick(element: WebElementFacade): T {
        element.waitUntilPresent<WebElementFacade>()
        actor.attemptsTo(Scroll.to(element))
        actor.attemptsTo(JSClickOnElement(element))
        return this as T
    }

    fun reloadPageByKey(): T {
        pressKey(Keys.F5)
        waitNotVisible(spinner)
        return this as T
    }

    fun reloadPageByDriver(): T {
        driver.navigate().refresh()
        waitNotVisible(spinner)
        return this as T
    }

    fun clickButtonRemoveInModal(): T {
        waitAndClick(buttonRemoveInModal)
        waitNotVisible(spinner)
        return this as T
    }

    fun clickButtonSubmitInModal(): T {
        waitAndClick(buttonSubmitInModal)
        waitNotVisible(spinner)
        return this as T
    }

    fun checkTextInModal(text: String): T {
        checkTextContent(textInModal, text)
        return this as T
    }

    fun clickButtonCancelInModal(): T {
        waitAndClick(buttonCancelInModal)
        return this as T
    }

    fun enterValue(element: WebElementFacade, value: String): T {
        enter(value).into(element)
        return this as T
    }

    fun enterValueAndSubmit(element: WebElementFacade, value: String): T {
        enter(value + Keys.ENTER).into(element)
        return this as T
    }

    fun checkText(element: WebElementFacade, text: String): T {
        waitFor("Expected: $text, Actual: ${element.text}", ExpectedConditions.textToBePresentInElement(element, text))
        assertThat(element.text).isEqualTo(text)
        return this as T
    }

    fun checkTextContent(element: WebElementFacade, text: String): T {
        waitIsVisible(element)
        assertThat(element.textContent.trim()).isEqualTo(text.trim())
        return this as T
    }

    fun checkAttribute(element: WebElementFacade, attribute: String, value: String): T {
        waitFor(
            "Expected: attribute $attribute is $value, Actual: ${element.getAttribute(attribute.toString())}",
            ExpectedConditions.attributeContains(element.element, attribute.toString(), value)
        )
        assertThat(element.getAttribute(attribute)).isEqualTo(value)
        return this as T
    }

    fun checkAttributeContains(element: WebElementFacade, attribute: String, value: String): T {
        waitFor(
            "Expected: attribute $attribute is $value, Actual: ${element.getAttribute(attribute.toString())}",
            ExpectedConditions.attributeContains(element.element, attribute.toString(), value)
        )
        assertThat(element.getAttribute(attribute)).contains(value)
        return this as T
    }

    fun checkTextContains(element: WebElementFacade, text: String): T {
        waitFor("Expected: $text, Actual: ${element.text}", ExpectedConditions.textToBePresentInElement(element, text))
        assertThat(element.text).contains(text.trim())
        return this as T
    }

    fun waitTextContains(element: WebElementFacade, text: String): T {
        waitFor("Expected: $text, Actual: ${element.text}", ExpectedConditions.textToBePresentInElement(element, text))
        return this as T
    }

    fun checkTextNotContains(element: WebElementFacade, text: String): T {
        scrollTo(element)
        assertThat(element.text).doesNotContain(text)
        return this as T
    }

    fun pressKey(keys: Keys): T {
        driver.findElement(By.xpath("//*")).sendKeys(keys)
        return this as T
    }

    fun pressShiftTab(): T {
        driver.findElement(By.xpath("//*")).sendKeys(Keys.chord(Keys.SHIFT, Keys.TAB))
        return this as T
    }

    fun pressCtrlC(): T {
        driver.switchTo().activeElement().sendKeys(Keys.chord(Keys.CONTROL, "C"))
        return this as T
    }

    fun pressCtrlV(): T {
        driver.switchTo().activeElement().sendKeys(Keys.chord(Keys.CONTROL, "V"))
        return this as T
    }

    fun pressCtrlArrowRight(): T {
        driver.switchTo().activeElement().sendKeys(Keys.chord(Keys.CONTROL, Keys.ARROW_RIGHT))
        return this as T
    }

    @Step
    fun sendText(text: String): T {
        actor.attemptsTo(SendKeys.of(text).into(By.xpath("//*")))
        return this as T
    }

    fun sendTextToActiveElement(text: String): T {
        val activeElement: WebElementFacade = element(driver.switchTo().activeElement())
        actor.attemptsTo(Clear.field(activeElement))
        actor.attemptsTo(SendKeys.of(text).into(activeElement))
        return this as T
    }

    fun sendTextToActiveElementAndSubmit(text: String): T {
        val activeElement: WebElementFacade = element(driver.switchTo().activeElement())
        actor.attemptsTo(Clear.field(activeElement))
        actor.attemptsTo(SendKeys.of(text).into(activeElement).thenHit(Keys.ENTER))
        return this as T
    }

    fun waitSpinnerNotVisible(): T {
        waitNotVisible(form_spinner)
        return this as T
    }

    fun waitNotVisible(element: WebElementFacade): T {
        element.waitUntilNotVisible<WebElementFacade>()
        return this as T
    }

    fun waitNotVisible(element: WebElementFacade, seconds: Int): T {
        element.withTimeoutOf<WebElementFacade>(seconds, ChronoUnit.SECONDS).waitUntilNotVisible<WebElementFacade>()
        return this as T
    }

    fun waitIsVisible(element: WebElementFacade): T {
        element.waitUntilVisible<WebElementFacade>()
        return this as T
    }

    fun shouldVisible(element: WebElementFacade): T {
        element.shouldBeVisible()
        return this as T
    }

    fun shouldNotVisible(element: WebElementFacade): T {
        element.shouldNotBeVisible()
        return this as T
    }

    fun waitIsPresent(element: WebElementFacade): T {
        element.waitUntilPresent<WebElementFacade>()
        return this as T
    }

    fun waitIsEnabled(element: WebElementFacade): T {
        element.waitUntilEnabled<WebElementFacade>()
        return this as T
    }

    fun waitIsClickable(element: WebElementFacade): T {
        element.waitUntilClickable<WebElementFacade>()
        return this as T
    }

    @Step
    fun fillInput(element: WebElementFacade, text: String): T {
        actor.attemptsTo(Clear.field(element))
        actor.attemptsTo(SendKeys.of(text).into(element))
        return this as T
    }

    @Step
    fun fillInputAndSubmit(element: WebElementFacade, text: String): T {
        actor.attemptsTo(Clear.field(element))
        actor.attemptsTo(SendKeys.of(text).into(element).thenHit(Keys.ENTER))
        return this as T
    }

    @Step
    fun clearInput(element: WebElementFacade): T {
        actor.attemptsTo(Clear.field(element))
        return this as T
    }

    fun getLastNotificationMessage(): String {
        return notification.text
    }

    fun scrollTo(element: WebElementFacade): T {
        executeJsScript("arguments[0].scrollIntoView(true);", element.element)
        return this as T
    }

    fun setValue(element: WebElementFacade, value: String): T {
        executeJsScript("arguments[0].value=arguments[1];", element.element, value)
        return this as T
    }

    fun waitModalIsDisplayed(): T {
        waitIsVisible(modalContent)
        return this as T
    }

    fun checkModalIsNotDisplayed(): T {
        shouldNotVisible(modalContent)
        return this as T
    }

    fun checkModalBodyTextContains(message: String): T {
        checkTextContains(modalBody, message)
        return this as T
    }


    fun checkErrorAlertMessage(message: String): T {
        checkTextContent(errorAlert, message)
        return this as T
    }

    fun checkErrorAlertMessageContains(message: String): T {
        checkTextContains(errorAlert, message)
        return this as T
    }

    fun clickModalTitle(): T {
        waitAndClick(modalTitle)
        return this as T
    }

    fun selectFromReactSelect(element: WebElementFacade, item: String): T {
        element.typeAndEnter<WebElementFacade>(item)
        return this as T
    }

    fun scrollTopModal(): T {
        val modal =
            driver.findElement(By.xpath("//*[@class='modal-body']//*[contains(@class,'scrollbar-container')]"))
        executeJsScript("arguments[0].scrollTop=0;", modal)
        return this as T
    }

    fun scrollEndModal(): T {
        val modal =
            driver.findElement(By.xpath("//body"))
        executeJsScript("arguments[0].scrollTop=20000;", modal)
        return this as T
    }

    fun getLocatorFromWebElement(element: WebElementFacade): String? {
        return element.element.toString().split("xpath: ")[1].replaceFirst("]", "")
    }

    private fun executeJsScript(script: String, element: WebElement, vararg args: Any) {
        val js: JavascriptExecutor = driver as JavascriptExecutor
        js.executeScript(script, element, args)
    }

    fun closeModalWindow(): T {
        if (modalContent.isPresent) {
            waitAndClick(closeButton)
        }
        return this as T
    }

    fun checkElementIsSelected(element: WebElementFacade, isSelected: Boolean) {
        assertThat(element.isSelected).isEqualTo(isSelected).withFailMessage("Expected: $element is selected = $isSelected")
    }

    fun checkCurrentPageIs403(): T {
        shouldVisible(page403)
        return this as T
    }

    fun checkCurrentPageIsNot403(): T {
        shouldNotVisible(page403)
        return this as T
    }

    fun checkDate(expectedDate: LocalDateTime, actualDate: String, formatter: DateTimeFormatter): T {
        val actual = LocalDateTime.parse(actualDate, formatter)
        assertThat(actual.isAfter(expectedDate.minusMinutes(2)) &&
            actual.isBefore(expectedDate.plusMinutes(2))).withFailMessage("Expected $expectedDate, but actual is $actualDate").isTrue()
        return this as T
    }

    fun setActor(actor: Actor): T {
        this.actor = actor
        return this as T
    }
}
