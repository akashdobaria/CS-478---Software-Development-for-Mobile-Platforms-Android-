package com.cs478.project4;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    //Declare variables
    private static final int START_GAME = 1;
    private static final int GUESS_NUMBER = 2;
    private static final int CHECK_NUMBER = 4;
    private static final int UPDATE_UI_PLAYER_1 = 5;
    private static final int UPDATE_UI_PLAYER_2 = 6;
    private static final int UPDATE_GUESS_CORRECTNESS = 7;

    //handlers and threads
    Handler uiHandler;
    Handler p1Handler;
    Handler p2Handler;
    Thread player1, player2;

    //views
    EditText editTextP1Choice;
    EditText editTextP2Choice;
    EditText editTextCorrectPlaceNumbers;
    EditText editTextWrongPlaceNumbers;

    TextView textViewWinText;

    LinearLayout p1Guesses;
    LinearLayout p2Guesses;
    RelativeLayout p1Container;
    RelativeLayout p2Container;

    Button button_start;

    int counter_moves;

    Context mContext;

    //ArrayList from which the players generates the guesses
    ArrayList<Integer> poolNumbersP1 = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    ArrayList<Integer> poolNumbersP2 = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

    //Flag to see if the player1 has chosen the number
    private boolean FLAG_p1Chosen = false;

    //Data structures to hold and keep track of player guesses
    int[] p1Choice = new int[4];
    int[] p1Guess = new int[4];
    HashMap<Integer, Integer> p1PrevGuess = new HashMap<Integer, Integer>();

    int[] p2Choice = new int[4];
    int[] p2Guess = new int[4];
    int[] p2PrevGuess = new int[4];

    ArrayList<Integer> p1CorrectPosition = new ArrayList<>();
    ArrayList<Integer> p1WrongPosition = new ArrayList<>();

    ArrayList<Integer> p2CorrectPosition = new ArrayList<>();
    ArrayList<Integer> p2WrongPosition = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set layout for the activity
        setContentView(R.layout.activity_main);

        //find all ui elements needed for this activity
        editTextP1Choice = (EditText) findViewById(R.id.editText_Player1Choice);
        editTextP2Choice = (EditText) findViewById(R.id.editText_Player2Choice);
        editTextCorrectPlaceNumbers = (EditText) findViewById(R.id.editText_CorrectPlaceNumbers);
        editTextWrongPlaceNumbers = (EditText) findViewById(R.id.editText_WrongPlaceNumbers);

        p1Guesses = (LinearLayout) findViewById(R.id.layout_Player1Guesses);
        p2Guesses = (LinearLayout) findViewById(R.id.layout_Player2Guesses);
        p1Container = (RelativeLayout) findViewById(R.id.layout_Player1);
        p2Container = (RelativeLayout) findViewById(R.id.layout_Player2);

        button_start = (Button) findViewById(R.id.button_startGame);

        textViewWinText = (TextView) findViewById(R.id.textView_winStatus);

        //counter to keep track of player moves
        counter_moves = 40;

        mContext = this;

        //initialize previous guess hashmap for player1
        for(int i=0; i<4; i++){
            p1PrevGuess.put((Integer)i, -1);
        }

        //define uihandler
        uiHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Message message;
                switch (msg.what){

                    // message to start the game
                    case START_GAME:{
                        Log.i("Guess Four","Game started!!");
                        //send message to player1 to chose the number
                        //by default player1 will make the 1st move on game start
                        p1Container.setBackgroundResource(R.drawable.border_red);
                        message = p1Handler.obtainMessage(GUESS_NUMBER);
                        p1Handler.sendMessage(message);
                        break;
                    }

                    // Update the UI for the player1
                    case UPDATE_UI_PLAYER_1:{
                        // decrement the counter as player 1 has guessed the number
                        counter_moves--;

                        //if moves are remaining then, send message to player2 to make guess
                        //otherwise, end the game.
                        if(counter_moves > 0) {
                            //check if player2 has chosen the number or not
                            //if not, then send message to player2 to choose the number
                            //otherwise, send message to player2 to check the guess of player1
                            if (p2Choice[0] == 0 && p2Choice[1] == 0) {
                                editTextP1Choice.setText(String.valueOf(msg.arg1));
                                p2Handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Log.i("Thread 2", " Sleeping");
                                            Thread.sleep(2000);
                                        } catch (InterruptedException e) {
                                            Log.i("Guess Four", "!!!!!!!!!!THREAD INTERRUPTED TO RESTART GAME!!!!!!!!!");
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                p1Container.setBackgroundResource(R.drawable.border);
                                p2Container.setBackgroundResource(R.drawable.border_red);
                                message = p2Handler.obtainMessage(GUESS_NUMBER);
                                p2Handler.sendMessage(message);
                            } else {
                                TextView show_guess = new TextView(mContext);
                                show_guess.setText(String.valueOf(msg.arg1));
                                p1Guesses.addView(show_guess);
                                Log.i("Guess Four", "Player1 Guessed");

                                p2Handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Log.i("Thread 2", " Sleeping");
                                            Thread.sleep(2000);
                                        } catch (InterruptedException e) {
                                            Log.i("Guess Four", "!!!!!!!!!!THREAD INTERRUPTED TO RESTART GAME!!!!!!!!!");
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                message = p2Handler.obtainMessage(CHECK_NUMBER);
                                message.arg1 = msg.arg1;
                                p2Handler.sendMessage(message);
                            }
                        }
                        else{
                            endGame();
                            textViewWinText.setText("Maximum moves reached! Game is ended !!");
                            textViewWinText.setVisibility(View.VISIBLE);
                        }
                        break;
                    }

                    //Update UI for the player2
                    case UPDATE_UI_PLAYER_2:{
                        //decrement count and player2 has guessed a number
                        counter_moves--;
                        //if there is no child in player1 guess layout, then player1 hasn't guessed the number yet but chose the initial number
                        //hence, send message to player1 to guess the number
                        //else, send message to player1 to check the number guessed by player 2
                        if(p1Guesses.getChildCount() == 0){
                            editTextP2Choice.setText(String.valueOf(msg.arg1));
                            p1Handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Log.i("Thread 1", " Sleeping");
                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        Log.i("Guess Four", "!!!!!!!!!!THREAD INTERRUPTED TO RESTART GAME!!!!!!!!!");
                                        e.printStackTrace();
                                    }
                                }
                            });

                            p1Container.setBackgroundResource(R.drawable.border_red);
                            p2Container.setBackgroundResource(R.drawable.border);

                            message = p1Handler.obtainMessage(GUESS_NUMBER);
                            p1Handler.sendMessage(message);
                        }
                        else {
                            TextView show_guess = new TextView(mContext);
                            show_guess.setText(String.valueOf(msg.arg1));
                            p2Guesses.addView(show_guess);

                            Log.i("Guess Four", "Player2 Guessed");
                            p1Handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Log.i("Thread 1", " Sleeping");
                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        Log.i("Guess Four", "!!!!!!!!!!THREAD INTERRUPTED TO RESTART GAME!!!!!!!!!");
                                        e.printStackTrace();
                                    }
                                }
                            });
                            message = p1Handler.obtainMessage(CHECK_NUMBER);
                            message.arg1 = msg.arg1;
                            p1Handler.sendMessage(message);
                        }
                        break;

                    }

                    case UPDATE_GUESS_CORRECTNESS:{

                        //if counter is odd currently, then we have to update the correctness of the guess for player1,
                        //else, update the correctness of the guess for player2
                        //if any of the player has 4 numbers guessed in correct position, that player wins
                        if ((counter_moves%2) == 1){
                            if(p1CorrectPosition.size() == 4){
                                endGame();
                                textViewWinText.setText("Player 1 won !!");
                                textViewWinText.setVisibility(View.VISIBLE);
                            }
                            else {
                                editTextCorrectPlaceNumbers.setText(arrayListToString(p1CorrectPosition));
                                editTextWrongPlaceNumbers.setText(arrayListToString(p1WrongPosition));
                                p2Handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Log.i("Thread 2", " Sleeping");
                                            Thread.sleep(2000);
                                        } catch (InterruptedException e) {
                                            Log.i("Guess Four", "!!!!!!!!!!THREAD INTERRUPTED TO RESTART GAME!!!!!!!!!");
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                p1Container.setBackgroundResource(R.drawable.border);
                                p2Container.setBackgroundResource(R.drawable.border_red);
                                message = p2Handler.obtainMessage(GUESS_NUMBER);
                                p2Handler.sendMessage(message);
                            }
                        }
                        else {
                            if (p2CorrectPosition.size() == 4) {
                                endGame();
                                textViewWinText.setText("Player 2 won !!");
                                textViewWinText.setVisibility(View.VISIBLE);
                            } else {
                                editTextCorrectPlaceNumbers.setText(arrayListToString(p2CorrectPosition));
                                editTextWrongPlaceNumbers.setText(arrayListToString(p2WrongPosition));
                                p1Handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Log.i("Thread 1", " Sleeping");
                                            Thread.sleep(2000);
                                        } catch (InterruptedException e) {
                                            Log.i("Guess Four", "!!!!!!!!!!THREAD INTERRUPTED TO RESTART GAME!!!!!!!!!");
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                p1Container.setBackgroundResource(R.drawable.border_red);
                                p2Container.setBackgroundResource(R.drawable.border);
                                message = p1Handler.obtainMessage(GUESS_NUMBER);
                                p1Handler.sendMessage(message);
                            }
                        }
                        break;
                    }

                    default:
                        break;
                }
            }
        };

        //onClick listener for start button
        button_start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //if player1 and player2 thread is already started, interrupt the game and restart
                if (player1!=null && player2!=null){
                    player1.interrupt();
                    player2.interrupt();
                    uiHandler.removeCallbacksAndMessages(null);
                    p1Handler.removeCallbacksAndMessages(null);
                    p2Handler.removeCallbacksAndMessages(null);
                    p1Handler.getLooper().quitSafely();
                    p1Handler.getLooper().quitSafely();
                    resetAll();
                }


                //player1 thread
                player1 = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Looper.prepare();

                        //handler for player1
                        p1Handler = new Handler() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void handleMessage(Message msg) {
                                Message message;
                                switch (msg.what) {

                                    case GUESS_NUMBER: {
                                        //if player1 has not chosen initial number, call function to chooseNumber()
                                        //else call function to guess number, guessPlayer1()
                                        if (p1Choice[0] == 0 && p1Choice[1] == 0) {
                                            message = uiHandler.obtainMessage(UPDATE_UI_PLAYER_1);
                                            message.arg1 = chooseNumber();
                                            uiHandler.sendMessage(message);
                                        } else {
                                            message = uiHandler.obtainMessage(UPDATE_UI_PLAYER_1);
                                            message.arg1 = guessPlayer1(0,0);
                                            uiHandler.sendMessage(message);
                                        }
                                        break;
                                    }

                                    //check number of player2
                                    case CHECK_NUMBER: {
                                        checkNumber(p1Choice,p2Guess);
                                        message = uiHandler.obtainMessage(UPDATE_GUESS_CORRECTNESS);
                                        uiHandler.sendMessage(message);
                                        break;
                                    }

                                    default:
                                        break;
                                }
                            }

                        };
                        Log.i("P1", "Handler created");
                        Message message = uiHandler.obtainMessage(START_GAME);
                        uiHandler.sendMessage(message);
                        Looper.loop();
                    }
                });
                player1.start();

                player2 = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        Looper.prepare();

                        //player2 handler
                        p2Handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                Message message;
                                switch (msg.what) {

                                    //if player1 has not chosen initial number, call function to chooseNumber()
                                    //else call function to guess number, guessPlayer1()
                                    case GUESS_NUMBER: {
                                        if (p2Choice[0] == 0 && p2Choice[1] == 0) {
                                            message = uiHandler.obtainMessage(UPDATE_UI_PLAYER_2);
                                            message.arg1 = chooseNumber();
                                            uiHandler.sendMessage(message);
                                        } else {
                                            Log.i("counter", "" + counter_moves);
                                            message = uiHandler.obtainMessage(UPDATE_UI_PLAYER_2);
                                            message.arg1 = guessPlayer2();
                                            uiHandler.sendMessage(message);
                                        }
                                        break;
                                    }

                                    //check number of player1
                                    case CHECK_NUMBER: {
                                        int number = msg.arg1;
                                        int[] p1Number = intTointArray(number);
                                        checkNumber(p2Choice,p1Number);
                                        message = uiHandler.obtainMessage(UPDATE_GUESS_CORRECTNESS);
                                        uiHandler.sendMessage(message);
                                        break;

                                    }
                                    default: {
                                        break;
                                    }
                                }
                            }
                        };

                        Looper.loop();

                    }
                });
                player2.start();
            }
        });
    }

    //function to reset all data structure and bring the game into initial stage
    private void resetAll() {
        editTextP1Choice.setText("");
        editTextP2Choice.setText("");
        editTextCorrectPlaceNumbers.setText("");
        editTextWrongPlaceNumbers.setText("");

        textViewWinText.setText("");

        p1Guesses.removeAllViews();
        p2Guesses.removeAllViews();

        counter_moves=40;

        FLAG_p1Chosen = false;

        p1Choice = new int[]{0,0,0,0};
        p1Guess = new int[]{0,0,0,0};
        p1PrevGuess.clear();
        p2Choice = new int[]{0,0,0,0};
        p2Guess = new int[]{0,0,0,0};
        p2PrevGuess = new int[]{0,0,0,0};

        poolNumbersP1.clear();
        for(int i=0; i<10; i++){
            poolNumbersP1.add((Integer)i);
        }
        p1CorrectPosition.clear();
        p2CorrectPosition.clear();
        p1WrongPosition.clear();
        p2WrongPosition.clear();
    }

    //function to end the game and quit the threads
    void endGame(){
        player1.interrupt();
        player2.interrupt();
        uiHandler.removeCallbacksAndMessages(null);
        p1Handler.removeCallbacksAndMessages(null);
        p2Handler.removeCallbacksAndMessages(null);
        p1Handler.getLooper().quitSafely();
        p1Handler.getLooper().quitSafely();
    }

    //function to generate random number for both players
    int chooseNumber(){
        int generated;
        if(!FLAG_p1Chosen){
            Collections.shuffle(poolNumbersP1);
            for(int i=0; i<4; i++){
                p1Choice[i] = poolNumbersP1.get(i);
            }
            if(p1Choice[0] == 0){
                generated = chooseNumber();
            }
            FLAG_p1Chosen = true;
            generated = Integer.parseInt(intArrayToString(p1Choice));
            return generated;
        }
        else{
            Collections.shuffle(poolNumbersP2);
            for(int i=0; i<4; i++){
                p2Choice[i] = poolNumbersP2.get(i);
            }
            if(p2Choice[0] == 0){
                generated = chooseNumber();
            }
            generated = Integer.parseInt(intArrayToString(p2Choice));
            return generated;
        }
    }

    //function to guess the number for player1 (intelligent player)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    int guessPlayer1(int correct, int incorrect){
        int generated;
        Collections.shuffle(poolNumbersP1);
        int[] temp = new int[]{-1,-1,-1,-1};

        if(p1Guess[0] != 0) {
            if (p1CorrectPosition.size() > 0) {
                for (int i = 0; i < p1CorrectPosition.size(); i++) {
                    temp[getKeyByValue(p1PrevGuess, p1CorrectPosition.get(i))] = p1CorrectPosition.get(i);
                }
            }
            if (p1WrongPosition.size() > 0) {
                for (int i = 0; i < p1WrongPosition.size(); i++) {
                    poolNumbersP1.add((Integer) p1WrongPosition.get(i));
                }
            }
        }

        for(int i=0; i<4; i++){
            if(temp[i] == -1) {
                p1Guess[i] = poolNumbersP1.get(0);
                p1PrevGuess.put((Integer) i, (Integer) p1Guess[i]);
                poolNumbersP1.remove(0);
            }
            else{
                p1Guess[i] = temp[i];
            }
        }
        if(p1Guess[0] == 0){
            for(int i=0; i<4; i++){
                poolNumbersP1.add((Integer)p1Guess[i]);
            }
            generated = guessPlayer1(correct, incorrect);
        }
        generated = Integer.parseInt(intArrayToString(p1Guess));

        return generated;
    }

    //function to generate guess for player2 (dumb player)
    int guessPlayer2(){
        Log.i("Player 2 Guessing", "Gussed");
        int generated;
        Collections.shuffle(poolNumbersP2);
        for(int i=0; i<4; i++){
            p2Guess[i] = poolNumbersP2.get(i);
            Log.i("p2guess " + i,""+p2Guess[i]);
            p2PrevGuess[i] = poolNumbersP2.get(i);
        }
        if(p2Guess[0] == 0){
            generated = guessPlayer2();
        }
        generated = Integer.parseInt(intArrayToString(p2Guess));

        return generated;
    }

    //function to check similarity between two array of int
    //checks for same numbers at correct positions and same numbers at different positions
    void checkNumber(int[] chosen, int[] guess){
        Log.i ("Checking", "Checked");
        if(p1Guesses.getChildCount() > p2Guesses.getChildCount()){
            p1CorrectPosition.clear();
            p1WrongPosition.clear();
        }
        else {
            p2CorrectPosition.clear();
            p2WrongPosition.clear();
        }
        for(int i=0; i<4; i++) {
            for(int j=0; j<4; j++) {
                if(chosen[i]==guess[j]) {
                    if(i==j) {
                        if(p1Guesses.getChildCount() > p2Guesses.getChildCount()){
                            p1CorrectPosition.add((Integer)chosen[i]);
                        }
                        else {
                            p2CorrectPosition.add((Integer)chosen[i]);
                        }
                    }
                    else {
                        if(p1Guesses.getChildCount() > p2Guesses.getChildCount()){
                            p1WrongPosition.add((Integer)chosen[i]);
                        }
                        else {
                            p2WrongPosition.add((Integer)chosen[i]);
                        }
                    }
                }
            }
        }
    }

    //function to convert array of int to String
    String intArrayToString(int[] a){
        String output = Integer.toString(a[0]);
        for(int i=1; i<a.length;i++) {
            output = output + Integer.toString(a[i]);
        }
        return output;
    }

    //function to convert ArrayList to String
    String arrayListToString(ArrayList<Integer> arrayList){
        String output="";
        if(arrayList.size() != 0) {
            output = String.valueOf(arrayList.get(0));
            for (int i = 1; i < arrayList.size(); i++) {
                output += ',' + String.valueOf(arrayList.get(i));
            }
        }
        return output;
    }

    //function convert int into array of int
    int[] intTointArray(int a){
        String temp = Integer.toString(a);
        int[] arr1 = new int[temp.length()];
        for (int i = 0; i < temp.length(); i++)
        {
            arr1[i] = temp.charAt(i) - '0';
        }
        return arr1;
    }

    //function to get key for a particular value
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }


}
