package zihanoku.ender.elrond;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class Group extends AppCompatActivity {
    DBHelper dbHelper;
    SharedPreferences sharedpreferences;
    static final int INTERNET_REQ = 23;
    static final String REQ_TAG = "VACTIVITY";
    RequestQueue requestQueue;
    EditText messageEdit;
    String messageToSend;
    String email;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    
        toolbar.setNavigationIcon(R.drawable.back);
    
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    
        WebView htmlWebView = (WebView) findViewById(R.id.group_webview);
        WebSettings webSetting = htmlWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(false);
        htmlWebView.loadUrl("file:///android_asset/group.html");
    
        dbHelper = new DBHelper(Group.this);
    
        sharedpreferences = getSharedPreferences("elrond", Context.MODE_PRIVATE);
    
        requestQueue = RequestQueueSingleton.getInstance(this.getApplicationContext())
                .getRequestQueue();
        
        messageEdit = findViewById(R.id.message_edit);
    
        email = sharedpreferences.getString("email", "elrond");
    
    }
    
    public void sendMessage(View view) {
        String theMessage = messageEdit.getText().toString();
        boolean messageEntered = !messageEdit.getText().toString().trim().isEmpty();
    
    
        if (!messageEntered) {
            ;
        } else {
    
    
            messageToSend = messageEdit.getText().toString().trim();
            messageEdit.setText("");

            //displaySentGroupMessage();


            String group_ID = "ender";
            long send_time = functions.getTimeStamp();
            String alphanumeric = functions.alphaNumeric();
    
            JSONObject json = new JSONObject();
            try {
                json.put("group_ID", group_ID);
                json.put("message", messageToSend);
                json.put("sender", email);
                json.put("alphanumeric", alphanumeric);
                json.put("send_time", send_time);
    
            } catch (JSONException e) {
                e.printStackTrace();
            }
        
            String url = "http://10.42.0.1:8000/sendgroupmessage";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //serverResp.setText("String Response : "+ response.toString());
                            try {
                                String outcome = response.getString("outcome");
                                if (outcome.equals("sent")) {
                                    //handle successful sent
                                    //updateSentStatusForGroupMessage(alphanumeric)
                                } else {
                                    //schedule for retry
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
                    couldNotSendDialog();
                    //schedule for retry
                }
            });
            jsonObjectRequest.setTag(REQ_TAG);
            requestQueue.add(jsonObjectRequest);
        
        }
    
    }//sendMessage

    }
    
    
    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(REQ_TAG);
        }
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

    public void populateChatOnOpen() {

    }

    public void displayNewForeignMessage() {


    }


    
}
