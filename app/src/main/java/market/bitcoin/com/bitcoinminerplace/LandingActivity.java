package market.bitcoin.com.bitcoinminerplace;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import market.bitcoin.com.bitcoinminerplace.adapter.CustomAdapter;

public class LandingActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    /**
     * declaring ADMOB_APP_ID
     */
    String YOUR_ADMOB_APP_ID = "ca-app-pub-8887154992538647~1073274338";
    /**
     * declaring listView and progressBar variable
     */
    ListView listView;
    /**
     * declaring AdView
     */
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            /** Inizialise MobileAds */
            MobileAds.initialize(this, YOUR_ADMOB_APP_ID);
            // Obtain the FirebaseAnalytics instance.
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        //if (!BuildConfig.DEBUG) {
            /** Create AdView */
            mAdView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
            mAdView.setVisibility(View.VISIBLE);
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "AdMod");
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Banner");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        //}

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

        loadDetails();
    }

    /**
     * loadDetails method calls to get details from blockchain web service
     *
     * @throws IllegalArgumentException
     */
    void loadDetails() {
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
        listView.setAdapter(customAdapter);
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

}
