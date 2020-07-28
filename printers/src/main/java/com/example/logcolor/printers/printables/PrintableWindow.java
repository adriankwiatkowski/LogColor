package com.example.logcolor.printers.printables;

import com.example.logcolor.printers.utils.HtmlUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Queue;

public class PrintableWindow extends Printable {

    private static final String BLACK_COLOR = "000000";
    private static final String WHITE_COLOR = "FFFFFF";

    private JFrame mMainFrame;
    private JList<String> mMessageList;
    private DefaultListModel<String> mListModel = new DefaultListModel<>();
    private boolean mIsNewLine = false;

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

            if (nightTheme) {
                setNightTheme();
            } else {
                setDayTheme();
            }

            if (mIsPendingRequest) {
                mIsPendingRequest = false;
                for (Runnable runnable : mPendingRequestQueue) {
                    runnable.run();
                }
                mPendingRequestQueue = null;
            }
        });
    }

    @Override
    protected void write(String s) {
        s = setDefaultColorIfNotOverridden(s);
        addTextToList(s);
    }

    @Override
    public void setDayTheme() {
        if (mIsPendingRequest) {
            mPendingRequestQueue.add(() -> {
                mMainFrame.getContentPane().setBackground(Color.decode(WHITE_COLOR));
                mMainFrame.getContentPane().setForeground(Color.decode(WHITE_COLOR));
                mMessageList.setBackground(Color.decode(WHITE_COLOR));
                mMessageList.setForeground(Color.decode(WHITE_COLOR));
            });
        } else {
            mMainFrame.getContentPane().setBackground(Color.decode(WHITE_COLOR));
            mMainFrame.getContentPane().setForeground(Color.decode(WHITE_COLOR));
            mMessageList.setBackground(Color.decode(WHITE_COLOR));
            mMessageList.setForeground(Color.decode(WHITE_COLOR));
        }
    }

    @Override
    public void setNightTheme() {
        if (mIsPendingRequest) {
            mPendingRequestQueue.add(() -> {
                mMainFrame.getContentPane().setBackground(Color.decode(BLACK_COLOR));
                mMainFrame.getContentPane().setForeground(Color.decode(BLACK_COLOR));
                mMessageList.setBackground(Color.decode(BLACK_COLOR));
                mMessageList.setForeground(Color.decode(BLACK_COLOR));
            });
        } else {
            mMainFrame.getContentPane().setBackground(Color.decode(BLACK_COLOR));
            mMainFrame.getContentPane().setForeground(Color.decode(BLACK_COLOR));
            mMessageList.setBackground(Color.decode(BLACK_COLOR));
            mMessageList.setForeground(Color.decode(BLACK_COLOR));
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

    private void addTextToList(String string) {
        if (string == null) {
            throw new IllegalArgumentException("String cannot be null.");
        }

        String[] splitLines = string.split("\n");

        for (String splitLine : splitLines) {
            String htmlText = HtmlUtils.buildHtmlFromAnsi(splitLine);
            if (mIsNewLine || mListModel.isEmpty()) {
                mListModel.addElement(htmlText);
            } else {
                htmlText = HtmlUtils.removeTextWrapperHtml(htmlText);
                String originalHtmlText = mListModel.get(mListModel.size() - 1);
                String concatHtmlText = HtmlUtils.appendTextToHtml(originalHtmlText, htmlText);
                mListModel.set(mListModel.size() - 1, concatHtmlText);
                mListModel.set(mListModel.size() - 1, concatHtmlText);
            }
        }

        mIsNewLine = string.endsWith("\n");
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
