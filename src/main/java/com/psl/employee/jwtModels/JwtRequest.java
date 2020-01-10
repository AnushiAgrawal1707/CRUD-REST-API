package com.psl.employee.jwtModels;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@ApiModel(description = "Details of request of the JWT token")
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest implements Serializable {

  @ApiModelProperty(notes = "Username of the employee")
  private String username;
  @ApiModelProperty(notes = "Password of the employee")
  private String password;

}
