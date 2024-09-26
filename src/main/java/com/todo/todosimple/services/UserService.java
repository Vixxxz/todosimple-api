package com.todo.todosimple.services;

import com.todo.todosimple.models.User;
import com.todo.todosimple.repositories.TaskRepository;
import com.todo.todosimple.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService
{
    private final UserRepository userRepository;

    private TaskRepository taskRepository;

    @Autowired //Spring faz o trabalho de instanciar o userRepository. o injeta automaticamente na classe onde for necessário.
    public UserService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    //Busca o usuário pelo ID: (Usa o metodo do repository para salvar no banco)
    public User findById(Long id){  //Optional<User>: uma classe que pode ou não conter um valor.
                                    // Nesse caso, ele pode conter um User ou estar vazio se o ID não for encontrado.
        Optional<User> user = userRepository.findById(id);
        //se o ID não for encontrado, ele lança uma exceção RuntimeException com a mensagem "Usuário não encontrado":
        return user.orElseThrow(() -> new RuntimeException  (
                                                                "Usuário não encontrado.\n ID:" + id + ", Tipo: " + User.class.getName()
                                                            )); //da get no nome da classe 'User.class.getName()'
    }

    @Transactional  //Ou salva tudo, ou não salva nada no banco.
                    //Usado para persistir dados no banco. Semelhante quando da erro em uma inserção SQL, onde da rollback em tudo
    public User createUser(User user)
    {
        user.setId(null);   //Persiste um usuario com id null. Pois se tentar criar um usuario com o mesmo id, vai substituir que ja existe no banco
        user = userRepository.save(user);
        //taskRepository.saveAll(user.getTasks());  //Salva as tasks do user assim que o user é criado
        return user;
    }

    @Transactional
    public User updateUser(User user)
    {
        User newUser = findById(user.getId());  //reutiliza o metodo findById com a devida logica ja implementada
        newUser.setPassword(user.getPassword());
        return userRepository.save(newUser);
    }

    public void deleteUser(Long id){
        findById(id);
        try
        {
            userRepository.deleteById(id);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Não foi possível deletar o usuário pois há entidades relacionadas.\n ID: " + id + ", Tipo: " + User.class.getName());
        }

    }
}
