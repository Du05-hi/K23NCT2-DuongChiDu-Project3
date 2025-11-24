package k23cnt2_duongchidu_lab08_2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "book_author")
public class BookAuthor {

    @EmbeddedId
    private BookAuthorKey id;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "bookid")
    private Book book;

    @ManyToOne
    @MapsId("authorId")
    @JoinColumn(name = "authorid")
    private Author author;

    @Column(name = "is_editor")
    private Integer isEditor;

    public BookAuthor() {}

    public BookAuthor(Book book, Author author, Integer isEditor) {
        this.book = book;
        this.author = author;
        this.isEditor = isEditor;
        this.id = new BookAuthorKey(book.getId(), author.getId());
    }

    public BookAuthorKey getId() { return id; }
    public void setId(BookAuthorKey id) { this.id = id; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }

    public Integer getIsEditor() { return isEditor; }
    public void setIsEditor(Integer isEditor) { this.isEditor = isEditor; }
}
