package com.crmbackendtests.log;

import com.crmbackendtests.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author BARNABA
 * @created 13/11/2023 - 11:13 AM
 * @project crmbackendtests
 */
@Component
@Slf4j
public class Logging {
    private static final Log logger = LogFactory.getLog(Logging.class);
    @Autowired
    private AppConfig appConfig;

    public Logging(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public enum LogType {
        ERRORS,
        APIREQUESTS,
        RESPONSES,
        INIT,
        QUERY
    }
    private static final DateTimeFormatter LogDateFormatter = DateTimeFormatter.ofPattern("ddMMyyyy", Locale.ENGLISH);
    public static void LogToFile(String Method, String Request, LogType logtype, String requestType) {

        try {

            LocalDateTime now = LocalDateTime.now();

            //String PATH = SwitchLinkController.getSettingValue("log_path", "API");
            String PATH = "/home/datadrive/logs/";

            String directoryName = PATH.concat(logtype.name());

            String fileName = LogDateFormatter.format(now) + ".log";

            Request = Request.concat(" :Method " + Method + "\n");

            File directory = new File(directoryName);
            if (!directory.exists()) {
                directory.mkdirs();
                // If you require it to make the entire directory path including parents,
                // use directory.mkdirs(); here instead.else mkdir
            }

            File file = new File(directoryName + "/" + fileName);

            logger.trace("About to log to this directory >|" + directoryName + "/" + fileName);

            if (!file.exists()) {
                System.out.println("File doesn't exist..About to create file");
                file.createNewFile();
            }

            switch (requestType.toUpperCase()) {
                case "POST":
                case "ERRORS":
                    FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter pw = new PrintWriter(bw);
                    pw.write("\n");
                    pw.write(Method + "|" + now + "|" + Request);
                    pw.close();
                    bw.close();
                    break;
                case "GET":

                default:
                    fw = new FileWriter(file.getAbsoluteFile(), true);
                    bw = new BufferedWriter(fw);
                    pw = new PrintWriter(bw);
                    pw.write("\n");
                    pw.write(Method + "|" + now + "|" + Request);
                    pw.close();
                    bw.close();

                    break;
            }

//
        } catch (IOException e) {
            e.printStackTrace();
            //System.exit(-1);
        }
    }
}
