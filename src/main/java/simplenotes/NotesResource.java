package simplenotes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;
import java.util.Set;


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
    public Iterable<Note> getAllNotes() {

        return notesRepository.findAll();
    }

    @GetMapping(path="/history")
    public ResponseEntity<Set<HistoricalNote>> getHistory(@RequestParam int id) {
        Optional<Note> optionalNote = notesRepository.findById(id);

        if (optionalNote.isPresent()) {
            return new ResponseEntity<>(optionalNote.get().getHistoricalNoteSet(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/getnote")
    public ResponseEntity<Note> getOneNote(@RequestParam int id) {

        Optional<Note> optionalNote = notesRepository.findById(id);

        if (optionalNote.isPresent()) {
            return new ResponseEntity<>(optionalNote.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Note> addNewNote(@ModelAttribute Note note) {

        if (note.getContent() != null && note.getTitle() != null) {
            note.setCreationDate(new Date());
            note.setModificationDate(new Date());
            notesRepository.save(note);

            HistoricalNote archivedNote = new HistoricalNote();

            archivedNote.setNote(note);
            archivedNote.setTitle(note.getTitle());
            archivedNote.setContent(note.getContent());
            archivedNote.setCreationDate(note.getCreationDate());
            archivedNote.setModificationDate(note.getModificationDate());
            archivedNote.setVersionNumber(0);
            historicalNotesRepository.save(archivedNote);

            return new ResponseEntity<>(note, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(path = "/modify")
    public ResponseEntity<Note> modifyNote(@ModelAttribute Note note, @RequestParam int id) {
        Optional<Note> optionalNote = notesRepository.findById(id);
        if (!optionalNote.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {

            note.setModificationDate(new Date());
            note.setCreationDate(optionalNote.get().getCreationDate());
            notesRepository.save(note);

            Set<HistoricalNote> historicalNoteSet = optionalNote.get().getHistoricalNoteSet();
            HistoricalNote archivedNote = new HistoricalNote();
            archivedNote.setNote(note);
            archivedNote.setTitle(note.getTitle());
            archivedNote.setContent(note.getContent());
            archivedNote.setCreationDate(note.getCreationDate());
            archivedNote.setModificationDate(note.getModificationDate());

            if (historicalNoteSet == null) {
                archivedNote.setVersionNumber(1);
            } else {
                archivedNote.setVersionNumber(historicalNoteSet.size());
            }

            historicalNotesRepository.save(archivedNote);

            return new ResponseEntity<>(note,HttpStatus.NO_CONTENT);
        }

    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<Note> deleteNote(@RequestParam int id) {
        Optional<Note> note = notesRepository.findById(id);

        if (note.isPresent()) {
            note.get().setContent("");
            note.get().setTitle("");
            note.get().setCreationDate(null);
            note.get().setModificationDate(null);
            notesRepository.save(note.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

