package app.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import app.util.LogWriter;

public class CalendarAgent extends Agent {

    private String uc;

    @Override
    protected void setup() {

        uc = System.getProperty("demo.uc", "UC1").toUpperCase();
        LogWriter.log("SYSTEM", "[Calendar] Init for scenario " + uc);

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

    /* --------------------------- UC1 ---------------------------------------- */

    private void handleUC1(ACLMessage msg) {

        String cid = msg.getConversationId();

        if ("UC1_FREEBUSY_QUERY".equals(cid)) {

            LogWriter.log("UC1",
                    "[Negotiator→Calendar] cid=UC1_FREEBUSY_QUERY content=" + msg.getContent());

            ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
            reply.addReceiver(new AID("Negotiator", AID.ISLOCALNAME));
            reply.setConversationId("UC1_FREEBUSY_REPLY");
            reply.setContent("2025-09-25T10:00-06:00 -> 2025-09-25T10:45-06:00");

            send(reply);
            LogWriter.log("UC1",
                    "[Calendar→Negotiator] cid=UC1_FREEBUSY_REPLY content=" + reply.getContent());
        }

        else if ("UC1_COMMIT".equals(cid)) {

            LogWriter.log("UC1",
                    "[Negotiator→Calendar] cid=UC1_COMMIT content=" + msg.getContent());

            String meetingId = "mtg-UC1-" + System.currentTimeMillis();

            ACLMessage ack = new ACLMessage(ACLMessage.INFORM);
            ack.addReceiver(new AID("Negotiator", AID.ISLOCALNAME));
            ack.setConversationId("UC1_COMMIT_ACK");
            ack.setContent("meetingId=" + meetingId);

            send(ack);
            LogWriter.log("UC1",
                    "[Calendar→Negotiator] cid=UC1_COMMIT_ACK content=meetingId=" + meetingId);
        }
    }

    /* --------------------------- UC2 ---------------------------------------- */

    private void handleUC2(ACLMessage msg) {

        String cid = msg.getConversationId();

        if ("UC2_FREEBUSY_QUERY_1".equals(cid)) {

            LogWriter.log("UC2",
                    "[Negotiator→Calendar] cid=UC2_FREEBUSY_QUERY_1 content=" + msg.getContent());

            ACLMessage reply1 = new ACLMessage(ACLMessage.INFORM);
            reply1.addReceiver(new AID("Negotiator", AID.ISLOCALNAME));
            reply1.setConversationId("UC2_FREEBUSY_REPLY_1");
            reply1.setContent("2025-09-25T10:00-06:00 -> 2025-09-25T10:45-06:00");

            send(reply1);
            LogWriter.log("UC2",
                    "[Calendar→Negotiator] cid=UC2_FREEBUSY_REPLY_1 content=" + reply1.getContent());
        }

        else if ("UC2_FREEBUSY_QUERY_2".equals(cid)) {

            LogWriter.log("UC2",
                    "[Negotiator→Calendar] cid=UC2_FREEBUSY_QUERY_2 content=" + msg.getContent());

            ACLMessage reply2 = new ACLMessage(ACLMessage.INFORM);
            reply2.addReceiver(new AID("Negotiator", AID.ISLOCALNAME));
            reply2.setConversationId("UC2_FREEBUSY_REPLY_2");
            reply2.setContent("2025-09-26T11:00-06:00 -> 2025-09-26T11:45-06:00");

            send(reply2);
            LogWriter.log("UC2",
                    "[Calendar→Negotiator] cid=UC2_FREEBUSY_REPLY_2 content=" + reply2.getContent());
        }

        else if ("UC2_COMMIT".equals(cid)) {

            LogWriter.log("UC2",
                    "[Negotiator→Calendar] cid=UC2_COMMIT content=" + msg.getContent());

            String meetingId = "mtg-UC2-" + System.currentTimeMillis();

            ACLMessage ack = new ACLMessage(ACLMessage.INFORM);
            ack.addReceiver(new AID("Negotiator", AID.ISLOCALNAME));
            ack.setConversationId("UC2_COMMIT_ACK");
            ack.setContent("meetingId=" + meetingId);

            send(ack);
            LogWriter.log("UC2",
                    "[Calendar→Negotiator] cid=UC2_COMMIT_ACK content=meetingId=" + meetingId);
        }
    }

    /* --------------------------- UC3 ---------------------------------------- */

    private void handleUC3(ACLMessage msg) {

        String cid = msg.getConversationId();

        if ("UC3_CANCEL_COMMIT".equals(cid)) {

            String meetingId = msg.getContent();
            LogWriter.log("UC3",
                    "[Negotiator→Calendar] cid=UC3_CANCEL_COMMIT meetingId=" + meetingId);

            ACLMessage ack = new ACLMessage(ACLMessage.INFORM);
            ack.addReceiver(new AID("Negotiator", AID.ISLOCALNAME));
            ack.setConversationId("UC3_CANCEL_ACK");
            ack.setContent(meetingId);

            send(ack);
            LogWriter.log("UC3",
                    "[Calendar→Negotiator] cid=UC3_CANCEL_ACK meetingId=" + meetingId);
        }
    }
}
