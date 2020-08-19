package com.example.logcolor.log;

import com.example.logcolor.colorbuilder.text.TextAlignment;
import com.example.logcolor.colorbuilder.utils.AnsiColor;
import com.example.logcolor.log.models.LogLevel;
import com.example.logcolor.printers.PrintableManager;

import java.awt.*;
import java.text.SimpleDateFormat;

public class LogManager {

    private static final Object LOCK = new Object();
    private static final Object DATE_LOCK = new Object();

    private static final int DEFAULT_MIN_LOG_LEVEL = LogLevel.INTERNAL_ERROR.getLevel();
    private static final boolean DEFAULT_SHOW_LOG_LEVEL = true;
    private static final boolean DEFAULT_SHOW_DATE = true;
    private static final boolean DEFAULT_SHOW_TAG = true;
    private static final String DEFAULT_DATE_PATTERN = "yyyy.MM.dd HH:mm:ss";
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT =
            new SimpleDateFormat(DEFAULT_DATE_PATTERN);
    private static final Color DEFAULT_INTERNAL_ERROR_COLOR_FOREGROUND_DAY =
            LogLevel.INTERNAL_ERROR.getDayThemeForeground();
    private static final Color DEFAULT_INTERNAL_ERROR_COLOR_BACKGROUND_DAY =
            LogLevel.INTERNAL_ERROR.getDayThemeBackground();
    private static final Color DEFAULT_INTERNAL_ERROR_COLOR_FOREGROUND_NIGHT =
            LogLevel.INTERNAL_ERROR.getNightThemeForeground();
    private static final Color DEFAULT_INTERNAL_ERROR_COLOR_BACKGROUND_NIGHT =
            LogLevel.INTERNAL_ERROR.getNightThemeBackground();
    private static final Color DEFAULT_VERBOSE_COLOR_FOREGROUND_DAY =
            LogLevel.VERBOSE.getDayThemeForeground();
    private static final Color DEFAULT_VERBOSE_COLOR_BACKGROUND_DAY =
            LogLevel.VERBOSE.getDayThemeBackground();
    private static final Color DEFAULT_VERBOSE_COLOR_FOREGROUND_NIGHT =
            LogLevel.VERBOSE.getNightThemeForeground();
    private static final Color DEFAULT_VERBOSE_COLOR_BACKGROUND_NIGHT =
            LogLevel.VERBOSE.getNightThemeBackground();
    private static final Color DEFAULT_INFO_COLOR_FOREGROUND_DAY =
            LogLevel.INFO.getDayThemeForeground();
    private static final Color DEFAULT_INFO_COLOR_BACKGROUND_DAY =
            LogLevel.INFO.getDayThemeBackground();
    private static final Color DEFAULT_INFO_COLOR_FOREGROUND_NIGHT =
            LogLevel.INFO.getNightThemeForeground();
    private static final Color DEFAULT_INFO_COLOR_BACKGROUND_NIGHT =
            LogLevel.INFO.getNightThemeBackground();
    private static final Color DEFAULT_DEBUG_COLOR_FOREGROUND_DAY =
            LogLevel.DEBUG.getDayThemeForeground();
    private static final Color DEFAULT_DEBUG_COLOR_BACKGROUND_DAY =
            LogLevel.DEBUG.getDayThemeBackground();
    private static final Color DEFAULT_DEBUG_COLOR_FOREGROUND_NIGHT =
            LogLevel.DEBUG.getNightThemeForeground();
    private static final Color DEFAULT_DEBUG_COLOR_BACKGROUND_NIGHT =
            LogLevel.DEBUG.getNightThemeBackground();
    private static final Color DEFAULT_WARNING_COLOR_FOREGROUND_DAY =
            LogLevel.WARNING.getDayThemeForeground();
    private static final Color DEFAULT_WARNING_COLOR_BACKGROUND_DAY =
            LogLevel.WARNING.getDayThemeBackground();
    private static final Color DEFAULT_WARNING_COLOR_FOREGROUND_NIGHT =
            LogLevel.WARNING.getNightThemeForeground();
    private static final Color DEFAULT_WARNING_COLOR_BACKGROUND_NIGHT =
            LogLevel.WARNING.getNightThemeBackground();
    private static final Color DEFAULT_ERROR_COLOR_FOREGROUND_DAY =
            LogLevel.ERROR.getDayThemeForeground();
    private static final Color DEFAULT_ERROR_COLOR_BACKGROUND_DAY =
            LogLevel.ERROR.getDayThemeBackground();
    private static final Color DEFAULT_ERROR_COLOR_FOREGROUND_NIGHT =
            LogLevel.ERROR.getNightThemeForeground();
    private static final Color DEFAULT_ERROR_COLOR_BACKGROUND_NIGHT =
            LogLevel.ERROR.getNightThemeBackground();
    private static final Color DEFAULT_DATE_TAG_COLOR_FOREGROUND_DAY =
            AnsiColor.ANSI_BLACK.getColor();
    private static final Color DEFAULT_DATE_TAG_COLOR_BACKGROUND_DAY =
            AnsiColor.ANSI_BRIGHT_BG_WHITE.getColor();
    private static final Color DEFAULT_DATE_TAG_COLOR_FOREGROUND_NIGHT =
            AnsiColor.ANSI_WHITE.getColor();
    private static final Color DEFAULT_DATE_TAG_COLOR_BACKGROUND_NIGHT =
            AnsiColor.ANSI_BRIGHT_BG_BLACK.getColor();
    private static final Color DEFAULT_TAG_COLOR_FOREGROUND_DAY =
            AnsiColor.ANSI_BRIGHT_BLACK.getColor();
    private static final Color DEFAULT_TAG_COLOR_BACKGROUND_DAY =
            AnsiColor.ANSI_BG_WHITE.getColor();
    private static final Color DEFAULT_TAG_COLOR_FOREGROUND_NIGHT =
            AnsiColor.ANSI_BRIGHT_WHITE.getColor();
    private static final Color DEFAULT_TAG_COLOR_BACKGROUND_NIGHT =
            AnsiColor.ANSI_BG_BLACK.getColor();
    private static final TextAlignment DEFAULT_TEXT_ALIGNMENT_LEVEL_LOG_INFO = TextAlignment.CENTER;
    private static final TextAlignment DEFAULT_TEXT_ALIGNMENT_DATE_TAG = TextAlignment.RIGHT;
    private static final TextAlignment DEFAULT_TEXT_ALIGNMENT_TAG = TextAlignment.CENTER;
    private static final TextAlignment DEFAULT_TEXT_ALIGNMENT_MESSAGE = TextAlignment.NONE;

