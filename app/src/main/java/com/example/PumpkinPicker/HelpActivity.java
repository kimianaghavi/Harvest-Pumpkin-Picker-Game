package com.example.PumpkinPicker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Display authors name, hyperlink to the CMPT 276 home-page, game explanation, and citation of the used images and icons
 */
public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        TextView infoWindow = (TextView) findViewById(R.id.txtInfo);

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(getString(R.string.developers));
        stringBuilder.append(getString(R.string.course_link));
        stringBuilder.append(getString(R.string.app_description));
        stringBuilder.append(getString(R.string.lbl_image_citations));
        stringBuilder.append(getString(R.string.tree_with_leaves_link));
        stringBuilder.append(getString(R.string.two_leaves_link));
        stringBuilder.append(getString(R.string.tubes_halloween_link));
        stringBuilder.append(getString(R.string.pumpkin_link));
        stringBuilder.append(getString(R.string.fall_wallpaper_link));
        stringBuilder.append(getString(R.string.autumn_pattern_link));
        stringBuilder.append(getString(R.string.back_ground_leaves_link));
        stringBuilder.append(getString(R.string.leaves_on_the_side_link));
        stringBuilder.append(getString(R.string.game_background_link));

        infoWindow.setText(stringBuilder.toString());
    }

    public static Intent makeLaunchIntent(Context context) {
        return (new Intent(context, HelpActivity.class));
    }

}
