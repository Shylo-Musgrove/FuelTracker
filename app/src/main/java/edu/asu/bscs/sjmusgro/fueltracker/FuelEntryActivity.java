package edu.asu.bscs.sjmusgro.fueltracker;

/**
 * Copyright 2015 Shylo Musgrove,
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the :License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * Purpose:
 *
 * @author Shylo Musgrove  Sjmusgro@asu.edu
 *         Computer Science Student, CIDSE, IAFSE, Arizona State University Tempe
 * @version 4/30/2015
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FuelEntryActivity extends Activity {
    FuelEntry fuelEntry;
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
        editState = true;
        if(b != null) {
            NumberFormat formatter = new DecimalFormat("#0.00");
            long id =  b.getLong("id");
            fuelEntry = datasource.getFuelEntryById(id);
            this.date.setText(fuelEntry.getDate());
            this.gallons.setText(formatter.format(fuelEntry.getGallons()));
            this.price.setText(formatter.format(fuelEntry.getPrice()));
            this.mileage.setText(formatter.format(fuelEntry.getMileage()));
            editState = false;
        }else{
            Date now = new Date();
            this.date.setText(new SimpleDateFormat("MM/dd/yyyy").format(now));
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
            if(fuelEntry==null) {
                fuelEntry = new FuelEntry(date.getText().toString(), Double.valueOf(gallons.getText().toString()), Double.valueOf(price.getText().toString()), Double.valueOf(mileage.getText().toString()));
                datasource.createFuelEntry(fuelEntry);
            } else {
                fuelEntry.setDate(date.getText().toString());
                fuelEntry.setGallons(Double.valueOf(gallons.getText().toString()));
                fuelEntry.setPrice(Double.valueOf(price.getText().toString()));
                fuelEntry.setMileage(Double.valueOf(mileage.getText().toString()));
                datasource.updateFuelEntry(fuelEntry);
            }
        }
    }

    public void deleteClick(View v) {
        if(fuelEntry!=null){
            datasource.deleteFuelEntry(fuelEntry);
        }
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
