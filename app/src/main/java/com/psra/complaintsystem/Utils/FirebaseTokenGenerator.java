package com.psra.complaintsystem.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by HP on 10/10/2018.
 */

public class FirebaseTokenGenerator extends  FirebaseInstanceIdService {

    private static final String TAG ="firebase" ;

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        storeRegIdInPref(refreshedToken);
    }
    private void storeRegIdInPref(String token) {
        SharedPreferences prefences = getApplicationContext().getSharedPreferences("loginData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefences.edit();
        editor.putString("token_key", token);
        Log.e("storeRegIdInPref:",token);
        editor.apply();
    }


}
