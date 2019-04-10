package com.example.calculations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import model.MyArrayAdapter;
import model.Result;

public class DisplayResultsActivity extends AppCompatActivity {
TextView textView, textViewStatistic;
int correctCounter=0;
float correctPercent;
int failCounter = 0;
int totalElapsedTime = 0;
    ArrayList<Result> results;
    MyArrayAdapter resultsAdapter;
    ListView resulsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_results);

        //textView = findViewById(R.id.textViewResults);
        textViewStatistic = findViewById(R.id.textViewStatistics);

        results = (ArrayList<Result>) getIntent().getExtras().getSerializable("tag1");
        resultsAdapter = new MyArrayAdapter(this, R.layout.list_items,results);
        resulsListView = findViewById(R.id.resultsListView);
        resulsListView.setAdapter(resultsAdapter);

        for (int i = 0; i < results.size(); i++) {
            //textView.append(results.get(i).toString());
            if (results.get(i).getCorrect_wrong().equals("Correct!"))
                correctCounter++;
            if (results.get(i).getCorrect_wrong().equals("Fail!"))
                failCounter++;
            totalElapsedTime += results.get(i).getTimeElapsed();

        }
       // textView.append("correct answers = "+ correctCounter+"\nWrong answers = "+ (results.size()-correctCounter));
        correctPercent = (float) correctCounter/results.size() *100;
        String summary = "Summary :\n"+
                "Total questions : "+ results.size() +"\n" +
                "Total answed questions : "+ (results.size()- failCounter)+ "\n" +
                "Total Duration: " + (results.size()*10) + " Second "+"\n"+
                "Total elapsed time : " + totalElapsedTime + " Second "+"\n" +
                "% correct answer : " + Math.round(correctPercent)+"\n" +
                "% wrong answer : " + (100-Math.round(correctPercent))+"\n" +
                "Velocity : " + ((double)totalElapsedTime / (results.size()*10)); //Total elapsed time/Total duration
        textViewStatistic.setText(summary);
    }

}
