package com.example.calculations;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import model.Calculator;
import model.Result;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    EditText editTextResult;
    TextView textViewQuestion, textViewTimer;
    Spinner difficultySpinner;
    Calculator calculator = new Calculator();
    ArrayList<Result> results = new ArrayList<Result>();

    boolean resultChecked = false;

    private int numberBtns[] = {    R.id.btn0,R.id.btn1,R.id.btn2,
                                    R.id.btn3,R.id.btn4,R.id.btn5,
                                    R.id.btn6,R.id.btn7,R.id.btn8,
                                    R.id.btn9,R.id.btnPoint,R.id.btnNegative,
                                    R.id.btnClear,R.id.btnStart,R.id.btnQuit,
                                    R.id.btnResult,R.id.btnShowAll,R.id.btnStop};
    Button[] btns = new Button[18];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    public void initialize(){
        /*
        a method to initialize all the variables and objects
         */
        textViewTimer = findViewById(R.id.textViewTimer);
        editTextResult = findViewById(R.id.editTextResult);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        difficultySpinner = findViewById(R.id.dificulitySpinner);
        //the for loop initialize all the buttons at once
        for (int i = 0; i < numberBtns.length; i++){
            btns[i] = findViewById(numberBtns[i]);
            btns[i].setOnClickListener(this);
        }//end of for
        btns[16].setEnabled(false);
    }//end of initialize()

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStart:
                startGame();
                break;
            case R.id.btnStop:
                stopGame();
                break;
            case R.id.btnResult:
                checkResult();
                break;
            case R.id.btnShowAll:
                stopGame();
                Intent intent = new Intent(this,DisplayResultsActivity.class);
                intent.putExtra("tag1",results);
                startActivity(intent);
                break;

            case R.id.btn0:
                setEditText(0);
                break;
            case R.id.btn1:
                setEditText(1);
                break;
            case R.id.btn2:
                setEditText(2);
                break;
            case R.id.btn3:
                setEditText(3);
                break;
            case R.id.btn4:
                setEditText(4);
                break;
            case R.id.btn5:
                setEditText(5);
                break;
            case R.id.btn6:
                setEditText(6);
                break;
            case R.id.btn7:
                setEditText(7);
                break;
            case R.id.btn8:
                setEditText(8);
                break;
            case R.id.btn9:
                setEditText(9);
                break;
            case R.id.btnNegative:
                if(editTextResult.getText().toString()=="")
                    editTextResult.setText("-");
                else
                    editTextResult.setText("-"+editTextResult.getText());
                break;
            case R.id.btnPoint:
                if(editTextResult.getText().toString()=="")
                    editTextResult.setText(".");
                else
                    editTextResult.append(".");
                break;
            case R.id.btnClear:
                editTextResult.setText("");
                break;
            case R.id.btnQuit:
                finish();
                break;
        }

    }//end of onClick()

    public void startGame(){
        startTimer();
        btns[16].setEnabled(true);
        //generate the question according to the difficulty level selected in the spinner
        textViewQuestion.setText(calculator.generateQuestion(difficultySpinner.getSelectedItem().toString()));
        editTextResult.setText("");
        btns[13].setEnabled(false);
    }

    public void stopGame(){
        countDownTimer.cancel();
        textViewTimer.setText("Click start button");
        btns[13].setEnabled(true);

    }

    public void setEditText(int number){
        /*
        a method to set the text with the corresponding button that the user clicked
        we try to check if the textBox is empty so we add a new number or if it is not we appened the number
         */
        if(editTextResult.getText().toString()=="")
            editTextResult.setText(String.valueOf(number));
        else
            editTextResult.append(String.valueOf(number));
    }//end of setEditText()

    public void checkResult() {
        /*
        this method checks the user answers using the validate method in class Calculator
        and adds an object to the ArrayList results wich will be passed later to the second activity
         */
        //first check if user generated a question or not
        if (textViewQuestion.getText().toString().equals("Click Generate Button to get a question"))
            Toast.makeText(this, "You have to generate a question first", Toast.LENGTH_SHORT).show();
        else {
            //try and catch if the user inputs an invalid text instead of a number
            try {
                //if user clicks check result while the text box is empty he should be warned to enter something
                if (!(editTextResult.getText().toString().equals(""))) {
                    //here we validate the user answer if correct show toast correct else show toast wrong and store the operation in the ArrayList
                    if (calculator.validate(Double.valueOf(editTextResult.getText().toString()))) {
                        Toast.makeText(this, "CORRECT! answer is: " + String.valueOf(calculator.getResult()), Toast.LENGTH_SHORT).show();
                        results.add(new Result(calculator.getNum1(), calculator.getNum2(), calculator.getOperator(),
                                editTextResult.getText().toString(), "Correct!",(9-timer)));
                        resultChecked = true;
                    } else {
                        Toast.makeText(this, "WRONG! correct answer is: " + String.valueOf(calculator.getResult()), Toast.LENGTH_SHORT).show();
                        results.add(new Result(calculator.getNum1(), calculator.getNum2(), calculator.getOperator(),
                                editTextResult.getText().toString(), "Wrong!",(9-timer)));
                        resultChecked = true;
                    }
                } else
                    Toast.makeText(this, "please input your answer", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Only numbers are accepted", Toast.LENGTH_SHORT).show();
                editTextResult.setText("");
            }
        }
    }//end of checkResult()

    ///variables to be used in the countDownTimer method startTimer()
    int interval = 1000;
    int timer = 10;
    CountDownTimer countDownTimer;
    Context context = this;
    private void startTimer(){
        interval = 1000;
        timer = 10;
        //this method is used to make a countDown timer for each question of 10 seconds to answer
        // and it generates a new question and starts again
        countDownTimer = new CountDownTimer(12000,interval) {
            @Override
            public void onTick(long millisUntilFinished) {

                interval = interval - 100;
                textViewTimer.setText("Time left: "+String.valueOf(timer));
                timer = timer - 1;
            }

            @Override
            public void onFinish() {
                if(!resultChecked) {
                    results.add(new Result(calculator.getNum1(), calculator.getNum2(), calculator.getOperator(),
                            "", "Fail!", (9 - timer)));
                    Toast.makeText(context, "Time's up try again " , Toast.LENGTH_SHORT).show();

                }
                //generate the question according to the difficulty level selected in the spinner
                textViewQuestion.setText(calculator.generateQuestion(difficultySpinner.getSelectedItem().toString()));
                editTextResult.setText("");
                interval = 1000;
                timer = 10;
                resultChecked =false;
                startTimer();
            }
        };
        countDownTimer.start();
    }
}//end of class MainActivity
