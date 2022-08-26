package org.example.ui.tests;

import io.qameta.allure.Description;
import org.example.ConfProperties;
import org.example.ui.pages.DragAndDropPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class DragAndDropTest extends BaseTest {
    DragAndDropPage page;

    @BeforeEach
    public void setup(){
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty(("chromedriver")));
        driver = new ChromeDriver();
        driver.get(ConfProperties.getProperty("dragpage"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        page = new DragAndDropPage(driver);
    }

    @Test
    @Description("Drag and drop elements and check that they was swapped")
    public void swapTest(){
        page.checkPositionBefore();
        page.dragAndDrop();
        page.checkPositionAfter();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
