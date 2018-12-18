package com.psra.complaintsystem.Utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.psra.complaintsystem.activities.HomeScreen;
import com.psra.complaintsystem.R;
import com.psra.complaintsystem.SqliteDB.DatabaseRooom;
import com.psra.complaintsystem.dbclasses.DbConstants;
import com.psra.complaintsystem.modle.ComplainData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by HP on 10/10/2018.
 */

public class MyFirebaseMessageReceived extends FirebaseMessagingService {

    DatabaseRooom databaseRooom;
    List<ComplainData> data = new ArrayList<>();
    ComplainData notificationModule;
    String noti_id;
    int randomNumber;
    int notificationId = 1;
    String channelId = "channel-01";
    String channelName = "Channel Name";
    int importance = NotificationManager.IMPORTANCE_HIGH;
    String data_one="three_five_two",data_two="two",data_three="hello",date_data="2018-10-16";
    SharedPreferences loginSharedPreferences;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        JSONObject json ;
        try {
            json = new JSONObject(remoteMessage.getData().toString());
            handleDataMessage(json);
        } catch (JSONException e) {
            Log.e("JSONException: ", e.toString());
        }


    }

    private void handleDataMessage(JSONObject json) {
        loginSharedPreferences = getApplicationContext().getSharedPreferences("loginData",Context.MODE_PRIVATE);
        JSONObject data = null;
        try {
            data = json.getJSONObject("complain_data");
            String complain_id=data.getString("complain_id");
            int user_id= Integer.parseInt(data.getString("user_id"));
            Log.e("notifi_Uid: ", ""+user_id);
            String complainTypeTitle=data.getString("complainTypeTitle");
            String complainDetail=data.getString("complainDetail");
            String statusTitle=data.getString("statusTitle");
            String date=data.getString("date");
            int notificationId=data.getInt("notificationId");
            String userID = loginSharedPreferences.getString("userId", "No Data");
            if (userID.equals(String.valueOf(user_id))){
                Random r = new Random();
                randomNumber = r.nextInt(100);
                noti_id = String.valueOf(randomNumber);

                saveToDb(complain_id, statusTitle, complainTypeTitle, date,complainDetail,notificationId, user_id);

                Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                intent.putExtra("key","status");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);


                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel mChannel = new NotificationChannel(
                            channelId, channelName, importance);
                    notificationManager.createNotificationChannel(mChannel);
                }



                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(),channelId);
                notificationBuilder.setStyle(new NotificationCompat.DecoratedCustomViewStyle());
                notificationBuilder.setContentTitle("PSRA Notification");
                Spanned spanned = Html.fromHtml(complainDetail);
                notificationBuilder.setContentText(spanned);
                Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                notificationBuilder.setSound(soundUri);
                notificationBuilder.setSmallIcon(R.drawable.logo);
                notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.logo));
                notificationBuilder.setAutoCancel(true);
                Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(1000);
                notificationBuilder.setContentIntent(pendingIntent);

                notificationManager.notify(0, notificationBuilder.build());
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void saveToDb(String complain_id, String statusTitle, String complainTypeTitle, String date, String complainDetail,int notificationId, int user_id) {
        databaseRooom = Room.databaseBuilder(getApplicationContext(),
                DatabaseRooom.class, DbConstants.DB_NAME)
                .fallbackToDestructiveMigration()
                .build();


        notificationModule = new ComplainData(complain_id, statusTitle, complainTypeTitle,date,complainDetail,notificationId, user_id);
        data.add(notificationModule);
        databaseRooom.daoAccess().insertNotification(data);
        Log.e("saveToDb: ", "sucessfully");
        Log.e("data: ", data.toString());
    }
}
