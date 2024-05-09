module am.aua.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;

    exports am.aua.demo.ui;
    opens am.aua.demo.ui to javafx.fxml;
    opens am.aua.demo.core to javafx.base;
}