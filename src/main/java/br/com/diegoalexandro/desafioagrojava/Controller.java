package br.com.diegoalexandro.desafioagrojava;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/teste")
public class Controller {

    @GetMapping
    public ResponseEntity<String> teste(){
        return ResponseEntity.ok("OK");
    }
}
