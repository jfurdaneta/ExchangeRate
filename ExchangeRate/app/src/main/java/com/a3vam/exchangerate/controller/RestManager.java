package com.a3vam.exchangerate.controller;

import com.a3vam.exchangerate.helper.Constants;
import com.a3vam.exchangerate.interfaces.CoinServices;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Jose Urdaneta
 * @version 1.0
 * @date today
 */

public class RestManager {

    private CoinServices coinServices;

    public CoinServices getCoinServices(){

        if (coinServices == null){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            coinServices = retrofit.create(CoinServices.class);
        }

        return  coinServices;
    }
}
