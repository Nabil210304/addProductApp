package com.example.addproductapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.addproductapp.helper.Helper;

public class EditorActivity extends AppCompatActivity {

    private EditText editNama, editJenis, editHarga;
    private Button btnSave;
    private ImageButton btnBack;
    private Helper db;
    private String id, nama, jenis;
    private double harga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editor);

        db = new Helper(this);
        editNama = findViewById(R.id.edit_nama);
        editJenis = findViewById(R.id.edit_jenis);
        editHarga = findViewById(R.id.edit_harga);
        btnSave = findViewById(R.id.btn_save);
        btnBack = findViewById(R.id.btn_back);

        id = getIntent().getStringExtra("id");
        nama = getIntent().getStringExtra("nama");
        jenis = getIntent().getStringExtra("jenis");
        harga = getIntent().getDoubleExtra("harga", 0);

        if (id == null || id.isEmpty()) {
            setTitle("Tambah Produk");
        } else {
            setTitle("Edit Produk");
            editNama.setText(nama);
            editJenis.setText(jenis);
            editHarga.setText(String.valueOf(harga));
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (id == null || id.isEmpty()) {
                        save();
                    } else {
                        edit();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Go back to MainActivity
            }
        });
    }

    private void save() {
        if (editNama.getText().toString().isEmpty() || editJenis.getText().toString().isEmpty() || editHarga.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
        } else {
            db.insert(editNama.getText().toString(), editJenis.getText().toString(), Double.parseDouble(editHarga.getText().toString()));
            finish();
        }
    }

    private void edit() {
        if (editNama.getText().toString().isEmpty() || editJenis.getText().toString().isEmpty() || editHarga.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
        } else {
            db.update(Integer.parseInt(id), editNama.getText().toString(), editJenis.getText().toString(), Double.parseDouble(editHarga.getText().toString()));
            finish();
        }
    }
}