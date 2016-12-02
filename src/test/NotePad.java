package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.undo.*;
import javax.swing.event.*;

public class NotePad extends JFrame {
	private UndoManager manager;
	private JFileChooser choose;
	private File selectedfile;
	private JTextArea text;
	private java.awt.Container container;
	private JMenuBar menuBar;
	private JTextField status;
	private boolean newPage = false;
	// File�˵�
	private JMenu menu;
	private JMenuItem newText;
	private JMenuItem open;
	private JCheckBoxMenuItem readOnly;
	private JMenuItem save;
	private JMenuItem saveAs;
	private JMenuItem exit;
	// Edit�˵�
	private JMenu edit;
	private JMenuItem undo;
	private JMenuItem redo;
	private JMenuItem cut;
	private JMenuItem copy;
	private JMenuItem copyAll;
	private JMenuItem paste;
	private JMenuItem delete;
	private JMenuItem deleteAll;
	private JMenuItem selectAll;
	private JMenuItem searchAndReplace; 
	// ��ͼ�˵�
	private JMenu view;
	private JCheckBoxMenuItem wrap;
	private JCheckBoxMenuItem onTop;
	private JMenuItem zoomIn;
	private JMenuItem zoomOut; 
	// ���߲˵�
	private JMenu tool;
	private JMenuItem fontSum;
	private JMenuItem setcolor;
	private JMenuItem setFontColor;
	// �����˵�
	private JMenu help;
	private JMenuItem about;
	// �Ҽ��˵�
	private JPopupMenu pop;
	private JMenuItem popUndo;
	private JMenuItem popRedo;
	private JMenuItem popCut;
	private JMenuItem popCopy;
	private JMenuItem popSelectAll;
	private JMenuItem popCopyAll;
	private JMenuItem popPaste;
	private JMenuItem popDelete;
	private JMenuItem popClear;

