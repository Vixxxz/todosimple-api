package com.todo.todosimple.repositories;

import com.todo.todosimple.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>
{
    //Busca uma task pelo id:
    //Optional<Task> findById(Long id);   //Opitional<> é utilizado pois pode ou não ser achado uma task.
                                        //Se não for achado, ira retornar como vazio. Não retornara null, pois pode dar erro no programa

    //Faz uma busca que retorna uma lista de task de um único usuário:
    List<Task> findByUser_Id(Long id);  //Para especificar por qual parametro que ira ser buscado, colocamos o "_".
                                        // Nesse caso, irá achar a lista de Task pelo Id que está dentro de um User


    //Faz uma busca que retorna uma lista de task de um único usuário usando JPQL:
    //@Query(value = "SELECT t FROM Task t WHERE t.user.id = :id")    //@Query define consultas personalizadas usando JPQL
                                                                        //JPQL é similar ao SQL, mas funciona atraves de classes
                                                                    //"SELECT t": seleciona as instâncias de Task (tarefas).
                                                                    //"FROM Task t": define a entidade de onde estamos buscando
                                                                    //"WHERE t.user.id = :id": filtra as tarefas onde o campo id do usuário associado à tarefa (t.user.id) é igual ao valor do parâmetro :id.
                                                                    //O parâmetro :id é uma referência ao parâmetro que será passado no metodo
    //List<Task> FindByUserId(@Param("id") Long id);  //@Param("id") indica que o valor passado como argumento do metodo (id) será usado como o parâmetro :id.

    //Faz uma busca que retorna uma lista de task de um único usuário usando SQL:
    //@Query(value = "SELECT * FROM task t WHERE t.tas_use_id = :id", nativeQuery = true) //nativeQuery = true: indica que essa consulta está usando SQL nativo
    //List<Task> FindByUser_id(Long id);
}
