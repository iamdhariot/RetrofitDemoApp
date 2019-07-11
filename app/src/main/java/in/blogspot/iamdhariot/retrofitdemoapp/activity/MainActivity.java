package in.blogspot.iamdhariot.retrofitdemoapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import in.blogspot.iamdhariot.retrofitdemoapp.MovieApiService;
import in.blogspot.iamdhariot.retrofitdemoapp.R;
import in.blogspot.iamdhariot.retrofitdemoapp.adapter.MoviesAdapter;
import in.blogspot.iamdhariot.retrofitdemoapp.model.Movie;
import in.blogspot.iamdhariot.retrofitdemoapp.model.MovieResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static Retrofit retrofit = null;
    private RecyclerView recyclerView = null;
    public static final String API_KEY = "ba6aade797a43fc3b10371e1235d90d7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        connectAndGetApiData();
    }
    // This method create an instance of Retrofit
    // set base url
    private void connectAndGetApiData() {
     if(retrofit==null){
         retrofit = new Retrofit
                 .Builder()
                 .baseUrl(BASE_URL)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();

     }
        MovieApiService movieApiService = retrofit.create(MovieApiService.class);
        Call<MovieResponse> call = movieApiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MoviesAdapter(movies,R.layout.list_item_movie,MainActivity.this));
                Log.d(TAG, "Number of movies received: " + movies.size());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });


    }
}
