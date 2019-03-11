package com.example.calculations;

import android.content.Intent;
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
    TextView textViewQuestion;
    Spinner difficultySpinner;
    Calculator calculator = new Calculator();
    ArrayList<Result> results = new ArrayList<Result>();

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
        /*
        a method to initialize all the variables and objects
         */
        editTextResult = findViewById(R.id.editTextResult);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        difficultySpinner = findViewById(R.id.dificulitySpinner);
        //the for loop initialize all the buttons at once
        for (int i = 0; i < numberBtns.length; i++){
            btns[i] = findViewById(numberBtns[i]);
            btns[i].setOnClickListener(this);
        }//end of for
    }//end of initialize()

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGenerate:
                //generate the question according to the difficulty level selected in the spinner
                textViewQuestion.setText(calculator.generateQuestion(difficultySpinner.getSelectedItem().toString()));
                editTextResult.setText("");
                break;
            case R.id.btnResult:
                checkResult();
                break;
            case R.id.btnShowAll:
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
            case R.id.btnQuit:
                finish();
        }

    }//end of onClick()

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
                                editTextResult.getText().toString(), "Correct!"));
                    } else {
                        Toast.makeText(this, "WRONG! correct answer is: " + String.valueOf(calculator.getResult()), Toast.LENGTH_SHORT).show();
                        results.add(new Result(calculator.getNum1(), calculator.getNum2(), calculator.getOperator(),
                                editTextResult.getText().toString(), "WRONG! correct answer is: " + String.valueOf(calculator.getResult())));
                    }
                } else
                    Toast.makeText(this, "please input your answer", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Only numbers are accepted", Toast.LENGTH_SHORT).show();
                editTextResult.setText("");
            }
        }
    }//end of checkResult()


}//end of class MainActivity
