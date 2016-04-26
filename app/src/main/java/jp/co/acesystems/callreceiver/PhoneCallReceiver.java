package jp.co.acesystems.callreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by u0268 on 2016/04/21.
 */
public class PhoneCallReceiver extends BroadcastReceiver {

    private Context ctx;

    @Override
    public void onReceive(Context context, Intent intent) {
        ctx = context;
        try {
            //TelephonyManagerの生成
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //リスナーの登録
            MyPhoneStateListener PhoneListener = new MyPhoneStateListener();

            Bundle extras = intent.getExtras();
//            Toast.makeText(context, "extras:" + extras.toString(),Toast.LENGTH_LONG).show();
            System.out.println("extras-size:" + extras.size());
            System.out.println("extras-toString:" + extras.toString());
//            Log.d("extras", "extras-size:" + extras.size());
            Log.d("extras", "extras-toString:" + extras.toString());

            appendPreference(context, extras.toString());
            tm.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void appendPreference(Context context, String text){
        SharedPreferences pref = context.getSharedPreferences("CallReceiver", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();
        String curText = pref.getString("text", "");

        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

        pref.edit().putString("text",curText + f.format(new Date()) + " extras:" + text + "\n").commit();
    }

    /**
     * カスタムリスナーの登録
     * 着信〜終了 CALL_STATE_RINGING > CALL_STATE_OFFHOOK > CALL_STATE_IDLE
     * 不在着信 CALL_STATE_RINGING > CALL_STATE_IDLE
     */
    private class MyPhoneStateListener extends PhoneStateListener {
        @SuppressWarnings("unused")
        private final String TAG = getClass().getSimpleName();
        public void onCallStateChanged(int state, String callNumber) {
            Log.d(TAG, "Listener state:" + state+"-PhoneNumber:"+callNumber);
            switch(state){
                case TelephonyManager.CALL_STATE_IDLE:      //待ち受け（終了時）
//                    Toast.makeText(ctx, "CALL_STATE_IDLE", Toast.LENGTH_LONG).show();
                    break;
                case TelephonyManager.CALL_STATE_RINGING:   //着信
//                    Toast.makeText(ctx, "CALL_STATE_RINGING: " + callNumber, Toast.LENGTH_LONG).show();
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:   //通話
//                    Toast.makeText(ctx, "CALL_STATE_OFFHOOK", Toast.LENGTH_LONG).show();
                    break;
            }
        }

    }
}
