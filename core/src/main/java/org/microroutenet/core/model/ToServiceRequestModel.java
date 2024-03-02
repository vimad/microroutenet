package org.microroutenet.core.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToServiceRequestModel {
    private String method;
    private String path;
}
