
package com.a3vam.exchangerate.models;

public class Coin {

    private String base;
    private String date;
    private Rates rates;

    /**
     * 
     * @return
     *     The base
     */
    public String getBase() {
        return base;
    }

    /**
     * 
     * @param base
     *     The base
     */
    public void setBase(String base) {
        this.base = base;
    }

    public Coin withBase(String base) {
        this.base = base;
        return this;
    }

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    public Coin withDate(String date) {
        this.date = date;
        return this;
    }

    /**
     * 
     * @return
     *     The rates
     */
    public Rates getRates() {
        return rates;
    }

    /**
     * 
     * @param rates
     *     The rates
     */
    public void setRates(Rates rates) {
        this.rates = rates;
    }

    public Coin withRates(Rates rates) {
        this.rates = rates;
        return this;
    }


}
