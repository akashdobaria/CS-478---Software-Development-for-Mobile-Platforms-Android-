package com.dobaria.akash.cs478.project5;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import com.dobaria.akash.cs478.CommonService.CommonInterface;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    //declaration and initialisation of variables

    protected static final String TAG = "TreasuryServService";
    private CommonInterface mQueryInterface;
    private boolean mIsBound = false;

    private static final int QUERY1 = 1;
    private static final int QUERY2 = 2;
    private static final int QUERY3 = 3;

    int year;
    int day;
    int month;
    int working_days;

    public static ArrayList<String> queries = new ArrayList<String>();
    public static ArrayList<String> queryResults = new ArrayList<String>();

    Spinner spinner_Query;

    Button button_UnboundService;
    Button button_SubmitQuery;
    Button button_ShowResults;

    EditText editText_Year;
    EditText editText_Month;
    EditText editText_Day;
    EditText editText_WorkingDays;

    LinearLayout linearLayout_Input;
    RelativeLayout relativeLayout_ExtraInput;

    int query_selected;

    boolean monthlyCash = false;
    boolean dailyCash = false;
    boolean yearAverage = false;

    Handler serviceThreadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get all 3 buttons
        button_UnboundService = (Button) findViewById(R.id.button_UnboundService);
        button_ShowResults = (Button) findViewById(R.id.button_ViewQueryResults);
        button_SubmitQuery = (Button) findViewById(R.id.button_SubmitQuery);

        //get edittexts
        editText_Year = (EditText) findViewById(R.id.editText_Year);
        editText_Month = (EditText) findViewById(R.id.editText_Month);
        editText_Day = (EditText) findViewById(R.id.editText_Day);
        editText_WorkingDays = (EditText) findViewById(R.id.editText_WorkingDays);

        //containers
        linearLayout_Input = (LinearLayout) findViewById(R.id.linearLayout_Input);
        relativeLayout_ExtraInput = (RelativeLayout) findViewById(R.id.ralativeLayout_ExtraInput);

        //spinner to select query
        spinner_Query = (Spinner) findViewById(R.id.spinner_SelectQuery);
        final ArrayAdapter<CharSequence> adapter_state = ArrayAdapter.createFromResource(this,R.array.query_titles, android.R.layout.simple_spinner_item);
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_Query.setAdapter(adapter_state);

        //listener on the spineer
        spinner_Query.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                spinner_Query.setSelection(pos);

                switch (pos) {
                    //Query1 - monthlyCash()
                    case 0:
                        linearLayout_Input.setVisibility(View.VISIBLE);
                        relativeLayout_ExtraInput.setVisibility(View.INVISIBLE);
                        monthlyCash = true;
                        query_selected = 0;
                        break;
                    //Query2 - dailyCash()
                    case 1:
                        linearLayout_Input.setVisibility(View.VISIBLE);
                        relativeLayout_ExtraInput.setVisibility(View.VISIBLE);
                        dailyCash = true;
                        query_selected = 1;
                        break;
                    //Query3 - yearlyAvg()
                    case 2:
                        linearLayout_Input.setVisibility(View.VISIBLE);
                        relativeLayout_ExtraInput.setVisibility(View.INVISIBLE);
                        yearAverage = true;
                        query_selected = 2;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });

        //listener on submit query button
        button_SubmitQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get inputs from the user
                year = Integer.parseInt(editText_Year.getText().toString());
                day = Integer.parseInt(editText_Day.getText().toString());
                month = Integer.parseInt(editText_Month.getText().toString());
                working_days = Integer.parseInt(editText_WorkingDays.getText().toString());

                //if service is connected, send message on thread to execute particular function
                if (mIsBound) {
                    //for query1
                    if (query_selected == 0) {
                        queries.add("Query: monthlyCash("+year+")");
                        Message message = serviceThreadHandler.obtainMessage(QUERY1);
                        serviceThreadHandler.sendMessage(message);
                    }
                    //for query2
                    if (query_selected == 1) {
                        queries.add("Query: dailyCash("+day+","+month+","+year+","+working_days+")");
                        Message message = serviceThreadHandler.obtainMessage(QUERY2);
                        serviceThreadHandler.sendMessage(message);
                    }
                    //for query3
                    if (query_selected == 2) {
                        queries.add("Query: yearlyAvg("+year+")");
                        Message message = serviceThreadHandler.obtainMessage(QUERY3);
                        serviceThreadHandler.sendMessage(message);
                    }
                }
                else {
                    Log.i(TAG, "************ Service was not bound *********");
                }
            }
        });

        //seperate thread to run queries from service
        Thread queryThread = new Thread(new Runnable() {

            @Override
            public  void run() {
                Looper.prepare();

                //thread handlers
                serviceThreadHandler = new Handler(){

                    @Override
                    public void handleMessage(Message msg) {

                        Message message;
                        switch (msg.what){

                            //query-1 : String monthlyCash(int year);
                            case QUERY1:
                                try {
                                    String output = mQueryInterface.monthlyCash(year);
                                    queryResults.add(output);
                                } catch (RemoteException e) {
                                    Log.i("","Exception"+e.getMessage());
                                }
                                break;

                            //query-2 : String dailyCash(int day, int month, int year, int working_days)
                            case QUERY2:
                                try {
                                    String output = mQueryInterface.dailyCash(day,month,year,working_days);
                                    queryResults.add(output);
                                }
                                catch (RemoteException e){
                                    Log.i("","Exception"+e.getMessage());
                                }
                                break;

                            //query-3 : String yearlyAvg(int year)
                            case QUERY3:
                                try {
                                    String output = mQueryInterface.yearlyAvg(year);
                                    queryResults.add(output);
                                }
                                catch (RemoteException e){
                                    Log.i("","Exception"+e.getMessage());
                                }
                                break;
                        }
                    }
                };
                Looper.loop();
            }
        });
        queryThread.start();

        //listener on show result button that will start fragment activity
        button_ShowResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QueryResultsActivity.class);
                startActivity(intent);
            }
        });

        //listener to unbind service
        button_UnboundService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unBindService();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        //check if service is bound or not
        if (!mIsBound) {

            boolean b = false;
            Intent i = new Intent(CommonInterface.class.getName());
            getPackageManager().resolveService(i, Context.BIND_AUTO_CREATE);
            ResolveInfo info = getPackageManager().resolveService(i, Context.BIND_AUTO_CREATE);
            i.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));

            b = bindService(i, this.mConnection, Context.BIND_AUTO_CREATE);
            if (b) {
                Log.i(TAG, "Service is bound.");
            } else {
                Log.i(TAG, "Couldn't bind the service.");
            }

        }
    }

    //unbind service if activity is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBindService();
    }

    //function to unbind the service
    private void unBindService(){
        if (mIsBound){
            unbindService(this.mConnection);
        }
    }


    private final ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder iservice) {
            mQueryInterface = CommonInterface.Stub.asInterface(iservice);
            mIsBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            mQueryInterface = null;
            mIsBound = false;
        }
    };
}
