package tests;

import com.gmailtest.pages.BasePage;
import com.gmailtest.pages.LoginPage;
import com.gmailtest.pages.MailPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    protected WebDriver driver;
    protected BasePage basePage;
    protected LoginPage loginPage;
    protected MailPage mailPage;
    private String url = "https://account.proton.me/mail";

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(url);
        basePage = new BasePage();
        basePage.setDriver(driver);
        loginPage = new LoginPage();
        mailPage = new MailPage();
    }

    @AfterClass
    public void tearDown() {
        // driver.quit();
    }
}
