package kr.luxsoft.example.asynctaskexample;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";

    ListView list;
    private DataAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        list = new ListView(this);
        setContentView(list);
        adapter = new DataAdapter(new ArrayList<String>());
        list.setAdapter(adapter);

    }


    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    void loadData() {
        Log.d(TAG, "loadData():");
        AsyncTask<Integer, Integer, ArrayList<String>> taskLoader = new AsyncTask<Integer, Integer, ArrayList<String>>() {


            @Override
            protected ArrayList<String> doInBackground(Integer... data) {
                Log.d(TAG, "taskLoader.doInBackground():" + data[0].toString());
                ArrayList<String> result = new ArrayList<String>();
                for (int i = 0; i < 10000; ++i) {
                    if (i % 1000 == 0) {
                        publishProgress(i);
                        result.add(String.format("item %05d", i));
                    }
                }
                return result;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);

                Log.d(TAG, "taskLoader.onProgressUpdate():" + values[0]);

            }

            @Override
            protected void onPostExecute(ArrayList<String> result) {
                super.onPostExecute(result);
                Log.d(TAG, "taskLoader.onPostExecute():" + result.toString());


                updateData(result);
            }
        };

        taskLoader.execute(0);
    }

    private void updateData(ArrayList<String> result) {
        adapter.setData(result);
        adapter.notifyDataSetChanged();
    }

    Context getContext() {
        return this;
    }


    class DataAdapter extends BaseAdapter {
        ArrayList<String> data = new ArrayList<String>();


        public DataAdapter(ArrayList<String> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            TextView tv = new TextView(getContext());
            tv.setText(data.get(i));

            return tv;
        }

        public void setData(ArrayList<String> data) {
            this.data = data;
        }

        public void add(String item) {
            data.add(item);
        }

    }

}