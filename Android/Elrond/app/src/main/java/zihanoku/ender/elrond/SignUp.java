package zihanoku.ender.elrond;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.support.v7.app.AlertDialog;


public class SignUp extends AppCompatActivity {
    
    EditText password_edit;
    EditText email_edit;
    EditText name_edit;
    SharedPreferences sharedpreferences;
    static final int INTERNET_REQ = 23;
    static final String REQ_TAG = "VACTIVITY";
    RequestQueue requestQueue;
    SharedPreferences.Editor editor;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    
        toolbar.setNavigationIcon(R.drawable.back);
    
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        password_edit = findViewById(R.id.password);
        email_edit = findViewById(R.id.email);
        name_edit = findViewById(R.id.full_name);
    
        requestQueue = RequestQueueSingleton.getInstance(this.getApplicationContext()).getRequestQueue();
    
        sharedpreferences = getSharedPreferences("elrond", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    
        //alertDialog();
    
    }
    
    public void openLogin(View view) {
        startActivity(new Intent(SignUp.this, Login.class));
        finish();
    }
    
    
    
    public void  signUp(View v) {
    
        boolean nameEntered = !name_edit.getText().toString().trim().isEmpty();
        boolean emailEntered = !password_edit.getText().toString().trim().isEmpty();
        boolean passwordEntered = !email_edit.getText().toString().trim().isEmpty();
    
        if (!nameEntered || !emailEntered || !passwordEntered) {
            missingInfoDialog();
        } else {
        
            String name = name_edit.getText().toString();
        final String password = password_edit.getText().toString();
        final String email = email_edit.getText().toString();
    
    
        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
            json.put("password", password);
            json.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    
        String url = "http://elrond.herokuapp.com/addnewuser";
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
                                editor.commit();
                                startActivity(new Intent(SignUp.this, ConfirmEmail.class));
                                finish();
                            } else if (outcome.equals("duplicate_email")) {
                                //handle duplicate email
                                alertDialog();
    
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
    
    
    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("That email address is alread associated with another account");
        dialog.setTitle("Email Address Error");
        dialog.setPositiveButton("Log In",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SignUp.this, Login.class));
                        finish();
                    }
                });
        dialog.setNegativeButton("Change Email",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(),"cancel is clicked",Toast.LENGTH_LONG).show();
                ;
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#4ABC96"));
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#4ABC96"));
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
    
    
    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(REQ_TAG);
        }
    }
}
