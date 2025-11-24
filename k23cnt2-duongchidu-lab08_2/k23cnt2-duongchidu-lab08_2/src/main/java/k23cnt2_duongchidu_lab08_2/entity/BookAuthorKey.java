package k23cnt2_duongchidu_lab08_2.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookAuthorKey implements Serializable {

    @Column(name = "bookid")
    private Long bookId;

    @Column(name = "authorid")
    private Long authorId;

    public BookAuthorKey() {}

    public BookAuthorKey(Long bookId, Long authorId) {
        this.bookId = bookId;
        this.authorId = authorId;
    }

    public Long getBookId() { return bookId; }
    public Long getAuthorId() { return authorId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookAuthorKey)) return false;
        BookAuthorKey key = (BookAuthorKey) o;
        return Objects.equals(bookId, key.bookId) &&
                Objects.equals(authorId, key.authorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, authorId);
    }
}
