package zihanoku.ender.elrond;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

public class JavaScriptReceiver
{
    Context mContext;
    
    /** Instantiate the receiver and set the context */
    JavaScriptReceiver(Context c) {
        mContext = c;
    }
    
    @JavascriptInterface
    public void openChat(){
        Intent intent = new Intent(mContext, Chat.class);
        mContext.startActivity(intent);
    }
    
    @JavascriptInterface
    public void openGroup(){
        Intent intent = new Intent(mContext, Group.class);
        //intent.putExtra("order",orderid);
        mContext.startActivity(intent);
    }
}