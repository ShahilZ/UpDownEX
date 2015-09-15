
package corybytez.updownex;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, FeedReaderContract.DATABASE_NAME, null, FeedReaderContract.DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase db)
    //Deleted the existing table before creating the new one. REMOVE DELETE EXECUTION BEFORE DEPLOYMENT!!
    {
        //db.execSQL(FeedReaderContract.Table1.DELETE_TABLE);
        db.execSQL(FeedReaderContract.Table1.CREATE_TABLE);
    }

    // Method is called during an upgrade of the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(FeedReaderContract.Table1.DELETE_TABLE);
        onCreate(db);
    }
}
