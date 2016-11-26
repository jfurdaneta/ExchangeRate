package com.a3vam.exchangerate;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a3vam.exchangerate.controller.RestManager;
import com.a3vam.exchangerate.data.RateDbHelper;
import com.a3vam.exchangerate.data.RatesContract;
import com.a3vam.exchangerate.models.Coin;
import com.a3vam.exchangerate.models.Rates;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    private RestManager restManager;
    private RateDbHelper db;
    ProgressDialog progress;
    private Holder vh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = this.getActionBar();
        actionBar.hide();
        final EditText etAmount = (EditText) this.findViewById(R.id.etAmount);
        Button ivCalculate = (Button) this.findViewById(R.id.ivCalculate);
        vh = new Holder();
        final ImageButton ibGbp = (ImageButton)(this.findViewById(R.id.ibGbp));
        final ImageButton ibEur=(ImageButton)(this.findViewById(R.id.ibEur));
        final ImageButton ibJpy=(ImageButton)(this.findViewById(R.id.ibJpy));
        final ImageButton ibBrl=(ImageButton)(this.findViewById(R.id.ibBrl));

        final View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (view==ibGbp){
                    loadIntent("GBP");
               }else if (view==ibEur){
                   loadIntent("EUR");
               }else if (view==ibBrl){
                   loadIntent("BRL");
               }else if (view==ibJpy){
                   loadIntent("JPY");
               }
            }
        };
        ibGbp.setOnClickListener(clickListener);
        ibEur.setOnClickListener(clickListener);
        ibJpy.setOnClickListener(clickListener);
        ibBrl.setOnClickListener(clickListener);
        db = new RateDbHelper(this);
        ivCalculate.setClickable(true);
        progress = new ProgressDialog(this);
        progress.setMessage("Calculating info, please wait...");

        ivCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if (isOnline()){ // offline not available for now
                if (true){
                    restManager = new RestManager();
                    Call<Coin> coinCall = restManager.getCoinServices().getCoin();
                    progress.show();
                    coinCall.enqueue(new Callback<Coin>() {
                        @Override
                        public void onResponse(Call<Coin> call, Response<Coin> response) {
                            if (response.isSuccessful()) {
                                Rates rates = response.body().getRates();vh.getTvGBP().setText(String.valueOf(rates.getGBP() * Double.parseDouble(etAmount.getText().toString())));
                                ContentValues val = new ContentValues();

                                val = rates.toContentValues(response.body().getDate(),"BRL");
                                db.RateInsert(val);
                                val = rates.toContentValues(response.body().getDate(),"GBP");
                                db.RateInsert(val);
                                val = rates.toContentValues(response.body().getDate(),"JPY");
                                db.RateInsert(val);
                                val = rates.toContentValues(response.body().getDate(),"EUR");
                                db.RateInsert(val);

                                vh.getTvGBP().setText(String.format("%.2f",(rates.getGBP() * Double.parseDouble(etAmount.getText().toString()))));
                                vh.getTvEUR().setText(String.format("%.2f",(rates.getEUR() * Double.parseDouble(etAmount.getText().toString()))));
                                vh.getTvJPY().setText(String.format("%.2f",(rates.getJPY() * Double.parseDouble(etAmount.getText().toString()))));
                                vh.getTvBRL().setText(String.format("%.2f",(rates.getBRL() * Double.parseDouble(etAmount.getText().toString()))));
                                vh.getTlResults().setVisibility(View.VISIBLE);
                                progress.dismiss();
                            } else {
                                progress.dismiss();
                                int stateCode = response.code();
                                switch (stateCode) {

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Coin> call, Throwable t) {
                            Log.w("error", t.getMessage());
                            progress.dismiss();
                            Toast.makeText(getApplicationContext(), "Service Error", Toast.LENGTH_LONG);
                        }
                    });
                }else{
                    vh.getTvOffline().setVisibility(View.VISIBLE);
                    progress.show();
                    Rates rates = new Rates();
                    ContentValues BRL = getOfflineData("BRL");
                    ContentValues GBP = getOfflineData("GBP");
                    ContentValues JPY = getOfflineData("JPY");
                    ContentValues EUR = getOfflineData("EUR");
                    String date = "";
                    String value = "";

                    vh.getTvEUR().setText(String.format("%.2f",(Double.parseDouble(EUR.get("value").toString()) * Double.parseDouble(etAmount.getText().toString()))));
                    vh.getTvJPY().setText(String.format("%.2f",(Double.parseDouble(JPY.get("value").toString()) * Double.parseDouble(etAmount.getText().toString()))));
                    vh.getTvBRL().setText(String.format("%.2f",(Double.parseDouble(BRL.get("value").toString()) * Double.parseDouble(etAmount.getText().toString()))));
                    vh.getTvGBP().setText(String.format("%.2f",(Double.parseDouble(GBP.get("value").toString()) * Double.parseDouble(etAmount.getText().toString())))) ;
                    vh.getTlResults().setVisibility(View.VISIBLE);
                    vh.getTvOffline().setText(vh.getTvOffline().getText() + " " + EUR.get("date").toString());
                }

            }
        });


    }
private ContentValues getOfflineData(String curr){
    Cursor c = db.RateSelectCursorTop(curr);
    String date = "";
    String value = "";
    ContentValues cont = new ContentValues();
    if (c != null && c.getCount() > 0) {

        while (c.moveToNext()) {
            date = c.getString(c.getColumnIndex(RatesContract.RatesEntry.date));
            value = c.getString(c.getColumnIndex(RatesContract.RatesEntry.value));
            cont.put(date, value);
        }
    }
    return (cont );
}
    private void loadIntent(String currency){
        Log.w("Clicked", currency);
        Toast.makeText(this, "Clicked: "+ currency, Toast.LENGTH_LONG);
        Intent intent = new Intent(getBaseContext(), graphActivity.class);
        intent.putExtra("CURRENCY", currency);
        startActivity(intent);
    }
    private class Holder{
        TextView tvGBP;
        TextView tvEUR;
        TextView tvJPY;
        TextView tvBRL;
        TextView tvOffline;
        TableLayout tlResults;

        public Holder() {
            tvGBP = (TextView) findViewById(R.id.tvAmountGBP);
            tvEUR = (TextView) findViewById(R.id.tvAmountEUR);
            tvJPY = (TextView) findViewById(R.id.tvAmountJPY);
            tvBRL = (TextView) findViewById(R.id.tvAmountBRL);
            tlResults = (TableLayout)  findViewById(R.id.tlResults);
            tvOffline = (TextView) findViewById(R.id.tvOffline);

        }

        public TextView getTvOffline() {
            return tvOffline;
        }
        public TableLayout getTlResults() {
            return tlResults;
        }
        public TextView getTvGBP() {
            return tvGBP;
        }

        public TextView getTvEUR() {
            return tvEUR;
        }

        public TextView getTvJPY() {
            return tvJPY;
        }

        public TextView getTvBRL() {
            return tvBRL;
        }
    }


    protected boolean isOnline() {
        ConnectivityManager connec = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED || connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING  ) {
            return true   ;

        }
        return false;
    }
}
