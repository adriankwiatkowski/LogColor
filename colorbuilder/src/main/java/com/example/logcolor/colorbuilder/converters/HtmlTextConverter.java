package com.example.logcolor.colorbuilder.converters;

import com.example.logcolor.colorbuilder.text.Text;
import com.example.logcolor.colorbuilder.text.TextAlignment;
import com.example.logcolor.colorbuilder.text.TextAttribute;
import com.example.logcolor.colorbuilder.text.TextStyle;

import java.awt.*;
import java.util.EnumSet;
import java.util.List;

public class HtmlTextConverter extends TextConverter {

    private static final String TAG_HTML_BEGIN = "<html>";
    private static final String TAG_HTML_END = "</html>";
    private static final String TAG_BODY_BEGIN = "<body>";
    private static final String TAG_BODY_END = "</body>";
    private static final String TAG_PRE_BEGIN = "<pre>";
    private static final String TAG_PRE_END = "</pre>";
    private static final String TAG_NEW_LINE = "<br>";
    private static final String TAG_ITALIC_BEGIN = "<i>";
    private static final String TAG_ITALIC_END = "</i>";
    private static final String TAG_BOLD_BEGIN = "<B>";
    private static final String TAG_BOLD_END = "</B>";
    private static final String TAG_UNDERLINE_BEGIN = "<U>";
    private static final String TAG_UNDERLINE_END = "</U>";
    private static final String SEMICOLON = ";";

    @Override
    public String convertText(List<Text> textList) {
        return super.convertText(textList);
    }

    @Override
    protected String convertMessage(String message) {
        return super.convertMessage(message);
    }

    @Override
    protected String convertTextList(List<Text> textList) {
        StringBuilder sb = new StringBuilder();
        sb.append(TAG_HTML_BEGIN).append(TAG_BODY_BEGIN).append(TAG_PRE_BEGIN);

        for (int i = 0; i < textList.size(); ++i) {
            Text text = textList.get(i);
            boolean isLastText = i == textList.size() - 1;
            appendText(sb, text, isLastText);
        }

        sb.append(TAG_PRE_END).append(TAG_BODY_END).append(TAG_HTML_END);

        return sb.toString();
    }

    public boolean isConvertedTextEndsWithNewLine(String message) {
        int index = message.indexOf(TAG_PRE_END);
        if (index < 0) {
            return false;
        }

        String s = message.substring(0, index);
        return s.endsWith(TAG_NEW_LINE);
    }

    private void appendText(StringBuilder sb, Text text, boolean ignoreLastEmptyNewLine) {
        TextAttribute textAttribute = text.getTextAttribute();
        String s = text.getMessage();

        while (ignoreLastEmptyNewLine && s.endsWith("\n")) {
            s = s.substring(0, s.length() - "\n".length());
        }

        // Replace new lines with equivalent <br> tag in html.
        s = s.replace("\n", TAG_NEW_LINE);

        if (textAttribute != null) {
            TextAlignment textAlignment = textAttribute.getTextAlignment();
            if (textAlignment != null) {
                s = textAlignment.writeAligned(s, textAttribute.getExtraSpace());
            }

            EnumSet<TextStyle> textStyles = textAttribute.getTextStyleEnumSet();
            if (textStyles != null) {
                appendTextStyleStart(sb, textStyles);
            }


            appendFontStyleText(sb, s, textAttribute);

            if (textStyles != null) {
                appendTextStyleEnd(sb, textStyles);
            }
        } else {
            sb.append(s);
        }
    }

    private void appendTextStyleStart(StringBuilder sb, EnumSet<TextStyle> textStyles) {
        if (textStyles.contains(TextStyle.ITALIC)) {
            sb.append(TAG_ITALIC_BEGIN);
        }
        if (textStyles.contains(TextStyle.BOLD)) {
            sb.append(TAG_BOLD_BEGIN);
        }
        if (textStyles.contains(TextStyle.UNDERLINE)) {
            sb.append(TAG_UNDERLINE_BEGIN);
        }
    }

    private void appendTextStyleEnd(StringBuilder sb, EnumSet<TextStyle> textStyles) {
        if (textStyles.contains(TextStyle.UNDERLINE)) {
            sb.append(TAG_UNDERLINE_END);
        }
        if (textStyles.contains(TextStyle.BOLD)) {
            sb.append(TAG_BOLD_END);
        }
        if (textStyles.contains(TextStyle.ITALIC)) {
            sb.append(TAG_ITALIC_END);
        }
    }

    private void appendFontStyleText(StringBuilder sb, String s, TextAttribute textAttribute) {
        Color background = textAttribute.getBackground();
        Color foreground = textAttribute.getForeground();

        String hexBackground = null;
        String hexForeground = null;
        if (background != null) {
            hexBackground = rgbToHex(background);
        }
        if (foreground != null) {
            hexForeground = rgbToHex(foreground);
        }

        if (hexBackground != null || hexForeground != null) {
            sb.append("<font style=\"");
            if (hexForeground != null) {
                sb.append("color:").append(hexForeground).append(SEMICOLON);
            }
            if (hexBackground != null) {
                sb.append("background-color:").append(hexBackground).append(SEMICOLON);
            }
            sb.append("\">");
        }

        sb.append(s);

        sb.append("</font>");
    }

    private String rgbToHex(Color color) {
        return Integer.toHexString(color.getRGB() & 0xFFFFFF);
    }
}
