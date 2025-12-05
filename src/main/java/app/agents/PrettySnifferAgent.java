package app.agents;

import app.util.LogWriter;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Iterator;

public class PrettySnifferAgent extends Agent {

    private final String scenario = System.getProperty("demo.uc", "UC1");

    @Override
    protected void setup() {
        System.out.println("\n===== PrettySniffer Started =====\n");
        LogWriter.log(scenario, "[PrettySniffer] Started as " + getLocalName());

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = myAgent.receive();
                if (msg == null) {
                    block();
                    return;
                }

                String from = msg.getSender() != null
                        ? msg.getSender().getLocalName()
                        : "?";

                String to = "?";
                Iterator<?> it = msg.getAllReceiver();
                if (it.hasNext()) {
                    to = ((AID) it.next()).getLocalName();
                }

                String p = ACLMessage.getPerformative(msg.getPerformative());
                String header = "[SNIFFER] " + from + " -> " + to +
                        " | " + p + " | " + msg.getOntology();

                String body = "          " + (msg.getContent() == null
                        ? "<no content>"
                        : msg.getContent());

                System.out.println(header);
                System.out.println(body);
                System.out.println("--------------------------------------------------");

                LogWriter.log(scenario, header);
                LogWriter.log(scenario, body);
            }
        });
    }
}
