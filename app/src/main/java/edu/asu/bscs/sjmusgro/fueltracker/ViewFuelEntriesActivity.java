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

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Comparator;
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
                R.layout.entry_item, fuelEntries);
        adapter.sort(new EntryComparator());
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
            intent.putExtra("id", fuelEntry.getId());
        }

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        datasource.open();
        fuelEntries = datasource.getAllFuelEntries();
        adapter.clear();
        adapter.addAll(fuelEntries);
        adapter.sort(new EntryComparator());
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

    public class EntryComparator implements Comparator<FuelEntry>{
        @Override
        public int compare(FuelEntry fuelEntry, FuelEntry fuelEntry2) {
            String[] split1 = fuelEntry.getDate().split("/");
            String[] split2 = fuelEntry2.getDate().split("/");
            int month1 = Integer.valueOf(split1[0]);
            int month2 = Integer.valueOf(split2[0]);
            int day1 = Integer.valueOf(split1[1]);
            int day2 = Integer.valueOf(split2[1]);
            int year1 = Integer.valueOf(split1[2]);
            int year2 = Integer.valueOf(split2[2]);
            if(year1 < year2)
                return 1;
            else if(year1 > year2)
                return -1;
            else {
                if(month1 < month2)
                    return 1;
                else if(month1 > month2)
                    return -1;
                else {
                    if(day1 < day2)
                        return 1;
                    else if(day1 > day2)
                        return -1;
                    else
                        return 0;
                }
            }

        }
    }
}
