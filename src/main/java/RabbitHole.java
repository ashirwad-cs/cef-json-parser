import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parsing.CEFtoJSON;

import java.io.File;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * Created by Gaurav Kumar <gauravphoenix@gmail.com> on 7/20/2014.
 */
public class RabbitHole extends AbstractSink implements Configurable {
    public static CounterGroup eventCounter;
    private final Logger logger = LoggerFactory.getLogger(RabbitHole.class);
    private String outputDirPath;

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
        logger.info("Output (JSON from CEF) will be stored in directory " + context.getString("outputDir"));
        outputDirPath = context.getString("outputDir");
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
                String jsonFromCEFevent = CEFtoJSON.getInstance().getJSONstringFromCEFstring(eventStr);
                logger.debug("JSON from CEF Event: " + jsonFromCEFevent);

                //write jsont to file
                File f = new File(outputDirPath + File.separator + UUID.randomUUID());
                PrintWriter printWriter = new PrintWriter(f);
                printWriter.print(jsonFromCEFevent);
                printWriter.close();

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
