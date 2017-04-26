package com.example.android.andela.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.widget.Toast;

/**
 * Created by UTYLO on 4/25/2017.
 */
public class ConnectionTest {


        static public boolean isNetworkAvailable(Context context) {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
        }

        static public void showToastForDuration(Context context,String message, int toastDurationInMilliSeconds, int gravity) {
            final Toast mToastToShow = Toast.makeText(context, message, Toast.LENGTH_LONG);
            CountDownTimer toastCountDown;
            toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1000 ) {
                public void onTick(long millisUntilFinished) {
                    mToastToShow.show();
                }
                public void onFinish() {
                    mToastToShow.cancel();
                }
            };
            mToastToShow.setGravity(gravity, 0, 0);
            mToastToShow.show();
            toastCountDown.start();

        }
    }


