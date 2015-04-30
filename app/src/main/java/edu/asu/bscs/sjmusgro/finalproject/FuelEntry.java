package edu.asu.bscs.sjmusgro.finalproject;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 2015 Tim Lindquist,
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * Purpose: This class is part of an example developed for the mobile
 * computing class at ASU Poly. The application provides a waypoint service.
 * This class encapsulates a waypoint and provides for serialization and
 * de-serialization of waypoint objects.
 *
 * @author Tim Lindquist
 * @version 2/8/2015
 **/
public class FuelEntry extends Object implements Serializable {

   public String date;
   public double gallons; // degrees gallons in DD.D format (+ east, - west)
   public double price; // elevation in feet MSL
   public double mileage; // a mileage for the waypoint

   public FuelEntry(String date, double gallons, double price, double mileage){
      this.date = date;
      this.gallons = gallons;
      this.price = price;
      this.mileage = mileage;
   }

   public FuelEntry(){

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
