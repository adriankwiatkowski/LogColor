[![](https://jitpack.io/v/adriankwiatkowski/LogColor.svg)](https://jitpack.io/#adriankwiatkowski/LogColor)

# What is LogColor?
LogColor is a highly customizable logging library.

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
    // implementation 'com.github.adriankwiatkowski:LogColor:ReleaseVersion'
    // You can use latest version that has not been released by using branch-SNAPSHOT.
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
    // You have to replace version number in path as well as file name.
    compile urlFile('https://github.com/adriankwiatkowski/LogColor/releases/download/v1.0/LogColor-1.0.jar', "LogColor")
}
```

# How do I use LogColor?
Simple use cases will look something like this:

Setting up Printer:
```java
PrintableManager printableManager = PrintableManager.getInstance();
printableManager.setPrintable(PrintableType.CONSOLE);
printableManager.setPrintable(PrintableType.WINDOW);
printableManager.setPrintable(new Printable(outputStream) { ... });
```

Setting up Printer default configuration:
```java
// Applies to current and further Printable instances in PrintableManager.
PrintableManager.getInstance()
    .setDefaultFormat(AnsiColor.ANSI_BLACK,
        AnsiColor.ANSI_BRIGHT_BG_BLUE,
        TextAlignment.CENTER);

// Applies only to current Printable instance.
Printable printable = PrintableManager.getInstance().getPrintable();
printable.setDefaultFormat(AnsiColor.ANSI_BLACK, AnsiColor.ANSI_BRIGHT_BG_BLUE); 
```

Printing using Printer (recommended way, because it uses multithreading by default):
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

Printing using directly Printable instance:
```java
Printable printable = PrintableManager.getInstance().getPrintable();
// Although, using this method you cannot specify colors and text alignment
// you can set default format for future prints on this Printable like this.
// Remember this format will only be used in this particular instance of Printable.
printable.setDefaultFormat(AnsiColor.ANSI_BLACK,
    AnsiColor.ANSI_BRIGHT_BG_BLUE,
    TextAlignment.LEFT);

// If you want to format current and all future instances of Printable you have to set it globally
// using PrintableManager class.
PrintableManager.getInstance().setDefaultFormat(AnsiColor.ANSI_BLACK,
    AnsiColor.ANSI_BRIGHT_BG_BLUE,
    TextAlignment.CENTER);

// Now our prints will be formatted using specified format, that we set earlier.
printable.println("3");
printable.println();
printable.print("10");
```

Printing using System.out:
```java
// We can set our own out PrintStream like this.
PrintableManager printableManager = PrintableManager.getInstance();
printableManager.setPrintable(PrintableType.CONSOLE);
// This line of code will redirect output of System.out.
System.setOut(printableManager.getPrintable());

// No we can use System.out directly, as well as all of its methods.
System.out.print("Default text");
System.out.println(" And another one that will appear on the same line.");

// Although, using this method you cannot specify colors and text alignment
// you can set default format for future prints on this Printable like this.
// Remember this format will only be used in this particular instance of Printable.
printableManager.getPrintable()
    .setDefaultFormat(AnsiColor.ANSI_BLACK,
        AnsiColor.ANSI_BRIGHT_BG_BLUE,
        TextAlignment.LEFT);

// If you want to format current and all future instances of Printable
// you have to set it globally using PrintableManager class.
PrintableManager.getInstance().setDefaultFormat(AnsiColor.ANSI_BLACK,
    AnsiColor.ANSI_BRIGHT_BG_BLUE,
    TextAlignment.CENTER);

// Now our prints will be formatted using specified format, that we set earlier.
System.out.print("Formatted text");
System.out.println(" And another one that will appear on the same line.");
System.out.println(
    "And you don't have to format every line of code, just good old System.out!");
```

#### Using Printable Manager class to customize logger
This will be mostly done on initialization if you want to change default settings.
```java
PrintableManager printableManager = PrintableManager.getInstance();

printableManager.setNightTheme();
printableManager.setDayTheme();

printableManager.setPrintable(PrintableType.WINDOW);

// You can even set your on Printable implementation to suit your needs.
// You can look at the source code on github to see
// how I implemented PrintableWindow, if you need an inspiration.
OutputStream outputStream;
PrintableManager.getInstance().setPrintable(new Printable(outputStream) {
    @Override
    protected void write(String s) {
    }

    @Override
    public void printForceOnNewLine(String msg) {
    }

    @Override
    public void setDayTheme() {
    }

    @Override
    public void setNightTheme() {
    }
});
```

Logging:
```java
Log.v("Verbose tag", "Useless verbose message.");
Log.i("Info tag", "Informative message.");
Log.d("Debug tag", "Something not working here.");
Log.w("Warning tag", "Warning, take care!");
Log.e("Error tag", "Behold almighty error...");
```

#### Using Log Manager class
```java
LogManager.getInstance().setMinLogLevel(LogLevel.INFO);
...
// Although you don't need to implicitly check if LogLevel you want to print is loggable, it is a good practice.
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


Printer.println(alignedColorBuilder.getText_Flush()));
```

# Colored random generated text
<img src="https://cdn.discordapp.com/attachments/667466573640105995/734020655477162034/unknown.png"/>