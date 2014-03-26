package com.crm.commentaires;
public class Comment {
	   public static final String TABLE_COMMENTS = "comments";
	   public static final String COLUMN_COMMENT = "comment";
	   public static final String COLUMN_ID = "_id";


   public static final String COMMENTS_CREATE = "create table "
           + TABLE_COMMENTS + "( " + COLUMN_ID
           + " integer primary key autoincrement, " + COLUMN_COMMENT
           + " text not null);";
   
	private long id;
	private String comment;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return comment;
	}
}