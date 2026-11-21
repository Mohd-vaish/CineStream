package com.cinestream.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="watchlist_items", uniqueConstraints=@UniqueConstraint(columnNames={"user_id","movie_id"}))
public class WatchlistItem {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) @JoinColumn(name="user_id") private AppUser user;
    @ManyToOne(optional=false) @JoinColumn(name="movie_id") private Movie movie;
    private LocalDateTime addedAt = LocalDateTime.now();
    protected WatchlistItem() {}
    public WatchlistItem(AppUser user, Movie movie){this.user=user;this.movie=movie;}
    public Movie getMovie(){return movie;}
}
