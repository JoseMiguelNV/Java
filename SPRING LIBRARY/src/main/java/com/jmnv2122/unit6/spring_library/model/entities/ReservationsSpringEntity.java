package com.jmnv2122.unit6.spring_library.model.entities;

import javax.persistence.*;
import java.sql.Date;

/**
 * The type Reservations spring entity.
 */
@Entity
@Table(name = "reservations", schema = "public", catalog = "Library")
public class ReservationsSpringEntity {
    private int idreservation;
    private Date datereservation;

    /**
     * Gets idreservation.
     *
     * @return the idreservation
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getIdreservation() {
        return idreservation;
    }

    /**
     * Sets idreservation.
     *
     * @param idreservation the idreservation
     */
    public void setIdreservation(int idreservation) {
        this.idreservation = idreservation;
    }

    /**
     * Gets datereservation.
     *
     * @return the datereservation
     */
    @Basic
    @Column(name = "date", nullable = true)
    public Date getDatereservation() {
        return datereservation;
    }

    /**
     * Sets datereservation.
     *
     * @param datereservation the datereservation
     */
    public void setDatereservation(Date datereservation) {
        this.datereservation = datereservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReservationsSpringEntity that = (ReservationsSpringEntity) o;

        if (idreservation != that.idreservation) return false;
        if (datereservation != null ? !datereservation.equals(that.datereservation) : that.datereservation != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idreservation;
        result = 31 * result + (datereservation != null ? datereservation.hashCode() : 0);
        return result;
    }
}
