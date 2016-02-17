package cs477.project.workoutitems;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Calories_MainActivity extends Activity implements OnItemSelectedListener {
	public final int ACTIVITY_RESULT = 1;
	public final int ACTIVITY_RESULT2 = 2;
	public final int ACTIVITY_RESULT3 = 3;
	public final static String PARAM1 ="com.example.fragment.P1";
	public final static String PARAM2 ="com.example.mealactivity.P1";
	private SQLiteDatabase db;
	//private DatabaseHelp dbHelper=null;
	public static final String TABLE="Meal";
	DatabaseOpenHelper dbHelper;
	List<String> list_name;
	ArrayList<FoodList> list;
	ListView listview;
	ArrayAdapter<String> adapter;
	ArrayList<String> listItems;
	static boolean first=false;
	int calorieTotal=0;
	TextView calorie;
	final Context context = this;
	ArrayList<Integer> calorieList;
	Activity mActivity;
	Context mContext;
	Spinner spinner, spinner2;
	String[] background1 = {
			"Breakfast",
			"Lunch",
			"Dinner",
	};
	String Text;
	TextView day;


	@Override   
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ate);
		mActivity = this;
		mContext = this.getApplicationContext();
		dbHelper = new DatabaseOpenHelper(this);
		list_name = new ArrayList<String>();
		list = new ArrayList<FoodList>();	
		this.getFood();	
		listview = (ListView) findViewById(R.id.listFood);
		listItems=new ArrayList<String>();
		calorieList= new ArrayList<Integer>();
		calorie=(TextView)findViewById(R.id.calorie);

		adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
		listview.setAdapter(adapter);


		setTime();



		spinner = (Spinner)findViewById(R.id.spinner);
		spinner.setAdapter(new MyAdapter2(this, R.layout.custom_spinner,
				background1));

		spinner.setOnItemSelectedListener( 		
				new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> b, View view,int num, long num2) {
						Text = b.getSelectedItem().toString();

					}
					@Override
					public void onNothingSelected(AdapterView<?> b) {
						// TODO Auto-generated method stub
					}
				}
				);
		spinner2 = (Spinner)findViewById(R.id.spinner2);
		spinner2.setAdapter(new MyAdapter(this, R.layout.custom_spinner,
				list_name));
		spinner2.setOnItemSelectedListener(this);

		listview.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?>parent, View view, final int position, final long id){
				//if(moveToPosition(position)){
				//db.delete(table, whereClause, whereArgs);
				final String food=	(String) parent.getItemAtPosition(position);
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

				alertDialog.setTitle("Delete");
				alertDialog.setMessage("Are you sure you want delete " +food+" ?");
				//final String inName = ((TextView) view.findViewById(R.id.text1)).getText().toString();
				// Setting Positive Button
				alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int which) {
						//    		            	db.delete(DatabaseHelp.TABLE, DatabaseHelp.COL_NAME+ "=?",  new String[] {inName});
						//    		            	finish();
						removeFood(food, position);
						//finish();
					}
				});

				// Setting Negative Button
				alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

				// Showing Alert Message
				alertDialog.show();
				//}
			}});


	}

	public void addToList(String Text, int position){
		int cal=0;
		if(Text==" "){
		}
		else if(first == false){
			first = true;
		}
		else{
			cal=list.get(position).getCalorie();
			calorieTotal=calorieTotal+cal;
			calorie.setText(""+calorieTotal);
			calorieList.add(cal);
			listItems.add(Text);
			adapter.notifyDataSetChanged();
		}
	}

	public void removeFood(String Text, int position){
		int cal=0;
		listItems.remove(position);
		cal=calorieList.get(position);
		calorieList.remove(position);
		calorieTotal=calorieTotal-cal;
		calorie.setText(""+calorieTotal);
		adapter.notifyDataSetChanged();
	}



	public void setTime(){
		Calendar c = Calendar.getInstance();
		//System.out.println("Current time => " + c.getTime());
		SimpleDateFormat df = new SimpleDateFormat("MMM-dd-yyyy");
		String formattedDate = df.format(c.getTime());
		day= (TextView)findViewById(R.id.date);
		day.setText(formattedDate);
	}



	public FoodList create(String s,int d){
		return new FoodList(s,d);
	}

	////Get all the list in the database

	private List<FoodList> getFood() {
		list.clear();		
		list_name.clear();
		list.add(create("",0));
		list_name.add(" ");
		if (db == null) db = dbHelper.getWritableDatabase();
		String[] COLUMNS = new String[]{"_id",dbHelper.COL_FOOD,dbHelper.COL_CAL};
		String selection = dbHelper.COL_CAL + " = ?";


		Cursor c = db.rawQuery("SELECT * FROM FoodDatabase", null);
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			list.add(create(c.getString(1),c.getInt(2)));
			list_name.add(c.getString(1));
		}
		return list;     
	}



	@Override
	public void onItemSelected(AdapterView<?> parent, View arg1, int pos,
			long arg3) {
		// TODO Auto-generated method stub
		if(first == false){
			first = true;
		}else{
			addToList(parent.getItemAtPosition(pos).toString(),pos);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	public void enterfood(View view){
		addFood();
	}

	public void sendMeal(View view){
		db=dbHelper.getWritableDatabase();
		String foods = "";
		for(int i = 0; i  < adapter.getCount(); i ++){
			foods += adapter.getItem(i) + "/";
		}
		dbHelper.addRowCalorie(day.getText().toString(), Text, foods, calorie.getText().toString());
		finish();

	}

	private void addFood(){
		// TODO Auto-generated method stub
		final Dialog dialog = new Dialog(mActivity);
		// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.customdialog);
		dialog.setTitle("Add New Item To Database");
		final TextView work = (TextView)dialog.findViewById(R.id.edit_work);
		work.setText("Enter Food Name:");
		final TextView descript = (TextView)dialog.findViewById(R.id.edit_descript);
		descript.setText("Enter amount calories:");
		final EditText work_name = (EditText)dialog.findViewById(R.id.workout_name);
		final EditText work_descript = (EditText)dialog.findViewById(R.id.workout_descript);
		work_name.setHint("Enter name of the food");
		work_descript.setHint("Enter amount calories of the food");
		work_descript.setInputType(InputType.TYPE_CLASS_NUMBER);
		Button button = (Button)dialog.findViewById(R.id.blue_submit);    
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if(work_descript.getText().toString().trim().equals("") || work_name.getText().toString().trim().equals("")){
					Toast.makeText(mContext, "Not Enough Fields Entered", 
							Toast.LENGTH_LONG).show();
				}						
				else{
					dbHelper.addRowFood( work_name.getText().toString(),Integer.parseInt(work_descript.getText().toString()));
					list.add(create(work_name.getText().toString(), Integer.parseInt(work_descript.getText().toString())));
					list_name.add(work_name.getText().toString());
					adapter.notifyDataSetChanged();	
					dialog.dismiss();
				}
			}
		});
		////CANCEl BUTTON
		Button button2 = (Button)dialog.findViewById(R.id.blue_cancel);    
		button2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});



		////Get the right window...For this I set up for 7 inches tablet only.
		dialog.show();
		Display display =((WindowManager)mActivity.getSystemService(mContext.WINDOW_SERVICE)).getDefaultDisplay();
		int width = display.getWidth();
		int height=display.getHeight();

		Log.v("width", width+"");
		dialog.getWindow().setLayout((6*width)/7,(4*height)/9);

	}


	public class MyAdapter extends ArrayAdapter<String> {

		public MyAdapter(Context ctx, int txtViewResourceId, List<String> objects) {
			super(ctx, txtViewResourceId, objects);
		}

		@Override
		public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
			return getCustomView(position, cnvtView, prnt);
		}
		@Override
		public View getView(int pos, View cnvtView, ViewGroup prnt) {
			return getCustomView(pos, cnvtView, prnt);
		}
		public View getCustomView(int position, View convertView,
				ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View mySpinner = inflater.inflate(R.layout.custom_spinner, parent,
					false);
			TextView main_text = (TextView) mySpinner
					.findViewById(R.id.custom_text_spinner);
			main_text.setText(list_name.get(position));



			return mySpinner;
		}
	}


	public class MyAdapter2 extends ArrayAdapter<String> {

		public MyAdapter2(Context ctx, int txtViewResourceId, String[] objects) {
			super(ctx, txtViewResourceId, objects);
		}

		@Override
		public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
			return getCustomView(position, cnvtView, prnt);
		}
		@Override
		public View getView(int pos, View cnvtView, ViewGroup prnt) {
			return getCustomView(pos, cnvtView, prnt);
		}
		public View getCustomView(int position, View convertView,
				ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View mySpinner = inflater.inflate(R.layout.custom_spinner, parent,
					false);
			TextView main_text = (TextView) mySpinner
					.findViewById(R.id.custom_text_spinner);
			main_text.setText(background1[position]);



			return mySpinner;
		}
	}





}
