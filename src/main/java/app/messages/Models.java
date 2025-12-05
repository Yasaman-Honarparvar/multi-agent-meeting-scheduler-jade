package app.messages;

import java.util.List;

public class Models {

    // ---------- 1) Basic Messages ----------

    public static class MeetingRequest {
        public String type = "MeetingRequest";
        public String title;
        public List<String> attendees;
        public int duration_min;
        public String window_from;
        public String window_to;
    }

    public static class Proposal {
        public String type = "Proposal";
        public String slot_start;
        public String slot_end;
    }

    public static class Decision {
        public String type = "Decision";
        public String action;      // "ACCEPT" or "REJECT"
        public String slot_start;
        public String slot_end;
    }

    public static class CommitRequest {
        public String type = "CommitRequest";
        public String title;
        public String slot_start;
        public String slot_end;
    }

    public static class CommitAck {
        public String type = "CommitAck";
        public boolean ok;
        public String meetingId;
        public String title;
        public String slot_start;
        public String slot_end;
    }

    // ---------- 2) Reschedule / Cancel ----------

    public static class RescheduleRequest {
        public String type = "RescheduleRequest";
        public String last_slot_start;
        public String last_slot_end;
        public String new_window_from;
        public String new_window_to;
    }

    public static class CancelRequest {
        public String type = "CancelRequest";
        public String meetingId;
    }

    public static class CancelCommit {
        public String type = "CancelCommit";
        public String meetingId;
    }

    public static class CancelAck {
        public String type = "CancelAck";
        public boolean ok;
        public String meetingId;
        public String message;
    }
}
