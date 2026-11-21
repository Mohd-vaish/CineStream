package com.cinestream.controller;

import com.cinestream.model.Movie;
import com.cinestream.repository.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieRepository movies;
    public MovieController(MovieRepository movies){this.movies=movies;}
    @GetMapping public List<Movie> all(@RequestParam(required=false) String q,
                                       @RequestParam(required=false) String genre) {
        String query=(q==null || q.isBlank())?null:q.trim();
        String g=(genre==null || genre.isBlank() || genre.equalsIgnoreCase("all"))?null:genre.trim();
        return movies.search(query,g);
    }
    @GetMapping("/featured") public List<Movie> featured(){return movies.findByFeaturedTrue();}
    @GetMapping("/{id}") public Movie one(@PathVariable Long id){
        return movies.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Title not found"));
    }
}
