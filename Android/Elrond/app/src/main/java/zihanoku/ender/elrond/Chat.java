package zihanoku.ender.elrond;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Chat extends AppCompatActivity {
    WebView htmlWebView;
    EditText messageInput;
    SharedPreferences sharedpreferences;
    String email;
    Functions functions;
    RequestQueue requestQueue;
    DBHelper dbHelper;
    static final int INTERNET_REQ = 23;
    static final String REQ_TAG = "VACTIVITY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    
    
        toolbar.setNavigationIcon(R.drawable.back);
    
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        htmlWebView = (WebView) findViewById(R.id.chat_webview);
    
        
        
        WebSettings webSetting = htmlWebView.getSettings();
        
        //webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(false);
        htmlWebView.loadUrl("file:///android_asset/chat.html");

        messageInput = findViewById(R.id.messageInput);
        email = sharedpreferences.getString("email", "elrond");

        dbHelper = new DBHelper(this);
    }
    
    public void sendMessage(View view) {
        //Toast.makeText(this, "Message sent!", Toast.LENGTH_SHORT).show();

        String message = messageInput.getText().toString().trim();
        messageInput.setText("");

        if(!message.isEmpty()) {
            //htmlWebView.loadUrl("javascript:displaySentMessage('Music video by SCANDAL (JP) performing Departure full ver', '15:38')");

            //displaySentMessage(message, time, alphanumeric);

            String recepient = "siziba.internet@gmail.com";
            long send_time = functions.getTimeStamp();
            String alphanumeric = functions.alphaNumeric();

            saveInboxMessage(message, sender, recepient, send_time, alphanumeric);

            JSONObject json = new JSONObject();
            try {
                json.put("message", message);
                json.put("sender", email);
                json.put("recepient", recepient);
                json.put("send_time", send_time);
                json.put("alphanumeric", alphanumeric);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String url = "http://elrond.herokuapp.com/sendinboxmessage";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //serverResp.setText("String Response : "+ response.toString());
                            try {
                                String outcome = response.getString("outcome");
                                if (outcome.equals("sent")) {
                                    //handle done
                                    updateSentStatusForInboxMessage(alphanumeric);

                                } else{
                                    //handle duplicate email
                                    couldNotSendDialog();

                                }

                            } catch (Exception e) {
                                //handle an_error_occured
                                couldNotSendDialog();

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //serverResp.setText("Error getting response" + error);
                    couldNotSendDialog();
                }
            });
            jsonObjectRequest.setTag(REQ_TAG);
            requestQueue.add(jsonObjectRequest);




        }


    }


    private void couldNotSendDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Could not send some messages. Messages will resend automatically once connection is re-established.");
        dialog.setTitle("Error");
        dialog.setPositiveButton("Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
                    }
                });;
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#4ABC96"));
    }

    private void errorDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Wiiiiiiiiiiiiiiiiiiiiiiii.");
        dialog.setTitle("Error");
        dialog.setPositiveButton("Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
                    }
                });;
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#4ABC96"));
    }


    
    public void backHome(View view) {
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(REQ_TAG);
        }
    }

    public void saveInboxMessage(String message, String sender, String recepient,long send_time, String alphanumeric){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        //(id integer primary key, send_time integer, sender text,recepient text, message text,received_report integer, read_report integer, alphanumeric text)
        values.put("send_time", send_time);
        values.put("sender", sender);
        values.put("message", message);
        values.put("received_report", 0);
        values.put("sent_report", 0);
        values.put("read_report", 0);
        values.put("alphanumeric", alphanumeric);
        values.put("recepient", recepient);
        long the_id =  db.insert("inboxdata", null, values);

    }

    public void updateSentStatusForInboxMessage(String alphanumeric) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sent_report", 1);
        String selection = "alphanumeric = ?";
        String[] selectionArgs = { alphanumeric };

        int count = db.update(
                "inboxdata",
                values,
                selection,
                selectionArgs);
        }
        //call javascript add sent icon


    public void loadConversationMassages(String theRecepient) {


    }

    public void displayForeignMessage(String theRecepient) {


    }
    
    
}
