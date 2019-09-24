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
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ConfirmEmail extends AppCompatActivity {
    
    SharedPreferences sharedpreferences;
    static final int INTERNET_REQ = 23;
    static final String REQ_TAG = "VACTIVITY";
    RequestQueue requestQueue;
    SharedPreferences.Editor editor;
    EditText codeEdit;
    TextView emailConfirmationView;
    String email;
    String code;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_email);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    
        toolbar.setNavigationIcon(R.drawable.back);
    
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConfirmEmail.this, SignUp.class));
                finish();
            }
        });
        
        codeEdit = findViewById(R.id.confirmation_text_field);
        emailConfirmationView = findViewById(R.id.the_email_to_confirm_view);
    
        sharedpreferences = getSharedPreferences("elrond", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    
        email = sharedpreferences.getString("email", "elrond");
    
        requestQueue = RequestQueueSingleton.getInstance(this.getApplicationContext()).getRequestQueue();
    
    
    }
    
    public void  confirmEmail(View v) {
        
        
        boolean codeEntered = !codeEdit.getText().toString().trim().isEmpty();
        
        
        if (!codeEntered) {
            missingInfoDialog();
        } else {
            
            
            code = codeEdit.getText().toString();
            
            
            JSONObject json = new JSONObject();
            try {
                json.put("email", email);
                json.put("code", code);
                //json.put("email", email);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            String url = "http://elrond.herokuapp.com/emailverify";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //serverResp.setText("String Response : "+ response.toString());
                            try {
                                String outcome = response.getString("outcome");
                                if (outcome.equals("done")) {
                                    //handle done
                                    editor.putInt("email_confirmed", 1);
                                    editor.commit();
                                    startActivity(new Intent(ConfirmEmail.this, MainActivity.class));
                                    finish();
                                }
                                
                                else if (outcome.equals("email_not_exists")) {
                                    //handle email not found
                                    wrongInfoDialog();
                                }
                                
                                else {
                                    //handle other errors
                                    errorDialog();
                                    
                                }
                                
                            } catch (Exception e) {
                                //handle an_error_occured
                                errorDialog();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //serverResp.setText("Error getting response" + error);
                    errorDialog();
                }
            });
            jsonObjectRequest.setTag(REQ_TAG);
            requestQueue.add(jsonObjectRequest);
            
        }
    }
    
    public void requestEmail(View view) {
    }
    
    private void missingInfoDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Please enter the verification code sent to " + email);
        dialog.setTitle("No code entered.");
        dialog.setPositiveButton("Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
        
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#4ABC96"));
    }
    
    private void wrongInfoDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("That verification code is incorrect");
        dialog.setTitle("Wrong code.");
        dialog.setPositiveButton("Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
        
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#4ABC96"));
    }
    
    private void errorDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("An error occured. Please make sure that you have an internet connection and try again.");
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
    
    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(REQ_TAG);
        }
    }
    
    
}
