package org.example.ui.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class DragAndDropPage {
    public WebDriver driver;

    public DragAndDropPage(WebDriver driver){
        this.driver = driver;
    }

    private By drag = By.id("column-a");
    private By drop = By.id("column-b");
    private By a_head = By.cssSelector("#column-a > header");
    private By b_head = By.cssSelector("#column-b > header");

    @Step("Check first position")
    public void checkPositionBefore(){
        WebElement a = driver.findElement(a_head);
        WebElement b = driver.findElement(b_head);
        Assertions.assertEquals("A", a.getText());
        Assertions.assertEquals("B", b.getText());
    }

    @Step("Switch elements")
    public void dragAndDrop(){
        WebElement dragger = driver.findElement(drag);
        WebElement dropper = driver.findElement(drop);
        Actions action = new Actions(driver);
        //action.dragAndDrop(dragger, dropper).perform();
        action.clickAndHold(dragger)
                .moveToElement(dropper)
                .release(dropper)
                .build()
                .perform();
    }


    @Step("Check their new positions")
    public void checkPositionAfter(){
        WebElement a = driver.findElement(a_head);
        WebElement b = driver.findElement(b_head);
        Assertions.assertEquals("B", a.getText());
        Assertions.assertEquals("A", b.getText());
    }
}
