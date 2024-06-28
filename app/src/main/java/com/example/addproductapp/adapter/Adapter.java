package com.example.addproductapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.addproductapp.EditorActivity;
import com.example.addproductapp.R;
import com.example.addproductapp.helper.Helper;
import com.example.addproductapp.model.Data;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends BaseAdapter implements Filterable {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Data> originalList;
    private List<Data> filteredList;
    private Helper db;
    private ItemFilter mFilter = new ItemFilter();

    public Adapter(Activity activity, List<Data> lists) {
        this.activity = activity;
        this.originalList = lists;
        this.filteredList = lists;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.db = new Helper(activity);
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_product, parent, false);
        }

        TextView id = convertView.findViewById(R.id.text_id);
        TextView nama = convertView.findViewById(R.id.text_nama);
        TextView jenis = convertView.findViewById(R.id.text_jenis);
        TextView harga = convertView.findViewById(R.id.text_harga);
        ImageButton btnEdit = convertView.findViewById(R.id.btn_edit);
        ImageButton btnDelete = convertView.findViewById(R.id.btn_delete);

        final Data data = filteredList.get(position);
        id.setText(String.valueOf(position + 1)); // Update the ID based on position
        nama.setText(data.getNama());
        jenis.setText(data.getJenis());
        harga.setText("Rp. " + data.getHarga()); // Add "Rp." prefix to harga

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, EditorActivity.class);
                intent.putExtra("id", data.getId());
                intent.putExtra("nama", data.getNama());
                intent.putExtra("jenis", data.getJenis());
                intent.putExtra("harga", data.getHarga());
                activity.startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.delete(Integer.parseInt(data.getId()));
                originalList.remove(position);
                filteredList.remove(position);
                updateIds();
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    private void updateIds() {
        for (int i = 0; i < filteredList.size(); i++) {
            filteredList.get(i).setId(String.valueOf(i + 1));
        }
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            final List<Data> list = originalList;

            int count = list.size();
            final ArrayList<Data> nlist = new ArrayList<>(count);

            String filterableString;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getNama();
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(list.get(i));
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<Data>) results.values;
            notifyDataSetChanged();
        }
    }
}
