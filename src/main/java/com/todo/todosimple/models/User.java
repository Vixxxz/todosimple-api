package com.todo.todosimple.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity // define que é uma entidade
@Table(name = User.TABLE_NAME) //define o nome da tabela
public class User
{
    // define interfaces para cada operação de criação e edição de um User
    public interface CreateUser {}
    public interface UpdateUser {}

    public static final String TABLE_NAME = "USER";

    //Anotações do spring como: "@Column" servem apenas para o bd, não contam como validação do programa em si

    @Id //identifica que o atributo abaixo é o id da tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Cria uma estrategia para criação do id
    @Column(name ="use_id", nullable = false, unique = true) //define o nome da coluna, se é nulo ou não e se é único
    private Long id; //Usar a classe do tipo pois não aceita nulo, ao contrario do tipo primitivo

    @Column(name = "use_username", length = 100, nullable = false, unique = true) //tambem pode definir o tamanho maximo que a coluna aceita
    @NotNull (groups = CreateUser.class) //@NotNull, @NotEmpty são validações no próprio programa, não no bd
    @NotEmpty (groups = CreateUser.class)
    @Size(groups = CreateUser.class, min = 2, max = 100)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //Isso impede da api retornar a senha para o front
    @Column(name = "use_password", length = 60, nullable = false)
    @NotNull (groups = {CreateUser.class, UpdateUser.class})
    @NotEmpty (groups = {CreateUser.class, UpdateUser.class})
    @Size(groups = {CreateUser.class, UpdateUser.class}, min = 8, max = 60)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks = new ArrayList<Task>();

    public User() {
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(groups = CreateUser.class) @NotEmpty(groups = CreateUser.class) @Size(groups = CreateUser.class, min = 2, max = 100) String getUsername() {
        return username;
    }

    public void setUsername(@NotNull(groups = CreateUser.class) @NotEmpty(groups = CreateUser.class) @Size(groups = CreateUser.class, min = 2, max = 100) String username) {
        this.username = username;
    }

    public @NotNull(groups = {CreateUser.class, UpdateUser.class}) @NotEmpty(groups = {CreateUser.class, UpdateUser.class}) @Size(groups = {CreateUser.class, UpdateUser.class}, min = 8, max = 60) String getPassword() {
        return password;
    }

    public void setPassword(@NotNull(groups = {CreateUser.class, UpdateUser.class}) @NotEmpty(groups = {CreateUser.class, UpdateUser.class}) @Size(groups = {CreateUser.class, UpdateUser.class}, min = 8, max = 60) String password) {
        this.password = password;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User other = (User) obj;
        if (this.id == null)
        {
            if(other.id != null) {
                return false;
            }
            else if (!this.id.equals(other.id)) {
                return false;
            }
        }
        return Objects.equals(this.username, other.username) && Objects.equals(this.id, other.id) &&
                Objects.equals(this.password, other.password);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }
}
