package fr.isep.ii3510.assignment4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.StreamHandler;

public class MainActivity extends AppCompatActivity {

    TextView symbolCurrencyText;
    TextView baseCurrencyText;
    Button callApiButton;
    Button swapButton;
    Spinner currencyList;
    Spinner secondCurrencyList;

    ArrayList<String> currencies = new ArrayList<String>();

//    public static final String SHARED_PREFS = "sharedPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        symbolCurrencyText = findViewById(R.id.symbol_currency_text);
        baseCurrencyText = findViewById(R.id.base_currency_text);
        callApiButton = findViewById(R.id.api_call_button);
        swapButton = findViewById(R.id.swap_button);
        currencyList = findViewById(R.id.currency_list);
        secondCurrencyList = findViewById(R.id.second_currency_list);


        apiCallCurrencyNames();
        currencies.add("EUR");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencyList.setAdapter(adapter);
        secondCurrencyList.setAdapter(adapter);

        baseCurrencyText.setText("1");
        symbolCurrencyText.setText("");


        callApiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currencyList.getSelectedItem().toString().equals(secondCurrencyList.getSelectedItem().toString()) == false) {
                    apiCallSpecific(currencyList.getSelectedItem().toString(), secondCurrencyList.getSelectedItem().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Vous avez selectionné deux fois la même monnaie", Toast.LENGTH_SHORT).show();
                }
            }
        });

        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapCurrencies();
            }
        });

    }


    private void apiCallSpecific(String firstCurrency, String secondCurrency) {

        String url = String.format(" https://api.exchangeratesapi.io/latest?base=%s&symbols=%s", firstCurrency, secondCurrency);

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONObject rates = response.getJSONObject("rates");
                            JSONArray keys = rates.names();

                            for (int i = 0; i < keys.length(); i++) {
                                String currencyName = keys.getString(i);
                                String value = rates.getString(currencyName);
                                symbolCurrencyText.setText(value);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley response", "An error occurred");
            }
        });
        queue.add(request);
    }

    private void apiCallCurrencyNames() {

        String url = "https://api.exchangeratesapi.io/latest";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONObject rates = response.getJSONObject("rates");
                            JSONArray keys = rates.names();

                            for (int i = 0; i < keys.length(); i++) {

                                currencies.add(keys.getString(i));
                            }
//                            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());  //https://stackoverflow.com/questions/7057845/save-arraylist-to-sharedpreferences/11050845#11050845
//                            SharedPreferences.Editor editor = sp.edit();
//                            editor.putInt("Status_size", currencies.size());
//
//                            for (int i = 0; i < currencies.size(); i++){
//                                editor.remove("Status_" + i);
//                                editor.putString("Status_" + i, currencies.get(i));
//                            } editor.commit();
//


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley response", "An error occurred");
            }
        });
        queue.add(request);

    }

    private void swapCurrencies() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currencies);

        String firstSwappedCurr = currencyList.getSelectedItem().toString();
        String secondSwappedCurr = secondCurrencyList.getSelectedItem().toString();

        currencyList.setSelection(adapter.getPosition(secondSwappedCurr));
        secondCurrencyList.setSelection(adapter.getPosition(firstSwappedCurr));

        apiCallSpecific(currencyList.getSelectedItem().toString(), secondCurrencyList.getSelectedItem().toString());


    }


}



