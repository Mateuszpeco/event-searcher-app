package pl.sda.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import pl.sda.model.Event;
import pl.sda.service.EventService;

import javax.validation.Valid;

@Slf4j
@Controller
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("admin/event/create")
    public String showEventForm(ModelMap modelMap) {
        modelMap.addAttribute("emptyEvent", new Event());
        return "event-create";
    }

    @PostMapping("admin/event/save")
    public String handleNewEvent(@ModelAttribute("emptyEvent") Event event) {
        log.info("Handle new event " + event);

        eventService.add(event);

        return "redirect:/event/list";
    }

    @GetMapping("/event/list")
    public String showEvents(ModelMap modelMap) {
        modelMap.addAttribute("events", eventService.getAll());
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.addAttribute("currentUser", currentUser);
        return "events-list";
    }

//    @GetMapping("/event/{city}")
//    public String listByCity(String city, ModelMap modelMap) {
//        modelMap.addAttribute("event", eventService.getByCity(city));
//        return "event-by-city";
//    }

    @GetMapping("/event/{id}")
    public String eventDetails(@PathVariable Integer id, ModelMap modelMap) {
        modelMap.addAttribute("event", eventService.getById(id));
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.addAttribute("currentUser", currentUser);
        return "event-details";
    }

    @GetMapping("/admin/event/delete/{id}")
    public String deleteEvent(@PathVariable Integer id) {
        eventService.deleteById(id);
        return "redirect:/event/list";
    }

    @GetMapping("/admin/event/edit/{id}")
    public String showEditEventForm(@PathVariable Integer id, ModelMap modelMap) {
        modelMap.addAttribute("event", eventService.getById(id));
        return "event-edit";
    }

    @PostMapping("/admin/event/update")
    public String handleEditedEvent(@Valid @ModelAttribute("event") Event event, Errors errors) {
        log.info("Handle event to edit: " + event);
        if (errors.hasErrors()) {
            log.error("Errors form frontend: " + errors.getFieldErrors());
            return "event-edit";
        }
        eventService.update(event);
        return "redirect:/event/list";
    }
}

