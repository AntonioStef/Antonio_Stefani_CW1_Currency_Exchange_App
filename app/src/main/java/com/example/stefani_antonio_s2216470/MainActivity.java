


//
// Name                 Antonio Stefani
// Student ID           S2216470
// Programme of Study   Software Development
//


package com.example.stefani_antonio_s2216470;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

// need to add another on click listener here
public class MainActivity extends AppCompatActivity implements  CurrencyAdapter.OnItemClickListener{


    // NEW: ViewModel class made and is used here as a variable
    private CurrencyViewModel viewModel;

    // Data list is only used locally for filtering purposes. The main source is LiveData.
    private List<Currency> CurrencyList = new ArrayList<>();


    private TextView dateDisplay;
    private TextView usdRate;
    private TextView eurRate;
    private TextView jpyRate;
    private EditText searchText;
    private RecyclerView recyclerView;
    private CurrencyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize ViewModel and UI Components
        viewModel = new ViewModelProvider(this).get(CurrencyViewModel.class);


        dateDisplay =findViewById(R.id.dateDisplay);
        usdRate = findViewById(R.id.usdRate);
        eurRate = findViewById(R.id.eurRate);
        jpyRate = findViewById(R.id.jpyRate);
        searchText = findViewById(R.id.searchText);

        // RecycleView Finder to display all rates
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Adapter using Currency List
        adapter = new CurrencyAdapter(CurrencyList, this); // 'this' passes the click listener
        recyclerView.setAdapter(adapter);

        //Sets up a LiveData Observer
        setupObserver();




     //  Search Listener for filtering by Code or Currency Name
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // filters the list as the user types
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     * Sets up observers to react to data changes from the ViewModel.
     * This is the modern, non-blocking way to update the UI.
     */
    private void setupObserver() {
        // Observe the main currency list
        viewModel.getCurrencyListLiveData().observe(this, new Observer<List<Currency>>() {
            @Override
            public void onChanged(List<Currency> freshCurrencyList) {
                //  Update the local list used for filtering
                CurrencyList.clear();
                CurrencyList.addAll(freshCurrencyList);

                // Update the full list display
                adapter.notifyDataSetChanged();
                displayMainCurrencies(freshCurrencyList);
                Log.d("Observer", "Full list updated via LiveData.");

            }
        });

        // edit the date display string
        viewModel.getDateDisplayLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String date) {
                dateDisplay.setText(date);
            }
        });
    }






   // used to handle any clicks from a item in the recyclerView
    public void onItemClick(Currency currency){

        Log.d("Conversion", "Currency clicked: " + currency.getCurrencyCode() +
                " Rate: " + currency.getRate());

        Intent intent = new Intent(this, CurrencyCalc.class);
        intent.putExtra("CURRENCY_CODE", currency.getCurrencyCode());
        intent.putExtra("EXCHANGE_RATE", currency.getRate());
        startActivity(intent);

    }

    // filters countrys currency name and currency code to try find it in a search
    private void filter(String text) {

        List<Currency> filteredList = new ArrayList<>();


        String searchText = text.toLowerCase();


        for (Currency currency : CurrencyList) {
            String currencyCode = currency.getCurrencyCode() != null ? currency.getCurrencyCode().toLowerCase() : "";
            String currencyName = currency.getDescription() != null ? currency.getDescription().toLowerCase() : "";

            // Check 1: Does the search text match the country code
            boolean matchesCode = currencyCode.contains(searchText);

            // Check 2: Does the search text match the currency name?
            boolean matchesName = currencyName.contains(searchText);

            // If either the code OR the name contains the search text, add it to the filtered list
            if (matchesCode || matchesName) {
                filteredList.add(currency);
            }
        }

        // Update the RecyclerView adapter with the new filtered list by calling the Currecny adapter

        adapter.updateList(filteredList);
    }




                private void displayMainCurrencies(List<Currency> list) {
                    for (Currency currency : CurrencyList) {
                        String code = currency.getCurrencyCode();
                        if (code != null) {
                            double rate = currency.getRate();


                            if (code.equals("USD")) {
                                usdRate.setText("USD United States Dollar " + String.format("%.4f", rate));
                            } else if (code.equals("EUR")) {
                                eurRate.setText("EUR Euro " + String.format("%.4f", rate));
                            } else if (code.equals("JPY")) {
                                jpyRate.setText("JPY Japanese Yen " + String.format("%.4f", rate));
                            }
                        }
                    }

    };

        }







