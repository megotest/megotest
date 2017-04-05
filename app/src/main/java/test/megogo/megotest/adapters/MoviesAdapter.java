package test.megogo.megotest.adapters;

import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.megogo.megotest.R;
import test.megogo.megotest.mvp.TmdbService;
import test.megogo.megotest.mvp.models.Movie;
import test.megogo.megotest.views.MaterialProgressView;

/**
 * Created by JSJEM on 04.04.2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_MOVIE = 0;
    private static final int TYPE_LOADER = 1;

    private static final int MIN_LEFT_TO_LOAD = 7;

    private final List<Movie> items;
    private final Handler handler;
    private boolean loading;
    private MovieScrollListener scrollListener;
    private MovieClickListener movieClickListener;

    public MoviesAdapter() {
        items = new ArrayList<>();
        handler = new Handler();
    }

    public void setScrollListener(final MovieScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    public void setMovieClickListener(final MovieClickListener movieClickListener) {
        this.movieClickListener = movieClickListener;
    }

    public void setLoading(final boolean loading) {
        this.loading = loading;
        handler.post(() -> notifyDataSetChanged());
    }

    public void setMovies(final List<Movie> movies) {
        items.clear();
        items.addAll(movies);
        handler.post(() -> notifyDataSetChanged());
    }

    public void addMovies(final List<Movie> movies) {
        int positionStart = items.size();
        int positionEnd = positionStart + movies.size() - 1;
        items.addAll(movies);
        handler.post(() -> notifyItemRangeInserted(positionStart, positionEnd));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_LOADER:
                return new LoadingViewHolder(inflater.inflate(R.layout.item_loading, parent, false));
            default:
                return new MovieViewHolder(inflater.inflate(R.layout.item_movie, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_MOVIE) {
            bindMovie((MovieViewHolder) holder, position);
            if (position > getItemCount() - MIN_LEFT_TO_LOAD) {
                notifyScrollEnd();
            }
        }
    }

    private void bindMovie(final MovieViewHolder holder, final int position) {
        Movie movieItem = items.get(position);
        holder.movieTitle.setText(movieItem.getTitle());
        holder.movieRating.setText(String.format("Rating: %.2f", movieItem.getVoteAverage()));
        holder.itemView.setOnClickListener(view -> notifyItemClick(position));
        int expectedSize = (int) holder.itemView.getContext().getResources()
                .getDimension(R.dimen.movie_poster_small_size);
        Picasso.with(holder.itemView.getContext())
                .load(TmdbService.getPreviewUrl(movieItem.getPosterPath()))
                .placeholder(R.drawable.image_movie_stub)
                .error(R.drawable.image_movie_stub)
                .resize(expectedSize, expectedSize)
                .centerInside().into(holder.movieImage);
    }

    private void notifyItemClick(final int position) {
        if (movieClickListener != null) {
            movieClickListener.onMovieClick(position, items.get(position));
        }
    }

    private void notifyScrollEnd() {
        if (scrollListener != null) {
            scrollListener.onEndReached();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return loading && position == getItemCount() - 1 ? TYPE_LOADER : TYPE_MOVIE;
    }

    @Override
    public int getItemCount() {
        int count = items.size();
        if (loading) {
            count++;
        }
        return count;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_image)
        ImageView movieImage;
        @BindView(R.id.movie_title)
        TextView movieTitle;
        @BindView(R.id.movie_rating)
        TextView movieRating;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_loading)
        MaterialProgressView progressView;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface MovieScrollListener {

        void onEndReached();
    }

    public interface MovieClickListener {

        void onMovieClick(final int position, final Movie movie);
    }
}
