package com.iiit.savedata;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import static java.security.AccessController.getContext;

public class AddActivity extends Activity {
    EditText company;
    EditText domain;
    EditText amount;
    Button add;
    Button update;
    String defcompany;
    String defdomain;
    String defamount;
    String t1;
    String t2;
    String t3;
    DataBaseHelper dbh;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        company = (EditText)findViewById(R.id.CompanyNameEnter);
        domain = (EditText)findViewById(R.id.CompanyDomainEnter);
        amount = (EditText)findViewById(R.id.AmountEnter);
        add = (Button)findViewById(R.id.AddItem);
        update = (Button)findViewById(R.id.UpdateItem);
        dbh = new DataBaseHelper(this);
        db = dbh.getWritableDatabase();
        final Context c= this;
        final File dirInternal = c.getFilesDir();
        final File dirExternal = c.getExternalFilesDir(getString(R.string.ExtFileDir));
        SharedPreferences sp = this.getPreferences(Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sp.edit();

        defcompany = sp.getString(getString(R.string.DefaultCompany),"");
        defdomain = sp.getString(getString(R.string.DefaultDomain),"");
        defamount = sp.getString(getString(R.string.DefaultAmount),"");

        company.setText(defcompany);
        domain.setText(defdomain);
        amount.setText(defamount);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               // File internalstoragefile = new File(dirInternal,getString(R.string.FileName));
                //FileOutputStream fos;
                t1=company.getText().toString();
                t2=domain.getText().toString();
                t3=amount.getText().toString();
                DataOutputStream dos;

                try {
                    dos = new DataOutputStream(new BufferedOutputStream(openFileOutput(getString(R.string.FileName), Context.MODE_PRIVATE)));
                    dos.writeChars(t1 + " " + t2 + " " + t3 + "\n");
                    //fos.close();
                    dos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                File fileToWrite;
                FileOutputStream fos2;
                DataOutputStream dos2;
                if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
                {
                     try {
                    //fos = openFileOutput(getString(R.string.FileName), Context.MODE_PRIVATE);
                    fileToWrite = new File(dirExternal,getString(R.string.FileName));

                    dos2 = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileToWrite,true)));
                    dos2.writeChars(t1 + " " + t2 + "\n");

                    dos2.close();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(AddActivity.this," Added to Internal storage and DataBase....External Storage Not Available !!!",Toast.LENGTH_LONG).show();
                }

                ContentValues entries = new ContentValues();
                entries.put(MyDataBase.Investments.Col1, t1);
                entries.put(MyDataBase.Investments.Col2, t2);
                entries.put(MyDataBase.Investments.Col3, t3);
                long row = db.insert(MyDataBase.Investments.TableName, null, entries);
                Toast.makeText(AddActivity.this,row + ", Added to Internal and External storage and DataBase",Toast.LENGTH_LONG).show();
                company.setText("");
                domain.setText("");
                amount.setText("");
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                t1=company.getText().toString();
                t2=domain.getText().toString();
                t3=amount.getText().toString();
                ContentValues entries = new ContentValues();
                entries.put(MyDataBase.Investments.Col3, t3);
                String[] check = {t1};
                int count = db.update(
                        MyDataBase.Investments.TableName,
                        entries,
                        MyDataBase.Investments.Col1 + " LIKE ?",
                        check);
                Toast.makeText(AddActivity.this,count+", Updated in DataBase",Toast.LENGTH_LONG).show();
                company.setText("");
                domain.setText("");
                amount.setText("");
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        //String temp1 = company.getText();
        //String temp2 =
        SharedPreferences sp = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(getString(R.string.DefaultCompany),company.getText().toString());
        editor.putString(getString(R.string.DefaultDomain),domain.getText().toString());
        editor.putString(getString(R.string.DefaultAmount), amount.getText().toString());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = this.getPreferences(Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sp.edit();

        defcompany = sp.getString(getString(R.string.DefaultCompany),"");
        defdomain = sp.getString(getString(R.string.DefaultDomain),"");
        defamount = sp.getString(getString(R.string.DefaultAmount),"");

        company.setText(defcompany);
        domain.setText(defdomain);
        amount.setText(defamount);
    }
}

