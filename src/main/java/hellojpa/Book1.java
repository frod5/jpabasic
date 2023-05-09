package hellojpa;

import javax.persistence.Entity;

@Entity
public class Book1 extends Item1 {
    private String author;
    private String isbn;
}
