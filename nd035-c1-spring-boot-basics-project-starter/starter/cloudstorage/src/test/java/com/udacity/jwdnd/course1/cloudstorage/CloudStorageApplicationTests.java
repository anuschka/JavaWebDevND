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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

		//creates a note and verifies it is displayed
		WebElement nav = driver.findElement(By.id("nav-notes-tab"));
		nav.click();
		notePage.addNote(driver, "This is my title", "This is Description",nav);

		driver.findElement(By.id("home-link")).click();

		driver.get(this.baseUrl + "/home");

		List<String> detail = notePage.getDetail(driver);

		Assertions.assertEquals("This is my title", detail.get(0));
		Assertions.assertEquals("This is Description", detail.get(1));

		//edits a note anad verifies the changes are displayed
		notePage.editNote(driver, "Edit title", "Edit Description");

		driver.get("http://localhost:" + this.port +  "/home");

		detail = notePage.getDetail(driver);

		Assertions.assertEquals("Edit title", detail.get(0));
		Assertions.assertEquals("Edit Description", detail.get(1));

		//deletes a note and verifies the note is no longer displayed
		notePage.deleteNote(driver);
		driver.get("http://localhost:" + this.port + "/home");

		wait.until(driver -> driver.findElement(By.id("nav-notes-tab"))).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int noteSize = this.notePage.getNotesSize();
		Assertions.assertEquals(0, noteSize);

	}

}
