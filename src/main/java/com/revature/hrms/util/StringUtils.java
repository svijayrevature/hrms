package com.revature.hrms.util;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Base64;
import java.util.StringTokenizer;

/**
 * This for the Only String Return method Class
 *
 * @author MUTHU G.K
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final Logger logger = LogManager.getLogger(StringUtils.class);

    private StringUtils() {
    }


    /**
     * @param atleastOneNumberRequired
     * @param atleastOneSmallLetterRequired
     * @param atleastOneCapsLetterRequired
     * @param atleastOneSpecialCharRequired
     * @param noSpaceRequired
     * @param minLength
     * @param maxLength
     * @return REGEX pattern for Validation
     * @author MUTHU G.K
     */
    public static String validationRegularExpression(boolean atleastOneNumberRequired,
                                                     boolean atleastOneSmallLetterRequired, boolean atleastOneCapsLetterRequired,
                                                     boolean atleastOneSpecialCharRequired, boolean noSpaceRequired, int minLength,
                                                     int maxLength) {

        StringBuilder regex = new StringBuilder("(");
        if (atleastOneNumberRequired) {
            regex.append("(?=.*\\d)");
        }
        if (atleastOneSmallLetterRequired) {
            regex.append("(?=.*[a-z])");
        }
        if (atleastOneCapsLetterRequired) {
            regex.append("(?=.*[A-Z])");
        }
        if (atleastOneSpecialCharRequired) {
            regex.append("(?=.*[\\p{Punct}])");
        }
        if (noSpaceRequired) {
            regex.append("(?=\\S+$)");
        }

        regex.append(".{" + minLength + "," + maxLength + "}");
        regex.append(")");

        return regex.toString();
    }

    /**
     * @param value - <b>Input for Encryption</b>
     * @return <b>Encrypted String</b>
     * @author MUTHU G.K
     */
    public static String basic64Encryption(byte[] value) {
        return (value != null && value.length != 0) ? Base64.getEncoder().encodeToString(value) : null;
    }

    /**
     * @param value - <b>Input for Decryption</b>
     * @return <b>Decrypted String</b>
     * @author MUTHU G.K
     */
    public static String basic64Decryption(String value) {
        return isNotBlank(value) ? new String(Base64.getDecoder().decode(value)) : null;
    }

    /**
     * @param value - <b>Input for CamelCase String</b>
     * @return <b>Camel Case String</b>
     * @author MUTHU G.K
     */
    public static String converToCamelCase(String strdata) {

        if (!isValidString(strdata)) {
            return null;
        }

        StringBuilder strbufCamelCase = new StringBuilder();
        StringTokenizer st = new StringTokenizer(strdata);

        st.countTokens();
        while (st.hasMoreTokens()) {
            String strWord = st.nextToken();
            strbufCamelCase.append(strWord.substring(0, 1).toUpperCase());
            if (strWord.length() > 1) {
                strbufCamelCase.append(strWord.substring(1).toLowerCase());
            }
            if (st.hasMoreTokens()) {
                strbufCamelCase.append(" ");
            }
        }
        return strbufCamelCase.toString();
    }

    /**
     * @param value - <b>Input for remove unwanted Space</b>
     * @return <b>Single Spaced String</b>
     * @author MUTHU G.K
     */
    public static String removeMoreThanOneSpaceFromString(String strdata) {

        if (!isValidString(strdata)) {
            return null;
        }

        StringBuilder value = new StringBuilder();
        StringTokenizer st = new StringTokenizer(strdata);

        st.countTokens();
        while (st.hasMoreTokens()) {
            value.append(st.nextToken());
            if (st.hasMoreTokens()) {
                value.append(" ");
            }
        }
        return value.toString();
    }

    public static boolean isNumericString(String str) {
        return NumberUtils.isParsable(str);
    }

    public static boolean isValidString(String str) {
        return isNotBlank(str);
    }

    // ------------------------------------------------------------- For logger

    /**
     * @param msg
     * @param args
     * @return message along with the arguments which we are passing
     * @author MUTHU G.K
     */
    public static String getFormattedMsg(String msg, Object... args) {
        try {
            return String.format(msg, args);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    /**
     * @param msg
     * @param args
     * @return string along with the arguments which we are passing
     * @author MUTHU G.K
     */
    public static String getFormattedString(String msg, Object... args) {
        return getFormattedMsg(msg, args);
    }

}
