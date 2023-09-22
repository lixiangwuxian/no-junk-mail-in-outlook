module com.lxtend.junkmailcollector {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;


    opens com.lxtend.junkmailcollector to javafx.fxml;
    exports com.lxtend.junkmailcollector;
}