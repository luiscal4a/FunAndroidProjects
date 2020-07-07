package com.example.orereo;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NameActivity extends AppCompatActivity {

    private Button btn_new;
    private Button btn_next;
    private LinearLayout ll_list;
    private EditText et_word1;
    private EditText et_word2;
    private EditText et_word3;
    private ArrayList<RelativeLayout> arl_additionalWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_new = findViewById(R.id.btn_new);
        ll_list = findViewById(R.id.ll_list);
        btn_next = findViewById(R.id.btn_next);
        et_word1 = findViewById(R.id.et_word1);
        et_word2 = findViewById(R.id.et_word2);
        et_word3 = findViewById(R.id.et_word3);

        arl_additionalWords = new ArrayList<>();

        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RelativeLayout view = generateNewListItemView(v);
                arl_additionalWords.add(view);
                ll_list.addView(view);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NameActivity.this, CombinationsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public String getAllTexts() {
        String aux = et_word1.getText().toString() + " " +
                et_word2.getText().toString() + " " +
                et_word3.getText().toString() + " ";

        for (int i = 0; i < arl_additionalWords.size(); i++) {
            aux += ((EditText) arl_additionalWords.get(i).getChildAt(0)).getText().toString() + " ";
        }

        return aux;
    }

    public RelativeLayout generateNewListItemView(View v) {
        float d = v.getResources().getDisplayMetrics().density;
        final RelativeLayout newChild = new RelativeLayout(NameActivity.this);

        // Defining the RelativeLayout layout parameters.
        // In this case I want to fill its parent
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        EditText et = new EditText(NameActivity.this);

        // Defining the layout parameters of the TextView
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins((int) d * 25, (int) d * 5, (int) d * 70, (int) d * 5);

        // Setting the parameters on the TextView
        et.setLayoutParams(lp);
        et.setHint("Fragmento adicional");

        ImageButton ib = new ImageButton(NameActivity.this);

        // Defining the layout parameters of the TextView
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
                (int) d * 50,(int) d * 50);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp2.setMargins(0, 0, (int) d * 10, 0);

        // Setting the parameters on the TextView
        ib.setLayoutParams(lp2);
        ib.setImageResource(R.drawable.ic_remove_circle_black_24dp);
        ib.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ib.setPadding(5, 5, 5, 5);

        TypedValue outValue = new TypedValue();
        v.getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);

        ib.setBackgroundResource(outValue.resourceId);

        newChild.addView(et);
        newChild.addView(ib);

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_list.removeView(newChild);
                arl_additionalWords.remove(newChild);
            }
        });

        return newChild;
    }
}
