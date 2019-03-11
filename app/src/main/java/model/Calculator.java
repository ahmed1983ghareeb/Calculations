package model;

import android.widget.Toast;

import com.example.calculations.MainActivity;

import java.util.Random;

public class Calculator {

     private String operator;
     private String operators[] = {"+","-","*","/"};
     private int num1, num2, result;

    public  String generateQuestion(String dificulity){
        Random random = new Random();
        switch (dificulity){
            case "Easy":
                //do while loop to guarantee we don't get a division by 0
                do {
                num1 = random.nextInt(9);
                num2 = random.nextInt(9);
                operator = operators[random.nextInt(4)];
            }while(operator.equals("/")&&num2==0.0);
                break;
            case "Hard":
                do {
                    num1 = random.nextInt(100);
                    num2 = random.nextInt(100);
                    operator = operators[random.nextInt(4)];
                }while(operator.equals("/")&&num2==0.0);
                break;
        }
        return num1 + operator + num2;
    }

    public  double calculate()
    {
        switch (operator){
            case "+":
                result = num1+num2;
                break;
            case "-":
                result = num1-num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                result = num1/num2;
                break;
        }
        return result;
    }

    public  boolean validate(double result){
        double userResult;

        userResult = result;

        if (userResult == calculate())
            return true;

        return false;
    }

    public String getOperator() {
        return operator;
    }

    public double getNum1() {
        return num1;
    }

    public double getNum2() {
        return num2;
    }

    public double getResult() {
        return result;
    }


}
