package com.eightmins.eightminutes;

import android.R.color;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.widget.TextView;

import com.eightmins.eightminutes.R.id;
import com.eightmins.eightminutes.R.layout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends WearableActivity {

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    private BoxInsetLayout mContainerView;
    private TextView mTextView;
    private TextView mClockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        setAmbientEnabled();

        mContainerView = (BoxInsetLayout) findViewById(id.container);
        mTextView = (TextView) findViewById(id.text);
        mClockView = (TextView) findViewById(id.clock);
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
        if (isAmbient()) {
            mContainerView.setBackgroundColor(getResources().getColor(color.black));
            mTextView.setTextColor(getResources().getColor(color.white));
            mClockView.setVisibility(View.VISIBLE);

            mClockView.setText(MainActivity.AMBIENT_DATE_FORMAT.format(new Date()));
        } else {
            mContainerView.setBackground(null);
            mTextView.setTextColor(getResources().getColor(color.black));
            mClockView.setVisibility(View.GONE);
        }
    }
}
