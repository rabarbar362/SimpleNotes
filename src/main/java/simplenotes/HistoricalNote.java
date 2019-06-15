package simplenotes;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table (name = "historical_note")
public class HistoricalNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    private Date creationDate;
    private Date modificationDate;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "note_id", nullable = false)
    private Note note;

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

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}
