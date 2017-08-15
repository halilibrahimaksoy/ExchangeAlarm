package com.haksoy.exchangealarm.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haksoy.exchangealarm.R;
import com.haksoy.exchangealarm.adapter.GeneralExchangeListAdapter;
import com.haksoy.exchangealarm.model.Constants;
import com.haksoy.exchangealarm.model.Exchange;
import com.haksoy.exchangealarm.model.MarketScreen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Halil ibrahim aksoy on 15.08.2017.
 */


public class GeneralExchangeListFragment extends Fragment {
    private String exchangeCategory;
    private List<Exchange> exchangeList;
    private GeneralExchangeListAdapter adapter;

    public static GeneralExchangeListFragment newInstance(String exchangeCategory, List<Exchange> exchangeList) {
        GeneralExchangeListFragment instance = new GeneralExchangeListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ExchangeCategory, exchangeCategory);
        bundle.putSerializable(Constants.ExchangeList, (Serializable) exchangeList);
        instance.setArguments(bundle);
        return instance;
    }

    @BindView(R.id.rclMainList)
    RecyclerView mainList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general_exchange_list, container, false);
        ButterKnife.bind(this, view);
        configureViews();
        openBundle();
        adapter = new GeneralExchangeListAdapter(inflater);
        mainList.setAdapter(adapter);
        return view;
    }

    private void openBundle() {
        exchangeCategory = getArguments().getString(Constants.ExchangeCategory);
        exchangeList = (List<Exchange>) getArguments().getSerializable(Constants.ExchangeList);
    }

    private void configureViews() {
        mainList.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mainList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mainList.setHasFixedSize(false);
        mainList.setItemAnimator(new DefaultItemAnimator());
    }

    public void filterData(String newText) {
        adapter.setData(filter(exchangeList,newText));
    }
    private List<Exchange> filter(List<Exchange> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<Exchange> filteredModelList = new ArrayList<>();
        if (models.get(0) instanceof Exchange)
            for (Exchange model : models) {
                Exchange exchange = (Exchange) model;
                final String text = exchange.getName().toLowerCase();
                if (text.contains(lowerCaseQuery)) {
                    filteredModelList.add(model);
                }
            }
//        else if (models.get(0) instanceof MarketScreen)
//            for (Object model : models) {
//                MarketScreen marketScreen = (MarketScreen) model;
//                final String text = marketScreen.getName().toLowerCase();
//                if (text.contains(lowerCaseQuery)) {
//                    filteredModelList.add(model);
//                }
//            }
        return filteredModelList;
    }
}
