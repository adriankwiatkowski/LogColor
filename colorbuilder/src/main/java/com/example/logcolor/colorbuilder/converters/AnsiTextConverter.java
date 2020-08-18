package com.example.logcolor.colorbuilder.converters;

import com.example.logcolor.colorbuilder.text.Text;
import com.example.logcolor.colorbuilder.text.TextAttribute;
import com.example.logcolor.colorbuilder.text.TextStyle;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class AnsiTextConverter extends TextConverter {

    private static final List<Pair<Integer, String>> COLOR_TABLE = new ArrayList<>() {{
        add(new Pair<>(0, "000000"));
        add(new Pair<>(1, "800000"));
        add(new Pair<>(2, "008000"));
        add(new Pair<>(3, "808000"));
        add(new Pair<>(4, "000080"));
        add(new Pair<>(5, "800080"));
        add(new Pair<>(6, "008080"));
        add(new Pair<>(7, "c0c0c0"));
        add(new Pair<>(8, "808080"));
        add(new Pair<>(9, "ff0000"));
        add(new Pair<>(10, "00ff00"));
        add(new Pair<>(11, "ffff00"));
        add(new Pair<>(12, "0000ff"));
        add(new Pair<>(13, "ff00ff"));
        add(new Pair<>(14, "00ffff"));
        add(new Pair<>(15, "ffffff"));
        add(new Pair<>(16, "000000"));
        add(new Pair<>(17, "00005f"));
        add(new Pair<>(18, "000087"));
        add(new Pair<>(19, "0000af"));
        add(new Pair<>(20, "0000d7"));
        add(new Pair<>(21, "0000ff"));
        add(new Pair<>(22, "005f00"));
        add(new Pair<>(23, "005f5f"));
        add(new Pair<>(24, "005f87"));
        add(new Pair<>(25, "005faf"));
        add(new Pair<>(26, "005fd7"));
        add(new Pair<>(27, "005fff"));
        add(new Pair<>(28, "008700"));
        add(new Pair<>(29, "00875f"));
        add(new Pair<>(30, "008787"));
        add(new Pair<>(31, "0087af"));
        add(new Pair<>(32, "0087d7"));
        add(new Pair<>(33, "0087ff"));
        add(new Pair<>(34, "00af00"));
        add(new Pair<>(35, "00af5f"));
        add(new Pair<>(36, "00af87"));
        add(new Pair<>(37, "00afaf"));
        add(new Pair<>(38, "00afd7"));
        add(new Pair<>(39, "00afff"));
        add(new Pair<>(40, "00d700"));
        add(new Pair<>(41, "00d75f"));
        add(new Pair<>(42, "00d787"));
        add(new Pair<>(43, "00d7af"));
        add(new Pair<>(44, "00d7d7"));
        add(new Pair<>(45, "00d7ff"));
        add(new Pair<>(46, "00ff00"));
        add(new Pair<>(47, "00ff5f"));
        add(new Pair<>(48, "00ff87"));
        add(new Pair<>(49, "00ffaf"));
        add(new Pair<>(50, "00ffd7"));
        add(new Pair<>(51, "00ffff"));
        add(new Pair<>(52, "5f0000"));
        add(new Pair<>(53, "5f005f"));
        add(new Pair<>(54, "5f0087"));
        add(new Pair<>(55, "5f00af"));
        add(new Pair<>(56, "5f00d7"));
        add(new Pair<>(57, "5f00ff"));
        add(new Pair<>(58, "5f5f00"));
        add(new Pair<>(59, "5f5f5f"));
        add(new Pair<>(60, "5f5f87"));
        add(new Pair<>(61, "5f5faf"));
        add(new Pair<>(62, "5f5fd7"));
        add(new Pair<>(63, "5f5fff"));
        add(new Pair<>(64, "5f8700"));
        add(new Pair<>(65, "5f875f"));
        add(new Pair<>(66, "5f8787"));
        add(new Pair<>(67, "5f87af"));
        add(new Pair<>(68, "5f87d7"));
        add(new Pair<>(69, "5f87ff"));
        add(new Pair<>(70, "5faf00"));
        add(new Pair<>(71, "5faf5f"));
        add(new Pair<>(72, "5faf87"));
        add(new Pair<>(73, "5fafaf"));
        add(new Pair<>(74, "5fafd7"));
        add(new Pair<>(75, "5fafff"));
        add(new Pair<>(76, "5fd700"));
        add(new Pair<>(77, "5fd75f"));
        add(new Pair<>(78, "5fd787"));
        add(new Pair<>(79, "5fd7af"));
        add(new Pair<>(80, "5fd7d7"));
        add(new Pair<>(81, "5fd7ff"));
        add(new Pair<>(82, "5fff00"));
        add(new Pair<>(83, "5fff5f"));
        add(new Pair<>(84, "5fff87"));
        add(new Pair<>(85, "5fffaf"));
        add(new Pair<>(86, "5fffd7"));
        add(new Pair<>(87, "5fffff"));
        add(new Pair<>(88, "870000"));
        add(new Pair<>(89, "87005f"));
        add(new Pair<>(90, "870087"));
        add(new Pair<>(91, "8700af"));
        add(new Pair<>(92, "8700d7"));
        add(new Pair<>(93, "8700ff"));
        add(new Pair<>(94, "875f00"));
        add(new Pair<>(95, "875f5f"));
        add(new Pair<>(96, "875f87"));
        add(new Pair<>(97, "875faf"));
        add(new Pair<>(98, "875fd7"));
        add(new Pair<>(99, "875fff"));
        add(new Pair<>(100, "878700"));
        add(new Pair<>(101, "87875f"));
        add(new Pair<>(102, "878787"));
        add(new Pair<>(103, "8787af"));
        add(new Pair<>(104, "8787d7"));
        add(new Pair<>(105, "8787ff"));
        add(new Pair<>(106, "87af00"));
        add(new Pair<>(107, "87af5f"));
        add(new Pair<>(108, "87af87"));
        add(new Pair<>(109, "87afaf"));
        add(new Pair<>(110, "87afd7"));
        add(new Pair<>(111, "87afff"));
        add(new Pair<>(112, "87d700"));
        add(new Pair<>(113, "87d75f"));
        add(new Pair<>(114, "87d787"));
        add(new Pair<>(115, "87d7af"));
        add(new Pair<>(116, "87d7d7"));
        add(new Pair<>(117, "87d7ff"));
        add(new Pair<>(118, "87ff00"));
        add(new Pair<>(119, "87ff5f"));
        add(new Pair<>(120, "87ff87"));
        add(new Pair<>(121, "87ffaf"));
        add(new Pair<>(122, "87ffd7"));
        add(new Pair<>(123, "87ffff"));
        add(new Pair<>(124, "af0000"));
        add(new Pair<>(125, "af005f"));
        add(new Pair<>(126, "af0087"));
        add(new Pair<>(127, "af00af"));
        add(new Pair<>(128, "af00d7"));
        add(new Pair<>(129, "af00ff"));
        add(new Pair<>(130, "af5f00"));
        add(new Pair<>(131, "af5f5f"));
        add(new Pair<>(132, "af5f87"));
        add(new Pair<>(133, "af5faf"));
        add(new Pair<>(134, "af5fd7"));
        add(new Pair<>(135, "af5fff"));
        add(new Pair<>(136, "af8700"));
        add(new Pair<>(137, "af875f"));
        add(new Pair<>(138, "af8787"));
        add(new Pair<>(139, "af87af"));
        add(new Pair<>(140, "af87d7"));
        add(new Pair<>(141, "af87ff"));
        add(new Pair<>(142, "afaf00"));
        add(new Pair<>(143, "afaf5f"));
        add(new Pair<>(144, "afaf87"));
        add(new Pair<>(145, "afafaf"));
        add(new Pair<>(146, "afafd7"));
        add(new Pair<>(147, "afafff"));
        add(new Pair<>(148, "afd700"));
        add(new Pair<>(149, "afd75f"));
        add(new Pair<>(150, "afd787"));
        add(new Pair<>(151, "afd7af"));
        add(new Pair<>(152, "afd7d7"));
        add(new Pair<>(153, "afd7ff"));
        add(new Pair<>(154, "afff00"));
        add(new Pair<>(155, "afff5f"));
        add(new Pair<>(156, "afff87"));
        add(new Pair<>(157, "afffaf"));
        add(new Pair<>(158, "afffd7"));
        add(new Pair<>(159, "afffff"));
        add(new Pair<>(160, "d70000"));
        add(new Pair<>(161, "d7005f"));
        add(new Pair<>(162, "d70087"));
        add(new Pair<>(163, "d700af"));
        add(new Pair<>(164, "d700d7"));
        add(new Pair<>(165, "d700ff"));
        add(new Pair<>(166, "d75f00"));
        add(new Pair<>(167, "d75f5f"));
        add(new Pair<>(168, "d75f87"));
        add(new Pair<>(169, "d75faf"));
        add(new Pair<>(170, "d75fd7"));
        add(new Pair<>(171, "d75fff"));
        add(new Pair<>(172, "d78700"));
        add(new Pair<>(173, "d7875f"));
        add(new Pair<>(174, "d78787"));
        add(new Pair<>(175, "d787af"));
        add(new Pair<>(176, "d787d7"));
        add(new Pair<>(177, "d787ff"));
        add(new Pair<>(178, "d7af00"));
        add(new Pair<>(179, "d7af5f"));
        add(new Pair<>(180, "d7af87"));
        add(new Pair<>(181, "d7afaf"));
        add(new Pair<>(182, "d7afd7"));
        add(new Pair<>(183, "d7afff"));
        add(new Pair<>(184, "d7d700"));
        add(new Pair<>(185, "d7d75f"));
        add(new Pair<>(186, "d7d787"));
        add(new Pair<>(187, "d7d7af"));
        add(new Pair<>(188, "d7d7d7"));
        add(new Pair<>(189, "d7d7ff"));
        add(new Pair<>(190, "d7ff00"));
        add(new Pair<>(191, "d7ff5f"));
        add(new Pair<>(192, "d7ff87"));
        add(new Pair<>(193, "d7ffaf"));
        add(new Pair<>(194, "d7ffd7"));
        add(new Pair<>(195, "d7ffff"));
        add(new Pair<>(196, "ff0000"));
        add(new Pair<>(197, "ff005f"));
        add(new Pair<>(198, "ff0087"));
        add(new Pair<>(199, "ff00af"));
        add(new Pair<>(200, "ff00d7"));
        add(new Pair<>(201, "ff00ff"));
        add(new Pair<>(202, "ff5f00"));
        add(new Pair<>(203, "ff5f5f"));
        add(new Pair<>(204, "ff5f87"));
        add(new Pair<>(205, "ff5faf"));
        add(new Pair<>(206, "ff5fd7"));
        add(new Pair<>(207, "ff5fff"));
        add(new Pair<>(208, "ff8700"));
        add(new Pair<>(209, "ff875f"));
        add(new Pair<>(210, "ff8787"));
        add(new Pair<>(211, "ff87af"));
        add(new Pair<>(212, "ff87d7"));
        add(new Pair<>(213, "ff87ff"));
        add(new Pair<>(214, "ffaf00"));
        add(new Pair<>(215, "ffaf5f"));
        add(new Pair<>(216, "ffaf87"));
        add(new Pair<>(217, "ffafaf"));
        add(new Pair<>(218, "ffafd7"));
        add(new Pair<>(219, "ffafff"));
        add(new Pair<>(220, "ffd700"));
        add(new Pair<>(221, "ffd75f"));
        add(new Pair<>(222, "ffd787"));
        add(new Pair<>(223, "ffd7af"));
        add(new Pair<>(224, "ffd7d7"));
        add(new Pair<>(225, "ffd7ff"));
        add(new Pair<>(226, "ffff00"));
        add(new Pair<>(227, "ffff5f"));
        add(new Pair<>(228, "ffff87"));
        add(new Pair<>(229, "ffffaf"));
        add(new Pair<>(230, "ffffd7"));
        add(new Pair<>(231, "ffffff"));
        add(new Pair<>(232, "080808"));
        add(new Pair<>(233, "121212"));
        add(new Pair<>(234, "1c1c1c"));
        add(new Pair<>(235, "262626"));
        add(new Pair<>(236, "303030"));
        add(new Pair<>(237, "3a3a3a"));
        add(new Pair<>(238, "444444"));
        add(new Pair<>(239, "4e4e4e"));
        add(new Pair<>(240, "585858"));
        add(new Pair<>(241, "626262"));
        add(new Pair<>(242, "6c6c6c"));
        add(new Pair<>(243, "767676"));
        add(new Pair<>(244, "808080"));
        add(new Pair<>(245, "8a8a8a"));
        add(new Pair<>(246, "949494"));
        add(new Pair<>(247, "9e9e9e"));
        add(new Pair<>(248, "a8a8a8"));
        add(new Pair<>(249, "b2b2b2"));
        add(new Pair<>(250, "bcbcbc"));
        add(new Pair<>(251, "c6c6c6"));
        add(new Pair<>(252, "d0d0d0"));
        add(new Pair<>(253, "dadada"));
        add(new Pair<>(254, "e4e4e4"));
        add(new Pair<>(255, "eeeeee"));
    }};

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

        for (Text text : textList) {
            appendText(sb, text);
        }

        return sb.toString();
    }

    private void appendText(StringBuilder sb, Text text) {
        TextAttribute textAttribute = text.getTextAttribute();

        String s = getTextAligned(text);

        String[] lines = s.split("\n");
        for (int i = 0; i < lines.length; ++i) {
            String line = lines[i];

            if (textAttribute != null) {
                EnumSet<TextStyle> textStyles = textAttribute.getTextStyleEnumSet();
                if (textStyles != null) {
                    if (textStyles.contains(TextStyle.ITALIC)) {
                        sb.append("\u001B[3m");
                    }
                    if (textStyles.contains(TextStyle.BOLD)) {
                        sb.append("\u001B[1m");
                    }
                    if (textStyles.contains(TextStyle.UNDERLINE)) {
                        sb.append("\u001B[4m");
                    }
                }

                Color background = textAttribute.getBackground();
                Color foreground = textAttribute.getForeground();

                if (background != null) {
                    int bgAnsi = rgbToAnsi(background.getRGB());
                    sb.append("\u001B[48;5;").append(bgAnsi).append("m");
                }
                if (foreground != null) {
                    int fgAnsi = rgbToAnsi(foreground.getRGB());
                    sb.append("\u001B[38;5;").append(fgAnsi).append("m");
                }
            }

            sb.append(line);

            sb.append("\u001B[0m");

            if (i != lines.length - 1 || text.getMessage().endsWith("\n")) {
                sb.append("\n");
            }
        }

        if (lines.length == 0 && text.getMessage().endsWith("\n")) {
            sb.append("\n");
        }
    }

    private String getTextAligned(Text text) {
        TextAttribute textAttribute = text.getTextAttribute();
        String s = text.getMessage();

        if (textAttribute != null && textAttribute.getTextAlignment() != null) {
            // Basically checks if it's print("\n");
            if (!s.equals("\n")) {
                if (s.endsWith("\n")) {
                    s = textAttribute.getTextAlignment()
                                     .writeAligned(s.substring(0, s.length() - "\n".length()),
                                                   textAttribute.getExtraSpace()) + "\n";
                } else {
                    s = textAttribute.getTextAlignment()
                                     .writeAligned(s, textAttribute.getExtraSpace());
                }
            }
        }

        return s;
    }

    private int rgbToAnsi(int rgb) {
        return rgbToAnsi((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, (rgb) & 0xFF);
    }

    private int rgbToAnsi(int r, int g, int b) {
        int bestChoice = 0;
        float bestDistance = 1000000000f;

        for (Pair<Integer, String> pair : COLOR_TABLE) {
            int ansi = pair.getKey();
            String rgb = pair.getValue();

            int cr = Integer.parseInt(rgb.substring(0, 2), 16);
            int cg = Integer.parseInt(rgb.substring(2, 4), 16);
            int cb = Integer.parseInt(rgb.substring(4, 6), 16);

            float dr = Math.abs((cr / 255.0f) - (r / 255.0f));
            float dg = Math.abs((cg / 255.0f) - (g / 255.0f));
            float db = Math.abs((cb / 255.0f) - (b / 255.0f));

            float dist = dr * dr + dg * dg + db * db;

            if (dist <= bestDistance) {
                bestDistance = dist;
                bestChoice = ansi;
            }
        }

        return bestChoice;
    }
}
