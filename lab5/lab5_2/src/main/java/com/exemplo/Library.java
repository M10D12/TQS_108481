package com.exemplo;

import com.exemplo.Book;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Library {
    private List<Book> store;

    public Library() {
        store = new ArrayList<>();
    }
    
    public void addBook(Book book) {
        store.add(book);
    }

    public List<Book> findBooksByAuthor(String author) {
        return store.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public List<Book> findBooks(LocalDateTime startDate, LocalDateTime endDate) {
        return store.stream()
                .filter(book -> !book.getPublished().isBefore(startDate) && !book.getPublished().isAfter(endDate))
                .sorted(Comparator.comparing(Book::getPublished).reversed()) 
                .collect(Collectors.toList());
    }
    
    
    
}


