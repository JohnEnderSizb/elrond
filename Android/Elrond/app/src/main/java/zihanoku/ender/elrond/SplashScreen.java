package zihanoku.ender.elrond;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SplashScreen extends AppCompatActivity {
    
    SharedPreferences sharedpreferences;
    String password;
    String email;
    int email_confirmed;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_splash_screen);
    
        sharedpreferences = getSharedPreferences("elrond",Context.MODE_PRIVATE);
    
        password = sharedpreferences.getString("password", "elrond");
    
        email = sharedpreferences.getString("email", "elrond");
        
        email_confirmed = sharedpreferences.getInt("email_confirmed", 0);
    
        //SharedPreferences.Editor editor = sharedpreferences.edit();
        //editor.putInt("email_confirmed", 0);
    
        //email_confirmed = 0;
        
        //email = "elrond";
        //password = "elrond";
    
    
    
    
    
        new Thread(new Runnable() {
            public void run() {
            
                doSomeWork();
            
                //startActivity(new Intent(SplashScreen.this, MainActivity.class));
                //finish();
                
                if(email.equals("elrond") && password.equals("elrond")) {
                    startActivity(new Intent(SplashScreen.this, Login.class));
                    finish();
                }
                else if(email_confirmed == 0) {
                    startActivity(new Intent(SplashScreen.this, ConfirmEmail.class));
                    finish();
                }
                
                else {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                }
            
            }
            //---do some long running work here---
            private void doSomeWork()
            {
                try {
                    //---simulate doing some work---
                    Thread.sleep(1200);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
}
