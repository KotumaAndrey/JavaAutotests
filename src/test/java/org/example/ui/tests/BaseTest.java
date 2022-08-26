package org.example.ui.tests;

import org.openqa.selenium.WebDriver;

public class BaseTest {
    public static WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }
}