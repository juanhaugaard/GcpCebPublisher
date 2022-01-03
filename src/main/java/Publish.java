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

//        String topicId = "whm_inventory_events_new_qa";
        String topicId = "whm_inventory_events_new_dev";
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
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASELOCK\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASEUNLOCK\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"containerBarcode\":\"00049111306595000501\",\"asn\":\"1233\",\"po\":\"3124910\",\"receipt\":\"124451\",\"items\":[{\"item\":\"4003683109219\",\"quantity\":10}]}",
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
                    "{\"application\":\"MACYSWMS\",\"eventName\":\"CASESCAN\",\"locnNbr\":\"3950\",\"lgclLocnNbr\":\"6687\",\"lockCodes\":[\"PP\"],\"containerBarcode\":\"00049623910242500401\",\"po\":\"4962396\",\"asn\":\"104151286\",\"receipt\":\"4582762\",\"keyrec\":\"8139422\",\"suffix\":\"000\",\"items\":[{\"upc\":\"636189491690\",\"quantity\":1,\"totalReceivedQty\":26,\"poOrderedQty\":50,\"avlOnOrderQty\":1}]}"
            );
            boolean allMessages = false;
            boolean useJwt = true;
            boolean useClientId = false;
            Map<String, String> attributes = new HashMap<>();
            if (useJwt) {
                attributes.put("X-WHM-JWT", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCSDE3MDk3Iiwic3ViamVjdCI6IkJIMTcwOTciLCJkaXNwbGF5TmFtZSI6IlJvbXVhbGQgUG9nbyBLYW1vdSAoY29udHJhY3RvcikiLCJuYW1lIjoiUm9tdWFsZCBQb2dvIEthbW91IChjb250cmFjdG9yKSIsImNuIjoiQkgxNzA5NyIsImZpcnN0bmFtZSI6IlJvbXVhbGQiLCJsYXN0bmFtZSI6IlBvZ28gS2Ftb3UiLCJyb2xlcyI6WyItV0hNX1NVUEVSVklTT1IiLCItV0hNX01HUiIsIi1XSE1fU1VQUE9SVCIsIi1XSE1fU1VQRVJVU0VSIiwiLVdITV9ERVZFTE9QRVIiLCItV0hNX0FSTyIsIi1XSE0tTk0tVVNFUiIsIi1XSE1fUExBTk5FUiIsIi1XSE1fSUNRQSIsIi1XSE1fVVNFUiIsIi1XSE0tTk0tQURNSU4iXSwibG9jYXRpb25zIjpbIi1XSE1fTE9DQVRJT05fMzk1MCIsIi1XSE1fTE9DQVRJT05fNDQyMCJdfQ.zkZOfTIiSSdIZWQZ9pw-sd72ff4xRlE2y_bOX1Rb2jM");
            }
            if (useClientId) {
                attributes.put("X-WHM-CLIENT", "receiving-service");
            }
            if (allMessages) {
                for (String message : messages) {
                    publish(publisher, message, attributes);
                }
            } else {
                String message = messages.get(50 - 45);
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
