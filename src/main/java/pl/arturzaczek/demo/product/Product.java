package pl.arturzaczek.demo.product;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class Product {

    Integer externalId;
    String author;
    String title;
    String genre;
    Double price;
    LocalDate publishDate;
    String description;

    @Override
    public String toString() {
        return "author=" + author +
                ", title=" + title + ", date: " + publishDate;
    }

}
