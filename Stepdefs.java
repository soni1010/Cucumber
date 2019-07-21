package cucumber.InitialProject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;

public class Stepdefs  {

	public static WebDriver driver;
	String baseURL = "https://www.google.com";
	String baseURL1= "https://www.Yahoo.com";
	String driverPath="E:\\Software\\Drivers\\chrome75driver_win32\\chromedriver.exe";
	List<Integer> numbers;
	Integer sum=new Integer(0);

	@Given("user launches Browser")
	public void user_launches_Browser() {
		try {
			System.setProperty("webdriver.chrome.driver",driverPath);
			ChromeOptions cOptions = new ChromeOptions();
			cOptions.addArguments("disable-infobars");
			cOptions.addArguments("test-type");
			cOptions.addArguments("start-maximized");
			cOptions.addArguments("--js-flags=--expose-gc");  
			cOptions.addArguments("--enable-precise-memory-info"); 
			cOptions.addArguments("--disable-popup-blocking");
			cOptions.addArguments("--disable-default-apps"); 
			driver = new ChromeDriver(cOptions);
			driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
		}catch (Exception e) {
			driver.quit();
			System.out.println(e.getMessage());
			throw e;
		}
	}
	String SearcgEngine;

	@When("user launches {string}")
	public void user_launches(String string) {
		SearcgEngine=string;
		if (string.equalsIgnoreCase("Google")) {
			driver.get(baseURL);
		} else {
			driver.get(baseURL1);
		}
	}

	List<String> searchNames; 
	List<String> answerList = new ArrayList<>();

	@When("user searches various names")
	public void user_searches_various_names(DataTable dataTable) {
		searchNames=dataTable.asList();
		for(int i=0;i<searchNames.size();i++) {
			try {
				if (SearcgEngine.equalsIgnoreCase("Google")) {
					WebElement searchBox = driver.findElement(By.name("q"));
					searchBox.clear();
					searchBox.sendKeys(searchNames.get(i));
					searchBox.sendKeys(Keys.ENTER);
					WebElement result = driver.findElement(By.xpath("(//*[@class='LC20lb'])[1]"));
					if (result.getText().contains(searchNames.get(i))) {
						answerList.add("Matching");
					} else {
						answerList.add("Not Matching");

					}
				}else if (SearcgEngine.equalsIgnoreCase("Yahoo")) {
					WebElement searchBox = driver.findElement(By.name("p"));
					searchBox.clear();
					searchBox.sendKeys(searchNames.get(i));
					searchBox.sendKeys(Keys.ENTER);
					WebElement result = driver.findElement(By.xpath("(//a[contains(text(),'"+searchNames.get(i)+"')])[1]"));
					if (result.getText().contains(searchNames.get(i))) {
						answerList.add("Matching");
					} else {
						answerList.add("Not Matching");

					}
				}

			}catch (Exception e) {
				driver.quit();
				System.out.println(e.getMessage());
				throw e;
			}
		}

	}

	@Then("results retrieved for all Search")
	public void results_retrieved_for_all_Search() {
		for(int i=0;i<answerList.size();i++)
			System.out.println(i+"->"+answerList.get(i));

		driver.quit();
	}
}