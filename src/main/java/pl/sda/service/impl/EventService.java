package pl.sda.service.impl;

import org.springframework.stereotype.Service;
import pl.sda.model.Event;
import pl.sda.repository.EventRepository;

import java.util.List;

@Service
public class EventService implements pl.sda.service.EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    @Override
    public void add(Event event) {
        eventRepository.save(event);
    }

    @Override
    public Event getByCity(String city) {
        return eventRepository.findByCity(city);
    }

    @Override
    public Event getById(Integer id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        Event event = getById(id);
        eventRepository.deleteById(event.getId());
    }

    @Override
    public void update(Event event) {
        eventRepository.save(event);
    }

}
