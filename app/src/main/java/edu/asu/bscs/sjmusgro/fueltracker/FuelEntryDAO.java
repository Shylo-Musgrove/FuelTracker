package edu.asu.bscs.sjmusgro.fueltracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

public class FuelEntryDAO {
    // Database fields
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] allColumns = { DBHelper.COLUMN_ID, DBHelper.COLUMN_DATE,
            DBHelper.COLUMN_GALLONS, DBHelper.COLUMN_PRICE, DBHelper.COLUMN_MILEAGE };

    public FuelEntryDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long createFuelEntry(FuelEntry fuelEntry) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_DATE, fuelEntry.getDate());
        values.put(DBHelper.COLUMN_GALLONS, fuelEntry.getGallons());
        values.put(DBHelper.COLUMN_PRICE, fuelEntry.getPrice());
        values.put(DBHelper.COLUMN_MILEAGE, fuelEntry.getMileage());

        long insertId = database.insert(DBHelper.TABLE_FUEL_ENTRY, null,
                values);
        return insertId;
    }

    public void deleteFuelEntry(FuelEntry fuelEntry) {
        long id = fuelEntry.getId();
        System.out.println("FuelEntry deleted with id: " + id);
        database.delete(DBHelper.TABLE_FUEL_ENTRY, DBHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<FuelEntry> getAllFuelEntries() {
        List<FuelEntry> fuelEntries = new ArrayList<FuelEntry>();

        Cursor cursor = database.query(DBHelper.TABLE_FUEL_ENTRY,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FuelEntry fuelEntry = cursorToFuelEntry(cursor);
            fuelEntries.add(fuelEntry);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return fuelEntries;
    }

    public void updateFuelEntry(FuelEntry fuelEntry){
        String strFilter = DBHelper.COLUMN_ID+"=" + fuelEntry.getId();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_DATE, fuelEntry.getDate());
        values.put(DBHelper.COLUMN_GALLONS, fuelEntry.getGallons());
        values.put(DBHelper.COLUMN_PRICE, fuelEntry.getPrice());
        values.put(DBHelper.COLUMN_MILEAGE, fuelEntry.getMileage());
        database.update(DBHelper.TABLE_FUEL_ENTRY, values, strFilter, null);
    }

    public FuelEntry getFuelEntryById(long id){
        Cursor cursor = database.query(DBHelper.TABLE_FUEL_ENTRY,
                allColumns, DBHelper.COLUMN_ID + " = " + id, null,
                null, null, null);
        cursor.moveToNext();
        FuelEntry fuelEntry = cursorToFuelEntry(cursor);
        cursor.close();
        return fuelEntry;
    }

    private FuelEntry cursorToFuelEntry(Cursor cursor) {
        FuelEntry fuelEntry = new FuelEntry();
        fuelEntry.setId(cursor.getLong(0));
        fuelEntry.setDate(cursor.getString(1));
        fuelEntry.setGallons(cursor.getDouble(2));
        fuelEntry.setPrice(cursor.getDouble(3));
        fuelEntry.setMileage(cursor.getDouble(4));
        return fuelEntry;
    }
}
