package org.codinjutsu.tools.jenkins.util;

import org.codinjutsu.tools.jenkins.logic.RssBuildStatusVisitor;
import org.codinjutsu.tools.jenkins.model.BuildStatusEnum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RssUtil {

    private static final Pattern BUILD_NUMBER_MATCHER = Pattern.compile("#[0-9]{1,}+");

    private static final Pattern SUCCESS_MATCHER = Pattern.compile("normal|stable");
    
    private static final Pattern FAILED_MATCHER = Pattern.compile("failing|broken");

    private static final Pattern ABORTED_MATCHER = Pattern.compile("aborted");

    private RssUtil() {
    }


    public static BuildStatusEnum extractStatus(String rssEntryTitle) {
        RssBuildStatusVisitor statusVisitor = new RssBuildStatusVisitor();
        visit(statusVisitor, rssEntryTitle);
        return statusVisitor.getStatus();
    }

    public static String extractBuildNumber(String rssEntryTitle) {
        Matcher matcher = BUILD_NUMBER_MATCHER.matcher(rssEntryTitle);
        if (matcher.find()) {
            String foundBuildNumber = matcher.group();
            return foundBuildNumber.substring(1, foundBuildNumber.length());
        }
        return null;

    }


    public static String extractBuildJob(String rssEntryTitle) {
        String[] splitStrings = BUILD_NUMBER_MATCHER.split(rssEntryTitle);
        if (splitStrings.length > 1) {
            return splitStrings[0].trim();
        }
        return null;
    }


    private static void visit(RssBuildStatusVisitor statusVisitor, String rssEntryTitle) {
        if (matches(rssEntryTitle, SUCCESS_MATCHER)) {
            statusVisitor.visitSuccess();
            return;
        }
        if (matches(rssEntryTitle, FAILED_MATCHER)) {
            statusVisitor.visitFailed();
            return;
        }
        if (matches(rssEntryTitle, ABORTED_MATCHER)) {
            statusVisitor.visitAborted();
            return;
        }

        statusVisitor.visitUnkown();
    }

    private static boolean matches(String rssEntryTitle, Pattern pattern) {
        Matcher matcher = pattern.matcher(rssEntryTitle);
        return matcher.find();
    }

}