    private int mMinLogLevel = DEFAULT_MIN_LOG_LEVEL;
    private boolean mShowLogLevel = DEFAULT_SHOW_LOG_LEVEL;
    private boolean mShowDate = DEFAULT_SHOW_DATE;
    private boolean mShowTag = DEFAULT_SHOW_TAG;
    private String mDatePattern = DEFAULT_DATE_PATTERN;
    private SimpleDateFormat mDateFormat = DEFAULT_DATE_FORMAT;
    private Color mColorInternalErrorForegroundDay = DEFAULT_INTERNAL_ERROR_COLOR_FOREGROUND_DAY;
    private Color mColorInternalErrorBackgroundDay = DEFAULT_INTERNAL_ERROR_COLOR_BACKGROUND_DAY;
    private Color mColorInternalErrorForegroundNight =
            DEFAULT_INTERNAL_ERROR_COLOR_FOREGROUND_NIGHT;
    private Color mColorInternalErrorBackgroundNight =
            DEFAULT_INTERNAL_ERROR_COLOR_BACKGROUND_NIGHT;
    private Color mColorVerboseForegroundDay = DEFAULT_VERBOSE_COLOR_FOREGROUND_DAY;
    private Color mColorVerboseBackgroundDay = DEFAULT_VERBOSE_COLOR_BACKGROUND_DAY;
    private Color mColorVerboseForegroundNight = DEFAULT_VERBOSE_COLOR_FOREGROUND_NIGHT;
    private Color mColorVerboseBackgroundNight = DEFAULT_VERBOSE_COLOR_BACKGROUND_NIGHT;
    private Color mColorInfoForegroundDay = DEFAULT_INFO_COLOR_FOREGROUND_DAY;
    private Color mColorInfoBackgroundDay = DEFAULT_INFO_COLOR_BACKGROUND_DAY;
    private Color mColorInfoForegroundNight = DEFAULT_INFO_COLOR_FOREGROUND_NIGHT;
    private Color mColorInfoBackgroundNight = DEFAULT_INFO_COLOR_BACKGROUND_NIGHT;
    private Color mColorDebugForegroundDay = DEFAULT_DEBUG_COLOR_FOREGROUND_DAY;
    private Color mColorDebugBackgroundDay = DEFAULT_DEBUG_COLOR_BACKGROUND_DAY;
    private Color mColorDebugForegroundNight = DEFAULT_DEBUG_COLOR_FOREGROUND_NIGHT;
    private Color mColorDebugBackgroundNight = DEFAULT_DEBUG_COLOR_BACKGROUND_NIGHT;
    private Color mColorWarningForegroundDay = DEFAULT_WARNING_COLOR_FOREGROUND_DAY;
    private Color mColorWarningBackgroundDay = DEFAULT_WARNING_COLOR_BACKGROUND_DAY;
    private Color mColorWarningForegroundNight = DEFAULT_WARNING_COLOR_FOREGROUND_NIGHT;
    private Color mColorWarningBackgroundNight = DEFAULT_WARNING_COLOR_BACKGROUND_NIGHT;
    private Color mColorErrorForegroundDay = DEFAULT_ERROR_COLOR_FOREGROUND_DAY;
    private Color mColorErrorBackgroundDay = DEFAULT_ERROR_COLOR_BACKGROUND_DAY;
    private Color mColorErrorForegroundNight = DEFAULT_ERROR_COLOR_FOREGROUND_NIGHT;
    private Color mColorErrorBackgroundNight = DEFAULT_ERROR_COLOR_BACKGROUND_NIGHT;
    private Color mColorDateTagBackgroundDay = DEFAULT_DATE_TAG_COLOR_BACKGROUND_DAY;
    private Color mColorDateTagForegroundNight = DEFAULT_DATE_TAG_COLOR_FOREGROUND_NIGHT;
    private Color mColorDateTagBackgroundNight = DEFAULT_DATE_TAG_COLOR_BACKGROUND_NIGHT;
    private Color mColorDateTagForegroundDay = DEFAULT_DATE_TAG_COLOR_FOREGROUND_DAY;
    private Color mColorTagForegroundDay = DEFAULT_TAG_COLOR_FOREGROUND_DAY;
    private Color mColorTagBackgroundDay = DEFAULT_TAG_COLOR_BACKGROUND_DAY;
    private Color mColorTagForegroundNight = DEFAULT_TAG_COLOR_FOREGROUND_NIGHT;
    private Color mColorTagBackgroundNight = DEFAULT_TAG_COLOR_BACKGROUND_NIGHT;
    private TextAlignment mTextAlignmentLevelLogInfo = DEFAULT_TEXT_ALIGNMENT_LEVEL_LOG_INFO;
    private TextAlignment mTextAlignmentDateTag = DEFAULT_TEXT_ALIGNMENT_DATE_TAG;
    private TextAlignment mTextAlignmentTag = DEFAULT_TEXT_ALIGNMENT_TAG;
    private TextAlignment mTextAlignmentMessage = DEFAULT_TEXT_ALIGNMENT_MESSAGE;

