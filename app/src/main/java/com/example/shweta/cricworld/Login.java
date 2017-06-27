package com.example.shweta.cricworld;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This file is handling all the processes in first page(activity)
 */
public class Login extends AppCompatActivity {
    /**
     * this class provides variables and methods used in login activity
     * @param savedInstanceState is used
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //to display all the content
        setContentView(R.layout.activity_login);

        //taking hardcode data
        final String un = "shweta";
        final String pass = "mindfire";
        Button mb = (Button)findViewById(R.id.button);

        //event listener for  button click
        mb.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                //fetching input entered by user i.e username and password
                String usr = ((EditText)findViewById(R.id.editText))
                        .getText().toString();
                String passw = ((EditText)findViewById(R.id.editText2))
                        .getText().toString();

                //checking whether user name field is filled or empty.
                if (usr.matches("")) {
                    Toast.makeText(Login.this, "username cannot be empty",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //checking whether the password id entered or not
                if (passw.matches("")) {
                    Toast.makeText(Login.this, "Please enter the password",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //checking user entered values with value saved previously
                if ((un.equalsIgnoreCase(usr)) && (pass.equalsIgnoreCase(passw))) {
                    //making object of intent to move to next activity
                    Intent i = new Intent(Login.this, MainActivity.class);

                    //starting the next activity
                    startActivity(i);

                    //finising current activity
                    finish();
                }else{
                    //getting context object for Toast to print message for user
                    Context context = getApplicationContext();
                    CharSequence text = "UserName or Password is incorrect!";

                    //setting duration for taost display
                    int duration = Toast.LENGTH_LONG;

                    //displaying message through toast
                    Toast.makeText(context, text, duration).show();
                }

            }
        });
    }
}
