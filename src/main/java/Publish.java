import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.gson.Gson;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Publish {
    private static final Logger log = LoggerFactory.getLogger("Publish.demo");
    private static Gson mapper;

    public static void main(String... args) throws Exception {
        mapper = new Gson();
        String projectId = "mtech-wms-dc2-nonprod";
//        String projectId = "mtech-dc2-perf";

        String topicId = "whm_inventory_events_new_qa";
//        String topicId = "whm_inventory_events_new_dev";
//        String topicId = "whm_inventory_events_new_uat";
        publishWithErrorHandlerExample(projectId, topicId);
    }

    static void publishWithErrorHandlerExample(String projectId, String topicId) throws IOException, InterruptedException {
        log.info("publishWithErrorHandlerExample({}, {})", projectId, topicId);
        TopicName topicName = ProjectTopicName.of(projectId, topicId);
        Publisher publisher = null;
        try {
            // Create a publisher instance with default settings bound to the topic
            publisher = Publisher.newBuilder(topicName).build();
            List<String> messages = Arrays.asList(
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASESCAN\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"0\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"PUTAWAY\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASEFROMACTIVE\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASELOCK\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"lockCodes\":[\"DM\",\"LW\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASELOCK\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"lockCodes\":[\"PP\",\"DM\",\"LW\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASEUNLOCK\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASEUNLOCK\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"lockCodes\":[\"DM\",\"LW\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASEUNLOCK\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"lockCodes\":[\"PP\",\"DM\",\"LW\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"INVUNLOCK\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"INVLOCK\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"ADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"RV\",\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}],\"orderNumber\":101123}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"ADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"RV\",\"lockCodes\":[\"PP\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":-5}],\"orderNumber\":101123}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"ADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"RV\",\"lockCodes\":[\"DM\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}],\"orderNumber\":101123}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"ADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"RV\",\"lockCodes\":[\"PP\",\"DM\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}],\"orderNumber\":101123}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"ADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"QD\",\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"ADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"QD\",\"lockCodes\":[\"PP\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"ADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"QD\",\"lockCodes\":[\"DM\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"ADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"QD\",\"lockCodes\":[\"PP\",\"DM\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"ADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"DW\",\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"ADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"DW\",\"lockCodes\":[\"PP\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"ADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"DW\",\"lockCodes\":[\"DM\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"ADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"DW\",\"lockCodes\":[\"DM\",\"PP\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASEADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"RV\",\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}],\"orderNumber\":101123}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASEADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"RV\",\"lockCodes\":[\"PP\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}],\"orderNumber\":101123}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASEADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"RV\",\"lockCodes\":[\"DM\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}],\"orderNumber\":101123}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASEADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"RV\",\"lockCodes\":[\"DM\",\"PP\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}],\"orderNumber\":101123}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASEADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"QD\",\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASEADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"QD\",\"lockCodes\":[\"PP\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASEADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"QD\",\"lockCodes\":[\"DM\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASEADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"QD\",\"lockCodes\":[\"PP\",\"DM\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASEADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"QD\",\"lockCodes\":[\"PP\",\"LW\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASEADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"QD\",\"lockCodes\":[\"PP\",\"DM\",\"LW\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASEADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"DW\",\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASEADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"DW\",\"lockCodes\":[\"PP\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASEADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"DW\",\"lockCodes\":[\"DM\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASEADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"DW\",\"lockCodes\":[\"DM\",\"PP\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CONSUME\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"RV\",\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":-5}],\"orderNumber\":101123}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CONSUME\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"RV\",\"lockCodes\":[\"PP\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":-5}],\"orderNumber\":101123}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CONSUME\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"RV\",\"lockCodes\":[\"DM\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":-5}],\"orderNumber\":101123}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CONSUME\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"RV\",\"lockCodes\":[\"PP\",\"DM\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":-5}],\"orderNumber\":101123}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CONSUME\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"QD\",\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":-5}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CONSUME\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"QD\",\"lockCodes\":[\"PP\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":-5}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CONSUME\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"QD\",\"lockCodes\":[\"DM\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":-5}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CONSUME\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"QD\",\"lockCodes\":[\"DM\",\"PP\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":-5}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CONSUME\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"DW\",\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":-5}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CONSUME\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"DW\",\"lockCodes\":[\"PP\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":-5}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CONSUME\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"DW\",\"lockCodes\":[\"DM\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":-5}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CONSUME\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"DW\",\"lockCodes\":[\"PP\",\"DM\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":-5}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"ADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"CYCLECOUNT\",\"lockCodes\":[],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"ADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"CYCLECOUNT\",\"lockCodes\":[\"PP\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":-10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"ADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"CYCLECOUNT\",\"lockCodes\":[\"DM\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"ADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"CYCLECOUNT\",\"lockCodes\":[\"DM\"],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":-10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"ADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"CYCLECOUNT\",\"lockCodes\":[],\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":-10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASESCAN\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"lockCodes\":[\"PP\"],\"containerBarcode\":\"00000699859521061575\",\"po\":\"3701533\",\"asn\":\"99374870\",\"receipt\":\"294561686\",\"keyrec\":\"7973396\",\"keyRecSuffix\":\"000\",\"items\":[{\"upc\":\"889569641138\",\"quantity\":56}]}",
                    "{\"application\":\"MACYSWMS\",\"locnNbr\":3950,\"lgclLocnNbr\":\"6687\",\"eventName\":\"PUTAWAY\",\"reason\":\"PUTAWAY\",\"containerBarcode\":\"00000000005204902898\",\"po\":\"4585357\",\"asn\":\"137456\",\"receipt\":\"45853351\",\"items\":[{\"item\":\"492034191890\",\"quantity\":5}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASEADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"RV\",\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"47288330000\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}],\"orderNumber\":47288330000}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASESCAN\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6230\",\"reason\":\"RV\",\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"47288330000\",\"receipt\":\"124451\",\"items\":[{\"item\":\"190618460522\",\"quantity\":10}],\"orderNumber\":47288330000}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASESCAN\",\"transactionName\":\"RF Case Scan\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"RV\",\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"47288330000\",\"receipt\":\"124451\",\"items\":[{\"item\":\"190618460522\",\"quantity\":10}],\"containerStatus\":\"PTW\",\"containerType\":\"CSE\"}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASEADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"RV\",\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"47288330000\",\"receipt\":\"124451\",\"items\":[{\"item\":\"190618460522\",\"quantity\":10}],\"orderNumber\":47288330000}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASEADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"containerBarcode\":\"PL020000002222222204\",\"asn\":\"104149339\",\"po\":4945005,\"reason\":\"RV\",\"lockCodes\":[],\"items\":[{\"item\":\"636206748615\",\"quantity\":-1,\"totalReceivedQty\":65,\"poOrderedQty\":50,\"avlOnOrderQty\":5}],\"receipt\":4580635,\"containerType\":\"PLT\",\"containerStatus\":\"SRT\",\"transactionName\":\"RF Consume Pallet\",\"createdTime\":\"2021-07-21 15:59:15.660\"}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASEADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"DW\",\"receiptStatus\":\"BOOKED\",\"lockCodes\":[\"PP\"],\"containerBarcode\":\"05489348023427\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"keyrec\":\"8235792\",\"suffix\":\"0000\",\"items\":[{\"item\":\"3123123\",\"quantity\":5,\"division\":71},{\"item\":\"312414\",\"quantity\":10,\"division\":72},{\"item\":\"312525\",\"quantity\":15},{\"item\":\"312636\",\"quantity\":20,\"division\":0}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASEADJUST\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"reason\":\"DW\",\"receiptStatus\":\"BOOKED\",\"lockCodes\":[\"PP\"],\"containerBarcode\":\"05489348023427\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"keyrec\":\"8235792\",\"suffix\":\"0000\",\"items\":[{\"item\":\"636189491690\",\"quantity\":5,\"division\":71}]}",
                    "{\"application\":\"MACYSWMS\",\"locnNbr\":3950,\"lgclLocnNbr\":\"6687\",\"eventName\":\"PUTAWAY\",\"reason\":\"PUTAWAY\",\"containerBarcode\":\"95141000000000002007\",\"po\":\"3674464\",\"asn\":\"153025376\",\"receipt\":\"232245904\",\"items\":[{\"item\":\"858557007405\",\"quantity\":-4}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASESCAN\",\"locnNbr\":\"4420\",\"lgclLocnNbr\":\"6236\",\"lockCodes\":[\"PP\"],\"containerBarcode\":\"00049623910242500401\",\"po\":\"4962396\",\"asn\":\"104151286\",\"receipt\":\"4582762\",\"keyrec\":\"8139422\",\"suffix\":\"000\",\"items\":[{\"upc\":\"636189491690\",\"quantity\":1,\"totalReceivedQty\":26,\"poOrderedQty\":50,\"avlOnOrderQty\":1}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CONSUME\",\"transactionName\":\"RF Consume Container\",\"locnNbr\":\"4420\",\"lgclLocnNbr\":\"6236\",\"reason\":\"BK\",\"lockCodes\":[\"PP\"],\"containerBarcode\":\"00049623910242500401\",\"po\":\"4962396\",\"asn\":\"104151286\",\"receipt\":\"4582762\",\"keyrec\":\"8139422\",\"suffix\":\"000\",\"items\":[{\"upc\":\"636189491690\",\"quantity\":1,\"totalReceivedQty\":26,\"poOrderedQty\":50,\"avlOnOrderQty\":1}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CONSUME\",\"transactionName\":\"RF Consume Container\",\"locnNbr\":\"4420\",\"lgclLocnNbr\":\"6236\",\"reason\":\"BK\",\"lockCodes\":[\"DM\"],\"containerBarcode\":\"00049623910242500401\",\"po\":\"4962396\",\"asn\":\"104151286\",\"receipt\":\"4582762\",\"keyrec\":\"8139422\",\"suffix\":\"000\",\"items\":[{\"upc\":\"636189491690\",\"quantity\":1,\"totalReceivedQty\":26,\"poOrderedQty\":50,\"avlOnOrderQty\":1}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CONSUME\",\"transactionName\":\"RF Consume Container\",\"createdTime\":\"2022-09-02T07:17:33.614-04:00\",\"locnNbr\":\"4420\",\"lgclLocnNbr\":\"6236\",\"containerBarcode\":\"95000000056152070365\",\"containerType\":\"BINBOX\",\"containerStatus\":\"50\",\"reason\":\"DW\",\"replayFlag\":\"false\",\"lockCodes\":[],\"items\":[{\"item\":\"190618460522\",\"quantity\":-10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CONSUME\",\"transactionName\":\"RF Consume Container\",\"createdTime\":\"2022-09-02T07:17:33.614-04:00\",\"locnNbr\":\"4420\",\"lgclLocnNbr\":\"6236\",\"containerBarcode\":\"95000000056152070365\",\"containerType\":\"BINBOX\",\"containerStatus\":\"50\",\"reason\":\"DW\",\"replayFlag\":\"false\",\"lockCodes\":[],\"items\":[{\"item\":\"190618460\",\"quantity\":-10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CONSUME\",\"transactionName\":\"RF Consume Container\",\"createdTime\":\"2022-10-13T14:21:55.903-04:00\",\"locnNbr\":\"4420\",\"lgclLocnNbr\":\"6687\",\"containerBarcode\":\"00015810416395000108\",\"containerType\":\"CSE\",\"containerStatus\":\"RCVD\",\"asn\":\"99756650\",\"po\":\"3998971\",\"receipt\":\"3588307\",\"reason\":\"QD\",\"replayFlag\":\"false\",\"lockCodes\":[\"PP\"],\"items\":[{\"item\":\"191671320204\",\"quantity\":-5}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASESCAN\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"lockCodes\":[\"PP\"],\"containerBarcode\":\"00016921510475800012\",\"po\":\"1691858\",\"asn\":\"104181223\",\"receipt\":\"4612881\",\"keyrec\":\"8151674\",\"suffix\":\"000\",\"items\":[{\"upc\":\"4003683108687\",\"quantity\":50,\"totalReceivedQty\":50,\"poOrderedQty\":100,\"avlOnOrderQty\":50}]}"
            );
            boolean allMessages = false;
            boolean useJwt = false;
            boolean useClientId = true;
            Map<String, String> attributes = new HashMap<>();
            if (useJwt) {
                attributes.put("X-WHM-JWT", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCMDEwNTIzIiwic3ViamVjdCI6IkIwMTA1MjMiLCJkaXNwbGF5TmFtZSI6IlNpbW9uIFNvaG4iLCJuYW1lIjoiU2ltb24gU29obiIsImNuIjoiQjAxMDUyMyIsImZpcnN0bmFtZSI6IlNpbW9uIiwibGFzdG5hbWUiOiJTb2huIiwicm9sZXMiOlsiLVdITV9TVVBFUlZJU09SIiwiLVdITV9TVVBQT1JUIiwiLVdITV9TVVBFUlVTRVIiLCItV0hNX1dNU19TVVBQT1JUX1VTRVIiLCItV0hNX0lDUUEiLCItV0hNX1dNU19BRE1JTiIsIi1XSE1fUkYiLCItV0hNX1VTRVIiLCItV0hNX01BQ1lTX1NVUEVSVVNFUiIsIi1XSE1fTUFDWVNfU1VQRVJWSVNPUiIsIi1XSE1fTUdSIiwiLVdITV9NQUNZU19SRl9TVVBFUlZJU09SIiwiLVdITV9NQUNZU19NR1IiLCItV0hNX0RFVkVMT1BFUiIsIi1XSE1fQVJPIiwiLVdITV9QTEFOTkVSIiwiLVdITV9NQUNZU19VU0VSIiwiLVdITV9DVVNUT01ST0xFIl0sImxvY2F0aW9ucyI6WyItV0hNX0xPQ0FUSU9OXzM5NTAiLCItV0hNX0xPQ0FUSU9OXzQ0MjAiLCItV0hNX0xPQ0FUSU9OXzcyMjEiXX0.-BvsD7U-MVR19svZFT1CIPFWP0HFuhqZA-e74kaMET8");
                //attributes.put("X-WHM-JWT", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCMDA2MjQzIiwic3ViamVjdCI6IkIwMDYyNDMiLCJkaXNwbGF5TmFtZSI6Ikp1YW4gSGF1Z2FhcmQiLCJuYW1lIjoiSnVhbiBIYXVnYWFyZCIsImNuIjoiQjAwNjI0MyIsImZpcnN0bmFtZSI6Ikp1YW4iLCJsYXN0bmFtZSI6IkhhdWdhYXJkIiwicm9sZXMiOlsiLVdITV9TVVBFUlZJU09SIiwiLVdITV9TVVBQT1JUIiwiLVdITV9TVVBFUlVTRVIiLCItV0hNX1dNU19TVVBQT1JUX1VTRVIiLCItV0hNX0lDUUEiLCItV0hNX1dNU19BRE1JTiIsIi1XSE1fUkYiLCItV0hNX1VTRVIiLCItV0hNX01BQ1lTX1NVUEVSVVNFUiIsIi1XSE1fTUFDWVNfU1VQRVJWSVNPUiIsIi1XSE1fTUdSIiwiLVdITV9NQUNZU19SRl9TVVBFUlZJU09SIiwiLVdITV9NQUNZU19NR1IiLCItV0hNX0RFVkVMT1BFUiIsIi1XSE1fQVJPIiwiLVdITV9QTEFOTkVSIiwiLVdITV9NQUNZU19VU0VSIiwiLVdITV9DVVNUT01ST0xFIl0sImxvY2F0aW9ucyI6WyItV0hNX0xPQ0FUSU9OXzM5NTAiLCItV0hNX0xPQ0FUSU9OXzQ0MjAiLCItV0hNX0xPQ0FUSU9OXzcyMjEiXX0.gIfedpAtiDJ0MxmzuhzG45g6bsqm9Rscie15wD2-i2k");
            }
            if (useClientId) {
                attributes.put("X-WHM-CLIENT", "receiving-service");
            }
            if (allMessages) {
                for (String message : messages) {
                    publish(publisher, message, attributes);
                }
            } else {
                String message = messages.get(114 - 45);
                publish(publisher, message, attributes);
            }
            sleep(3);
        } finally {
            if (publisher != null) {
                // When finished with the publisher, shutdown to free up resources.
                log.info("publisher.shutdown()");
                publisher.shutdown();
                log.info("publisher.awaitTermination()");
                publisher.awaitTermination(1, TimeUnit.MINUTES);
            }
        }
        log.info("Application done");
    }

    private static void publish(Publisher publisher, String message, Map<String, String> attributes) {
        IncomingMessageDTO dto = mapper.fromJson(message, IncomingMessageDTO.class);
        attributes.put("locnNbr", dto.getLocnNbr());
        attributes.put("eventName", dto.getEventName());
        ByteString data = ByteString.copyFromUtf8(message);
        for (int index = 0; index < 1; index++) {
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                    .setData(data)
                    .putAllAttributes(attributes)
                    .build();

            log.info("publishing: {}", message);
            // Once published, returns a server-assigned message id (unique within the topic)
            ApiFuture<String> future = publisher.publish(pubsubMessage);

            // Add an asynchronous callback to handle success / failure
            ApiFutures.addCallback(future, new BasicFutureCallback(message), MoreExecutors.directExecutor());
        }
    }

    static void sleep(int seconds) {
        try {
            log.info("sleeping {} seconds", seconds);
            Thread.sleep(seconds * 1000L);
            log.info("done sleeping.");
        } catch (InterruptedException e) {
            log.error("Sleep interrupted", e);
        }
    }

    static class BasicFutureCallback implements ApiFutureCallback<String> {
        private final String message;

        public BasicFutureCallback(final String message) {
            this.message = message;
        }

        @Override
        public void onFailure(Throwable throwable) {
            if (throwable instanceof ApiException) {
                ApiException apiException = ((ApiException) throwable);
                // details on the API exception
                log.error("ApiException status code: {}", apiException.getStatusCode().getCode());
                log.error("ApiException is retryable: {}", apiException.isRetryable());
            }
            log.error("Error publishing message : {}", message);
            log.error("Throwable class: {}", throwable.getClass().getName());
            log.error("Stack trace", throwable);
        }

        @Override
        public void onSuccess(String messageId) {
            // Once published, returns server-assigned message ids (unique within the topic)
            log.info("Published message ID: {}", messageId);
        }
    }
}
