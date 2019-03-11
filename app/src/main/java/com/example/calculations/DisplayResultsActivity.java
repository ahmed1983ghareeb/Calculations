package com.example.calculations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import model.Result;

public class DisplayResultsActivity extends AppCompatActivity {
TextView textView, textViewStatistic;
int correctCounter=0;
float correctPercent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_results);
        textView = findViewById(R.id.textViewResults);
        textViewStatistic = findViewById(R.id.textViewStatistics);

        ArrayList<Result> results = (ArrayList<Result>) getIntent().getExtras().getSerializable("tag1");
        for (int i = 0; i < results.size(); i++) {
            textView.append(results.get(i).toString());
            if (results.get(i).getCorrect_wrong().equals("Correct!"))
                correctCounter++;
        }
        textView.append("correct answers = "+ correctCounter+"\nWrong answers = "+ (results.size()-correctCounter));
        correctPercent = (float) correctCounter/results.size() *100;
        textViewStatistic.setText(Math.round(correctPercent)+"% correct answers \n"+
                (100-Math.round(correctPercent))+"% Wrong answers");
    }

}
