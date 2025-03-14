package main.java.com.exemplo;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Library {
    private List<Book> store= new ArrayList<Book>();

    public void Library() {
        store = new ArrayList<Book>();
    }

    public void addBook(Book book) {
        store.add(book);
    }

    public List<Book> findBooksByAuthor() {
        return store.stream().filter(book -> book.getAuthor().equals(author)).collect(Collectors.toList());
    }

    public List<Book> findBooks(LocalDateTime startDate, LocalDateTime endDate) {
        return store.stream()
                .filter(book -> !book.getPublished().isBefore(startDate) && !book.getPublished().isAfter(endDate))
                .collect(Collectors.toList());
    }
}
