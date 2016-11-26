
package com.a3vam.exchangerate.models;


import android.content.ContentValues;

public class Rates {

    private Double GBP;
    private Double EUR;
    private Double JPY;
    private Double BRL;
     /**
     *
     * @return
     *     The BRL
     */
    public Double getBRL() {
        return BRL;
    }

    /**
     *
     * @param BRL
     *     The BRL
     */
    public void setBRL(Double BRL) {
        this.BRL = BRL;
    }

    public Rates withBRL(Double BRL) {
        this.BRL = BRL;
        return this;
    }



    /**
     *
     * @return
     *     The GBP
     */
    public Double getGBP() {
        return GBP;
    }

    /**
     *
     * @param GBP
     *     The GBP
     */
    public void setGBP(Double GBP) {
        this.GBP = GBP;
    }

    public Rates withGBP(Double GBP) {
        this.GBP = GBP;
        return this;
    }


    /**
     *
     * @return
     *     The JPY
     */
    public Double getJPY() {
        return JPY;
    }

    /**
     *
     * @param JPY
     *     The JPY
     */
    public void setJPY(Double JPY) {
        this.JPY = JPY;
    }

    public Rates withJPY(Double JPY) {
        this.JPY = JPY;
        return this;
    }


    /**
     *
     * @return
     *     The EUR
     */
    public Double getEUR() {
        return EUR;
    }

    /**
     *
     * @param EUR
     *     The EUR
     */
    public void setEUR(Double EUR) {
        this.EUR = EUR;
    }

    public Rates withEUR(Double EUR) {
        this.EUR = EUR;
        return this;
    }

    public ContentValues toContentValues(String date, String currency){
        ContentValues values = new ContentValues();

        // Pares clave-valor
        values.put("date", date);
        values.put("currency", currency);
        switch (currency){
            case "BRL":
                values.put("value", this.BRL);
                break;
            case "EUR":
                values.put("value", this.EUR);
                break;
            case "JPY":
                values.put("value", this.JPY);
                break;
            case "GBP":
                values.put("value", this.GBP);
                break;
        }

        return values;
    }
}