package com.example.logcolor.colorbuilder.converters;

import com.example.logcolor.colorbuilder.text.TextAlignment;
import com.example.logcolor.colorbuilder.text.TextAttribute;
import com.example.logcolor.colorbuilder.text.TextStyle;
import com.example.logcolor.colorbuilder.text.Text;

import java.awt.*;
import java.util.EnumSet;
import java.util.List;

public class HtmlTextConverter extends TextConverter {

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
        StringBuilder sb = new StringBuilder("<html><body><pre>");

        for (int i = 0; i < textList.size(); ++i) {
            Text text = textList.get(i);
            boolean isLastText = i == textList.size() - 1;
            appendText(sb, text, isLastText);
        }

        sb.append("</pre></body></html>");

        return sb.toString();
    }

    private void appendText(StringBuilder sb, Text text, boolean ignoreLastEmptyNewLine) {
        TextAttribute textAttribute = text.getTextAttribute();
        String s = text.getMessage();

        while (ignoreLastEmptyNewLine && s.endsWith("\n")) {
            s = s.substring(0, s.length() - "\n".length());
        }

        // Replace new lines with equivalent <br> tag in html.
        s = s.replace("\n", "<br>");

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

        // <font style="color:FFFFFF;background-color:000000;">
        // </font>
    }

    private void appendTextStyleStart(StringBuilder sb, EnumSet<TextStyle> textStyles) {
        if (textStyles.contains(TextStyle.ITALIC)) {
            sb.append("<i>");
        }
        if (textStyles.contains(TextStyle.BOLD)) {
            sb.append("<B>");
        }
        if (textStyles.contains(TextStyle.UNDERLINE)) {
            sb.append("<U>");
        }
    }

    private void appendTextStyleEnd(StringBuilder sb, EnumSet<TextStyle> textStyles) {
        if (textStyles.contains(TextStyle.UNDERLINE)) {
            sb.append("</U>");
        }
        if (textStyles.contains(TextStyle.BOLD)) {
            sb.append("</B>");
        }
        if (textStyles.contains(TextStyle.ITALIC)) {
            sb.append("</i>");
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
                sb.append("color:").append(hexForeground).append(";");
            }
            if (hexBackground != null) {
                sb.append("background-color:").append(hexBackground).append(";");
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
