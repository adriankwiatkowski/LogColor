[![](https://jitpack.io/v/adriankwiatkowski/LogColor.svg)](https://jitpack.io/#adriankwiatkowski/LogColor)

# What is LogColor?
LogColor is a customizable logging library.

# Why LogColor?
- Easy to use
- Customizable
- Logging and printing are done using multithreading
- Built-in support for colors
- Built-in implementation for console and java window
- Supports day/night theme.

# Adding project dependency to gradle

Using jitpack.io:

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
    repositories {
		...
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency
```groovy
dependencies {
    // You can look up version in a badge.
    // implementation 'com.github.adriankwiatkowski:LogColor:Version'
    // You can use latest version that has not been released by using branch-SNAPSHOT
    implementation 'com.github.adriankwiatkowski:LogColor:master-SNAPSHOT'
}
```

More info on https://jitpack.io/

------------

Not Recommended: alternative without using jitpack.io.

```groovy
def urlFile = { url, name ->
    File file = new File("$buildDir/download/${name}.jar")
    file.parentFile.mkdirs()
    if (!file.exists()) {
        new URL(url).withInputStream { downloadStream ->
            file.withOutputStream { fileOut ->
                fileOut << downloadStream
            }
        }
    }
    files(file.absolutePath)
} as Object

dependencies {
    compile urlFile('https://github.com/adriankwiatkowski/LogColor/releases/download/v1.0/LogColor-1.0.jar', "LogColor")
}
```

# How do I use LogColor?
Simple use cases will look something like this:

Printing:
```java
AnsiColor fg = AnsiColor.ANSI_BRIGHT_BLUE;
AnsiColor bg = AnsiColor.ANSI_BRIGHT_BG_WHITE;
TextAlignment center = TextAlignment.CENTER;
int space = 10;
String msg = "Message.";
Printer.println(msg);
Printer.println(fg, msg);
Printer.println(fg, bg, msg);
Printer.println(center, space, msg);
Printer.println(fg, center, space, msg);
Printer.println(fg, bg, center, space, msg);
Printer.print(msg);
Printer.print(fg, msg);
Printer.print(fg, bg, msg);
Printer.print(center, space, msg);
Printer.print(fg, center, space, msg);
Printer.print(fg, bg, center, space, msg);
```

Logging:
```java
Log.v("Verbose tag", "Useless verbose message.");
Log.i("Info tag", "Informative message.");
Log.d("Debug tag", "Something not working here.");
Log.w("Warning tag", "Warning, take care!");
Log.e("Error tag", "Behold almighty error...");
```

#### Using Printable Manager class to customize logger
This will be mostly done on initialization if you want to change default settings.
```java
PrintableManager printableManager = PrintableManager.getInstance();

printableManager.setNightTheme();
printableManager.setDayTheme();

printableManager.setPrintable(PrintableType.WINDOW);
```

##### Set your own printable implementation to suit your needs
```java
printableManager.setPrintable(new Printable() {
    @Override
    public void print(String string) {
    }

    @Override
    public void print(ColorBuilder colorBuilder) {
    }

    @Override
    public void print_flush(ColorBuilder colorBuilder) {
    }

    @Override
    public void setDayTheme() {
    }

    @Override
    public void setNightTheme() {
    }

    @Override
    public void onClose() {
    }
});
```

#### Using Log Manager class
```java
LogManager.getInstance().setMinLogLevel(LogLevel.INFO);
...
if (LogManager.getInstance().isLoggable(LogLevel.DEBUG)) {
    Log.d("Class tag", "Important debug message!");
}
```

# Example logs in window
<img src="https://cdn.discordapp.com/attachments/667466573640105995/734017507786227782/unknown.png"/>

# Example logs in console
<img src="https://cdn.discordapp.com/attachments/667466573640105995/734019940759109693/unknown.png"/>

##### Code used for example logs:
```java
Log.v("Verbose tag", "Useless verbose message.");
Log.i("Info tag", "Informative message.");
Log.d("Debug tag", "Something not working here.");
Log.w("Warning tag", "Warning, take care!");
Log.e("Error tag", "Behold almighty error...");
Log.w("Warning tag that will exceed max character limit.",
    "Friendly warning message.");   
```

# Colored indexes
<img src="https://cdn.discordapp.com/attachments/667466573640105995/734020388954308668/unknown.png"/>

##### Code used for image above:
```java
int maxTextLength = Math.max(
    TextUtils.getTextLength(AnsiColor.FOREGROUNDS),
    TextUtils.getTextLength(AnsiColor.BACKGROUNDS));

TextAttribute textAttribute =
    new TextAttribute(textAlignment, maxTextLength + extraSpace);

int textAttributeDuplicateCount = 2;

AlignedColorBuilder alignedColorBuilder = new AlignedColorBuilder
    .Builder(textAttribute, textAttributeDuplicateCount)
    .build();

for (int i = 0; i < AnsiColor.FOREGROUNDS.length; ++i) {
    AnsiColor fg = AnsiColor.FOREGROUNDS[i];
    for (int j = 0; j < AnsiColor.BACKGROUNDS.length; ++j) {
        AnsiColor bg = AnsiColor.BACKGROUNDS[j];
        alignedColorBuilder.appendTextColor(fg, bg, String.valueOf(i), String.valueOf(j));
    }
    alignedColorBuilder.appendColorReset_NewLine();
}

PrintableManager.getInstance().logThread(() ->
    Printer.println(alignedColorBuilder.getText_Flush()));
```

# Colored random generated text
<img src="https://cdn.discordapp.com/attachments/667466573640105995/734020655477162034/unknown.png"/>