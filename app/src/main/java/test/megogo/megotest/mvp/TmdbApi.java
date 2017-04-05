package test.megogo.megotest.mvp;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import test.megogo.megotest.mvp.models.Movie;
import test.megogo.megotest.mvp.models.TopMovies;

/**
 * Created by JSJEM on 04.04.2017.
 */
public interface TmdbApi {

    @GET("/3/movie/top_rated")
    Observable<TopMovies> getTopMovies(@Query("api_key") final String apiKey, @Query("page") final int page);

    @GET("/3/movie/{movie_id}")
    Observable<Movie> getMovieDetails(@Path("movie_id") final long movieId, @Query("api_key") final String apiKey);

}
