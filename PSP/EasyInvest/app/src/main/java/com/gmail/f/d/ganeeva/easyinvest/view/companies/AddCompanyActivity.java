package com.gmail.f.d.ganeeva.easyinvest.view.companies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Spinner;

import com.gmail.f.d.ganeeva.easyinvest.R;
import com.gmail.f.d.ganeeva.easyinvest.model.companies.Company;
import com.gmail.f.d.ganeeva.easyinvest.model.groups.Group;
import com.gmail.f.d.ganeeva.easyinvest.view.EditActivity;
import com.gmail.f.d.ganeeva.easyinvest.viewmodels.AddCompanyViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddCompanyActivity extends EditActivity {

    @BindView(R.id.name_et)        EditText nameEt;
    @BindView(R.id.owner_name_et)  EditText ownerNameEt;
    @BindView(R.id.description_et) EditText descriptionEt;
    @BindView(R.id.phone_et)       EditText phoneEt;
    @BindView(R.id.group_spinner)  Spinner groupSpinner;

    private AddCompanyViewModel viewModel;
    private List<Group> groups = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_add_company;
    }

    @Override
    protected int getScreenTitle() {
        return R.string.add_company_title;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GroupsSpinnerAdapter adapter = new GroupsSpinnerAdapter(this, R.layout.item_spinner_adapter);
        groupSpinner.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(AddCompanyViewModel.class);
        LiveData<List<Group>> groups = viewModel.getGroups();
        if (groups != null) {
            groups.observe(this, new Observer<List<Group>>() {
                @Override
                public void onChanged(@Nullable List<Group> groups) {
                    AddCompanyActivity.this.groups = groups;
                    adapter.setGroups(groups);
                }
            });
        }
    }

    @Override
    protected boolean checkCorrectness() {
        if (TextUtils.isEmpty(nameEt.getText().toString())) {
            nameEt.requestFocus();
            nameEt.setError(getString(R.string.error_company_no_name));
            return false;
        }
        if (TextUtils.isEmpty(ownerNameEt.getText().toString())) {
            ownerNameEt.requestFocus();
            ownerNameEt.setError(getString(R.string.error_company_no_owner_name));
            return false;
        }
        if (TextUtils.isEmpty(descriptionEt.getText().toString())) {
            descriptionEt.requestFocus();
            descriptionEt.setError(getString(R.string.error_company_no_description));
            return false;
        }
        if (TextUtils.isEmpty(phoneEt.getText().toString()) ||
                !Patterns.PHONE.matcher(phoneEt.getText().toString()).matches()) {
            phoneEt.requestFocus();
            phoneEt.setError(getString(R.string.error_company_no_phone));
            return false;
        }
        return true;
    }

    @Override
    protected boolean save() {
        String name = nameEt.getText().toString();
        String description = descriptionEt.getText().toString();
        String ownerName = ownerNameEt.getText().toString();
        String phone = phoneEt.getText().toString();
        long groupId = groups.get(groupSpinner.getSelectedItemPosition()).getId();
        Company c = new Company(name, ownerName, phone, groupId);
        c.setDescription(description);
        viewModel.insert(c);
        return true;
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AddCompanyActivity.class);
        context.startActivity(intent);
    }
}
