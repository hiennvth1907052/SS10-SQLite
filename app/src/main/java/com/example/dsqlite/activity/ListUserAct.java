package com.example.dsqlite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.dsqlite.database.DBHelper;

public class ListUserAct extends AppCompatActivity {
    private DBHelper db;
    private Cursor c;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        db = new DBHelper(this);
        ListView lvUser = (ListView) findViewById(R.id.lvUser);
        c = db.getAllUser();
        adapter = new SimpleCursorAdapter(this, R.layout.item_user, c, new String[] {DBHelper.ID, DBHelper.NAME, DBHelper.GENDER},
                new int[]{R.id.tvId, R.id.tvName, R.id.tvGender}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lvUser.setAdapter(adapter);
        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Cursor cursor = (Cursor) adapter.getItem(position);
                int indexId = cursor.getColumnIndex(DBHelper.ID);
                int indexName = cursor.getColumnIndex(DBHelper.NAME);
                int indexGender = cursor.getColumnIndex(DBHelper.GENDER);
                int indexDes = cursor.getColumnIndex(DBHelper.DES);

                int _id = cursor.getInt(indexId);
                String name = cursor.getString(indexName);
                String gender = cursor.getString(indexGender);
                String des = cursor.getString(indexDes);

                Intent intent = new Intent(ListUserAct.this, UpdateAct.class);
                intent.putExtra(DBHelper.ID, _id);
                intent.putExtra(DBHelper.NAME, name);
                intent.putExtra(DBHelper.GENDER, gender);
                intent.putExtra(DBHelper.DES, des);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart (){
        super.onStart();
        c = db.getAllUser();
        adapter.changeCursor(c);
        adapter.notifyDataSetChanged();
        db.close();
    }
}