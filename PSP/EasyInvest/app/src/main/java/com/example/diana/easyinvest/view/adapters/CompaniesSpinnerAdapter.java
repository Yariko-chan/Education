package com.example.diana.easyinvest.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.diana.easyinvest.model.Company;

import java.util.ArrayList;
import java.util.List;

public class CompaniesSpinnerAdapter extends ArrayAdapter {

    private List<Company> companies = new ArrayList<>();

    public CompaniesSpinnerAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        TextView tv = (TextView) view.findViewById(android.R.id.text1);
        tv.setText(companies.get(position).getName());
        return view;
    }


    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
        TextView tv = (TextView) row.findViewById(android.R.id.text1);
        tv.setText(companies.get(position).getName());
        return row;
    }

    @Override
    public int getCount() {
        return companies.size();
    }
}
