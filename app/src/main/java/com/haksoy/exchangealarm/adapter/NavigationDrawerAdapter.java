package com.haksoy.exchangealarm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.haksoy.exchangealarm.R;

import java.util.List;

/**
 * Created by haksoy on 31.03.2017.
 */

public class NavigationDrawerAdapter extends ArrayAdapter<NavigationDrawerAdapter.Item> {
    public interface Item {
        int getViewType();

        View getView(LayoutInflater inflater, View convertView);
    }

    public enum RowType {
        LIST_ITEM, HEADER_ITEM
    }

    View mRow;
    LayoutInflater mInflater;

    public NavigationDrawerAdapter(Context context, List<Item> objects) {
        super(context, R.layout.nav_item, objects);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getViewTypeCount() {
        return RowType.values().length;

    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItem(position).getView(mInflater, convertView);
    }
}
