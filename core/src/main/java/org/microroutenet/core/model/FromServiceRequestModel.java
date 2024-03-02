package org.microroutenet.core.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FromServiceRequestModel {
    private String name;
    private boolean synchronous;
    private String requestPayload;
}
