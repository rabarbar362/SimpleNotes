package simplenotes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;


@RestController
public class NotesResource {

    private final NotesRepository notesRepository;
    private final HistoricalNotesRepository historicalNotesRepository;

    @Autowired
    public NotesResource(NotesRepository notesRepository, HistoricalNotesRepository historicalNotesRepository) {
        this.notesRepository = notesRepository;
        this.historicalNotesRepository = historicalNotesRepository;
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Note> getAllNotes() {
        return notesRepository.findAll();
    }

    @GetMapping(path = "/getnote")
    public @ResponseBody
    Optional<Note> getOneNote(@RequestParam int id) {
        Optional<Note> note = notesRepository.findById(id);
        if (note.isPresent()) {
            return note;
        } else {
            return null;
        }
    }

    @PostMapping(path = "/add")
    public @ResponseBody
    String addNewNote(@ModelAttribute Note note) {
        //TODO: add validation
        note.setCreationDate(new Date());
        HistoricalNote archivedNote = new HistoricalNote();

        archivedNote.setNote(note);
        archivedNote.setTitle(note.getTitle());
        archivedNote.setContent(note.getContent());
        archivedNote.setCreationDate(note.getCreationDate());
        notesRepository.save(note);
        historicalNotesRepository.save(archivedNote);

        return "Note " + note.getTitle() + "\n" + note.getContent() + "\nsaved! " + note.getCreationDate();
    }

    //TODO: versioning
    @PutMapping(path = "/modify")
    public @ResponseBody
    String modifyNote(@RequestBody Note note) {

        return null;
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody
    String deleteNote(@RequestParam int id) {
        Optional<Note> note = notesRepository.findById(id);

        if (note.isPresent()) {
            notesRepository.deleteById(id);
            return "Note with id " + id + " deleted.";
        } else {
            return "There is no note with id " + id;
        }
    }
}

