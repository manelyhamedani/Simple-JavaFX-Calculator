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

    private double calculatedNumber;
    private Double currentNumber = 0.0;
    private long fractionLevel = 10;
    private boolean isFraction = false;
    private String operator;
    private final NumberFormat formatter = NumberFormat.getInstance();

    @FXML
    private GridPane root;

    @FXML
    private TextField displayTextField;


    public void initialize() {
        root.setStyle("-fx-font-family: 'Apple Braille'");
        displayTextField.setText(formatter.format(0));
    }

    private void resetMainData() {
        calculatedNumber = 0;
        operator = null;
    }

    private void resetCurrentData() {
        currentNumber = null;
        isFraction = false;
        fractionLevel = 10;
    }

    private void onError() {
        displayTextField.setText("Error");
        resetMainData();
        resetCurrentData();
    }

    private void calculate() {
        switch (operator) {
            case "/" -> {
                if (currentNumber == 0) {
                    onError();
                    return;
                }
                else {
                    calculatedNumber /= currentNumber;
                }
            }
            case "x" -> calculatedNumber *= currentNumber;
            case "+" -> calculatedNumber += currentNumber;
            case "-" -> calculatedNumber -= currentNumber;
            case "=" -> calculatedNumber = currentNumber;
        }

        resetCurrentData();
        displayTextField.setText(formatter.format(calculatedNumber));
    }

    @FXML
    void ACButtonPressed() {
        resetMainData();
        resetCurrentData();
        displayTextField.setText(formatter.format(calculatedNumber));
    }

    @FXML
    void operatorButtonPressed(ActionEvent event) {
        if (operator != null && currentNumber != null) {
            calculate();
        }

        if (currentNumber != null) {
            calculatedNumber = currentNumber;
            resetCurrentData();
        }

        operator = ((Button) event.getSource()).getText();
    }

    @FXML
    void dotButtonPressed() {
        if (!isFraction) {
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
            if (currentNumber == null) {
                currentNumber = 0.0;
                displayTextField.setText(formatter.format(currentNumber));
            }
            if (digit == 0) {
                displayTextField.appendText(formatter.format(0));
            }
            else {
                currentNumber += digit / fractionLevel;
                displayTextField.setText(formatter.format(currentNumber));
            }
            fractionLevel *= 10;
        }
        else {
            if (currentNumber == null) {
                currentNumber = digit;
            }
            else {
                currentNumber = currentNumber * 10 + digit;
            }
            displayTextField.setText(formatter.format(currentNumber));
        }

    }

    @FXML
    void keyPressed(KeyEvent event) {

    }

}
