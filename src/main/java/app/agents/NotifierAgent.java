package app.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import app.util.LogWriter;

public class NotifierAgent extends Agent {

    private String uc;

    @Override
    protected void setup() {

        uc = System.getProperty("demo.uc", "UC1").toUpperCase();
        LogWriter.log("SYSTEM", "[Notifier] Init for scenario " + uc);

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

        if ("UC1_NOTIFY".equals(cid)) {

            LogWriter.log("UC1",
                    "[Negotiator→Notifier] cid=UC1_NOTIFY content=" + msg.getContent());

            ACLMessage notif = new ACLMessage(ACLMessage.INFORM);
            notif.addReceiver(new AID("User", AID.ISLOCALNAME));
            notif.setConversationId("UC1_NOTIFICATION");
            notif.setContent(msg.getContent());

            send(notif);
            LogWriter.log("UC1",
                    "[Notifier→User] cid=UC1_NOTIFICATION content=" + notif.getContent());
        }
    }

    /* --------------------------- UC2 ---------------------------------------- */

    private void handleUC2(ACLMessage msg) {

        String cid = msg.getConversationId();

        if ("UC2_NOTIFY".equals(cid)) {

            LogWriter.log("UC2",
                    "[Negotiator→Notifier] cid=UC2_NOTIFY content=" + msg.getContent());

            ACLMessage notif = new ACLMessage(ACLMessage.INFORM);
            notif.addReceiver(new AID("User", AID.ISLOCALNAME));
            notif.setConversationId("UC2_NOTIFICATION");
            notif.setContent(msg.getContent());

            send(notif);
            LogWriter.log("UC2",
                    "[Notifier→User] cid=UC2_NOTIFICATION content=" + notif.getContent());
        }
    }

    /* --------------------------- UC3 ---------------------------------------- */

    private void handleUC3(ACLMessage msg) {

        String cid = msg.getConversationId();

        if ("UC3_NOTIFY_CANCELLATION".equals(cid)) {

            String meetingId = msg.getContent();
            LogWriter.log("UC3",
                    "[Negotiator→Notifier] cid=UC3_NOTIFY_CANCELLATION meetingId=" + meetingId);

            ACLMessage notif = new ACLMessage(ACLMessage.INFORM);
            notif.addReceiver(new AID("User", AID.ISLOCALNAME));
            notif.setConversationId("UC3_SEND_CANCEL_NOTICE");
            notif.setContent("Cancelled meetingId=" + meetingId);

            send(notif);
            LogWriter.log("UC3",
                    "[Notifier→User] cid=UC3_SEND_CANCEL_NOTICE content=Cancelled meetingId="
                            + meetingId);
        }
    }
}
