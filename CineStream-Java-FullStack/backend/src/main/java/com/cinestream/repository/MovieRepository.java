package com.cinestream.repository;
import com.cinestream.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("select m from Movie m where (:q is null or lower(m.title) like lower(concat('%',:q,'%')) or lower(m.description) like lower(concat('%',:q,'%'))) and (:genre is null or lower(m.genre)=lower(:genre))")
    List<Movie> search(@Param("q") String q, @Param("genre") String genre);
    List<Movie> findByFeaturedTrue();
}
