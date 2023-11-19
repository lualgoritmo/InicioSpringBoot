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
        return ResponseEntity.ok().body(list);
    }

}
