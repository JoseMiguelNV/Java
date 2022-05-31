package com.jmnv2122.unit6.spring_library.controllers;

import com.jmnv2122.unit6.spring_library.model.entities.BooksjpaEntityExam;
import com.jmnv2122.unit6.spring_library.model.dao.BooksENtityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api-rest-jmnv2122/books")
public class BooksController {

    @Autowired
    private BooksENtityDAO booksxmlDAO;

    @DeleteMapping("/{isbn}")
    public ResponseEntity<?> deleteReservation(@PathVariable(value = "isbn") String isbn) {
        Optional<BooksjpaEntityExam> booksxml = booksxmlDAO.findById(isbn);
        if(booksxml.isPresent()) {
            booksxmlDAO.deleteById(isbn);
            return ResponseEntity.ok().body("Deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
