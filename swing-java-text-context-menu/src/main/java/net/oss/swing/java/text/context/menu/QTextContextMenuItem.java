/*
The MIT License (MIT)

Copyright (c) 2013 Sergey Nechaev <serg.nechaev@gmail.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package net.oss.swing.java.text.context.menu;

import javax.swing.JMenuItem;

/**
 * 
 * @author Sergey Nechaev <serg.nechaev@gmail.com>
 * 
 *         https://code.google.com/p/swing-java-text-context-menu/
 */
@SuppressWarnings("serial")
public final class QTextContextMenuItem extends JMenuItem {

	private QTextContextMenuItem() {
	}

	private QTextContextMenuItem(String text) {
	}

	public static JMenuItem getItem(String itemName) {
		return getItem(itemName, null);
	}

	/**
	 * @param itemName
	 * @param icon
	 * @return
	 */
	public static JMenuItem getItem(String itemName, String icon) {

		final JMenuItem menuItem = new JMenuItem(itemName);

		if (icon != null) {
			menuItem.setIcon(QTextContextMenu.getIcon(icon));
		}

		return menuItem;

	}

}