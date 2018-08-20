package ru.javawebinar.topjava.web.meal;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.javawebinar.topjava.web.meal.MealRestController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {

    static final String REST_URL = "/rest/meals";

    @GetMapping
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    @GetMapping(value = "/{id}")
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public void update(@PathVariable("id") int id, @RequestBody Meal meal) {
        super.update(meal, id);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> creates(@RequestBody Meal meal) {
        Meal created = super.create(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

  /*  @GetMapping (value = "/filter",params = {"startDate","startTime","endDate","endTime"},produces = APPLICATION_JSON_VALUE)
    public List <MealWithExceed> getBetween (@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)@RequestParam(value = "startDate") LocalDateTime startDate,
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)@RequestParam(value = "startTime") LocalDateTime startTime,
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)@RequestParam(value = "endDate") LocalDateTime endDate,
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)@RequestParam(value = "endTime") LocalDateTime endTime){
        return super.getBetween(startDate.toLocalDate(),startTime.toLocalTime(),endDate.toLocalDate(),endTime.toLocalTime());
    }*/

    @GetMapping(value = "/filter", params = {"startDate", "startTime", "endDate", "endTime"})
    public List<MealWithExceed> getBetween(@RequestParam(value = "startDate", required = false) LocalDate startDate,
                                           @RequestParam(value = "startTime", required = false) LocalTime startTime,
                                           @RequestParam(value = "endDate", required = false) LocalDate endDate,
                                           @RequestParam(value = "endTime", required = false) LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}