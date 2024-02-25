module com.hotmomcircle {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.hotmomcircle to javafx.fxml;
    exports com.hotmomcircle;
}
