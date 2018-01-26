package com.exercises.javid.educationapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class GridViewCustomAdapter extends BaseAdapter {
    private Context context;
    private static LayoutInflater inflater=null;

    private ArrayList<HashMap<String, String>> mapList;

    public GridViewCustomAdapter(Context context) {
        mapList = new DatabaseHandler(context).getDataTable();
        this.context=context;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mapList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        HashMap<String, String> map = mapList.get(position);
        final String name = map.get(DatabaseHandler.T_NAME);
        final String img_name = map.get(DatabaseHandler.T_IMG);
        int img_id = context.getResources().getIdentifier(
                img_name, "drawable", context.getPackageName());

        View rowView = inflater.inflate(R.layout.grid_item, null);
        TextView tv = rowView.findViewById(R.id.txt_grid_item);
        ImageView img = rowView.findViewById(R.id.img_grid_item);

        tv.setText(name);
        img.setImageResource(img_id);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked "+ name, Toast.LENGTH_LONG).show();
            }
        });

        return rowView;
    }
}
