package com.example.addproductapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.addproductapp.adapter.Adapter;
import com.example.addproductapp.helper.Helper;
import com.example.addproductapp.model.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DisplayDataActivity extends AppCompatActivity {

    ListView listView;
    AlertDialog.Builder dialog;
    List<Data> lists = new ArrayList<>();
    Adapter adapter;
    Helper db;
    ImageButton btnBack;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        db = new Helper(this);
        listView = findViewById(R.id.list_item);
        btnBack = findViewById(R.id.btn_back);
        searchView = findViewById(R.id.search_view);

        adapter = new Adapter(this, lists);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                final String itemId = lists.get(position).getId();
                final String itemNama = lists.get(position).getNama();
                final String itemJenis = lists.get(position).getJenis();
                final double itemHarga = lists.get(position).getHarga();
                final CharSequence[] dialogItem = {"Edit", "Hapus"};
                dialog = new AlertDialog.Builder(DisplayDataActivity.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent(DisplayDataActivity.this, EditorActivity.class);
                                intent.putExtra("id", itemId);
                                intent.putExtra("nama", itemNama);
                                intent.putExtra("jenis", itemJenis);
                                intent.putExtra("harga", itemHarga);
                                startActivity(intent);
                                break;
                            case 1:
                                db.delete(Integer.parseInt(itemId));
                                lists.clear();
                                getData();
                                break;
                        }
                    }
                }).show();
                return true;
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Go back to MainActivity
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        getData();
    }

    private void getData() {
        ArrayList<HashMap<String, String>> rows = db.getAll();
        for (int i = 0; i < rows.size(); i++) {
            String id = rows.get(i).get("id_produk");
            String nama = rows.get(i).get("nama");
            String jenis = rows.get(i).get("jenis");
            double harga = Double.parseDouble(rows.get(i).get("harga"));

            Data data = new Data(id, nama, jenis, harga);
            lists.add(data);
        }
        adapter.notifyDataSetChanged();
    }
}
