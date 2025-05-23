package tests;

import com.gmailtest.pages.BasePage;
import com.gmailtest.pages.LoginPage;
import com.gmailtest.pages.MailPage;
import com.gmailtest.pages.TrashPage;
import com.gmailtest.utils.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    protected WebDriver driver;
    protected BasePage basePage;
    protected LoginPage loginPage;
    protected MailPage mailPage;
    protected TrashPage trashPage;
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
        trashPage = new TrashPage();
        System.out.println("Working dir: " + System.getProperty("user.dir"));
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        Logger.info("Shutting down WebDriver and closing log file...");
        Logger.shutdown();
        driver.quit();
    }
}
