package com.baylor.se.lms.selenium;

import com.baylor.se.lms.LmsApplication;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@RunWith(SpringRunner.class)
@Ignore
public class    LoginTest {


    private WebDriver driver;
    @BeforeClass
    public static void setupClass(){
        WebDriverManager.chromedriver().setup();
    }
    @Before
    public void setupTest(){
        driver =  new ChromeDriver();
    }

    @After
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }
    @Test
    public void testLoginPageTitle(){
        driver.get("http://localhost:3000/");
        String a =driver.getTitle();
        Assert.assertEquals(a,"React App");
    }
    @Test
    public void testLoginUser() {
        driver.get("http://localhost:3000/login");
       // driver.wait(2000);
        WebElement username = driver.findElement(By.xpath("//*[@id=\"normal_login_username\"]"));
        username.sendKeys("sanjelji");
        WebElement password = driver.findElement(By.xpath("//*[@id=\"normal_login_password\"]"));
        password.sendKeys("password");
        WebElement button = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[3]/div/div/span/button"));
        button.click();
        Assert.assertTrue(driver.findElement(By.xpath("/html/body/div/div/div/div/form/div[4]/div/div/span/button")).isDisplayed());

    }
    @Test
    public  void testInvalid(){

    }
}
