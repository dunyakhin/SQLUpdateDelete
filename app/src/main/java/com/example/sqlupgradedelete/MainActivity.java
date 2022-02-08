package com.example.sqlupgradedelete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
Button save, read, clear, deleteElement, upgrade;
EditText name, email, id;
ListView list;
ArrayList<String> arrayList=new ArrayList<>();
ArrayAdapter adapter;
DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        save=findViewById(R.id.saveBut);
        save.setOnClickListener(this);
        read=findViewById(R.id.readBut);
        read.setOnClickListener(this);
        clear=findViewById(R.id.clearBut);
        clear.setOnClickListener(this);
        deleteElement=findViewById(R.id.deleteElement);
        deleteElement.setOnClickListener(this);
        upgrade=findViewById(R.id.upgradeBut);
        upgrade.setOnClickListener(this);
        dbHelper=new DBHelper(this,DBHelper.DB_NAME,null, DBHelper.databaseVer);
        name=findViewById(R.id.et1);
        email=findViewById(R.id.et2);
        id=findViewById(R.id.inputID);
        list=findViewById(R.id.list);

    }


    @Override
    public void onClick(View view) {
        SQLiteDatabase sqldtb=dbHelper.getReadableDatabase();
        adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        list.setAdapter(adapter);
        ContentValues cv=new ContentValues();
    switch (view.getId()){
        case R.id.saveBut:
            cv.put(dbHelper.NAME, name.getText().toString());
            cv.put(dbHelper.EMAIL, email.getText().toString());
            sqldtb.insert(dbHelper.TABLE_NAME, null, cv);
            break;
        case R.id.readBut:
            arrayList.clear();
            Cursor cursor=sqldtb.query(dbHelper.TABLE_NAME, null, null, null,
                    null,null,null);
            int idIndex=cursor.getColumnIndex(dbHelper.ID);
            int nameIndex=cursor.getColumnIndex(dbHelper.NAME);
            int mailIndex=cursor.getColumnIndex(dbHelper.EMAIL);
            if (cursor.moveToFirst()){
                do{
                    arrayList.add(cursor.getString(idIndex)+ " "+ cursor.getString(nameIndex)+ " "+ cursor.getString(mailIndex));
                adapter.notifyDataSetChanged();
                }while(cursor.moveToNext());

            }else{
                cursor.close();
            }

            break;

        case R.id.clearBut:
            sqldtb.delete(dbHelper.TABLE_NAME, null, null);
            arrayList.clear();
            break;
        case R.id.deleteElement:
            if (id.getText().toString().equalsIgnoreCase("")){
                break;
            }else{
                sqldtb.delete(dbHelper.TABLE_NAME, dbHelper.ID +"="+id.getText().toString(), null);
            }
            break;
        case R.id.upgradeBut:
            if(id.getText().toString().equalsIgnoreCase("")){
                break;
            }else{
                cv.put(dbHelper.NAME, name.getText().toString());
                cv.put(dbHelper.EMAIL, email.getText().toString());
                sqldtb.update(dbHelper.TABLE_NAME,cv,dbHelper.ID+"="+id.getText().toString(),null);
            }
            break;
    }
    dbHelper.close();
    }
}