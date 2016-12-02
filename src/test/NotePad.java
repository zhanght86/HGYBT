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
	// File菜单
	private JMenu menu;
	private JMenuItem newText;
	private JMenuItem open;
	private JCheckBoxMenuItem readOnly;
	private JMenuItem save;
	private JMenuItem saveAs;
	private JMenuItem exit;
	// Edit菜单
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
	// 视图菜单
	private JMenu view;
	private JCheckBoxMenuItem wrap;
	private JCheckBoxMenuItem onTop;
	private JMenuItem zoomIn;
	private JMenuItem zoomOut; 
	// 工具菜单
	private JMenu tool;
	private JMenuItem fontSum;
	private JMenuItem setcolor;
	private JMenuItem setFontColor;
	// 帮助菜单
	private JMenu help;
	private JMenuItem about;
	// 右键菜单
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
		super("记事本");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		container = getContentPane();
		container.setLayout(new BorderLayout(0, -2));
		manager = new UndoManager();
		text = new JTextArea();
		text.setFont(new Font("宋体", Font.PLAIN, 14));
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
		// File菜单
		menu = new JMenu("文件(F)");
		menu.setMnemonic('F');
		newText = new JMenuItem("新建(N)");
		newText.setMnemonic('N');
		newText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (selectedfile == null
						&& !text.getText().equals("")
						&& JOptionPane.showConfirmDialog(container, "是否保存",
								"是否保存", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
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
							JOptionPane.showMessageDialog(container, "保存文件失败",
									"ERROR", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else if (selectedfile != null
						&& JOptionPane.showConfirmDialog(container, "是否保存",
								"是否保存", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
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
		open = new JMenuItem("打开(O)");
		open.setMnemonic('O');
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (selectedfile != null
						&& JOptionPane.showConfirmDialog(container, "是否保存",
								"是否保存", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
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
						&& JOptionPane.showConfirmDialog(container, "是否保存",
								"是否保存", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
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
							JOptionPane.showMessageDialog(container, "保存文件失败",
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
						JOptionPane.showMessageDialog(container, "打开文件失败",
								"ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		readOnly = new JCheckBoxMenuItem("只读模式(R)");
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
		save = new JMenuItem("保存(S)");
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
							JOptionPane.showMessageDialog(container, "保存文件失败",
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
						JOptionPane.showMessageDialog(container, "保存文件失败",
								"ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		saveAs = new JMenuItem("另存为(A)");
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
						JOptionPane.showMessageDialog(container, "保存文件失败",
								"ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		exit = new JMenuItem("退出(Q)");
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
		// Edit菜单
		edit = new JMenu("编辑(E)");
		edit.setMnemonic('E');
		undo = new JMenuItem("撤销(U)");
		undo.setMnemonic('U');
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (manager.canUndo())
					manager.undo();
			}
		});
		redo = new JMenuItem("重做(Y)");
		redo.setMnemonic('Y');
		redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (manager.canRedo())
					manager.redo();
			}
		});
		cut = new JMenuItem("剪切(X)");
		cut.setMnemonic('X');
		cut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.cut();
			}
		});
		copy = new JMenuItem("复制(C)");
		copy.setMnemonic('C');
		copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.copy();
			}
		});
		copyAll = new JMenuItem("复制所有(Z)");
		copyAll.setMnemonic('Z');
		copyAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.selectAll();
				text.copy();
			}
		});
		paste = new JMenuItem("粘贴(V)");
		paste.setMnemonic('V');
		paste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.paste();
			}
		});
		delete = new JMenuItem("删除(D)");
		delete.setMnemonic('D');
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.replaceRange("", text.getSelectionStart(), text
						.getSelectionEnd());
			}
		});
		deleteAll = new JMenuItem("删除所有(W)");
		deleteAll.setMnemonic('W');
		deleteAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.replaceRange("", 0, text.getText().length());
			}
		});
		selectAll = new JMenuItem("选择所有(B)");
		selectAll.setMnemonic('B');
		selectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.selectAll();
			}
		});
		searchAndReplace = new JMenuItem("查找与替换(M)");
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
		// 视图菜单
		view = new JMenu("视图(V)");
		view.setMnemonic('V');
		wrap = new JCheckBoxMenuItem("自动换行(J)");
		wrap.setSelected(true);
		wrap.setMnemonic('J');
		wrap.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				text.setLineWrap(wrap.isSelected());
			}
		});
		onTop = new JCheckBoxMenuItem("保持窗口在最上");
		onTop.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				keepOnTop(onTop.isSelected());
			}
		});
		zoomIn = new JMenuItem("放大页面(I)");
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
		zoomOut = new JMenuItem("缩小页面(O)");
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
		// 工具菜单
		tool = new JMenu("工具(T)");
		tool.setMnemonic('T');
		fontSum = new JMenuItem("字数统计(L)");
		fontSum.setMnemonic('L');
		fontSum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String all = text.getText();
				int space = 0;// 空格
				int letter = 0;// 英文字母
				int digit = 0;// 数字
				int enter = 0;// 换行
				int other = 0;// 其它
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
				JOptionPane.showMessageDialog(container, "字符个数：\n\n数字：" + digit
						+ "\n英文字母：" + letter + "\n其它字母:" + otherLetter
						+ "\n空格：" + space + "\n换行：" + enter + "\n其它：" + other,
						"字符统计", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		setFontColor = new JMenuItem("设置字体颜色");
		setFontColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Color color = JColorChooser.showDialog(container, "字体色选择",
						Color.BLACK);
				text.setForeground(color);
			}
		});
		setcolor = new JMenuItem("设置背景色(G)");
		setcolor.setMnemonic('G');
		setcolor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Color color = JColorChooser.showDialog(container, "背景色选择",
						Color.WHITE);
				text.setBackground(color);
			}
		});
		tool.add(fontSum); // tool.add(setfont);
		tool.add(setFontColor);
		tool.add(setcolor);
		// 帮助菜单
		help = new JMenu("帮助(H)");
		help.setMnemonic('H');
		about = new JMenuItem("关于(P)");
		about.setMnemonic('P');
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(container, "Java记事本", "关于",
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
		popUndo = new JMenuItem("撤销");
		popUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (manager.canUndo())
					manager.undo();
			}
		});
		popRedo = new JMenuItem("重做");
		popRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (manager.canRedo())
					manager.redo();
			}
		});
		popCut = new JMenuItem("剪切");
		popCut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.cut();
			}
		});
		popCopy = new JMenuItem("复制");
		popCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.copy();
			}
		});
		popSelectAll = new JMenuItem("全部选择");
		popSelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.selectAll();
			}
		});
		popCopyAll = new JMenuItem("全部复制");
		popCopyAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.selectAll();
				text.copy();
			}
		});
		popPaste = new JMenuItem("粘贴");
		popPaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.paste();
			}
		});
		popDelete = new JMenuItem("删除");
		popDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.replaceRange("", text.getSelectionStart(), text
						.getSelectionEnd());
			}
		});
		popClear = new JMenuItem("清空");
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
		// 查找
		private JPanel searPanel;
		private JTextField searField;
		private JButton searButton;
		// 替换
		private JPanel replPanel;
		private JPanel buttonPanel;
		private JTextField replField;
		private JButton replButton;
		private JLabel label;
		private JTextField replSear;

		public Search() {
			super("查找与替换");
			cont = getContentPane();
			tab = new JTabbedPane(JTabbedPane.TOP);
			// 查找
			searPanel = new JPanel(new FlowLayout());
			searField = new JTextField(15);
			searButton = new JButton("查找下一处");
			searPanel.add(searField);
			searPanel.add(searButton);
			searButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					String searText = searField.getText().trim();
					if (searText.equals("")) {
						JOptionPane.showMessageDialog(cont, "查找串为空", "提示信息",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						String allText = text.getText();
						int start = allText.indexOf(searText, text
								.getSelectionEnd());
						if (start == -1)
							JOptionPane.showMessageDialog(container,
									"已到达文件末尾，没有找到\n" + searText, "提示信息",
									JOptionPane.INFORMATION_MESSAGE);
						else
							text.select(start, start + searText.length());
					}
				}
			});
			// 替换
			replPanel = new JPanel(new GridLayout(3, 1));
			buttonPanel = new JPanel(new FlowLayout());
			label = new JLabel("替换为");
			replField = new JTextField(15);
			replSear = new JTextField(15);
			replButton = new JButton("替换下一处");
			replButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					String searText = replSear.getText();
					if (searText.equals("")) {
						JOptionPane.showMessageDialog(cont, "要替换的串为空", "提示信息",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						String allText = text.getText();
						int start = allText.indexOf(searText, text
								.getSelectionEnd());
						if (start == -1)
							JOptionPane.showMessageDialog(container, "替换完毕",
									"提示信息", JOptionPane.INFORMATION_MESSAGE);
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
			tab.addTab("查找", searPanel);
			tab.addTab("替换", panel);
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
