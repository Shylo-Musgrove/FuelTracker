package edu.asu.bscs.sjmusgro.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Copyright 2015 Shylo Musgrove,
 * <p/>
 * Licensed under the Apacher License, Version 2.0 (the :License");
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
        private String[] allColumns = { DBHelper.COLUMN_DATE,
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

        public FuelEntry createFuelEntry(String date, double gallons, double price, double mileage) {
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_DATE, date);
            values.put(DBHelper.COLUMN_GALLONS, gallons);
            values.put(DBHelper.COLUMN_PRICE, price);
            values.put(DBHelper.COLUMN_MILEAGE, mileage);

            database.insert(DBHelper.TABLE_FUEL_ENTRY, null,
                    values);
            FuelEntry newEntry = new FuelEntry(date, gallons, price, mileage);
            return newEntry;
        }

        public void deleteFuelEntry(FuelEntry fuelEntry) {
            String date = fuelEntry.getDate();
            System.out.println("FuelEntry deleted with date: " + date);
            database.delete(DBHelper.TABLE_FUEL_ENTRY, DBHelper.COLUMN_DATE
                    + " = " + date, null);
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

        private FuelEntry cursorToFuelEntry(Cursor cursor) {
            FuelEntry fuelEntry = new FuelEntry();
            fuelEntry.setDate(cursor.getString(0));
            fuelEntry.setGallons(cursor.getDouble(1));
            fuelEntry.setPrice(cursor.getDouble(1));
            fuelEntry.setMileage(cursor.getDouble(1));
            return fuelEntry;
        }
    }

