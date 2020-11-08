package com.kak666tus.collect_data

import net.thucydides.core.annotations.Managed
import org.openqa.selenium.WebDriver

abstract class BaseTest {
    @Managed
    lateinit var driver: WebDriver
}
