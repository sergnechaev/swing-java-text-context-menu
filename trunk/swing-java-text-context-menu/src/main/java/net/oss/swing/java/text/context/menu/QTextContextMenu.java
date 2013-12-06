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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Sergey Nechaev <serg.nechaev@gmail.com>
 * 
 *         https://code.google.com/p/swing-java-text-context-menu/
 * 
 */
public final class QTextContextMenu extends MouseAdapter {

	private static final String CUT_PNG = "cut.gif";

	private static final String ARROW_REDO_PNG = "redo.gif";

	private static final String ARROW_UNDO_PNG = "undo.gif";

	private static final String PASTE_PNG = "paste.gif";

	private static final String COPY_PNG = "copy.gif";

	private static String ICONS_PACKAGE_PATH = "/net/oss/swing/java/text/context/menu/";

	private static final Log log = LogFactory.getLog(QTextContextMenu.class);

	private JTextComponent component;

	private UndoManager undo;

	private AdditionalMenu additionalMenu;

	public static interface AdditionalMenu {
		void process(JPopupMenu menu);
	}

	public QTextContextMenu(JTextComponent component, AdditionalMenu additionalMenu, String iconsPackagePath) {

		this.additionalMenu = additionalMenu;
		this.component = component;

		if (iconsPackagePath != null) {
			ICONS_PACKAGE_PATH = iconsPackagePath;
		}

		this.initUndoManager();

		component.addMouseListener(this);
	}

	public QTextContextMenu(JTextComponent component) {

		this(component, null, null);

	}

	@SuppressWarnings("serial")
	private void initUndoManager() {

		undo = new UndoManager();

		final Document doc = component.getDocument();

		// Listen for undo and redo events
		doc.addUndoableEditListener(new UndoableEditListener() {
			public void undoableEditHappened(UndoableEditEvent evt) {
				undo.addEdit(evt.getEdit());
			}
		});

		// Create an undo action and add it to the text component
		component.getActionMap().put("Undo", new AbstractAction("Undo") {
			public void actionPerformed(ActionEvent evt) {
				try {
					if (undo.canUndo()) {
						undo.undo();
					}
				} catch (CannotUndoException e) {
				}
			}
		});

		// Bind the undo action to ctl-Z
		component.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "Undo");

		// Create a redo action and add it to the text component
		component.getActionMap().put("Redo", new AbstractAction("Redo") {
			public void actionPerformed(ActionEvent evt) {
				try {
					if (undo.canRedo()) {
						undo.redo();
					}
				} catch (CannotRedoException e) {
				}
			}
		});

		// Bind the redo action to ctl-Y
		component.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "Redo");

	}

	private void showMenu(MouseEvent e) {

		this.component.requestFocus();

		final JPopupMenu menu = new JPopupMenu();

		final boolean isTextSelected = isNotBlank(component.getSelectedText());
		final boolean isText = isNotBlank(component.getText());
		final boolean isEditable = component.isEditable();
		final boolean isEnabled = component.isEnabled();

		JMenuItem item = null;
		KeyStroke stroke = null;

		// Cut
		item = QTextContextMenuItem.getItem("Cut");
		stroke = KeyStroke.getKeyStroke("control X");
		item.setAccelerator(stroke);
		item.setIcon(getIcon(CUT_PNG));
		item.setEnabled(isTextSelected && isEditable && isEnabled);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				component.cut();
			}
		});

		menu.add(item);

		// Copy
		item = QTextContextMenuItem.getItem("Copy");
		stroke = KeyStroke.getKeyStroke("control C");
		item.setAccelerator(stroke);
		item.setIcon(getIcon(COPY_PNG));
		item.setEnabled(isTextSelected);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				component.copy();
			}
		});

		menu.add(item);

		// Paste
		item = QTextContextMenuItem.getItem("Paste");
		stroke = KeyStroke.getKeyStroke("control V");
		item.setAccelerator(stroke);
		item.setIcon(getIcon(PASTE_PNG));
		item.setEnabled(isEditable && isEnabled);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				component.paste();
			}
		});

		menu.add(item);

		menu.addSeparator();

		// Undo
		item = QTextContextMenuItem.getItem("Undo");
		stroke = KeyStroke.getKeyStroke("control Z");
		item.setAccelerator(stroke);
		item.setIcon(getIcon(ARROW_UNDO_PNG));
		item.setEnabled(undo.canUndo() && isEditable && isEnabled);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				undo.undo();
			}
		});

		menu.add(item);

		// Redo
		item = QTextContextMenuItem.getItem("Redo");
		stroke = KeyStroke.getKeyStroke("control Y");
		item.setAccelerator(stroke);
		item.setIcon(getIcon(ARROW_REDO_PNG));
		item.setEnabled(undo.canRedo() && isEditable && isEnabled);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				undo.redo();
			}
		});

		menu.add(item);

		menu.addSeparator();

		// Select All
		item = QTextContextMenuItem.getItem("Select All");
		stroke = KeyStroke.getKeyStroke("control A");
		item.setAccelerator(stroke);
		item.setEnabled(isText && isEnabled);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				component.selectAll();
			}
		});

		menu.add(item);

		// additional menu
		if (additionalMenu != null) {
			additionalMenu.process(menu);
		}

		menu.show(e.getComponent(), e.getX(), e.getY());

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.isPopupTrigger()) {
			this.showMenu(e);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.isPopupTrigger())
			this.showMenu(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.isPopupTrigger())
			this.showMenu(e);
	}

	private static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	private static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	public static Icon getIcon(String name) {

		try {
			return new ImageIcon(ImageIO.read(QTextContextMenu.class.getResource(ICONS_PACKAGE_PATH + name)));
		} catch (IOException e) {
			log.error("Error: icon was not loaded: " + name, e);
		}

		return null;

	}

}