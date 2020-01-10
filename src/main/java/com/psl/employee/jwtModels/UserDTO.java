package com.psl.employee.jwtModels;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "Details of UserDTO")
public class UserDTO {
  @ApiModelProperty(notes = "username of the employee")
  private String username;
  @ApiModelProperty(notes = "password of the employee")
  private String password;
}
