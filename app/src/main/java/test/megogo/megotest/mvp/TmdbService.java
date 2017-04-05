package test.megogo.megotest.mvp;

import android.content.Context;

import rx.Observable;
import test.megogo.megotest.R;
import test.megogo.megotest.mvp.models.Movie;
import test.megogo.megotest.mvp.models.TopMovies;

/**
 * Created by JSJEM on 04.04.2017.
 */
public class TmdbService {

    private final TmdbApi api;
    private final String apiKey;

    public static String getPreviewUrl(final String filePath) {
        return "https://image.tmdb.org/t/p/w342" + filePath;
    }

    public static String getImageUrl(final String filePath) {
        return "https://image.tmdb.org/t/p/w780" + filePath;
    }

    public TmdbService(final Context context, final TmdbApi api) {
        this.api = api;
        this.apiKey = context.getString(R.string.tmdb_api_key);
    }

    public Observable<TopMovies> getTopMovies(final int page) {
        return api.getTopMovies(apiKey, page);
    }

    public Observable<Movie> getMovieDetails(final long movieId) {
        return api.getMovieDetails(movieId, apiKey);
    }


}
