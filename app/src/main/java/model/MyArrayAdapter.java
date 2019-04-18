package model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.calculations.R;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<Result> {

    private Context context;
    int resource;

    public MyArrayAdapter( Context context, int resource, List<Result> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String question = getItem(position).getQuestion();
        String answer = String.valueOf(getItem(position).getResult());
        String elapsetime = String.valueOf(getItem(position).getTimeElapsed()) + " sec";
        String status = getItem(position).getCorrect_wrong();


        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource,parent,false);

        TextView textView_question = (TextView) convertView.findViewById(R.id.textViewListOperation);
        TextView textView_answer = (TextView) convertView.findViewById(R.id.textViewListAnswer);
        TextView textView_time = (TextView) convertView.findViewById(R.id.textViewListTime);
        TextView textView_status = (TextView) convertView.findViewById(R.id.textViewListStatus);

        textView_question.setText(question);
        textView_answer.setText(answer);
        textView_time.setText(elapsetime);
        textView_status.setText(status);

        return convertView;


    }
}
