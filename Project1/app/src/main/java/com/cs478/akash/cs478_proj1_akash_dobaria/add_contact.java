package com.cs478.akash.cs478_proj1_akash_dobaria;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class add_contact extends AppCompatActivity {

    // EditText to get user input, button to save contact and a TextView to show messages
    protected EditText contact_name;
    protected Button save_contact;
    protected TextView message;
    public String name;

    //Request variable
    static final int CREATE_CONTACT_REQUEST = 1 ;

    //Flag to close activity on save
    public static final String INTENT_KEY_FINISH_ACTIVITY_ON_SAVE_COMPLETED = "finishActivityOnSaveCompleted";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        //Get UI objects to variables
        contact_name = (EditText) findViewById(R.id.txt_contact_name);
        save_contact = (Button) findViewById(R.id.btn_save_contact);
        message = (TextView) findViewById(R.id.tv_message);

        //Event listener for button
        save_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get string from EditText widget
                name = contact_name.getText().toString();

                //Trim string to remove preceding or trailing white spaces
                name = name.trim();

                //Check if the name entered contains at least one space and is not null.
                if (name.contains(" ") && name!=""){
                    //Create intent to save contact
                    Intent create_contact = new Intent(Intent.ACTION_INSERT);
                    create_contact.setType(ContactsContract.Contacts.CONTENT_TYPE);
                    create_contact.putExtra(ContactsContract.Intents.Insert.NAME, name);
                    create_contact.putExtra(INTENT_KEY_FINISH_ACTIVITY_ON_SAVE_COMPLETED, true);
                    startActivityForResult(create_contact, CREATE_CONTACT_REQUEST);
                }
                else{
                    // Show appropriate message if the input is not acceptable for name
                    if(name.length()==0){
                        message.setText("Message:\nThe text field cannot be empty. Enter first and last name.");
                    }
                    else{
                        message.setText("Message:\nEnter both, first and last name.");
                    }

                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //Check for the return flag and show appropriate message
        Log.i ("Data : ", " "+data);
        Log.i("The result code: ", " " + resultCode);
        if (requestCode == CREATE_CONTACT_REQUEST) {
            Log.i("The result code: ", " " + resultCode);
            if (resultCode == RESULT_OK){
                Log.i("The result code: ", " " + resultCode);
                message.setText("Message:\nContact was saved successfully");
            }
            else{
                Log.i("The result code: ", " " + resultCode);
                message.setText("Message:\nContact was not saved successfully");
            }
        }
    }
}
