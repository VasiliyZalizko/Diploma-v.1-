package ru.netology.cloudstorage.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserData {
    @Column
    @Size(min = 5, max = 20)
    private String name;

    @Column
    @Size(min = 5, max = 20)
    private String surname;

    @Column(unique = true)
    @Size(min = 2, max = 20)
    private String userName;

    @Override
    public String toString() {
        return ", name = " + name + ", surname = " + surname + ", userName = " + userName;
    }
}