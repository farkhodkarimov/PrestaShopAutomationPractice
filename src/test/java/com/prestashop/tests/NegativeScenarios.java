package com.prestashop.tests;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;

public class NegativeScenarios {
	
	WebDriver driver;
	
	@BeforeMethod
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("http://automationpractice.com/");
		driver.findElement(By.linkText("Sign in".trim())).click();
	}
	
	@Test
	public void wrongCredentialsTest() {
		driver.findElement(By.id("email")).sendKeys("wrongemail@gmail.com");
		driver.findElement(By.id("passwd")).sendKeys("wrongemail");
		driver.findElement(By.id("SubmitLogin")).click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		String actual = driver.findElement(By.cssSelector("#center_column ol li")).getText();
		String expected = "Authentication failed.";
		Assert.assertTrue(actual.equals(expected));
	}
	
	@Test
	public void invalidEmailTest() {
		driver.findElement(By.id("email")).sendKeys("wrongemail");
		driver.findElement(By.id("passwd")).sendKeys("wrongemail");
		driver.findElement(By.id("SubmitLogin")).click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		String actual = driver.findElement(By.cssSelector("#center_column ol li")).getText();
		String expected = "Invalid email address.";
		Assert.assertTrue(actual.equals(expected));
	}
	
	@Test
	public void blankEmailTest() {
		driver.findElement(By.id("email")).sendKeys("");
		driver.findElement(By.id("passwd")).sendKeys("wrongemail");
		driver.findElement(By.id("SubmitLogin")).click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		String actual = driver.findElement(By.cssSelector("#center_column ol li")).getText();
		String expected = "An email address required.";
		Assert.assertTrue(actual.equals(expected));
	}
	
	@Test
	public void blankPasswordTest() {
		driver.findElement(By.id("email")).sendKeys("wrongemail@gmail.com");
		driver.findElement(By.id("passwd")).sendKeys("");
		driver.findElement(By.id("SubmitLogin")).click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		String actual = driver.findElement(By.cssSelector("#center_column ol li")).getText();
		String expected = "Password is required.";
		Assert.assertTrue(actual.equals(expected));
	}
	
	@AfterMethod
	public void tearDown() {
		driver.close();
	}
}
