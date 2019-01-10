package com.psra.complaintsystem.Utils;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by HP on 10/10/2018.
 */

public class MyFirebaseMessageReceived2 extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("working", "yes");
        Log.e("data", remoteMessage.getData().toString());
        //you can get your text message here.
        JSONObject json = null;
        try {
            json = new JSONObject(remoteMessage.getData().toString());
            handleDataMessage(json);
        } catch (JSONException e) {
            Log.e("JSONException: ", e.toString());
        }


    }

    private void handleDataMessage(JSONObject json) {
        JSONObject data = null;
        try {
            data = json.getJSONObject("complain_data");
            String complain_id=data.getString("complain_id");
            String complainTypeTitle=data.getString("complainTypeTitle");
            Log.e("complain_id: ", complain_id);
            Log.e("complainTypeTitle: ", complainTypeTitle);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
