## swing-java-text-context-menu - a simple text component context menu ##

Enrich your Java Swing application **text fields, text areas, editors and any other text components** with a great **context menu** that can automatically enable the following options for text manipulation in Swing text fields:

  * **Select all** text.
  * **Cut** to clipboard.
  * **Copy** to clipboard.
  * **Paste** from clipboard.
  * **Undo** changes.
  * **Redo** changes.

The menu items are automatically enabled and disabled based on the current context and data.

The default icons are included (please, see the screenshots below).

### How to use ###

JTextField:
```
new QTextContextMenu(myTextField);
```

JPasswordField:
```
new QTextContextMenu(myPasswordField);
```

JComboBox (editable):
```
new QTextContextMenu((JTextComponent) myComboBox.getEditor().getEditorComponent());
```

JSpinner:
```
new QTextContextMenu((JTextComponent) mySpinner.getEditor().getComponent(0));
```

JTextArea:
```
new QTextContextMenu(myTextArea);
```

JTextPane:
```
new QTextContextMenu(myTextPane);
```

JEditorPane:
```
new QTextContextMenu(myEditorPane);
```

### Download ###

Browse the source code using the **Source** tab at the top or
download JAR file compiled for JDK 1.6 here: http://google-code.s3.amazonaws.com/swing-java-text-context-menu-1.0.0.jar

### Screenshots ###

Windows 8.1

![http://google-code.s3.amazonaws.com/context-menu-demo-win8.1.jpg](http://google-code.s3.amazonaws.com/context-menu-demo-win8.1.jpg)

Ubuntu Linux

![http://google-code.s3.amazonaws.com/context-menu-demo-ubuntu.png](http://google-code.s3.amazonaws.com/context-menu-demo-ubuntu.png)

CentOs Linux

![http://google-code.s3.amazonaws.com/context-menu-demo-centos.png](http://google-code.s3.amazonaws.com/context-menu-demo-centos.png)