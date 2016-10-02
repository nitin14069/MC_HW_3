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

public class DeleteActivity extends Activity {

    EditText et;
    DataBaseHelper dbh;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        et = (EditText)findViewById(R.id.CompanyNameDelete);
        dbh = new DataBaseHelper(this);
        db = dbh.getWritableDatabase();
        Button delete = (Button)findViewById(R.id.DeleteItem);



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String check[] = {et.getText().toString()};
                 db.delete(
                        MyDataBase.Investments.TableName,
                        MyDataBase.Investments.Col1 + " Like ?",
                        check
                );
                Toast.makeText(DeleteActivity.this,"Deleted the Investment !",Toast.LENGTH_SHORT).show();

            }
        });



    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sp = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("DV", et.getText().toString());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = this.getPreferences(Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sp.edit();
        String temp = sp.getString("DV","");

        et.setText(temp);
    }

}