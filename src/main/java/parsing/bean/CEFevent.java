package parsing.bean;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Created by Gaurav Kumar <gk@tail-f.guru> on 8/16/2014.
 */
public class CEFevent {

    @Expose
    LocalDateTime localDateTime;

    @Expose
    String CEFversion;

    @Expose
    String DeviceVendor;


    @Expose
    String DeviceProduct;

    @Expose
    String DeviceVersion;

    @Expose
    String SignatureID;


    @Expose
    String EventName;


    @Expose
    String Severity;

    @Expose
    String SyslogHost;

    @Expose
    HashMap<String, String> extensions;


    String eventBody;


    @Override
    public String toString() {
        return "CEFevent{" +
                "localDateTime=" + localDateTime +
                ", CEFversion='" + CEFversion + '\'' +
                ", DeviceVendor='" + DeviceVendor + '\'' +
                ", DeviceProduct='" + DeviceProduct + '\'' +
                ", DeviceVersion='" + DeviceVersion + '\'' +
                ", SignatureID='" + SignatureID + '\'' +
                ", EventName='" + EventName + '\'' +
                ", Severity='" + Severity + '\'' +
                ", SyslogHost='" + SyslogHost + '\'' +
                ", eventBody='" + eventBody + '\'' +
                ", extensions=" + extensions +
                '}';
    }

    public HashMap<String, String> getExtensions() {
        return extensions;
    }

    public void setExtensions(HashMap<String, String> extensions) {
        this.extensions = extensions;
    }

    public String getEventBody() {
        return eventBody;
    }

    public void setEventBody(String eventBody) {
        this.eventBody = eventBody;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getSyslogHost() {
        return SyslogHost;
    }

    public void setSyslogHost(String syslogHost) {
        this.SyslogHost = syslogHost;
    }

    public String getCEFversion() {
        return CEFversion;
    }

    public void setCEFversion(String CEFversion) {
        this.CEFversion = CEFversion;
    }

    public String getDeviceVendor() {
        return DeviceVendor;
    }

    public void setDeviceVendor(String deviceVendor) {
        DeviceVendor = deviceVendor;
    }

    public String getDeviceProduct() {
        return DeviceProduct;
    }

    public void setDeviceProduct(String deviceProduct) {
        DeviceProduct = deviceProduct;
    }

    public String getDeviceVersion() {
        return DeviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        DeviceVersion = deviceVersion;
    }

    public String getSignatureID() {
        return SignatureID;
    }

    public void setSignatureID(String signatureID) {
        SignatureID = signatureID;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getSeverity() {
        return Severity;
    }

    public void setSeverity(String severity) {
        Severity = severity;
    }

}
