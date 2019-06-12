package com.erycoking.MusicStore.models.Client;

import com.erycoking.MusicStore.models.response.OperationResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ClientResponse extends OperationResponse {
    @ApiModelProperty(required = true)
    private Client client;
}
