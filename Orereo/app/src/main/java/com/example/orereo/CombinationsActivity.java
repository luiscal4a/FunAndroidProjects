package com.example.orereo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;

public class CombinationsActivity extends AppCompatActivity {

    private Button btn_new;
    private Button btn_next;
    private LinearLayout ll_list;
    private ArrayList<RelativeLayout> arl_additionalCombinations;
    private String[] arr_words = {"Uno", "Dos", "Tres", "Cuatro", "Cinco", "Seis", "Siete"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combinations);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_new = findViewById(R.id.btn_new);
        ll_list = findViewById(R.id.ll_list);
        btn_next = findViewById(R.id.btn_next);

        arl_additionalCombinations = new ArrayList<>();

        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCombinationDialog();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(NameActivity.this, getAllTexts(), Toast.LENGTH_SHORT).show();
            }
        });

        newCombinationDialog();
    }

    private void newCombinationDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(CombinationsActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_combination, null);
        mBuilder.setTitle("Crea combinaciones pulsando botones");
        final TextView tv_combination = mView.findViewById(R.id.tv_combination);
        FlexboxLayout fl_words = mView.findViewById(R.id.fl_words);

        mBuilder.setCancelable(false);

        for (int i = 0; i < arr_words.length; i++) {

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            final Button btn = new Button(CombinationsActivity.this);
            btn.setLayoutParams(lp);
            btn.setText(arr_words[i]);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_combination.setText(tv_combination.getText()+btn.getText().toString());
                }
            });

            fl_words.addView(btn);
        }


        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                createNewCombination(tv_combination.getText().toString(), mView);
                dialogInterface.dismiss();
            }
        });


        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();

        dialog.show();
    }

    public void createNewCombination(String text, View v) {
        RelativeLayout view = generateNewListItemView(text, v);
        arl_additionalCombinations.add(view);
        ll_list.addView(view);
    }


    public RelativeLayout generateNewListItemView(String text, View v) {
        float d = v.getResources().getDisplayMetrics().density;
        final RelativeLayout newChild = new RelativeLayout(CombinationsActivity.this);

        // Defining the RelativeLayout layout parameters.
        // In this case I want to fill its parent
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        TextView tv = new TextView(CombinationsActivity.this);

        // Defining the layout parameters of the TextView
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins((int) d * 25, (int) d * 5, (int) d * 70, (int) d * 5);

        // Setting the parameters on the TextView
        tv.setLayoutParams(lp);
        tv.setText(text);
        tv.setTextSize(d*10);

        ImageButton ib = new ImageButton(CombinationsActivity.this);

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

        newChild.addView(tv);
        newChild.addView(ib);

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_list.removeView(newChild);
                arl_additionalCombinations.remove(newChild);
            }
        });

        return newChild;
    }
}
