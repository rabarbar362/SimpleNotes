package simplenotes;

import org.springframework.beans.factory.annotation.Autowired;
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
    public @ResponseBody
    Iterable<Note> getAllNotes() {
        return notesRepository.findAll();
    }

    @GetMapping(path="/history")
    public @ResponseBody
    Set<HistoricalNote> getHistory(@RequestParam int id) {
        Optional<Note> note = notesRepository.findById(id);
        if (note.isPresent()) {
            return note.get().getHistoricalNoteSet();
        } else {
            return null;
        }
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
        notesRepository.save(note);

        HistoricalNote archivedNote = new HistoricalNote();

        archivedNote.setNote(note);
        archivedNote.setTitle(note.getTitle());
        archivedNote.setContent(note.getContent());
        archivedNote.setCreationDate(note.getCreationDate());
        archivedNote.setVersionNumber(0);
        historicalNotesRepository.save(archivedNote);

        return "Note " + note.getTitle() + "\n" + note.getContent() + "\nsaved! " + note.getCreationDate();
    }

    @PutMapping(path = "/modify")
    public @ResponseBody
    String modifyNote(@ModelAttribute Note note, @RequestParam int id) {
        Optional<Note> optionalNote = notesRepository.findById(id);
        if (!optionalNote.isPresent()) {
            return "there is no such note";
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

            return "note changed";
        }

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

    @DeleteMapping(path = "/deleteall")
    public @ResponseBody
    String deleteAll() {
       notesRepository.deleteAll();
       return "deleted";
    }
}

