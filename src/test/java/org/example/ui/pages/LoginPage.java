package org.example.ui.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    public WebDriver driver;

    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    private By submit = By.id("submit");
    private By submission = By.id("example-modal-sizes-title-lg");

    private By firstName = By.id("firstName");
    private By lastName = By.id("lastName");
    private By userEmail = By.id("userEmail");
    private By maleRadio = By.cssSelector("#genterWrapper > div.col-md-9.col-sm-12 > div:nth-child(1) > label");
    private By femaleRadio = By.cssSelector("#genterWrapper > div.col-md-9.col-sm-12 > div:nth-child(2) > label");
    private By otherRadio = By.cssSelector("#genterWrapper > div.col-md-9.col-sm-12 > div:nth-child(3) > label");
    private By mobile = By.id("userNumber");
    private By date = By.id("dateOfBirthInput");
    private By subj = By.cssSelector("#subjectsContainer > div > div.subjects-auto-complete__value-container.subjects-auto-complete__value-container--is-multi.css-1hwfws3");
    private By sportRadio = By.cssSelector("#hobbiesWrapper > div.col-md-9.col-sm-12 > div:nth-child(1) > label");
    private By readingRadio = By.cssSelector("#hobbiesWrapper > div.col-md-9.col-sm-12 > div:nth-child(2) > label");
    private By musicRadio = By.cssSelector("#hobbiesWrapper > div.col-md-9.col-sm-12 > div:nth-child(3) > label");
    private By address = By.id("currentAddress");



    public void fillCorrectFirstName(){
        driver.findElement(firstName).click();
        driver.findElement(firstName).sendKeys("^ ^");
    }

    public void fillIncorrectFirstName(){
        driver.findElement(firstName).click();
        driver.findElement(firstName).sendKeys("");
    }

    public void fillCorrectLastName(){
        driver.findElement(lastName).click();
        driver.findElement(lastName).sendKeys("New");
    }

    public void fillIncorrectLastName(){
        driver.findElement(lastName).click();
        driver.findElement(lastName).sendKeys("");
    }

    public void fillCorrectMail(){
        driver.findElement(userEmail).click();
        driver.findElement(userEmail).sendKeys("0@0.ru");
    }


    public void fillIncorrectMail(String mail) {
        driver.findElement(userEmail).click();
        driver.findElement(userEmail).sendKeys("0@0." + mail);
    }

    public void fillGender(){
        driver.findElement(otherRadio).click();
    }

    public void notFillGender(){
        //Nothing to do
    }

    public void fillCorrectNumber(){
        driver.findElement(mobile).click();
        driver.findElement(mobile).sendKeys("0000009987");
    }

    public void fillIncorrectNumber(String number){
        driver.findElement(mobile).click();
        driver.findElement(mobile).sendKeys(number);
    }


    public void fillHobbies(){
        driver.findElement(sportRadio).click();
        driver.findElement(readingRadio).click();
    }

    public void fillAddress(){
        driver.findElement(address).click();
        driver.findElement(address).sendKeys("aloyt");
    }

    public void clickSubmit(){
        driver.findElement(submit).click();
    }

    public void checkSubmition(){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(submission));
        Assertions.assertTrue(driver.findElement(submission).isDisplayed());
    }

    public void checkNoSubmition(){
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.presenceOfElementLocated(submission));
    }

    //TODO: Add opportunity to change the date and select pictures, also choosing of subjects, states and cities
}
