package com.example.calculations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextResult;
    int num1,num2, result;
    String operator;
    String operators[] = {"+","-","*","/"};
    TextView textViewQuestion;
    private int numberBtns[] = {    R.id.btn0,R.id.btn1,R.id.btn2,
                                    R.id.btn3,R.id.btn4,R.id.btn5,
                                    R.id.btn6,R.id.btn7,R.id.btn8,
                                    R.id.btn9,R.id.btnPoint,R.id.btnNegative,
                                    R.id.btnClear,R.id.btnGenerate,R.id.btnQuit,
                                    R.id.btnResult,R.id.btnShowAll};
    Button[] btns = new Button[17];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    public void initialize(){
        editTextResult = findViewById(R.id.editTextResult);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        for (int i = 0; i < numberBtns.length; i++){
            btns[i] = findViewById(numberBtns[i]);
            btns[i].setOnClickListener(this);
        }//end of for
    }//end of initialize()

    public void generateQuestion(){
        Random random = new Random();
        num1 = random.nextInt(100);
        num2 = random.nextInt(100);
        operator = operators[random.nextInt(4)];
        textViewQuestion.setText(num1 +operator+ num2);
    }
public int calculate()
{
    switch (operator){
        case "+":
            result = num1+num2;
            break;
        case "-":
            result = num1-num2;
        case "*":
            result = num1*num2;
        case "/":
            result = num1/num2;
    }
    return result;
}

public void validate(){
        int userResult;
        userResult = Integer.valueOf(editTextResult.getText().toString());
        if (userResult == calculate())
            Toast.makeText(this,"CORRECT!",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"WRONG!",Toast.LENGTH_SHORT).show();
}
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGenerate:
                generateQuestion();
                break;
            case R.id.btnResult:
                validate();
                break;
        }

    }
}//end of class MainActivity
