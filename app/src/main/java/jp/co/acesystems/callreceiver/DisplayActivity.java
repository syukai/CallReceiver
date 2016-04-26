package jp.co.acesystems.callreceiver;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear Preferences
                clearHistories(view);


            }
        });

        TextView tv = (TextView) this.findViewById(R.id.hwLabel);
        SharedPreferences pref = getSharedPreferences("CallReceiver", MODE_PRIVATE);
        tv.setText(pref.getString("text","nothing.."));
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView tv = (TextView) this.findViewById(R.id.hwLabel);
        SharedPreferences pref = getSharedPreferences("CallReceiver", MODE_PRIVATE);
        tv.setText(pref.getString("text", "nothing.."));
   }


    private void clearHistories(View view){
        getSharedPreferences("CallReceiver", MODE_PRIVATE).edit().clear().commit();
        Snackbar.make(view, "histories cleared.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        TextView tv = (TextView) this.findViewById(R.id.hwLabel);
        SharedPreferences pref = getSharedPreferences("CallReceiver", MODE_PRIVATE);
        tv.setText(pref.getString("text", "nothing.."));
    }

}
