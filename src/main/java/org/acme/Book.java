package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.smallrye.common.constraint.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Book extends PanacheEntityBase {

    @NotNull
    @NotBlank
    public String title;
    public String genre;

    @Column(unique = true)
    @NotNull
    @Id
    public String isbn;

    @Column(columnDefinition = "TEXT")
    public String summary;


    public static List<Book> getAll(){
        return listAll();
    }

    public static Book getOne(String isbn){
        return findById(isbn);
    }

}
