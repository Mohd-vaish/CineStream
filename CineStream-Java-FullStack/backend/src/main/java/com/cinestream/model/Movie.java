package com.cinestream.model;

import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String title;
    @Column(length = 2000) private String description;
    private String genre;
    private Integer releaseYear;
    private Integer durationMinutes;
    private String rating;
    private String posterUrl;
    private String backdropUrl;
    private String videoUrl;
    private boolean featured;

    protected Movie() {}
    public Movie(String title, String description, String genre, Integer releaseYear,
                 Integer durationMinutes, String rating, String posterUrl, String backdropUrl,
                 String videoUrl, boolean featured) {
        this.title=title; this.description=description; this.genre=genre; this.releaseYear=releaseYear;
        this.durationMinutes=durationMinutes; this.rating=rating; this.posterUrl=posterUrl;
        this.backdropUrl=backdropUrl; this.videoUrl=videoUrl; this.featured=featured;
    }
    public Long getId(){return id;} public String getTitle(){return title;}
    public String getDescription(){return description;} public String getGenre(){return genre;}
    public Integer getReleaseYear(){return releaseYear;} public Integer getDurationMinutes(){return durationMinutes;}
    public String getRating(){return rating;} public String getPosterUrl(){return posterUrl;}
    public String getBackdropUrl(){return backdropUrl;} public String getVideoUrl(){return videoUrl;}
    public boolean isFeatured(){return featured;}
}
