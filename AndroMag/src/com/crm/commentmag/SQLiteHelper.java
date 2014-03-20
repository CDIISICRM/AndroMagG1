package com.crm.commentmag;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {



    public static final String TABLE_COMMENTS = "comments";
    public static final String COLUMN_COMMENT = "comment";

    public static final String COLUMN_ID = "_id";

	private static final String DATABASE_NAME = "commments.db";
	private static final int DATABASE_VERSION = 1;



	private static final String MAGAZINES_CREATE = "create table "
			+ Magazine.TABLE_MAGAZINE + "( " + Magazine.COLUMN_ID
			+ " integer primary key autoincrement, " + Magazine.COLUMN_NOM
			+ " text not null, " + Magazine.COLUMN_VISIBLE + " integer );";

    private static final String COMMENTS_CREATE = "create table "
            + TABLE_COMMENTS + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_COMMENT
            + " text not null);";

	
	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(MAGAZINES_CREATE);
        database.execSQL(COMMENTS_CREATE);
		String[] mags = new String[] { "Mag1", "Mag2", "Mag3" };
		ContentValues values = new ContentValues();
		for (int n=0;n<mags.length;n++){
		values.put(SQLiteHelper.COLUMN_COMMENT,mags[n]);
		long insertId = database.insert(SQLiteHelper.TABLE_COMMENTS, null,values);
		}
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(SQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
		onCreate(db);
	}


	
}