package simplenotes;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table (name = "note")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    private Date creationDate;
    private Date modificationDate;

    @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "id")
    private Set<HistoricalNote> historicalNoteSet;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setNote(Note note) {
    }

    public Note getNote() {
        return null;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Set<HistoricalNote> getHistoricalNoteSet() {
        return historicalNoteSet;
    }

    public void setHistoricalNoteSet(Set<HistoricalNote> historicalNoteSet) {
        this.historicalNoteSet = historicalNoteSet;
    }
}
