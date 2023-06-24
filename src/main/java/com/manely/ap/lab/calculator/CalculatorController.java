package com.manely.ap.lab.calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.text.NumberFormat;
import java.text.ParseException;

public class CalculatorController {
    private final CalculatorModel model = new CalculatorModel();

    private Double displayValue = 0.0;
    private long fractionLevel = 10;
    private boolean isFraction = false;

    private final NumberFormat formatter = NumberFormat.getInstance();

    @FXML
    private GridPane root;

    @FXML
    private TextField displayTextField;


    public void initialize() {
        root.setStyle("-fx-font-family: 'Apple Braille'");
        displayTextField.setText(formatter.format(0));
    }

    private void reset() {
        displayValue = null;
        isFraction = false;
        fractionLevel = 10;
    }

    private void onError() {
        displayTextField.setText("Error");
        model.reset();
        reset();
    }

    private void calculate() {
        if (!model.calculate(displayValue)) {
            onError();
        }
        else {
            reset();
            displayTextField.setText(formatter.format(model.getAccumulator()));
        }
    }

    @FXML
    void ACButtonPressed() {
        model.reset();
        reset();
        displayTextField.setText(formatter.format(0));
    }

    @FXML
    void operatorButtonPressed(ActionEvent event) {
        if (displayValue != null) {

            if (model.getOperator() != null) {
                calculate();
            }
            else {
                model.setAccumulator(displayValue);
                reset();
            }

        }
        model.setOperator(((Button) event.getSource()).getText());

    }

    @FXML
    void dotButtonPressed() {
        if (!isFraction) {
            if (displayValue == null) {
                displayValue = 0.0;
                displayTextField.setText(formatter.format(displayValue));
            }
            displayTextField.appendText(".");
            isFraction = true;
        }
    }

    @FXML
    void digitButtonPressed(ActionEvent event) {
        double digit;

        try {
            digit = formatter.parse(((Button) event.getSource()).getText()).doubleValue();
        }
        catch (ParseException e) {
            onError();
            return;
        }

        if (isFraction) {
            if (displayValue == null) {
                displayValue = 0.0;
                displayTextField.setText(formatter.format(displayValue));
            }
            if (digit == 0) {
                displayTextField.appendText(formatter.format(0));
            }
            else {
                displayValue += digit / fractionLevel;
                displayTextField.setText(formatter.format(displayValue));
            }
            fractionLevel *= 10;
        }
        else {
            if (displayValue == null) {
                displayValue = digit;
            }
            else {
                displayValue = displayValue * 10 + digit;
            }
            displayTextField.setText(formatter.format(displayValue));
        }

    }

    @FXML
    void keyPressed(KeyEvent event) {

    }

}
