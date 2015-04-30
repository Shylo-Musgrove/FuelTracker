package edu.asu.bscs.sjmusgro.finalproject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ViewFuelEntriesActivity extends ListActivity {
    private FuelEntryDAO datasource;
    List<FuelEntry> fuelEntries = new ArrayList<FuelEntry>();
    ArrayAdapter<FuelEntry> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fuel_entries);
        datasource = new FuelEntryDAO(this);
        datasource.open();
        fuelEntries = datasource.getAllFuelEntries();
        adapter = new ArrayAdapter<FuelEntry>(this,
                android.R.layout.simple_list_item_1, fuelEntries);
        setListAdapter(adapter);
    }
    @Override
    protected void onListItemClick (ListView l, View v, int position, long id) {
       viewFuelEntry(adapter.getItem(position));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void viewFuelEntry(FuelEntry fuelEntry) {
        Intent intent = new Intent(ViewFuelEntriesActivity.this, FuelEntryActivity.class);

        if(fuelEntry != null)
        {
            intent.putExtra("date", fuelEntry.getDate());
            intent.putExtra("gallons", fuelEntry.getGallons());
            intent.putExtra("price", fuelEntry.getPrice());
            intent.putExtra("mileage", fuelEntry.getMileage());
        }

        startActivity(intent);
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
