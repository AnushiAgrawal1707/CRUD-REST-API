package com.psl.employee.jwtModels;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@ApiModel(description = "Details of response of the JWT token")
public class JwtResponse implements Serializable {

  @ApiModelProperty(notes = "generated JWT token")
  private final String jwtToken;
}
