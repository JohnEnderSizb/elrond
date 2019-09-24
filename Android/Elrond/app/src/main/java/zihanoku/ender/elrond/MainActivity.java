package zihanoku.ender.elrond;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    android.support.design.widget.TabLayout.Tab tab;
    FloatingActionButton fab;
    
    
    
    int currentTab = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
    
    
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        fab = findViewById(R.id.fab);
        fab.setImageResource(android.R.drawable.stat_notify_chat);
        fab.setColorFilter(Color.GREEN);
        
        /*
        tab = tabLayout.newTab().setIcon(android.R.drawable.sym_action_chat);
        tab.getIcon().setColorFilter(Color.parseColor("#E62C6E"), PorterDuff.Mode.SRC_IN);
        tabLayout.addTab(tab);
    
        tab = tabLayout.newTab().setIcon(android.R.drawable.ic_menu_view);
        tab.getIcon().setColorFilter(Color.parseColor("#9E9999"), PorterDuff.Mode.SRC_IN);
        tabLayout.addTab(tab);
    
        tab = tabLayout.newTab().setIcon(android.R.drawable.star_on);
        tab.getIcon().setColorFilter(Color.parseColor("#9E9999"), PorterDuff.Mode.SRC_IN);
        tabLayout.addTab(tab);
        */
        
        //tabLayout.addTab(tabLayout.newTab().setIcon(android.R.drawable.ic_dialog_dialer));
        
        //tabLayout.addTab(tabLayout.newTab().setIcon(android.R.drawable.ic_dialog_map));
        
        
        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("Posts"));
        tabLayout.addTab(tabLayout.newTab().setText("Peeps"));
        
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(4); //do not recreate
        final PagerAdapter adapter = new TabPagerAdapter (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
    
                currentTab  = tab.getPosition();
                
                //Toast.makeText(getApplicationContext(), currentTab + "", Toast.LENGTH_SHORT).show();
    
                switch (currentTab){
                    case 0:
                        fab.show();
                        fab.setImageResource(android.R.drawable.stat_notify_chat);
                        fab.setColorFilter(Color.GREEN);
                        break;
                    case 1:
                        fab.show();
                        fab.setImageResource(android.R.drawable.ic_input_add);
                        fab.setColorFilter(Color.GREEN);
                        break;
                    case 2:
                        fab.hide();
                        break;
                }
    
            }
        
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //tab.getIcon().setColorFilter(Color.parseColor("#9E9999"), PorterDuff.Mode.SRC_IN);
                //fab.setImageResource(android.R.drawable.stat_notify_chat);
    
            }
        
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            
            }
        });
    
    
        //FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                try {
                    switch (currentTab){
                        case 0:
                            //Snackbar.make(view, "New Message", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            startActivity(new Intent(MainActivity.this, NewConvo.class));
                            break;
                        case 1:
                            Snackbar.make(view, "New Post", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            break;
                    }
                } catch (Exception e) {
                    ;
                }
                
            }
        });
        
        
        
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
    
            Toast.makeText(getApplicationContext(), "Settings Selected", Toast.LENGTH_SHORT).show();
            
            return true;
        }
    
        if (id == R.id.action_help) {
        
            Toast.makeText(getApplicationContext(), "Settings Help", Toast.LENGTH_SHORT).show();
        
            return true;
        }
    
        if (id == R.id.action_log) {
        
            Toast.makeText(getApplicationContext(), "Settings Log", Toast.LENGTH_SHORT).show();
        
            return true;
        }
    
        if (id == R.id.action_about) {
        
            Toast.makeText(getApplicationContext(), "Settings About", Toast.LENGTH_SHORT).show();
        
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    
    /*
    Database:
    
    1 INBOX
    
    
    2. GROUP
    
    
    3. PROFILE
    
    4. TWEET
    
    5. TWEET LIKES
    
    6. TWEET COMMENTS
    
    
    
    
    
    
    
    
    
     */
    
   
}
