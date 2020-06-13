package carcheck;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import carcheck.model.CarModel;
import carcheck.util.TestConstants;
import test.ant.TestCommandLineArgs;

public class CarSearchTest {
	WebDriver driver;
	WebDriverWait wait;
	private static long WAIT_TIME = 1000l;
	Map<String, CarModel> outputMap = new HashMap<String, CarModel>();

	@BeforeClass(alwaysRun = true)
	public void setUpClass() throws Exception {

		List<String> allLines;
		Object[][] data = null;

		try {
			Path path = Paths.get(TestConstants.INPUT_FILE_PATH);
			allLines = Files.readAllLines(path);
			for (String string : allLines) {
				String[] expectedCarStr = string.split(",");
				CarModel model = new CarModel();
				model.setCarNum(expectedCarStr[0]);
				model.setMake(expectedCarStr[1]);
				model.setModel(expectedCarStr[2]);
				model.setCarColor(expectedCarStr[3]);
				model.setYear(expectedCarStr[4]);
				outputMap.put(model.getCarNum(), model);

			}
			for (String path2 : outputMap.keySet()) {
				System.out.println("Key is " + outputMap.get(path2));
			}
		} catch (IOException fn) {

		}
	}

	@BeforeMethod
	public void beforeMethod() throws Exception {

		File file = new File(TestConstants.CHROME_DRIVER_PATH);
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		driver = new ChromeDriver();
		driver.get("https://cartaxcheck.co.uk/");

	}

	@AfterMethod
	public void cleanUp() {
		System.out.println("Cleanup called ");
		
		driver.close();
		
	}

	@DataProvider(name = "testData")
	public static Object[][] testData() {
		List<String> carList = new ArrayList<String>();
		String fileName = TestConstants.CAR_INPUT_MODIFIEDFILE;
		Path path = Paths.get(fileName);
		List<String> allLines;
		Object[][] data = null;
		try {
			allLines = Files.readAllLines(path);
			for (String string : allLines) {

				System.out.println(string);
				String carName = string.replace(" ", "");
				carList.add(carName.trim());

			}
			data = new CarModel[carList.size()][1];

			int i = 0;

			for (String s : carList) {
				CarModel car = new CarModel();
				car.setCarNum(s);
				data[i][0] = car;
				i++;
			}
			System.out.println("car list size " + carList.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}

	@Test(dataProvider = "testData")
	public void instanceDbProvider(CarModel car) {
		System.out.println("Reading carnumber " + car.getCarNum());

		driver.findElement(By.id("vrm-input")).sendKeys(car.getCarNum());

		// Click 'Free Car check' Button
		driver.findElement(By.xpath("//button[.='Free Car Check']")).click();
		Boolean isPresent = true;
		WebElement findElement = null;
		try {
			Thread.sleep(2000);
			try {
				findElement = driver.findElement(By.xpath("//a[.='Try Again']"));
			} catch (NoSuchElementException nse) {
				isPresent = false;
			}
			if (findElement == null) {

				System.out.println("Registration Number found on web");
				CarModel carFromSearch = new CarModel();

				// Get the values from Car details from CarTaxCheck
				String registrationNumber = driver
						.findElement(By.xpath("//dt[text()='Registration']/following-sibling::dd")).getText();
				carFromSearch.setCarNum(registrationNumber);
				String carMake = driver.findElement(By.xpath("//dt[text()='Make']/following-sibling::dd")).getText();
				carFromSearch.setMake(carMake);
				String carModel = driver.findElement(By.xpath("//dt[text()='Model']/following-sibling::dd")).getText();
				carFromSearch.setModel(carModel);
				String carColour = driver.findElement(By.xpath("//dt[text()='Colour']/following-sibling::dd")).getText();
				carFromSearch.setCarColor(carColour);
				String carYear = driver.findElement(By.xpath("//dt[text()='Year']/following-sibling::dd")).getText();
				carFromSearch.setYear(carYear);
				// Verify CarTaxCheck values matches with the ouput file
				System.out.println("Web car object " + carFromSearch.toString());
				if (outputMap.containsKey(car.getCarNum())) {
					System.out.println("-- Beginning object comparison ---");
					boolean sameObj = carFromSearch.equals(outputMap.get(car.getCarNum()));
					System.out.println("Object matched " + sameObj);
					org.testng.Assert.assertTrue(sameObj);

				} else {
					System.out.println("Car not matched from output list");
					  org.testng.Assert.fail("Car not matched from output list");
				}

			} else {
				System.out.println("Registration Number not found");
				  org.testng.Assert.fail("Car not found");
			}

		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() throws Exception {
				
	}
}
