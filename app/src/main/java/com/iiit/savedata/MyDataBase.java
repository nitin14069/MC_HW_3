package com.iiit.savedata;

import android.provider.BaseColumns;

/**
 * Created by nitin on 2/10/2016.
 */
public class MyDataBase {
    private MyDataBase()
    {

    }
    public static class Investments implements BaseColumns
    {
        public static final String TableName = "InvestmentStats";
        public static final String Col1="Name";
        public static final String Col2="Domain";
        public static final String Col3="Amount";
        public static final String Create_db =
                "CREATE TABLE IF NOT EXISTS " + TableName + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                Col1 + " TEXT," +
                Col2 + " TEXT," + Col3 + " TEXT )" ;
        public static final String Delete_db =
                "DROP TABLE IF EXISTS " + TableName;

    }
}
