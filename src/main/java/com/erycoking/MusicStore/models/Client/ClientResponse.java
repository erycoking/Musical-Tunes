package com.erycoking.MusicStore.models.Client;

import com.erycoking.MusicStore.models.response.OperationResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import springfox.documentation.annotations.ApiIgnore;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiIgnore
public class ClientResponse extends OperationResponse {
    @ApiModelProperty(required = true)
    private Client client;
}