    private static LogManager sInstance;

    private LogManager() {
        resetSettings();
    }

    public static LogManager getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new LogManager();
                }
            }
        }

        return sInstance;
    }

    public boolean isLoggable(LogLevel logLevel) {
        return mMinLogLevel <= logLevel.getLevel();
    }

    public boolean isNotLoggable(LogLevel logLevel) {
        return mMinLogLevel > logLevel.getLevel();
    }

    public void resetSettings() {
        this.mMinLogLevel = DEFAULT_MIN_LOG_LEVEL;
        this.mShowLogLevel = DEFAULT_SHOW_LOG_LEVEL;
        this.mShowDate = DEFAULT_SHOW_DATE;
        this.mShowTag = DEFAULT_SHOW_TAG;
        this.mDatePattern = DEFAULT_DATE_PATTERN;
        this.mDateFormat = DEFAULT_DATE_FORMAT;
        this.mColorInternalErrorForegroundDay = DEFAULT_INTERNAL_ERROR_COLOR_FOREGROUND_DAY;
        this.mColorInternalErrorBackgroundDay = DEFAULT_INTERNAL_ERROR_COLOR_BACKGROUND_DAY;
        this.mColorInternalErrorForegroundNight = DEFAULT_INTERNAL_ERROR_COLOR_FOREGROUND_NIGHT;
        this.mColorInternalErrorBackgroundNight = DEFAULT_INTERNAL_ERROR_COLOR_BACKGROUND_NIGHT;
        this.mColorVerboseForegroundDay = DEFAULT_VERBOSE_COLOR_FOREGROUND_DAY;
        this.mColorVerboseBackgroundDay = DEFAULT_VERBOSE_COLOR_BACKGROUND_DAY;
        this.mColorVerboseForegroundNight = DEFAULT_VERBOSE_COLOR_FOREGROUND_NIGHT;
        this.mColorVerboseBackgroundNight = DEFAULT_VERBOSE_COLOR_BACKGROUND_NIGHT;
        this.mColorInfoForegroundDay = DEFAULT_INFO_COLOR_FOREGROUND_DAY;
        this.mColorInfoBackgroundDay = DEFAULT_INFO_COLOR_BACKGROUND_DAY;
        this.mColorInfoForegroundNight = DEFAULT_INFO_COLOR_FOREGROUND_NIGHT;
        this.mColorInfoBackgroundNight = DEFAULT_INFO_COLOR_BACKGROUND_NIGHT;
        this.mColorDebugForegroundDay = DEFAULT_DEBUG_COLOR_FOREGROUND_DAY;
        this.mColorDebugBackgroundDay = DEFAULT_DEBUG_COLOR_BACKGROUND_DAY;
        this.mColorDebugForegroundNight = DEFAULT_DEBUG_COLOR_FOREGROUND_NIGHT;
        this.mColorDebugBackgroundNight = DEFAULT_DEBUG_COLOR_BACKGROUND_NIGHT;
        this.mColorWarningForegroundDay = DEFAULT_WARNING_COLOR_FOREGROUND_DAY;
        this.mColorWarningBackgroundDay = DEFAULT_WARNING_COLOR_BACKGROUND_DAY;
        this.mColorWarningForegroundNight = DEFAULT_WARNING_COLOR_FOREGROUND_NIGHT;
        this.mColorWarningBackgroundNight = DEFAULT_WARNING_COLOR_BACKGROUND_NIGHT;
        this.mColorErrorForegroundDay = DEFAULT_ERROR_COLOR_FOREGROUND_DAY;
        this.mColorErrorBackgroundDay = DEFAULT_ERROR_COLOR_BACKGROUND_DAY;
        this.mColorErrorForegroundNight = DEFAULT_ERROR_COLOR_FOREGROUND_NIGHT;
        this.mColorErrorBackgroundNight = DEFAULT_ERROR_COLOR_BACKGROUND_NIGHT;
        this.mColorDateTagForegroundDay = DEFAULT_DATE_TAG_COLOR_FOREGROUND_DAY;
        this.mColorDateTagBackgroundDay = DEFAULT_DATE_TAG_COLOR_BACKGROUND_DAY;
        this.mColorDateTagForegroundNight = DEFAULT_DATE_TAG_COLOR_FOREGROUND_NIGHT;
        this.mColorDateTagBackgroundNight = DEFAULT_DATE_TAG_COLOR_BACKGROUND_NIGHT;
        this.mColorTagForegroundDay = DEFAULT_TAG_COLOR_FOREGROUND_DAY;
        this.mColorTagBackgroundDay = DEFAULT_TAG_COLOR_BACKGROUND_DAY;
        this.mColorTagForegroundNight = DEFAULT_TAG_COLOR_FOREGROUND_NIGHT;
        this.mColorTagBackgroundNight = DEFAULT_TAG_COLOR_BACKGROUND_NIGHT;
        this.mTextAlignmentLevelLogInfo = DEFAULT_TEXT_ALIGNMENT_LEVEL_LOG_INFO;
        this.mTextAlignmentDateTag = DEFAULT_TEXT_ALIGNMENT_DATE_TAG;
        this.mTextAlignmentTag = DEFAULT_TEXT_ALIGNMENT_TAG;
        this.mTextAlignmentMessage = DEFAULT_TEXT_ALIGNMENT_MESSAGE;
    }

    public void setMinLogLevel(LogLevel mMinLogLevel) {
        setMinLogLevel(mMinLogLevel.getLevel());
    }

    public void setMinLogLevel(int newMinLevel) {
        if (newMinLevel < LogLevel.MIN_LOG_LEVEL) {
            throw new IllegalArgumentException(
                    "New min log level cannot be lower than lowest level.");
        }
        if (newMinLevel > LogLevel.MAX_LOG_LEVEL) {
            throw new IllegalArgumentException(
                    "New max log level cannot be grater than highest level.");
        }

        mMinLogLevel = newMinLevel;
    }

    public boolean isShowLogLevel() {
        return mShowLogLevel;
    }

    public void setShowLogLevel(boolean showLogLevel) {
        mShowLogLevel = showLogLevel;
    }

    public boolean isShowDate() {
        return mShowDate;
    }

    public void setShowDate(boolean showDate) {
        mShowDate = showDate;
    }

    public boolean isShowTag() {
        return mShowTag;
    }

    public void setShowTag(boolean showTag) {
        mShowTag = showTag;
    }

    public String getDatePattern() {
        return mDatePattern;
    }

    public void setDatePattern(String pattern) {
        synchronized (DATE_LOCK) {
            mDatePattern = pattern;
            mDateFormat.applyPattern(mDatePattern);
        }
    }

    public String formatDate(Object date) {
        synchronized (DATE_LOCK) {
            return mDateFormat.format(date);
        }
    }

    public Color getColorInternalErrorForeground() {
        return PrintableManager.getInstance().isDayTheme() ?
                mColorInternalErrorForegroundDay :
                mColorInternalErrorForegroundNight;
    }

    public Color getColorInternalErrorBackground() {
        return PrintableManager.getInstance().isDayTheme() ?
                mColorInternalErrorBackgroundDay :
                mColorInternalErrorBackgroundNight;
    }

    public Color getColorVerboseForeground() {
        return PrintableManager.getInstance().isDayTheme() ?
                mColorVerboseForegroundDay :
                mColorVerboseForegroundNight;
    }

    public Color getColorVerboseBackground() {
        return PrintableManager.getInstance().isDayTheme() ?
                mColorVerboseBackgroundDay :
                mColorVerboseBackgroundNight;
    }

    public Color getColorInfoForeground() {
        return PrintableManager.getInstance().isDayTheme() ?
                mColorInfoForegroundDay :
                mColorInfoForegroundNight;
    }

    public Color getColorInfoBackground() {
        return PrintableManager.getInstance().isDayTheme() ?
                mColorInfoBackgroundDay :
                mColorInfoBackgroundNight;
    }

    public Color getColorDebugForeground() {
        return PrintableManager.getInstance().isDayTheme() ?
                mColorDebugForegroundDay :
                mColorDebugForegroundNight;
    }

    public Color getColorDebugBackground() {
        return PrintableManager.getInstance().isDayTheme() ?
                mColorDebugBackgroundDay :
                mColorDebugBackgroundNight;
    }

    public Color getColorWarningForeground() {
        return PrintableManager.getInstance().isDayTheme() ?
                mColorWarningForegroundDay :
                mColorWarningForegroundNight;
    }

    public Color getColorWarningBackground() {
        return PrintableManager.getInstance().isDayTheme() ?
                mColorWarningBackgroundDay :
                mColorWarningBackgroundNight;
    }

    public Color getColorErrorForeground() {
        return PrintableManager.getInstance().isDayTheme() ?
                mColorErrorForegroundDay :
                mColorErrorForegroundNight;
    }

    public Color getColorErrorBackground() {
        return PrintableManager.getInstance().isDayTheme() ?
                mColorErrorBackgroundDay :
                mColorErrorBackgroundNight;
    }

    public Color getColorDateTagForeground() {
        return PrintableManager.getInstance().isDayTheme() ?
                mColorDateTagForegroundDay :
                mColorDateTagForegroundNight;
    }

    public Color getColorDateTagBackground() {
        return PrintableManager.getInstance().isDayTheme() ?
                mColorDateTagBackgroundDay :
                mColorDateTagBackgroundNight;
    }

    public Color getColorTagForeground() {
        return PrintableManager.getInstance().isDayTheme() ?
                mColorTagForegroundDay :
                mColorTagForegroundNight;
    }

    public Color getColorTagBackground() {
        return PrintableManager.getInstance().isDayTheme() ?
                mColorTagBackgroundDay :
                mColorTagBackgroundNight;
    }

    public Color getColorInternalErrorForegroundDay() {
        return mColorInternalErrorForegroundDay;
    }

    public void setColorInternalErrorForegroundDay(Color colorInternalErrorForegroundDay) {
        this.mColorInternalErrorForegroundDay = colorInternalErrorForegroundDay;
    }

    public Color getColorInternalErrorBackgroundDay() {
        return mColorInternalErrorBackgroundDay;
    }

    public void setColorInternalErrorBackgroundDay(Color colorInternalErrorBackgroundDay) {
        this.mColorInternalErrorBackgroundDay = colorInternalErrorBackgroundDay;
    }

    public Color getColorInternalErrorForegroundNight() {
        return mColorInternalErrorForegroundNight;
    }

    public void setColorInternalErrorForegroundNight(Color colorInternalErrorForegroundNight) {
        this.mColorInternalErrorForegroundNight = colorInternalErrorForegroundNight;
    }

    public Color getColorInternalErrorBackgroundNight() {
        return mColorInternalErrorBackgroundNight;
    }

    public void setColorInternalErrorBackgroundNight(Color colorInternalErrorBackgroundNight) {
        this.mColorInternalErrorBackgroundNight = colorInternalErrorBackgroundNight;
    }

    public Color getColorVerboseForegroundDay() {
        return mColorVerboseForegroundDay;
    }

    public void setColorVerboseForegroundDay(Color colorVerboseForegroundDay) {
        this.mColorVerboseForegroundDay = colorVerboseForegroundDay;
    }

    public Color getColorVerboseBackgroundDay() {
        return mColorVerboseBackgroundDay;
    }

    public void setColorVerboseBackgroundDay(Color colorVerboseBackgroundDay) {
        this.mColorVerboseBackgroundDay = colorVerboseBackgroundDay;
    }

    public Color getColorVerboseForegroundNight() {
        return mColorVerboseForegroundNight;
    }

    public void setColorVerboseForegroundNight(Color colorVerboseForegroundNight) {
        this.mColorVerboseForegroundNight = colorVerboseForegroundNight;
    }

    public Color getColorVerboseBackgroundNight() {
        return mColorVerboseBackgroundNight;
    }

    public void setColorVerboseBackgroundNight(Color colorVerboseBackgroundNight) {
        this.mColorVerboseBackgroundNight = colorVerboseBackgroundNight;
    }

    public Color getColorInfoForegroundDay() {
        return mColorInfoForegroundDay;
    }

    public void setColorInfoForegroundDay(Color colorInfoForegroundDay) {
        this.mColorInfoForegroundDay = colorInfoForegroundDay;
    }

    public Color getColorInfoBackgroundDay() {
        return mColorInfoBackgroundDay;
    }

    public void setColorInfoBackgroundDay(Color colorInfoBackgroundDay) {
        this.mColorInfoBackgroundDay = colorInfoBackgroundDay;
    }

    public Color getColorInfoForegroundNight() {
        return mColorInfoForegroundNight;
    }

    public void setColorInfoForegroundNight(Color colorInfoForegroundNight) {
        this.mColorInfoForegroundNight = colorInfoForegroundNight;
    }

    public Color getColorInfoBackgroundNight() {
        return mColorInfoBackgroundNight;
    }

    public void setColorInfoBackgroundNight(Color colorInfoBackgroundNight) {
        this.mColorInfoBackgroundNight = colorInfoBackgroundNight;
    }

    public Color getColorDebugForegroundDay() {
        return mColorDebugForegroundDay;
    }

    public void setColorDebugForegroundDay(Color colorDebugForegroundDay) {
        this.mColorDebugForegroundDay = colorDebugForegroundDay;
    }

    public Color getColorDebugBackgroundDay() {
        return mColorDebugBackgroundDay;
    }

    public void setColorDebugBackgroundDay(Color colorDebugBackgroundDay) {
        this.mColorDebugBackgroundDay = colorDebugBackgroundDay;
    }

    public Color getColorDebugForegroundNight() {
        return mColorDebugForegroundNight;
    }

    public void setColorDebugForegroundNight(Color colorDebugForegroundNight) {
        this.mColorDebugForegroundNight = colorDebugForegroundNight;
    }

    public Color getColorDebugBackgroundNight() {
        return mColorDebugBackgroundNight;
    }

    public void setColorDebugBackgroundNight(Color colorDebugBackgroundNight) {
        this.mColorDebugBackgroundNight = colorDebugBackgroundNight;
    }

    public Color getColorWarningForegroundDay() {
        return mColorWarningForegroundDay;
    }

    public void setColorWarningForegroundDay(Color colorWarningForegroundDay) {
        this.mColorWarningForegroundDay = colorWarningForegroundDay;
    }

    public Color getColorWarningBackgroundDay() {
        return mColorWarningBackgroundDay;
    }

    public void setColorWarningBackgroundDay(Color colorWarningBackgroundDay) {
        this.mColorWarningBackgroundDay = colorWarningBackgroundDay;
    }

    public Color getColorWarningForegroundNight() {
        return mColorWarningForegroundNight;
    }

    public void setColorWarningForegroundNight(Color colorWarningForegroundNight) {
        this.mColorWarningForegroundNight = colorWarningForegroundNight;
    }

    public Color getColorWarningBackgroundNight() {
        return mColorWarningBackgroundNight;
    }

    public void setColorWarningBackgroundNight(Color colorWarningBackgroundNight) {
        this.mColorWarningBackgroundNight = colorWarningBackgroundNight;
    }

    public Color getColorErrorForegroundDay() {
        return mColorErrorForegroundDay;
    }

    public void setColorErrorForegroundDay(Color colorErrorForegroundDay) {
        this.mColorErrorForegroundDay = colorErrorForegroundDay;
    }

    public Color getColorErrorBackgroundDay() {
        return mColorErrorBackgroundDay;
    }

    public void setColorErrorBackgroundDay(Color colorErrorBackgroundDay) {
        this.mColorErrorBackgroundDay = colorErrorBackgroundDay;
    }

    public Color getColorErrorForegroundNight() {
        return mColorErrorForegroundNight;
    }

    public void setColorErrorForegroundNight(Color colorErrorForegroundNight) {
        this.mColorErrorForegroundNight = colorErrorForegroundNight;
    }

    public Color getColorErrorBackgroundNight() {
        return mColorErrorBackgroundNight;
    }

    public void setColorErrorBackgroundNight(Color colorErrorBackgroundNight) {
        this.mColorErrorBackgroundNight = colorErrorBackgroundNight;
    }

    public Color getColorDateTagForegroundDay() {
        return mColorDateTagForegroundDay;
    }

    public void setColorDateTagForegroundDay(Color colorDateTagForegroundDay) {
        this.mColorDateTagForegroundDay = colorDateTagForegroundDay;
    }

    public Color getColorDateTagBackgroundDay() {
        return mColorDateTagBackgroundDay;
    }

    public void setColorDateTagBackgroundDay(Color colorDateTagBackgroundDay) {
        this.mColorDateTagBackgroundDay = colorDateTagBackgroundDay;
    }

    public Color getColorDateTagForegroundNight() {
        return mColorDateTagForegroundNight;
    }

    public void setColorDateTagForegroundNight(Color colorDateTagForegroundNight) {
        this.mColorDateTagForegroundNight = colorDateTagForegroundNight;
    }

    public Color getColorDateTagBackgroundNight() {
        return mColorDateTagBackgroundNight;
    }

    public void setColorDateTagBackgroundNight(Color colorDateTagBackgroundNight) {
        this.mColorDateTagBackgroundNight = colorDateTagBackgroundNight;
    }

    public Color getColorTagForegroundDay() {
        return mColorTagForegroundDay;
    }

    public void setColorTagForegroundDay(Color colorTagForegroundDay) {
        this.mColorTagForegroundDay = colorTagForegroundDay;
    }

    public Color getColorTagBackgroundDay() {
        return mColorTagBackgroundDay;
    }

    public void setColorTagBackgroundDay(Color colorTagBackgroundDay) {
        this.mColorTagBackgroundDay = colorTagBackgroundDay;
    }

    public Color getColorTagForegroundNight() {
        return mColorTagForegroundNight;
    }

    public void setColorTagForegroundNight(Color colorTagForegroundNight) {
        this.mColorTagForegroundNight = colorTagForegroundNight;
    }

    public Color getColorTagBackgroundNight() {
        return mColorTagBackgroundNight;
    }

    public void setColorTagBackgroundNight(Color colorTagBackgroundNight) {
        this.mColorTagBackgroundNight = colorTagBackgroundNight;
    }

    public TextAlignment getTextAlignmentLevelLogInfo() {
        return mTextAlignmentLevelLogInfo;
    }

    public void setTextAlignmentLevelLogInfo(TextAlignment textAlignmentLevelLogInfo) {
        this.mTextAlignmentLevelLogInfo = textAlignmentLevelLogInfo;
    }

    public TextAlignment getTextAlignmentDateTag() {
        return mTextAlignmentDateTag;
    }

    public void setTextAlignmentDateTag(TextAlignment textAlignmentDateTag) {
        this.mTextAlignmentDateTag = textAlignmentDateTag;
    }

    public TextAlignment getTextAlignmentTag() {
        return mTextAlignmentTag;
    }

    public void setTextAlignmentTag(TextAlignment textAlignmentTag) {
        this.mTextAlignmentTag = textAlignmentTag;
    }

    public TextAlignment getTextAlignmentMessage() {
        return mTextAlignmentMessage;
    }

    public void setTextAlignmentMessage(TextAlignment textAlignmentMessage) {
        this.mTextAlignmentMessage = textAlignmentMessage;
    }
}
