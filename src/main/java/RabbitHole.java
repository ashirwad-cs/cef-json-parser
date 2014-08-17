import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Gaurav Kumar <gauravphoenix@gmail.com> on 7/20/2014.
 */
public class RabbitHole extends AbstractSink implements Configurable {
    public static CounterGroup eventCounter;
    private final Logger logger = LoggerFactory.getLogger(RabbitHole.class);

    public RabbitHole() {
        eventCounter = new CounterGroup();
    }

    @Override
    public void start() {
        logger.info("-=-=-=-=-=-=- Starting sink -=-=-=-=-=-=- ");
        super.start();
    }

    @Override
    public void stop() {

        logger.info("-=-=-=-=-=-=- Stopping sink -=-=-=-=-=-=- ");
        super.stop();


    }


    @Override
    public void configure(Context context) {
        logger.info("Starting configuration");

    }

    @Override
    public Status process() throws EventDeliveryException {
        Channel channel = getChannel();
        Transaction transaction = channel.getTransaction();
        Event event = null;

        try {
            transaction.begin();
            event = channel.take();
            transaction.commit();

            if (event != null) {

                String eventStr = new String(event.getBody());
                logger.debug("Consumed the event: " + eventStr);
                eventCounter.incrementAndGet("events.successful");
                return Status.READY;
            } else {
                return Status.BACKOFF;
            }
        } catch (Exception ex) {
            transaction.rollback();
            eventCounter.incrementAndGet("events.failed");
            logger.error("Failed to deliver event. Exception follows.", ex);
            throw new EventDeliveryException("Failed to deliver event: " + event, ex);
        } finally {
            transaction.close();
        }
    }
}
