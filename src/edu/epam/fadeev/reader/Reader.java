package edu.epam.fadeev.reader;

import edu.epam.fadeev.entity.Airline;
import edu.epam.fadeev.entity.PlaneType;
import edu.epam.fadeev.entity.Weekday;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;

public class Reader {

    private static final Logger logger = LogManager.getLogger(Reader.class);
    private static final String FILE_NAME = "data/test.properties";
    private static final String REGEX = ", ";
    public static final int DESTINATION_INDEX = 0;
    public static final int FLIGHTNUMBER_INDEX = 1;
    public static final int PLANETYPE_INDEX = 2;
    public static final int DEPARTURE_INDEX = 3;
    public static final int WEEKDAYS_INDEX = 4;

    public static List<Airline> read() {
        logger.info(">> start <<");
        List<String[]> lineInfoList = readFromFile();
        List<Airline> out = new ArrayList<>();
        for (int i = 0; i < lineInfoList.size(); i++) {
            out.add(createAirline(lineInfoList.get(i)));
        }
        return  out;
    }

    private static List<String[]> readFromFile() {
        logger.info(">> start read file <<");
        List<String[]> lineInfoList = new ArrayList<>();
        Scanner scanFile = null;
        try {
            scanFile = new Scanner(new File(FILE_NAME));
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        String lineInfo;
        if (scanFile != null) {
            try {
                scanFile.nextLine();
                while (scanFile.hasNextLine()) {
                    lineInfo = scanFile.nextLine();
                    lineInfoList.add(lineInfo.split(REGEX));
                }
            } catch (NullPointerException e) {
                lineInfoList = new ArrayList<>();
                logger.error(e.getMessage(), e);
            }
            scanFile.close();
        }
        logger.info(">> end read file<<");
        return lineInfoList;
    }

    private static Airline createAirline(String[] str) {
        String destination = str[DESTINATION_INDEX];
        int flightNumber = Integer.parseInt(str[FLIGHTNUMBER_INDEX]);
        PlaneType planeType = PlaneType.values()[Integer.parseInt(str[PLANETYPE_INDEX])];
        LocalTime departure =  LocalTime.parse(str[DEPARTURE_INDEX]);
        EnumSet<Weekday> weekdays = EnumSet.noneOf(Weekday.class);
        for (int i = WEEKDAYS_INDEX; i < str.length; i++) {
            weekdays.add(Weekday.values()[Integer.parseInt(str[i])]);
        }
        return new Airline(destination, flightNumber, planeType, departure, weekdays);
    }
}
