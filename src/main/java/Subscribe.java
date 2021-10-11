import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.PubsubMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Subscribe {
    private static Logger log = LoggerFactory.getLogger("Subscriber.demo");

    public static void main(String... args) {
//        String projectId = "mtech-wms-dc2-nonprod";
//        String subscriptionName = "pull__location_events_new_dev__locationintegration_br.development";
//        String subscriptionName = "pull__location_events_new_qa__locationintegration_br.automation";
//        String subscriptionName = "whm-inventory-ng-outbound-eventLogging-dev";
        String projectId = "mtech-dc2-perf";
        String subscriptionName = "pull__whm_inventory_events_new_uat__ceb_adapter";
        subscribeAsyncExample(projectId, subscriptionName);
    }

    private static void subscribeAsyncExample(String projectId, String subscriptionName) {
        // Instantiate an asynchronous message receiver.
        log.info("Subscribing to project: {}, subscription: {}", projectId, subscriptionName);
        MessageReceiver receiver = Subscribe::receiveMessage;
        log.info("Subscribed to project: {}, subscription: {}", projectId, subscriptionName);
        String fullSubscriptionName = "projects/" + projectId + "/subscriptions/" + subscriptionName;
        Subscriber subscriber = null;
        try {
            subscriber = Subscriber.newBuilder(fullSubscriptionName, receiver).build();
            // Start the subscriber.
            subscriber.startAsync().awaitRunning();
            log.info("Listening for messages on project: {}, subscription: {}", projectId, subscriptionName);
            // Allow the subscriber to run for 30s unless an unrecoverable error occurs.
            subscriber.awaitTerminated(30, TimeUnit.SECONDS);
        } catch (TimeoutException timeoutException) {
            // Shut down the subscriber after 30s. Stop receiving messages.
            log.info("Subscriber shutting down after {}s", 30);
            subscriber.stopAsync();
            log.info("Shutdown done");
        }
    }

    // Handle incoming message, then ack the received message.
    public static void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
        log.info("receiveMessage - message: {}", message);
//        consumer.ack();
//        log.info("Acknowledged received message");
    }
}
