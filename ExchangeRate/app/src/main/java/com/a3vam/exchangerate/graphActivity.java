package com.a3vam.exchangerate;

import android.app.ActionBar;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import com.a3vam.exchangerate.data.RateDbHelper;
import com.a3vam.exchangerate.data.RatesContract;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class graphActivity extends Activity {
    private RateDbHelper db;
    public String currency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        db = new RateDbHelper(this);
        ActionBar actionBar = this.getActionBar();
        actionBar.hide();
        currency = getIntent().getStringExtra("CURRENCY");
        new loadTask().execute();
    }

    private class loadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return db.RateSelectCursor(currency);
        }

        @Override
        protected void onPostExecute(Cursor c) {
            List<Entry> entries = new ArrayList<Entry>();
            int i = 0;
            final ArrayList<String> dates = new ArrayList<String>();

            if (c != null && c.getCount() > 0) {

                while(c.moveToNext()) {

                    String date = c.getString(c.getColumnIndex(RatesContract.RatesEntry.date));
                    String value = c.getString(c.getColumnIndex(RatesContract.RatesEntry.value));
                    Entry entry = new Entry(i, Float.parseFloat(value),date);
                    entries.add(entry);
                    dates.add(date);
                    i++;
                }
                c.close();
                IAxisValueFormatter formatter = new IAxisValueFormatter() {

                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return dates.get((int) value);
                    }

                };


                LineDataSet dataSet = new LineDataSet(entries, currency);
                dataSet.setColor(Color.RED);
                dataSet.setValueTextColor(Color.BLUE);

                LineData lineData = new LineData(dataSet);
                LineChart chart = (LineChart) findViewById(R.id.chart);
                chart.setNoDataText(getString(R.string.chart_nodata));
                chart.setData(lineData);
                Description des = new Description();
                des.setText(getString(R.string.chart_desc));
                des.setTextColor(Color.RED);
                des.setTextSize(12.0f);

                chart.setDescription(des);
                XAxis xAxis = chart.getXAxis();
                xAxis.setGranularity(0.1f);
                xAxis.setGranularityEnabled(true);
                xAxis.setValueFormatter(formatter);
                chart.invalidate(); // refresh
            }
        }



    }

}
