package com.example.qlsv;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtmalop, edttenlop, edtsiso;
    Button btInsert, btDelete, btUpdate;
    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;
    SQLiteDatabase mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtmalop = findViewById(R.id.edtClassID);
        edttenlop = findViewById(R.id.edtClassName);
        edtsiso = findViewById(R.id.edtNumberStudent);

        btInsert = findViewById(R.id.btInsert);
        btDelete = findViewById(R.id.btDelete);
        btUpdate = findViewById(R.id.btUpdate);

        lv = findViewById(R.id.lvClass);
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
        lv.setAdapter(myadapter);

        mydatabase = openOrCreateDatabase("qlsinhvien.db", MODE_PRIVATE, null);
        try{
            String sql = "create table tblop(malop TEXT primary key, tenlop TEXT, siso INTEGER)";
            mydatabase.execSQL(sql);
        }
        catch (Exception e){
            Log.e("Error", "Table đã tồn tại");
        }

        btInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String malop = edtmalop.getText().toString();
                String tenlop = edttenlop.getText().toString();
                int siso = Integer.parseInt(edtsiso.getText().toString());
                String sisoStr = edtsiso.getText().toString();

                if(!isSisoUnique(siso)){
                    Toast.makeText(MainActivity.this, "Sĩ số đã tồn tại", Toast.LENGTH_SHORT).show();
                } else if (!isTenUnique(tenlop)) {
                    Toast.makeText(MainActivity.this, "Tên lớp đã tồn tại", Toast.LENGTH_SHORT).show();
                } else if (!isDataValid(malop, tenlop, sisoStr)) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    ContentValues myvalue = new ContentValues();
                    myvalue.put("malop", malop);
                    myvalue.put("tenlop", tenlop);
                    myvalue.put("siso", siso);
                    String msg ="";
                    if(mydatabase.insert("tblop", null, myvalue)  == -1){
                        msg = "Fail to Insert Record";
                    }
                    else{
                        msg = "Insert record Sucessfully";
                    }
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    LoadData();
                }

            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String malop = edtmalop.getText().toString();
                int n = mydatabase.delete("tblop", "malop = ?", new String[]{malop});
                String msg = "";
                if(n == 0){
                    msg = "No record to Delete";
                }
                else{
                    msg = n + "record is deleted";
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                LoadData();
            }
        });

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int siso = Integer.parseInt(edtsiso.getText().toString());
                String malop = edtmalop.getText().toString();

                if(!isSisoUnique(siso)){
                    Toast.makeText(MainActivity.this, "Sĩ số đã tồn tại", Toast.LENGTH_SHORT).show();
                }
                else{
                    ContentValues myvalue = new ContentValues();
                    myvalue.put("siso", siso);
                    int n = mydatabase.update("tblop", myvalue, "malop = ?", new String[]{malop});
                    String msg = "";
                    if(n == 0){
                        msg = "No record to Update";
                    }
                    else{
                        msg = n + "record is updated";
                    }
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    LoadData();

                }
            }
        });
        LoadData();
    }

    private void LoadData(){
        edtmalop.setText("");
        edttenlop.setText("");
        edtsiso.setText("");
        mylist.clear();
        Cursor c = mydatabase.query("tblop", null, null, null, null, null, null);
        c.moveToNext();
        String data = "";
        while(c.isAfterLast() == false){
            data = c.getString(0)+" - "+c.getString(1)+" - "+c.getString(2);
            c.moveToNext();
            mylist.add(data);
        }
        c.close();
        myadapter.notifyDataSetChanged();
    }

    private boolean isSisoUnique(int siso) {
        Cursor cursor = mydatabase.rawQuery("SELECT COUNT(*) FROM tblop WHERE siso = ?", new String[]{String.valueOf(siso)});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count == 0;
    }

    private boolean isTenUnique(String tenlop) {
        Cursor cursor = mydatabase.rawQuery("SELECT COUNT(*) FROM tblop WHERE tenlop = ?", new String[]{String.valueOf(tenlop)});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count == 0;
    }

    private boolean isDataValid(String malop, String tenlop, String siso) {
        if (malop.isEmpty() || tenlop.isEmpty() || siso.isEmpty()) {
            return false;
        }
        return true;
    }
}