# Collect data

Kotlin (jdk11), Selenium, Serenity

It is a selenium project for collect data from web sites.
You can see `auto.ru` parsing as example:

`src\main\kotlin\com\kak666tus\collect_data\beans` - Beans for writing to csv files

```kotlin
data class CarInfo(
    var id: String,
    var engineDisplacement: String,
    var enginePower: String,
    var fuelType: String,
    var transmission: String,
    var bodyType: String,
    var driveUnit: String,
    var carColor: String,
    var carPrice: String,
    var productionDate: String,
    var mileage: String
)
```

`src\main\kotlin\com\kak666tus\collect_data\pages` - Page's elements descriptions 

```kotlin

@DefaultUrl("https://auto.ru/cars")
open class AutoRuCarSearchPage : AbstractBlock<AutoRuCarSearchPage>() {

    @FindBy(css = "[class='Button Button_color_white Button_size_s Button_type_link Button_width_default ListingPagination-module__next']")
    lateinit var nextPageButton: WebElementFacade

    @FindBy(css = "[class='GeoSelect__title-shrinker']")
    lateinit var geoSelect: WebElementFacade

}
```

`src\main\kotlin\com\kak666tus\collect_data\steps` - Actions for elements from `collect_data\pages`

```kotlin
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

}
```

`src\test\kotlin\com\kak666tus\collect_data\autoru\CarInfoCollector.kt` - Collector method example

```kotlin
    @Test
    fun `collect auto ru data all_owners asc`() {
        autoRuCarSearchSteps
            .openPage(allOwners)
        val writer = createFileAndGetWriter( "src/test/output/all_owners.csv")
        autoRuCarSearchSteps
            .clearGeoData()
            .writeCarInfoIntoFile(writer)
        writer.close()
    }
```

`src\test\output` - Folder for csv files
