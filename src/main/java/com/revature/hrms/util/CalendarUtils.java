package com.revature.hrms.util;

import com.revature.hrms.constants.BusinessConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.revature.hrms.constants.BusinessConstants.DEFAULT_TIME_ZONE;
import static java.sql.Timestamp.valueOf;
import static org.apache.commons.lang3.BooleanUtils.isNotTrue;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class CalendarUtils {

    public static final String DATE_FORMAT = "dd-MMM-yyyy";
    private static Logger logger = LogManager.getLogger(CalendarUtils.class);
    SimpleDateFormat sdf;


    public static CalendarUtils getInstanceOf() {
        return new CalendarUtils();
    }

    // ---------------------------------------------- Most often used method
    private static boolean isValidString(String string) {
        return string != null && string.trim().length() > 0;
    }

    /**
     * Get list of days.
     *
     * @return days
     */
    public static List<String> getAllDays() {
        List<String> days = new ArrayList<>();
        days.add("Sunday");
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");
        return days;

    }

    /**
     * This method used to sort the timezone by value.
     *
     * @param map key and values
     * @return {@link Map}.
     */
    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        return map.entrySet().stream().sorted(Entry.comparingByValue()).collect(Collectors
                .toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    /**
     * This method used to convert UTC date to user time zone.
     *
     * @param utcDate  input valid UTC Date
     * @param timeZone input valid String timeZone
     * @return LocalDateTime
     */
    public static LocalDateTime convertUTCDateToUserTimeZone(LocalDateTime utcDate, String timeZone) {
        logger.info("Converting UTC LocalDateTime to user time zone");
        ZonedDateTime zdt = utcDate.atZone(ZoneId.of(BusinessConstants.DEFAULT_TIME_ZONE));
        return zdt.withZoneSameInstant(ZoneId.of(timeZone)).toLocalDateTime();
    }

    /**
     * This method used to convert user time zone to UTC date.
     *
     * @param userdate input valid UTC Date
     * @param timeZone input valid String timeZone
     * @return LocalDateTime
     */
    public static LocalDateTime convertUserTimeZoneToUTCDate(LocalDateTime userdate,
                                                             String timeZone) {
        logger.info("Converting UTC LocalDateTime to user time zone");
        ZonedDateTime zdt = userdate.atZone(ZoneId.of(timeZone));
        return zdt.withZoneSameInstant(ZoneId.of(BusinessConstants.DEFAULT_TIME_ZONE))
                .toLocalDateTime();
    }

    /**
     * This method used to convert the date into LocalDateTime using the UTC.
     *
     * @param date input valid date
     * @return LocalDateTime
     */

    public static LocalDateTime convertToLocalDateTime(Date date) {
        logger.info("Converting Date to LocalDateTime");
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneOffset.UTC);
    }

    /**
     * This method used to get the Date from LocalDateTime Date using the UTC.
     *
     * @param date input valid LocalDateTime
     * @return Date
     */
    public static Date toUtilDate(LocalDateTime date) {
        logger.info("Converting LocalDateTime to Date");
        return Date.from(date.toInstant(ZoneOffset.UTC));
    }

    /**
     * Used to get the current date and time in utc timezone
     *
     * @return present time in utc format.
     */
    public static Timestamp getNowInUTC() {
        return valueOf(LocalDateTime.now(ZoneId.of(DEFAULT_TIME_ZONE)));
    }

    /**
     * Used to convert the given date in given timezone to specific timezone
     *
     * @param gnDate
     * @param gnDateTimeZone
     * @param convertToTimeZone
     * @return
     */
    public static Optional<Timestamp> convertToSpecificTimeZone(Date gnDate, String gnDateTimeZone,
                                                                String convertToTimeZone) {
        Timestamp dateOut = null;
        try {
            if (gnDate != null && isNotBlank(gnDateTimeZone)) {
                ZonedDateTime z = ZonedDateTime.ofInstant(gnDate.toInstant(), ZoneId.of(gnDateTimeZone));
                z = new Timestamp(gnDate.getTime()).toLocalDateTime()
                        .atZone(ZoneId.of(gnDateTimeZone));
                dateOut = Timestamp
                        .valueOf(z.withZoneSameInstant(ZoneId.of(convertToTimeZone)).toLocalDateTime());
            }
        } catch (Exception e) {
            logger.error("Timezone conversion failed.", e);
        }
        return Optional.ofNullable(dateOut);
    }

    /**
     * Used to convert the given UTC timestamp to given timezone's timestamp.
     *
     * @param timestampUTC timestamp to convert
     * @param timeZone     timezone to convert
     * @return converted timetamp
     */
    public static Optional<Timestamp> convertFromUTC(Timestamp timestampUTC, String timeZone) {
        logger.info("Converting given timestamp to specific timeZone's timestamp");
        Timestamp convertedTimestamp = null;
        try {
            if (timestampUTC != null && isNotBlank(timeZone)) {
                ZonedDateTime in = timestampUTC.toLocalDateTime().atZone(ZoneId.of(DEFAULT_TIME_ZONE));
                convertedTimestamp = valueOf(in.withZoneSameInstant(ZoneId.of(timeZone)).toLocalDateTime());
            }
        } catch (Exception e) {
            logger.error("Timezone conversion failed.", e);
        }
        return Optional.ofNullable(convertedTimestamp);
    }

    /**
     * Used to convert the given LocalDateTime to Timestamp.
     *
     * @param localDateTime input valid LocalDateTime
     * @return converted timetamp
     */
    public static Timestamp toTimestamp(LocalDateTime localDateTime) {
        return (localDateTime == null) ? null : Timestamp.valueOf(localDateTime);
    }

    /**
     * Used to get the current date and time in given timezone with given format
     *
     * @param timeZone timezone for formatting the date time.
     * @param format   date time format.
     * @return current date and time in given timezone with given format
     */
    public static String getNow(String timeZone, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.now(ZoneId.of(timeZone)).format(formatter);
    }

    /**
     * This method used to get the Date from LocalDateTime based on the time zone.
     *
     * @param date     input valid LocalDateTime
     * @param timeZone input vaild timezone String
     * @return util.Date
     */
    public static Date toUtilDate(LocalDateTime date, String timeZone) {
        logger.info("Converting LocalDateTime to Date based on timeZone");
        ZonedDateTime zonedDateTime = date.atZone(ZoneId.of(BusinessConstants.DEFAULT_TIME_ZONE));
        zonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of(timeZone));
        return Timestamp.valueOf(zonedDateTime.toLocalDateTime());
    }

    /**
     * Used to get the UTC time for given timezone's start day and apply the minus of given period
     *
     * @param timezone timezone to convert UTC.
     * @param period   period to minus from localdatetime.
     * @return start day time of given timezone in UTC zone.
     */
    public static LocalDateTime getDayStartOfTimezoneApplyMinusPeriod(String timezone,
                                                                      Period period) {
        return LocalDate.now(ZoneId.of(timezone)).atStartOfDay().minus(period)
                .atZone(ZoneId.of(timezone)).withZoneSameInstant(ZoneId.of(DEFAULT_TIME_ZONE))
                .toLocalDateTime();
    }

    public static LocalDateTime getNowInUTCDateTime() {
        return LocalDateTime.now(ZoneId.of(DEFAULT_TIME_ZONE));
    }

    /**
     * This method is used to convert any timezone from UTC
     *
     * @param input
     * @param timeZone
     * @return Date
     */

    public static Date convertTimeZone(Date input, String timeZone) {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        DateFormat df = new SimpleDateFormat(dateFormat);
        String date = df.format(input);
        LocalDateTime localTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(dateFormat));
        ZoneId utcTime = ZoneId.of("UTC");
        ZonedDateTime asiaZonedDateTime = localTime.atZone(utcTime);
        ZoneId convertZone = ZoneId.of(timeZone);
        ZonedDateTime convertedZone = asiaZonedDateTime.withZoneSameInstant(convertZone);
        Date convertedDate = Date.from(convertedZone.toInstant());
        return convertedDate;
    }

    /**
     * This is to get all the date of a particular day in a year
     *
     * @param dayOfWeek
     * @param year
     * @return List<Date>
     */

    public static List<Date> getDateforPaticularDay(Integer dayOfWeek, Integer year) {
        List<Date> dates = new ArrayList<>();
        Calendar cal = new GregorianCalendar();
        cal.set(year, 0, 1, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        while (cal.get(Calendar.YEAR) == year) {
            dates.add(cal.getTime());
            cal.add(Calendar.DAY_OF_MONTH, 7);
        }
        return dates;
    }

    /**
     * This is to day number in a week
     *
     * @param day
     * @return List<Date>
     */
    public static Integer getDayNo(String day) {
        Integer dayNo = null;
        if (day.equalsIgnoreCase("Sunday")) {
            dayNo = 1;
        } else if (day.equalsIgnoreCase("Monday")) {
            dayNo = 2;
        } else if (day.equalsIgnoreCase("Tuesday")) {
            dayNo = 3;
        } else if (day.equalsIgnoreCase("Wednesday")) {
            dayNo = 4;
        } else if (day.equalsIgnoreCase("Thursday")) {
            dayNo = 5;
        } else if (day.equalsIgnoreCase("Friday")) {
            dayNo = 6;
        } else if (day.equalsIgnoreCase("Saturday")) {
            dayNo = 7;
        }
        return dayNo;
    }

    public static Timestamp getEndDate(Timestamp st, int duration, Boolean isAllSevenDays,
                                       String workingDays, int workTimeLength, LocalTime wst) {
        LocalTime wet = wst.plusMinutes(workTimeLength);
        LocalDateTime et = st.toLocalDateTime();
        long remaingHrs = duration;
        while (true) {
            long d = Duration.between(et.toLocalTime(), wet).abs().toMinutes();
            if (remaingHrs <= d) {
                et = et.plusMinutes(remaingHrs);
                break;
            } else if (remaingHrs > d) {
                remaingHrs = remaingHrs - d;
                et = et.plusDays(1).with(wst);
                et = getNextWorkingDay(et, isAllSevenDays, workingDays, wst);
            }
        }
        return Timestamp.valueOf(et);
    }

    public static LocalDateTime getNextWorkingDay(LocalDateTime gn, Boolean isAllSevenDays,
                                                  String workingDays, LocalTime wst) {
        LocalDateTime et = gn;
        if (isNotTrue(isAllSevenDays)) {
            while (true) {
                if (workingDays.contains(et.getDayOfWeek().toString())) {
                    break;
                }
                et = et.plusDays(1).with(wst);
            }
        }
        return et;
    }

    public static Timestamp getStartDate(Timestamp st, Boolean isAllSevenDays, String workingDays,
                                         int workTimeLength, LocalTime wst) {
        LocalDateTime stime = st.toLocalDateTime();
        stime = getNextWorkingDay(stime, isAllSevenDays, workingDays, wst);
        long d = Duration.between(stime.toLocalTime(), wst).toMinutes();
        if (d == 0) {
            return Timestamp.valueOf(stime);
        }
        LocalTime wet = wst.plusMinutes(workTimeLength);
        d = Duration.between(stime.toLocalTime(), wet).toMinutes();
        if (d <= 0) {
            stime = stime.plusDays(1).with(wst);
            stime = getNextWorkingDay(stime, isAllSevenDays, workingDays, wst);
        }
        return Timestamp.valueOf(stime);
    }

    /**
     * Converts the given LocalDateTime to specific Time zone Timestamp
     *
     * @param date
     * @param timeZone
     * @return Timestamp
     */
    public static Timestamp toUtilTimestamp(LocalDateTime date, String timeZone) {
        logger.info("Converting LocalDateTime to Timestamp based on timeZone");
        ZonedDateTime zonedDateTime = date.atZone(ZoneId.of(BusinessConstants.DEFAULT_TIME_ZONE));
        zonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of(timeZone));
        return toTimestamp(zonedDateTime.toLocalDateTime());
    }

    /**
     * To convert localDateTime to date
     *
     * @param localDateTime
     * @return {@link String}
     */
    public static String localDateToUtilDate(LocalDateTime localDateTime) {
        logger.info("Converting LocalDateTime to Date");

        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date dateFromOld = Date.from(instant);

        String dateFormat = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(dateFormat);

        logger.info("Converted successfully LocalDateTime to Date");
        return df.format(dateFromOld);
    }

    /**
     * Get days of previous week.
     *
     * @param refDate        refDate
     * @param firstDayOfWeek firstDayOfWeek
     * @return daysOfWeek
     */
    public Date[] getDaysOfPreviousWeek(Date refDate, int firstDayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar = getDailyTrackerPreWeekStartDate();
        Date[] daysOfWeek = new Date[7];
        for (int i = 0; i < 7; i++) {
            daysOfWeek[i] = calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return daysOfWeek;
    }

    /**
     * Get days of week.
     *
     * @param refDate        refDate
     * @param firstDayOfWeek firstDayOfWeek
     * @return daysOfWeek
     */
    public Date[] getDaysOfWeek(Date refDate, int firstDayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(refDate);
        Date[] daysOfWeek = new Date[7];

        for (int i = 0; i < 7; i++) {
            daysOfWeek[i] = calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return daysOfWeek;
    }

    /**
     * Get daily trackers start date.
     *
     * @return startDate
     */
    public Calendar getDailyTrackerStartDate() {
        Calendar today = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        int datecount = today.get(Calendar.DAY_OF_WEEK);
        if (datecount == 1) {
            datecount = 8;
        }
        startDate.add(Calendar.DATE, (2 - datecount));
        return startDate;
    }

    /**
     * Get daily tracker preweek start date.
     *
     * @return daysOfWeek
     */
    public Calendar getDailyTrackerPreWeekStartDate() {
        Calendar today = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        int datecount = today.get(Calendar.DAY_OF_WEEK);

        if (datecount == 1) {
            datecount = datecount + 14;
        } else {
            datecount = datecount + 7;
        }
        startDate.add(Calendar.DATE, (2 - datecount));
        return startDate;
    }

    /**
     * Get days of week.
     *
     * @param utilDate utilDate
     * @return retustr
     */
    public String utilDateToStr(Date utilDate) {
        String retustr = null;
        try {
            DateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            retustr = sdf.format(utilDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return retustr;
    }

    /**
     * It will convert String to sql date.
     *
     * @param strDate strDate
     * @return retuDate
     */
    public java.sql.Date strToSQLDate(String strDate) {
        java.sql.Date retuDate = null;
        try {
            DateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            sdf.setLenient(false);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(strDate));
            retuDate = new java.sql.Date(cal.getTime().getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return retuDate;
    }

    /**
     * Get days between dates.
     *
     * @param startDate startDate
     * @param endDate   endDate
     * @return dates
     */
    public List<Date> getDaysBtwnDates(Date startDate, Date endDate) {
        List<Date> dates = new ArrayList<>();
        Calendar startDateCalendar = Calendar.getInstance();
        startDateCalendar.setTime(startDate);
        while (endDate.after(startDateCalendar.getTime())
                || new SimpleDateFormat(DATE_FORMAT).format(endDate).equalsIgnoreCase(
                new SimpleDateFormat(DATE_FORMAT).format(startDateCalendar.getTime()))) {
            dates.add(startDateCalendar.getTime());
            startDateCalendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    /**
     * It used to know sunday or staturday.
     *
     * @param givenDate givenDate
     * @return result
     */
    public boolean isSundayOrStaturDay(Date givenDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(givenDate);
        boolean result = false;
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            result = true;
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            result = true;
        }
        return result;
    }

    /**
     * Get list of year.
     *
     * @return yearList
     */
    public List<Long> getAllYearList() {
        long currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Long> yearList = new ArrayList<>();
        yearList.add(currentYear - 1);
        yearList.add(currentYear);
        yearList.add(currentYear + 1);
        return yearList;

    }

    /**
     * Get list of month.
     *
     * @return monthList
     */
    public List<String> getAllMonthList() {
        List<String> monthList = new ArrayList<>();
        monthList.add("Jan");
        monthList.add("Feb");
        monthList.add("Mar");
        monthList.add("Apr");
        monthList.add("May");
        monthList.add("Jun");
        monthList.add("Jul");
        monthList.add("Aug");
        monthList.add("Sep");
        monthList.add("Oct");
        monthList.add("Nov");
        monthList.add("Dec");
        return monthList;

    }

    /**
     * Get month.
     *
     * @param monthName monthName
     * @return month
     */
    public int getMonths(String monthName) {
        int month = 0;
        if (monthName.equalsIgnoreCase("Jan")) {
            month = 1;
        } else if (monthName.equalsIgnoreCase("Feb")) {
            month = 2;
        } else if (monthName.equalsIgnoreCase("Mar")) {
            month = 3;
        } else if (monthName.equalsIgnoreCase("Apr")) {
            month = 4;
        } else if (monthName.equalsIgnoreCase("May")) {
            month = 5;
        } else if (monthName.equalsIgnoreCase("Jun")) {
            month = 6;
        } else if (monthName.equalsIgnoreCase("Jul")) {
            month = 7;
        } else if (monthName.equalsIgnoreCase("Aug")) {
            month = 8;
        } else if (monthName.equalsIgnoreCase("Sep")) {
            month = 9;
        } else if (monthName.equalsIgnoreCase("Oct")) {
            month = 10;
        } else if (monthName.equalsIgnoreCase("Nov")) {
            month = 11;
        } else if (monthName.equalsIgnoreCase("Dec")) {
            month = 12;
        }

        return month;

    }

    /**
     * Get month.
     *
     * @param month month
     * @return month
     */
    public String getMonths(int month) {
        String monthName = null;
        if (month == 0) {
            monthName = "Jan";
        } else if (month == 1) {
            monthName = "Feb";
        } else if (month == 2) {
            monthName = "Mar";
        } else if (month == 3) {
            monthName = "Apr";
        } else if (month == 4) {
            monthName = "May";
        } else if (month == 5) {
            monthName = "Jun";
        } else if (month == 6) {
            monthName = "Jul";
        } else if (month == 7) {
            monthName = "Aug";
        } else if (month == 8) {
            monthName = "Sep";
        } else if (month == 9) {
            monthName = "Oct";
        } else if (month == 10) {
            monthName = "Nov";
        } else if (month == 11) {
            monthName = "Dec";
        }

        return monthName;

    }

    /**
     * Get before date.
     *
     * @param givenDate givenDate
     * @param days      days
     * @return date
     */
    public Date getBeforeDate(Date givenDate, int days) {
        Date date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(givenDate);
        calendar.add(Calendar.DAY_OF_YEAR, -days);
        date = calendar.getTime();
        return date;
    }

    /**
     * Get after date.
     *
     * @param givenDate givenDate
     * @param days      days
     * @return date
     */
    public Date getAfterDate(Date givenDate, int days) {
        Date date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(givenDate);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        date = calendar.getTime();
        return date;
    }

    /**
     * check date is available.
     *
     * @param givenDate givenDate
     * @param startDate startDate
     * @param endDate   endDate
     * @return result
     */
    public boolean isDateIsAvailabel(Date startDate, Date endDate, Date givenDate) {
        boolean result = false;
        if (givenDate.after(startDate) && givenDate.before(endDate)) {
            result = true;
        }
        if (givenDate.equals(startDate) || givenDate.equals(endDate)) {
            result = true;
        }
        return result;
    }

    /**
     * check date is available.
     *
     * @param givenDate givenDate
     * @param startDate startDate
     * @return weekNo
     */
    public Integer getWeekByStartDate(Date startDate, Date givenDate) {
        Integer weekNo = null;
        if (givenDate != null) {
            int noOfDays = getNoOfDays(getFirstDateOfTheWeek(startDate), givenDate);
            if (noOfDays < 7) {
                weekNo = 1;
            } else {
                double temp = 0;
                temp = noOfDays / 7.0;
                weekNo = (int) Math.ceil(temp);
            }
        }
        return weekNo;
    }

    /**
     * Get week start date.
     *
     * @param givenDate givenDate
     * @param startDate startDate
     * @return weekNo
     */
    public Integer getByWeeklyByStartDate(Date startDate, Date givenDate) {
        Integer weekNo = null;
        if (givenDate != null) {
            int noOfDays = getNoOfDays(getFirstDateOfTheWeek(startDate), givenDate);
            if (noOfDays < 14) {
                weekNo = 2;
            } else {
                double temp = 0;
                temp = noOfDays / 14.0;
                weekNo = (int) Math.ceil(temp);
                if (weekNo % 2 != 0) {
                    weekNo = weekNo + 1;
                }
            }
        }
        return weekNo;
    }

    /**
     * Get month start date.
     *
     * @param givenDate givenDate
     * @param startDate startDate
     * @return weekNo
     */
    public Integer getMonthByStartDate(Date startDate, Date givenDate) {
        Integer monthNo = null;
        if (givenDate != null) {
            Calendar startDateTmp = Calendar.getInstance();
            startDateTmp.setTime(startDate);
            Calendar endDateTmp = Calendar.getInstance();
            endDateTmp.setTime(givenDate);
            int diff = 0;
            if (endDateTmp.after(startDateTmp)) {
                while (endDateTmp.after(startDateTmp)) {
                    startDateTmp.add(Calendar.MONTH, 1);
                    if (endDateTmp.after(startDateTmp)) {
                        diff++;
                    }
                }
            } else if (endDateTmp.before(startDateTmp)) {
                while (endDateTmp.before(startDateTmp)) {
                    startDateTmp.add(Calendar.MONTH, -1);
                    if (startDateTmp.before(endDateTmp)) {
                        diff--;
                    }
                }
            }
            if (diff < 1) {
                monthNo = 1;
            } else {
                monthNo = diff;
            }
        }
        return monthNo;
    }

    public int getNoOfDays(Date startDate, Date endDate) {
        int noOfDays = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
        return noOfDays;
    }

    /**
     * Get first date fo the week.
     *
     * @param givenDate givenDate
     * @return result
     */
    public Date getFirstDateOfTheWeek(Date givenDate) {
        Date result;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(givenDate);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        result = calendar.getTime();
        return result;
    }

    /**
     * Get end date fo the week.
     *
     * @param givenDate givenDate
     * @return result
     */
    public Date getEndDateOfTheWeek(Date givenDate) {
        Date result;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(givenDate);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        result = calendar.getTime();
        return result;
    }

    /**
     * Get month list between two dates.
     *
     * @param startDate startDate
     * @param endDate   endDate
     * @return monthList
     */
    public List<String> getMonthListBetweenTwoDates(Date startDate, Date endDate) {
        List<String> monthList = new ArrayList<>();
        Calendar startDateTmp = Calendar.getInstance();
        startDateTmp.setTime(startDate);
        Calendar endDateTmp = Calendar.getInstance();
        endDateTmp.setTime(endDate);
        while (startDateTmp.before(endDateTmp)) {
            monthList.add(new SimpleDateFormat("MMM-yyyy").format(startDateTmp.getTime()));
            startDateTmp.add(Calendar.MONTH, 1);
        }
        return monthList;
    }

    /**
     * Get month list between two dates.
     *
     * @param date      date
     * @param dateStart dateStart
     * @param dateEnd   dateEnd
     * @return result
     */
    public boolean between(Date date, Date dateStart, Date dateEnd) {
        boolean result = false;
        if (date != null && dateStart != null && dateEnd != null) {
            result = date.after(dateStart) && date.before(dateEnd);
        }
        return result;
    }

    /**
     * Get month from month number.
     *
     * @param monthNumber monthNumber
     * @param ifShort     ifShort
     * @return result
     */
    public String getMonthFromMonthNumber(Integer monthNumber, boolean ifShort) {
        String result = "";
        int monthNumberTmp = 0;
        if (monthNumber != null) {
            monthNumberTmp = monthNumber;
        }
        if (monthNumberTmp == 1) {
            if (ifShort) {
                result = "Jan";
            } else {
                result = "January";
            }
        } else if (monthNumberTmp == 2) {
            if (ifShort) {
                result = "Feb";
            } else {
                result = "February";
            }
        } else if (monthNumberTmp == 3) {
            if (ifShort) {
                result = "Mar";
            } else {
                result = "March";
            }
        } else if (monthNumberTmp == 4) {
            if (ifShort) {
                result = "Apr";
            } else {
                result = "April";
            }
        } else if (monthNumberTmp == 5) {
            if (ifShort) {
                result = "May";
            } else {
                result = "May";
            }
        } else if (monthNumberTmp == 6) {
            if (ifShort) {
                result = "Jun";
            } else {
                result = "June";
            }
        } else if (monthNumberTmp == 7) {
            if (ifShort) {
                result = "Jul";
            } else {
                result = "July";
            }
        } else if (monthNumberTmp == 8) {
            if (ifShort) {
                result = "Aug";
            } else {
                result = "August";
            }
        } else if (monthNumberTmp == 9) {
            if (ifShort) {
                result = "Sep";
            } else {
                result = "September";
            }
        } else if (monthNumberTmp == 10) {
            if (ifShort) {
                result = "Oct";
            } else {
                result = "October";
            }
        } else if (monthNumberTmp == 11) {
            if (ifShort) {
                result = "Nov";
            } else {
                result = "November";
            }
        } else if (monthNumberTmp == 12) {
            if (ifShort) {
                result = "Dec";
            } else {
                result = "December";
            }
        }
        return result;
    }

    /**
     * Get the converted date from String to Date.
     *
     * @param stringDate input valid String Date
     * @param format     input valid String Format
     * @return date
     */
    public Date stringToDate(String stringDate, String format) {
        Date date = null;
        if (isValidString(stringDate) && isValidString(format)) {
            try {
                sdf = new SimpleDateFormat(format);
                date = sdf.parse(stringDate);
            } catch (ParseException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return date;
    }

    public List<String> getMajorTimeZonesOfUSAndIndia() {
        List<String> timeZones = new ArrayList<>();
        timeZones.add("US/Alaska");
        timeZones.add("America/New_York");
        timeZones.add("US/Central");
        timeZones.add("US/Hawaii");
        timeZones.add("US/Mountain");
        timeZones.add("US/Pacific");
        timeZones.add("Asia/Kolkata");
        return timeZones;
    }

    /**
     * Get All avaliable Timezones.
     *
     * @return list of time zones with GMT
     */
    public List<String> getAllTimeZones() {
        logger.info("Populating the all timezones");
        Map<String, Long> unSortedTimeZones = new HashMap<>();
        Set<String> timeZoneIds = ZoneId.getAvailableZoneIds();
        for (String timeZoneId : timeZoneIds) {
            if (!timeZoneId.contains("SystemV")) {
                TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
                long hours = TimeUnit.MILLISECONDS.toHours(timeZone.getRawOffset());
                long minutes = TimeUnit.MILLISECONDS.toMinutes(timeZone.getRawOffset())
                        - TimeUnit.HOURS.toMinutes(hours);
                minutes = Math.abs(minutes);
                String timeZoneStr;
                String zoneFormat = Optional.ofNullable(hours).filter(hr -> hr > 0)
                        .map(zone -> "(GMT +%d:%02d) %s").orElse("(GMT %d:%02d) %s");
                timeZoneStr = String.format(zoneFormat, hours, minutes, timeZone.getID());
                unSortedTimeZones.put(timeZoneStr, hours);
            }
        }
        Map<String, Long> sortedMap = sortByValue(unSortedTimeZones);
        return sortedMap.entrySet().stream().map(Entry::getKey).collect(Collectors.toList());
    }

    /**
     * This method used to convert string date to LocalDateTime.
     *
     * @param stringDate input valid String Date
     * @param format     input valid String Format
     * @return date
     */
    public Optional<LocalDateTime> convertStringDateToLocalDate(String stringDate, String format) {
        LocalDateTime date = null;
        logger.info("Converting string date to LocalDateTime - START");
        if (stringDate != null && stringDate.trim().length() > 0 && format != null
                && format.trim().length() > 0) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            date = LocalDateTime.parse(stringDate, formatter);
        }
        logger.info("Converting string date to LocalDateTime - END");
        return Optional.ofNullable(date);
    }

    /**
     * This method used to convert string date to LocalDateTime using Locale information.
     *
     * @param stringDate input valid String Date
     * @param format     input valid String Format
     * @param locale     input valid Locale
     * @return date
     */
    public Optional<LocalDateTime> convertStringDateToLocalDateWithLocale(String stringDate,
                                                                          String format, Locale locale) {
        LocalDateTime date = null;
        logger.info("Converting string date to LocalDateTime - START");
        if (!StringUtils.isBlank(stringDate) && !StringUtils.isBlank(format)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, locale);
            date = LocalDateTime.parse(stringDate, formatter);
        }
        logger.info("Converting string date to LocalDateTime - END");
        return Optional.ofNullable(date);
    }

    /**
     * This method used to convert UTC date string to user time zone.
     *
     * @param utcDate  input valid UTC Date
     * @param timeZone input valid String timeZone
     * @return LocalDateTime
     */
    public LocalDateTime convertUTCDateToUserTimeZone(String utcDate, String timeZone) {
        logger.info("Converting string UTC date to user time zone");
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(BusinessConstants.DEFAULT_DATE_FORMAT);
        LocalDateTime ldt = LocalDateTime.parse(utcDate, formatter);
        ZonedDateTime zdt = ldt.atZone(ZoneId.of(BusinessConstants.DEFAULT_TIME_ZONE));
        return zdt.withZoneSameInstant(ZoneId.of(timeZone)).toLocalDateTime();
    }

    /**
     * This method used to convert the UTC LocalDateTime from the user time zone.
     *
     * @param date     input valid date
     * @param timeZone input valid String timeZone
     * @return LocalDateTime
     */
    public LocalDateTime convertToUTCDate(LocalDateTime date, String timeZone) {
        logger.info("Converting UTC LocalDateTime to user time zone");
        ZonedDateTime zdt = date.atZone(ZoneId.of(timeZone));
        return zdt.withZoneSameInstant(ZoneId.of(BusinessConstants.DEFAULT_TIME_ZONE))
                .toLocalDateTime();
    }

    /**
     * This is for get the date as a string, as a given format
     *
     * @param date
     * @param format
     * @return
     */
    public String dateToString(Date date, String format) {
        String formatedDate = null;
        if (date != null && isValidString(format)) {
            try {
                formatedDate = new SimpleDateFormat(format).format(date);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return formatedDate;
    }

    public static Timestamp resetTimestampTime(Timestamp timestamp) {
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTimeInMillis(timestamp.getTime());                           // set cal to date
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);
        timestamp = new Timestamp(cal.getTimeInMillis());
        return timestamp;
    }

    /**
     * Returns Calender date for the date string
     *
     * @param today - date
     * @param month - month
     * @param year  - year
     * @return calendar - calendar Date
     */
    public Calendar getCalenderDate(Integer today, String month, Integer year) {

        if (month == null || year == null)
            return null;

        HashMap<String, Integer> map = new HashMap<>();
        map.put("January", 1);
        map.put("February", 2);
        map.put("March", 3);
        map.put("April", 4);
        map.put("May", 5);
        map.put("June", 6);
        map.put("July", 7);
        map.put("August", 8);
        map.put("September", 9);
        map.put("October", 10);
        map.put("November", 11);
        map.put("December", 12);

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String dateInString = map.get(month) + "/" + today + "/" + year;
        Date newDate = null;
        try {
            newDate = formatter.parse(dateInString);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }

        Calendar calendar = Calendar.getInstance();
        if (newDate != null) {
            calendar.setTime(newDate);
        }
        return calendar;
    }

    public static Timestamp addFieldToTimestamp(Timestamp timestamp, int type, Integer amount) {
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTimeInMillis(timestamp.getTime());                           // set cal to date
        cal.add(type, amount);
        timestamp = new Timestamp(cal.getTimeInMillis());
        return timestamp;
    }

    /**
     * This method is used to get zone id from Sting
     *
     * @param day
     * @return List<Date>
     */
    public String getZoneID(String day) {
        String zones = "";
        if (day.equalsIgnoreCase("AKST")) {
            zones = "US/Alaska";
        } else if (day.equalsIgnoreCase("EST")) {
            zones = "America/New_York";
        } else if (day.equalsIgnoreCase("CST")) {
            zones = "US/Central";
        } else if (day.equalsIgnoreCase("HAST")) {
            zones = "US/Hawaii";
        } else if (day.equalsIgnoreCase("MST")) {
            zones = "US/Mountain";
        } else if (day.equalsIgnoreCase("PST")) {
            zones = "US/Pacific";
        } else if (day.equalsIgnoreCase("IST")) {
            zones = "Asia/Kolkata";
        }
        return zones;
    }

    public static Timestamp convertStringDateToTimestampForFormat(String date, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return new Timestamp(dateFormat.parse(date).getTime());

    }
}
