package app.boot;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class AutoBoot {

    public static void main(String[] args) throws Exception {

        String scenario = System.getProperty("demo.uc", "UC1");

        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        p.setParameter(Profile.GUI, "true");   // RMA auto-start

        // Create Main Container
        AgentContainer mc = rt.createMainContainer(p);

        // Create all system agents except User
        mc.createNewAgent("Calendar", "app.agents.CalendarAgent", new Object[]{ scenario }).start();
        mc.createNewAgent("Negotiator", "app.agents.NegotiatorAgent", new Object[]{ scenario }).start();
        mc.createNewAgent("Notifier", "app.agents.NotifierAgent", new Object[]{ scenario }).start();
        mc.createNewAgent("PrettySniffer", "app.agents.PrettySnifferAgent", new Object[]{ scenario }).start();

        System.out.println("=== System Agents started. Now create User manually in RMA. ===");
    }
}
