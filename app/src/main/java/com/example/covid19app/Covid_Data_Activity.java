package com.example.covid19app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.neovisionaries.i18n.CountryCode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Covid_Data_Activity extends AppCompatActivity {
    TextView textView;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    TextView textView8;
    TextView textView9;
    TextView textView10;

    TextView dialog_cases_textview;
    TextView dialog_deaths_textview;
    TextView dialog_tests_textview;
    TextView dialog_tests1_textview;
    TextView dialog_affected_textview;
    TextView dialog_actives_textview;
    TextView dialog_recovered_textview;
    TextView dialog_critical_textview;
    ImageView imageView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_data);
        Intent intent = getIntent();
        button = findViewById(R.id.button4);
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<JSONObject> country = es.submit(new Callable<JSONObject>() {
            public JSONObject call() throws Exception {
                JSONObject jsonObject = get_json_from_country();
                JSONObject object = jsonObject.getJSONObject("countryInfo");
                imageView = findViewById(R.id.imageView);
                String urlOfImage = object.getString("flag");
                Bitmap logo = null;
                try{
                    InputStream is = new URL(urlOfImage).openStream();

                    logo = BitmapFactory.decodeStream(is);
                    imageView.setImageBitmap(logo);
                }catch(Exception e){ // Catch the download exception
                    e.printStackTrace();
                }

                return jsonObject;
            }
        });


        try {
                JSONObject x = country.get();
                edit_country(x);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject x = country.get();
                    edit_dialog(x);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        es.shutdown();
        }


    public JSONObject get_json_from_country(){
        Intent intent = getIntent();
        String country_code = CountryCode.findByName(intent.getStringExtra("country")).get(0).name();
        URL url = null;
        try {
            String inline = "";
            url = new URL("https://disease.sh/v3/covid-19/countries/"+country_code);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            Scanner sc = new Scanner(url.openStream());
            while(sc.hasNext())
            {
                inline+=sc.nextLine();
            }
            System.out.println("\nJSON data in string format");
            System.out.println(inline);
            sc.close();
            JSONObject jObject = new JSONObject(inline);
            return jObject;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void edit_country(JSONObject object){


        String country_name = null;
        ImageView imageView = null;



        try {
            textView = findViewById(R.id.country_name);
            textView1=findViewById(R.id.continent);
            textView2 = findViewById(R.id.population);
            textView3=findViewById(R.id.total_cases);
            textView4 = findViewById(R.id.today_cases);
            textView5=findViewById(R.id.total_deaths);
            textView6 = findViewById(R.id.textView16);
            textView7=findViewById(R.id.total_recovered);
            textView8 = findViewById(R.id.today_recovered);
            textView9=findViewById(R.id.actives);
            textView10 = findViewById(R.id.criticals);
            country_name = (String) object.get("country");
            String continent = (String) object.get("continent");


            DecimalFormat formatter = new DecimalFormat("#,###,###");
            String population = formatter.format((int) object.get("population"));
            String total_cases = formatter.format((int) object.get("cases"));
            String today_cases = formatter.format((int) object.get("todayCases"));
            String total_deaths = formatter.format((int) object.get("deaths"));
            String today_deaths = formatter.format((int) object.get("todayDeaths"));
            String total_recovered = formatter.format((int) object.get("recovered"));
            String today_recovered = formatter.format((int) object.get("todayRecovered"));
            String active = formatter.format((int) object.get("active"));
            String critical = formatter.format((int) object.get("critical"));
            textView.setText(country_name);
            System.out.println(country_name);
            textView1.setText(continent);
            textView2.setText("Population: " + population);
            textView3.setText("Cases: " + total_cases);
            textView4.setText("Today Cases: " + today_cases);
            textView5.setText("Deaths: " + total_deaths);
            textView6.setText("Today Deaths: " + today_deaths);
            textView7.setText("Recovered: " + total_recovered);
            textView8.setText("Today Recovered: " + today_recovered);
            textView9.setText("Active: "+active);
            textView10.setText("Critical: "+critical);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void edit_dialog(JSONObject object) {


        try {
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            String dialog_cases = formatter.format(object.getDouble("casesPerOneMillion"));
            String dialog_deaths = formatter.format(object.getLong("deathsPerOneMillion"));
            String dialog_tests = formatter.format(object.getDouble("tests"));
            String dialog_tests1 = formatter.format(object.getDouble("testsPerOneMillion"));
            String dialog_actives = formatter.format(object.getDouble("activePerOneMillion"));
            String dialog_recovered =formatter.format(object.getDouble("recoveredPerOneMillion"));
            String dialog_critical = formatter.format(object.getDouble("criticalPerOneMillion"));
            Dialog dialog = new Dialog(Covid_Data_Activity.this);
            dialog.setContentView(R.layout.dialog_layout2);
            dialog.getWindow().setLayout(800,1450);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dialog_cases_textview = dialog.findViewById(R.id.dialog_cases);
            dialog_deaths_textview = dialog.findViewById(R.id.dialog_deaths);
            dialog_tests_textview = dialog.findViewById(R.id.dialog_tests);
            dialog_tests1_textview = dialog.findViewById(R.id.dialog_tests1);
            dialog_actives_textview = dialog.findViewById(R.id.dialog_actives);
            dialog_recovered_textview = dialog.findViewById(R.id.dialog_recovered);
            dialog_critical_textview = dialog.findViewById(R.id.dialog_critical);


            dialog_cases_textview.setText("Cases Per One Million: "+ dialog_cases);
            dialog_deaths_textview.setText("Deaths Per One Million: "+dialog_deaths);
            dialog_tests_textview.setText("Tests: "+ dialog_tests);
            dialog_tests1_textview.setText("Tests Per One Million: "+ dialog_tests1);
            dialog_actives_textview.setText("Actives Per One Million: "+ dialog_actives);
            dialog_recovered_textview.setText("Recovered Per One Million: "+ dialog_recovered);
            dialog_critical_textview.setText("Critical Per One Million: "+ dialog_critical);

            dialog.show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
