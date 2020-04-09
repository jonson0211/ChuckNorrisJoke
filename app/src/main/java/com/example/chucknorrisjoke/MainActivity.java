package com.example.chucknorrisjoke;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //https://api.chucknorris.io/jokes/random?category=dev

    private TextView quote;
    private Button button;
    private ImageView cursor;
    public static final Random RANDOM = new Random();
    public static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quote = (TextView)findViewById(R.id.quote);
        button = (Button)findViewById(R.id.button);
        cursor = (ImageView)findViewById(R.id.cursor);

        Log.d(TAG, "onCreate: Yay!");



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.chucknorris.io").addConverterFactory(GsonConverterFactory.create()).build();
                Log.d(TAG, "onCreate: Yay 2");

                //whilst the data is being fetched from the API, the text is replaced by by a string within an array of reply options, selected at random
                String [] replyOptions = new String[]{"Not good enough for ya? Check this out...", "No good? How about this one...", "You're a tough cookie aren't ya. Hear this one...", "Ok maybe that wasn't the best one. What about this...", "Maybe you'll appreciate this one...", "Are all programmers this hard to impress? How's this..."};
                quote.setText(replyOptions[RANDOM.nextInt(6)]);
                JokeService service = retrofit.create(JokeService.class);
                Call<Joke> call = service.getValue();

                //setting cursor image to mouse1, to emulate the motion of clicking
                cursor.setImageResource(getResources().getIdentifier("mouse1", "drawable", "com.example.chucknorrisjoke"));

                call.enqueue(new Callback<Joke>() {
                    @Override
                    public void onResponse(Call<Joke> call, Response<Joke> response) {
                        Log.d(TAG, "onResponse: Success");

                        //instantiate joke object with the returned response
                        Joke joke = response.body();

                        //call the getValue() method from Joke.java to get the actual Chuck Norris joke
                        quote.setText(joke.getValue());

                        //setting cursor image back to original
                        cursor.setImageResource(getResources().getIdentifier("mouse2", "drawable", "com.example.chucknorrisjoke"));
                    }

                    @Override
                    public void onFailure(Call<Joke> call, Throwable t) {
                        Log.d(TAG, "onFailure: Failed");

                    }
                });
            }
        });


    }



}
