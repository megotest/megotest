package test.megogo.megotest.mvp.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by JSJEM on 04.04.2017.
 */

public class Movie {

    @SerializedName("adult")
    private boolean adult;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("budget")
    private long budget;
    @SerializedName("genres")
    private List<Genre> genres;
    @SerializedName("homepage")
    private String homepage;
    @SerializedName("id")
    private long id;
    @SerializedName("imdb_id")
    private String imdbId;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("overview")
    private String overview;
    @SerializedName("popularity")
    private float popularity;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("production_companies")
    private List<Company> productionCompanies;
    @SerializedName("production_countries")
    private List<Country> productionCountries;
    @SerializedName("release_date")
    private Date releaseDate;
    @SerializedName("revenue")
    private long revenue;
    @SerializedName("runtime")
    private int runtime;
    @SerializedName("spoken_languages")
    private List<Language> spokenLanguages;
    @SerializedName("status")
    private String status;
    @SerializedName("tagline")
    private String tagline;
    @SerializedName("title")
    private String title;
    @SerializedName("video")
    private String video;
    @SerializedName("vote_average")
    private float voteAverage;
    @SerializedName("vote_count")
    private long voteCount;

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<Company> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<Company> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public List<Country> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<Country> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public List<Language> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<Language> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }
}
