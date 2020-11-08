package com.kak666tus.collect_data.autoru

import com.kak666tus.collect_data.BaseSerenityTest
import com.kak666tus.collect_data.steps.autoru.AutoRuCarSearchSteps
import net.thucydides.core.annotations.Steps
import org.junit.Test
import java.io.BufferedWriter
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class CarInfoCollector : BaseSerenityTest() {

    private val oneOwner="https://auto.ru/cars/bmw/used/?has_image=false&owners_count_group=ONE&steering_wheel=LEFT&sort=price-asc"
    private val noMoreTwoOwners="https://auto.ru/cars/bmw/used/?has_image=false&owners_count_group=LESS_THAN_TWO&steering_wheel=LEFT&sort=price-asc"
    private val allOwners="https://auto.ru/cars/bmw/used/?has_image=false&steering_wheel=LEFT&sort=price-asc"
    private val oneOwner_desc="https://auto.ru/cars/bmw/used/?has_image=false&owners_count_group=ONE&steering_wheel=LEFT&sort=price-desc"
    private val noMoreTwoOwners_desc="https://auto.ru/cars/bmw/used/?has_image=false&owners_count_group=LESS_THAN_TWO&steering_wheel=LEFT&sort=price-desc"
    private val allOwners_desc="https://auto.ru/cars/bmw/used/?has_image=false&steering_wheel=LEFT&sort=price-desc"
    private val allOwners_from56000="https://auto.ru/cars/bmw/used/?price_from=56001&sort=price-asc&output_type=list"
    private val allOwners_from560000="https://auto.ru/cars/bmw/used/?price_from=560001&sort=price-asc&output_type=list"
    private val allOwners_from1080000="https://auto.ru/cars/bmw/used/?price_from=1080001&sort=price-asc&output_type=list"
    private val allOwners_from2100000="https://auto.ru/cars/bmw/used/?price_from=2100001&sort=price-asc&output_type=list"
    private val allOwners_from6500000="https://auto.ru/cars/bmw/used/?price_from=6500001&sort=price-asc&output_type=list"

    @Steps
    lateinit var autoRuCarSearchSteps: AutoRuCarSearchSteps

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

    @Test
    fun `collect auto ru data no_more_two_owners asc`() {
        autoRuCarSearchSteps
            .openPage(noMoreTwoOwners)
        val writer = createFileAndGetWriter("src/test/output/no_more_two_owners.csv")
        autoRuCarSearchSteps
            .clearGeoData()
            .writeCarInfoIntoFile(writer)
        writer.close()
    }

    @Test
    fun `collect auto ru data one_owner asc`() {
        autoRuCarSearchSteps
            .openPage(oneOwner)
       val writer = createFileAndGetWriter("src/test/output/one_owner.csv")
        autoRuCarSearchSteps
            .clearGeoData()
            .writeCarInfoIntoFile(writer)
        writer.close()
    }

    @Test
    fun `collect auto ru data all_owners desc`() {
        autoRuCarSearchSteps
            .openPage(allOwners_desc)
        val writer = createFileAndGetWriter( "src/test/output/all_owners_desc.csv")
        autoRuCarSearchSteps
            .clearGeoData()
            .writeCarInfoIntoFile(writer)
        writer.close()
    }

    @Test
    fun `collect auto ru data no_more_two_owners desc`() {
        autoRuCarSearchSteps
            .openPage(noMoreTwoOwners_desc)
        val writer = createFileAndGetWriter("src/test/output/no_more_two_owners_desc.csv")
        autoRuCarSearchSteps
            .clearGeoData()
            .writeCarInfoIntoFile(writer)
        writer.close()
    }

    @Test
    fun `collect auto ru data one_owner desc`() {
        autoRuCarSearchSteps
            .openPage(oneOwner_desc)
        val writer = createFileAndGetWriter("src/test/output/one_owner_desc.csv")
        autoRuCarSearchSteps
            .clearGeoData()
            .writeCarInfoIntoFile(writer)
        writer.close()
    }

    @Test
    fun `collect auto ru data allOwners_from56000`() {
        autoRuCarSearchSteps
            .openPage(allOwners_from56000)
        val writer = createFileAndGetWriter("src/test/output/allOwners_from56000.csv")
        autoRuCarSearchSteps
            .clearGeoData()
            .writeCarInfoIntoFile(writer)
        writer.close()
    }

    @Test
    fun `collect auto ru data allOwners_from560000`() {
        autoRuCarSearchSteps
            .openPage(allOwners_from560000)
        val writer = createFileAndGetWriter("src/test/output/allOwners_from560000.csv")
        autoRuCarSearchSteps
            .clearGeoData()
            .writeCarInfoIntoFile(writer)
        writer.close()
    }

    @Test
    fun `collect auto ru data allOwners_from1080000`() {
        autoRuCarSearchSteps
            .openPage(allOwners_from1080000)
        val writer = createFileAndGetWriter("src/test/output/allOwners_from1080000.csv")
        autoRuCarSearchSteps
            .clearGeoData()
            .writeCarInfoIntoFile(writer)
        writer.close()
    }

    @Test
    fun `collect auto ru data allOwners_from2100000`() {
        autoRuCarSearchSteps
            .openPage(allOwners_from2100000)
        val writer = createFileAndGetWriter("src/test/output/allOwners_from2100000.csv")
        autoRuCarSearchSteps
            .clearGeoData()
            .writeCarInfoIntoFile(writer)
        writer.close()
    }

    @Test
    fun `collect auto ru data allOwners_from6500000`() {
        autoRuCarSearchSteps
            .openPage(allOwners_from6500000)
        val writer = createFileAndGetWriter("src/test/output/allOwners_from6500000.csv")
        autoRuCarSearchSteps
            .clearGeoData()
            .writeCarInfoIntoFile(writer)
        writer.close()
    }

    private fun createFileAndGetWriter(path: String): BufferedWriter {
        val file = File(path)
        if (!file.exists()) {
            file.createNewFile()
        }
        val writer = Files.newBufferedWriter(Paths.get(path))
        writer.write("id,engineDisplacement,enginePower,fuelType,transmission,bodyType,driveUnit,carColor,carPrice,productionDate,mileage")
        writer.newLine()
        return writer
    }
}
