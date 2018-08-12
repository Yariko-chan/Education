package com.example.diana.easyinvest.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.diana.easyinvest.App;
import com.example.diana.easyinvest.R;
import com.example.diana.easyinvest.model.InvestmentsRepository;
import com.example.diana.easyinvest.model.Project;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddProjectActivity extends EditActivity {

    @BindView(R.id.name_et)        EditText nameEt;
    @BindView(R.id.description_et) EditText descriptionEt;
    @BindView(R.id.r_et)           EditText rEt;
    @BindView(R.id.duration_et)    EditText durationEt;

    private List<EditText> yearsEts;

    @Override
    protected int getContentView() {
        return R.layout.activity_add_project;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        durationEt.setOnKeyListener(this::onKey);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AddProjectActivity.class);
        context.startActivity(intent);
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            addYearsEts();
        }
        return true;
    }

    private void addYearsEts() {
        try {
            int count = Integer.valueOf(durationEt.getText().toString()) + 1; // additional for 0'th year
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
        } catch (NumberFormatException e) {
            durationEt.setError("Number is invalid, should be positive integer");
        }
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
    protected void save() {
        String name = nameEt.getText().toString();
        String description = descriptionEt.getText().toString();

        float r;
        int duration;
        try {
            r = Float.valueOf(rEt.getText().toString());
        } catch (NumberFormatException e) {
            rEt.setError(getString(R.string.error_positive_number));
            return;
        }
        try {
            duration = Integer.valueOf(durationEt.getText().toString());
        } catch (NumberFormatException e) {
            durationEt.setError(getString(R.string.error_positive_integer));
            return;
        }
        Project newProject = new Project(name, description, r, duration);
        InvestmentsRepository repository = new InvestmentsRepository(getApplication());
        repository.insert(newProject);
    }
}
