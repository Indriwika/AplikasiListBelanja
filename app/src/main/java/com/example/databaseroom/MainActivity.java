package com.example.databaseroom;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //nama variabel

    EditText editText;
    Button btAdd,btReset;
    RecyclerView recyclerView;

    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign Variabel
        editText = findViewById(R.id.edit_text);
        btAdd = findViewById(R.id.bt_add);
        btReset = findViewById(R.id.bt_reset);
        recyclerView = findViewById(R.id.recycler_view);

        //initialize database
        database = RoomDB.getInstance(this);
        //store database value in data list
        dataList = database.mainDao().getAll();


        //initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        //set layou manager
        recyclerView.setLayoutManager(linearLayoutManager);
        //initialize adapter
        adapter = new MainAdapter(MainActivity.this,dataList);
        //set adapter
        recyclerView.setAdapter(adapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get string dari edit text
                String sText = editText.getText().toString().trim();
                //cek condition
                if (!sText.equals("")){
                    //notifikasi text tidak kosong initialize main data
                    MainData data = new MainData();
                    //set text on main data
                    data.setText(sText);
                    //insert text in database
                    database.mainDao().insert(data);
                    //clear edit text
                    editText.setText("");

                    //notifikasi data ditambahkan
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Data has been saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("All data will be deleted");
                builder.setCancelable(true);
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //delete all data from database
                        database.mainDao().reset(dataList);
                        //notifikasi data di reset
                        dataList.clear();
                        dataList.addAll(database.mainDao().getAll());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "All data has been deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}
