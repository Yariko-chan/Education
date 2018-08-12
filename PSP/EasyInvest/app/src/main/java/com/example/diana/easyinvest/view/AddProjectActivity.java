package com.example.diana.easyinvest.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.diana.easyinvest.R;

public class AddProjectActivity extends EditActivity implements View.OnKeyListener {

    private EditText yearsEt;

    @Override
    protected int getScreenTitle() {
        return R.string.add_project_title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        yearsEt = findViewById(R.id.years_et);
        yearsEt.setOnKeyListener(this);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AddProjectActivity.class);
        context.startActivity(intent);
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            try {
                int count = Integer.valueOf(yearsEt.getText().toString());

                LinearLayout myLayout = findViewById(R.id.container);
                myLayout.removeAllViews();
                TextView tv = new TextView(this);
                tv.setText(R.string.enter_money_flow);
                myLayout.addView(tv);
                for (int i = 0; i < count; i++) {
                    EditText et = new EditText(this);
                    et.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    et.setHint(getString(R.string.year_num, i));
                    myLayout.addView(et);
                }
            } catch (NumberFormatException e) {
                // show toast and highlight et with error
                yearsEt.setError("Number is invalid, should be positive integer");
            }
        }
        return false;
    }

    @Override
    protected boolean checkCorrectness() {
        return true;
    }

    @Override
    protected void save() {

    }
}
