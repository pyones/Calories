package cs477.project.workoutitems;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
	public static final String TABLE_NAME4 ="CalorieDaily";
	public static final String COLUMN_ID4= "_id";
	public static final String COL_DATE2 = "Date";
	public static final String COL_TYPE = "Type";
	public static final String COL_EATEN = "Food";
	public static final String COL_CALCON= "Calories Consumed";
	//Calorie Database
	public static final String TABLE_NAME3 ="WorkDaily";
	public static final String COLUMN_ID3= "_id";
	public static final String COL_WORK = "Workout";
	public static final String COL_DATE = "Date";
	public static final String COL_RESULT = "Result";
	public static final String COL_CALBURN= "Calories Burn";
	///Food database
	public static final String TABLE_NAME2 = "FoodDatabase";
	public static final String COLUMN_ID2 = "_id";
	public static final String COL_FOOD = "Food";
	public static final String COL_CAL = "Calorie";
	//Workout Database
	public static final String TABLE_NAME = "WorkoutDatabase";
	public static final String COLUMN_ID = "_id";
	public static final String COL_NAME = "NameExercise";
	public static final String COL_INFO = "Description";
	public static final String COL_SCORE = "Score";
	private static final int DB_VERSION = 1;
	public SQLiteDatabase DB;
	public String DBPath;
	public static String DBName = "WorkoutDatabase";
	public static final int version = '1';
	public static Context currentContext;
	/////Create FOOD DATABASE
	// Database creation sql statement
	private static final String DATABASE_CREATE_F = 
			"create table " +
					TABLE_NAME2 + 
					"(" + 
					COLUMN_ID + " integer primary key autoincrement, " + 
					COL_FOOD + " text not null, "+ 
					COL_CAL +  " text not null " + 
					");";
	private static final String DATABASE_INSERT_F1 =
			"Insert Into " +
					TABLE_NAME2 +
					"(" +
					COL_FOOD +
					"," + COL_CAL + 
					")" + "\n" + "Values ('Banana','100');";

	private static final String DATABASE_INSERT_F2 =
			"Insert Into " +
					TABLE_NAME2 +
					"(" +
					COL_FOOD +
					"," + COL_CAL + 
					")" + "\n" + "Values ('Yogurt','200');";

	private static final String DATABASE_INSERT_F3 =
			"Insert Into " +
					TABLE_NAME2 +
					"(" +
					COL_FOOD +
					"," + COL_CAL + 
					")" + "\n" + "Values ('Meat','300');";


	private static final String DATABASE_INSERT_F4 =
			"Insert Into " +
					TABLE_NAME2 +
					"(" +
					COL_FOOD +
					"," + COL_CAL + 
					")" + "\n" + "Values ('Fruit','200');";


	// Database creation sql statement
	private static final String DATABASE_CREATE = 
			"create table " +
					TABLE_NAME + 
					"(" + 
					COLUMN_ID + " integer primary key autoincrement, " + 
					COL_NAME + " text not null, "+ 
					COL_INFO +  " text not null " + 
					");";
	private static final String DATABASE_INSERT1 =
			"Insert Into " +
					TABLE_NAME +
					"(" +
					COL_NAME +
					"," + COL_INFO + 
					")" + "\n" + "Values ('Push-Ups','An exercise in which a person lies facing the floor and, keeping their back straight, raises their body by pressing down on their hands.');";

	private static final String DATABASE_INSERT2 =
			"Insert Into " +
					TABLE_NAME +
					"(" +
					COL_NAME +
					"," + COL_INFO + 
					")" + "\n" + "Values ('Bear Crawl','Embrace that inner grizzly. Starting on the hands and knees, rise up onto the toes, tighten the core, and slowly reach forward with the right arm and right knee, followed by the left side. Continue the crawl for 8-10 reps (or until you scare your roommates off).');";

	private static final String DATABASE_INSERT3 =
			"Insert Into " +
					TABLE_NAME +
					"(" +
					COL_NAME +
					"," + COL_INFO + 
					")" + "\n" + "Values ('Plank','Lie face down with forearms on the floor and hands clasped. Extend the legs behind the body and rise up on the toes. Keeping the back straight, tighten the core and hold the position for 30-60 seconds (or as long as you can hang).');";


	private static final String DATABASE_INSERT4 =
			"Insert Into " +
					TABLE_NAME +
					"(" +
					COL_NAME +
					"," + COL_INFO + 
					")" + "\n" + "Values ('Wall Sit','Slowly slide your back down a wall until the thighs are parallel to the ground. Make sure the knees are directly above the ankles and keep the back straight. Go for 60 seconds per set (or however long it takes to turn those legs to jelly).');";

	public DatabaseOpenHelper(Context context) {
		super(context, DBName, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {	
		database.execSQL(DATABASE_CREATE_F);
		database.execSQL(DATABASE_INSERT_F1);
		database.execSQL(DATABASE_INSERT_F2);
		database.execSQL(DATABASE_INSERT_F3);
		database.execSQL(DATABASE_INSERT_F4);
		database.execSQL(DATABASE_CREATE);
		database.execSQL(DATABASE_INSERT1);
		database.execSQL(DATABASE_INSERT2);
		database.execSQL(DATABASE_INSERT3);
		database.execSQL(DATABASE_INSERT4);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DatabaseOpenHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
		onCreate(db);
	}


	public void deleteRow(String unique)
	{
		DB = this.getReadableDatabase();
		// ask the database manager to delete the row of given id
		try
		{
			DB.delete(TABLE_NAME, COL_NAME + "=" + "'" + unique + "'", null);
		}
		catch (Exception e)
		{
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
	}

	public void addRow(String name, String descript)
	{
		DB = this.getReadableDatabase();
		// this is a key value pair holder used by android's SQLite functions
		ContentValues values = new ContentValues();

		// this is how you add a value to a ContentValues object
		// we are passing in a key string and a value string each time
		values.put(COL_NAME, name);
		values.put(COL_INFO, descript);

		// ask the database object to insert the new data 
		try
		{
			DB.insert(TABLE_NAME, null, values);
		}
		catch(Exception e)
		{
			Log.e("DB ERROR", e.toString()); // prints the error message to the log
			e.printStackTrace(); // prints the stack trace to the log
		}
	}

	public void updateRow(String uniqueName, String name, String descript)
	{
		DB = this.getReadableDatabase();
		// this is a key value pair holder used by android's SQLite functions
		ContentValues values = new ContentValues();
		values.put(COL_NAME, name);
		values.put(COL_INFO, descript);

		// ask the database object to update the database row of given rowID
		try {DB.update(TABLE_NAME, values, COL_NAME + "=" + "'" + uniqueName + "'", null);}
		catch (Exception e)
		{
			Log.e("DB Error", e.toString());
			e.printStackTrace();
		}
	}
	
	
	  public void deleteRowFood(String unique)
	  {
		  DB = this.getReadableDatabase();
	  	// ask the database manager to delete the row of given id
	  	try
	  	{
	  		DB.delete(TABLE_NAME2, COL_FOOD + "=" + "'" + unique + "'", null);
	      }
	  	catch (Exception e)
	  	{
	  		Log.e("DB ERROR", e.toString());
	  		e.printStackTrace();
	  	}
	  }
	  
	  public void addRowFood(String name, int descript)
	  {
		  DB = this.getReadableDatabase();
	  	// this is a key value pair holder used by android's SQLite functions
	  	ContentValues values = new ContentValues();
	   
	  	// this is how you add a value to a ContentValues object
	  	// we are passing in a key string and a value string each time
	  	values.put(COL_FOOD, name);
	  	values.put(COL_CAL, descript);
	   
	  	// ask the database object to insert the new data 
	  	try
	  	{
	  		DB.insert(TABLE_NAME2, null, values);
	  	}
	  	catch(Exception e)
	  	{
	  		Log.e("DB ERROR", e.toString()); // prints the error message to the log
	  		e.printStackTrace(); // prints the stack trace to the log
	  	}
	  }
	  
	  public void updateRowFood(String uniqueName, String name, String descript)
	  {
		  DB = this.getReadableDatabase();
	  	// this is a key value pair holder used by android's SQLite functions
	  	ContentValues values = new ContentValues();
	  	values.put(COL_FOOD, name);
	  	values.put(COL_CAL, descript);
	   
	  	// ask the database object to update the database row of given rowID
	  	try {DB.update(TABLE_NAME2, values, COL_FOOD + "=" + "'" + uniqueName + "'", null);}
	  	catch (Exception e)
	  	{
	  		Log.e("DB Error", e.toString());
	  		e.printStackTrace();
	  	}
	  }

	public void addRowCalorie(String date, String type, String eat, int calorie)
	  {
		  DB = this.getReadableDatabase();
	  	// this is a key value pair holder used by android's SQLite functions
	  	ContentValues values = new ContentValues();
	   
	  	// this is how you add a value to a ContentValues object
	  	// we are passing in a key string and a value string each time
	  	values.put(COL_DATE2, date);
	  	values.put(COL_TYPE, type);
	  	values.put(COL_EATEN, eat);
	  	values.put(COL_CALCON, calorie);

	  	// ask the database object to insert the new data 
	  	try
	  	{
	  		DB.insert(TABLE_NAME4, null, values);
	  	}
	  	catch(Exception e)
	  	{
	  		Log.e("DB ERROR", e.toString()); // prints the error message to the log
	  		e.printStackTrace(); // prints the stack trace to the log
	  	}
	  }
	  


}
