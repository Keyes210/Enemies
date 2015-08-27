package com.alexlowe.enemies;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;


public class DetailActivity extends Activity {


    TextView detailName;
    ListView lvReasons;
    CardAdapter adapter;

    ArrayList<Reason> enemies;

    FloatingActionButton actionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        actionButton = (FloatingActionButton) findViewById(R.id.action_button);
        actionButton.show(true);

        detailName = (TextView) findViewById(R.id.detailNameTV);
        lvReasons = (ListView) findViewById(R.id.lvReason);
        actionButton.attachToListView(lvReasons);

        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/Summeron.ttf");
        detailName.setTypeface(customFont);

        final Enemy enemy = (Enemy) getIntent().getSerializableExtra(MainActivity.ENEMY_DETAIL_KEY);
        enemies = enemy.getReasons();

        detailName.setText(enemy.getName());

        adapter = new CardAdapter(this, enemies);
        lvReasons.setAdapter(adapter);

        setupFabListener(enemy);
        setupLongClick();

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
                                Reason reason = new Reason(desc);
                                enemies.add(reason);
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

    private void setupLongClick() {
        lvReasons.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder adb = new AlertDialog.Builder(DetailActivity.this);
                adb.setTitle("Delete/Edit Entry");
                adb.setNegativeButton("Cancel", null);
                adb.setNeutralButton("Edit Name", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Reason reason = adapter.getItem(position);
                        editReason(reason);
                    }
                });
                adb.setPositiveButton("Delete", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        enemies.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                adb.show();
                return false;
            }
        });
    }

    private void editReason(final Reason reason){
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        builder.setTitle("Edit Reason");

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

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String desc = input.getText().toString();
                if(desc.length() > 1) {
                    reason.setDescription(desc);
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

}
