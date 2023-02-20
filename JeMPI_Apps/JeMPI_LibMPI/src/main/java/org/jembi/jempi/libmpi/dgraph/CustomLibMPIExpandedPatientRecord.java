package org.jembi.jempi.libmpi.dgraph;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jembi.jempi.shared.models.CustomDemographicData;
import org.jembi.jempi.shared.models.ExpandedPatientRecord;
import org.jembi.jempi.shared.models.PatientRecord;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
record CustomLibMPIExpandedPatientRecord(
      @JsonProperty("uid") String uid,
      @JsonProperty("PatientRecord.source_id") LibMPISourceId sourceId,
      @JsonProperty("PatientRecord.aux_id") String auxId,
      @JsonProperty("PatientRecord.given_name") String givenName,
      @JsonProperty("PatientRecord.family_name") String familyName,
      @JsonProperty("PatientRecord.gender") String gender,
      @JsonProperty("PatientRecord.dob") String dob,
      @JsonProperty("PatientRecord.city") String city,
      @JsonProperty("PatientRecord.phone_number") String phoneNumber,
      @JsonProperty("PatientRecord.national_id") String nationalId,
      @JsonProperty("~GoldenRecord.patients") List<CustomLibMPIDGraphGoldenRecord> dgraphGoldenRecordList) {

   PatientRecord toPatientRecord() {
      return new PatientRecord(this.uid(),
                               this.sourceId().toSourceId(),
                               new CustomDemographicData(
                                     this.auxId(),
                                     this.givenName(),
                                     this.familyName(),
                                     this.gender(),
                                     this.dob(),
                                     this.city(),
                                     this.phoneNumber(),
                                     this.nationalId()));
   }

   ExpandedPatientRecord toExpandedPatientRecord() {
      return new ExpandedPatientRecord(this.toPatientRecord(),
                                       this.dgraphGoldenRecordList()
                                           .stream()
                                           .map(CustomLibMPIDGraphGoldenRecord::toGoldenRecordWithScore)
                                           .toList());
   }

}