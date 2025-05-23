package tests;

import com.gmailtest.utils.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GmailDeletionTest extends BaseTest {

    private String subject;

    /* 
        Test: Login to Proton Mail and verify that the Inbox page is displayed 
    */
    @Test(description = "Verify user can login to Proton successfully")
    public void testLoginToProton() {
        Logger.info("===== Test: Login to Proton =====");
        Logger.info("Starting login test to Proton Mail");

        loginPage.loginAs("qiyachan@proton.me", "qiyachan123");
        Assert.assertTrue(mailPage.isAtInbox(), "Inbox page is not visible, login might have failed.");

        Logger.info("Successfully landed in Inbox");
    }

    /* 
        Test: Verify that the inbox contains at least 5 emails as a prerequisite 
    */
    @Test(dependsOnMethods = "testLoginToProton", description = "Verify email with specific subject exists in Inbox")
    public void testEmailPresenceInInbox() {
        Logger.info("===== Test: Verify email presence in Inbox =====");
        Logger.info("Checking that there are at least 5 emails in the inbox...");

        List<String> subjects = mailPage.getAllInboxEmailSubjects();
        Assert.assertTrue(subjects.size() >= 5, "Expected at least 5 emails, but found: " + subjects.size());

        Logger.info("Found " + subjects.size() + " emails in the inbox.");
    }

    /* 
        Test: Capture the subject of the first email in the inbox for use in the next steps 
    */
    @Test(dependsOnMethods = "testEmailPresenceInInbox", description = "Set target email subject from top of inbox")
    public void testSetEmailSubjectFromInbox() {
        Logger.info("===== Test: Set subject from Inbox =====");
        Logger.info("Grabbing first available email subject from inbox...");

        List<String> subjects = mailPage.getAllInboxEmailSubjects();
        Assert.assertFalse(subjects.isEmpty(), "No email subjects found in inbox.");
        subject = subjects.get(0);

        Logger.info("Selected email subject: " + subject);
    }

    /* 
        Test: Open the email using the previously selected subject 
    */
    @Test(dependsOnMethods = "testSetEmailSubjectFromInbox", description = "Verify email with specific subject can be opened")
    public void testOpenEmailBySubject() {
        Logger.info("===== Test: Open email by subject =====");
        Logger.info("Opening email with subject: " + subject);

        mailPage.openEmailBySubject(subject);
        boolean opened = retry(() -> mailPage.isEmailOpened(subject), 3);
        Assert.assertTrue(opened, "Email with subject '" + subject + "' could not be opened.");
    }

    /* 
        Test: Delete the currently opened email 
    */
    @Test(dependsOnMethods = "testOpenEmailBySubject", description = "Delete the opened email")
    public void testDeleteEmail() {
        Logger.info("===== Test: Delete opened email =====");
        Logger.info("Deleting the opened email...");

        mailPage.deleteOpenedEmail();

        Logger.info("Email deleted, expected to be moved to Trash.");
    }

    /* 
        Test: Navigate to the Trash page and verify it is correctly loaded 
    */
    @Test(dependsOnMethods = "testDeleteEmail", description = "Verify that the current page is Trash")
    public void testAtTrashPage() {
        Logger.info("===== Test: Navigate to Trash =====");
        Logger.info("Navigating to Trash...");
        mailPage.goToTrash();

        boolean atTrash = retry(() -> trashPage.isAtTrash(), 3);
        Assert.assertTrue(atTrash, "Not currently in Trash page.");

        Logger.info("Successfully arrived at Trash page.");
    }

    /* 
        Test: Confirm that the deleted email is now present in the Trash 
    */
    @Test(dependsOnMethods = "testAtTrashPage", description = "Verify email appears in Trash")
    public void testEmailMovedToTrash() {
    Logger.info("===== Test: Email appears in Trash =====");
        Logger.info("Verifying email '" + subject + "' exists in Trash...");

        boolean foundInTrash = retry(() -> trashPage.isEmailInTrash(subject), 3);
        Assert.assertTrue(foundInTrash, "Deleted email was not found in Trash.");

        Logger.info("Email successfully found in Trash.");
    }

    /* 
        Test: Select and delete two emails using checkboxes and confirm they are in the Trash 
    */
    @Test(dependsOnMethods = "testEmailMovedToTrash", description = "Delete two emails using checkbox and verify both appear in Trash")
    public void testDeleteMultipleEmails() {
        Logger.info("===== Test: Delete multiple emails via checkbox =====");
        Logger.info("Navigating back to Inbox...");

        mailPage.goToInbox();
        Assert.assertTrue(mailPage.isAtInbox(), "Not at Inbox page.");

        Logger.info("Retrieving inbox email subjects...");

        List<String> subjects = mailPage.getAllInboxEmailSubjects();
        Assert.assertTrue(subjects.size() >= 3, "Need at least 3 emails to delete 2 more.");

        List<String> targets = subjects.subList(0, 2);

        Logger.info("Selected emails for deletion via checkbox: " + targets);

        mailPage.selectFirstNEmailsAndDelete(2);

        Logger.info("Navigating to Trash...");

        mailPage.goToTrash();
        Assert.assertTrue(trashPage.isAtTrash(), "Failed to navigate to Trash.");

        for (String subj : targets) {
            Logger.info("Verifying presence in Trash: " + subj);
            Assert.assertTrue(trashPage.isEmailInTrash(subj), "Email not found in Trash: " + subj);
        }

        Logger.info("Successfully deleted and verified 2 emails in Trash.");
    }

    /* 
        Test: Move all emails from the Trash back to the Inbox 
    */
    @Test(dependsOnMethods = "testDeleteMultipleEmails", description = "Move all emails in Trash back to Inbox")
    public void testMoveTrashEmailsBackToInbox() {
        Logger.info("===== Test: Move emails from Trash back to Inbox =====");
        Logger.info("Navigating to Trash...");

        mailPage.goToTrash();
        Assert.assertTrue(trashPage.isAtTrash(), "Not at Trash page.");

        trashPage.moveAllTrashEmailsToInbox();

        Logger.info("Navigating to Inbox to verify...");

        mailPage.goToInbox();
        Assert.assertTrue(mailPage.isAtInbox(), "Not at Inbox page.");
        List<String> inboxSubjects = mailPage.getAllInboxEmailSubjects();

        Logger.info("Inbox now contains: " + inboxSubjects.size() + " emails.");

        Assert.assertTrue(inboxSubjects.size() >= 3, "Expected inbox to contain restored emails.");
    }

    /* 
        Test: Move all emails from the Inbox to the Trash, then move them all back to the Inbox 
    */
    @Test(dependsOnMethods = "testMoveTrashEmailsBackToInbox", description = "Move all emails from Inbox to Trash and back to Inbox")
    public void testMoveAllInboxToTrashAndBack() {
        Logger.info("===== Test: Move ALL Inbox ➜ Trash ➜ Inbox =====");
        Logger.info("Navigating to Inbox...");

        mailPage.goToInbox();
        Assert.assertTrue(mailPage.isAtInbox(), "Not at Inbox page.");

        List<String> beforeSubjects = mailPage.getAllInboxEmailSubjects();
        Assert.assertFalse(beforeSubjects.isEmpty(), "No emails in Inbox to test.");
        mailPage.moveAllInboxEmailsToTrash();

        Logger.info("Navigating to Trash...");

        mailPage.goToTrash();
        Assert.assertTrue(trashPage.isAtTrash(), "Not at Trash page.");
        trashPage.moveAllTrashEmailsToInbox();

        Logger.info("Returning to Inbox to verify...");

        mailPage.goToInbox();
        List<String> afterSubjects = mailPage.getAllInboxEmailSubjects();

        boolean allRestored = beforeSubjects.stream().allMatch(afterSubjects::contains);
        Assert.assertTrue(allRestored, "Not all emails were restored to Inbox.");

        Logger.info("All emails successfully restored to Inbox.");
    }

    private boolean retry(Check check, int attempts) {
        for (int i = 0; i < attempts; i++) {
            if (check.run()) return true;
            Logger.warn("Retrying... attempt " + (i + 1));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {}
        }
        return false;
    }

    private interface Check {
        boolean run();
    }
}
