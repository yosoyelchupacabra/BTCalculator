package market.bitcoin.com.bitcoinminerplace;

import android.app.ProgressDialog;
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

import market.bitcoin.com.bitcoinminerplace.adapter.CustomAdapter;

public class MainActivity extends AppCompatActivity {

    /** declaring listView, button and progressBar variable */
    ListView listView;
    Button calculateButton;
    ProgressBar progressBar = null;

    /**
     * @override method call first when application start
     * @param savedInstanceState
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** setting strict moode policy */
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        /** initializing listView and refresh button */
        listView = (ListView) findViewById(R.id.mainListView);
        calculateButton = (Button) findViewById(R.id.calculateButton);
        /** initializing ProgressBar */
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

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
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle(GetDetails.details[1][i] + " : ");
                alertDialog.setMessage(GetDetails.blockchainDetails.get(GetDetails.details[0][i]));
                alertDialog.show();
            }
        });

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
                // Rendo visibile la progressBar per mostrare che sto caricando
                progressBar.setVisibility(View.VISIBLE);
                // Quando premo il pulsante gli cambio il colore per far capire che sta lavorando
                calculateButton.setBackgroundColor(getResources().getColor(R.color.buttonPrimaryDark));

                // Ripopolo i dettagli
                loadDetails();

                // Rendo invisibile la progressBar per far capire che ho finito di caricare
                progressBar.setVisibility(View.GONE); //To Hide ProgressBar
                // Alla fine del listener riporto il colore del pulsante come prima
                calculateButton.setBackgroundColor(getResources().getColor(R.color.buttonPrimary));
            }
        });

        /**
         * initializing setOnLongCLickListener calls when long click on listView
         * @param OnCLickListener
         * */
        calculateButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // Pulisco i dettagli
                cleanDetails();
                return true;
            }
        });

        // Commentato per non caricare gli elementi all'avvio ma solo al premere del pulsante
        // loadDetails();
    }

    /**
     * cleanDetails method calls to clean details
     * @throws IllegalArgumentException
     * */
    void cleanDetails(){
        /** cleaning up listView */
        listView.setAdapter(null);
    }

    /**
     * loadDetails method calls to get details from blockchain web service
     * @throws IllegalArgumentException
     * */
    void loadDetails(){
        try {
            GetDetails.loadDetailsFromWeb();
        }catch (IllegalArgumentException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
        /**
         * initializing customadapter
         * @param context
         * @param blockchainDetails
         * */
        CustomAdapter customAdapter = new CustomAdapter(this, GetDetails.blockchainDetails);
        /** setting up custom adapter object */
        listView.setAdapter(customAdapter);
    }
}