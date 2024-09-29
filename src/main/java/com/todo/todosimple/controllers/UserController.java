package com.todo.todosimple.controllers;


import com.todo.todosimple.models.User;
import com.todo.todosimple.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController //define que essa classe é uma classe Controller Restfull
@RequestMapping("/user") //delimita a rota base. Todos os metos devem começar com /user e depois a rota de cada metodo
@Validated
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Como acessar esse metodo do controller: ex: localhost:8080/user/1
    @GetMapping("/{id}")    //espera receber o ID pela URL. esse id será utilizado no metodo abaixo.
    //Busca um User pelo ID, utilizando como base o id recebido pela url.
    //Retorna um ResponseEntity com o User caso ele seja encontrado. Caso não seja encontrado, retorna um ResponseEntity com a exceção RuntimeException.
    //ResponseEntity é um meio de retornar os dados já tratados para o front, ao inves de mandar somente o User (onde não estaria tratado)
    //@PathVariable diz que o Long id é uma variavel que vai ser extraida do meio da URL
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user); //retorna um 200 ou OK como http status e no body retorna o User
    }

    //get e delete não passa infomação no body do http request
    //somente create e update passa no body do http request
    @PostMapping //declara que ira realizar um post
    @Validated(User.CreateUser.class) //declara que sera executado as validacoes de criação definidas na classe User
    //@Valid confirma se o User que esta sendo recebido no metodo passou nas validacoes definidas na classe User
    //@RequestBody diz que o body do post é do tipo User. Então, converte automaticamento o JSON do body em uma instancia do User
    public ResponseEntity<Void> createCliente(@Valid @RequestBody User user) {
        userService.createUser(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(user.getId()).toUri();    //URI (Uniform Resource Identifier)
                                                                            //se refere a qualquer coisa que identifica um recurso na internet
                                                                        //URL (Uniform Resource Locator)
                                                                            //não só identifica o recurso, mas também informa como localizá-lo.
                                                                        //ServletUriComponentsBuilder.fromCurrentRequest():
                                                                            //Pega a URI base da requisição atual definido pelo @RequestMapping, ou seja, "/user"
                                                                        //.path("/{id}"):
                                                                            //adiciona o "id" apos o "/user", apenas identifica que ira ter um id ali
                                                                            //ex: http://localhost:8080/users/1
                                                                        //.buildAndExpand(user.getId()):
                                                                            //Substitui o "{id}" no caminho pelo valor real do ID do usuário usando o "user.getId()"
                                                                        //.toUri():
                                                                            //Converte essa construção em um objeto URI
                                                                            //usado para dizer ao cliente qual é a URI do recurso recém-criado.
        return ResponseEntity.created(uri).build();
    }

    //para atualizar existe o Put e o Pat. Put: atualiza tudo. Pat: atualiza apenas algumas coisas do User
    @PutMapping("/{id}")
    @Validated(User.UpdateUser.class)
    public ResponseEntity<Void> updateCliente(@Valid @RequestBody User user, @PathVariable Long id){
        user.setId(id);
        userService.updateUser(user);
        return ResponseEntity.noContent().build(); //retorna um 204 ou No Content como http status
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
