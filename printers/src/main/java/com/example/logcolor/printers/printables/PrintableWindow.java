package com.example.logcolor.printers.printables;

import com.example.logcolor.colorbuilder.converters.HtmlTextConverter;
import com.example.logcolor.colorbuilder.converters.TextConverter;
import com.example.logcolor.colorbuilder.interfaces.ColorBuilder;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Queue;

public class PrintableWindow extends Printable {

    private static final HtmlTextConverter HTML_TEXT_CONVERTER = new HtmlTextConverter();

    private static final String BLACK_COLOR = "0x121212";
    private static final String WHITE_COLOR = "0xFFFAFA";
    private static final int MAX_MESSAGE_COUNT = 1000;

    private JFrame mMainFrame;
    private JList<String> mMessageList;
    private DefaultListModel<String> mListModel = new DefaultListModel<>();
    private boolean mIsForceOnNewLine = false;

    // Not safe to use, may produce Null Pointer Exception.
    private Queue<Runnable> mPendingRequestQueue = new LinkedList<>();
    private boolean mIsPendingRequest = true;

    public PrintableWindow(boolean nightTheme) {
        super(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                // Not implemented.
            }
        });

        SwingUtilities.invokeLater(() -> {
            mMainFrame = new JFrame("Printable Window");
            mMainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            mMessageList = new JList<>(mListModel);
            mMessageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            mMessageList.setLayoutOrientation(JList.VERTICAL);
            mMessageList.setVisibleRowCount(-1);
            mMessageList.setCellRenderer(createCallRenderer());

            JScrollPane listScroller = new JScrollPane(mMessageList);

            mMainFrame.getContentPane().add(listScroller, BorderLayout.CENTER);

            mMainFrame.pack();

            mMainFrame.setBounds(300, 150, 1024, 768);
            mMainFrame.setVisible(true);


            mIsPendingRequest = false;
            if (nightTheme) {
                setNightTheme();
            } else {
                setDayTheme();
            }

            for (Runnable runnable : mPendingRequestQueue) {
                runnable.run();
            }
            mPendingRequestQueue = null;
        });
    }

    @Override
    protected void write(ColorBuilder colorBuilder) {
        addTextToList(colorBuilder.convertText(getTextConverter()));
    }

    @Override
    public HtmlTextConverter getTextConverter() {
        return HTML_TEXT_CONVERTER;
    }

    @Override
    public void printForceOnNewLine(ColorBuilder colorBuilder) {
        mIsForceOnNewLine = true;
        write(colorBuilder);
    }

    @Override
    public void setDayTheme() {
        if (mIsPendingRequest) {
            mPendingRequestQueue.add(() -> {
                mMainFrame.getContentPane().setBackground(decodeColor(WHITE_COLOR));
                mMainFrame.getContentPane().setForeground(decodeColor(WHITE_COLOR));
                mMessageList.setBackground(decodeColor(WHITE_COLOR));
                mMessageList.setForeground(decodeColor(WHITE_COLOR));
            });
        } else {
            mMainFrame.getContentPane().setBackground(decodeColor(WHITE_COLOR));
            mMainFrame.getContentPane().setForeground(decodeColor(WHITE_COLOR));
            mMessageList.setBackground(decodeColor(WHITE_COLOR));
            mMessageList.setForeground(decodeColor(WHITE_COLOR));
        }
    }

    @Override
    public void setNightTheme() {
        if (mIsPendingRequest) {
            mPendingRequestQueue.add(() -> {
                mMainFrame.getContentPane().setBackground(decodeColor(BLACK_COLOR));
                mMainFrame.getContentPane().setForeground(decodeColor(BLACK_COLOR));
                mMessageList.setBackground(decodeColor(BLACK_COLOR));
                mMessageList.setForeground(decodeColor(BLACK_COLOR));
            });
        } else {
            mMainFrame.getContentPane().setBackground(decodeColor(BLACK_COLOR));
            mMainFrame.getContentPane().setForeground(decodeColor(BLACK_COLOR));
            mMessageList.setBackground(decodeColor(BLACK_COLOR));
            mMessageList.setForeground(decodeColor(BLACK_COLOR));
        }
    }

    @Override
    public void close() {
        super.close();
        if (mIsPendingRequest) {
            mPendingRequestQueue.add(() -> {
                mMainFrame.setVisible(false);
                mMainFrame.dispose();
            });
        } else {
            mMainFrame.setVisible(false);
            mMainFrame.dispose();
        }
    }

    private synchronized void addTextToList(String string) {
        if (string == null) {
            throw new IllegalArgumentException("String cannot be null.");
        }

        if (mIsForceOnNewLine && !getTextConverter().isConvertedTextEndsWithNewLine(string)) {
            mListModel.addElement(string);
        } else {
            // Same action, because currently we are adding all new message as new item.
            mListModel.addElement(string);
        }

        if (mListModel.size() >= MAX_MESSAGE_COUNT) {
            mListModel.remove(0);
        }
    }

    private Color decodeColor(String hex) {
        return new Color(Integer.valueOf(hex.substring(2, 4), 16),
                         Integer.valueOf(hex.substring(4, 6), 16),
                         Integer.valueOf(hex.substring(6, 8), 16));
    }

    private ListCellRenderer<String> createCallRenderer() {
        return new ListRenderer();
    }

    private static class ListRenderer extends JLabel implements ListCellRenderer<String> {

        @Override
        public Component getListCellRendererComponent(JList<? extends String> list,
                                                      String value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus) {

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            setOpaque(true);
            setText(value);

            return this;
        }
    }
}
