package simplenotes;

import org.springframework.data.repository.CrudRepository;

public interface HistoricalNotesRepository extends CrudRepository<HistoricalNote, Integer> {
}
