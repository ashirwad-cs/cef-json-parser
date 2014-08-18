package parsing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import parsing.bean.CEFevent;

import static org.junit.Assert.*;

public class CEFtoJSONTest {

    CEFtoJSON instance;
    String CEFevent;

    @Before
    public void setUp() throws Exception {

        instance = CEFtoJSON.getInstance();
        this.CEFevent = "Sep 19 08:26:10 host CEF:2|Security|threatmanager|1.0|100|worm successfully stopped|10|src=10.0.0.1 dst=2.1.2.2 spt=1232";

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCEFtoJSON() throws Exception {

        String ceFtoJSON = instance.getJSONstringFromCEFstring(CEFevent);
        System.out.println("JSON of CEF event: " + ceFtoJSON);
        assertNotNull(ceFtoJSON);
    }


    @Test
    public void testparseHeadersAndBody() throws Exception {

        CEFevent cefHeaders = instance.parseHeadersAndBody(CEFevent);
        assertNotNull(cefHeaders.getCEFversion());
        System.out.println("CEF Version: " + cefHeaders.getCEFversion());

        assertNotNull(cefHeaders.getDeviceVendor());
        System.out.println("Device Vendor: " + cefHeaders.getDeviceVendor());

        assertNotNull(cefHeaders.getDeviceProduct());
        System.out.println("Device Product:" + cefHeaders.getDeviceProduct());

        assertNotNull(cefHeaders.getDeviceVersion());
        System.out.println("Device Version:" + cefHeaders.getDeviceVersion());

        assertNotNull(cefHeaders.getSignatureID());
        System.out.println("SignatureID: " + cefHeaders.getSignatureID());

        assertNotNull(cefHeaders.getEventName());
        System.out.println("EventName: " + cefHeaders.getEventName());

        assertNotNull(cefHeaders.getSeverity());
        System.out.println("Event Severity: " + cefHeaders.getSeverity());


    }
}