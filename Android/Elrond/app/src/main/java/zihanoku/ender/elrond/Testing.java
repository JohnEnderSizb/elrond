package zihanoku.ender.elrond;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;


public class Testing extends AppCompatActivity {
    public TextView theTextview;
    DBHelper dbHelper;
    EditText nameEdit;
    EditText superNameEdit;
    EditText universeEdit;
    TextView inputResults;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        dbHelper = new DBHelper(this);

        nameEdit = findViewById(R.id.name);
        superNameEdit = findViewById(R.id.superName);
        universeEdit = findViewById(R.id.universe);
        inputResults = findViewById(R.id.inputResults);

    
    }


    public void submit(View v) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String name = nameEdit.getText().toString();
        String superName = superNameEdit.getText().toString();
        String universe = universeEdit.getText().toString();

        //values.put(FeedEntry.COLUMN_NAME_TITLE, title);
        values.put("name", name);
        values.put("superhero_name", superName);
        values.put("universe", universe);
        long the_id =  db.insert("testing", null, values);
        inputResults.setText(the_id + "");
        nameEdit.setText("");
        superNameEdit.setText("");
        universeEdit.setText("");

    }

    public void marvel(View v) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

    }

    public void load(View v) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                "testing",   // The table to query
                null,
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        int rows = cursor.getCount();
        inputResults.setText("Got " + rows + "rows.");

        String results = "";
        String name;
        String superName;
        String universe;
        if(rows > 0) {
            //cursor.moveToFirst();

            while(cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex("name"));
                superName = cursor.getString(cursor.getColumnIndex("superhero_name"));
                universe = cursor.getString(cursor.getColumnIndex("universe"));
                results += name + ", " + superName + ", " + universe + "\n";
            }
            cursor.close();
            //findViewById(R.id.theResults).setText(results);
            TextView theResults = findViewById(R.id.theResults);
            theResults.setText(results);
        }


    }

    public void modify(View v) {

        String name = nameEdit.getText().toString();
        String superName = superNameEdit.getText().toString();
        String universe = universeEdit.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "UPDATE testing SET " +
                        "superhero_name = " + superName + "," +
                        "universe = " + universe + " " +
                        "WHERE name = " + name;

        db.execSQL("UPDATE ");


    }

    public void dc(View v) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
