package com.example.printers.printables;

import com.example.colorbuilder.interfaces.ColorBuilder;
import com.example.printers.interfaces.Printable;
import com.example.printers.utils.HtmlUtils;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class PrintableWindow implements Printable {

    private static final String BLACK_COLOR = "#1F1B24";
    private static final String WHITE_COLOR = "#FFFFFF";

    private JFrame mMainFrame;
    private JList<String> mMessageList;
    private DefaultListModel<String> mListModel = new DefaultListModel<>();

    private boolean mIsThemeSet = false;
    private boolean mIsDayTheme;

    // Not safe to use, may produce Null Exception.
    private Queue<Runnable> mPendingRequestQueue = new LinkedList<>();
    private boolean mIsPendingRequest = true;

    public PrintableWindow(Builder builder) {
        SwingUtilities.invokeLater(() -> {
            mMainFrame = new JFrame("Printable Window");
            mMainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            Color backgroundColor;
            if (mIsThemeSet) {
                backgroundColor = mIsDayTheme ? Color.WHITE : Color.BLACK;
            } else {
                backgroundColor = builder.dayTheme ? Color.WHITE : Color.BLACK;
            }

            mMessageList = new JList<>(mListModel);
            mMessageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            mMessageList.setLayoutOrientation(JList.VERTICAL);
            mMessageList.setVisibleRowCount(-1);
            mMessageList.setCellRenderer(createCallRenderer());
            // Set background color.
            mMessageList.setBackground(backgroundColor);

            JScrollPane listScroller = new JScrollPane(mMessageList);

            mMainFrame.getContentPane().add(listScroller, BorderLayout.CENTER);

            mMainFrame.getContentPane().setBackground(backgroundColor);

            mMainFrame.pack();

            mMainFrame.setBounds(300, 150, 1024, 768);
            mMainFrame.setVisible(true);

            if (mIsPendingRequest) {
                mIsPendingRequest = false;
                for (Runnable runnable : mPendingRequestQueue) {
                    runnable.run();
                }
                mPendingRequestQueue = null;
            }
        });
    }

    public static class Builder extends AbsBuilder {

        private boolean dayTheme = true;

        public Builder() {
        }

        public Builder setNightTheme(boolean nightTheme) {
            dayTheme = !nightTheme;
            return this;
        }

        public Builder setDayTheme() {
            dayTheme = true;
            return this;
        }

        public Builder setNightTheme() {
            dayTheme = false;
            return this;
        }

        @Override
        public PrintableWindow build() {
            return new PrintableWindow(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    private abstract static class AbsBuilder<T extends AbsBuilder<T>> {

        public abstract PrintableWindow build();

        protected abstract T self();
    }

    @Override
    public void print(String string) {
        addTextToList(string);
    }

    @Override
    public void print(ColorBuilder colorBuilder) {
        addTextToList(colorBuilder.getText());
    }

    @Override
    public void print_flush(ColorBuilder colorBuilder) {
        print(colorBuilder);
        colorBuilder.flush();
    }

    @Override
    public void setDayTheme() {
        if (mIsPendingRequest) {
            mPendingRequestQueue.add(() -> {
                mMainFrame.getContentPane().setBackground(Color.getColor(WHITE_COLOR));
                mMessageList.setBackground(Color.getColor(WHITE_COLOR));
            });
        } else {
            mMainFrame.getContentPane().setBackground(Color.getColor(WHITE_COLOR));
            mMessageList.setBackground(Color.getColor(WHITE_COLOR));
        }
    }

    @Override
    public void setNightTheme() {
        if (mIsPendingRequest) {
            mPendingRequestQueue.add(() -> {
                mMainFrame.getContentPane().setBackground(Color.getColor(BLACK_COLOR));
                mMessageList.setBackground(Color.getColor(BLACK_COLOR));
            });
        } else {
            mMainFrame.getContentPane().setBackground(Color.getColor(BLACK_COLOR));
            mMessageList.setBackground(Color.getColor(BLACK_COLOR));
        }
    }

    @Override
    public void onClose() {
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
        if (!string.contains("\n")) {
            String htmlText = HtmlUtils.buildHtmlFromAnsi(string);
            htmlText = HtmlUtils.removeTextWrapperHtml(htmlText);
            String originalHtmlText = mListModel.get(mListModel.size() - 1);
            String concatHtmlText = HtmlUtils.appendTextToHtml(originalHtmlText, htmlText);
            mListModel.set(mListModel.size() - 1, concatHtmlText);
        } else {
            mListModel.addElement(HtmlUtils.buildHtmlFromAnsi(string));
        }
    }

    private ListCellRenderer<String> createCallRenderer() {
        return new ListRenderer();
    }

    private static class ListRenderer extends JLabel
            implements ListCellRenderer<String> {

        @Override
        public Component getListCellRendererComponent(
                JList<? extends String> list,
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
