package ante.resetar.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class OnlineShopDbHelper extends SQLiteOpenHelper {
    private final String TABLE1_NAME = "korisnici";
    public static final String TABLE1_USERNAME = "username";
    public static final String TABLE1_MAIL = "mail";
    public static final String TABLE1_PASSWORD = "password";
    public static final String TABLE1_ID = "ID";

    public OnlineShopDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE1_NAME +
                " (" + TABLE1_USERNAME + " TEXT, " +
                TABLE1_MAIL + " TEXT, " +
                TABLE1_PASSWORD + " TEXT, " +
                TABLE1_ID + " INTEGER PRIMARY KEY AUTOINCREMENT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertUser(User user) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TABLE1_USERNAME, user.getUsername());
        values.put(TABLE1_MAIL, user.getMail());
        values.put(TABLE1_PASSWORD, user.getPassword());

        db.insert(TABLE1_NAME, null, values);
        close();
    }

    public void deleteUser(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE1_NAME, TABLE1_ID + " =?", new String[]{String.valueOf(id)});
        close();
    }

    public User[] readUsers() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE1_NAME, null, null, null, null, null, null);

        if (cursor.getCount() <= 0) {
            return null;
        }
        User[] users = new User[cursor.getCount()];
        int i = 0;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            users[i++] = createUser(cursor);
        }

        close();
        return users;
    }

    public User readUser(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE1_NAME, null, TABLE1_ID + " =?", new String[] {String.valueOf(id)}, null, null, null);

        if (cursor.getCount() <= 0) {
            return null;
        }

        cursor.moveToFirst();

        User user = createUser(cursor);

        close();
        return user;
    }

    private User createUser(Cursor cursor) {
        String username = cursor.getString(cursor.getColumnIndexOrThrow(TABLE1_USERNAME));
        String mail = cursor.getString(cursor.getColumnIndexOrThrow(TABLE1_MAIL));
        String passoword = cursor.getString(cursor.getColumnIndexOrThrow(TABLE1_PASSWORD));

        return new User(username, mail, passoword);
    }

    public boolean correctUsernameAndPassword(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();

        // Define the columns you want to retrieve
        String[] projection = {TABLE1_ID};

        // Define the selection criteria
        String selection = TABLE1_USERNAME + " = ? AND " + TABLE1_PASSWORD + " = ?";

        // Define the selection arguments
        String[] selectionArgs = {username, password};

        // Perform the query
        Cursor cursor = db.query(
                TABLE1_NAME,    // The table to query
                projection,     // The columns to return
                selection,      // The columns for the WHERE clause
                selectionArgs,  // The values for the WHERE clause
                null,           // Don't group the rows
                null,           // Don't filter by row groups
                null            // The sort order
        );

        // Check if any row was returned
        boolean correctCredentials = cursor.moveToFirst();

        // Close the cursor and database connection
        cursor.close();
        db.close();

        return correctCredentials;
    }

    public String getEmailByUsername(String username) {
        SQLiteDatabase db = getReadableDatabase();

        // Define the columns you want to retrieve
        String[] projection = {TABLE1_MAIL};

        // Define the selection criteria
        String selection = TABLE1_USERNAME + " = ?";

        // Define the selection arguments
        String[] selectionArgs = {username};

        // Perform the query
        Cursor cursor = db.query(
                TABLE1_NAME,    // The table to query
                projection,     // The columns to return
                selection,      // The columns for the WHERE clause
                selectionArgs,  // The values for the WHERE clause
                null,           // Don't group the rows
                null,           // Don't filter by row groups
                null            // The sort order
        );

        String email = null;
        if (cursor.moveToFirst()) {
            email = cursor.getString(cursor.getColumnIndexOrThrow(TABLE1_MAIL));
        }

        // Close the cursor and database connection
        cursor.close();
        db.close();

        return email;
    }

    public boolean userExists(String username) {
        SQLiteDatabase db = getReadableDatabase();

        // Define the columns you want to retrieve
        String[] projection = {TABLE1_ID};

        // Define the selection criteria
        String selection = TABLE1_USERNAME + " = ?";

        // Define the selection arguments
        String[] selectionArgs = {username};

        // Perform the query
        Cursor cursor = db.query(
                TABLE1_NAME,    // The table to query
                projection,     // The columns to return
                selection,      // The columns for the WHERE clause
                selectionArgs,  // The values for the WHERE clause
                null,           // Don't group the rows
                null,           // Don't filter by row groups
                null            // The sort order
        );

        // Check if any row was returned
        boolean exists = cursor.moveToFirst();

        // Close the cursor and database connection
        cursor.close();
        db.close();

        return exists;
    }

    public boolean updatePassword(String username, String newPassword) {
        SQLiteDatabase db = getWritableDatabase();

        // Define the ContentValues object to hold the new password value
        ContentValues values = new ContentValues();
        values.put(TABLE1_PASSWORD, newPassword);

        // Define the selection criteria
        String selection = TABLE1_USERNAME + " = ?";

        // Define the selection argument
        String[] selectionArgs = {username};

        // Perform the update operation
        int rowsAffected = db.update(TABLE1_NAME, values, selection, selectionArgs);
        close();

        return rowsAffected > 0;
    }

    public boolean correctPassword(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();

        // Define the columns you want to retrieve
        String[] projection = {TABLE1_ID};

        // Define the selection criteria
        String selection = TABLE1_USERNAME + " = ? AND " + TABLE1_PASSWORD + " = ?";

        // Define the selection arguments
        String[] selectionArgs = {username, password};

        // Perform the query
        Cursor cursor = db.query(
                TABLE1_NAME,    // The table to query
                projection,     // The columns to return
                selection,      // The columns for the WHERE clause
                selectionArgs,  // The values for the WHERE clause
                null,           // Don't group the rows
                null,           // Don't filter by row groups
                null            // The sort order
        );

        // Check if any row was returned
        boolean correctPassword = cursor.moveToFirst();

        // Close the cursor and database connection
        cursor.close();
        db.close();

        return correctPassword;
    }

}
