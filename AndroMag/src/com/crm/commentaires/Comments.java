package com.crm.commentaires;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Comments {

	// Database fields
	private SQLiteDatabase database;
	//private SQLiteHelper dbHelper;
	private String[] allColumns = { Comment.COLUMN_ID,
			Comment.COLUMN_COMMENT };
/*
	public Comments(Context context) {
		dbHelper = new SQLiteHelper(context);
	}
*/
	public void open() throws SQLException {
	//	database = dbHelper.getWritableDatabase();
	}

	public void close() {
	//	dbHelper.close();
	}

	public Comment createComment(String comment) {
		ContentValues values = new ContentValues();
		values.put(Comment.COLUMN_COMMENT, comment);
		long insertId = database.insert(Comment.TABLE_COMMENTS, null,
				values);
	
		Cursor cursor = database.query(Comment.TABLE_COMMENTS,
				allColumns, Comment.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Comment newComment = cursorToComment(cursor);
		cursor.close();
		return newComment;
	}

	public void deleteComment(Comment comment) {
		long id = comment.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(Comment.TABLE_COMMENTS, Comment.COLUMN_ID
				+ " = " + id, null);
	}

	public List<Comment> getAllComments() {
		List<Comment> comments = new ArrayList<Comment>();

		Cursor cursor = database.query(Comment.TABLE_COMMENTS,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Comment comment = cursorToComment(cursor);
			comments.add(comment);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return comments;
	}

	private Comment cursorToComment(Cursor cursor) {
		Comment comment = new Comment();
		comment.setId(cursor.getLong(0));
		comment.setComment(cursor.getString(1));
		return comment;
	}
}