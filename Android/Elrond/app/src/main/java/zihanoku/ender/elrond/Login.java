package zihanoku.ender.elrond;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    EditText password_edit;
    EditText email_edit;
    SharedPreferences sharedpreferences;
    static final int INTERNET_REQ = 23;
    static final String REQ_TAG = "VACTIVITY";
    RequestQueue requestQueue;
    SharedPreferences.Editor editor;
    String password;
    String email;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        password_edit = findViewById(R.id.password);
        email_edit = findViewById(R.id.email);
        sharedpreferences = getSharedPreferences("elrond", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    
        requestQueue = RequestQueueSingleton.getInstance(this.getApplicationContext())
                .getRequestQueue();
    
    
    }
    
    
    public void openSignUp(View view) {
        startActivity(new Intent(Login.this, SignUp.class));
    }
    
    public void login(View view) {
        boolean passwordEntered = !password_edit.getText().toString().trim().isEmpty();
        boolean emailEntered = !email_edit.getText().toString().trim().isEmpty();
        
    
        if (!emailEntered || !passwordEntered) {
            missingInfoDialog();
        } else {
        
            
            password = password_edit.getText().toString();
            email = email_edit.getText().toString();
        
        
            JSONObject json = new JSONObject();
            try {
                json.put("password", password);
                json.put("email", email);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        
            String url = "http://elrond.herokuapp.com/loginuser";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //serverResp.setText("String Response : "+ response.toString());
                            try {
                                String outcome = response.getString("outcome");
                                if (outcome.equals("done")) {
                                    //handle done
                                    editor.putString("email", email);
                                    editor.putString("password", password);
                                    editor.putInt("email_confirmed", 1);
                                    editor.commit();
                                    startActivity(new Intent(Login.this, MainActivity.class));
                                    finish();
                                } else if (outcome.equals("incorrect")) {
                                    //handle duplicate email
                                    wrongInfoDialog();
                                
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
    
    
    private void missingInfoDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Fill out all fields.");
        dialog.setTitle("Missing Information");
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
        dialog.setMessage("Email or password incorrect");
        dialog.setTitle("Access Denied!");
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(REQ_TAG);
        }
    }
}