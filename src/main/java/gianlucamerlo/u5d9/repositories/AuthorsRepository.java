package gianlucamerlo.u5d9.repositories;

import gianlucamerlo.u5d9.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorsRepository extends JpaRepository<Author, Integer> {
    Optional<Author> findByEmail(String email);
}
