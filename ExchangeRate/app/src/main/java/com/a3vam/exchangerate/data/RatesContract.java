package com.a3vam.exchangerate.data;

import android.provider.BaseColumns;

/**
 * @author Jose Urdaneta
 * @version 1.0
 * @date today
 */

public class RatesContract  implements BaseColumns {

    public static abstract class RatesEntry implements BaseColumns {
        public static final String TABLE_NAME = "coin";
        public static final String date = "date";
        public static final String currency = "currency";
        public static final String value = "value";
    }

}
