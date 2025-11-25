package com.example.stefani_antonio_s2216470;

import android.app.Application;
import android.os.Handler;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
  CurrencyViewModel: Manages the data layer, including network access for the API and XML parsing.
 It uses LiveData to communicate data changes to the Activity.
 it also updates the existing threading on the base code
 */
public class CurrencyViewModel extends  AndroidViewModel
{
    // LiveData holds the list of currencies and allows the Activity to observe changes.
    private final MutableLiveData<List<Currency>> currencyListLiveData = new MutableLiveData<>();
    // LiveData for the timestamp of the last update
    private final MutableLiveData<String> dateDisplayLiveData = new MutableLiveData<>();

    private final String urlSource = "https://www.fx-exchange.com/gbp/rss.xml";

    // changes threading from the old version now using a Executor  Replaces manual Thread/runOnUiThread with ExecutorService
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    // Auto Update Feature
    private final Handler handler = new Handler();
    private final int UPDATE_INTERVAL_MS = 60 * 1000; // 1 minute

    // Starts the initial data fetch and recurring update when the ViewModel is created
    public CurrencyViewModel(Application application) {
        super(application);

        startAutoUpdate();
    }

    // Public getters for LiveData
    public LiveData<List<Currency>> getCurrencyListLiveData() {
        return currencyListLiveData;
    }

    public LiveData<String> getDateDisplayLiveData() {
        return dateDisplayLiveData;
    }


     // Starts the recurring data fetch using a Handler.

    // runs the data source right away and updates every 1 minute
    private void startAutoUpdate() {

        handler.post(updateTask);
    }

    private final Runnable updateTask = new Runnable() {
        @Override
        public void run() {
            fetchData();

            handler.postDelayed(this, UPDATE_INTERVAL_MS);
        }
    };


     //Triggers the network operation on the background thread.

    public void fetchData() {

        executor.submit(this::processFeed);
    }

    /**
      Handles the API access thread and XML parsing.
      This is executed on the background thread.
     */
    private void processFeed() {
        String result = "";
        String currencyDate = "";
        List<Currency> temporaryList = new ArrayList<>();

        // API Access (Background Thread)
        try {
            URL aurl = new URL(urlSource);
            URLConnection yc = aurl.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;
            StringBuilder sb = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();
            result = sb.toString();
        } catch (IOException e) {
            Log.e("ViewModel", "Network error: " + e.getMessage());
            return;
        }

        // Clean up leading/trailing garbage
        int i = result.indexOf("<?");
        if (i != -1) {
            result = result.substring(i);
        }
        i = result.indexOf("</rss>");
        if (i != -1) {
            result = result.substring(0, i + 6);
        }

    //Xml Parsing(Background Thread) used for getting data on the API

        try {
            XmlPullParserFactory factory =
                    XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput( new StringReader( result ) );


            int eventType =xpp.getEventType();
            Currency currentItem=null;
            String text="";
            boolean insideItem=false; //flag indicating when the parser is traversing a new Currency



            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase("item")) {
                            insideItem = true;
                            // Create a new object for the item
                            currentItem = new Currency(null, null, 0.0, null);
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase("lastBuildDate")) {
                            if (!insideItem) {
                                // Find the most recent date/time
                                currencyDate = text;
                            }
                        } else if (insideItem) {
                            if (tagName.equalsIgnoreCase("title")) {
                                currentItem.setTitle(text);
                                // Extract three letter currency code

                                String code = null;
                                int startIndex = text.lastIndexOf('('); // Find the last open parenthesis
                                int endIndex = text.lastIndexOf(')');   // Find the last close parenthesis

                                if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {

                                    code = text.substring(startIndex + 1, endIndex);
                                }

                                // Check if it's a 3-letter code
                                if (code != null && code.length() == 3) {
                                    currentItem.setCurrencyCode(code);
                                } else {
                                    //   set a placeholder to prevent crash later if parsing fails
                                    currentItem.setCurrencyCode("N/A");
                                }

                            } else if (tagName.equalsIgnoreCase("description")) {
                                String fullDescription = text.trim();
                                String currencyName = "Name N/A";

                                // will extract the exchange rate and country Name
                                try {

                                    // Extract the Rate

                                    String[] parts = fullDescription.split(" = ");

                                    if (parts.length > 1) {

                                        String ratePart = parts[1].split(" ")[0].trim();
                                        currentItem.setRate(Double.parseDouble(ratePart));

                                        // Extract the Country Name

                                        String valueAndName = parts[1].trim();
                                        int firstSpaceIndex = valueAndName.indexOf(' ');

                                        if (firstSpaceIndex != -1) {

                                            currencyName = valueAndName.substring(firstSpaceIndex + 1).trim();
                                        }
                                    }
                                } catch (Exception e) {
                                    Log.e("Parsing", "Error parsing rate: " + text);
                                }
                                currentItem.setDescription(currencyName);

                            } else if (tagName.equalsIgnoreCase("item")) {
                                // End of an item, add the completed object to the list
                                temporaryList.add(currentItem);
                                insideItem = false;
                            }

                        }
                        break;
                }
                //  gets the next event
                eventType = xpp.next();
            }
            Log.d("Parsing", "Number of items parsed: " + temporaryList.size());

        } catch (XmlPullParserException e) {
            Log.e("Parsing","EXCEPTION" + e);

        } catch (IOException e) {
            Log.e("Parsing","I/O EXCEPTION" + e);

        }

        //  Updates LiveData (Pushes data back to the UI thread automatically)
        if (!temporaryList.isEmpty()) {
            currencyListLiveData.postValue(temporaryList);
            dateDisplayLiveData.postValue("Contains Fx Currency Exchange data | Last Updated: " + currencyDate);
        }
    }





}
