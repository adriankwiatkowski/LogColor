package com.example.logcolor.printers.utils;

import com.example.logcolor.color.models.AnsiColor;

public class HtmlUtils {

    private HtmlUtils() {
    }

    public static String buildHtmlFromAnsi(String text) {
        // Replace new lines with equivalent <br> tag in html.
        text = text.replace("\n", "<br>");

        StringBuilder sb = new StringBuilder("<html><body><pre>" + text);
        replaceAnsiWithHtml(sb);
        sb.append("</pre></body></html>");

        return sb.toString();
    }

    private static void replaceAnsiWithHtml(StringBuilder sb) {
        // Replace ansi reset with closing font tag.
        String ansiReset = AnsiColor.ANSI_RESET.getAnsi();
        int ansiResetLength = ansiReset.length();
        int indexOfAnsiReset;
        while ((indexOfAnsiReset = sb.indexOf(ansiReset)) != -1) {
            sb.replace(indexOfAnsiReset, indexOfAnsiReset + ansiResetLength, "</font>");
        }

        for (AnsiColor ansiColor : AnsiColor.FOREGROUNDS) {
            int index;
            String from = ansiColor.getAnsi();
            while ((index = sb.indexOf(from)) != -1) {
                sb.replace(index,
                           index + from.length(),
                           "<font style=\"color:" + ansiColor.getHex() + ";\">");
            }
        }

        for (AnsiColor ansiColor : AnsiColor.BACKGROUNDS) {
            int indexTagBg;
            String from = ansiColor.getAnsi();
            while ((indexTagBg = sb.indexOf(from)) != -1) {
                int replaceStartIndex = indexTagBg;
                int replaceEndIndex = indexTagBg + from.length();
                String replaceText =
                        "<font style=\"background-color:" + ansiColor.getHex() + ";\">";

                int indexTagEnd = getEndIndex(sb, "</font>", indexTagBg);
                int indexTagStart = getStartIndex(sb, "<font", indexTagEnd, indexTagBg);

                if (isBetweenTags(sb, indexTagStart, "</font>", indexTagEnd, indexTagBg)) {
                    // If foreground color was applied and not closed.
                    // If in middle of foreground color.
                    String colorOfForeground = getColorHtml(sb, indexTagStart);

                    replaceText = "</font><font style=\"color:" + colorOfForeground +
                                  ";background-color:" + ansiColor.getHex() + ";\">";
                } else if (isBeforeTag(sb, indexTagStart, indexTagEnd, indexTagBg)) {
                    // If before foreground color.
                    String colorOfForeground = getColorHtml(sb, indexTagStart);

                    int fgTagLength = sb.indexOf(">", indexTagStart) - indexTagStart + 1;
                    sb.delete(indexTagStart, indexTagStart + fgTagLength);
                    sb.insert(indexTagStart,
                              "<font style=\"color:" + colorOfForeground + ";background-color:" +
                              ansiColor.getHex() + ";\">");
                    replaceText = "";
                }
                // Don't do anything if foreground color was applied and closed
                // or if foreground color was not applied at all.

                sb.replace(replaceStartIndex, replaceEndIndex, replaceText);
            }
        }

        removeExtraClosingTags(sb);
    }

    private static void removeExtraClosingTags(StringBuilder sb) {
        int fontOpenTagCount = 0;
        for (int index = sb.indexOf("<font"); index != -1; index = sb.indexOf("<font", index + 1)) {
            ++fontOpenTagCount;
        }
        int fontCloseTagCount = 0;
        for (int index = sb.indexOf("</font>");
             index != -1; index = sb.indexOf("</font>", index + 1)) {
            ++fontCloseTagCount;
        }

        int diff = fontCloseTagCount - fontOpenTagCount;
        while (diff-- > 0) {
            int lastIndexOf = sb.lastIndexOf("</font>");
            int lastIndexOfEnd = lastIndexOf + "</font>".length();
            sb.delete(lastIndexOf, lastIndexOfEnd);
        }
    }

    private static boolean isBetweenTags(StringBuilder sb,
                                         int indexTagStart,
                                         String tagEnd,
                                         int indexTagEnd,
                                         int indexTag) {

        if (indexTagEnd == -1) {
            return false;
        }

        if (indexTagStart == -1) {
            return false;
        }


        int index = indexTagStart;
        int temp = index;
        while ((temp = sb.indexOf(tagEnd, temp + 1)) != -1 && temp < indexTagEnd) {
            index = temp;
        }

        return indexTagStart < indexTag && indexTagStart < indexTagEnd && indexTag < indexTagEnd &&
               index > indexTag;
    }

    private static boolean isBeforeTag(StringBuilder sb,
                                       int indexTagStart,
                                       int indexTagEnd,
                                       int indexTag) {

        if (indexTagEnd == -1) {
            return false;
        }

        if (indexTagStart == -1) {
            return false;
        }

        return indexTagStart < indexTagEnd && indexTagStart > indexTag;
    }

    private static int getEndIndex(StringBuilder sb, String tagEnd, int indexTag) {
        return sb.indexOf(tagEnd, indexTag);
    }

    private static int getStartIndex(StringBuilder sb,
                                     String tagStart,
                                     int indexTagEnd,
                                     int indexTag) {
        int indexTagStart = -1;

        int temp = indexTagStart;
        while ((temp = sb.indexOf(tagStart, temp + 1)) != -1 && temp < indexTagEnd) {
            indexTagStart = temp;
        }

        return indexTagStart;
    }

    private static String getColorHtml(StringBuilder sb, int indexTagStart) {
        String colorStr = "color:";
        int index = sb.indexOf(colorStr, indexTagStart);
        int indexStart = index + colorStr.length();
        int indexEnd = sb.indexOf(";", indexTagStart);
        char[] chars = new char[indexEnd - indexStart];
        sb.getChars(indexStart, indexEnd, chars, 0);
        return String.valueOf(chars);
    }

    public static String removeTextWrapperHtml(String htmlText) {
        StringBuilder sb = new StringBuilder(htmlText);
        String htmlOpen = "<html>";
        String bodyOpen = "<body>";
        String preOpen = "<pre>";
        String htmlClose = "</html>";
        String bodyClose = "</body>";
        String preClose = "</pre>";

        String[] openTags = {htmlOpen, bodyOpen, preOpen};
        String[] closeTags = {htmlClose, bodyClose, preClose};
        for (String openTag : openTags) {
            int startIndex = sb.indexOf(openTag);
            sb.delete(startIndex, startIndex + openTag.length());
        }
        for (String closeTag : closeTags) {
            int startIndex = sb.lastIndexOf(closeTag);
            sb.delete(startIndex, startIndex + closeTag.length());
        }

        return sb.toString();
    }

    public static String appendTextToHtml(String originHtmlText, String unwrappedTextToAppend) {
        int indexPreClosingTag = originHtmlText.lastIndexOf("</pre>");
        originHtmlText = originHtmlText.substring(0, +indexPreClosingTag) + unwrappedTextToAppend +
                         originHtmlText.substring(indexPreClosingTag);
        return originHtmlText;
    }
}
