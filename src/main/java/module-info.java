module am.aua.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens am.aua.demo to javafx.fxml;
    exports am.aua.demo;
}