package employees;

import exceptions.IncorrectDateFormatException;
import exceptions.WrongOrderOfDatesException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimePeriod {

    private Date dateFrom;

    private Date dateTo;

    public TimePeriod(String from, String to) throws WrongOrderOfDatesException, IncorrectDateFormatException, ParseException {
        this.setFrom(from);
        this.setTo(to);
    }

    private void setFrom(String from) throws  IncorrectDateFormatException, ParseException {
        this.dateFrom = this.convertToDate(from);
    }

    private void setTo(String to) throws ParseException, WrongOrderOfDatesException, IncorrectDateFormatException {
        Date date = this.convertToDate(to);

        // check if dates are in right order
        if(this.dateFrom.compareTo(date) < 0) {
            this.dateTo = date;
        }
        else {
            throw new WrongOrderOfDatesException();
        }
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    private Date convertToDate(String date) throws ParseException, IncorrectDateFormatException {

        if(date == null || date.equals("")) {
            throw new IncorrectDateFormatException();
        }

        // 'NULL' is today - return today
        if(date.equals("NULL")) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            // return current date
            return simpleDateFormat.parse( simpleDateFormat.format(new Date()) );
        }

        // date delimiter
        char delimiter = this.getDateDelimiter(date);

        // invalid date
        if(delimiter == 0) {
            throw new IncorrectDateFormatException();
        }

        SimpleDateFormat simpleDateFormat = null;

        if(isYearFirst(date)) {
            simpleDateFormat = new SimpleDateFormat("yyyy" + delimiter + "MM" + delimiter + "dd");
        }
        else {
            if(isYearLast(date)) {
                simpleDateFormat = new SimpleDateFormat("dd" + delimiter + "MM" + delimiter + "yyyy");
            }
            else {
                throw new IncorrectDateFormatException();
            }
        }

        return simpleDateFormat.parse(date);
    }

    private char getDateDelimiter(String str) {

        // regular expression that matches every char but digit
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(str);

        char delimiter = 0;

        // loop through every delimiter
        //if there are at least 2 different -> incorrect date
        while(matcher.find()) {
            String match = matcher.group(0);
            char tempChar = match.charAt(0);

            if(delimiter != 0 && delimiter != tempChar) {
                return 0;
            }

            delimiter = match.charAt(0);
        }

        return delimiter;
    }

    private boolean isYearFirst(String date) {
        // check if date starts with 4 digits
        Pattern pattern = Pattern.compile("^[0-9]{4}");
        Matcher matcher = pattern.matcher(date);

        return matcher.find();
    }

    private boolean isYearLast(String date) {
        // check if date ends with 4 digits
        Pattern pattern = Pattern.compile("[0-9]{4}$");
        Matcher matcher = pattern.matcher(date);

        return matcher.find();
    }

    public static int calculateParallelDays(TimePeriod firstInterval, TimePeriod secondInterval) {

        Date firstStart = firstInterval.dateFrom;
        Date firstFinish = firstInterval.dateTo;

        Date secondStart = secondInterval.dateFrom;
        Date secondFinish = secondInterval.dateTo;

        if( (firstFinish.compareTo(secondStart) < 0) || (secondFinish.compareTo(firstStart) < 0) ) {
            return 0;
        }

        if(firstStart.compareTo(secondStart) == 0) {

            if(firstFinish.compareTo(secondFinish) > 0) {
                return (int) TimePeriod.daysBetween(secondStart, secondFinish);
            }
            return (int) TimePeriod.daysBetween(firstStart, firstFinish);
        }

        if(firstFinish.compareTo(secondFinish) == 0) {

            if(firstStart.compareTo(secondStart) < 0) {
                return (int) TimePeriod.daysBetween(secondStart, secondFinish);
            }
            return (int) TimePeriod.daysBetween(firstStart, firstFinish);
        }

        if(firstStart.compareTo(secondStart) < 0) {

            if(secondFinish.compareTo(firstFinish) < 0) {
                return (int) TimePeriod.daysBetween(secondStart, secondFinish);
            }
            return (int) TimePeriod.daysBetween(secondStart, firstFinish);
        }

        if(firstFinish.compareTo(secondFinish) < 0) {
            return (int) TimePeriod.daysBetween(firstStart, firstFinish);
        }

        return (int) TimePeriod.daysBetween(firstStart, secondFinish);
    }

    //in use only if dates are in right order - no need to check
    private static int daysBetween(Date date1, Date date2) {

        long timeInMillis = date2.getTime() - date1.getTime() + TimeUnit.DAYS.toMillis(1);

        return (int) TimeUnit.DAYS.convert(timeInMillis, TimeUnit.MILLISECONDS);
    }
}

