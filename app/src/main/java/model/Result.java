package model;

import java.io.Serializable;

public class Result implements Serializable {

    private double num1,num2;
    private String operator;
    private String result, correct_wrong;
    private int timeElapsed;



    public Result(double num1, double num2, String operator, String result, String correct_wrong, int time) {
        this.num1 = num1;
        this.num2 = num2;
        this.operator = operator;
        this.result = result;
        this.correct_wrong = correct_wrong;
        this.timeElapsed = time;
    }

    public double getNum1() {
        return num1;
    }

    public double getNum2() {
        return num2;
    }

    public String getOperator() {
        return operator;
    }

    public String getResult() {
        return result;
    }

    public String getCorrect_wrong() {
        return correct_wrong;
    }

    public int getTimeElapsed() {
        return timeElapsed;
    }

    public String getQuestion(){
        return ((int)num1) +
                " " + operator +
                " " + ((int)num2) ;
    }

    @Override
    public String toString() {
        return
                ((int)num1) +
                " " + operator +
                " " + ((int)num2) +
                "   " + result +
                "  " +correct_wrong +
                " time elapsed = "+ timeElapsed + " Seconds." +
                "\n-------------------------------------------------\n";
    }
}
