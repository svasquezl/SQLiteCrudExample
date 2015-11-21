package com.prgguru.example;

import java.util.ArrayList;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class SQLiteCrudExample  extends ListActivity {
	//DB name
	private final String dbName = "Android";
	//Table name
	private final String tableName = "Versions";
	//String array has list Android versions which will be populated in the list
	private final String[] versionNames= new String[]{"Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread", "Honeycomb", "Ice Cream Sandwich", "Jelly Bean", "Kitkat"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> results = new ArrayList<String>();
        //Declare SQLiteDatabase object
        SQLiteDatabase sampleDB = null;
        
        try {
        	//Instantiate sampleDB object
        	sampleDB =  this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);
        	//Create table using execSQL
        	sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableName +	" (versionname VARCHAR);");
        	//Insert Android versions into table created
        	for(String ver: versionNames){
        		sampleDB.execSQL("INSERT INTO " + tableName + " Values ('"+ver+"');");
        	}
        	
        	//Create Cursor object to read versions from the table
        	Cursor c = sampleDB.rawQuery("SELECT versionname FROM " + tableName, null);
        	//If Cursor is valid
        	if (c != null ) {
        		//Move cursor to first row
        		if  (c.moveToFirst()) {
        			do {
        				//Get version from Cursor
        				String firstName = c.getString(c.getColumnIndex("versionname"));
        				//Add the version to Arraylist 'results'
        				results.add(firstName);
        			}while (c.moveToNext()); //Move to next row
        		} 
        	}
        	
        	//Set the ararylist to Android UI List
        	this.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,results));
        	
        } catch (SQLiteException se ) {
        	Toast.makeText(getApplicationContext(), "Couldn't create or open the database", Toast.LENGTH_LONG).show();
        } finally {
        	if (sampleDB != null) {
        		sampleDB.execSQL("DELETE FROM " + tableName);
        		sampleDB.close();
        	}
        }
    }
}
