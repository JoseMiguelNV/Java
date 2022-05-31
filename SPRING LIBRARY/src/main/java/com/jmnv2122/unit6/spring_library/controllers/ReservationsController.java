package com.jmnv2122.unit6.spring_library.controllers;

import com.jmnv2122.unit6.spring_library.model.dao.ReservationsEntityDAO;
import com.jmnv2122.unit6.spring_library.model.entities.ReservationsjpaEntityFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


/**
 * The type Reservations controller.
 */
@RestController
@RequestMapping("/api-rest-jmnv2122/reservations")
public class ReservationsController{

    @Autowired
    private ReservationsEntityDAO reservationxmlDAO;

    /**
     * Find all rerservationxmls list.
     *
     * @return the list
     */

    @GetMapping
    public List<ReservationsjpaEntityFinal> findAllRerservationxmls(){
        return (List<ReservationsjpaEntityFinal>) reservationxmlDAO.findAll();
    }

    /**
     * Find reservations by id response entity.
     *
     * @param idreservation the idreservation
     * @return the response entity
     */

    @GetMapping("/{id}")
    public ResponseEntity<ReservationsjpaEntityFinal> findReservationsById(@PathVariable(value = "id") int idreservation) {
        Optional<ReservationsjpaEntityFinal> reservation = reservationxmlDAO.findById(idreservation);

        if(reservation.isPresent()) {
            return ResponseEntity.ok().body(reservation.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Save reservation reservationsjpa entity final.
     *
     * @param reservationxml the reservationxml
     * @return the reservationsjpa entity final
     */

    @PostMapping
    public ReservationsjpaEntityFinal saveReservation(@Validated @RequestBody ReservationsjpaEntityFinal reservationxml) {
        return reservationxmlDAO.save(reservationxml);
    }

    /**
     * Delete reservation response entity.
     *
     * @param id the id
     * @return the response entity
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable(value = "id") int id) {
        Optional<ReservationsjpaEntityFinal> reservationxml = reservationxmlDAO.findById(id);
        if(reservationxml.isPresent()) {
            reservationxmlDAO.deleteById(id);
            return ResponseEntity.ok().body("Deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
