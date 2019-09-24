package zihanoku.ender.elrond;

import java.security.SecureRandom;
import java.util.Calendar;



public class Functions {

    public String alphaNumeric() {
        String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        int count = 10;
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public long getTimeStamp() {
        Calendar currentTime = Calendar.getInstance();
        return currentTime.getTimeInMillis();
    }
}