	public NotePad() {
		super("���±�");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		container = getContentPane();
		container.setLayout(new BorderLayout(0, -2));
		manager = new UndoManager();
		text = new JTextArea();
		text.setFont(new Font("����", Font.PLAIN, 14));
		text.getDocument().addUndoableEditListener(manager);
		text.setTabSize(2);
		text.setSelectedTextColor(Color.WHITE);
		text.setSelectionColor(Color.DARK_GRAY);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setCaretColor(Color.RED);
		text.setMargin(new Insets(3, 10, 3, 7));
		text.setDragEnabled(true);
		container.add(new JScrollPane(text,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		menuBar = new JMenuBar();
		// File�˵�
		menu = new JMenu("�ļ�(F)");
		menu.setMnemonic('F');
		newText = new JMenuItem("�½�(N)");
		newText.setMnemonic('N');
		newText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (selectedfile == null
						&& !text.getText().equals("")
						&& JOptionPane.showConfirmDialog(container, "�Ƿ񱣴�",
								"�Ƿ񱣴�", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					choose = new JFileChooser();
					int state = choose.showSaveDialog(container);
					if (state == JFileChooser.APPROVE_OPTION) {
						try {
							File file = choose.getSelectedFile();
							FileWriter os = new FileWriter(file);
							os
									.write(text.getText(), 0, text.getText()
											.length());
							os.flush();
							file.createNewFile();
							os.close();
						} catch (IOException e) {
							JOptionPane.showMessageDialog(container, "�����ļ�ʧ��",
									"ERROR", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else if (selectedfile != null
						&& JOptionPane.showConfirmDialog(container, "�Ƿ񱣴�",
								"�Ƿ񱣴�", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					try {
						FileWriter os = new FileWriter(selectedfile);
						os.write(text.getText(), 0, text.getText().length());
						os.flush();
						os.close();
					} catch (IOException e) {
					}
				}
				text.setText("");
				newPage = true;
				selectedfile = null;
				status.setText("");
			}
		});
		open = new JMenuItem("��(O)");
		open.setMnemonic('O');
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (selectedfile != null
						&& JOptionPane.showConfirmDialog(container, "�Ƿ񱣴�",
								"�Ƿ񱣴�", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					try {
						FileWriter os = new FileWriter(selectedfile);
						os.write(text.getText(), 0, text.getText().length());
						os.flush();
						os.close();
						newPage = false;
					} catch (IOException e) {
					}
				} else if (selectedfile == null
						&& !text.getText().equals("")
						&& JOptionPane.showConfirmDialog(container, "�Ƿ񱣴�",
								"�Ƿ񱣴�", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					choose = new JFileChooser();
					int state = choose.showSaveDialog(container);
					if (state == JFileChooser.APPROVE_OPTION) {
						try {
							File file = choose.getSelectedFile();
							FileWriter os = new FileWriter(file);
							os
									.write(text.getText(), 0, text.getText()
											.length());
							os.flush();
							file.createNewFile();
							os.close();
							newPage = false;
						} catch (IOException e) {
							JOptionPane.showMessageDialog(container, "�����ļ�ʧ��",
									"ERROR", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				choose = new JFileChooser();
				choose.setFileFilter(new filter());
				int state = choose.showOpenDialog(container);
				if (state == JFileChooser.APPROVE_OPTION) {
					status.setText(choose.getSelectedFile().toString());
					try {
						selectedfile = choose.getSelectedFile();
						FileReader isr = new FileReader(selectedfile);
						text.read(isr, "");
						isr.close();
						newPage = false;
					} catch (IOException e) {
						JOptionPane.showMessageDialog(container, "���ļ�ʧ��",
								"ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		readOnly = new JCheckBoxMenuItem("ֻ��ģʽ(R)");
		readOnly.setMnemonic('R');
		readOnly.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				if (readOnly.isSelected()) {
					text.setEditable(false);
					cut.setEnabled(false);
					paste.setEnabled(false);
					delete.setEnabled(false);
					deleteAll.setEnabled(false);
					redo.setEnabled(false);
					undo.setEnabled(false);
					searchAndReplace.setEnabled(false);
					popClear.setEnabled(false);
					popCut.setEnabled(false);
					popDelete.setEnabled(false);
					popPaste.setEnabled(false);
					popRedo.setEnabled(false);
					popUndo.setEnabled(false);
				} else {
					text.setEditable(true);
					cut.setEnabled(true);
					paste.setEnabled(true);
					delete.setEnabled(true);
					deleteAll.setEnabled(true);
					redo.setEnabled(true);
					undo.setEnabled(true);
					searchAndReplace.setEnabled(true);
					popClear.setEnabled(true);
					popCut.setEnabled(true);
					popDelete.setEnabled(true);
					popPaste.setEnabled(true);
					popRedo.setEnabled(true);
					popUndo.setEnabled(true);
				}
			}
		});
		save = new JMenuItem("����(S)");
		save.setMnemonic('S');
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (newPage == true || selectedfile == null) {
					choose = new JFileChooser();
					int state = choose.showSaveDialog(container);
					if (state == JFileChooser.APPROVE_OPTION) {
						try {
							File file = choose.getSelectedFile();
							FileWriter os = new FileWriter(file);
							os
									.write(text.getText(), 0, text.getText()
											.length());
							os.flush();
							file.createNewFile();
							os.close();
							newPage = false;
						} catch (IOException e) {
							JOptionPane.showMessageDialog(container, "�����ļ�ʧ��",
									"ERROR", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else if (newPage == false && selectedfile != null) {
					try {
						FileWriter os = new FileWriter(selectedfile);
						os.write(text.getText(), 0, text.getText().length());
						os.flush();
						os.close();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(container, "�����ļ�ʧ��",
								"ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		saveAs = new JMenuItem("���Ϊ(A)");
		saveAs.setMnemonic('A');
		saveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				choose = new JFileChooser();
				int state = choose.showSaveDialog(container);
				if (state == JFileChooser.APPROVE_OPTION) {
					try {
						File file = choose.getSelectedFile();
						FileWriter os = new FileWriter(file);
						os.write(text.getText(), 0, text.getText().length());
						os.flush();
						file.createNewFile();
						os.close();
						newPage = false;
					} catch (IOException e) {
						JOptionPane.showMessageDialog(container, "�����ļ�ʧ��",
								"ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		exit = new JMenuItem("�˳�(Q)");
		exit.setMnemonic('Q');
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		menu.add(newText);
		menu.add(open);
		menu.add(readOnly);
		menu.add(save);
		menu.add(saveAs);
		menu.add(exit);
		// Edit�˵�
		edit = new JMenu("�༭(E)");
		edit.setMnemonic('E');
		undo = new JMenuItem("����(U)");
		undo.setMnemonic('U');
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (manager.canUndo())
					manager.undo();
			}
		});
		redo = new JMenuItem("����(Y)");
		redo.setMnemonic('Y');
		redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (manager.canRedo())
					manager.redo();
			}
		});
		cut = new JMenuItem("����(X)");
		cut.setMnemonic('X');
		cut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.cut();
			}
		});
		copy = new JMenuItem("����(C)");
		copy.setMnemonic('C');
		copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.copy();
			}
		});
		copyAll = new JMenuItem("��������(Z)");
		copyAll.setMnemonic('Z');
		copyAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.selectAll();
				text.copy();
			}
		});
		paste = new JMenuItem("ճ��(V)");
		paste.setMnemonic('V');
		paste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.paste();
			}
		});
		delete = new JMenuItem("ɾ��(D)");
		delete.setMnemonic('D');
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.replaceRange("", text.getSelectionStart(), text
						.getSelectionEnd());
			}
		});
		deleteAll = new JMenuItem("ɾ������(W)");
		deleteAll.setMnemonic('W');
		deleteAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.replaceRange("", 0, text.getText().length());
			}
		});
		selectAll = new JMenuItem("ѡ������(B)");
		selectAll.setMnemonic('B');
		selectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.selectAll();
			}
		});
		searchAndReplace = new JMenuItem("�������滻(M)");
		searchAndReplace.setMnemonic('M');
		searchAndReplace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				new Search();
			}
		});
		edit.add(undo);
		edit.add(redo);
		edit.addSeparator();
		edit.add(selectAll);
		edit.add(cut);
		edit.add(copy);
		edit.add(copyAll);
		edit.add(paste);
		edit.add(delete);
		edit.add(deleteAll);
		edit.addSeparator();
		edit.add(searchAndReplace);
		// ��ͼ�˵�
		view = new JMenu("��ͼ(V)");
		view.setMnemonic('V');
		wrap = new JCheckBoxMenuItem("�Զ�����(J)");
		wrap.setSelected(true);
		wrap.setMnemonic('J');
		wrap.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				text.setLineWrap(wrap.isSelected());
			}
		});
		onTop = new JCheckBoxMenuItem("���ִ���������");
		onTop.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				keepOnTop(onTop.isSelected());
			}
		});
		zoomIn = new JMenuItem("�Ŵ�ҳ��(I)");
		zoomIn.setMnemonic('I');
		zoomIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String fontName = text.getFont().getFontName();
				int fontSize = text.getFont().getSize();
				int style = text.getFont().getStyle();
				if (fontSize < 70)
					text.setFont(new Font(fontName, style, fontSize + 2));
			}
		});
		zoomOut = new JMenuItem("��Сҳ��(O)");
		zoomOut.setMnemonic('O');
		zoomOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String fontName = text.getFont().getFontName();
				int fontSize = text.getFont().getSize();
				int style = text.getFont().getStyle();
				if (fontSize > 9)
					text.setFont(new Font(fontName, style, fontSize - 1));
			}
		});
		view.add(wrap);
		view.addSeparator();
		view.add(onTop);
		view.addSeparator();
		view.add(zoomIn);
		view.add(zoomOut);
		// ���߲˵�
		tool = new JMenu("����(T)");
		tool.setMnemonic('T');
		fontSum = new JMenuItem("����ͳ��(L)");
		fontSum.setMnemonic('L');
		fontSum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String all = text.getText();
				int space = 0;// �ո�
				int letter = 0;// Ӣ����ĸ
				int digit = 0;// ����
				int enter = 0;// ����
				int other = 0;// ����
				int otherLetter = 0;
				for (int i = 0; i < all.length(); i++) {
					char ch = all.charAt(i);
					if (Character.isDigit(ch))
						digit++;
					else if (Character.isLowerCase(ch)
							|| Character.isUpperCase(ch))
						letter++;
					else if (Character.isLetter(ch))
						otherLetter++;
					else if (Character.isSpaceChar(ch))
						space++;
					else if (ch == 10)
						enter++;
					else
						other++;
				}
				JOptionPane.showMessageDialog(container, "�ַ�������\n\n���֣�" + digit
						+ "\nӢ����ĸ��" + letter + "\n������ĸ:" + otherLetter
						+ "\n�ո�" + space + "\n���У�" + enter + "\n������" + other,
						"�ַ�ͳ��", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		setFontColor = new JMenuItem("����������ɫ");
		setFontColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Color color = JColorChooser.showDialog(container, "����ɫѡ��",
						Color.BLACK);
				text.setForeground(color);
			}
		});
		setcolor = new JMenuItem("���ñ���ɫ(G)");
		setcolor.setMnemonic('G');
		setcolor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Color color = JColorChooser.showDialog(container, "����ɫѡ��",
						Color.WHITE);
				text.setBackground(color);
			}
		});
		tool.add(fontSum); // tool.add(setfont);
		tool.add(setFontColor);
		tool.add(setcolor);
		// �����˵�
		help = new JMenu("����(H)");
		help.setMnemonic('H');
		about = new JMenuItem("����(P)");
		about.setMnemonic('P');
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(container, "Java���±�", "����",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		help.add(about);
		menuBar.add(menu);
		menuBar.add(edit);
		menuBar.add(view);
		menuBar.add(tool);
		menuBar.add(help);
		// popupMenu
		pop = new JPopupMenu();
		popUndo = new JMenuItem("����");
		popUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (manager.canUndo())
					manager.undo();
			}
		});
		popRedo = new JMenuItem("����");
		popRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (manager.canRedo())
					manager.redo();
			}
		});
		popCut = new JMenuItem("����");
		popCut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.cut();
			}
		});
		popCopy = new JMenuItem("����");
		popCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.copy();
			}
		});
		popSelectAll = new JMenuItem("ȫ��ѡ��");
		popSelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.selectAll();
			}
		});
		popCopyAll = new JMenuItem("ȫ������");
		popCopyAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.selectAll();
				text.copy();
			}
		});
		popPaste = new JMenuItem("ճ��");
		popPaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.paste();
			}
		});
		popDelete = new JMenuItem("ɾ��");
		popDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.replaceRange("", text.getSelectionStart(), text
						.getSelectionEnd());
			}
		});
		popClear = new JMenuItem("���");
		popClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.setText("");
			}
		});
		pop.add(popUndo);
		pop.add(popRedo);
		pop.addSeparator();
		pop.add(popCut);
		pop.add(popCopy);
		pop.add(popPaste);
		pop.add(popCopyAll);
		pop.add(popSelectAll);
		pop.add(popDelete);
		pop.add(popClear);
		text.add(pop);
		text.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent event) {
				if (event.isPopupTrigger()) {
					pop.setVisible(true);
					pop.show(text, event.getX(), event.getY());
				}
			}
		});
		status = new JTextField();
		status.setEditable(false);
		container.add(status, BorderLayout.SOUTH);
		setSize(730, 660); // pack();
		container.add(menuBar, BorderLayout.NORTH);
		Dimension frameSize = getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void keepOnTop(boolean b) {
		setAlwaysOnTop(b);
	}

	private class Search extends JFrame {
		private JTabbedPane tab;
		private Container cont;
		// ����
		private JPanel searPanel;
		private JTextField searField;
		private JButton searButton;
		// �滻
		private JPanel replPanel;
		private JPanel buttonPanel;
		private JTextField replField;
		private JButton replButton;
		private JLabel label;
		private JTextField replSear;

		public Search() {
			super("�������滻");
			cont = getContentPane();
			tab = new JTabbedPane(JTabbedPane.TOP);
			// ����
			searPanel = new JPanel(new FlowLayout());
			searField = new JTextField(15);
			searButton = new JButton("������һ��");
			searPanel.add(searField);
			searPanel.add(searButton);
			searButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					String searText = searField.getText().trim();
					if (searText.equals("")) {
						JOptionPane.showMessageDialog(cont, "���Ҵ�Ϊ��", "��ʾ��Ϣ",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						String allText = text.getText();
						int start = allText.indexOf(searText, text
								.getSelectionEnd());
						if (start == -1)
							JOptionPane.showMessageDialog(container,
									"�ѵ����ļ�ĩβ��û���ҵ�\n" + searText, "��ʾ��Ϣ",
									JOptionPane.INFORMATION_MESSAGE);
						else
							text.select(start, start + searText.length());
					}
				}
			});
			// �滻
			replPanel = new JPanel(new GridLayout(3, 1));
			buttonPanel = new JPanel(new FlowLayout());
			label = new JLabel("�滻Ϊ");
			replField = new JTextField(15);
			replSear = new JTextField(15);
			replButton = new JButton("�滻��һ��");
			replButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					String searText = replSear.getText();
					if (searText.equals("")) {
						JOptionPane.showMessageDialog(cont, "Ҫ�滻�Ĵ�Ϊ��", "��ʾ��Ϣ",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						String allText = text.getText();
						int start = allText.indexOf(searText, text
								.getSelectionEnd());
						if (start == -1)
							JOptionPane.showMessageDialog(container, "�滻���",
									"��ʾ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
						else
							text.replaceRange(replField.getText(), start, start
									+ searText.length());
					}
				}
			});
			replPanel.add(replSear);
			replPanel.add(label);
			replPanel.add(replField);
			buttonPanel.add(replButton);
			JPanel panel = new JPanel(new FlowLayout());
			panel.add(replPanel);
			panel.add(buttonPanel);
			tab.addTab("����", searPanel);
			tab.addTab("�滻", panel);
			tab.setVisible(true);
			cont.add(tab);
			pack();
			Point paraLoca = container.getLocationOnScreen();
			setLocation(paraLoca.y + getSize().width, paraLoca.x
					+ getSize().height);
			setVisible(true);
			setResizable(false);
			addWindowListener(new WindowAdapter() {
				public void windowClosing() {
					dispose();
				}
			});
		}
	}

	private class filter extends javax.swing.filechooser.FileFilter {
		public boolean accept(File file) {
			String name = file.getName().toLowerCase();
			if (name.endsWith(".txt") || name.endsWith(".cmd")
					|| file.isDirectory() || name.endsWith(".sql")
					|| name.endsWith(".properties") || name.endsWith(".java")
					|| name.endsWith(".cpp") || name.endsWith(".c")
					|| name.endsWith(".css") || name.endsWith(".js")
					|| name.endsWith(".xml") || name.endsWith(".html")
					|| name.endsWith(".log"))
				return true;
			else
				return false;
		}

		public String getDescription() {
			return ".txt | .cmd | .sql |.properties | .java | .cpp | .c | .css | .js | .xml | .html |.log";
		}
	}
	
	public static void main(String args[]) {
		new NotePad();
	}
}
