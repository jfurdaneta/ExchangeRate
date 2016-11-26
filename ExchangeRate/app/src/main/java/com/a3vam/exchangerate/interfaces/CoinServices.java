package com.a3vam.exchangerate.interfaces;

import com.a3vam.exchangerate.models.Coin;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author Jose Urdaneta
 * @version 1.0
 * @date today
 */

public interface CoinServices {
    @GET("/latest?base=USD")
    Call<Coin> getCoin();
    //void getCoin(Callback<Coin> callback);
}
