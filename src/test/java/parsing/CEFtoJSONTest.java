package parsing;

import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import parsing.bean.CEFheaders;

import static org.junit.Assert.*;

public class CEFtoJSONTest {

    CEFtoJSON instance;

    @Before
    public void setUp() throws Exception {

        instance = CEFtoJSON.getInstance();

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetCEFHeaders() throws Exception {

        CEFheaders cefHeaders = instance.getCEFHeaders("Sep 19 08:26:10 host CEF:2|Security|threatmanager|1.0|100|worm successfully stopped|10|src=10.0.0.1 dst=2.1.2.2 spt=1232");
        assertNotNull(cefHeaders.getCEFversion());
        System.out.println("CEF Version: " + cefHeaders.getCEFversion());

        assertNotNull(cefHeaders.getDeviceVendor());
        System.out.println("Device Vendor: " + cefHeaders.getDeviceVendor());

        assertNotNull(cefHeaders.getDeviceProduct());
        System.out.println("Device Product:" + cefHeaders.getDeviceProduct());

        assertNotNull(cefHeaders.getDeviceVersion());
        System.out.println("Device Version:" + cefHeaders.getDeviceVersion());

        assertNotNull(cefHeaders.getSignatureID());
        System.out.println("SignatureID: "+cefHeaders.getSignatureID());

        assertNotNull(cefHeaders.getEventName());
        System.out.println("EventName: "+cefHeaders.getEventName());

        assertNotNull(cefHeaders.getSeverity());
        System.out.println("Event Severity: "+cefHeaders.getSeverity());


    }
}