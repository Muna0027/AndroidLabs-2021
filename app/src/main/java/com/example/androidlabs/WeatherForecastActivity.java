package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecastActivity extends AppCompatActivity {
    ProgressBar progressBar;
    String currTemp = null;
    String minTemp = null;
    String maxTemp = null;
    String weatherIcon = null;
    String uV = null;
    TextView currentTemp;
    TextView minimumTemp;
    TextView maximumTemp;
    TextView UVRating;
    ImageView weatherImage;
    Bitmap image;
    HttpURLConnection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        progressBar = findViewById(R.id.pBar);
        progressBar.setVisibility(View.VISIBLE);

        ForecastQuery forecastQuery = new ForecastQuery();
        forecastQuery.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");
    }

    public class ForecastQuery extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... args) {
            Log.e("doInBackground", "First Line");
            try {
                URL url = new URL(args[0]);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
;
                InputStream response = urlConnection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response, "UTF-8");

                String parameter = null;

                int eventType = xpp.getEventType();

                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_TAG) {
                            Log.e("Start Tag", "What!");
                            if (xpp.getName().equals("temperature")) {
                                currTemp = xpp.getAttributeValue(null, "value");
                                publishProgress(25);
                                minTemp = xpp.getAttributeValue(null, "min");
                                publishProgress(50);
                                maxTemp = xpp.getAttributeValue(null, "max");
                                publishProgress(75);
                            } else if (xpp.getName().equals("weather")) {
                                weatherIcon = xpp.getAttributeValue(null, "icon");
                                String fileName = weatherIcon + ".png";
                                Log.e("AsyncTask", "Found icon name: " + weatherIcon);
                                if (fileExistence(fileName)) {
                                    FileInputStream fis = null;
                                    try {
                                        fis = new FileInputStream(getBaseContext().getFileStreamPath(fileName));
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    image = BitmapFactory.decodeStream(fis);
                                } else {
                                    Log.e("AsyncTask", "Downloading weather image");
                                    URL imageURL = new URL("http://openweathermap.org/img/w/" + weatherIcon + ".png");
                                    connection = (HttpURLConnection) imageURL.openConnection();
                                    connection.connect();
                                    int responseCode = connection.getResponseCode();
                                    if (responseCode == 200) {
                                        image = BitmapFactory.decodeStream(connection.getInputStream());
                                        FileOutputStream outputStream = openFileOutput( weatherIcon + ".png", Context.MODE_PRIVATE);
                                        image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                        outputStream.flush();
                                        outputStream.close();
                                    }
                                }
                                publishProgress(100);
                            }
                        }
                        eventType = xpp.next();

                        URL uVURL = new URL("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");
                        HttpURLConnection UVConnection = (HttpURLConnection) uVURL.openConnection();
                        response = UVConnection.getInputStream();

                        BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                        StringBuilder sB = new StringBuilder();

                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            sB.append(line + "\n");
                        }
                        String result = sB.toString();
                        
                        JSONObject jObject = new JSONObject(result);
                        uV = String.valueOf(jObject.getDouble("value"));
                        Log.e("AsyncTask", "Found UV: " + uV);
                    }
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            return "Done";
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.e("onProgressUpdate", "First Line");
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("onPostExecute", "First Line");
            currentTemp = findViewById(R.id.currTemp);
            minimumTemp = findViewById(R.id.minTemp);
            maximumTemp = findViewById(R.id.maxTemp);
            UVRating = findViewById(R.id.uv);
            weatherImage = findViewById(R.id.weather);

            currentTemp.setText(currentTemp.getText() + " " + currTemp);
            minimumTemp.setText(minimumTemp.getText() + " " + minTemp);
            maximumTemp.setText(maximumTemp.getText() + " " + maxTemp);
            UVRating.setText(UVRating.getText() + " " + uV);
            weatherImage.setImageBitmap(image);
            progressBar.setVisibility(View.INVISIBLE);
        }

        private boolean fileExistence(String fileName) {
            File file = getBaseContext().getFileStreamPath(fileName);
            return file.exists();
        }
    }
}