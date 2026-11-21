package com.cinestream.repository;
import com.cinestream.model.WatchlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface WatchlistRepository extends JpaRepository<WatchlistItem, Long> {
    List<WatchlistItem> findByUserEmailOrderByAddedAtDesc(String email);
    Optional<WatchlistItem> findByUserEmailAndMovieId(String email, Long movieId);
}
