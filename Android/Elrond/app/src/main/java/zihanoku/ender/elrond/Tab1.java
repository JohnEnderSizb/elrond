package zihanoku.ender.elrond;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1 extends Fragment {
    
    
    public Tab1() {
        // Required empty public constructor
    }
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab1, container, false);
        
        
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        WebView htmlWebView = (WebView) getActivity().findViewById(R.id.tab1_webview);
        WebSettings webSetting = htmlWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(false);
        htmlWebView.loadUrl("file:///android_asset/home.html");
    
        JavaScriptReceiver javaScriptReceiver;
        javaScriptReceiver = new JavaScriptReceiver(getActivity());
        htmlWebView.addJavascriptInterface(javaScriptReceiver, "JSReceiver");
    }
    
    /*
    final class JavaScriptInterface {
        JavaScriptInterface () { }
        public void openChat() {
            //return "string";
            startActivity(new Intent(getActivity(), Chat.class));
        }
    
        public void openGroup() {
            startActivity(new Intent(getActivity(), Group.class));
            //return "string";
        }
    }
    */
}
