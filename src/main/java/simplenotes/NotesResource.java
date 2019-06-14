package simplenotes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;


@RestController
public class NotesResource {

    private final NotesRepository notesRepository;

    @Autowired
    public NotesResource(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @GetMapping(path="/all")
    public @ResponseBody
    Iterable <Note> getAllNotes() {
        return notesRepository.findAll();
    }

    @GetMapping(path="/getnote")
    public @ResponseBody
    Optional<Note> getOneNote(@RequestParam int id) {
        Optional<Note> note = notesRepository.findById(id);
        if (!note.isPresent()) {
            return null;
        } else {
            return note;
        }
    }

    @PostMapping(path = "/add")
    public @ResponseBody String addNewNote (@ModelAttribute Note note) {
        //TODO: add validation
        note.setCreationDate(new Date());
        notesRepository.save(note);
        return "Note " + note.getTitle() + "\n" + note.getContent() + "\nsaved! " + note.getCreationDate();
    }

    @PutMapping(path="/modify")
    public @ResponseBody String modifyNote (@RequestBody Note note) {
        return null;
    }

    @DeleteMapping(path="/delete")
    public @ResponseBody String deleteNote (@RequestParam int id) {
        Optional<Note> note = notesRepository.findById(id);

        if (note.isPresent()) {
            note.get().setDeleted(true);
            return "Note with id " + id + " deleted.";
        } else {
            return "There is no note with id " + id;
        }
    }
}

