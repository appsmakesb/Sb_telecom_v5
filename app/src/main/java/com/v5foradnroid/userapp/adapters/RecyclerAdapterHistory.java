package com.v5foradnroid.userapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.v5foradnroid.userapp.R;
import com.v5foradnroid.userapp.activities.ActivityHistory;
import com.v5foradnroid.userapp.models.History;
import com.v5foradnroid.userapp.utilities.Utils;

import java.util.List;

public class RecyclerAdapterHistory extends RecyclerView.Adapter<RecyclerAdapterHistory.ViewHolder> {

    private Context context;
    private List<History> arrayItemCart;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_purchase_code;
        TextView txt_order_date;
        TextView txt_order_total;

        public ViewHolder(View view) {
            super(view);
            txt_purchase_code = view.findViewById(R.id.txt_purchase_code);
            txt_order_date = view.findViewById(R.id.txt_order_date);
            txt_order_total = view.findViewById(R.id.txt_order_total);
        }

    }

    public RecyclerAdapterHistory(Context context, List<History> arrayItemCart) {
        this.context = context;
        this.arrayItemCart = arrayItemCart;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txt_purchase_code.setText(ActivityHistory.code.get(position));
        holder.txt_order_date.setText(Utils.getFormatedDateSimple(ActivityHistory.date_time.get(position)));
        holder.txt_order_total.setText(ActivityHistory.order_total.get(position));

    }

    @Override
    public int getItemCount() {
        return ActivityHistory.id.size();
    }

}
