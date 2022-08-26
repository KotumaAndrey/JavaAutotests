package org.example.ui.tests;

import io.qameta.allure.Description;
import org.example.ConfProperties;
import org.example.ui.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class LoginNegativeTests extends BaseTest {
    LoginPage page;

    //Здесь использую аннотации testng вместо junit для параметризации проверок
    @BeforeTest
    public void setup(){
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty(("chromedriver")));
        driver = new ChromeDriver();
        driver.get(ConfProperties.getProperty("loginpage"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        page = new LoginPage(driver);
    }

    @DataProvider
    public Object[][] mailChanges() {
        return new Object[][]{
                {"111"},
                {"comomo"},
                {"###"}
        };
    }

    @DataProvider
    public Object[][] numberChanges() {
        return new Object[][]{
                {"@!@!@!@!@!"},
                {"aaaaaaaaaa"},
                {"123124"}
        };
    }

    @Test
    @Description("Wrong FirstName")
    public void negativeFirstName(){
        page.fillIncorrectFirstName();
        page.fillCorrectLastName();
        page.fillGender();
        page.fillCorrectNumber();
        page.fillCorrectMail();
        page.fillHobbies();
        page.fillAddress();
        page.clickSubmit();
        page.checkNoSubmition();
    }

    @Test
    @Description("Wrong LastName")
    public void negativeLastName(){
        page.fillCorrectFirstName();
        page.fillIncorrectLastName();
        page.fillGender();
        page.fillCorrectNumber();
        page.fillCorrectMail();
        page.fillHobbies();
        page.fillAddress();
        page.clickSubmit();
        page.checkNoSubmition();
    }

    @Test
    @Description("No Gender")
    public void negativeGender(){
        page.fillCorrectFirstName();
        page.fillCorrectLastName();
        page.notFillGender();
        page.fillCorrectNumber();
        page.fillCorrectMail();
        page.fillHobbies();
        page.fillAddress();
        page.clickSubmit();
        page.checkNoSubmition();
    }

    @Test(dataProvider = "mailChanges")
    @Description("Wrong mail")
    public void negativeMail(String mail){
        page.fillIncorrectFirstName();
        page.fillCorrectLastName();
        page.fillGender();
        page.fillCorrectNumber();
        page.fillIncorrectMail(mail);
        page.fillHobbies();
        page.fillAddress();
        page.clickSubmit();
        page.checkNoSubmition();
    }

    @Test(dataProvider = "numberChanges")
    @Description("Wrong mobile")
    public void negativeNumber(String number){
        page.fillIncorrectFirstName();
        page.fillCorrectLastName();
        page.fillGender();
        page.fillIncorrectNumber(number);
        page.fillCorrectMail();
        page.fillHobbies();
        page.fillAddress();
        page.clickSubmit();
        page.checkNoSubmition();
    }


    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
