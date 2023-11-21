package com.luciano.lucianobass.services;

import com.luciano.lucianobass.dto.CategoryDTO;
import com.luciano.lucianobass.entites.Category;
import com.luciano.lucianobass.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    //Genrencia as instâncias que passará pelo repository
    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> list = repository.findAll();
        //List<CategoryDTO> listDTO = list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
        return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
//         List<CategoryDTO> listDTO = new ArrayList<>();
//         for(Category cat: list) {
//             listDTO.add(new CategoryDTO(cat));
//         }
        //return listDTO;
    }
}
