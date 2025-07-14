/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package testjwt.demo.task;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import testjwt.demo.user.User;

/**
 *
 * @author macbookair
 */
// from lombok, generated getters and setters to fields
@Data

// from lombok, adds .builder() -> ... -> .build() method to create an object with fields
@Builder

// from lombok, adds constructor with no args
@NoArgsConstructor

// from lombok, adds counstructor with all of the args
@AllArgsConstructor

// tells to database that this is a db table
@Entity

// table name
@Table(name = "_task")
public class Task {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private User user;

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 2, max = 500, message = "Description must be between 2 and 500 characters")
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;
}
