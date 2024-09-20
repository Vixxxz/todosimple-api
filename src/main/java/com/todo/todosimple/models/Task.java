package com.todo.todosimple.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "TASK")
public class Task
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tas_id", nullable = false, unique = true)
    private Long id;

    @ManyToOne //Diz que Many (task) são de One (usuario)
    @JoinColumn(name = "tas_use_id", nullable = false, updatable = false)
    private User user;

    @Column(name = "tas_description", length = 255, nullable = false)
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 255)
    private String description;

    public Task(Long id, User user, String description) {
        this.id = id;
        this.user = user;
        this.description = description;
    }

    public Task() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public @NotNull @NotEmpty @Size(min = 1, max = 255) String getDescription() {
        return description;
    }

    public void setDescription(@NotNull @NotEmpty @Size(min = 1, max = 255) String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Task)) {
            return false;
        }
        Task other = (Task) obj;
        if (this.id == null)
        {
            if(other.id != null) {
                return false;
            }
            else if (!this.id.equals(other.id)) {
                return false;
            }
        }
        return Objects.equals(this.user, other.user) && Objects.equals(this.id, other.id) &&
                Objects.equals(this.description, other.description);


    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }
}
