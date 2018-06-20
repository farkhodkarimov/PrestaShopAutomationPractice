package com.prestashop.tests;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;
import com.github.javafaker.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PositiveScenarios {
	
	WebDriver driver;
	
	@BeforeMethod
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("http://automationpractice.com/");
		driver.findElement(By.linkText("Sign in".trim())).click();
	}
	
	@Test
	public void loginTest() {
		Faker faker = new Faker();
		String email = faker.internet().emailAddress();
		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();
		String password = faker.internet().password();
		String addressStreet = faker.address().streetAddress();
		String addressCity = faker.address().city();
		String zipCode = faker.address().zipCode();
		String mobilePhone = faker.phoneNumber().cellPhone();
		
		
		driver.findElement(By.id("email_create")).sendKeys(email);
		driver.findElement(By.id("SubmitCreate")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		driver.findElement(By.id("customer_firstname")).sendKeys(firstName);
		driver.findElement(By.id("customer_lastname")).sendKeys(lastName);
		driver.findElement(By.id("passwd")).sendKeys(password);
		
		driver.findElement(By.id("firstname")).sendKeys(firstName);
		driver.findElement(By.id("lastname")).sendKeys(lastName);
		
		driver.findElement(By.id("address1")).sendKeys(addressStreet);
		driver.findElement(By.id("city")).sendKeys(addressCity);
		
		WebElement selectState = driver.findElement(By.id("id_state"));
		Select list = new Select(selectState);
		List<WebElement> options = list.getOptions();
		int rn = ((int)(Math.random() * ((options.size() - 1) + 1)) + 1);
		list.selectByIndex(rn);
		
		driver.findElement(By.id("postcode")).sendKeys(zipCode.substring(0,5));
		driver.findElement(By.id("phone_mobile")).sendKeys(mobilePhone);
		driver.findElement(By.id("submitAccount")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.linkText("Sign out")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.id("passwd")).sendKeys(password);
		driver.findElement(By.id("SubmitLogin")).click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		String actual = driver.findElement(By.cssSelector("a[title='View my customer account']>span")).getText();
		String expected = firstName + " " + lastName;
		Assert.assertTrue(actual.equals(expected));
	}

	@AfterMethod
	public void tearDown() {
		driver.close();
	}
}
