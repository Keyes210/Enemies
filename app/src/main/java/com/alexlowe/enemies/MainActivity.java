package com.alexlowe.enemies;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainActivity extends Activity {
    public static final String ENEMY_DETAIL_KEY = "book";
    public static final String LIST_INSTANCE_STATE = "savedList";


    protected TextView mAddTextView;
    protected TextView mLabel;
    private String name;
    ListView mNames;


    ArrayList<Enemy> listItems = new ArrayList<Enemy>();
    EnemyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLabel = (TextView) findViewById(R.id.appLabel);
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/Summeron.ttf");
        mLabel.setTypeface(customFont);

        mNames = (ListView) findViewById(android.R.id.list);
        mAddTextView = (TextView) findViewById(R.id.addEnemy);

        adapter = new EnemyAdapter(this, listItems);
        mNames.setAdapter(adapter);

        if(savedInstanceState!=null) {
            Parcelable listInstanceState = savedInstanceState.getParcelable(LIST_INSTANCE_STATE);
            mNames.onRestoreInstanceState(listInstanceState);
        }

        setupAddClick();
        setupItemClick();
        setupLongClick();

    }

    private void setupItemClick() {
        mNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra(ENEMY_DETAIL_KEY, adapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    private void setupLongClick() {
        mNames.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                adb.setTitle("Delete/Edit Name");
                adb.setMessage("Would you like to delete or edit entry?");
                adb.setNegativeButton("Cancel", null);
                adb.setNeutralButton("Edit Name", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Enemy enemy = adapter.getItem(position);
                        editName(enemy);
                    }
                });
                adb.setPositiveButton("Delete", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        listItems.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                adb.show();
                return false;
            }
        });
    }

    private void addItems(String name) {
        final Enemy enemy = new Enemy(name);
        listItems.add(enemy);
        adapter.notifyDataSetChanged();
    }



    private void setupAddClick(){
        mAddTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Add Enemy");

                final EditText input = new EditText(MainActivity.this);

                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                builder.setView(input);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        name = input.getText().toString();
                        if (name.length() > 1) {
                            addItems(name);
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
                validateInput(dialog, input);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LIST_INSTANCE_STATE, mNames.onSaveInstanceState());
    }

    private void editName(final Enemy enemy){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Edit Enemy Name");

        final EditText input = new EditText(MainActivity.this);

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        builder.setView(input);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = input.getText().toString();
                if(name.length() > 1) {
                    enemy.setName(name);
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
        validateInput(dialog, input);
    }

    private void validateInput(AlertDialog dialog, final EditText input){
        final Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        button.setEnabled(false);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // my validation condition
                if (input.getText().length() > 0) {
                    button.setEnabled(true);
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


