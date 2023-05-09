package hellojpa;

import javax.persistence.Entity;

@Entity
public class Book extends Item1 {
    private String author;
    private String isbn;
}
