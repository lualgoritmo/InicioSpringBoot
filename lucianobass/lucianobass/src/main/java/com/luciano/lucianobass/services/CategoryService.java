package com.luciano.lucianobass.services;

import com.luciano.lucianobass.entites.Category;
import com.luciano.lucianobass.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    //Genrencia as instâncias que passará pelo repository
    @Autowired
    private CategoryRepository repository;

    public List<Category> findAll() {
        return repository.findAll();
    }
}
