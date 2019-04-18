package com.example.calculations;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import model.FileController;
import model.MyArrayAdapter;
import model.Result;

public class DisplayResultsActivity extends AppCompatActivity implements View.OnClickListener {
TextView textView, textViewStatistic;
int correctCounter=0;
float correctPercent;
int failCounter = 0;
int totalElapsedTime = 0;
    String summary;
    ArrayList<Result> results;
    MyArrayAdapter resultsAdapter;
    ListView resulsListView;
    Button btnSave, btnEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_results);

        //textView = findViewById(R.id.textViewResults);

        initialize();

    }

    public void initialize(){
        textViewStatistic = findViewById(R.id.textViewStatistics);
        btnSave = findViewById(R.id.btnSaveToFile);
        btnSave.setOnClickListener(this);
        btnEmail = findViewById(R.id.btnEmail);
        btnEmail.setOnClickListener(this);
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
        summary = "Summary :\n"+
                "Total questions : "+ results.size() +"\n" +
                "Total answred questions : "+ (results.size()- failCounter)+ "\n" +
                "Total Duration: " + ((results.size()*10)) + " Seconds "+"\n"+
                "Total elapsed time : " + totalElapsedTime + " Second "+"\n" +
                "% correct answer : " + Math.round(correctPercent)+"%\n" +
                "% wrong answer : " + (100-Math.round(correctPercent))+"%\n" +
                "Velocity : " + ((double)totalElapsedTime / (results.size()*10)); //Total elapsed time/Total duration
        textViewStatistic.setText(summary);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnSaveToFile:
                String fileTitle = "========================= Game Result ========================= \n";
                Context ctx = getApplicationContext();
                String resultLine = "";
                FileController fileController = new FileController();

                fileController.writeFile(ctx, "game_result.txt", fileTitle);
                for(int i = 0; i<results.size();i++){
                    resultLine = results.get(i).toString();
                    fileController.appendFile(ctx,"game_result.txt",resultLine);
                }
                fileController.appendFile(ctx,"game_result.txt",summary);
                    break;
            case R.id.btnEmail:
                Toast.makeText(this,"email clicked", Toast.LENGTH_SHORT).show();

                Intent myIntent = new Intent(Intent.ACTION_SEND);

                PackageManager pm = getPackageManager();
                Intent tempIntent = new Intent(Intent.ACTION_SEND);
                tempIntent.setType("*/*");
                List<ResolveInfo> resInfo = pm.queryIntentActivities(tempIntent, 0);
                for (int i = 0; i < resInfo.size(); i++) {
                    ResolveInfo ri = resInfo.get(i);
                    if (ri.activityInfo.packageName.contains("android.gm")) {
                        myIntent.setComponent(new ComponentName(ri.activityInfo.packageName, ri.activityInfo.name));
                        myIntent.setAction(Intent.ACTION_SEND);
                        myIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"exampleto@gmail.com"});
                        myIntent.setType("message/rfc822");
                        myIntent.putExtra(Intent.EXTRA_TEXT, "message body");
                        myIntent.putExtra(Intent.EXTRA_SUBJECT, "subject");
                        myIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("game_result.txt"));
                    }
                }
                startActivity(myIntent);
                break;
        }



    }
}
