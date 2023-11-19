package com.luciano.projetoheloword.criarhelloword.resource;

import com.luciano.projetoheloword.criarhelloword.entites.HelloWord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
@RequestMapping(value = "/message")
public class HelloWordResource implements Serializable {

    @GetMapping
    public ResponseEntity<HelloWord> getMessage() {
        HelloWord hello = new HelloWord("Hello Word! Executando Testando");
        return ResponseEntity.ok(hello);
        //return ResponseEntity.ok().body(hello);
    }
}
