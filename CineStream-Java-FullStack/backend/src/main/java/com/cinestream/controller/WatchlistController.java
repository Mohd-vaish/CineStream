package com.cinestream.controller;

import com.cinestream.model.*;
import com.cinestream.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {
    private final WatchlistRepository watchlist; private final UserRepository users; private final MovieRepository movies;
    public WatchlistController(WatchlistRepository watchlist, UserRepository users, MovieRepository movies){
        this.watchlist=watchlist;this.users=users;this.movies=movies;
    }
    @GetMapping public List<Movie> list(Authentication auth){
        return watchlist.findByUserEmailOrderByAddedAtDesc(auth.getName()).stream().map(WatchlistItem::getMovie).toList();
    }
    @PostMapping("/{movieId}") @ResponseStatus(HttpStatus.CREATED)
    public void add(@PathVariable Long movieId, Authentication auth){
        if(watchlist.findByUserEmailAndMovieId(auth.getName(),movieId).isPresent()) return;
        AppUser user=users.findByEmail(auth.getName()).orElseThrow();
        Movie movie=movies.findById(movieId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Title not found"));
        watchlist.save(new WatchlistItem(user,movie));
    }
    @DeleteMapping("/{movieId}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long movieId, Authentication auth){
        WatchlistItem item=watchlist.findByUserEmailAndMovieId(auth.getName(),movieId)
            .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Watchlist item not found"));
        watchlist.delete(item);
    }
}
