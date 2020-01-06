package com.psl.employee.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "employee")
@Getter
@Setter
public class Employee {
    @Id
    private int id;

    @NotBlank
    @Column(length = 20)
    private String fname;

    @Column(length = 20)
    private String lname;

    @Column(length = 5)
    private int empno;

    @Column(length = 5)
    private int mgrid;

    @Column(columnDefinition = "0", length = 1)
    private int isdeleted;
}
