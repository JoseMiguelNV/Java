package com.jmnv2122.unit6.spring_library.model.entities;

import javax.persistence.*;
import java.sql.Date;

/**
 * The type Reservationsjpa entity final.
 */
@Entity
@Table(name = "reservations", schema = "public", catalog = "Library")
public class ReservationsjpaEntityFinal {
    private int id;
    private String book;
    private String borrower;
    private Date date;

    /**
     * Gets id.
     *
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets book.
     *
     * @return the book
     */
    @Basic
    @Column(name = "book", nullable = true, length = 13)
    public String getBook() {
        return book;
    }

    /**
     * Sets book.
     *
     * @param book the book
     */
    public void setBook(String book) {
        this.book = book;
    }

    /**
     * Gets borrower.
     *
     * @return the borrower
     */
    @Basic
    @Column(name = "borrower", nullable = true, length = 8)
    public String getBorrower() {
        return borrower;
    }

    /**
     * Sets borrower.
     *
     * @param borrower the borrower
     */
    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    @Basic
    @Column(name = "date", nullable = true)
    public Date getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReservationsjpaEntityFinal that = (ReservationsjpaEntityFinal) o;

        if (id != that.id) return false;
        if (book != null ? !book.equals(that.book) : that.book != null) return false;
        if (borrower != null ? !borrower.equals(that.borrower) : that.borrower != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (borrower != null ? borrower.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
