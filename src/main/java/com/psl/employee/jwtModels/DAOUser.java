package com.psl.employee.jwtModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Entity
@Table(name = "users")
@ApiModel(description = "DAOUser model: schema used to save data in the database")
public class DAOUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Id of the username")
    private int id;
    @Column
    @ApiModelProperty(notes = "username of the employee")
    private String username;
    @Column
    @JsonIgnore
    @ApiModelProperty(notes = "Password of the employee")
    private String password;
}