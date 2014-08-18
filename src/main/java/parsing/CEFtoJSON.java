package parsing;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import parsing.bean.CEFevent;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Gaurav Kumar <gk@tail-f.guru> on 8/16/2014.
 */

public class CEFtoJSON {

    private Gson gson;
    private static CEFtoJSON instance;

    /**
     * @return singleton CEFtoJSON. Note that it is not thread safe but it should be okay as the intent is to use this instance under Flume Sink which is single threaded anyways
     */

    public static CEFtoJSON getInstance() {

        if (instance == null) {
            instance = new CEFtoJSON();
        }
        return instance;
    }


    public CEFtoJSON() {
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    /**
     * @param CEFstring valid CEF String which is to be converted to JSON
     * @return JSON converted from input string CEFstring
     */
    public String getJSONstringFromCEFstring(String CEFstring) throws IOException {

        CEFevent ceFevent = parseHeadersAndBody(CEFstring);
        String eventBody = ceFevent.getEventBody();
        HashMap<String, String> cefExtensions = new HashMap<>(200);

        for (; ; ) {
            if (indexOfNextUnescapedEqualSign(eventBody) == -1) {
                break;
            }

            int indexOfNextEqualSign = indexOfNextUnescapedEqualSign(eventBody);
            String key = eventBody.substring(0, indexOfNextEqualSign);

            String eventBodyNew = eventBody.substring(indexOfNextEqualSign + 1);
            int tmpIndex = indexOfNextUnescapedEqualSign(eventBodyNew);

            if (tmpIndex == -1) { //this is the last equal sign
                cefExtensions.put(key, eventBodyNew);
                break;
            }

            int nextToNextEqualSign = tmpIndex + key.length() + 1;
            String subString = eventBody.substring(indexOfNextEqualSign + 1, nextToNextEqualSign);
            String value = subString.substring(0, subString.lastIndexOf(' '));
            key = key.replaceAll("\\s+", "");//remove whitespaces if any from key
            cefExtensions.put(key, value);

            eventBody = eventBodyNew.substring(value.length());
        }
        ceFevent.setExtensions(cefExtensions);
        return gson.toJson(ceFevent);
    }

    private int indexOfNextUnescapedEqualSign(String CEFString) {

        for (int i = 0; i < CEFString.length(); i++) {
            if (CEFString.charAt(i) == '=') {
                if (i > 0) {
                    if (CEFString.charAt(i - 1) == '\\') {
                        continue;
                    }
                }

                return i;
            }

        }


        return -1;

    }//end of method


    /**
     * @param CEFstring valid CEF String which is to be converted to JSON
     * @return JSON string representing CEF headers
     */
    public CEFevent parseHeadersAndBody(String CEFstring) {

        //TODO: validate if input is a valid CEF string and throw appropriate exception
        //TODO: escape \ in headers
        //sample event: Sep 19 08:26:10 host CEF:0|Security|threatmanager|1.0|100|worm successfully stopped|10|src=10.0.0.1 dst=2.1.2.2 spt=1232

        if (CEFstring == null) {
            return null;

        }

        CEFevent ceFevent = new CEFevent();

        //extract CEF Version
        int firstPipePosition = CEFstring.indexOf("|");
        String CEFVersionTag = CEFstring.substring(CEFstring.indexOf("CEF"), firstPipePosition);
        String[] versionSplit = CEFVersionTag.split(":");
        ceFevent.setCEFversion(versionSplit[1]);


        //extract vendor
        int secondPipePosition = CEFstring.indexOf("|", firstPipePosition + 1);
        String vendor = CEFstring.substring(firstPipePosition + 1, secondPipePosition);
        ceFevent.setDeviceVendor(vendor);


        //extract product
        int thirdPipePosition = CEFstring.indexOf("|", secondPipePosition + 1);
        String deviceProduct = CEFstring.substring(secondPipePosition + 1, thirdPipePosition);
        ceFevent.setDeviceProduct(deviceProduct);

        //extract version
        int fourthPipePosition = CEFstring.indexOf("|", thirdPipePosition + 1);
        String deviceVersion = CEFstring.substring(thirdPipePosition + 1, fourthPipePosition);
        ceFevent.setDeviceVersion(deviceVersion);


        //extract signature ID
        int fifthPipePosition = CEFstring.indexOf("|", fourthPipePosition + 1);
        String sigID = CEFstring.substring(fourthPipePosition + 1, fifthPipePosition);
        ceFevent.setSignatureID(sigID);

        //extract eventName
        int sixthPipePoistion = CEFstring.indexOf("|", fifthPipePosition + 1);
        String eventName = CEFstring.substring(fifthPipePosition + 1, sixthPipePoistion);
        ceFevent.setEventName(eventName);

        //extract severity

        int seventhPipePostion = CEFstring.indexOf("|", sixthPipePoistion + 1);
        String severity = CEFstring.substring(sixthPipePoistion + 1, seventhPipePostion);
        ceFevent.setSeverity(severity);

        //extract body
        ceFevent.setEventBody(CEFstring.substring(seventhPipePostion + 1));

        return ceFevent;

    }


}
