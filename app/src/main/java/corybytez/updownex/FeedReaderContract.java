
package corybytez.updownex;

import android.provider.BaseColumns;

public final class FeedReaderContract {

    public static final  int    DATABASE_VERSION   = 1;
    public static final  String DATABASE_NAME      = "database.db";
    private static final String TEXT_TYPE          = " TEXT";
    public static final String DATE_TYPE           = " DATE";
    public static final String INT_TYPE            = " INTEGER";
    //Find out appropriate data type of the x coordinate and the y coordinate!!!!
    private static final String COMMA_SEP          = ",";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private FeedReaderContract() {}

    public static abstract class Table1 implements BaseColumns {
        public static final String TABLE_NAME       = "UpDown";
        public static final String COLUMN_NAME_COL1 = "TestDate";
        public static final String COLUMN_NAME_COL2 = "DOB";
        public static final String COLUMN_NAME_COL3 = "Age";
        public static final String COLUMN_NAME_COL4 = "Gender";
        public static final String COLUMN_NAME_COL5 = "TestingSite";
        public static final String COLUMN_NAME_COL6 = "StudyCondition";
        public static final String COLUMN_NAME_COL7 = "ItemType"; //WHAT DOES THIS REPRESENT EXACTLY???
        public static final String COLUMN_NAME_COL8 = "Image1"; //Each referenced image has it's own unique identifier.
        public static final String COLUMN_NAME_COL9 = "Image2";
        public static final String COLUMN_NAME_COL10 = "X1"; //The x-coordinate of Image1
        public static final String COLUMN_NAME_COL11 = "Y1"; //The y-coordinate of Image1
        public static final String COLUMN_NAME_COL12 = "X2";
        public static final String COLUMN_NAME_COL13 = "Y2";
        public static final String COLUMN_NAME_COL14 = "ReactionTime";
        public static final String COLUMN_NAME_COL15 = "Accuracy"; //IS THIS A BINARY VALUE????


        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                 //In yyyy-mm-dd format.
                COLUMN_NAME_COL1 + DATE_TYPE + " DEFAULT CURRENT_DATE" + COMMA_SEP +
                COLUMN_NAME_COL2 + DATE_TYPE + COMMA_SEP +
                COLUMN_NAME_COL3 + INT_TYPE + COMMA_SEP +
                COLUMN_NAME_COL4 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_COL5 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_COL6 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_COL7 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_COL8 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_COL9 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_COL10 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_COL11 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_COL12 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_COL13 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_COL14 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_COL15 + TEXT_TYPE +  " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String SHOW_ALL = "SELECT * FROM "+ TABLE_NAME;
    }
}

