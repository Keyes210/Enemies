package com.alexlowe.enemies;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class DetailActivity extends Activity {

    TextView detailName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailName = (TextView) findViewById(R.id.detailNameTV);
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/Summeron.ttf");
        detailName.setTypeface(customFont);

        Enemy enemy = (Enemy) getIntent().getSerializableExtra(MainActivity.ENEMY_DETAIL_KEY);

        detailName.setText(enemy.getName());
    }


}
