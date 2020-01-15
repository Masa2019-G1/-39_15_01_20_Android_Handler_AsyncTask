package com.telran.a15_01_20_cw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ProgressBar myProgress, horProgress;
    TextView resultTxt, progressTxt;
    Button doBtn;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myProgress = findViewById(R.id.myProgress);
        horProgress = findViewById(R.id.horProgress);
        resultTxt = findViewById(R.id.resultTxt);
        progressTxt = findViewById(R.id.progressTxt);
        doBtn = findViewById(R.id.clickBtn);

        doBtn.setOnClickListener(this);
        handler = new Handler(msg -> {
            switch (msg.what) {
                case Worker.START:
                    resultTxt.setVisibility(View.GONE);
                    myProgress.setVisibility(View.VISIBLE);
                    horProgress.setVisibility(View.VISIBLE);
                    doBtn.setEnabled(false);
                    return true;
                case Worker.END:
                    resultTxt.setVisibility(View.VISIBLE);
                    horProgress.setVisibility(View.GONE);
                    myProgress.setVisibility(View.GONE);
                    doBtn.setEnabled(true);
                    return true;
                case Worker.PROGRESS:
                    horProgress.setProgress(msg.arg1);
                    return true;
            }
            return false;
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.clickBtn) {
//            Worker worker = new Worker(handler);
//            new Thread(worker).start();
            WorkerTask task = new WorkerTask();
            task.execute(1000,100);
        }
    }


    class WorkerTask extends AsyncTask<Integer,Integer,String>{

        @Override
        protected void onPreExecute() {
            myProgress.setVisibility(View.VISIBLE);
            horProgress.setVisibility(View.VISIBLE);
            horProgress.setMax(1000);
            progressTxt.setVisibility(View.VISIBLE);
            doBtn.setEnabled(false);
            resultTxt.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            horProgress.setProgress(values[0]);
            progressTxt.setText(values[0] / 10.0 + " %");
        }

        @Override
        protected String doInBackground(Integer... args) {
            for (int i = 0; i < args[0]; i++) {
                Log.d("MY_TAG", "doInBackground: " + i);
                publishProgress(i,56);
                try {
                    Thread.sleep(args[1]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Finish";
        }

        @Override
        protected void onPostExecute(String str) {
            myProgress.setVisibility(View.GONE);
            horProgress.setVisibility(View.GONE);
            resultTxt.setVisibility(View.VISIBLE);
            resultTxt.setText(str);
            progressTxt.setVisibility(View.GONE);
            doBtn.setEnabled(true);
        }
    }
}
