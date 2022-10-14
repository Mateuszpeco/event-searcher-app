package pl.sda.service;

import pl.sda.model.Event;

import java.util.List;

public interface EventService {

    List<Event> getAll();

    void add(Event event);

    Event getByCity(String city);

    Event getById(Integer id);

    void deleteById(Integer id);

    void update(Event event);

}
