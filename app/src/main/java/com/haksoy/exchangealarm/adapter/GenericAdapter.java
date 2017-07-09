package com.haksoy.exchangealarm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haksoy.exchangealarm.R;
import com.haksoy.exchangealarm.model.Exchange;
import com.haksoy.exchangealarm.model.MarketScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haksoy on 6.04.2017.
 */

public class GenericAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final LayoutInflater mInflater;

    private List<Object> objectList;

    public GenericAdapter(LayoutInflater mInflater) {
        this.mInflater = mInflater;
        objectList = new ArrayList<>();
    }

    public void setData(List<Object> newList) {
        objectList.clear();
        objectList.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new GenericAdapter.ExchangeHolder(mInflater.inflate(R.layout.item_exchange, parent, false));
            case 1:
                return new GenericAdapter.MarketScreenHolder(mInflater.inflate(R.layout.item_market_screen, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                configureExchangeHolder(holder, position);
                break;
            case 1:
                configureMarketScreenHolder(holder, position);
                break;
        }
    }


    private void configureMarketScreenHolder(RecyclerView.ViewHolder holder, int position) {
        GenericAdapter.MarketScreenHolder marketScreenHolder = (GenericAdapter.MarketScreenHolder) holder;
        Context context = marketScreenHolder.itemView.getContext();
        MarketScreen marketScreen = (MarketScreen) objectList.get(position);
        marketScreenHolder.mExchangeName.setText(marketScreen.getName());
        marketScreenHolder.mExchangeValue.setText(marketScreen.getPrice() + "");
        marketScreenHolder.mExchangeYesterValue.setText(marketScreen.getYesterDayPrice() + "");
        marketScreenHolder.mExchangePercentage.setText("% " + marketScreen.getPercentageChange());
        if (marketScreen.getPercentageChange().contains("-")) {
            marketScreenHolder.mExchangePercentage.setTextColor(context.getResources().getColor(R.color.redTextColor));
            marketScreenHolder.mExchangeStatus.setImageResource(R.drawable.ic_down_d32f2f);
        } else if (marketScreen.getPercentageChange().equals("0,00")) {
            marketScreenHolder.mExchangePercentage.setTextColor(context.getResources().getColor(R.color.blueTextColor));
            marketScreenHolder.mExchangeStatus.setImageResource(R.drawable.ic_stabil_0277bd);
        } else {
            marketScreenHolder.mExchangePercentage.setTextColor(context.getResources().getColor(R.color.greenTextColor));
            marketScreenHolder.mExchangeStatus.setImageResource(R.drawable.ic_up_2e7d32);
        }
    }


    private void configureExchangeHolder(RecyclerView.ViewHolder holder, int position) {
        GenericAdapter.ExchangeHolder exchangeHolder = (GenericAdapter.ExchangeHolder) holder;
        Context context = exchangeHolder.itemView.getContext();
        Exchange exchange = (Exchange) objectList.get(position);
        exchangeHolder.mExchangeName.setText(exchange.getName());
        exchangeHolder.mExchangeValue.setText(exchange.getPrice() + "");
        exchangeHolder.mExchangePercentage.setText("% " + exchange.getPercentageChange());
        if (exchange.getPercentageChange().contains("-")) {
            exchangeHolder.mExchangePercentage.setTextColor(context.getResources().getColor(R.color.redTextColor));
            exchangeHolder.mExchangeStatus.setImageResource(R.drawable.ic_down_d32f2f);
        } else if (exchange.getPercentageChange().equals("0,00")) {
            exchangeHolder.mExchangePercentage.setTextColor(context.getResources().getColor(R.color.blueTextColor));
            exchangeHolder.mExchangeStatus.setImageResource(R.drawable.ic_stabil_0277bd);
        } else {
            exchangeHolder.mExchangePercentage.setTextColor(context.getResources().getColor(R.color.greenTextColor));
            exchangeHolder.mExchangeStatus.setImageResource(R.drawable.ic_up_2e7d32);
        }
    }


    @Override
    public int getItemCount() {
        return objectList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (objectList.get(position).getClass() == Exchange.class) return 0;
        else if (objectList.get(position).getClass() == MarketScreen.class) return 1;
        return 0;
    }

    class ExchangeHolder extends RecyclerView.ViewHolder {
        private TextView mExchangeName, mExchangeValue, mExchangePercentage;
        private ImageView mExchangeStatus;

        ExchangeHolder(View itemView) {
            super(itemView);
            mExchangeStatus = (ImageView) itemView.findViewById(R.id.imgExchangeStatus);
            mExchangeName = (TextView) itemView.findViewById(R.id.txtExchangeName);
            mExchangeValue = (TextView) itemView.findViewById(R.id.txtExchangeValue);
            mExchangePercentage = (TextView) itemView.findViewById(R.id.txtExchangePercentage);
        }
    }

    class MarketScreenHolder extends RecyclerView.ViewHolder {
        private TextView mExchangeName, mExchangeValue, mExchangeYesterValue, mExchangePercentage;
        private ImageView mExchangeStatus;

        MarketScreenHolder(View itemView) {
            super(itemView);
            mExchangeStatus = (ImageView) itemView.findViewById(R.id.imgExchangeStatus);
            mExchangeName = (TextView) itemView.findViewById(R.id.txtExchangeName);
            mExchangeValue = (TextView) itemView.findViewById(R.id.txtExchangeValue);
            mExchangeYesterValue = (TextView) itemView.findViewById(R.id.txtExchangeYesterValue);
            mExchangePercentage = (TextView) itemView.findViewById(R.id.txtExchangePercentage);
        }
    }
}
