package org.jembi.jempi.shared.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LinkEntityToGidSyncBody(@JsonProperty("stan") String stan,
                                      @JsonProperty("patient") CustomPatient patient,
                                      @JsonProperty("gid") String gid) {}