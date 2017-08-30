package com.codephillip.app.busticket;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class GeneralUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void checkServerSoapString() throws Exception {
        String soapString = "<?xml version=\"1.0\"?>\n" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:b2b=\"http://b2b.mobilemoney.mtn.zm_v1.0\">\n" +
                "  <soapenv:Header>\n" +
                "    <RequestSOAPHeader xmlns=\"http://www.huawei.com.cn/schema/common/v2_1\">\n" +
                "      <spId>2560110004776</spId>\n" +
                "      <spPassword>BD3B1BB8B4822636B7520B96C4A568C7</spPassword>\n" +
                "      <bundleID/>\n" +
                "      <serviceId/>\n" +
                "      <timeStamp>20170503144638</timeStamp>\n" +
                "    </RequestSOAPHeader>\n" +
                "  </soapenv:Header>\n" +
                "  <soapenv:Body>\n" +
                "    <b2b:processRequest>\n" +
                "      <serviceId>200</serviceId>\n" +
                "      <parameter>\n" +
                "        <name>DueAmount</name>\n" +
                "        <value>500</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>MSISDNNum</name>\n" +
                "        <value>256789900760</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>ProcessingNumber</name>\n" +
                "        <!-- generate random java value -->\n" +
                "        <value>%s</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>serviceId</name>\n" +
                "        <value>appchallenge3.sp</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>AcctRef</name>\n" +
                "         <value>101</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>AcctBalance</name>\n" +
                "        <value>300000</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>MinDueAmount</name>\n" +
                "        <value>200</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>Narration</name>\n" +
                "        <value>You have made payment for a bus ticket</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>PrefLang</name>\n" +
                "        <value>en</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>OpCoID</name>\n" +
                "        <value>25601</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>CurrCode</name>\n" +
                "        <value>UGX</value>\n" +
                "      </parameter>\n" +
                "    </b2b:processRequest>\n" +
                "  </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        String expectedSoapString = "<?xml version=\"1.0\"?>\n" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:b2b=\"http://b2b.mobilemoney.mtn.zm_v1.0\">\n" +
                "  <soapenv:Header>\n" +
                "    <RequestSOAPHeader xmlns=\"http://www.huawei.com.cn/schema/common/v2_1\">\n" +
                "      <spId>2560110004776</spId>\n" +
                "      <spPassword>BD3B1BB8B4822636B7520B96C4A568C7</spPassword>\n" +
                "      <bundleID/>\n" +
                "      <serviceId/>\n" +
                "      <timeStamp>20170503144638</timeStamp>\n" +
                "    </RequestSOAPHeader>\n" +
                "  </soapenv:Header>\n" +
                "  <soapenv:Body>\n" +
                "    <b2b:processRequest>\n" +
                "      <serviceId>200</serviceId>\n" +
                "      <parameter>\n" +
                "        <name>DueAmount</name>\n" +
                "        <value>500</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>MSISDNNum</name>\n" +
                "        <value>256789900760</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>ProcessingNumber</name>\n" +
                "        <!-- generate random java value -->\n" +
                "        <value>hello</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>serviceId</name>\n" +
                "        <value>appchallenge3.sp</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>AcctRef</name>\n" +
                "         <value>101</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>AcctBalance</name>\n" +
                "        <value>300000</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>MinDueAmount</name>\n" +
                "        <value>200</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>Narration</name>\n" +
                "        <value>You have made payment for a bus ticket</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>PrefLang</name>\n" +
                "        <value>en</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>OpCoID</name>\n" +
                "        <value>25601</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>CurrCode</name>\n" +
                "        <value>UGX</value>\n" +
                "      </parameter>\n" +
                "    </b2b:processRequest>\n" +
                "  </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        soapString = String.format(soapString, "hello");

        assertEquals(expectedSoapString, soapString);
    }

    @Test
    public void testFormatDate() throws Exception {
        assertEquals("Wed Aug 30", Utils.formatDateString(new Date().toString()));
    }
}