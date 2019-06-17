package simplenotes;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HistoricalNotesRepository extends CrudRepository<HistoricalNote, Integer> {
}
