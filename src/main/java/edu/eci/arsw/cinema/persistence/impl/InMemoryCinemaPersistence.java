/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.persistence.impl;

import edu.eci.arsw.cinema.flitro.filter;
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author cristian
 */
@Service 
public class InMemoryCinemaPersistence implements CinemaPersitence {
    
    private final Map<String, Cinema> cinemas = new HashMap<>();
    public InMemoryCinemaPersistence() {
        //load stub data
        String functionDate = "2018-12-18 15:30";
        String functionDate1 = "2019-02-15 12:30";
        List<CinemaFunction> functions = new ArrayList<>();
        List<CinemaFunction> functions2 = new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie", "Action"), functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night", "Horror"), functionDate1);
        CinemaFunction funct3 = new CinemaFunction(new Movie("The dog Bolt", "Action"), functionDate);
        CinemaFunction funct4 = new CinemaFunction(new Movie("The Night 2", "Horror"), functionDate1);
        functions.add(funct1);
        functions.add(funct2);
        functions2.add(funct3);
        functions2.add(funct4);
        Cinema c2 = new Cinema("cinemaX2", functions2);
        Cinema c = new Cinema("cinemaX", functions);
        cinemas.put("cinemaX", c);
        cinemas.put("cinemaX2",c2);
    }

    @Override
    public void buyTicket(int row, int col, String cinema, String date, String movieName) throws CinemaException {
        try {
            for (CinemaFunction cf : getCinema(cinema).getFunctions()) {
                if (cf.getMovie().getName().equals(movieName) || cf.getDate().equals(date)) {
                    cf.buyTicket(row, col);
                }
            }
        } catch (CinemaPersistenceException ex) {
            ex.getMessage();
        }

    }

    @Override
    public List<CinemaFunction> getFunctionsbyCinemaAndDate(String cinema,String date) {
        List<CinemaFunction> functions = new ArrayList<CinemaFunction>();
        try {
            for (CinemaFunction cf : getCinema(cinema).getFunctions()) {
                if (cf.getDate().startsWith(date)) {
                    functions.add(cf);
                }
            }
        } catch (CinemaPersistenceException ex) {
            Logger.getLogger(InMemoryCinemaPersistence.class.getName()).log(Level.SEVERE, null, ex);
        }
        return functions;
    }

    @Override
    public void saveCinema(Cinema c) throws CinemaPersistenceException {
        if (cinemas.containsKey(c.getName())) {
            throw new CinemaPersistenceException("The given cinema already exists: " + c.getName());
        } else {
            cinemas.put(c.getName(), c);
        }
    }

    @Override
    public Cinema getCinema(String name) throws CinemaPersistenceException {
        if (cinemas.containsKey(name)) {
            return cinemas.get(name);
        } else {
            throw new CinemaPersistenceException("El cine no existe " + name);
        }
    }

    @Override
    public Map< String,Cinema > getAllCinemas() throws CinemaPersistenceException {
        return cinemas;
    }
    @Override
    public void actualizar(String name, CinemaFunction cf) throws CinemaPersistenceException{
        Cinema movie = getCinema(name);
        movie.actualizar(cf);
    }

}
