package com.haksoy.exchangealarm.model;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.haksoy.exchangealarm.R;
import com.haksoy.exchangealarm.adapter.NavigationDrawerAdapter;

/**
 * Created by haksoy on 31.03.2017.
 */

public class NavItem implements NavigationDrawerAdapter.Item {
    private final String name;

    public NavItem(String name) {
        this.name = name;
    }

    @Override
    public int getViewType() {
        return NavigationDrawerAdapter.RowType.LIST_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if (convertView == null) {
            view = inflater.inflate(R.layout.nav_item, null);
        } else {
            view = convertView;
        }

        TextView text = (TextView) view.findViewById(R.id.txtName);
        text.setText(name);

        return view;
    }
}
