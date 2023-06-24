module com.manely.ap.lab.calculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.manely.ap.lab.calculator to javafx.fxml;
    exports com.manely.ap.lab.calculator;
}