package simplenotes;

import org.springframework.data.repository.CrudRepository;

public interface NotesRepository extends CrudRepository<Note, Integer> {

}
