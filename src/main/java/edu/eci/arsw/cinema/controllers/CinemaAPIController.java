/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.controllers;

import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.services.CinemaServices;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author cristian
 */
@RestController
@Service
@RequestMapping(value = "/cinemas")
public class CinemaAPIController {

    @Autowired
    CinemaServices cs;
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllCinemas() {
        try {
            return new ResponseEntity<>(cs.getAllCinemas(), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error, Cinemas not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{cinema}")
    public ResponseEntity<?> CinemaName(@PathVariable String cinema) {
        try {
            return new ResponseEntity<>(cs.getCinemaByName(cinema), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error, Cinema not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{cinema}/{date}")
    public ResponseEntity<?> CinemaFunctionDate(@PathVariable String cinema, @PathVariable String date) {
        try {
            return new ResponseEntity<>(cs.getFunctionsbyCinemaAndDate(cinema, date), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error,In the cinema In The date not exit Functions", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{cinema}/{date}/{name}")
    public ResponseEntity<?> CinemaFunctionDateName(@PathVariable String cinema, @PathVariable String date, @PathVariable String name) {
        try {
            List<CinemaFunction> n = cs.getFunctionsbyCinemaAndDate(cinema, date);
            CinemaFunction cinemaEs = null;
            for (CinemaFunction f : n) {
                if (f.getMovie().getName().equals(name)) {
                    cinemaEs = f;
                }
            }
            if (cinemaEs == null) {
                return new ResponseEntity<>("Error,The function dont exist", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(cinemaEs, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error,The function dont exist", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{name}")
    public ResponseEntity<?> Agregarfuncion(@RequestBody CinemaFunction f,@PathVariable String name) {
        try {
            cs.getCinemaByName(name).addFunction(f);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error al agregar una funcion", HttpStatus.FORBIDDEN);
        }
    }
     @PutMapping("/{name}")
    public ResponseEntity<?> actualizar(@RequestBody CinemaFunction f,@PathVariable String name) {
        try {
            cs.actualizar(name, f);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error al actualizar", HttpStatus.FORBIDDEN);
        }
    }
}
