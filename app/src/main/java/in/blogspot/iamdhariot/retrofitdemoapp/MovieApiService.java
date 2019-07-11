package in.blogspot.iamdhariot.retrofitdemoapp;

import in.blogspot.iamdhariot.retrofitdemoapp.model.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApiService {
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key")
                                          String apiKey);
}
