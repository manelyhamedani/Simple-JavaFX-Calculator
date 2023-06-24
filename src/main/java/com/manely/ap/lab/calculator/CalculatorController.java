package com.manely.ap.lab.calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;

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

    private void operate(String pendingOperator) {
        if (displayValue != null) {

            if (model.getOperator() != null) {
                calculate();
            }
            else {
                model.setAccumulator(displayValue);
                reset();
            }

        }

        model.setOperator(pendingOperator);

    }

    @FXML
    void ACButtonPressed() {
        model.reset();
        reset();
        displayTextField.setText(formatter.format(0));
    }

    @FXML
    void operatorButtonPressed(ActionEvent event) {
        operate(((Button) event.getSource()).getText());
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

    private void changeDisplayValue(double digit) {
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
    void digitButtonPressed(ActionEvent event) {
        double digit;

        try {
            digit = formatter.parse(((Button) event.getSource()).getText()).doubleValue();
        }
        catch (ParseException e) {
            onError();
            return;
        }

        changeDisplayValue(digit);
    }

    private ArrayList<KeyCode> pressedKeys = new ArrayList<>();

    @FXML
    void keyPressed(KeyEvent event) {
        pressedKeys.add(event.getCode());
    }

    @FXML
    void keyReleased() {
        KeyCode keyCode;
        if (pressedKeys.size() == 0) {
            return;
        }
        if (pressedKeys.size() == 1) {
            keyCode = pressedKeys.get(0);
        }
        else if (pressedKeys.size() == 2) {
            if (pressedKeys.get(0).equals(KeyCode.SHIFT) && pressedKeys.get(1).equals(KeyCode.EQUALS)) {
                keyCode = KeyCode.PLUS;
            }
            else {
                onError();
                return;
            }
        }
        else {
            onError();
            return;
        }

        switch (keyCode) {
            case SLASH:
                operate("/");
                break;
            case X:
                operate("x");
                break;
            case PLUS:
                operate("+");
                break;
            case MINUS:
                operate("-");
                break;
            case EQUALS:
                operate("=");
                break;
            case PERIOD:
                dotButtonPressed();
                break;
            case BACK_SPACE:
                ACButtonPressed();
                return;
            default:
                try {
                    double digit = formatter.parse(keyCode.getChar()).doubleValue();
                    changeDisplayValue(digit);
                }
                catch (ParseException e) {
                    onError();
                }
        }
        pressedKeys.clear();

    }

}
