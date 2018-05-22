package market.bitcoin.com.bitcoinminerplace;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import market.bitcoin.com.bitcoinminerplace.adapter.CustomAdapter;

public class LandingActivity extends AppCompatActivity {
    /**
     * declaring ADMOB_APP_ID
     */
    String YOUR_ADMOB_APP_ID = "ca-app-pub-8887154992538647~1073274338";
    String DEVICE_ID = "ca-app-pub-8887154992538647/4082295649";
    /**
     * declaring listView and progressBar variable
     */
    ListView listView;
    ProgressBar progressBar = null;
    /**
     * declaring AdView
     */
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        /** initializing ProgressBar */
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        /** Inizialise MobileAds */
        MobileAds.initialize(this, YOUR_ADMOB_APP_ID);

        /** Create AdView */
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(DEVICE_ID).build();
        mAdView.loadAd(adRequest);
        if (BuildConfig.DEBUG) {
            mAdView.setVisibility(View.GONE);
        } else {
            mAdView.setVisibility(View.VISIBLE);
        }

        /** Possible implementation of mAdView */
        /*
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });
        */

        /** initializing listView */
        listView = (ListView) findViewById(R.id.landingListView);

        /**
         * setOnItemClickListener this method calls when any item selected from listView item menu
         * @param OnItemClickListener
         * */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * onItemClick method calls when item click on listView
             * @param adapterView
             * @param view
             * @param i
             * @param l
             * */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /** initializing alertDialog to show details */
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(LandingActivity.this);
                alertDialog.setTitle(GetDetails.details[1][i] + " : ");
                alertDialog.setMessage(GetDetails.blockchainDetails.get(GetDetails.details[0][i]));
                alertDialog.show();
            }
        });

        new MyAsyncCaller().execute();
    }

    /**
     * loadDetails method calls to get details from blockchain web service
     *
     * @throws IllegalArgumentException
     */
    CustomAdapter loadDetails() {
        try {
            GetDetails.loadDetailsFromWeb();
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
        /**
         * initializing customadapter
         * @param context
         * @param blockchainDetails
         * */
        CustomAdapter customAdapter = new CustomAdapter(this, GetDetails.blockchainDetails);

        return customAdapter;
    }

    /** Called when leaving the activity */
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private class MyAsyncCaller extends AsyncTask<String, Integer, CustomAdapter> {
        @Override
        protected void onPreExecute() {
            // Pre Code
            // Rendo visibile la progressBar per mostrare che sto caricando
            progressBar.setVisibility(View.VISIBLE);
            // Quando premo il pulsante gli cambio il colore per far capire che sta lavorando
            //calculateButton.setBackgroundColor(getResources().getColor(R.color.buttonPrimaryDark));
        }

        @Override
        protected CustomAdapter doInBackground(String... parametro) {
            // Background Code
            // Ripopolo i dettagli
            CustomAdapter customAdapter = loadDetails();
            //publishProgress();
            return customAdapter;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // Executes whenever publishProgress is called from doInBackground
            // Used to update the progress indicator
            // progressBar.setProgress();
        }

        @Override
        protected void onPostExecute(CustomAdapter result) {
            // Post Code
            /** setting up custom adapter object */
            listView.setAdapter(result);
            // Rendo invisibile la progressBar per far capire che ho finito di caricare
            progressBar.setVisibility(View.GONE); //To Hide ProgressBar
            // Alla fine del listener riporto il colore del pulsante come prima
            //calculateButton.setBackgroundColor(getResources().getColor(R.color.buttonPrimary));
        }
    }
}
