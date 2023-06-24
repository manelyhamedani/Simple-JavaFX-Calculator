package com.manely.ap.lab.calculator;

public class CalculatorModel {
    private double accumulator;
    private String operator;

    public double getAccumulator() {
        return accumulator;
    }

    public void setAccumulator(double num) {
        accumulator = num;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String op) {
        operator = op;
    }

    public void reset() {
        accumulator = 0;
        operator = null;
    }

    public boolean calculate(double operand) {
        switch (operator) {
            case "/" -> {
                if (operand == 0) {
                    return false;
                }
                else {
                    accumulator /= operand;
                }
            }
            case "x" -> accumulator *= operand;
            case "+" -> accumulator += operand;
            case "-" -> accumulator -= operand;
            case "=" -> accumulator = operand;
        }
        return true;
    }
}
