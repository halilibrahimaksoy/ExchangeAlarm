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
 * Created by Halil ibrahim aksoy on 15.08.2017.
 */

public class GeneralExchangeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final LayoutInflater mInflater;

    private List<Exchange> exchangeList;

    public GeneralExchangeListAdapter(LayoutInflater mInflater) {
        this.mInflater = mInflater;
        exchangeList = new ArrayList<>();
    }

    public void setData(List<Exchange> newList) {
        exchangeList.clear();
        exchangeList.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExchangeHolder(mInflater.inflate(R.layout.item_exchange, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ExchangeHolder exchangeHolder = (ExchangeHolder) holder;
        Context context = exchangeHolder.itemView.getContext();
        Exchange exchange = (Exchange) exchangeList.get(position);
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
        return exchangeList.size();
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
}
