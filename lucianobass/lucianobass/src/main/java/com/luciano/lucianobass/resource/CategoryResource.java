package com.luciano.lucianobass.resource;

import com.luciano.lucianobass.entites.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categoryes")
public class CategoryResource {
    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        List<Category> list = new ArrayList<>();
        list.add(new Category(1L, "Engenharia de Dados"));
        list.add(new Category(2L, "Lógica em Java"));
        list.add(new Category(3L, "Kotlin"));
        list.add(new Category(4L, "Spring Boot"));
        list.add(new Category(5L, "PHP"));
        list.add(new Category(6L, "Node JS"));
        list.add(new Category(7L, "Angular"));
        return ResponseEntity.ok().body(list);
    }

}
