package com.iiit.savedata;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ViewActivity extends Activity {

    EditText et;
    DataBaseHelper dbh;
    SQLiteDatabase db;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        et = (EditText)findViewById(R.id.CompanyNameEnterView);
        tv = (TextView)findViewById(R.id.Result);
        dbh = new DataBaseHelper(this);
        db = dbh.getWritableDatabase();
        Button view = (Button)findViewById(R.id.ViewItem);



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String [] fields = {
                        MyDataBase.Investments._ID,
                        MyDataBase.Investments.Col1,
                        MyDataBase.Investments.Col2,
                        MyDataBase.Investments.Col3
                };
                String check[] = {et.getText().toString()};
                Cursor c = db.query(
                        MyDataBase.Investments.TableName,                     // The table to query
                        fields,                               // The columns to return
                        MyDataBase.Investments.Col1 + " = ?",                                // The columns for the WHERE clause
                        check,                            // The values for the WHERE clause
                        null,                                     // don't group the rows
                        null,                                     // don't filter by row groups
                        null                                // The sort order
                );
                String Data="";
                //c.getCount();
                //Toast.makeText(ViewActivity.this,c.getCount()+" ",Toast.LENGTH_LONG).show();
                if(c.getCount()==0)
                {
                    tv.setText("No Investment in this company !!! ");
                }
                //c.moveToFirst();
                else
                {
                    while(c.moveToNext())
                    {
                        Data = Data + c.getString(1) + " " + c.getString(2) + " " + c.getString(3)  + "\n";
                        c.moveToNext();
                    }
                    tv.setText(Data);
                }


            }
        });



    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sp = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("CV", et.getText().toString());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = this.getPreferences(Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sp.edit();
        String temp = sp.getString("CV","");

        et.setText(temp);
    }

}
