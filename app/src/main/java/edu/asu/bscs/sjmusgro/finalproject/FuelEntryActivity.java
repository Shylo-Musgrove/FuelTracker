package edu.asu.bscs.sjmusgro.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Shylo on 1/31/2015.
 */
public class FuelEntryActivity extends Activity {
    private FuelEntryDAO datasource;
    EditText date;
    EditText gallons;
    EditText price;
    EditText mileage;

    Button editButton;
    Button deleteButton;

    boolean editState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fuel_entry_layout);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
        datasource = new FuelEntryDAO(this);
        datasource.open();

        date = (EditText)findViewById(R.id.entry_date);
        gallons = (EditText)findViewById(R.id.entry_gallons);
        price = (EditText)findViewById(R.id.entry_price);
        mileage = (EditText)findViewById(R.id.entry_mileage);

        editButton = (Button)findViewById(R.id.editButton);
        deleteButton = (Button)findViewById(R.id.deleteButton);

        extractIntent();

    }

    private void extractIntent() {
        Bundle b = getIntent().getExtras();
        if(b != null) {
            String date =  b.getString("date");
            double gallons =  b.getDouble("gallons");
            double price =  b.getDouble("price");
            double mileage =  b.getDouble("mileage");

            this.date.setText(date);
            this.gallons.setText(gallons + "");
            this.price.setText(price + "");
            this.mileage.setText(mileage+"");
            editState = false;
        } else {
            editState = true;

        }
        makeEditable(editState);
    }


    public void finishDialog(View v) {
        FuelEntryActivity.this.finish();
    }

    public void editClick(View v) {
        editState = !editState;

        makeEditable(editState);

        if(editState)
        {

        } else {
            datasource.createFuelEntry(date.getText().toString(), Double.valueOf(gallons.getText().toString()), Double.valueOf(price.getText().toString()), Double.valueOf(mileage.getText().toString()));
        }
    }

    public void deleteClick(View v) {
        makeEditable(!editState);
        onBackPressed();
    }

    public void backClick(View v) {
        onBackPressed();
    }

    private void makeEditable(boolean isEditable) {
        date.setEnabled(isEditable);
        gallons.setEnabled(isEditable);
        price.setEnabled(isEditable);
        mileage.setEnabled(isEditable);
        editButton.setText(isEditable?"Save":"Edit");
        deleteButton.setText(isEditable?"Cancel":"Delete");
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }
}
