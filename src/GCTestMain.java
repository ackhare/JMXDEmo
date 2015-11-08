/**
 * Created by chetan on 8/11/15.
 */

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

// -verbose:gc -Xms256m -Xmx256m
/*
The above simply means that when gc occurs details will be shown
ms is minimum  and mx is maximum
verbose is a flag
 */

public class GCTestMain {

    private static void init() throws MalformedObjectNameException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
        MBeanServer mbs = null;
        //will be registerd in mbean server with the GCTestAgent
        mbs = ManagementFactory.getPlatformMBeanServer();
        GCTestAgent agent = new GCTestAgent();
        ObjectName agentName;
        //name of the agent
        agentName = new ObjectName("PVTests:name=GCTestAgent");
        mbs.registerMBean(agent, agentName);
    }

    public static void main(String[] args) throws Exception {
        init();
        for (;;) {
            Thread.sleep(1000);
        }
    }
}
