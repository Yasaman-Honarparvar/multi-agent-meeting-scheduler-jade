package app.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import app.util.LogWriter;

public class NegotiatorAgent extends Agent {

    private String uc;

    @Override
    protected void setup() {

        uc = System.getProperty("demo.uc", "UC1").toUpperCase();
        LogWriter.log("SYSTEM", "[Negotiator] Init for scenario " + uc);

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {

                ACLMessage msg = receive();
                if (msg == null) {
                    block();
                    return;
                }

                switch (uc) {
                    case "UC1":
                        handleUC1(msg);
                        break;
                    case "UC2":
                        handleUC2(msg);
                        break;
                    case "UC3":
                        handleUC3(msg);
                        break;
                    default:
                        // ignore
                }
            }
        });
    }

    /* --------------------------- UC1 logic ---------------------------------- */

    private void handleUC1(ACLMessage msg) {

        String cid = msg.getConversationId();

        if (msg.getPerformative() == ACLMessage.REQUEST &&
                "UC1_MEETING_REQUEST".equals(cid)) {

            LogWriter.log("UC1",
                    "[User→Negotiator] cid=UC1_MEETING_REQUEST content=" + msg.getContent());

            ACLMessage fbq = new ACLMessage(ACLMessage.REQUEST);
            fbq.addReceiver(new AID("Calendar", AID.ISLOCALNAME));
            fbq.setConversationId("UC1_FREEBUSY_QUERY");
            fbq.setContent(msg.getContent());

            send(fbq);
            LogWriter.log("UC1",
                    "[Negotiator→Calendar] cid=UC1_FREEBUSY_QUERY content=" + fbq.getContent());
        }

        else if ("UC1_FREEBUSY_REPLY".equals(cid)) {

            LogWriter.log("UC1",
                    "[Calendar→Negotiator] cid=UC1_FREEBUSY_REPLY content=" + msg.getContent());

            ACLMessage proposal = new ACLMessage(ACLMessage.INFORM);
            proposal.addReceiver(new AID("User", AID.ISLOCALNAME));
            proposal.setConversationId("UC1_PROPOSAL");
            proposal.setContent(msg.getContent());

            send(proposal);
            LogWriter.log("UC1",
                    "[Negotiator→User] cid=UC1_PROPOSAL content=" + proposal.getContent());
        }

        else if ("UC1_DECISION".equals(cid)) {

            LogWriter.log("UC1",
                    "[User→Negotiator] cid=UC1_DECISION content=" + msg.getContent());

            ACLMessage commit = new ACLMessage(ACLMessage.REQUEST);
            commit.addReceiver(new AID("Calendar", AID.ISLOCALNAME));
            commit.setConversationId("UC1_COMMIT");
            commit.setContent(msg.getContent());

            send(commit);
            LogWriter.log("UC1",
                    "[Negotiator→Calendar] cid=UC1_COMMIT content=" + commit.getContent());
        }

        else if ("UC1_COMMIT_ACK".equals(cid)) {

            LogWriter.log("UC1",
                    "[Calendar→Negotiator] cid=UC1_COMMIT_ACK content=" + msg.getContent());

            ACLMessage notify = new ACLMessage(ACLMessage.INFORM);
            notify.addReceiver(new AID("Notifier", AID.ISLOCALNAME));
            notify.setConversationId("UC1_NOTIFY");
            notify.setContent(msg.getContent());

            send(notify);
            LogWriter.log("UC1",
                    "[Negotiator→Notifier] cid=UC1_NOTIFY content=" + notify.getContent());
        }

        else if ("UC1_CONFIRM".equals(cid)) {

            LogWriter.log("UC1",
                    "[User→Negotiator] cid=UC1_CONFIRM content=" + msg.getContent()
                            + " (UC1 finished)");
        }
    }

    /* --------------------------- UC2 logic ---------------------------------- */

    private void handleUC2(ACLMessage msg) {

        String cid = msg.getConversationId();

        if (msg.getPerformative() == ACLMessage.REQUEST &&
                "UC2_MEETING_REQUEST".equals(cid)) {

            LogWriter.log("UC2",
                    "[User→Negotiator] cid=UC2_MEETING_REQUEST content=" + msg.getContent());

            ACLMessage fbq1 = new ACLMessage(ACLMessage.REQUEST);
            fbq1.addReceiver(new AID("Calendar", AID.ISLOCALNAME));
            fbq1.setConversationId("UC2_FREEBUSY_QUERY_1");
            fbq1.setContent("Window#1");

            send(fbq1);
            LogWriter.log("UC2",
                    "[Negotiator→Calendar] cid=UC2_FREEBUSY_QUERY_1 content=Window#1");
        }

        else if ("UC2_FREEBUSY_REPLY_1".equals(cid)) {

            LogWriter.log("UC2",
                    "[Calendar→Negotiator] cid=UC2_FREEBUSY_REPLY_1 content=" + msg.getContent());

            ACLMessage p1 = new ACLMessage(ACLMessage.INFORM);
            p1.addReceiver(new AID("User", AID.ISLOCALNAME));
            p1.setConversationId("UC2_PROPOSAL_1");
            p1.setContent(msg.getContent());

            send(p1);
            LogWriter.log("UC2",
                    "[Negotiator→User] cid=UC2_PROPOSAL_1 content=" + p1.getContent());
        }

        else if ("UC2_DECISION_REJECT".equals(cid)) {

            LogWriter.log("UC2",
                    "[User→Negotiator] cid=UC2_DECISION_REJECT content=REJECT");

            ACLMessage fbq2 = new ACLMessage(ACLMessage.REQUEST);
            fbq2.addReceiver(new AID("Calendar", AID.ISLOCALNAME));
            fbq2.setConversationId("UC2_FREEBUSY_QUERY_2");
            fbq2.setContent("Window#2 (+1 day)");

            send(fbq2);
            LogWriter.log("UC2",
                    "[Negotiator→Calendar] cid=UC2_FREEBUSY_QUERY_2 content=Window#2 (+1 day)");
        }

        else if ("UC2_FREEBUSY_REPLY_2".equals(cid)) {

            LogWriter.log("UC2",
                    "[Calendar→Negotiator] cid=UC2_FREEBUSY_REPLY_2 content=" + msg.getContent());

            ACLMessage p2 = new ACLMessage(ACLMessage.INFORM);
            p2.addReceiver(new AID("User", AID.ISLOCALNAME));
            p2.setConversationId("UC2_PROPOSAL_2");
            p2.setContent(msg.getContent());

            send(p2);
            LogWriter.log("UC2",
                    "[Negotiator→User] cid=UC2_PROPOSAL_2 content=" + p2.getContent());
        }

        else if ("UC2_DECISION_ACCEPT".equals(cid)) {

            LogWriter.log("UC2",
                    "[User→Negotiator] cid=UC2_DECISION_ACCEPT content=ACCEPT");

            ACLMessage commit = new ACLMessage(ACLMessage.REQUEST);
            commit.addReceiver(new AID("Calendar", AID.ISLOCALNAME));
            commit.setConversationId("UC2_COMMIT");
            commit.setContent("Confirm reservation");

            send(commit);
            LogWriter.log("UC2",
                    "[Negotiator→Calendar] cid=UC2_COMMIT content=Confirm reservation");
        }

        else if ("UC2_COMMIT_ACK".equals(cid)) {

            LogWriter.log("UC2",
                    "[Calendar→Negotiator] cid=UC2_COMMIT_ACK content=" + msg.getContent());

            ACLMessage notify = new ACLMessage(ACLMessage.INFORM);
            notify.addReceiver(new AID("Notifier", AID.ISLOCALNAME));
            notify.setConversationId("UC2_NOTIFY");
            notify.setContent(msg.getContent());

            send(notify);
            LogWriter.log("UC2",
                    "[Negotiator→Notifier] cid=UC2_NOTIFY content=" + notify.getContent());
        }

        else if ("UC2_CONFIRM".equals(cid)) {

            LogWriter.log("UC2",
                    "[User→Negotiator] cid=UC2_CONFIRM content=OK (UC2 finished)");
        }
    }

    /* --------------------------- UC3 logic ---------------------------------- */

    private void handleUC3(ACLMessage msg) {

        String cid = msg.getConversationId();

        if (msg.getPerformative() == ACLMessage.REQUEST &&
                "UC3_CANCEL_REQUEST".equals(cid)) {

            String meetingId = msg.getContent();
            LogWriter.log("UC3",
                    "[User→Negotiator] cid=UC3_CANCEL_REQUEST meetingId=" + meetingId);

            ACLMessage cancelCommit = new ACLMessage(ACLMessage.REQUEST);
            cancelCommit.addReceiver(new AID("Calendar", AID.ISLOCALNAME));
            cancelCommit.setConversationId("UC3_CANCEL_COMMIT");
            cancelCommit.setContent(meetingId);

            send(cancelCommit);
            LogWriter.log("UC3",
                    "[Negotiator→Calendar] cid=UC3_CANCEL_COMMIT meetingId=" + meetingId);
        }

        else if ("UC3_CANCEL_ACK".equals(cid)) {

            String meetingId = msg.getContent();
            LogWriter.log("UC3",
                    "[Calendar→Negotiator] cid=UC3_CANCEL_ACK meetingId=" + meetingId);

            ACLMessage notify = new ACLMessage(ACLMessage.INFORM);
            notify.addReceiver(new AID("Notifier", AID.ISLOCALNAME));
            notify.setConversationId("UC3_NOTIFY_CANCELLATION");
            notify.setContent(meetingId);

            send(notify);
            LogWriter.log("UC3",
                    "[Negotiator→Notifier] cid=UC3_NOTIFY_CANCELLATION meetingId=" + meetingId);
        }

        else if ("UC3_CANCELLATION_OK".equals(cid)) {

            LogWriter.log("UC3",
                    "[User→Negotiator] cid=UC3_CANCELLATION_OK content=OK (UC3 finished)");
        }
    }
}
