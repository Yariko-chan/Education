package com.example.diana.easyinvest.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diana.easyinvest.R;
import com.example.diana.easyinvest.model.Company;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompaniesAdapter extends RecyclerView.Adapter<CompaniesAdapter.Holder> {

    private List<Company> companies = new ArrayList<>();

    public CompaniesAdapter() {
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_companies_list, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(companies.get(position));
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.name_tv)        TextView nameTV;
        @BindView(R.id.owner_name_tv)  TextView ownerNameTv;
        @BindView(R.id.description_tv) TextView descriptionTV;
        @BindView(R.id.phone_tv)       TextView phoneTv;

        private Context c;

        public Holder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            c = view.getContext();
        }

        public void bind(Company company) {
            nameTV.setText(company.getName());
            ownerNameTv.setText(company.getOwnerName());
            descriptionTV.setText(company.getDescription());
            phoneTv.setText(company.getPhone());
        }
    }
}
