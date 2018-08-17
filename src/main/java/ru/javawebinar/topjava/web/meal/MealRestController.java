package ru.javawebinar.topjava.web.meal;


import org.springframework.format.annotation.DateTimeFormat;
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

@RestController(value = REST_URL)
public class MealRestController extends AbstractMealController {

    static final String REST_URL = "/rest/meals";

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Meal get (@PathVariable("id")int id){
        return super.get(id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete (@PathVariable("id")int id){
        super.delete(id);
    }

    @PutMapping (value ="/{id}", consumes = APPLICATION_JSON_VALUE)
    public void update (@PathVariable("id")int id, @RequestBody Meal meal){
        super.update(meal, id);
    }

    @PostMapping (produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity <Meal> creates (@RequestBody Meal meal){
       Meal created = super.create(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
       return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping (value = "/filter?startDate&startTime&endDate&endTime", produces = APPLICATION_JSON_VALUE)
    public List <MealWithExceed> getBetween (@RequestParam(value = "startDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                             @RequestParam(value = "startTime")@DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                                             @RequestParam(value = "endDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                             @RequestParam(value = "endTime")@DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime){
        return super.getBetween(startDate,startTime,endDate,endTime);
    }
}