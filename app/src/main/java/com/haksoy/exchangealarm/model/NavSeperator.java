package com.haksoy.exchangealarm.model;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.haksoy.exchangealarm.R;
import com.haksoy.exchangealarm.adapter.NavigationDrawerAdapter;

/**
 * Created by haksoy on 31.03.2017.
 */

public class NavSeperator implements NavigationDrawerAdapter.Item {
    private final String name;

    public NavSeperator(String name) {
        this.name = name;
    }

    @Override
    public int getViewType() {
        return NavigationDrawerAdapter.RowType.HEADER_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if (convertView == null) {
            view = inflater.inflate(R.layout.nav_item_seperator, null);
        } else {
            view = convertView;
        }

        TextView text = (TextView) view.findViewById(R.id.txtName);
        text.setText(name);

        return view;
    }
}
