package corybytez.updownex;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class GetInfo extends ActionBarActivity {
        //ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info);
    }

    @Override  
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_info, menu);
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

    public void delete_field(View button)
    {
        //final EditText nameField = (EditText) findViewById(R.id.NameField);
        //String name = nameField.getText().toString();
       // Log.d("myTag", name);


    }


    public void setup_db(View button)
            /*
             * TO DO: Sanitize input. (Make sure that the date formate matches up)
             */

    {
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        //db.execSQL(FeedReaderContract.Table1.DELETE_TABLE);
        db.execSQL(FeedReaderContract.Table1.CREATE_TABLE);

        //Retrieving all the user input
        final EditText DOB = (EditText) findViewById(R.id.DOB);
        final EditText Age = (EditText) findViewById(R.id.Age);
        final EditText TestSite = (EditText) findViewById(R.id.TestSite);
        final EditText StudyCondition = (EditText) findViewById(R.id.StudyCondition);
        final EditText ItemType = (EditText) findViewById(R.id.ItemType);
        RadioGroup radioSexGroup = (RadioGroup) findViewById(R.id.RadioSex);
        int selectedId = radioSexGroup.getCheckedRadioButtonId();
        RadioButton radioSexButton = (RadioButton) findViewById(selectedId);


        ContentValues values = new ContentValues();
        //values.put(FeedReaderContract.Table1.COLUMN_NAME_COL1, "12/12/1212"); TAKES ON THE DEFAULT VALUE.
        values.put(FeedReaderContract.Table1.COLUMN_NAME_COL2, DOB.getText().toString());
        values.put(FeedReaderContract.Table1.COLUMN_NAME_COL3, Age.getText().toString());
        values.put(FeedReaderContract.Table1.COLUMN_NAME_COL4, radioSexButton.getText().toString());
        values.put(FeedReaderContract.Table1.COLUMN_NAME_COL5, TestSite.getText().toString());
        values.put(FeedReaderContract.Table1.COLUMN_NAME_COL6, StudyCondition.getText().toString());
        values.put(FeedReaderContract.Table1.COLUMN_NAME_COL7, ItemType.getText().toString());
        values.put(FeedReaderContract.Table1.COLUMN_NAME_COL8, "Charles"); //Stand in values - to be filled in later.
        values.put(FeedReaderContract.Table1.COLUMN_NAME_COL9, "Charles");
        values.put(FeedReaderContract.Table1.COLUMN_NAME_COL10, "Charles");
        values.put(FeedReaderContract.Table1.COLUMN_NAME_COL11, "Charles");
        values.put(FeedReaderContract.Table1.COLUMN_NAME_COL12, "Charles");
        values.put(FeedReaderContract.Table1.COLUMN_NAME_COL13, "Charles");
        values.put(FeedReaderContract.Table1.COLUMN_NAME_COL14, "Charles");
        values.put(FeedReaderContract.Table1.COLUMN_NAME_COL15, "Charles");


        long newRowId;
        newRowId = db.insert(
                FeedReaderContract.Table1.TABLE_NAME,
                null,
                values);


        Log.d("myTag", radioSexButton.getText().toString());
        //Cursor c= db.rawQuery(FeedReaderContract.Table1.SHOW_ALL, null);
        finish();
    }


}