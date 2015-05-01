package edu.asu.bscs.sjmusgro.fueltracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_FUEL_ENTRY = "fuelentry";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_GALLONS = "gallons";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_MILEAGE = "mileage";
    public static final String COLUMN_DATE = "date";

    private static final String DATABASE_NAME = "fuelentries.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_FUEL_ENTRY + "(" + COLUMN_ID+" integer primary key autoincrement, "+COLUMN_DATE
            + " text, " + COLUMN_GALLONS
            + " double, "+COLUMN_PRICE+" double, "+COLUMN_MILEAGE+" double );";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("DROP TABLE IF EXISTS "+TABLE_FUEL_ENTRY);
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FUEL_ENTRY);
        onCreate(db);
    }

}