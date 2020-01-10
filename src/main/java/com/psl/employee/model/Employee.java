package com.psl.employee.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "employee")
@Getter
@Setter
@ApiModel(description = "details about the employee")
public class Employee {

    @ApiModelProperty(notes = "id of the employee")
    @Id
    private int id;
    @ApiModelProperty(notes = "first name of the employee")

    @NotBlank
    @Column(length = 20)
    private String fname;

    @ApiModelProperty(notes = "last name of the employee")
    @Column(length = 20)
    private String lname;

    @ApiModelProperty(notes = "Employee number")
    @Column(length = 5)
    private int empno;

    @ApiModelProperty(notes = "Manager id of the employee")
    @Column(length = 5)

    private int mgrid;
    @ApiModelProperty(notes = "whether employee is deleted or not. (0=not deleted, 1=deleted)")
    @Column(columnDefinition = "0", length = 1)

    private int isdeleted;

}
