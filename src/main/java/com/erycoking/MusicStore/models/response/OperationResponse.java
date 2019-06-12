package com.erycoking.MusicStore.models.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OperationResponse {
    public enum ResposeStatusEnum {SUCCESS, ERROR, WARNING, NO_ACCESS};
    @ApiModelProperty(required = true)
    private ResposeStatusEnum operationStatus;
    private String operationMessage;

}
