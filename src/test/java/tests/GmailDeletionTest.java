package tests;

import org.testng.annotations.Test;

public class GmailDeletionTest extends BaseTest {

    @Test(description = "Verify user can login to Proton successfully")
    public void testLoginToGmail() {
        loginPage.loginAs("qiyachan@proton.me", "qiyachan123");
    }

}
