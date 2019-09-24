package zihanoku.ender.elrond;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class NewConvo extends AppCompatActivity {
    WebView theWebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_convo);
    
        WebView htmlWebView = (WebView) findViewById(R.id.thewebview);
        WebSettings webSetting = htmlWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(false);
        htmlWebView.loadUrl("file:///android_asset/newConvo.html");
    }


    public void searchForUser() {
        //search entire users database
        //send search query to server
        //pagination
        //return json with results
    }

    public void createNewGroup() {
        //create group and register it with server
        //pick initial members from peeps
        //non-peeps can be invited later
    }
}
