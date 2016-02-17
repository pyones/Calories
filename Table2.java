package cs477.project.workoutitems;




import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Table extends Activity {
	DatabaseOpenHelper myDatabaseHelper;
	private Cursor mContacts;
	private SQLiteDatabase db;
	private DatabaseOpenHelper dbHelper;
	int counter=0;
	private static final String[] firstNames = {  };
	private static final String[] lastNames = {  };
	private static final String[] date={ };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tablerow);

		dbHelper = new DatabaseOpenHelper(this);
		if (db == null) db = dbHelper.getWritableDatabase();
		String[] COLUMNS = new String[]{"_id",DatabaseOpenHelper.COLUMN_ID, 
				DatabaseOpenHelper.COL_FOOD,DatabaseOpenHelper.COL_CAL};

		String[]from={DatabaseOpenHelper.COLUMN_ID};
		int[] to={android.R.id.text1};
		
		mContacts = db.rawQuery("SELECT * FROM CalorieDaily", null);
		// Display contacts in a ListView
		//cursor.moveToFirst();
		String sucker, sucker1, sucker2,sucker3;

		for(mContacts.moveToFirst(); !mContacts.isAfterLast(); mContacts.moveToNext()){
			sucker=mContacts.getString(1);
			sucker1=mContacts.getString(2);
			sucker2=mContacts.getString(3);
			sucker3=mContacts.getString(4);
			fillTable(sucker,sucker1,sucker2,sucker3);
		}

	}




//	public void fillTable() {
//		int rowCount = firstNames.length;
//		Log.d("Fill Table", "rowCount = " + rowCount);
//		TableLayout table = (TableLayout) this.findViewById(R.id.tablelayout);
//		for (int i = 0; i < rowCount; i++) {
//
//			fillRow2(table, i);
//		}
//	}
//
//
//
//	public void fillRow2(TableLayout table, int noRow) {
//		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View fullRow = inflater.inflate(R.layout.tablerow, null, false);
//		TextView nr = (TextView) fullRow.findViewById(R.id.nr);
//		nr.setText(String.valueOf(noRow + 1));
//		TextView fName = (TextView) fullRow.findViewById(R.id.fName);
//		fName.setText(firstNames[noRow]);
//		fName.setId(noRow + 1);
//		TextView lName = (TextView) fullRow.findViewById(R.id.lName);
//		lName.setText(lastNames[noRow]);
//		TextView day= (TextView)fullRow.findViewById(R.id.date);
//		day.setText(date[noRow]);
//		table.addView(fullRow);
//	}
	public void fillTable(String date, String type, String food, String calories) {


		TableLayout table = (TableLayout) this.findViewById(R.id.tablelayout);
		fillRow(table, date,type, food, calories);
		counter++;
	}

	public void fillRow(TableLayout table, String date, String type, String food, String calories){
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View fullRow = inflater.inflate(R.layout.tablerow_1, null, false);
		TextView nr = (TextView) fullRow.findViewById(R.id.nr);
		nr.setText(date);
		TextView fName = (TextView) fullRow.findViewById(R.id.fName);
		fName.setText(type);
		TextView lName = (TextView) fullRow.findViewById(R.id.lName);
		lName.setText(food);
		TextView day= (TextView)fullRow.findViewById(R.id.date);
		day.setText(calories);
		table.addView(fullRow);
		Log.i("YOTOOO","ADD HERE");
	}




}