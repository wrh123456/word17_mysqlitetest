package com.example.word17_mysqlitetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button createSqlLite;
    private MyDatabaseHelper databaseHelper;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createSqlLite=(Button)findViewById(R.id.create_SqLite);
        databaseHelper=new MyDatabaseHelper(this,"BookStore.db",null,2);
        createSqlLite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.getWritableDatabase();
            }
        });
        add=(Button)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=databaseHelper.getWritableDatabase();
                db.execSQL("insert into Book(name,author,pages,price) values(?,?,?,?)",new String[]{"第一条","Dan Brown","454","16.96"});
                db.execSQL("insert into Book(name,author,pages,price) values(?,?,?,?)",new String[]{"第二条","Dan Brown","500","21"});
//                ContentValues values=new ContentValues();
//                values.put("name","The Da Vinci Code");
//                values.put("pages",454);
//                values.put("author","Dan Brown");
//                values.put("price",16.96);
//                db.insert("Book",null,values);//插入第一条数据
//                values.clear();
//                values.put("name","The Lost Symbol");
//                values.put("pages",510);
//                values.put("author","Dan Brown");
//                values.put("price",19.95);
//                db.insert("Book",null,values);//插入第二条数据
//                Log.d("hhahhhahh:","插入成功！");
            }
        });
        Button delete=(Button)findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=databaseHelper.getWritableDatabase();
                db.execSQL("delete from Book Where pages>?",new String[]{"490"});
//                db.delete("Book","pages>?",new String[]{"500"});
            }
        });
        Button modify=(Button)findViewById(R.id.modify);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=databaseHelper.getWritableDatabase();
                db.execSQL("update Book set price=? where name=?",new String[]{"10.99","第一条"});
//                ContentValues values=new ContentValues();
//                values.put("price","10.99");
//                db.update("Book",values,"name=?",new String[]{"The Lost Symbol"});
            }
        });
        Button query=(Button)findViewById(R.id.query_data);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=databaseHelper.getWritableDatabase();
                Cursor cursor=db.rawQuery("select* from Book",null);
//                Cursor cursor=db.query("Book",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity", "onClick: name：" + name);
                        Log.d("MainActivity", "onClick: author：" + author);
                        Log.d("MainActivity", "onClick: pages：" + pages);
                        Log.d("MainActivity", "onClick: price：" + price);
                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
        });

    }
}
