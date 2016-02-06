package com.example.deedeehan.daylight;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class CommentActivity extends AppCompatActivity {

    private Button mOkButton;
    private Button mCancelButton;
    private EditText mTypeText;
    private EditText mCommentText;
    private RatingBar mRatingBar;

    public String type;
    public String comment;
    public int numStars;
    public double latitude;
    public double longitude;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getNumStars() {
        return numStars;
    }

    public void setNumStars(int numStars) {
        this.numStars = numStars;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        mOkButton = (Button)findViewById(R.id.okButton);
        mCancelButton = (Button)findViewById(R.id.cancelButton);
        mTypeText = (EditText)findViewById(R.id.mType);
        mCommentText = (EditText)findViewById(R.id.comment);
        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);

        setType(mTypeText.getText().toString());
        setComment(mCommentText.getText().toString());
        setNumStars(mRatingBar.getNumStars());

        Intent intent = getIntent();
        setLatitude(intent.getDoubleExtra("latitude", 0.0));
        setLongitude(intent.getDoubleExtra("longitude", 0.0));


        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSMS newMessage = new doSMS();
                newMessage.compose(latitude, longitude, type, numStars, comment);


            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });
    }

}
