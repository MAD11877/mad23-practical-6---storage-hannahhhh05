package sg.edu.np.mad.madpractical;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserDB";
    private static final String TABLE_USERS = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_FOLLOWED = "followed";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_FOLLOWED + " INTEGER"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Generate and insert 20 User data into the table
//        Random random = new Random();
//        for (int i = 0; i < 20; i++) {
//            String name = "User " + i;
//            String description = "Description " + i;
//            boolean followed = random.nextBoolean();
//
//            ContentValues values = new ContentValues();
//            values.put(KEY_NAME, name);
//            values.put(KEY_DESCRIPTION, description);
//            values.put(KEY_FOLLOWED, followed ? 1 : 0);
//
//            db.insert(TABLE_USERS, null, values);
//        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_DESCRIPTION, user.getDescription());
        values.put(KEY_FOLLOWED, user.isFollowed() ? 1 : 0);

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> userList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(KEY_ID);
            int nameIndex = cursor.getColumnIndex(KEY_NAME);
            int descriptionIndex = cursor.getColumnIndex(KEY_DESCRIPTION);
            int followedIndex = cursor.getColumnIndex(KEY_FOLLOWED);
            do {
                int id = -1;
                String name = "";
                String description = "";
                boolean followed = false;

                if (idIndex >= 0)
                    id = cursor.getInt(idIndex);
                if (nameIndex >= 0)
                    name = cursor.getString(nameIndex);
                if (descriptionIndex >= 0)
                    description = cursor.getString(descriptionIndex);
                if (followedIndex >= 0) {
                    int followedValue = cursor.getInt(followedIndex);
                    followed = (followedValue == 1);
                }

                User user = new User(name, description, id, followed);
                userList.add(user);

                Log.v("DBHandler", "User added: " + user.getName() + " (Followed: " + user.isFollowed() + ")");
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return userList;
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_DESCRIPTION, user.getDescription());
        values.put(KEY_FOLLOWED, user.isFollowed() ? 1 : 0);

        db.update(TABLE_USERS, values, KEY_ID + " = ?", new String[]{String.valueOf(user.getId())});
        db.close();
    }
}
