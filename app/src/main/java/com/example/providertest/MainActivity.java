package com.example.providertest;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int newId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 添加数据
                Uri uri = Uri.parse("content://cn.edu.bistu.cs.se.testfragment.provider/dict");
                ContentValues values = new ContentValues();

                values.put("word", "providertestword");
                values.put("mean_word", "providertestmean_word");
                values.put("example_sentence", "providertestexample_sentence");
                Uri newUri = getContentResolver().insert(uri, values);
                newId = Integer.parseInt(newUri.getPathSegments().get(1));
                Log.d("MainActivity", " newId is " + newId);
            }
        });


        Button queryData = (Button) findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 查询数据
                Uri uri = Uri.parse("content://cn.edu.bistu.cs.se.testfragment.provider/dict");
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        int id = cursor.getInt(cursor. getColumnIndex("_id"));
                        String word = cursor.getString(cursor. getColumnIndex("word"));
                        String mean_word = cursor.getString(cursor.getColumnIndex ("mean_word"));
                        String example_sentence = cursor.getString(cursor. getColumnIndex("example_sentence"));

                        Toast.makeText(MainActivity.this," id is " + id +
                                " word is " + word+
                                " mean_word is " + mean_word+
                                " example_sentence is " + example_sentence,Toast.LENGTH_SHORT)
                                .show();
                    }
                    cursor.close();
                }
            }
        });


        Button findoneword= (Button)findViewById(R.id.findoneword);
        findoneword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et=new EditText(MainActivity.this);
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setView(et);
                builder.setPositiveButton("搜索", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String etstring=et.getText().toString();
                        System.out.println("etstring="+etstring);
                        Uri uri = Uri.parse("content://cn.edu.bistu.cs.se.testfragment.provider/dict/#");

                        Cursor cursor = getContentResolver().query(uri, new String[]{"*"},"word=?", new  String[]{etstring}, null);
                        System.out.println("before if");
                            if (cursor != null) {
                                System.out.println("come in  if");
                            while (cursor.moveToNext()) {
                                int id = cursor.getInt(cursor. getColumnIndex("_id"));
                                String word = cursor.getString(cursor. getColumnIndex("word"));
                                String mean_word = cursor.getString(cursor.getColumnIndex ("mean_word"));
                                String example_sentence = cursor.getString(cursor. getColumnIndex("example_sentence"));

                                Toast.makeText(MainActivity.this,
                                        " id is " + id +
                                        " word is " + word+
                                        " mean_word is " + mean_word+
                                        " example_sentence is " + example_sentence,Toast.LENGTH_LONG)
                                        .show();
                            }
                            cursor.close();
                        }
                            else {

                                Toast.makeText(MainActivity.this,"failed",Toast.LENGTH_SHORT).show();
                            }

                    }
                }).setTitle("搜索").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();

                builder.show();


            }
        });


        Button updateData = (Button) findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 更新数据
                Uri uri = Uri.parse("content://cn.edu.bistu.cs.se.testfragment.provider/dict/" + newId);
                ContentValues values = new ContentValues();
                values.put("word", "updatadataword");
                values.put("mean_word", "updatadatamean_word");
                values.put("example_sentence", "updatadataexample_sentence");
                getContentResolver().update(uri, values, null, null);
            }
        });


        Button deleteData = (Button) findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 删除数据
                Uri uri = Uri.parse("content://cn.edu.bistu.cs.se.testfragment.provider/dict/" + newId);
                getContentResolver().delete(uri, null, null);
            }
        });
    }

}
