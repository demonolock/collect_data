package com.kak666tus.collect_data.steps.autoru

import com.kak666tus.collect_data.pages.autoru.AutoRuCarSearchPage
import net.serenitybdd.screenplay.Actor
import net.serenitybdd.screenplay.abilities.BrowseTheWeb
import net.thucydides.core.annotations.Step
import net.thucydides.core.steps.ScenarioSteps
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.BufferedWriter

open class AutoRuCarSearchSteps : ScenarioSteps() {

    private val actor = Actor("Marry")

    @Step("Open page {url}")
    fun openPage(url: String) {
        driver.navigate().to(url)
        waitLoadPage()
    }

    @Step("CLear geo data")
    fun clearGeoData(): AutoRuCarSearchSteps {
        onAutoRuCarSearchPage().clearGeoData()
        return this
    }

    @Step("Write car into file")
    fun writeCarInfoIntoFile(writer: BufferedWriter): AutoRuCarSearchSteps {
        var pageCounter = 1
        do {
            println("Page number - $pageCounter")
            pageCounter+=1
            for (card in onAutoRuCarSearchPage().carCards()) {
                writer.write(onAutoRuCarSearchPage().getCarInfo(card).toString())
                writer.newLine()
                writer.flush()
            }
            onAutoRuCarSearchPage().clickToNextPage()
        } while (onAutoRuCarSearchPage().nextPageButton.isPresent)
        return this
    }

    private fun onAutoRuCarSearchPage(): AutoRuCarSearchPage {
        return pages.get(AutoRuCarSearchPage::class.java).setActor(actor)
    }

    private fun waitLoadPage() {
        val spinner = By.cssSelector("[data-test-id='spinner']")
        WebDriverWait(driver, 15)
            .until(ExpectedConditions.invisibilityOfElementLocated(spinner))
        actor.can(BrowseTheWeb.with(driver))
    }
}
