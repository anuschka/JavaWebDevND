package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private String username = "aUser";
	private String password = "aPassword";
	private String firstName = "Ana";
	private String lastName = "Banana";

	private SignupPage signupPage;
	private LoginPage loginPage;
	private HomePage homePage;
	private NotePage notePage;

	private static WebDriver driver;
	private String baseUrl;

	@BeforeAll
	static void beforeAll() {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@BeforeEach
	public void beforeEach() {

		this.baseUrl = "http://localhost:" + port;
		//this.driver = new ChromeDriver();
		this.loginPage = new LoginPage(driver);
		this.signupPage = new SignupPage(driver);
		this.homePage = new HomePage(driver);
		this.notePage = new NotePage(driver);
	}

	@AfterAll
	public static void afterAll() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void testValidLoginLogout() {
		//unauthenicated access redirects to login page
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

		//unauthenicated access to random page redirects to login page
		driver.get("http://localhost:" + this.port + "/random");
		Assertions.assertEquals("Login", driver.getTitle());

		//get the signup page
		driver.get(baseUrl + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		//signs up a new user
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstName, lastName, username, password);

		//get the login page
		driver.get(baseUrl + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		//login the new user
		loginPage.login(username, password);


		WebDriverWait wait = new WebDriverWait(driver, 10);

		//verifies the home page is accessible
		driver.get(baseUrl +"/home");

		//logout the user
		homePage.logout();

		wait.until(ExpectedConditions.titleContains("Login"));

		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());

		//After logout the home page will redirect to the login page
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

	}


	@Test
	@Order(2)
	public void testNoteFunctions() {
		//get the signup page
		driver.get(baseUrl + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		//signs up a new user
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstName, lastName, username, password);

		//get the login page
		driver.get(baseUrl + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		//login the new user
		loginPage.login(username, password);


		WebDriverWait wait = new WebDriverWait(driver, 10);

		//verifies the home page is accessible
		driver.get(baseUrl +"/home");


		WebElement nav = driver.findElement(By.id("nav-notes-tab"));
		nav.click();
		notePage.addNote(driver, "This is my title", "This is Description",nav);

		driver.findElement(By.id("home-link")).click();

		driver.get(this.baseUrl + "/home");

		List<String> detail = notePage.getDetail(driver);

		Assertions.assertEquals("This is my title", detail.get(0));
		Assertions.assertEquals("This is Description", detail.get(1));

		notePage.editNote(driver, "Edit title", "Edit Description");

		driver.get("http://localhost:" + this.port +  "/home");

		detail = notePage.getDetail(driver);

		Assertions.assertEquals("Edit Description", detail.get(0));
		Assertions.assertEquals("This is Description", detail.get(1));

		notePage.deleteNote(driver);
		driver.get("http://localhost:" + this.port + "/home");

		wait.until(driver -> driver.findElement(By.id("nav-notes-tab"))).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//String noteSize = wait.until(driver -> driver.findElement(By.id("note-size")).getText());
		int noteSize = this.notePage.getNotesSize();
		Assertions.assertEquals(0, noteSize);


	}

}
