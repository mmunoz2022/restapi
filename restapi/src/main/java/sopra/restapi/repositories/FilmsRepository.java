package sopra.restapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sopra.restapi.dtos.Film;

import java.util.List;
@Repository
public interface FilmsRepository extends JpaRepository<Film, Long> {

    public List<Film> findByYear(int year);
}
