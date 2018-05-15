package market.bitcoin.com.bitcoinminerplace;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ProgressBar;
import android.os.AsyncTask;
import market.bitcoin.com.bitcoinminerplace.adapter.CustomAdapter;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** setting strict moode policy */
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        /** initializing refresh button */
        calculateButton = (Button) findViewById(R.id.calculateButton);

        /**
         * initializing setOnCLickListener calls when click on listView
         * @param OnCLickListener
         * */
        calculateButton.setOnClickListener(new View.OnClickListener() {
            /**
             * @override method onClick calls when button click action performed
             * @param view
             * */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LandingActivity.class));
            }
        });
    }


}