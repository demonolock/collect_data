package com.kak666tus.collect_data.pages.autoru

import com.kak666tus.collect_data.beans.autoru.CarInfo
import com.kak666tus.collect_data.pages.AbstractBlock
import net.serenitybdd.core.annotations.findby.By
import net.serenitybdd.core.annotations.findby.FindBy
import net.serenitybdd.core.pages.WebElementFacade
import net.thucydides.core.annotations.DefaultUrl

@DefaultUrl("https://auto.ru/cars")
open class AutoRuCarSearchPage : AbstractBlock<AutoRuCarSearchPage>() {

    @FindBy(css = "[class='Button Button_color_white Button_size_s Button_type_link Button_width_default ListingPagination-module__next']")
    lateinit var nextPageButton: WebElementFacade

    @FindBy(css = "[class='GeoSelect__title-shrinker']")
    lateinit var geoSelect: WebElementFacade

    @FindBy(css = "[class='IconSvg IconSvg_close IconSvg_size_24 GeoSelectPopupRegion__clear']")
    lateinit var clearGeo: WebElementFacade

    @FindBy(xpath = "//*[@class='Button__text' and text()='Сохранить']")
    lateinit var saveGeo: WebElementFacade

    fun carCards(): List<WebElementFacade> {
        waitABit(3000)
        return findAll(By.xpath("//div[contains(@class, 'ListingItem-module__container')]"))
    }

    private fun carEngine(carCard: WebElementFacade): WebElementFacade {
        return carCard.find(By.xpath(".//div[@class='ListingItemTechSummaryDesktop__column'][1]/div[@class='ListingItemTechSummaryDesktop__cell'][1]"))
    }

    private fun carTransmission(carCard: WebElementFacade): WebElementFacade {
        return carCard.find(By.xpath(".//div[@class='ListingItemTechSummaryDesktop__column'][1]/div[@class='ListingItemTechSummaryDesktop__cell'][2]"))
    }

    private fun carBodyType(carCard: WebElementFacade): WebElementFacade {
        return carCard.find(By.xpath(".//div[@class='ListingItemTechSummaryDesktop__column'][1]/div[@class='ListingItemTechSummaryDesktop__cell'][3]"))
    }

    private fun carDriveUnit(carCard: WebElementFacade): WebElementFacade {
        return carCard.find(By.xpath(".//div[@class='ListingItemTechSummaryDesktop__column'][2]/div[@class='ListingItemTechSummaryDesktop__cell'][1]"))
    }

    private fun carColor(carCard: WebElementFacade): WebElementFacade {
        return carCard.find(By.xpath(".//div[@class='ListingItemTechSummaryDesktop__column'][2]/div[@class='ListingItemTechSummaryDesktop__cell'][2]"))
    }

    private fun carPrice(carCard: WebElementFacade): WebElementFacade {
        return carCard.find(By.xpath(".//div[@class='ListingItem-module__columnCellPrice']"))
    }

    private fun carProductionDate(carCard: WebElementFacade): WebElementFacade {
        return carCard.find(By.xpath(".//div[@class='ListingItem-module__year']"))
    }

    private fun carMileage(carCard: WebElementFacade): WebElementFacade {
        return carCard.find(By.xpath(".//div[@class='ListingItem-module__kmAge']"))
    }

    private fun carId(carCard: WebElementFacade): WebElementFacade {
        return carCard.find(By.xpath(".//div[@class='ListingItem-module__thumb']/a"))
    }

    fun clearGeoData() {
        waitAndClick(geoSelect)
        waitAndClick(clearGeo)
        waitAndClick(saveGeo)
    }

    fun getCarInfo(car: WebElementFacade): CarInfo {
        return CarInfo(
            carId(car).getAttribute("href").substringBeforeLast("/").substringAfterLast("/").trim(),
            carEngine(car).textContent.split(" ")[0].trim(),
            carEngine(car).textContent.split("/")[1].split("л.с.")[0].trim(),
            carEngine(car).textContent.substringAfterLast("/").trim(),
            carTransmission(car).textContent.trim(),
            carBodyType(car).textContent.split(" ")[0].trim(),
            carDriveUnit(car).textContent.trim(),
            carColor(car).textContent.trim(),
            carPrice(car).textContent.split("₽")[0].replace(" ", ""),
            carProductionDate(car).textContent,
            carMileage(car).textContent.split("км")[0].replace(" ", "")
        )
    }

    fun clickToNextPage() {
        if (nextPageButton.isPresent) {
            pressCtrlArrowRight()
        }
        this.waitForAngularRequestsToFinish()
        this.waitForRenderedElementsToBePresent(By.xpath("//div[contains(@class, 'ListingItem-module__container')]"))
    }
}
