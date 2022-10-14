package pl.sda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.model.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {

    Event findByCity(String city);
}
