package de.netprojectev.client.gui.main;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.logging.log4j.Logger;

import de.netprojectev.client.datastructures.ClientMediaFile;
import de.netprojectev.client.datastructures.ClientTickerElement;
import de.netprojectev.client.gui.main.CreateTickerElementDialog.DialogClosedListener;
import de.netprojectev.client.gui.models.AllMediaTableModel;
import de.netprojectev.client.gui.models.CustomQueueTableModel;
import de.netprojectev.client.gui.models.PriorityComboBoxModel;
import de.netprojectev.client.gui.models.TickerTableModel;
import de.netprojectev.client.gui.preferences.AddCountdownDialog;
import de.netprojectev.client.gui.preferences.AddPriorityDialog;
import de.netprojectev.client.gui.preferences.AddThemeDialog;
import de.netprojectev.client.gui.preferences.PreferencesFrame;
import de.netprojectev.client.gui.themeslide.ThemeslideCreatorFrame;
import de.netprojectev.client.model.MediaModelClient;
import de.netprojectev.client.model.PreferencesModelClient;
import de.netprojectev.client.model.TickerModelClient;
import de.netprojectev.client.networking.ClientMessageProxy;
import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.exceptions.PriorityDoesNotExistException;
import de.netprojectev.exceptions.ThemeDoesNotExistException;
import de.netprojectev.misc.LoggerBuilder;
import de.netprojectev.misc.MediaFileFilter;
import de.netprojectev.misc.Misc;

/**
 * 
 * @author samu
 */
public class MainClientGUIWindow extends javax.swing.JFrame {

	public interface UpdateAutoModeStateListener {
		public void update(boolean fullsync);
	}

	public interface UpdateCurrentFileListener {
		public void update();
	}

	public interface TimeSyncListener {
		public void timesync(long timeLeftInSeconds);
	}
	
	public interface ServerShutdownListener {
		public void serverShutdown();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4247573311423083026L;
	private static final Logger log = LoggerBuilder.createLogger(MainClientGUIWindow.class);

	private final ClientMessageProxy proxy;
	private final MediaModelClient mediaModel;
	private final TickerModelClient tickerModel;
	private final PreferencesModelClient prefs;

	private ClientMediaFile currentSelectedMediaFile;
	private ClientTickerElement currentSelectedTickerElement;

	private Timer refreshTimeLeftTimer;
	private TimeLeftData timeleftData;

	/**
	 * Creates new form MainClientGUIWindow
	 */
	public MainClientGUIWindow(Container parent, ClientMessageProxy proxy) {
		this.mediaModel = proxy.getMediaModel();

		this.timeleftData = new TimeLeftData(0);
		
		this.mediaModel.setUpdateCurrentFileListener(new UpdateCurrentFileListener() {

			@Override
			public void update() {
				if (prefs.isAutomode()) {
					startRefreshTimeLeftTimer(false);
				} else {
					disableRefreshTimeLeftTimer();
				}
			}
		});

		this.tickerModel = proxy.getTickerModel();
		this.prefs = proxy.getPrefs();

		this.prefs.setAutoModeStateListener(new UpdateAutoModeStateListener() {

			@Override
			public void update(boolean fullsync) {
				jmirbAutomode.setSelected(prefs.isAutomode());
				if (!prefs.isAutomode()) {
					disableRefreshTimeLeftTimer();
				} else {
					startRefreshTimeLeftTimer(fullsync);
				}
			}

		});

		this.proxy = proxy;
		
		this.proxy.setTimeSyncListener(new TimeSyncListener() {
			
			@Override
			public void timesync(long timeLeftInSeconds) {
				timeleftData.setTimeleftInSeconds(timeLeftInSeconds);
				
			}
		});
		
		this.proxy.setServerShutdownListener(new ServerShutdownListener() {
			
			@Override
			public void serverShutdown() {
				JOptionPane.showMessageDialog(MainClientGUIWindow.this, "Server was shut down. The Program is no exiting."
						, "Server shutdown", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
		});
		
		initComponents();

		this.setLocationRelativeTo(parent);

		jtAllMedia.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					updatePreviewLable();
				} catch (MediaDoesNotExsistException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					updateEditorLable();
				} catch (MediaDoesNotExsistException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});

		jtCustomQueue.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					updatePreviewLable();
				} catch (MediaDoesNotExsistException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					updateEditorLable();
				} catch (MediaDoesNotExsistException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		jtLiveTicker.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					updatePreviewLable();
				} catch (MediaDoesNotExsistException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					updateEditorLable();
				} catch (MediaDoesNotExsistException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		try {
			jcbPriorityChange.setModel(new PriorityComboBoxModel(prefs));
		} catch (PriorityDoesNotExistException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jpmAllTables = new javax.swing.JPopupMenu();
		jmipopAddFile = new javax.swing.JMenuItem();
		jmipopAddThemeslide = new javax.swing.JMenuItem();
		jmipopQueue = new javax.swing.JMenuItem();
		jmipopRemove = new javax.swing.JMenuItem();
		jmipopShow = new javax.swing.JMenuItem();
		jmipopShowNext = new javax.swing.JMenuItem();
		jpmQueueTable = new javax.swing.JPopupMenu();
		jmipopDequeue = new javax.swing.JMenuItem();
		jmipopShowNextInQueue = new javax.swing.JMenuItem();
		jpmTickerTable = new javax.swing.JPopupMenu();
		jmipopAddElement = new javax.swing.JMenuItem();
		jmiPopToggleActivated = new javax.swing.JMenuItem();
		jmipopRemoveElement = new javax.swing.JMenuItem();
		tabbedPaneContainer = new javax.swing.JTabbedPane();
		jpAllMedia = new javax.swing.JPanel();
		jspMediaTableContainer = new javax.swing.JScrollPane();
		jtAllMedia = new javax.swing.JTable();
		jpCustomQueue = new javax.swing.JPanel();
		jspQueueTableContainer = new javax.swing.JScrollPane();
		jtCustomQueue = new javax.swing.JTable();
		jpLiveTicker = new javax.swing.JPanel();
		jspTickerTableContainer = new javax.swing.JScrollPane();
		jtLiveTicker = new javax.swing.JTable();
		jpButtonContainer = new javax.swing.JPanel();
		jbAddFile = new javax.swing.JButton();
		jbAddThemeslide = new javax.swing.JButton();
		jbAddTickerElement = new javax.swing.JButton();
		jbRemoveFromList = new javax.swing.JButton();
		jbShowSelected = new javax.swing.JButton();
		jbShowNext = new javax.swing.JButton();
		jlAutomodeTimeleft = new javax.swing.JLabel();
		jsToolbar = new javax.swing.JSeparator();
		jpPreviewAndEditor = new javax.swing.JPanel();
		jspPreview = new javax.swing.JScrollPane();
		jlPreview = new javax.swing.JLabel();
		jspEditor = new javax.swing.JScrollPane();
		jpEditor = new javax.swing.JPanel();
		jbUpdateFileData = new javax.swing.JButton();
		jbResetFileData = new javax.swing.JButton();
		jlFileName = new javax.swing.JLabel();
		jtfFileName = new javax.swing.JTextField();
		jlPriority = new javax.swing.JLabel();
		jcbPriorityChange = new javax.swing.JComboBox();
		jlShowcount = new javax.swing.JLabel();
		jlShowcountNumber = new javax.swing.JLabel();
		jlCurrent = new javax.swing.JLabel();
		jlCurrentYesNo = new javax.swing.JLabel();
		jchbEnabled = new javax.swing.JCheckBox();
		jbResetShowcount = new javax.swing.JButton();
		jmbMain = new javax.swing.JMenuBar();
		jmFile = new javax.swing.JMenu();
		jmiAddFile = new javax.swing.JMenuItem();
		jmiAddThemeslide = new javax.swing.JMenuItem();
		jmiAddTickerElt = new javax.swing.JMenuItem();
		jmiAddCntDown = new javax.swing.JMenuItem();
		jmiRemove = new javax.swing.JMenuItem();
		jmiExit = new javax.swing.JMenuItem();
		jmPrefs = new javax.swing.JMenu();
		jmiAddPrio = new javax.swing.JMenuItem();
		jmiAddTheme = new javax.swing.JMenuItem();
		jmirbAutomode = new javax.swing.JRadioButtonMenuItem();
		jmiPrefs = new javax.swing.JMenuItem();

		jmipopAddFile.setText("Add File");
		jmipopAddFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmipopAddFileActionPerformed(evt);
			}
		});
		jpmAllTables.add(jmipopAddFile);

