package session5;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestNG {
	

	WebDriver driver;
	String browser = null;
	String url;
	
	By CustomersField = By.xpath("//ul[@id='side-menu']/li[3]/a/span[1]");
	By AddCustomerFiled = By.xpath("//a[text()='Add Customer']");
	By FullNameField = By.xpath("//input[@id='account']");
	By CompanyField = By.xpath("//select[@id='cid']");
	By EmailField = By.xpath("//input[@id='email']");
	By PhoneField = By.xpath("//input[@id='phone']");
	By CountryField = By.xpath("//select[@name='country']");
	
	@BeforeClass
	public void readConfig() {
		
		try {
			
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
		}catch(IOException e) {
			e.printStackTrace();
			
		}
	}
	
    @BeforeMethod
	public void init() {
    	
    	if(browser.equalsIgnoreCase("Chrome")) {

	     System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
  		 driver = new ChromeDriver();
    	}else  if(browser.equalsIgnoreCase("Firefox")) {

        	System.setProperty("webdriver.gecko.driver", "driver\\geckodriver.exe");
        	driver = new FirefoxDriver();
    	}
		
		driver.manage().deleteAllCookies();
		driver.get("https://techfios.com/billing/?ng=admin/");
		driver.manage().window().maximize();
		
	}
//	@Test(priority = 1)
	public void login() {
		
		driver.findElement(By.id("username")).sendKeys("demo@techfios.com");
		driver.findElement(By.id("password")).sendKeys("abc123");
		driver.findElement(By.name("login")).click();
	String DashbordElement =driver.findElement(By.xpath("//div[@id='page-wrapper']/div[2]/div/h2")).getText();
		
		Assert.assertEquals(DashbordElement, "Dashboard", "page not found");
	}
	@Test
	public void addCustomer() {
		
		login();

       
		generaterandomNumber(999);
		
		driver.findElement(CustomersField).click();
		driver.findElement(AddCustomerFiled).click();
		driver.findElement(FullNameField).sendKeys("selenium");
		selectfromeDropdown(driver.findElement(CompanyField), "Amazon");
		driver.findElement(EmailField).sendKeys(generaterandomNumber(999) + "demo@techfios.com");
		driver.findElement(PhoneField).sendKeys("4555555666" + generaterandomNumber(999));
		selectfromeDropdown(CountryField, "United States");
		
	}
private int generaterandomNumber(int boundaryNum) {
	 Random rnd = new Random();
		int genNum = rnd.nextInt(boundaryNum);
		return genNum;
		
	}

private void selectfromeDropdown(By locator, String visibleText) {
	Select sel = new Select(driver.findElement(locator));
	sel.selectByVisibleText(visibleText);
		
	}

public void selectfromeDropdown(WebElement element, String visibleText) {
		
		Select sel = new Select(element);
		sel.selectByVisibleText(visibleText);
		
		
	}

}
