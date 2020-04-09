package com.example.chucknorrisjoke;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JokeService {

    @GET("/jokes/random?category=dev")
    Call<Joke> getValue();
}
