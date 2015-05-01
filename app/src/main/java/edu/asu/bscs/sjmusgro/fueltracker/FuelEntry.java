package edu.asu.bscs.sjmusgro.fueltracker;

import java.io.Serializable;

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
public class FuelEntry extends Object implements Serializable {
    public long id; // db fuelEntry key
    public String date; // date of refuel
    public double gallons; // total gallons from refuel
    public double price; // total price paid for fuel
    public double mileage; // total miles on tank of gas

    public FuelEntry(String date, double gallons, double price, double mileage){
        this.date = date;
        this.gallons = gallons;
        this.price = price;
        this.mileage = mileage;
    }

    public FuelEntry(){

    }
    public void setId(long  id){
        this.id=id;
    }
    public long getId(){
        return this.id;
    }
    public void setDate(String  date){
        this.date=date;
    }
    public String getDate(){
        return this.date;
    }
    public void setGallons(double gallons){
        this.gallons=gallons;
    }
    public double getGallons(){
        return this.gallons;
    }
    public void setPrice(double price){
        this.price=price;
    }
    public double getPrice(){
        return this.price;
    }
    public void setMileage(double mileage){
        this.mileage=mileage;
    }
    public double getMileage(){
        return this.mileage;
    }

    public String toString(){
        return this.date;
    }
}
