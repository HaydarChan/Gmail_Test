module com.gmailtest.gmailtests {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.gmailtest.gmailtests to javafx.fxml;
    exports com.gmailtest.gmailtests;
}