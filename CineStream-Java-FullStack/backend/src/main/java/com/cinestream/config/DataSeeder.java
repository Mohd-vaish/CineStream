package com.cinestream.config;

import com.cinestream.model.Movie;
import com.cinestream.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {
    @Bean CommandLineRunner seedMovies(MovieRepository repo) {
        return args -> {
            if(repo.count()>0) return;
            String demo="https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
            repo.save(new Movie("The Last Horizon","A lone explorer discovers a signal beyond the edge of the known world.","Sci-Fi",2025,118,"U/A 13+",
                "https://images.unsplash.com/photo-1446776811953-b23d57bd21aa?w=700","https://images.unsplash.com/photo-1462331940025-496dfbfc7564?w=1600",demo,true));
            repo.save(new Movie("Midnight Protocol","A rookie analyst races to uncover a digital conspiracy before sunrise.","Thriller",2024,106,"U/A 16+",
                "https://images.unsplash.com/photo-1519608487953-e999c86e7455?w=700","https://images.unsplash.com/photo-1519608487953-e999c86e7455?w=1600",demo,false));
            repo.save(new Movie("Wild Earth","A breathtaking journey through remote landscapes and the creatures that call them home.","Documentary",2023,92,"U",
                "https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05?w=700","https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05?w=1600",demo,false));
            repo.save(new Movie("Neon City","In a city that never sleeps, two strangers find their paths unexpectedly connected.","Drama",2025,124,"U/A 13+",
                "https://images.unsplash.com/photo-1519608487953-e999c86e7455?w=700","https://images.unsplash.com/photo-1519608487953-e999c86e7455?w=1600",demo,false));
            repo.save(new Movie("Deep Blue","A marine research team encounters a mystery in the deepest part of the ocean.","Adventure",2022,111,"U/A 7+",
                "https://images.unsplash.com/photo-1518837695005-2083093ee35b?w=700","https://images.unsplash.com/photo-1518837695005-2083093ee35b?w=1600",demo,false));
            repo.save(new Movie("Codebreakers","A small team of engineers builds a tool that changes how the world communicates.","Documentary",2024,98,"U/A 13+",
                "https://images.unsplash.com/photo-1518770660439-4636190af475?w=700","https://images.unsplash.com/photo-1518770660439-4636190af475?w=1600",demo,false));
        };
    }
}
