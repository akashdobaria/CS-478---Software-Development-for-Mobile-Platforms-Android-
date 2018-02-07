package com.dobaria.akash.cs478.treasuryserv;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.dobaria.akash.cs478.CommonService.CommonInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

//treasury serv service
public class TreasuryServ extends Service {

    //sharedpreference and editor to store service status
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    private static final String TAG = "TreasuryServe";

    //service created
    @Override
    public void onCreate() {
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = pref.edit();
        super.onCreate();
        editor.putString(TAG, "Service Created !");
        editor.commit();
    }

    //service bound
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        editor.putString(TAG, "Service Bounded and idle !");
        editor.commit();
        return mBinder;
    }

    //implementation of interface
    private final CommonInterface.Stub mBinder = new CommonInterface.Stub(){

        public String monthlyCash(int year){

            //update service status
            editor.putString(TAG, "Service is bound running !");
            editor.commit();

            String str="http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=";
            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            String sYear = String.format("%04d", year);

            try
            {
                //prepare the API URL, get response from api using URLConnection, get json response, parse it and return as a string
                String query=URLEncoder.encode("SELECT DISTINCT \"date\", \"table\", \"open_today\" FROM t1 WHERE (\"date\" > '" + sYear + "-01-01' AND \"date\" < '" + sYear + "-12-31') AND \"open_today\" IS NOT 0 AND (\"date\" LIKE \"" + sYear + "-%-01\" OR \"date\" LIKE \"" + sYear + "%-02\" OR \"date\" LIKE \"" + sYear + "-%-03\" OR \"date\" LIKE \"" + sYear + "-%-04\" OR \"date\" LIKE \"" + sYear + "-%-05\")" ,"utf-8").toString();
                String finalLink = str + query;
                URL url = new URL(finalLink);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                System.out.print(urlConn.toString());
                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    stringBuffer.append(line);
                }

                String jsonString = stringBuffer.toString();
                editor.putString(TAG, "Service Bounded and idle !");
                editor.commit();
                return parseQuery1(jsonString);
            }
            catch(Exception ex)
            {
                Log.e(TAG, "Exception"+ ex.getMessage());
                editor.putString(TAG, "Service Bounded and idle !");
                editor.commit();
                return null;
            }
            finally
            {
                if(bufferedReader != null)
                {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        public String dailyCash(int day, int month, int year, int workingDays){

            editor.putString(TAG, "Service is  bound and running !");
            editor.commit();

            String sDay = String.format("%02d", day);
            String sMonth = String.format("%02d", month);
            String sYear = String.format("%04d", year);
            String sWorkingDays = String.valueOf(workingDays);

            String str="http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=";

            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;

            try
            {
                //prepare the API URL, get response from api using URLConnection, get json response, parse it and return as a string
                String query= URLEncoder.encode("SELECT DISTINCT \"date\", \"table\", \"open_today\" FROM t1 WHERE (\"date\" > '" + sYear + "-" + sMonth + "-"  + sDay + "' AND \"open_today\" IS NOT 0) limit " + sWorkingDays +";" ,"utf-8").toString();
                String finalLink = str + query;
                URL url = new URL(finalLink);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                System.out.print(urlConn.toString());
                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    stringBuffer.append(line);
                }

                String jsonString = stringBuffer.toString();
                editor.putString(TAG, "Service Bounded and idle !");
                editor.commit();

                return parseQuery2(jsonString);
            }
            catch(Exception ex)
            {
                Log.e(TAG, "Exception"+ ex.getMessage());
                editor.putString(TAG, "Service Bounded and idle !");
                editor.commit();
                return null;
            }
            finally
            {
                if(bufferedReader != null)
                {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        public String yearlyAvg(int year){
            editor.putString(TAG, "Service is   bound and running !");
            editor.commit();


            String sYear = String.format("%04d", year);

            String str="http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=";

            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            try
            {
                //prepare the API URL, get response from api using URLConnection, get json response, parse it and return as a string
                String query= URLEncoder.encode("SELECT DISTINCT date, \"table\", AVG(\"open_today\") AS AVERAGE FROM t1 WHERE (\"date\" >= '" + sYear +"-01-01' AND \"date\" <= '" + sYear + "-12-31' AND \"open_today\" IS NOT 0)" ,"utf-8").toString();
                String finalLink = str + query;
                URL url = new URL(finalLink);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                System.out.print(urlConn.toString());
                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    stringBuffer.append(line);
                }

                String jsonString = stringBuffer.toString();

                JSONArray mArray = new JSONArray(jsonString);
                JSONObject mJsonObject = mArray.getJSONObject(0);
                String finalResult = "Yearly Average ==> " + mJsonObject.getString("AVERAGE");
                return finalResult;
            }
            catch(Exception ex)
            {
                Log.e(TAG, "Exception"+ ex.getMessage());
                return null;
            }
            finally
            {
                if(bufferedReader != null)
                {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    };

    //service will unbind
    @Override
    public boolean onUnbind(Intent intent) {
        editor.putString(TAG, "Client Unbinded!");
        editor.commit();
        return super.onUnbind(intent);
    }

    //service will be destroyed
    @Override
    public void onDestroy() {
        editor.putString(TAG, "Service Destroyed !");
        editor.commit();
        super.onDestroy();
    }

    //function to parse JSON string response from executing query2 to output wanted
    private String parseQuery2(String jsonString) {
        String finalResult = "Date ==> Value \n";

        JSONArray mArray;

        try {
            mArray = new JSONArray(jsonString);

            for (int i = 0; i < mArray.length(); i++) {
                JSONObject mJsonObject = mArray.getJSONObject(i);
                finalResult = finalResult + mJsonObject.getString("date") + "==>" + mJsonObject.getString("open_today") + "\n";
            }

        }
        catch(JSONException e){
            e.printStackTrace();
        }
        return finalResult;

    }

    //function to parse JSON string response from executing query1 to output wanted
    private String parseQuery1(String jsonString) {
        JSONArray mArray;
        String finalResult = "Date ==> Value \n";
        try {
            mArray = new JSONArray(jsonString);
            String date="";
            String new_date = "";
            String new_month = "";
            String month ="";

            for (int i = 0; i < mArray.length(); i++) {
                JSONObject mJsonObject = mArray.getJSONObject(i);
                new_date = mJsonObject.getString("date");
                String[] splitdate = new_date.split("-");
                new_month = splitdate[1];

                if(i == 0){
                    date = mJsonObject.getString("date");
                    finalResult = finalResult + date + "==>" + mJsonObject.getString("open_today") + "\n";
                    splitdate = date.split("-");
                    month = splitdate[1];
                }

                else{
                    if(new_month.equals(month)){
                        continue;
                    }
                    else{
                        date = mJsonObject.getString("date");
                        finalResult = finalResult + date + "==>" + mJsonObject.getString("open_today") + "\n";
                        splitdate = date.split("-");
                        month = splitdate[1];
                    }

                }
            }
        }

        catch(JSONException e){
            e.printStackTrace();
        }
        return finalResult;
    }

}

