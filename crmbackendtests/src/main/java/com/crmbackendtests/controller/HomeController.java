package com.crmbackendtests.controller;

import com.crmbackendtests.log.Logging;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static com.crmbackendtests.color.Color.*;

/**
 * @author BARNABA
 * @created 13/11/2023 - 11:03 AM
 * @project crmbackendtests
 */
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class HomeController {
    Logger logger = LoggerFactory.getLogger(HomeController.class);
    @PostMapping(path ="/request",produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public String createTutorial(@RequestBody String req) {
//        logger.info("INCOMING REQUEST ->>>>>>>"+ANSI_GREEN+req);
        Logging.LogToFile("Main", req, Logging.LogType.APIREQUESTS,req);
//        logger.warn("A WARN Message");
//        logger.error("An ERROR Message");
        if(req.contains("siebel-xmlext-fields-req")){
            logger.info("INCOMING REQUEST ->>>>>>>"+ANSI_GREEN+req);
            Logging.LogToFile("Main", req, Logging.LogType.INIT,req);
            return  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                    + "<siebel-xmlext-fields-ret>\n"
                    +    "<support field=\"requestRefId\"/>\n"
                    +     "<support field=\"responseCode\"/>\n"
                    +     "<support field=\"responseMessage\"/>\n"
                    +     "<support field=\"customerMessage\"/>\n"
                    +     "<support field=\"timestamp\"/>\n"
                    +     "<support field=\"emailAddress\"/>\n"
                    +     "<support field=\"mySafId\"/>\n"
                    +     "<support field=\"firstName\"/>\n"
                    +     "<support field=\"otherNames\"/>\n"
                    +     "<support field=\"name\"/>\n"
                    +     "<support field=\"status\"/>\n"
                    +     "<support field=\"msisdn\"/>\n"
                    +     "<support field=\"fieldType\"/>\n"
                    +     "<support field=\"body\"/>\n"
                    +     "<support field=\"userName\"/>\n"
                    +     "<support field=\"reason\"/>\n"
                    +     "<support field=\"identificationDocumentValue\"/>\n"
                    +     "<support field=\"blockedUnblockedBy\"/>\n"
                    +     "<support field=\"blockedUnblockedAt\"/>\n"
                    + "<siebel-xmlext-fields-ret>\n";
        }
        if(req.contains("siebel-xmlext-query-req")){
//            logger.info("INCOMING REQUEST ->>>>>>>"+ANSI_GREEN+req);
            Logging.LogToFile("Main", req, Logging.LogType.QUERY,req);
            breakXML(req);
            return req;
        }
        return req;
    }


    public void breakXML(String XML){
//        System.out.println(ANSI_GREEN+XML);
        JSONObject req = new JSONObject();

        JSONObject jsonObject = org.json.XML.toJSONObject(XML.replaceAll("\"?xml version=\"1.0\" encoding=\"UTF-8\"?", ""));
        JSONObject xmlQuery = jsonObject.getJSONObject("siebel-xmlext-query-req");
//        JSONObject busComp = xmlQuery.getJSONObject("buscomp");
        JSONObject searchSpec = xmlQuery.getJSONObject("search-spec");
        JSONObject getNodes = searchSpec.getJSONObject("node");
        JSONArray ja_data = getNodes.getJSONArray("node");
        JSONArray jsonArray = (JSONArray)getNodes.get("node");
        JSONObject jObject4 =(JSONObject)jsonArray.get(0);
        JSONArray data = jObject4.getJSONArray("node");
          // To get email
        JSONObject jObject5 =(JSONObject)data.get(0);
        JSONArray data1 = jObject5.getJSONArray("node");
        JSONObject jObject6 =(JSONObject)data1.get(1);
        String getEmail = jObject6.getString("content");

        // Loop
        for(int i = 0; i < data.length(); i++)
        {
            JSONObject objects = data.getJSONObject(i);
//            System.out.println(ANSI_GREEN+objects.getJSONArray("node").get(0));
            JSONArray json = objects.getJSONArray("node");
            JSONObject objects2 = (JSONObject) json.get(0);
            JSONObject objects3 = (JSONObject) json.get(1);
//            System.out.println(ANSI_GREEN+objects2.getClass().getName());
            JSONObject jo = new JSONObject();

            String names = objects2.getString("content");
            String values = objects3.getString("content");
            jo.put(names,values);
            System.out.println(ANSI_GREEN+names);
            System.out.println(ANSI_BLUE+values);
            System.out.println(ANSI_PURPLE+jo);
        }
//        System.out.println(ANSI_GREEN+data.length());
//        System.out.println(ANSI_GREEN+getEmail);

    }
}
