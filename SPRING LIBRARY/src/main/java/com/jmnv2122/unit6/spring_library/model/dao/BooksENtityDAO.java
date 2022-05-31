package com.jmnv2122.unit6.spring_library.model.dao;

import com.jmnv2122.unit6.spring_library.model.entities.BooksjpaEntityExam;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Reservations entity dao.
 */
@Repository
public interface BooksENtityDAO extends CrudRepository<BooksjpaEntityExam, String> {
}
