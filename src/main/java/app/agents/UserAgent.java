package app.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import app.util.LogWriter;

public class UserAgent extends Agent {

    private String uc;

    @Override
    protected void setup() {

        uc = System.getProperty("demo.uc", "UC1").toUpperCase();

        LogWriter.log("SYSTEM", "[User] Init as User | scenario=" + uc);

        switch (uc) {
            case "UC1":
                setupUC1();
                break;
            case "UC2":
                setupUC2();
                break;
            case "UC3":
                setupUC3();
                break;
            default:
                LogWriter.log("SYSTEM", "[User] Unknown scenario: " + uc + " (defaulting to UC1)");
                uc = "UC1";
                setupUC1();
        }
    }

    /* ------------------------- UC1 : Basic scheduling ------------------------- */

    private void setupUC1() {

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                try { Thread.sleep(800); } catch (Exception ignored) {}

                ACLMessage req = new ACLMessage(ACLMessage.REQUEST);
                req.addReceiver(new AID("Negotiator", AID.ISLOCALNAME));
                req.setConversationId("UC1_MEETING_REQUEST");
                req.setContent("Thesis Discussion | 2025-09-25T10:00-06:00/45m");

                send(req);
                LogWriter.log("UC1",
                        "[User→Negotiator] cid=UC1_MEETING_REQUEST content=" + req.getContent());
            }
        });

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {

                ACLMessage msg = receive();
                if (msg == null) {
                    block();
                    return;
                }

                String cid = msg.getConversationId();

                if ("UC1_PROPOSAL".equals(cid)) {
                    LogWriter.log("UC1",
                            "[Negotiator→User] cid=UC1_PROPOSAL content=" + msg.getContent());

                    ACLMessage decision = new ACLMessage(ACLMessage.INFORM);
                    decision.addReceiver(new AID("Negotiator", AID.ISLOCALNAME));
                    decision.setConversationId("UC1_DECISION");
                    decision.setContent("ACCEPT");

                    send(decision);
                    LogWriter.log("UC1",
                            "[User→Negotiator] cid=UC1_DECISION content=ACCEPT");
                }

                else if ("UC1_NOTIFICATION".equals(cid)) {
                    LogWriter.log("UC1",
                            "[Notifier→User] cid=UC1_NOTIFICATION content=" + msg.getContent());

                    ACLMessage confirm = new ACLMessage(ACLMessage.INFORM);
                    confirm.addReceiver(new AID("Negotiator", AID.ISLOCALNAME));
                    confirm.setConversationId("UC1_CONFIRM");
                    confirm.setContent("OK");

                    send(confirm);
                    LogWriter.log("UC1",
                            "[User→Negotiator] cid=UC1_CONFIRM content=OK (end of UC1)");
                }
            }
        });
    }

    /* ------------------------- UC2 : Reschedule meeting ---------------------- */

    private void setupUC2() {

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                try { Thread.sleep(800); } catch (Exception ignored) {}

                ACLMessage req = new ACLMessage(ACLMessage.REQUEST);
                req.addReceiver(new AID("Negotiator", AID.ISLOCALNAME));
                req.setConversationId("UC2_MEETING_REQUEST");
                req.setContent("Thesis Discussion | initial window");

                send(req);
                LogWriter.log("UC2",
                        "[User→Negotiator] cid=UC2_MEETING_REQUEST content=" + req.getContent());
            }
        });

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {

                ACLMessage msg = receive();
                if (msg == null) {
                    block();
                    return;
                }
                String cid = msg.getConversationId();

                if ("UC2_PROPOSAL_1".equals(cid)) {
                    LogWriter.log("UC2",
                            "[Negotiator→User] cid=UC2_PROPOSAL_1 content=" + msg.getContent());

                    ACLMessage reject = new ACLMessage(ACLMessage.INFORM);
                    reject.addReceiver(new AID("Negotiator", AID.ISLOCALNAME));
                    reject.setConversationId("UC2_DECISION_REJECT");
                    reject.setContent("REJECT");

                    send(reject);
                    LogWriter.log("UC2",
                            "[User→Negotiator] cid=UC2_DECISION_REJECT content=REJECT");
                }

                else if ("UC2_PROPOSAL_2".equals(cid)) {
                    LogWriter.log("UC2",
                            "[Negotiator→User] cid=UC2_PROPOSAL_2 content=" + msg.getContent());

                    ACLMessage accept = new ACLMessage(ACLMessage.INFORM);
                    accept.addReceiver(new AID("Negotiator", AID.ISLOCALNAME));
                    accept.setConversationId("UC2_DECISION_ACCEPT");
                    accept.setContent("ACCEPT");

                    send(accept);
                    LogWriter.log("UC2",
                            "[User→Negotiator] cid=UC2_DECISION_ACCEPT content=ACCEPT");
                }

                else if ("UC2_NOTIFICATION".equals(cid)) {
                    LogWriter.log("UC2",
                            "[Notifier→User] cid=UC2_NOTIFICATION content=" + msg.getContent());

                    ACLMessage confirm = new ACLMessage(ACLMessage.INFORM);
                    confirm.addReceiver(new AID("Negotiator", AID.ISLOCALNAME));
                    confirm.setConversationId("UC2_CONFIRM");
                    confirm.setContent("OK");

                    send(confirm);
                    LogWriter.log("UC2",
                            "[User→Negotiator] cid=UC2_CONFIRM content=OK (end of UC2)");
                }
            }
        });
    }

    /* ------------------------- UC3 : Cancel meeting ------------------------- */

    private void setupUC3() {

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                try { Thread.sleep(800); } catch (Exception ignored) {}

                String meetingId = "mtg-" + System.currentTimeMillis();

                ACLMessage cancelReq = new ACLMessage(ACLMessage.REQUEST);
                cancelReq.addReceiver(new AID("Negotiator", AID.ISLOCALNAME));
                cancelReq.setConversationId("UC3_CANCEL_REQUEST");
                cancelReq.setContent(meetingId);

                send(cancelReq);
                LogWriter.log("UC3",
                        "[User→Negotiator] cid=UC3_CANCEL_REQUEST content=Cancel " + meetingId);
            }
        });

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {

                ACLMessage msg = receive();
                if (msg == null) {
                    block();
                    return;
                }
                String cid = msg.getConversationId();

                if ("UC3_SEND_CANCEL_NOTICE".equals(cid)) {
                    LogWriter.log("UC3",
                            "[Notifier→User] cid=UC3_SEND_CANCEL_NOTICE content=" + msg.getContent());

                    ACLMessage ok = new ACLMessage(ACLMessage.INFORM);
                    ok.addReceiver(new AID("Negotiator", AID.ISLOCALNAME));
                    ok.setConversationId("UC3_CANCELLATION_OK");
                    ok.setContent("OK");

                    send(ok);
                    LogWriter.log("UC3",
                            "[User→Negotiator] cid=UC3_CANCELLATION_OK content=OK (end of UC3)");
                }
            }
        });
    }
}
