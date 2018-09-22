package com.gmail.f.d.ganeeva.easyinvest.view.projects;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.f.d.ganeeva.easyinvest.R;
import com.gmail.f.d.ganeeva.easyinvest.utils.Numbers;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoneyFlowAdapter extends RecyclerView.Adapter<MoneyFlowAdapter.Holder> {

    private double[] flows;

    public MoneyFlowAdapter(double[] flows) {
        this.flows = flows;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_money_flow, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(position + 1, flows[position + 1]);
    }

    @Override
    public int getItemCount() {
        return flows.length - 1;
    }

    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.number)    TextView numberTv;
        @BindView(R.id.moneyflow) TextView moneyFlowTV;

        public Holder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(int i, double flow) {
            numberTv.setText(String.valueOf(i));
            moneyFlowTV.setText(Numbers.unifiedDouble(flow));
        }
    }
}
