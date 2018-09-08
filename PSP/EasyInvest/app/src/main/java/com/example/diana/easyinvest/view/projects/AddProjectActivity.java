package com.example.diana.easyinvest.view.projects;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.diana.easyinvest.R;
import com.example.diana.easyinvest.model.analysis.Analysis;
import com.example.diana.easyinvest.model.companies.Company;
import com.example.diana.easyinvest.model.projects.Project;
import com.example.diana.easyinvest.utils.ArrayUtils;
import com.example.diana.easyinvest.view.EditActivity;
import com.example.diana.easyinvest.viewmodels.AddProjectViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddProjectActivity extends EditActivity {

    @BindView(R.id.name_et)        EditText nameEt;
    @BindView(R.id.description_et) EditText descriptionEt;
    @BindView(R.id.r_et)           EditText rEt;
    @BindView(R.id.duration_et)    EditText durationEt;
    @BindView(R.id.company_spinner)Spinner  companiesSpinner;

    private AddProjectViewModel viewModel;

    private List<EditText> yearsEts;
    private List<Company> companies;

    @Override
    protected int getContentView() {
        return R.layout.activity_add_project;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        durationEt.setOnKeyListener(this::onKey);


        CompaniesSpinnerAdapter adapter = new CompaniesSpinnerAdapter(this, android.R.layout.simple_spinner_item);
        companiesSpinner.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(AddProjectViewModel.class);
        LiveData<List<Company>> groups = viewModel.getCompanies();
        if (groups != null) {
            groups.observe(this, new Observer<List<Company>>() {
                @Override
                public void onChanged(@Nullable List<Company> companies) {
                    AddProjectActivity.this.companies = companies;
                    adapter.setCompanies(companies);
                }
            });
        }
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            addYearsEts();
        }
        return true;
    }

    private void addYearsEts() {
        int count; // additional for 0'th year

        try {
            count = Integer.valueOf(durationEt.getText().toString()) + 1;
        } catch (NumberFormatException e) {
            durationEt.setError(getString(R.string.error_positive_integer));
            return;
        }
        if (count < 0 || count > 20) {
            durationEt.setError(getString(R.string.error_duration_too_big));
            return;
        }
        LinearLayout myLayout = findViewById(R.id.container);
        myLayout.removeAllViews();
        TextView tv = new TextView(this);
        tv.setText(R.string.enter_money_flow);
        myLayout.addView(tv);
        yearsEts = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            EditText et = new EditText(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 50, 0, 0);
            et.setLayoutParams(params);
            et.setHint(getString(R.string.year_num, i));
            et.setInputType(EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_FLAG_SIGNED);
            et.setFocusableInTouchMode(true);
            myLayout.addView(et);
            yearsEts.add(et);
        }
        yearsEts.get(0).requestFocus();
    }

    @Override
    protected int getScreenTitle() {
        return R.string.add_project_title;
    }

    @Override
    protected boolean checkCorrectness() {
        if (TextUtils.isEmpty(nameEt.getText().toString())) {
            nameEt.requestFocus();
            nameEt.setError(getString(R.string.error_project_no_name));
            return false;
        }
        if (TextUtils.isEmpty(descriptionEt.getText().toString())) {
            descriptionEt.requestFocus();
            descriptionEt.setError(getString(R.string.error_project_no_description));
            return false;
        }
        if (TextUtils.isEmpty(rEt.getText().toString())) {
            rEt.requestFocus();
            rEt.setError(getString(R.string.error_project_no_r));
            return false;
        }
        if (TextUtils.isEmpty(durationEt.getText().toString())) {
            durationEt.requestFocus();
            durationEt.setError(getString(R.string.error_project_no_duration));
            return false;
        }
        int count; // additional for 0'th year
        try {
            count = Integer.valueOf(durationEt.getText().toString()) + 1;
        } catch (NumberFormatException e) {
            durationEt.setError(getString(R.string.error_positive_integer));
            return false;
        }
        if (count < 0 || count > 20) {
            durationEt.setError(getString(R.string.error_duration_too_big));
            return false;
        }
        if (yearsEts == null || yearsEts.isEmpty()) {
            addYearsEts();
        }
        for (EditText et : yearsEts) {
            if (TextUtils.isEmpty(et.getText().toString())) {
                et.requestFocus();
                et.setError(getString(R.string.error_project_no_year_value));
                return false;
            }
        }
        return true;
    }

    @Override
    protected boolean save() {
        String name = nameEt.getText().toString();
        String description = descriptionEt.getText().toString();

        float r;
        try {
            r = Float.valueOf(rEt.getText().toString());
        } catch (NumberFormatException e) {
            rEt.setError(getString(R.string.error_positive_number));
            return false;
        }
        double[] flows = new double[yearsEts.size()];
        for (int i = 0; i < yearsEts.size(); i++) {
            try {
                flows[i] = Double.valueOf(yearsEts.get(i).getText().toString());
            } catch (NumberFormatException e) {
                yearsEts.get(i).setError(getString(R.string.error_wrong_format));
                return false;
            }
        }
        if (!ArrayUtils.containsPositives(flows)) {
            yearsEts.get(0).setError(getString(R.string.error_wrong_format));
            return false;
        }
        long companyId = companies.get(companiesSpinner.getSelectedItemPosition()).getId();
        Project newProject = new Project.Builder()
                .setName(name)
                .setCompanyId(companyId)
                .setDescription(description)
                .setR(r)
                .setMoneyFlows(flows)
                .build();
        viewModel.insert(newProject, result -> {
            newProject.id = result;
            Analysis newAnalysis = new Analysis(newProject);
            viewModel.insert(newAnalysis);
        });

        return true;
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AddProjectActivity.class);
        context.startActivity(intent);
    }
}
