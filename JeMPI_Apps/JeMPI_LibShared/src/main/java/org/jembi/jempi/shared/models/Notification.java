package org.jembi.jempi.shared.models;

import java.util.List;

public record Notification(
      Long timeStamp,
      // UTC Time
      NotificationType notificationType,
      String dID,
      // Document ID
      String patientNames,
      MatchData linkedTo,
      List<MatchData> candidates) {
   public enum NotificationType {
      ABOVE_THRESHOLD("Above Threshold"),
      BELOW_THRESHOLD("Below Threshold"),
      MARGIN("Margin"),
      UPDATE("Update");

      public final String label;

      NotificationType(final String label) {
         this.label = label;
      }
   }

   public enum NotificationState {
      NEW("New"),
      SEEN("Seen"),
      ACTIONED("Actioned");
      public final String label;

      NotificationState(final String label) {
         this.label = label;
      }
   }

   public record MatchData(
         String gID,
         // Golden ID
         Float score) {
   }
}
