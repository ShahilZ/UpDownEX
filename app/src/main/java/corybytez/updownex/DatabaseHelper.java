
package corybytez.updownex;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        //super(context, FeedReaderContract.DATABASE_NAME, null, FeedReaderContract.DATABASE_VERSION);

        super(context, context.getExternalFilesDir(null).getAbsolutePath() + "/" + FeedReaderContract.DATABASE_NAME, null,
                FeedReaderContract.DATABASE_VERSION);
       // super(context, Environment.getExternalStorageDirectory() + FeedReaderContract.DATABASE_NAME, null,
        //        FeedReaderContract.DATABASE_VERSION);
        //Data is stored at internal storage/ android/ data/corybytez/ file/ dtabase.db
        //System.out.println("context.getExternalFilesDir(null) " + context.getExternalFilesDir(null));
        System.out.println(Environment.getExternalStorageDirectory() + " Environment.getExternalStorageDirectory()");
        System.out.println("DB Path @ " + context.getDatabasePath(FeedReaderContract.DATABASE_NAME));
       //System.out.println("SerahTag " +  context.getExternalFilesDir(null).getAbsolutePath() + "/" + FeedReaderContract.DATABASE_NAME);
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
