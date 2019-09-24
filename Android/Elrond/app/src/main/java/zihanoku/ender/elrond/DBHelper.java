package zihanoku.ender.elrond;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    
    public static final String DATABASE_NAME = "Elrond.db";
    
    public static final String INBOX_TABLE_NAME = "inboxdata";
    public static final String TWEET_COMMENTS_TABLE_NAME = "tweetcommentsdata";
    public static final String GROUP_TABLE_NAME = "groupdata";
    public static final String PROFILE_TABLE_NAME = "profiledata";
    public static final String TWEET_TABLE_NAME = "tweetdata";
    public static final String TWEET_LIKES_TABLE_NAME = "tweetlikesdata";
    
    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 6);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
    
        
        /*
        send_time = models.DateTimeField()
        sender = models.CharField(max_length=50)
        recepient = models.CharField(max_length=50)
        message = models.TextField()
        received_report = models.IntegerField(default=0) # 1 for yes, 0 for no
        read_report = models.IntegerField(default=0) # 1 for yes, 0 for no
         */
        db.execSQL(
                "create table inboxdata " +
                        "(id integer primary key, send_time integer, sender text,recepient text, message text,received_report integer, read_report integer, sent_report integer, alphanumeric text)"
        );
    
        /*
            send_time = models.DateTimeField()
            sender = models.CharField(max_length=50)
            message = models.TextField()
            
            group_ID = models.IntegerField()
         */
        db.execSQL(
                "create table groupdata " +
                        "(id integer primary key, send_time integer,sender text,message text, group_ID text, sent integer, alphanumeric integer)"
        );
    
        /*
            tweet_time = models.DateTimeField()
            tweet = models.TextField()
            tweet_bg = models.CharField(max_length=15)
            sender =  models.CharField(max_length=50)
         */
        db.execSQL(
                "create table tweetdata " +
                        "(id integer primary key, tweet_time integer,tweet text,sender text)"
        );
    
        /*
            full_name = models.CharField(max_length=50)
            nick_name = models.CharField(max_length=50)
            date_of_birth = models.DateTimeField()
            relationship_status = models.CharField(max_length=100)
            telephone_number = models.CharField(max_length=15)
            email_addeess = models.CharField(max_length=50)
            resident_address = models.CharField(max_length=200)
            what_makes_you_happy = models.TextField()
            what_makes_you_angry = models.TextField()
            what_makes_you_sad = models.TextField()
            
         */
        db.execSQL(
                "create table profiledata " +
                        "(id integer primary key, full_name text, nick_name text,date_of_birth integer, relationship_status text,telephone_number text, email_address text, what_makes_you_happy text, what_makes_you_angry text,what_makes_you_sad text)"
        );
        
        /*
        tweet_id = models.IntegerField()
        liker = models.CharField(max_length=50)
        like_time = models.DateTimeField()
         */
        db.execSQL(
                "create table tweetlikesdata " +
                        "(id integer primary key, tweet_id text, liker text, like_time integer)"
        );
    
        /*
        tweet_id = models.IntegerField()
        comment_by = models.CharField(max_length=50)
        comment = models.TextField()
        comment_time = models.DateTimeField()
         */
        db.execSQL(
                "create table tweetcommentsdata " +
                        "(id integer primary key, tweet_id text, comment_by text, comment text, comment_time integer)"
        );

        //////////////////////////////////////////////////////////
        db.execSQL(
                "create table conversations " +
                        "(id integer primary key, identifier text, name text, lastmsg text, updated_time integer, conv_type text)"
        );


        db.execSQL(
                "create table testing " +
                        "(id integer primary key, name text, superhero_name text, universe text)"
        );
    
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS inboxdata");
        db.execSQL("DROP TABLE IF EXISTS groupdata");
        db.execSQL("DROP TABLE IF EXISTS tweetdata");
        db.execSQL("DROP TABLE IF EXISTS profiledata");
        db.execSQL("DROP TABLE IF EXISTS tweetlikesdata");
        db.execSQL("DROP TABLE IF EXISTS tweetcommentsdata");
        db.execSQL("DROP TABLE IF EXISTS conversations");
        db.execSQL("DROP TABLE IF EXISTS testing");
        onCreate(db);
    }
    
    public boolean insertTweetComment(String tweet_id, String comment_by, String comment, int comment_time) {
        return true;
    }
    
    public boolean insertTweetLike(String tweet_id, String comment_by, String comment, int comment_time) {
        return true;
    }
    
    public boolean insertProfile(String tweet_id, String comment_by, String comment, int comment_time) {
        return true;
    }
    
    public boolean insertTweet(String tweet_id, String comment_by, String comment, int comment_time) {
        return true;
    }
    
    public boolean insertInboxMessage(String tweet_id, String comment_by, String comment, int comment_time) {
        return true;
    }
    
    public boolean insertGroupMessage(String tweet_id, String comment_by, String comment, int comment_time) {
        return true;
    }
    
    public boolean updateProfile() {
        return true;
    }
    
    public boolean getAllInboxMessages() {
        return true;
    }
    
    public boolean getRangeInboxMessages() {
        return true;
    }
    
    public boolean openInboxGetMessages() {
        return true;
    }
    
    public boolean markAsRead() {
        return true;
    }
    
    public boolean markAsDelivered() {
        return true;
    }
    
    public boolean likeTweet() {
        return true;
    }
    
    public boolean unlikeTweet() {
        return true;
    }
    
    public boolean openGroupGetMessages() {
        return true;
    }
    
    public boolean getAllGroupMessages() {
        return true;
    }
}
