/**
 * This file is made to handle all the processes in the second page(activity) of application
 */
package com.example.shweta.cricworld;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import static com.example.shweta.cricworld.R.id.imageView;

public class MainActivity extends AppCompatActivity {
/*
 This class provides methods and variables for MainActivity i.e the main content of applocation
 */
    private SharedPreferences spref;   //declaring shared preference for saving data
    private SharedPreferences.Editor ed;
    private Button mEditButton;
    private Button mSaveButton;
    private EditText team_name;
    private EditText mCaptain;
    private EditText mCoach;
    private EditText mTestRank;
    private EditText mOdiRank;
    private EditText mT20Rank;
    private ImageView mImgView;
    private FloatingActionButton ac;
    private static final int SELECT_PICTURE=100;   //variale used for uplaoding image

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        spref = getApplicationContext().getSharedPreferences("mypref",0);  //gettiing shared preference
        ed = spref.edit();                                               //making editor
        setContentView(R.layout.activity_main);
        mEditButton = (Button)findViewById(R.id.edit);
        mSaveButton = (Button)findViewById(R.id.save);
        ac = (FloatingActionButton)findViewById(R.id.action);
        team_name = (EditText)findViewById(R.id.team);
        mCaptain = (EditText)findViewById(R.id.cap);
        mCoach = (EditText)findViewById(R.id.coach);
        mTestRank = (EditText)findViewById(R.id.tst);
        mOdiRank = (EditText)findViewById(R.id.odi);
        mT20Rank = (EditText)findViewById(R.id.T20);
        mImgView =(ImageView)findViewById(imageView);
        changeTintcolor();                          //for changing tint color

        try
        {
            showdata();                               //displaying data
        }
        catch(Exception e){}

        mEditButton.setOnClickListener(new View.OnClickListener()        //listener for edit button click
        {
            public void onClick(View view)
            {
                team_name.setEnabled(true);                    //enabling text fields
                mCaptain.setEnabled(true);
                mCoach.setEnabled(true);
                mTestRank.setEnabled(true);
                mOdiRank.setEnabled(true);
                mT20Rank.setEnabled(true);
                ((EditText)findViewById(R.id.team)).getBackground().
                        setColorFilter(getResources().getColor(R.color.background_light),
                                PorterDuff.Mode.SRC_ATOP);
                ((EditText)findViewById(R.id.cap)).getBackground()
                        .setColorFilter(getResources().getColor(R.color.background_light),
                                PorterDuff.Mode.SRC_ATOP);
                ((EditText)findViewById(R.id.coach)).getBackground()
                        .setColorFilter(getResources().getColor(R.color.background_light),
                                PorterDuff.Mode.SRC_ATOP);
                ((EditText)findViewById(R.id.tst)).getBackground()
                        .setColorFilter(getResources().getColor(R.color.background_light),
                                PorterDuff.Mode.SRC_ATOP);
                ((EditText)findViewById(R.id.odi)).getBackground()
                        .setColorFilter(getResources().getColor(R.color.background_light),
                                PorterDuff.Mode.SRC_ATOP);
                ((EditText)findViewById(R.id.T20)).getBackground()
                        .setColorFilter(getResources().getColor(R.color.background_light),
                                PorterDuff.Mode.SRC_ATOP);
                mEditButton.setVisibility(View.GONE);
                mSaveButton.setVisibility(View.VISIBLE);
                ac.setVisibility(View.VISIBLE);
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener()            //listener for save button click
        {
            public void onClick(View view)
            {
                //saving data in shared preference
                ed.putString("team_name", ((EditText)findViewById(R.id.team))
                        .getText().toString());
                ed.putString("Captain", ((EditText)findViewById(R.id.cap))
                        .getText().toString());
                ed.putString("Coach",((EditText)findViewById(R.id.coach))
                        .getText().toString());
                ed.putString("TestRank",((EditText)findViewById(R.id.tst))
                        .getText().toString());
                ed.putString("OdiRank",((EditText)findViewById(R.id.odi))
                        .getText().toString());
                ed.putString("T20Rank",((EditText)findViewById(R.id.T20))
                        .getText().toString());

                //converting image in bitmap
                Bitmap bitImg = ((BitmapDrawable) mImgView.getDrawable()).getBitmap();

                // saving encoded image in shared preference
                ed.putString("imageP", encodeTobase64(bitImg));
                ed.commit();
                team_name.setEnabled(false);
                mCaptain.setEnabled(false);
                mCoach.setEnabled(false);
                mTestRank.setEnabled(false);
                mOdiRank.setEnabled(false);
                mT20Rank.setEnabled(false);
                mSaveButton.setVisibility(View.GONE);
                mEditButton.setVisibility(View.VISIBLE);
                ac.setVisibility(View.GONE);
                changeTintcolor();

            }
        });

        ac.setOnClickListener(new View.OnClickListener() {      //listener for image change (action)button click
            public void onClick(View view) {
                openImg();                                      //function to upload image
            }
        });

    }

    private static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }  //function to encode bitmap image

    private Bitmap decodeBase64(String imageP) {
        byte[] decodedByte = Base64.decode(imageP, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }            //function to decode image

    private void openImg() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Set the image in ImageView
                    mImgView.setImageURI(selectedImageUri);
                }
            }
        }
    }       //for uploading image from device

    private void changeTintcolor() {
        ((EditText)findViewById(R.id.team)).getBackground()
                .setColorFilter(getResources().getColor(R.color.holo_blue_dark),
                        PorterDuff.Mode.SRC_ATOP);
        ((EditText)findViewById(R.id.cap)).getBackground()
                .setColorFilter(getResources().getColor(R.color.holo_blue_dark),
                        PorterDuff.Mode.SRC_ATOP);
        ((EditText)findViewById(R.id.coach)).getBackground()
                .setColorFilter(getResources().getColor(R.color.holo_blue_dark),
                        PorterDuff.Mode.SRC_ATOP);
        ((EditText)findViewById(R.id.tst)).getBackground()
                .setColorFilter(getResources().getColor(R.color.holo_blue_dark),
                        PorterDuff.Mode.SRC_ATOP);
        ((EditText)findViewById(R.id.odi)).getBackground()
                .setColorFilter(getResources().getColor(R.color.holo_blue_dark),
                        PorterDuff.Mode.SRC_ATOP);
        ((EditText)findViewById(R.id.T20)).getBackground()
                .setColorFilter(getResources().getColor(R.color.holo_blue_dark),
                        PorterDuff.Mode.SRC_ATOP);
    }

    private void showdata() {
        Log.d("check data",spref.getString("team_name",""));
        team_name.setText(spref.getString("team_name",""));
        mCaptain.setText(spref.getString("Captain",""));
        mCoach.setText(spref.getString("Coach",""));
        mTestRank.setText(spref.getString("TestRank",""));
        mOdiRank.setText(spref.getString("OdiRank",""));
        mT20Rank.setText(spref.getString("T20Rank",""));
        Bitmap bp= decodeBase64(spref.getString("imageP",""));
        mImgView.setImageBitmap(bp);
    }

}