		jmipopAddThemeslide.setText("Add Themeslide");
		jmipopAddThemeslide.setToolTipText("");
		jmipopAddThemeslide.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmipopAddThemeslideActionPerformed(evt);
			}
		});
		jpmAllTables.add(jmipopAddThemeslide);

		jmipopQueue.setLabel("Queue");
		jmipopQueue.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmipopQueueActionPerformed(evt);
			}
		});
		jpmAllTables.add(jmipopQueue);

		jmipopRemove.setText("Remove");
		jmipopRemove.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmipopRemoveActionPerformed(evt);
			}
		});
		jpmAllTables.add(jmipopRemove);

		jmipopShow.setText("Show");
		jmipopShow.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmipopShowActionPerformed(evt);
			}
		});
		jpmAllTables.add(jmipopShow);

		jmipopShowNext.setText("Show Next");
		jmipopShowNext.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmipopShowNextActionPerformed(evt);
			}
		});
		jpmAllTables.add(jmipopShowNext);

		jmipopDequeue.setLabel("Dequeue");
		jmipopDequeue.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmipopDequeueActionPerformed(evt);
			}
		});
		jpmQueueTable.add(jmipopDequeue);

		jmipopShowNextInQueue.setText("Show Next");
		jmipopShowNextInQueue.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmipopShowNextInQueueActionPerformed(evt);
			}
		});
		jpmQueueTable.add(jmipopShowNextInQueue);

		jmipopAddElement.setText("Add Element");
		jmipopAddElement.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmipopAddElementActionPerformed(evt);
			}
		});
		jpmTickerTable.add(jmipopAddElement);

		jmiPopToggleActivated.setText("Toggle Activated");
		jmiPopToggleActivated.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmiPopToggleActivatedActionPerformed(evt);
			}
		});
		jpmTickerTable.add(jmiPopToggleActivated);

		jmipopRemoveElement.setText("Remove");
		jmipopRemoveElement.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmipopRemoveElementActionPerformed(evt);
			}
		});
		jpmTickerTable.add(jmipopRemoveElement);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Beamer Client");

		tabbedPaneContainer.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				tabbedPaneContainerStateChanged(evt);
			}
		});

		jtAllMedia.setModel(new AllMediaTableModel(mediaModel));
		jtAllMedia.setRowHeight(20);
		jtAllMedia.setRowMargin(2);
		jtAllMedia.getColumnModel().getColumn(0).setCellRenderer(new TableButtonRenderer(true));
		jtAllMedia
				.getColumnModel()
				.getColumn(0)
				.setCellEditor(
						new ButtonEditor(new JCheckBox(),
								new ButtonEditor.TableButtonActionListener() {
									public void buttonClicked(int row) {
										queue(row);
									}
								}));

		jtAllMedia.getColumnModel().getColumn(1).setMaxWidth(30);
		jtAllMedia.getColumnModel().getColumn(2).setMaxWidth(30);
		jtAllMedia.getColumnModel().getColumn(0).setMaxWidth(30);
		jtAllMedia.getColumnModel().getColumn(0).setMinWidth(30);
		jtAllMedia.getColumnModel().getColumn(1).setMinWidth(30);
		jtAllMedia.getColumnModel().getColumn(2).setMinWidth(30);
		jtAllMedia.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jtAllMediaMouseClicked(evt);
			}
		});
		jspMediaTableContainer.setViewportView(jtAllMedia);

		javax.swing.GroupLayout jpAllMediaLayout = new javax.swing.GroupLayout(jpAllMedia);
		jpAllMedia.setLayout(jpAllMediaLayout);
		jpAllMediaLayout.setHorizontalGroup(jpAllMediaLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(jspMediaTableContainer,
				javax.swing.GroupLayout.DEFAULT_SIZE, 1240, Short.MAX_VALUE));
		jpAllMediaLayout.setVerticalGroup(jpAllMediaLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jpAllMediaLayout
						.createSequentialGroup()
						.addComponent(jspMediaTableContainer, javax.swing.GroupLayout.DEFAULT_SIZE,
								373, Short.MAX_VALUE).addContainerGap()));

		tabbedPaneContainer.addTab("Media", jpAllMedia);

		jtCustomQueue.setModel(new CustomQueueTableModel(mediaModel));
		jtCustomQueue.setRowHeight(20);
		jtCustomQueue.setRowMargin(2);
		jtCustomQueue.getColumnModel().getColumn(0).setCellRenderer(new TableButtonRenderer(false));
		jtCustomQueue
				.getColumnModel()
				.getColumn(0)
				.setCellEditor(
						new ButtonEditor(new JCheckBox(),
								new ButtonEditor.TableButtonActionListener() {
									public void buttonClicked(int row) {
										dequeue(row);
									}
								}));

		jtCustomQueue.getColumnModel().getColumn(0).setMaxWidth(30);
		jtCustomQueue.getColumnModel().getColumn(0).setMinWidth(30);
		jtCustomQueue.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jtCustomQueueMouseClicked(evt);
			}
		});
		jspQueueTableContainer.setViewportView(jtCustomQueue);

		javax.swing.GroupLayout jpCustomQueueLayout = new javax.swing.GroupLayout(jpCustomQueue);
		jpCustomQueue.setLayout(jpCustomQueueLayout);
		jpCustomQueueLayout.setHorizontalGroup(jpCustomQueueLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(jspQueueTableContainer,
				javax.swing.GroupLayout.DEFAULT_SIZE, 1240, Short.MAX_VALUE));
		jpCustomQueueLayout.setVerticalGroup(jpCustomQueueLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(jspQueueTableContainer,
				javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE));

		tabbedPaneContainer.addTab("Queue", jpCustomQueue);

		jtLiveTicker.setModel(new TickerTableModel(tickerModel));
		jtLiveTicker.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jtLiveTickerMouseClicked(evt);
			}
		});
		jtLiveTicker.getColumnModel().getColumn(0).setMaxWidth(30);
		jtLiveTicker.getColumnModel().getColumn(0).setMinWidth(30);
		jspTickerTableContainer.setViewportView(jtLiveTicker);

		javax.swing.GroupLayout jpLiveTickerLayout = new javax.swing.GroupLayout(jpLiveTicker);
		jpLiveTicker.setLayout(jpLiveTickerLayout);
		jpLiveTickerLayout.setHorizontalGroup(jpLiveTickerLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(jspTickerTableContainer,
				javax.swing.GroupLayout.DEFAULT_SIZE, 1240, Short.MAX_VALUE));
		jpLiveTickerLayout.setVerticalGroup(jpLiveTickerLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(jspTickerTableContainer,
				javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE));

		tabbedPaneContainer.addTab("Ticker", jpLiveTicker);

		jbAddFile.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/de/netprojectev/client/gfx/plus_2.png"))); // NOI18N
		jbAddFile.setText("File");
		jbAddFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbAddFileActionPerformed(evt);
			}
		});

		jbAddThemeslide.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/de/netprojectev/client/gfx/plus_2.png"))); // NOI18N
		jbAddThemeslide.setText("Themeslide");
		jbAddThemeslide.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbAddThemeslideActionPerformed(evt);
			}
		});

		jbAddTickerElement.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/de/netprojectev/client/gfx/plus_2.png"))); // NOI18N
		jbAddTickerElement.setText("Ticker");
		jbAddTickerElement.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbAddTickerElementActionPerformed(evt);
			}
		});

		jbRemoveFromList.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/de/netprojectev/client/gfx/delete_2.png"))); // NOI18N
		jbRemoveFromList.setToolTipText("");
		jbRemoveFromList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbRemoveFromListActionPerformed(evt);
			}
		});

		jbShowSelected.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/de/netprojectev/client/gfx/play.png"))); // NOI18N
		jbShowSelected.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbShowSelectedActionPerformed(evt);
			}
		});

		jbShowNext.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/de/netprojectev/client/gfx/next.png"))); // NOI18N
		jbShowNext.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbShowNextActionPerformed(evt);
			}
		});

		jlAutomodeTimeleft.setText("Timeleft: --:--");

		jsToolbar.setOrientation(javax.swing.SwingConstants.VERTICAL);

		javax.swing.GroupLayout jpButtonContainerLayout = new javax.swing.GroupLayout(
				jpButtonContainer);
		jpButtonContainer.setLayout(jpButtonContainerLayout);
		jpButtonContainerLayout.setHorizontalGroup(jpButtonContainerLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jpButtonContainerLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(jbAddFile, javax.swing.GroupLayout.PREFERRED_SIZE, 77,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jbAddThemeslide)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jbAddTickerElement)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jbRemoveFromList)
						.addGap(18, 18, 18)
						.addComponent(jsToolbar, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addComponent(jbShowSelected)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jbShowNext)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jlAutomodeTimeleft).addContainerGap()));
		jpButtonContainerLayout
				.setVerticalGroup(jpButtonContainerLayout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpButtonContainerLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpButtonContainerLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(jbRemoveFromList)
														.addComponent(jbShowSelected)
														.addComponent(jbShowNext)
														.addComponent(jlAutomodeTimeleft)
														.addGroup(
																jpButtonContainerLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(jbAddFile)
																		.addComponent(
																				jbAddThemeslide)
																		.addComponent(
																				jbAddTickerElement))
														.addComponent(
																jsToolbar,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																36,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		jspPreview.setBorder(javax.swing.BorderFactory.createTitledBorder("Preview"));
		jspPreview.setViewportView(jlPreview);

		jspEditor.setBorder(javax.swing.BorderFactory.createTitledBorder(
				javax.swing.BorderFactory.createTitledBorder(""), "Editor"));

		jpEditor.setPreferredSize(new java.awt.Dimension(521, 320));
		jpEditor.setRequestFocusEnabled(false);

		jbUpdateFileData.setText("Update");
		jbUpdateFileData.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbUpdateFileDataActionPerformed(evt);
			}
		});

		jbResetFileData.setLabel("Reset to Server");
		jbResetFileData.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbResetFileDataActionPerformed(evt);
			}
		});

		jlFileName.setText("Filename");

		jlPriority.setText("Priority");

		jlShowcount.setText("Showcount");

		jlShowcountNumber.setText("0");

		jlCurrent.setText("Current");

		jlCurrentYesNo.setText("No");

		jchbEnabled.setSelected(true);
		jchbEnabled.setText("Enabled");

		jbResetShowcount.setText("Reset");
		jbResetShowcount.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbResetShowcountActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jpEditorLayout = new javax.swing.GroupLayout(jpEditor);
		jpEditor.setLayout(jpEditorLayout);
		jpEditorLayout
				.setHorizontalGroup(jpEditorLayout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jpEditorLayout
										.createSequentialGroup()
										.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE).addComponent(jbUpdateFileData)
										.addGap(18, 18, 18).addComponent(jbResetFileData)
										.addGap(29, 29, 29))
						.addGroup(
								jpEditorLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpEditorLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jpEditorLayout
																		.createSequentialGroup()
																		.addGroup(
																				jpEditorLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jlFileName)
																						.addComponent(
																								jlPriority))
																		.addGap(31, 31, 31)
																		.addGroup(
																				jpEditorLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jcbPriorityChange,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								220,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addGroup(
																								jpEditorLayout
																										.createSequentialGroup()
																										.addComponent(
																												jtfFileName,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												220,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGap(18,
																												18,
																												18)
																										.addComponent(
																												jchbEnabled))))
														.addGroup(
																jpEditorLayout
																		.createSequentialGroup()
																		.addGroup(
																				jpEditorLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jlShowcount)
																						.addComponent(
																								jlCurrent))
																		.addGap(18, 18, 18)
																		.addGroup(
																				jpEditorLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jlCurrentYesNo)
																						.addGroup(
																								jpEditorLayout
																										.createSequentialGroup()
																										.addComponent(
																												jlShowcountNumber)
																										.addGap(18,
																												18,
																												18)
																										.addComponent(
																												jbResetShowcount)))))
										.addContainerGap(125, Short.MAX_VALUE)));
		jpEditorLayout.setVerticalGroup(jpEditorLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				jpEditorLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								jpEditorLayout
										.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jlFileName)
										.addComponent(jtfFileName,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jchbEnabled))
						.addGap(18, 18, 18)
						.addGroup(
								jpEditorLayout
										.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jlPriority)
										.addComponent(jcbPriorityChange,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(
								jpEditorLayout
										.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jlShowcount).addComponent(jlShowcountNumber)
										.addComponent(jbResetShowcount))
						.addGap(18, 18, 18)
						.addGroup(
								jpEditorLayout
										.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jlCurrent).addComponent(jlCurrentYesNo))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 129,
								Short.MAX_VALUE)
						.addGroup(
								jpEditorLayout
										.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jbUpdateFileData)
										.addComponent(jbResetFileData)).addGap(19, 19, 19)));

		jspEditor.setViewportView(jpEditor);

		javax.swing.GroupLayout jpPreviewAndEditorLayout = new javax.swing.GroupLayout(
				jpPreviewAndEditor);
		jpPreviewAndEditor.setLayout(jpPreviewAndEditorLayout);
		jpPreviewAndEditorLayout.setHorizontalGroup(jpPreviewAndEditorLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jpPreviewAndEditorLayout
						.createSequentialGroup()
						.addComponent(jspPreview)
						.addGap(18, 18, 18)
						.addComponent(jspEditor, javax.swing.GroupLayout.PREFERRED_SIZE, 553,
								javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpPreviewAndEditorLayout.setVerticalGroup(jpPreviewAndEditorLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				jpPreviewAndEditorLayout
						.createSequentialGroup()
						.addGroup(
								jpPreviewAndEditorLayout
										.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(jspEditor)
										.addComponent(jspPreview,
												javax.swing.GroupLayout.DEFAULT_SIZE, 368,
												Short.MAX_VALUE)).addContainerGap()));

		jmFile.setText("File");

		jmiAddFile.setText("Add File");
		jmiAddFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmiAddFileActionPerformed(evt);
			}
		});
		jmFile.add(jmiAddFile);

		jmiAddThemeslide.setText("Add Themeslide");
		jmiAddThemeslide.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmiAddThemeslideActionPerformed(evt);
			}
		});
		jmFile.add(jmiAddThemeslide);

		jmiAddTickerElt.setText("Add Tickerelement");
		jmiAddTickerElt.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmiAddTickerEltActionPerformed(evt);
			}
		});
		jmFile.add(jmiAddTickerElt);

		jmiAddCntDown.setText("Add Countdown");
		jmiAddCntDown.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmiAddCntDownActionPerformed(evt);
			}
		});
		jmFile.add(jmiAddCntDown);

		jmiRemove.setText("Remove");
		jmiRemove.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmiRemoveActionPerformed(evt);
			}
		});
		jmFile.add(jmiRemove);

		jmiExit.setText("Exit");
		jmiExit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmiExitActionPerformed(evt);
			}
		});
		jmFile.add(jmiExit);

		jmbMain.add(jmFile);

		jmPrefs.setText("Preferences");

		jmiAddPrio.setText("Add Priority");
		jmiAddPrio.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmiAddPrioActionPerformed(evt);
			}
		});
		jmPrefs.add(jmiAddPrio);

		jmiAddTheme.setText("Add Theme");
		jmiAddTheme.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmiAddThemeActionPerformed(evt);
			}
		});
		jmPrefs.add(jmiAddTheme);

		jmirbAutomode.setText("Automode");
		jmirbAutomode.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmirbAutomodeActionPerformed(evt);
			}
		});
		jmPrefs.add(jmirbAutomode);

		jmiPrefs.setText("Preferences");
		jmiPrefs.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jmiPrefsActionPerformed(evt);
			}
		});
		jmPrefs.add(jmiPrefs);

		jmbMain.add(jmPrefs);

		setJMenuBar(jmbMain);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(tabbedPaneContainer)
				.addComponent(jpButtonContainer, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(jpPreviewAndEditor,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup()
						.addComponent(jpButtonContainer, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(tabbedPaneContainer, javax.swing.GroupLayout.PREFERRED_SIZE,
								408, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jpPreviewAndEditor, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void jbUpdateFileDataActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbUpdateFileDataActionPerformed
		String text = jtfFileName.getText();

		// TODO get Prio, server sided change to prio uuid not the prio object
		// TODO add proper string handling, like removing trailing and leading
		// spaces, avoid only spaces in the string and so on
		if (currentSelectedMediaFile != null) {
			ClientMediaFile copyToSend = currentSelectedMediaFile.copy();
			Priority selectedPrio = (Priority) jcbPriorityChange.getSelectedItem();
			copyToSend.setName(text);
			copyToSend.setPriority(selectedPrio);
			if (text != null && !text.isEmpty() && selectedPrio != null) {
				proxy.sendEditMediaFile(copyToSend);
			}

		}
		if (currentSelectedTickerElement != null) {
			ClientTickerElement copyToSend = currentSelectedTickerElement.copy();
			copyToSend.setText(text);
			copyToSend.setShow(jchbEnabled.isSelected());
			if (text != null && !text.isEmpty()) {
				proxy.sendEditTickerElement(copyToSend);
			}
		}

	}// GEN-LAST:event_jbUpdateFileDataActionPerformed

	private void jbResetFileDataActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbResetFileDataActionPerformed
		try {
			updateEditorLable();
		} catch (MediaDoesNotExsistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// GEN-LAST:event_jbResetFileDataActionPerformed

	private void jbResetShowcountActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbResetShowcountActionPerformed
		resetShowCount();
	}// GEN-LAST:event_jbResetShowcountActionPerformed

	private void jmiAddFileActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jmiAddFileActionPerformed
		addFile();
	}// GEN-LAST:event_jmiAddFileActionPerformed

	private void jmiAddPrioActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jmiAddPrioActionPerformed
		new AddPriorityDialog(this, true, proxy).setVisible(true);
	}// GEN-LAST:event_jmiAddPrioActionPerformed

	private void jmipopAddFileActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jmipopAddFileActionPerformed
		addFile();
	}// GEN-LAST:event_jmipopAddFileActionPerformed

	private void jmipopAddThemeslideActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jmipopAddThemeslideActionPerformed
		addThemeslide();
	}// GEN-LAST:event_jmipopAddThemeslideActionPerformed

	private void jmipopRemoveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jmipopRemoveActionPerformed
		remove();
	}// GEN-LAST:event_jmipopRemoveActionPerformed

	private void jmiAddThemeslideActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jmiAddThemeslideActionPerformed
		addThemeslide();
	}// GEN-LAST:event_jmiAddThemeslideActionPerformed

	private void jmiAddTickerEltActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jmiAddTickerEltActionPerformed
		addTickerElement();
	}// GEN-LAST:event_jmiAddTickerEltActionPerformed

	private void jmiAddCntDownActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jmiAddCntDownActionPerformed
		new AddCountdownDialog(this, true, proxy).setVisible(true);
	}// GEN-LAST:event_jmiAddCntDownActionPerformed

	private void jmiRemoveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jmiRemoveActionPerformed
		remove();
	}// GEN-LAST:event_jmiRemoveActionPerformed

	private void jmiAddThemeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jmiAddThemeActionPerformed
		new AddThemeDialog(this, true, proxy).setVisible(true);
	}// GEN-LAST:event_jmiAddThemeActionPerformed

	private void jmiPrefsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jmiPrefsActionPerformed
		new PreferencesFrame(this, proxy).setVisible(true);
	}// GEN-LAST:event_jmiPrefsActionPerformed

	private void jmirbAutomodeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jmirbAutomodeActionPerformed
		proxy.sendAutoModeToggle(jmirbAutomode.isSelected());
	}// GEN-LAST:event_jmirbAutomodeActionPerformed

	private void jmiExitActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jmiExitActionPerformed
		quit();
	}// GEN-LAST:event_jmiExitActionPerformed

	private void quit() {
		Misc.quit(this, proxy);
	}

	private void jtAllMediaMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jtAllMediaMouseClicked

		boolean isRowAlreadySelected = false;
		if ((SwingUtilities.isRightMouseButton(evt) || evt.isPopupTrigger())
				&& evt.getClickCount() == 1) {

			int dataRow = jtAllMedia.rowAtPoint(evt.getPoint());
			for (int i = 0; i < jtAllMedia.getSelectedRows().length; i++) {
				if (jtAllMedia.getSelectedRows()[i] == dataRow) {
					isRowAlreadySelected = true;
				}
			}
			if (!isRowAlreadySelected) {
				jtAllMedia.changeSelection(dataRow, 0, false, false);
			}
			if (dataRow >= 0) {
				jpmAllTables.show(evt.getComponent(), evt.getX(), evt.getY());
			}
		}
	}// GEN-LAST:event_jtAllMediaMouseClicked

	private void jtCustomQueueMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jtCustomQueueMouseClicked
		boolean isRowAlreadySelected = false;
		if ((SwingUtilities.isRightMouseButton(evt) || evt.isPopupTrigger())
				&& evt.getClickCount() == 1) {

			int dataRow = jtCustomQueue.rowAtPoint(evt.getPoint());
			for (int i = 0; i < jtCustomQueue.getSelectedRows().length; i++) {
				if (jtCustomQueue.getSelectedRows()[i] == dataRow) {
					isRowAlreadySelected = true;
				}
			}
			if (!isRowAlreadySelected) {
				jtCustomQueue.changeSelection(dataRow, 0, false, false);
			}
			if (dataRow >= 0) {
				jpmQueueTable.show(evt.getComponent(), evt.getX(), evt.getY());
			}
		}
	}// GEN-LAST:event_jtCustomQueueMouseClicked

	private void jtLiveTickerMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jtLiveTickerMouseClicked
		boolean isRowAlreadySelected = false;
		if ((SwingUtilities.isRightMouseButton(evt) || evt.isPopupTrigger())
				&& evt.getClickCount() == 1) {

			int dataRow = jtLiveTicker.rowAtPoint(evt.getPoint());
			for (int i = 0; i < jtLiveTicker.getSelectedRows().length; i++) {
				if (jtLiveTicker.getSelectedRows()[i] == dataRow) {
					isRowAlreadySelected = true;
				}
			}
			if (!isRowAlreadySelected) {
				jtLiveTicker.changeSelection(dataRow, 0, false, false);
			}
			if (dataRow >= 0) {
				jpmTickerTable.show(evt.getComponent(), evt.getX(), evt.getY());
			}
		}
	}// GEN-LAST:event_jtLiveTickerMouseClicked

	private void jmipopDequeueActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jmipopDequeueActionPerformed
		int[] selRows = jtCustomQueue.getSelectedRows();
		for (int i = 0; i < selRows.length; i++) {
			dequeue(selRows[i]);
		}

	}// GEN-LAST:event_jmipopDequeueActionPerformed

	private void jmipopAddElementActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jmipopAddElementActionPerformed
		addTickerElement();
	}// GEN-LAST:event_jmipopAddElementActionPerformed

	private void jmipopRemoveElementActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jmipopRemoveElementActionPerformed
		remove();
	}// GEN-LAST:event_jmipopRemoveElementActionPerformed

	private void jmipopQueueActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jmipopQueueActionPerformed
		int[] selRows = jtAllMedia.getSelectedRows();
		for (int i = 0; i < selRows.length; i++) {
			queue(selRows[i]);
		}
	}// GEN-LAST:event_jmipopQueueActionPerformed

	private void jbShowSelectedActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbShowSelectedActionPerformed
		showSelected();

	}// GEN-LAST:event_jbShowSelectedActionPerformed

	private void showSelected() {
		if (jtAllMedia.getSelectedRow() >= 0) {
			proxy.sendShowMediaFile(jtAllMedia.getSelectedRow());
		}
	}

	private void jbShowNextActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbShowNextActionPerformed
		showNext();
	}// GEN-LAST:event_jbShowNextActionPerformed

	private void showNext() {
		proxy.sendShowNextMediaFile();
	}

	private void resetShowCount() {
		if (currentSelectedMediaFile != null) {
			proxy.sendResetShowCount(currentSelectedMediaFile.getId());
		}
	}

	private void jmipopShowActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jmipopShowActionPerformed
		showSelected();
	}// GEN-LAST:event_jmipopShowActionPerformed

	private void jmipopShowNextActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jmipopShowNextActionPerformed
		showNext();
	}// GEN-LAST:event_jmipopShowNextActionPerformed

	private void jmipopShowNextInQueueActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jmipopShowNextInQueueActionPerformed
		showNext();
	}// GEN-LAST:event_jmipopShowNextInQueueActionPerformed

	private void jmiPopToggleActivatedActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jmiPopToggleActivatedActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_jmiPopToggleActivatedActionPerformed

	private void tabbedPaneContainerStateChanged(javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_tabbedPaneContainerStateChanged
		JTabbedPane sourceTabbedPane = (JTabbedPane) evt.getSource();
		int index = sourceTabbedPane.getSelectedIndex();
		switch (index) {
		case 0:
			jbShowSelected.setEnabled(true);
			break;
		case 1:
			jbShowSelected.setEnabled(false);
			break;
		case 2:
			jbShowSelected.setEnabled(false);
			break;
		default:
			log.error("Invalid tab selection");
			jbShowSelected.setEnabled(true);
			break;
		}
	}// GEN-LAST:event_tabbedPaneContainerStateChanged

	private void jbAddFileActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbAddFileActionPerformed
		addFile();

	}// GEN-LAST:event_jbAddFileActionPerformed

	private void addFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setApproveButtonText("Add");
		fileChooser.setDialogTitle("Add Files");
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setFileFilter(new MediaFileFilter());
		int returnVal = fileChooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				proxy.sendAddMediaFiles(fileChooser.getSelectedFiles());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void jbAddThemeslideActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbAddThemeslideActionPerformed
		addThemeslide();
	}// GEN-LAST:event_jbAddThemeslideActionPerformed

	private void addThemeslide() {
		try {
			new ThemeslideCreatorFrame(this, proxy).setVisible(true);
		} catch (ThemeDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PriorityDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void jbAddTickerElementActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbAddTickerElementActionPerformed
		addTickerElement();
	}// GEN-LAST:event_jbAddTickerElementActionPerformed

	private void addTickerElement() {
		new CreateTickerElementDialog(this, true, new DialogClosedListener() {

			@Override
			public void dialogClosed(int result, String text) {
				if (result == 1) {
					proxy.sendAddTickerElement(text);
				}
			}
		}).setVisible(true);
	}

	private void jbRemoveFromListActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbRemoveFromListActionPerformed
		remove();
	}// GEN-LAST:event_jbRemoveFromListActionPerformed

	private void remove() {
		int selectedTab = tabbedPaneContainer.getSelectedIndex();

		switch (selectedTab) {
		case 0:
			int[] selectedRowsAllMedia = jtAllMedia.getSelectedRows();
			proxy.sendRemoveSelectedMedia(selectedRowsAllMedia);
			break;
		case 1:
			int[] selectedRowsCustomQueue = jtCustomQueue.getSelectedRows();
			proxy.sendDequeueSelectedMedia(selectedRowsCustomQueue);
			break;
		case 2:
			int[] selectedRowsLiveTicker = jtLiveTicker.getSelectedRows();
			proxy.sendRemoveSelectedTickerElements(selectedRowsLiveTicker);
			break;
		default:
			log.debug("remove: no propper tab selected");
			break;

		}
	}

	private void disableRefreshTimeLeftTimer() {
		jlAutomodeTimeleft.setText("Timeleft: --:--");
		if (refreshTimeLeftTimer != null) {
			refreshTimeLeftTimer.stop();
			refreshTimeLeftTimer = null;

		}
	}

	private void startRefreshTimeLeftTimer(boolean fullsync) {

		if (refreshTimeLeftTimer != null) {
			refreshTimeLeftTimer.stop();
		}
		if (!fullsync) {
			timeleftData.setTimeleftInSeconds(mediaModel
					.getCurrentMediaFile().getPriority().getMinutesToShow() * 60);
			
			refreshTimeLeftTimer = new Timer(1000, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					timeleftData.decreaseTimeLeftByOne();
					
					log.debug("refreshing timeleft label, seconds left: " + timeleftData.getTimeleftInSeconds());
					
					if (timeleftData.getTimeleftInSeconds() > 3600) {
						jlAutomodeTimeleft.setText("Timeleft:"
								+ Misc.convertFromSecondsToTimeString(timeleftData.getTimeleftInSeconds(), true));
					} else {
						jlAutomodeTimeleft.setText("Timeleft:"
								+ Misc.convertFromSecondsToTimeString(timeleftData.getTimeleftInSeconds(), false));
					}

				}
			});
			refreshTimeLeftTimer.start();

		} else {
			jlAutomodeTimeleft.setText("Timeleft:" + "out of sync");
		}
	}

	private void updateEditorLable() throws MediaDoesNotExsistException {
		int selectedTab = tabbedPaneContainer.getSelectedIndex();
		int row;
		switch (selectedTab) {
		case 0:
			jlFileName.setText("Filename");
			jchbEnabled.setVisible(false);
			jlPriority.setVisible(true);
			jcbPriorityChange.setVisible(true);
			jlShowcount.setVisible(true);
			jlShowcountNumber.setVisible(true);
			jlCurrent.setVisible(true);
			jlCurrentYesNo.setVisible(true);
			jbResetShowcount.setVisible(true);

			row = jtAllMedia.getSelectedRow();
			if (row >= 0) {
				ClientMediaFile selected = mediaModel.getValueAt(row);

				currentSelectedMediaFile = selected;
				currentSelectedTickerElement = null;

				jtfFileName.setText(selected.getName());
				jlShowcountNumber.setText(Integer.toString(selected.getShowCount()));
				if (selected.isCurrent()) {
					jlCurrentYesNo.setText("Yes");
				} else {
					jlCurrentYesNo.setText("No");
				}

				jcbPriorityChange.setSelectedItem(currentSelectedMediaFile.getPriority());

			}

			break;
		case 1:

			jlFileName.setText("Filename");
			jchbEnabled.setVisible(false);
			jlPriority.setVisible(true);
			jcbPriorityChange.setVisible(true);
			jlShowcount.setVisible(true);
			jlShowcountNumber.setVisible(true);
			jlCurrent.setVisible(true);
			jlCurrentYesNo.setVisible(true);
			jbResetShowcount.setVisible(true);

			row = jtCustomQueue.getSelectedRow();
			if (row >= 0) {
				ClientMediaFile selected = mediaModel.getMediaFileById(mediaModel.getCustomQueue()
						.get(row));

				currentSelectedMediaFile = selected;
				currentSelectedTickerElement = null;

				jtfFileName.setText(selected.getName());
				jlShowcountNumber.setText(Integer.toString(selected.getShowCount()));
				if (selected.isCurrent()) {
					jlCurrentYesNo.setText("Yes");
				} else {
					jlCurrentYesNo.setText("No");
				}
				jcbPriorityChange.setSelectedItem(currentSelectedMediaFile.getPriority());
			}
			break;
		case 2:
			jlFileName.setText("Text");
			jchbEnabled.setVisible(true);
			jlPriority.setVisible(false);
			jcbPriorityChange.setVisible(false);
			jlShowcount.setVisible(false);
			jlShowcountNumber.setVisible(false);
			jlCurrent.setVisible(false);
			jlCurrentYesNo.setVisible(false);
			jbResetShowcount.setVisible(false);

			row = jtLiveTicker.getSelectedRow();
			if (row >= 0) {
				ClientTickerElement selected = tickerModel.getValueAt(row);

				currentSelectedMediaFile = null;
				currentSelectedTickerElement = selected;

				jtfFileName.setText(selected.getText());
				jchbEnabled.setSelected(selected.isShow());
			}
			break;
		default:
			log.debug("remove: no propper tab selected");
			break;

		}

	}

	private void updatePreviewLable() throws MediaDoesNotExsistException {
		int selectedTab = tabbedPaneContainer.getSelectedIndex();
		int row;
		switch (selectedTab) {
		case 0:
			row = jtAllMedia.getSelectedRow();
			if (row >= 0) {
				ClientMediaFile selected = mediaModel.getValueAt(row);
				jlPreview.setIcon(selected.getPreview());
			}

			break;
		case 1:
			row = jtCustomQueue.getSelectedRow();
			if (row >= 0) {
				ClientMediaFile selected = mediaModel.getMediaFileById(mediaModel.getCustomQueue()
						.get(row));
				jlPreview.setIcon(selected.getPreview());
			}
			break;
		case 2:
			jlPreview.setIcon(null);
			jlPreview.setText(tickerModel.completeTickerText());
			break;
		default:
			log.debug("remove: no propper tab selected");
			break;

		}

	}

	/**
	 * changing programs standard behaviour if user clicks the "x" on the main
	 * frame therefore it invokes the quit method
	 */
	@Override
	protected void processWindowEvent(WindowEvent e) {

		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			quit();
		} else {
			super.processWindowEvent(e);
		}
	}

	private void queue(int row) {
		proxy.sendQueueSelectedMedia(row);
	}

	protected void dequeue(int row) {
		proxy.sendDequeueSelectedMedia(row);
	}

	private class TimeLeftData {
		private long timeleftInSeconds;

		public TimeLeftData(long timeleftInSeconds) {
			this.timeleftInSeconds = timeleftInSeconds;
		}

		public void decreaseTimeLeftByOne() {
			timeleftInSeconds--;
		}

		public long getTimeleftInSeconds() {
			return timeleftInSeconds;
		}

		public void setTimeleftInSeconds(long timeleftInSeconds) {
			this.timeleftInSeconds = timeleftInSeconds;
		}
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed"
		// desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase
		 * /tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(MainClientGUIWindow.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MainClientGUIWindow.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MainClientGUIWindow.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainClientGUIWindow.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainClientGUIWindow(null, null).setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jbAddFile;
	private javax.swing.JButton jbAddThemeslide;
	private javax.swing.JButton jbAddTickerElement;
	private javax.swing.JButton jbRemoveFromList;
	private javax.swing.JButton jbResetFileData;
	private javax.swing.JButton jbResetShowcount;
	private javax.swing.JButton jbShowNext;
	private javax.swing.JButton jbShowSelected;
	private javax.swing.JButton jbUpdateFileData;
	private javax.swing.JComboBox jcbPriorityChange;
	private javax.swing.JCheckBox jchbEnabled;
	private javax.swing.JLabel jlAutomodeTimeleft;
	private javax.swing.JLabel jlCurrent;
	private javax.swing.JLabel jlCurrentYesNo;
	private javax.swing.JLabel jlFileName;
	private javax.swing.JLabel jlPreview;
	private javax.swing.JLabel jlPriority;
	private javax.swing.JLabel jlShowcount;
	private javax.swing.JLabel jlShowcountNumber;
	private javax.swing.JMenu jmFile;
	private javax.swing.JMenu jmPrefs;
	private javax.swing.JMenuBar jmbMain;
	private javax.swing.JMenuItem jmiAddCntDown;
	private javax.swing.JMenuItem jmiAddFile;
	private javax.swing.JMenuItem jmiAddPrio;
	private javax.swing.JMenuItem jmiAddTheme;
	private javax.swing.JMenuItem jmiAddThemeslide;
	private javax.swing.JMenuItem jmiAddTickerElt;
	private javax.swing.JMenuItem jmiExit;
	private javax.swing.JMenuItem jmiPopToggleActivated;
	private javax.swing.JMenuItem jmiPrefs;
	private javax.swing.JMenuItem jmiRemove;
	private javax.swing.JMenuItem jmipopAddElement;
	private javax.swing.JMenuItem jmipopAddFile;
	private javax.swing.JMenuItem jmipopAddThemeslide;
	private javax.swing.JMenuItem jmipopDequeue;
	private javax.swing.JMenuItem jmipopQueue;
	private javax.swing.JMenuItem jmipopRemove;
	private javax.swing.JMenuItem jmipopRemoveElement;
	private javax.swing.JMenuItem jmipopShow;
	private javax.swing.JMenuItem jmipopShowNext;
	private javax.swing.JMenuItem jmipopShowNextInQueue;
	private javax.swing.JRadioButtonMenuItem jmirbAutomode;
	private javax.swing.JPanel jpAllMedia;
	private javax.swing.JPanel jpButtonContainer;
	private javax.swing.JPanel jpCustomQueue;
	private javax.swing.JPanel jpEditor;
	private javax.swing.JPanel jpLiveTicker;
	private javax.swing.JPanel jpPreviewAndEditor;
	private javax.swing.JPopupMenu jpmAllTables;
	private javax.swing.JPopupMenu jpmQueueTable;
	private javax.swing.JPopupMenu jpmTickerTable;
	private javax.swing.JSeparator jsToolbar;
	private javax.swing.JScrollPane jspEditor;
	private javax.swing.JScrollPane jspMediaTableContainer;
	private javax.swing.JScrollPane jspPreview;
	private javax.swing.JScrollPane jspQueueTableContainer;
	private javax.swing.JScrollPane jspTickerTableContainer;
	private javax.swing.JTable jtAllMedia;
	private javax.swing.JTable jtCustomQueue;
	private javax.swing.JTable jtLiveTicker;
	private javax.swing.JTextField jtfFileName;
	private javax.swing.JTabbedPane tabbedPaneContainer;

	// End of variables declaration//GEN-END:variables

	public TimeLeftData getTimeleftData() {
		return timeleftData;
	}

}
