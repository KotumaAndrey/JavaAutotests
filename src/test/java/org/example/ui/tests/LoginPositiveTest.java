package org.example.ui.tests;

import io.qameta.allure.Description;
import org.example.ConfProperties;
import org.example.ui.pages.DragAndDropPage;
import org.example.ui.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;

import java.util.concurrent.TimeUnit;

public class LoginPositiveTest extends BaseTest {
    LoginPage page;

    @BeforeEach
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
    @Description("Login")
    public void positiveLogin(){
        page.fillCorrectFirstName();
        page.fillCorrectLastName();
        page.fillGender();
        page.fillCorrectNumber();
        page.fillCorrectMail();
        page.fillHobbies();
        page.fillAddress();
        page.clickSubmit();
        page.checkSubmition();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
