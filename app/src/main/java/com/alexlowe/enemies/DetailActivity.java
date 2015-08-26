package com.alexlowe.enemies;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.software.shell.fab.ActionButton;

import java.util.ArrayList;


public class DetailActivity extends Activity {


    TextView detailName;
    ListView lvReasons;
    CardAdapter adapter;

    ArrayList<String> enemies;

    ActionButton actionButton;

    String toast = "Description must not exceed 280 Characters";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        actionButton = (ActionButton) findViewById(R.id.action_button);
        actionButton.playShowAnimation();

        detailName = (TextView) findViewById(R.id.detailNameTV);
        lvReasons = (ListView) findViewById(R.id.lvReason);
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/Summeron.ttf");
        detailName.setTypeface(customFont);

        final Enemy enemy = (Enemy) getIntent().getSerializableExtra(MainActivity.ENEMY_DETAIL_KEY);
        enemies = enemy.getDescriptions();

        detailName.setText(enemy.getName());

        adapter = new CardAdapter(this, enemies);
        lvReasons.setAdapter(adapter);

        setupFabListener(enemy);
    }






    private void setupFabListener(final Enemy enemy){
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setTitle(enemy.getName());
                final TextView countTV = new TextView(DetailActivity.this);
                final EditText input = new EditText(DetailActivity.this);
                input.setHint(R.string.detail_hint);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

                TextWatcher txwatcher = new TextWatcher() {
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    public void onTextChanged(CharSequence s, int start, int before, int cont) {
                        countTV.setText(String.valueOf(s.length()) + "/280");
                        if(s.length() > 280){
                            countTV.setTextColor(Color.RED);
                        }else{
                            countTV.setTextColor(Color.BLACK);
                        }
                    }

                    public void afterTextChanged(Editable s) {
                    }
                };

                input.addTextChangedListener(txwatcher);
                input.setSingleLine(false);

                LinearLayout ll=new LinearLayout(getApplicationContext());
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.addView(input);
                ll.addView(countTV);


                builder.setView(ll);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String desc = input.getText().toString();
                        if (desc.length() > 1) {
                            enemies.add(desc);
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                //this is to actually show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();
                validateCount(dialog, input);
            }
        });

    }

    private void validateCount(AlertDialog dialog, final EditText input){
        final Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        button.setEnabled(false);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // my validation condition
                if (input.getText().length() > 1 && input.getText().length() < 281) {
                    button.setEnabled(true);
                } else if (input.getText().length() > 281) {
                    button.setEnabled(false);
                } else {
                    button.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



}
