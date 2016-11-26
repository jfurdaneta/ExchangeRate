package com.a3vam.exchangerate.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a3vam.exchangerate.R;

/**
 * @author Jose Urdaneta
 * @version 1.0
 * @date today
 */

public class RatesAdapter extends RecyclerView.Adapter<RatesAdapter.Holder> {


    public RatesAdapter() {
        super();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent,false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class Holder extends RecyclerView.ViewHolder{
        public Holder(View itemView) {
            super(itemView);
        }
    }
}
