package com.erycoking.MusicStore.models.Client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Class representing user credentials")
public class AuthRequest {

    @ApiModelProperty
    private String username;
    @ApiModelProperty
    private String password;
}
