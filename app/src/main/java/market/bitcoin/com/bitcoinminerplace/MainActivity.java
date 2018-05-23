package market.bitcoin.com.bitcoinminerplace;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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