package org.microroutenet.core.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseModel {
    private int status;
    private String response;
}
