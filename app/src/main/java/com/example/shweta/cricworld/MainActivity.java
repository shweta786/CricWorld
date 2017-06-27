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
import android.support.v7.widget.AppCompatImageView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import static com.example.shweta.cricworld.R.id.imageView;

public class MainActivity extends AppCompatActivity {

    SharedPreferences spref;   //declaring shared preference for saving data
    SharedPreferences.Editor ed;
    Button mb,sv;
    EditText tm, cp,co,tst,od,t2;
    private static final int SELECT_PICTURE=100;   //variale used for uplaoding image
    ImageView imgView;
    FloatingActionButton ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        spref= getApplicationContext().getSharedPreferences("mypref",0);  //gettiing shared preference
        ed= spref.edit();                                               //making editor
        setContentView(R.layout.activity_main);
        mb= (Button)findViewById(R.id.edit);
        sv= (Button)findViewById(R.id.save);
        ac= (FloatingActionButton)findViewById(R.id.action);
        tm= (EditText)findViewById(R.id.team);
        cp= (EditText)findViewById(R.id.cap);
        co= (EditText)findViewById(R.id.coach);
        tst= (EditText)findViewById(R.id.tst);
        od= (EditText)findViewById(R.id.odi);
        t2= (EditText)findViewById(R.id.T20);
        imgView=(ImageView)findViewById(imageView);
        changeTintcolor();                          //for changing tint color

        try
        {
            showdata();                               //displaying data
        }
        catch(Exception e){}

        mb.setOnClickListener(new View.OnClickListener()        //listener for edit button click
        {
            public void onClick(View view)
            {
                tm.setEnabled(true);                    //enabling text fields
                cp.setEnabled(true);
                co.setEnabled(true);
                tst.setEnabled(true);
                od.setEnabled(true);
                t2.setEnabled(true);
                ((EditText)findViewById(R.id.team)).getBackground().setColorFilter(getResources().getColor(R.color.background_light), PorterDuff.Mode.SRC_ATOP);
                ((EditText)findViewById(R.id.cap)).getBackground().setColorFilter(getResources().getColor(R.color.background_light), PorterDuff.Mode.SRC_ATOP);
                ((EditText)findViewById(R.id.coach)).getBackground().setColorFilter(getResources().getColor(R.color.background_light), PorterDuff.Mode.SRC_ATOP);
                ((EditText)findViewById(R.id.tst)).getBackground().setColorFilter(getResources().getColor(R.color.background_light), PorterDuff.Mode.SRC_ATOP);
                ((EditText)findViewById(R.id.odi)).getBackground().setColorFilter(getResources().getColor(R.color.background_light), PorterDuff.Mode.SRC_ATOP);
                ((EditText)findViewById(R.id.T20)).getBackground().setColorFilter(getResources().getColor(R.color.background_light), PorterDuff.Mode.SRC_ATOP);
                mb.setVisibility(View.GONE);
                sv.setVisibility(View.VISIBLE);
                ac.setVisibility(View.VISIBLE);
            }
        });

        sv.setOnClickListener(new View.OnClickListener()            //listener for save button click
        {
            public void onClick(View view)
            {
                ed.putString("team_name", ((EditText)findViewById(R.id.team)).getText().toString());        //saving data in shared preference
                ed.putString("Captain", ((EditText)findViewById(R.id.cap)).getText().toString());
                ed.putString("Coach",((EditText)findViewById(R.id.coach)).getText().toString());
                ed.putString("TestRank",((EditText)findViewById(R.id.tst)).getText().toString());
                ed.putString("OdiRank",((EditText)findViewById(R.id.odi)).getText().toString());
                ed.putString("T20Rank",((EditText)findViewById(R.id.T20)).getText().toString());

                Bitmap bitImg = ((BitmapDrawable)imgView.getDrawable()).getBitmap();        //converting image in bitmap
                ed.putString("imageP", encodeTobase64(bitImg));                         // saving encoded image in shared preference
                ed.commit();
                tm.setEnabled(false);
                cp.setEnabled(false);
                co.setEnabled(false);
                tst.setEnabled(false);
                od.setEnabled(false);
                t2.setEnabled(false);
                sv.setVisibility(View.GONE);
                mb.setVisibility(View.VISIBLE);
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
    }           //function to decode image

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
                    imgView.setImageURI(selectedImageUri);
                }
            }
        }
    }       //for uploading image from device

    private void changeTintcolor() {
        ((EditText)findViewById(R.id.team)).getBackground().setColorFilter(getResources().getColor(R.color.holo_blue_dark), PorterDuff.Mode.SRC_ATOP);
        ((EditText)findViewById(R.id.cap)).getBackground().setColorFilter(getResources().getColor(R.color.holo_blue_dark), PorterDuff.Mode.SRC_ATOP);
        ((EditText)findViewById(R.id.coach)).getBackground().setColorFilter(getResources().getColor(R.color.holo_blue_dark), PorterDuff.Mode.SRC_ATOP);
        ((EditText)findViewById(R.id.tst)).getBackground().setColorFilter(getResources().getColor(R.color.holo_blue_dark), PorterDuff.Mode.SRC_ATOP);
        ((EditText)findViewById(R.id.odi)).getBackground().setColorFilter(getResources().getColor(R.color.holo_blue_dark), PorterDuff.Mode.SRC_ATOP);
        ((EditText)findViewById(R.id.T20)).getBackground().setColorFilter(getResources().getColor(R.color.holo_blue_dark), PorterDuff.Mode.SRC_ATOP);
    }

    private void showdata() {
        Log.d("check data",spref.getString("team_name",""));
        tm.setText(spref.getString("team_name",""));
        cp.setText(spref.getString("Captain",""));
        co.setText(spref.getString("Coach",""));
        tst.setText(spref.getString("TestRank",""));
        od.setText(spref.getString("OdiRank",""));
        t2.setText(spref.getString("T20Rank",""));
        Bitmap bp= decodeBase64(spref.getString("imageP",""));
        imgView.setImageBitmap(bp);
    }

}
