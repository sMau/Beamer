/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.netprojectev.client.gui.preferences;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import org.apache.logging.log4j.Logger;

import de.netprojectev.client.ConstantsClient;
import de.netprojectev.client.gui.main.MainClientGUIWindow;
import de.netprojectev.client.model.PreferencesModelClient;
import de.netprojectev.client.model.PreferencesModelClient.FullscreenStateListener;
import de.netprojectev.client.model.PreferencesModelClient.LiveTickerStateListener;
import de.netprojectev.client.networking.ClientMessageProxy;
import de.netprojectev.client.networking.ClientMessageProxy.ServerPropertyUpdateListener;
import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.datastructures.media.Theme;
import de.netprojectev.exceptions.PriorityDoesNotExistException;
import de.netprojectev.exceptions.ThemeDoesNotExistException;
import de.netprojectev.misc.LoggerBuilder;
import de.netprojectev.misc.Misc;
import de.netprojectev.server.ConstantsServer;

/**
 * 
 * @author samu
 */
public class PreferencesFrame extends javax.swing.JFrame {

	private static final Logger log = LoggerBuilder.createLogger(PreferencesFrame.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1287233997445904109L;

	private final ClientMessageProxy proxy;
	private final PreferencesModelClient prefs;

	/**
	 * Creates new form PreferencesFrame
	 */
	public PreferencesFrame(Frame parent, ClientMessageProxy proxy) {
		this.prefs = proxy.getPrefs();
		this.proxy = proxy;
		initComponents();
		setLocationRelativeTo(parent);
		
		proxy.setServerPropertyUpdateListener(new ServerPropertyUpdateListener() {

			@Override
			public void propertyUpdated() {
				updateServerProperties();
			}

		});

		jliPriorities.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				updatePriorityPanel();
			}
		});

		jliThemes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				updateThemePanel();
			}

		});

		prefs.setLiveTickerStateListener(new LiveTickerStateListener() {

			@Override
			public void update() {
				updateLiveTickerState();
			}

		});

		prefs.setFullscreenStateListener(new FullscreenStateListener() {

			@Override
			public void update() {
				updateFullscreenState();
			}

		});

		updateServerProperties();

	}

	private void updateServerProperties() {
		/*
		 * some general inits
		 */
		String[] fontFamilies = PreferencesModelClient.getServerFonts();
		
		for(int i = 0; i < fontFamilies.length; i++) {
			jcbCountdownFont.addItem(fontFamilies[i]);
			jcbTickerFont.addItem(fontFamilies[i]);
		}
		
		
		for(int i = 0; i < ConstantsClient.FONT_SIZES.length; i++) {
			jcbCreatorFontSize.addItem(ConstantsClient.FONT_SIZES[i]);
			jcbTickerFontSize.addItem(ConstantsClient.FONT_SIZES[i]);
		}
		
		for(int i = 0; i < ConstantsClient.FONT_FAMILIES.length; i++) {
			jcbCreatorFont.addItem(ConstantsClient.FONT_FAMILIES[i]);
		}
		
		for(int i = 0; i < ConstantsClient.FONT_SIZES_COUNTDOWN.length; i++) {
			jcbCountdownFontSize.addItem(ConstantsClient.FONT_SIZES_COUNTDOWN[i]);
		}
		
		/*
		 * General
		 */
		updateFullscreenState();

		
		/*
		 * Priorities
		 */
		jlDefaultPrioVar.setText(prefs.getDefaultPriority().getName());

		
		/*
		 * Live Ticker
		 */
		updateLiveTickerState();
		jtfTickerSeparator.setText(PreferencesModelClient.getServerPropertyByKey(ConstantsServer.PROP_TICKER_SEPERATOR));
		jcbTickerFont.setSelectedItem(PreferencesModelClient.getServerPropertyByKey(ConstantsServer.PROP_TICKER_FONTTYPE));
		jcbTickerFontSize.setSelectedItem(PreferencesModelClient.getServerPropertyByKey(ConstantsServer.PROP_TICKER_FONTSIZE));
		
		Color tickerFontColor = Color.BLACK;
		
		try {
			tickerFontColor = new Color(Integer.parseInt(PreferencesModelClient.getServerPropertyByKey(ConstantsServer.PROP_TICKER_FONTCOLOR)));
		} catch (NumberFormatException e) {
			log.warn("Could not read ticker font color from prefs. Setting to black.", e );
			tickerFontColor = Color.BLACK;
		}
		
		jbChooseTickerFontColor.setBackground(tickerFontColor);
		jbChooseTickerFontColor.setForeground(tickerFontColor);
		
		
		Color tickerBackgroundColor = Color.WHITE;
		
		try {
			tickerBackgroundColor = new Color(Integer.parseInt(PreferencesModelClient.getServerPropertyByKey(ConstantsServer.PROP_TICKER_BACKGROUND_COLOR)));
		} catch (NumberFormatException e) {
			log.warn("Could not read ticker background color from prefs. Setting to white.", e );
			tickerBackgroundColor = Color.WHITE;
		}
		
		jbChooseTickerBGColor.setBackground(tickerBackgroundColor);
		jbChooseTickerBGColor.setForeground(tickerBackgroundColor);
		
		int tickerSpeed = Integer.parseInt(PreferencesModelClient.getServerPropertyByKey(ConstantsServer.PROP_TICKER_SPEED));
		String speedAsString;
		if(tickerSpeed <= 20) {
			speedAsString = "High";
		} else if(tickerSpeed <= 30) {
			speedAsString = "Medium";
		} else {
			speedAsString = "Low";
		}
		jcbTickerSpeed.setSelectedItem(speedAsString);
		
		int tickerBacḱgroundAlpha = (int) (ConstantsServer.DEFAULT_TICKER_BACKGROUND_ALPHA * 100);
		try {
			tickerBacḱgroundAlpha = (int) (Float.parseFloat(PreferencesModelClient.getServerPropertyByKey(ConstantsServer.PROP_TICKER_BACKGROUND_ALPHA)) * 100);
		} catch (NumberFormatException e1) {
			tickerBacḱgroundAlpha = (int) (ConstantsServer.DEFAULT_TICKER_BACKGROUND_ALPHA * 100);
			log.warn("Could not read ticker background alpha from prefs. Setting to default.", e1 );
		}
		jslTickerBackgroundAlpha.setValue(tickerBacḱgroundAlpha);
		
		/*
		 * ThemeslideCreator
		 */
		jcbCreatorFontSize.setSelectedItem(PreferencesModelClient.getClientPropertyByKey(ConstantsClient.PROP_THEMESLIDECREATOR_PRESETTINGS_FONTSIZE));
		jcbCreatorFont.setSelectedItem(PreferencesModelClient.getClientPropertyByKey(ConstantsClient.PROP_THEMESLIDECREATOR_PRESETTINGS_FONTTYPE));
		jtfCreatorMarginLeft.setText(PreferencesModelClient.getClientPropertyByKey(ConstantsClient.PROP_THEMESLIDECREATOR_PRESETTINGS_MARGINLEFT));
		jtfCreatorMarginTop.setText(PreferencesModelClient.getClientPropertyByKey(ConstantsClient.PROP_THEMESLIDECREATOR_PRESETTINGS_MARGINTOP));
		
		
		/*
		 * Countdown
		 */
		jcbCountdownFont.setSelectedItem(PreferencesModelClient.getServerPropertyByKey(ConstantsServer.PROP_COUNTDOWN_FONTTYPE));
		jcbCountdownFontSize.setSelectedItem(PreferencesModelClient.getServerPropertyByKey(ConstantsServer.PROP_COUNTDOWN_FONTSIZE));

		Color cntdwnFontColor = Color.BLACK;
		try {
			cntdwnFontColor = new Color(Integer.parseInt(PreferencesModelClient.getServerPropertyByKey(ConstantsServer.PROP_COUNTDOWN_FONTCOLOR)));
		} catch (NumberFormatException e) {
			log.warn("Could not read countdown font color from prefs. Setting to black.", e );
			cntdwnFontColor = Color.BLACK;
		}
		jbChooseCountdownFontColor.setBackground(cntdwnFontColor);
		jbChooseCountdownFontColor.setForeground(cntdwnFontColor);

		
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtpPreferences = new javax.swing.JTabbedPane();
        jpGeneral = new javax.swing.JPanel();
        jbEnableFullscreen = new javax.swing.JButton();
        jbExitFullscreen = new javax.swing.JButton();
        jlFullscreen = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jlGeneralServerControl = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jbServerShutdown = new javax.swing.JButton();
        jpPriorities = new javax.swing.JPanel();
        jspPrioList = new javax.swing.JScrollPane();
        jliPriorities = new javax.swing.JList();
        jlPrioName = new javax.swing.JLabel();
        jlPrioNameVar = new javax.swing.JLabel();
        jlPrioTime = new javax.swing.JLabel();
        jlTimeToShowVar = new javax.swing.JLabel();
        jbRemovePrio = new javax.swing.JButton();
        jbAddPrio = new javax.swing.JButton();
        jlPrioDefault = new javax.swing.JLabel();
        jlDefaultPrioVar = new javax.swing.JLabel();
        jpThemes = new javax.swing.JPanel();
        jspThemeList = new javax.swing.JScrollPane();
        jliThemes = new javax.swing.JList();
        jlThemeName = new javax.swing.JLabel();
        jlThemeNameVar = new javax.swing.JLabel();
        jlThemeBackgroundPreview = new javax.swing.JLabel();
        jbRemoveTheme = new javax.swing.JButton();
        jbAddNewTheme = new javax.swing.JButton();
        jpTicker = new javax.swing.JPanel();
        jbStartTicker = new javax.swing.JButton();
        jbStopLiveTicker = new javax.swing.JButton();
        jlTickerStatus = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jlSeperator = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jtfTickerSeparator = new javax.swing.JTextField();
        jbUpdateTickerSeparator = new javax.swing.JButton();
        jlTickerLookAFeel = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jlTickerFont = new javax.swing.JLabel();
        jcbTickerFont = new javax.swing.JComboBox();
        jbTickerFontUpdate = new javax.swing.JButton();
        jlTickerFontColor = new javax.swing.JLabel();
        jbChooseTickerFontColor = new javax.swing.JButton();
        jbUpdateTickerFontColor = new javax.swing.JButton();
        jlTickerBGColor = new javax.swing.JLabel();
        jbChooseTickerBGColor = new javax.swing.JButton();
        jbUpdateTickerBGColor = new javax.swing.JButton();
        jlTickerFontSize = new javax.swing.JLabel();
        jcbTickerFontSize = new javax.swing.JComboBox();
        jbUpdateTickerFontSize = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jlTickerSpeed = new javax.swing.JLabel();
        jbUpdateTickerSpeed = new javax.swing.JButton();
        jSeparator9 = new javax.swing.JSeparator();
        jcbTickerSpeed = new javax.swing.JComboBox();
        jlTickerBackgroundAlpha = new javax.swing.JLabel();
        jslTickerBackgroundAlpha = new javax.swing.JSlider();
        jbUpdateTickerBGAlpha = new javax.swing.JButton();
        jpCountdown = new javax.swing.JPanel();
        jlCountdownLookAFeel = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jlCountdownFont = new javax.swing.JLabel();
        jcbCountdownFont = new javax.swing.JComboBox();
        jbUpdateCountdownFont = new javax.swing.JButton();
        jlCountdownFontColor = new javax.swing.JLabel();
        jbChooseCountdownFontColor = new javax.swing.JButton();
        jbUpdateCountdownFontColor = new javax.swing.JButton();
        jlCountdownFontSize = new javax.swing.JLabel();
        jcbCountdownFontSize = new javax.swing.JComboBox();
        jbUpdateCountdownFontSize = new javax.swing.JButton();
        jpThemeslideCreator = new javax.swing.JPanel();
        jlFontAttrCreator = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jlFontSizeCreator = new javax.swing.JLabel();
        jcbCreatorFontSize = new javax.swing.JComboBox();
        jlCreatorFontColor = new javax.swing.JLabel();
        jbCreatorChooseFontColor = new javax.swing.JButton();
        jlCreatorFontFamily = new javax.swing.JLabel();
        jcbCreatorFont = new javax.swing.JComboBox();
        jlCreatorMargin = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jlCreatorMarginLeft = new javax.swing.JLabel();
        jtfCreatorMarginLeft = new javax.swing.JTextField();
        jlCreatorMarginTop = new javax.swing.JLabel();
        jtfCreatorMarginTop = new javax.swing.JTextField();
        jbClosePrefs = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Preferences");

        jtpPreferences.setDoubleBuffered(true);

        jbEnableFullscreen.setLabel("Enter");
        jbEnableFullscreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEnableFullscreenActionPerformed(evt);
            }
        });

        jbExitFullscreen.setLabel("Exit");
        jbExitFullscreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbExitFullscreenActionPerformed(evt);
            }
        });

        jlFullscreen.setText("Fullscreen");

        jlGeneralServerControl.setText("Server Control");

        jbServerShutdown.setText("Server Shutdown");
        jbServerShutdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbServerShutdownActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpGeneralLayout = new javax.swing.GroupLayout(jpGeneral);
        jpGeneral.setLayout(jpGeneralLayout);
        jpGeneralLayout.setHorizontalGroup(
            jpGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpGeneralLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jbServerShutdown)
                        .addContainerGap(452, Short.MAX_VALUE))
                    .addGroup(jpGeneralLayout.createSequentialGroup()
                        .addGroup(jpGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpGeneralLayout.createSequentialGroup()
                                .addComponent(jlFullscreen)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1))
                            .addGroup(jpGeneralLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jbEnableFullscreen)
                                .addGap(18, 18, 18)
                                .addComponent(jbExitFullscreen)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jpGeneralLayout.createSequentialGroup()
                                .addComponent(jlGeneralServerControl)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator3)))
                        .addContainerGap())))
        );
        jpGeneralLayout.setVerticalGroup(
            jpGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlFullscreen)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jpGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbEnableFullscreen)
                    .addComponent(jbExitFullscreen))
                .addGap(18, 18, 18)
                .addGroup(jpGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlGeneralServerControl)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbServerShutdown)
                .addContainerGap(286, Short.MAX_VALUE))
        );

        jtpPreferences.addTab("General", jpGeneral);

        jspPrioList.setDoubleBuffered(true);

        jliPriorities.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jliPriorities.setDoubleBuffered(true);
        jliPriorities.setModel(new PriorityListModel(prefs));
        jspPrioList.setViewportView(jliPriorities);

        jlPrioName.setText("Name");

        jlPrioNameVar.setText("unknown");

        jlPrioTime.setText("Time to show");

        jlTimeToShowVar.setText("unknown");

        jbRemovePrio.setText("Remove");
        jbRemovePrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemovePrioActionPerformed(evt);
            }
        });

        jbAddPrio.setText("Add New");
        jbAddPrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddPrioActionPerformed(evt);
            }
        });

        jlPrioDefault.setText("Default");

        jlDefaultPrioVar.setText("unknown");

        javax.swing.GroupLayout jpPrioritiesLayout = new javax.swing.GroupLayout(jpPriorities);
        jpPriorities.setLayout(jpPrioritiesLayout);
        jpPrioritiesLayout.setHorizontalGroup(
            jpPrioritiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPrioritiesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jspPrioList, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80)
                .addGroup(jpPrioritiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpPrioritiesLayout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(jbAddPrio))
                    .addGroup(jpPrioritiesLayout.createSequentialGroup()
                        .addGroup(jpPrioritiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlPrioTime)
                            .addComponent(jlPrioName)
                            .addComponent(jlPrioDefault)
                            .addComponent(jbRemovePrio))
                        .addGap(30, 30, 30)
                        .addGroup(jpPrioritiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlDefaultPrioVar)
                            .addComponent(jlPrioNameVar)
                            .addComponent(jlTimeToShowVar))))
                .addContainerGap(125, Short.MAX_VALUE))
        );
        jpPrioritiesLayout.setVerticalGroup(
            jpPrioritiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPrioritiesLayout.createSequentialGroup()
                .addGroup(jpPrioritiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpPrioritiesLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jpPrioritiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlPrioName)
                            .addComponent(jlPrioNameVar))
                        .addGap(18, 18, 18)
                        .addGroup(jpPrioritiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlPrioTime)
                            .addComponent(jlTimeToShowVar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jpPrioritiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlPrioDefault)
                            .addComponent(jlDefaultPrioVar))
                        .addGap(11, 11, 11)
                        .addGroup(jpPrioritiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbRemovePrio)
                            .addComponent(jbAddPrio)))
                    .addGroup(jpPrioritiesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jspPrioList, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)))
                .addGap(45, 45, 45))
        );

        jtpPreferences.addTab("Priorities", jpPriorities);

        jspThemeList.setDoubleBuffered(true);

        jliThemes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jliThemes.setDoubleBuffered(true);
        jliThemes.setModel(new ThemeListModel(prefs));
        jspThemeList.setViewportView(jliThemes);

        jlThemeName.setText("Name");

        jlThemeNameVar.setText("unknown");

        jlThemeBackgroundPreview.setText("Preview");

        jbRemoveTheme.setText("Remove");
        jbRemoveTheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoveThemeActionPerformed(evt);
            }
        });

        jbAddNewTheme.setText("Add New");
        jbAddNewTheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddNewThemeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpThemesLayout = new javax.swing.GroupLayout(jpThemes);
        jpThemes.setLayout(jpThemesLayout);
        jpThemesLayout.setHorizontalGroup(
            jpThemesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpThemesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jspThemeList, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80)
                .addGroup(jpThemesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpThemesLayout.createSequentialGroup()
                        .addGroup(jpThemesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpThemesLayout.createSequentialGroup()
                                .addComponent(jlThemeName)
                                .addGap(77, 77, 77)
                                .addComponent(jlThemeNameVar))
                            .addGroup(jpThemesLayout.createSequentialGroup()
                                .addComponent(jbRemoveTheme)
                                .addGap(18, 18, 18)
                                .addComponent(jbAddNewTheme)))
                        .addGap(0, 119, Short.MAX_VALUE))
                    .addComponent(jlThemeBackgroundPreview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpThemesLayout.setVerticalGroup(
            jpThemesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpThemesLayout.createSequentialGroup()
                .addGroup(jpThemesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpThemesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jspThemeList, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpThemesLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jpThemesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlThemeName)
                            .addComponent(jlThemeNameVar))
                        .addGap(18, 18, 18)
                        .addComponent(jlThemeBackgroundPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jpThemesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbRemoveTheme)
                            .addComponent(jbAddNewTheme))))
                .addContainerGap(100, Short.MAX_VALUE))
        );

        jtpPreferences.addTab("Themes", jpThemes);

        jbStartTicker.setText("Start");
        jbStartTicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbStartTickerActionPerformed(evt);
            }
        });

        jbStopLiveTicker.setText("Stop");
        jbStopLiveTicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbStopLiveTickerActionPerformed(evt);
            }
        });

        jlTickerStatus.setText("Status");

        jlSeperator.setText("Separator");

        jbUpdateTickerSeparator.setText("Update");
        jbUpdateTickerSeparator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbUpdateTickerSeparatorActionPerformed(evt);
            }
        });

        jlTickerLookAFeel.setText("Look and Feel");

        jlTickerFont.setText("Font");

        jcbTickerFont.setDoubleBuffered(true);

        jbTickerFontUpdate.setText("Update");
        jbTickerFontUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbTickerFontUpdateActionPerformed(evt);
            }
        });

        jlTickerFontColor.setText("Font Color");

        jbChooseTickerFontColor.setText("Choose Color");
        jbChooseTickerFontColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbChooseTickerFontColorActionPerformed(evt);
            }
        });

        jbUpdateTickerFontColor.setText("Update");
        jbUpdateTickerFontColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbUpdateTickerFontColorActionPerformed(evt);
            }
        });

        jlTickerBGColor.setText("Background Color");

        jbChooseTickerBGColor.setText("Choose Color");
        jbChooseTickerBGColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbChooseTickerBGColorActionPerformed(evt);
            }
        });

        jbUpdateTickerBGColor.setText("Update");
        jbUpdateTickerBGColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbUpdateTickerBGColorActionPerformed(evt);
            }
        });

        jlTickerFontSize.setText("Font Size");

        jcbTickerFontSize.setDoubleBuffered(true);

        jbUpdateTickerFontSize.setText("Update");
        jbUpdateTickerFontSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbUpdateTickerFontSizeActionPerformed(evt);
            }
        });

        jLabel1.setText("Causes a ticker restart");

        jLabel2.setText("Causes a ticker restart");

        jLabel3.setText("Causes a ticker restart");

        jlTickerSpeed.setText("Speed");

        jbUpdateTickerSpeed.setText("Update");
        jbUpdateTickerSpeed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbUpdateTickerSpeedActionPerformed(evt);
            }
        });

        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jcbTickerSpeed.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Low", "Medium", "High" }));

        jlTickerBackgroundAlpha.setText("Background Alpha");

        jslTickerBackgroundAlpha.setMajorTickSpacing(10);
        jslTickerBackgroundAlpha.setMinorTickSpacing(1);
        jslTickerBackgroundAlpha.setToolTipText("100 is completly opaque. 0 means completly transculent");
        jslTickerBackgroundAlpha.setValue(30);

        jbUpdateTickerBGAlpha.setText("Update");
        jbUpdateTickerBGAlpha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbUpdateTickerBGAlphaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpTickerLayout = new javax.swing.GroupLayout(jpTicker);
        jpTicker.setLayout(jpTickerLayout);
        jpTickerLayout.setHorizontalGroup(
            jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTickerLayout.createSequentialGroup()
                .addGroup(jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpTickerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpTickerLayout.createSequentialGroup()
                                .addComponent(jlTickerStatus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2))
                            .addGroup(jpTickerLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jbStartTicker)
                                .addGap(18, 18, 18)
                                .addComponent(jbStopLiveTicker)
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jlTickerSpeed)
                                .addGap(18, 18, 18)
                                .addComponent(jcbTickerSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jbUpdateTickerSpeed)
                                .addGap(0, 145, Short.MAX_VALUE))
                            .addGroup(jpTickerLayout.createSequentialGroup()
                                .addComponent(jlSeperator)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator4))))
                    .addGroup(jpTickerLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jtfTickerSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbUpdateTickerSeparator)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jpTickerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpTickerLayout.createSequentialGroup()
                                .addComponent(jlTickerLookAFeel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator5))
                            .addGroup(jpTickerLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTickerLayout.createSequentialGroup()
                                            .addComponent(jlTickerFont)
                                            .addGap(101, 101, 101))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTickerLayout.createSequentialGroup()
                                            .addComponent(jlTickerFontSize)
                                            .addGap(73, 73, 73)))
                                    .addGroup(jpTickerLayout.createSequentialGroup()
                                        .addGroup(jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jlTickerFontColor)
                                            .addComponent(jlTickerBGColor)
                                            .addComponent(jlTickerBackgroundAlpha))
                                        .addGap(18, 18, 18)))
                                .addGroup(jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpTickerLayout.createSequentialGroup()
                                        .addGroup(jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jcbTickerFont, 0, 111, Short.MAX_VALUE)
                                            .addComponent(jcbTickerFontSize, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jpTickerLayout.createSequentialGroup()
                                                .addComponent(jbUpdateTickerFontSize)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel2))
                                            .addGroup(jpTickerLayout.createSequentialGroup()
                                                .addComponent(jbTickerFontUpdate)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel1))))
                                    .addGroup(jpTickerLayout.createSequentialGroup()
                                        .addComponent(jbChooseTickerFontColor)
                                        .addGap(18, 18, 18)
                                        .addComponent(jbUpdateTickerFontColor))
                                    .addGroup(jpTickerLayout.createSequentialGroup()
                                        .addComponent(jbChooseTickerBGColor)
                                        .addGap(18, 18, 18)
                                        .addComponent(jbUpdateTickerBGColor))
                                    .addGroup(jpTickerLayout.createSequentialGroup()
                                        .addComponent(jslTickerBackgroundAlpha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jbUpdateTickerBGAlpha)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jpTickerLayout.setVerticalGroup(
            jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTickerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlTickerStatus)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jbStartTicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbStopLiveTicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlTickerSpeed, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbUpdateTickerSpeed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcbTickerSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlSeperator)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfTickerSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbUpdateTickerSeparator)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlTickerLookAFeel)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlTickerFont)
                    .addComponent(jcbTickerFont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbTickerFontUpdate)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlTickerFontSize)
                    .addComponent(jcbTickerFontSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbUpdateTickerFontSize)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpTickerLayout.createSequentialGroup()
                        .addGroup(jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlTickerFontColor)
                            .addComponent(jbChooseTickerFontColor)
                            .addComponent(jbUpdateTickerFontColor))
                        .addGap(18, 18, 18)
                        .addGroup(jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlTickerBGColor)
                            .addComponent(jbChooseTickerBGColor)
                            .addComponent(jbUpdateTickerBGColor))
                        .addGap(18, 18, 18)
                        .addGroup(jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jlTickerBackgroundAlpha)
                            .addComponent(jslTickerBackgroundAlpha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jbUpdateTickerBGAlpha))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jtpPreferences.addTab("Ticker", jpTicker);

        jlCountdownLookAFeel.setText("Look and Feel");

        jlCountdownFont.setText("Font");

        jcbCountdownFont.setDoubleBuffered(true);

        jbUpdateCountdownFont.setText("Update");
        jbUpdateCountdownFont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbUpdateCountdownFontActionPerformed(evt);
            }
        });

        jlCountdownFontColor.setText("Font Color");

        jbChooseCountdownFontColor.setText("Choose Color");
        jbChooseCountdownFontColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbChooseCountdownFontColorActionPerformed(evt);
            }
        });

        jbUpdateCountdownFontColor.setText("Update");
        jbUpdateCountdownFontColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbUpdateCountdownFontColorActionPerformed(evt);
            }
        });

        jlCountdownFontSize.setText("Font Size");

        jcbCountdownFontSize.setDoubleBuffered(true);

        jbUpdateCountdownFontSize.setText("Update");
        jbUpdateCountdownFontSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbUpdateCountdownFontSizeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpCountdownLayout = new javax.swing.GroupLayout(jpCountdown);
        jpCountdown.setLayout(jpCountdownLayout);
        jpCountdownLayout.setHorizontalGroup(
            jpCountdownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCountdownLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCountdownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpCountdownLayout.createSequentialGroup()
                        .addComponent(jlCountdownLookAFeel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator6)
                        .addContainerGap())
                    .addGroup(jpCountdownLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jpCountdownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlCountdownFontColor)
                            .addComponent(jlCountdownFontSize)
                            .addComponent(jlCountdownFont))
                        .addGap(23, 23, 23)
                        .addGroup(jpCountdownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jbChooseCountdownFontColor)
                            .addComponent(jcbCountdownFont, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcbCountdownFontSize, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jpCountdownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbUpdateCountdownFont)
                            .addComponent(jbUpdateCountdownFontSize)
                            .addComponent(jbUpdateCountdownFontColor))
                        .addContainerGap(296, Short.MAX_VALUE))))
        );
        jpCountdownLayout.setVerticalGroup(
            jpCountdownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCountdownLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCountdownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlCountdownLookAFeel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpCountdownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCountdownFont)
                    .addComponent(jcbCountdownFont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbUpdateCountdownFont))
                .addGap(18, 18, 18)
                .addGroup(jpCountdownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCountdownFontColor)
                    .addComponent(jbChooseCountdownFontColor)
                    .addComponent(jbUpdateCountdownFontColor))
                .addGap(18, 18, 18)
                .addGroup(jpCountdownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCountdownFontSize)
                    .addComponent(jcbCountdownFontSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbUpdateCountdownFontSize))
                .addContainerGap(263, Short.MAX_VALUE))
        );

        jtpPreferences.addTab("Countdown", jpCountdown);

        jlFontAttrCreator.setText("Font Attribute Defaults");

        jlFontSizeCreator.setText("Font Size");

        jcbCreatorFontSize.setDoubleBuffered(true);
        jcbCreatorFontSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbCreatorFontSizeActionPerformed(evt);
            }
        });

        jlCreatorFontColor.setText("Font Color");

        jbCreatorChooseFontColor.setText("Choose Color");
        jbCreatorChooseFontColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCreatorChooseFontColorActionPerformed(evt);
            }
        });

        jlCreatorFontFamily.setText("Font");

        jcbCreatorFont.setDoubleBuffered(true);
        jcbCreatorFont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbCreatorFontActionPerformed(evt);
            }
        });

        jlCreatorMargin.setText("Margin Default");

        jlCreatorMarginLeft.setText("Margin Left");

        jtfCreatorMarginLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfCreatorMarginLeftActionPerformed(evt);
            }
        });

        jlCreatorMarginTop.setText("Margin Top");

        jtfCreatorMarginTop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfCreatorMarginTopActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpThemeslideCreatorLayout = new javax.swing.GroupLayout(jpThemeslideCreator);
        jpThemeslideCreator.setLayout(jpThemeslideCreatorLayout);
        jpThemeslideCreatorLayout.setHorizontalGroup(
            jpThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpThemeslideCreatorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpThemeslideCreatorLayout.createSequentialGroup()
                        .addGroup(jpThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpThemeslideCreatorLayout.createSequentialGroup()
                                .addComponent(jlFontAttrCreator)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator7))
                            .addGroup(jpThemeslideCreatorLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jlCreatorFontColor)
                                .addGap(26, 26, 26)
                                .addComponent(jbCreatorChooseFontColor)
                                .addGap(0, 379, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jpThemeslideCreatorLayout.createSequentialGroup()
                        .addGroup(jpThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpThemeslideCreatorLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jpThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlCreatorFontFamily)
                                    .addComponent(jlFontSizeCreator))
                                .addGap(34, 34, 34)
                                .addGroup(jpThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jcbCreatorFontSize, 0, 111, Short.MAX_VALUE)
                                    .addComponent(jcbCreatorFont, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jpThemeslideCreatorLayout.createSequentialGroup()
                                .addComponent(jlCreatorMargin)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator8)))
                        .addGap(6, 6, 6))
                    .addGroup(jpThemeslideCreatorLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jpThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jpThemeslideCreatorLayout.createSequentialGroup()
                                .addComponent(jlCreatorMarginTop)
                                .addGap(18, 18, 18)
                                .addComponent(jtfCreatorMarginTop))
                            .addGroup(jpThemeslideCreatorLayout.createSequentialGroup()
                                .addComponent(jlCreatorMarginLeft)
                                .addGap(18, 18, 18)
                                .addComponent(jtfCreatorMarginLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jpThemeslideCreatorLayout.setVerticalGroup(
            jpThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpThemeslideCreatorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlFontAttrCreator))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCreatorFontFamily)
                    .addComponent(jcbCreatorFont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlFontSizeCreator)
                    .addComponent(jcbCreatorFontSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCreatorFontColor)
                    .addComponent(jbCreatorChooseFontColor))
                .addGroup(jpThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpThemeslideCreatorLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jlCreatorMargin))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpThemeslideCreatorLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCreatorMarginLeft)
                    .addComponent(jtfCreatorMarginLeft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCreatorMarginTop)
                    .addComponent(jtfCreatorMarginTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(156, Short.MAX_VALUE))
        );

        jtpPreferences.addTab("Themeslide Creator", jpThemeslideCreator);

        jbClosePrefs.setText("Close");
        jbClosePrefs.setDoubleBuffered(true);
        jbClosePrefs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbClosePrefsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jbClosePrefs)
                .addGap(34, 34, 34))
            .addComponent(jtpPreferences, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jtpPreferences, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbClosePrefs)
                .addGap(0, 17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbTickerFontUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbTickerFontUpdateActionPerformed
        String newFont = (String) jcbTickerFont.getSelectedItem();
        proxy.sendPropertyUpdate(ConstantsServer.PROP_TICKER_FONTTYPE, newFont);
    }//GEN-LAST:event_jbTickerFontUpdateActionPerformed

    private void jbUpdateTickerFontColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbUpdateTickerFontColorActionPerformed
        int rgbColor = jbChooseTickerFontColor.getBackground().getRGB();
        proxy.sendPropertyUpdate(ConstantsServer.PROP_TICKER_FONTCOLOR, Integer.toString(rgbColor));
    }//GEN-LAST:event_jbUpdateTickerFontColorActionPerformed

    private void jbChooseTickerFontColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbChooseTickerFontColorActionPerformed
    	openColorPickerTicker((JButton) evt.getSource());
    }//GEN-LAST:event_jbChooseTickerFontColorActionPerformed

    private void jbChooseTickerBGColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbChooseTickerBGColorActionPerformed
    	openColorPickerTicker((JButton) evt.getSource());
    }//GEN-LAST:event_jbChooseTickerBGColorActionPerformed

    private void jbUpdateTickerBGColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbUpdateTickerBGColorActionPerformed
    	int rgbColor = jbChooseTickerBGColor.getBackground().getRGB();
        proxy.sendPropertyUpdate(ConstantsServer.PROP_TICKER_BACKGROUND_COLOR, Integer.toString(rgbColor));
    }//GEN-LAST:event_jbUpdateTickerBGColorActionPerformed

    private void jbUpdateCountdownFontActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbUpdateCountdownFontActionPerformed
    	String newFont = (String) jcbCountdownFont.getSelectedItem();
        proxy.sendPropertyUpdate(ConstantsServer.PROP_COUNTDOWN_FONTTYPE, newFont);
    }//GEN-LAST:event_jbUpdateCountdownFontActionPerformed

    private void jbUpdateCountdownFontColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbUpdateCountdownFontColorActionPerformed
    	int rgbColor = jbChooseCountdownFontColor.getBackground().getRGB();
        proxy.sendPropertyUpdate(ConstantsServer.PROP_COUNTDOWN_FONTCOLOR, Integer.toString(rgbColor));
    }//GEN-LAST:event_jbUpdateCountdownFontColorActionPerformed

    private void jbUpdateCountdownFontSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbUpdateCountdownFontSizeActionPerformed
        String newSize = (String) jcbCountdownFontSize.getSelectedItem();
        proxy.sendPropertyUpdate(ConstantsServer.PROP_COUNTDOWN_FONTSIZE, newSize);
    }//GEN-LAST:event_jbUpdateCountdownFontSizeActionPerformed

    private void jbUpdateTickerFontSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbUpdateTickerFontSizeActionPerformed
    	String newSize = (String) jcbTickerFontSize.getSelectedItem();
        proxy.sendPropertyUpdate(ConstantsServer.PROP_TICKER_FONTSIZE, newSize);
    }//GEN-LAST:event_jbUpdateTickerFontSizeActionPerformed

    private void jbChooseCountdownFontColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbChooseCountdownFontColorActionPerformed
    	openColorPickerTicker((JButton) evt.getSource());
    }//GEN-LAST:event_jbChooseCountdownFontColorActionPerformed

    private void jbCreatorChooseFontColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCreatorChooseFontColorActionPerformed
    	Color newColor = openColorPickerTicker((JButton) evt.getSource());
    	if(newColor != null) {
    		PreferencesModelClient.setClientProperty(ConstantsClient.PROP_THEMESLIDECREATOR_PRESETTINGS_FONTCOLOR, Integer.toString(newColor.getRGB()));
    	}
    }//GEN-LAST:event_jbCreatorChooseFontColorActionPerformed

    private void jcbCreatorFontSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbCreatorFontSizeActionPerformed
    	String newSize = (String) jcbCreatorFontSize.getSelectedItem();
    	PreferencesModelClient.setClientProperty(ConstantsClient.PROP_THEMESLIDECREATOR_PRESETTINGS_FONTSIZE, newSize);
    }//GEN-LAST:event_jcbCreatorFontSizeActionPerformed

    private void jtfCreatorMarginLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfCreatorMarginLeftActionPerformed
        String newMarginLeft = jtfCreatorMarginLeft.getText().trim();
        try {
			Integer.parseInt(newMarginLeft);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Margin Left has to be a numeric value.", "Margin Left", JOptionPane.ERROR_MESSAGE);
			return;
		}
        PreferencesModelClient.setClientProperty(ConstantsClient.PROP_THEMESLIDECREATOR_PRESETTINGS_MARGINLEFT, newMarginLeft);
    }//GEN-LAST:event_jtfCreatorMarginLeftActionPerformed

    private void jtfCreatorMarginTopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfCreatorMarginTopActionPerformed
    	String newMarginTop = jtfCreatorMarginTop.getText().trim();
        try {
			Integer.parseInt(newMarginTop);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Margin Top has to be a numeric value.", "Margin Top", JOptionPane.ERROR_MESSAGE);
			return;
		}
        PreferencesModelClient.setClientProperty(ConstantsClient.PROP_THEMESLIDECREATOR_PRESETTINGS_MARGINTOP, newMarginTop);
    }//GEN-LAST:event_jtfCreatorMarginTopActionPerformed

    private void jcbCreatorFontActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbCreatorFontActionPerformed
        String newFont = (String) jcbCreatorFont.getSelectedItem();
        PreferencesModelClient.setClientProperty(ConstantsClient.PROP_THEMESLIDECREATOR_PRESETTINGS_FONTTYPE, newFont);
    }//GEN-LAST:event_jcbCreatorFontActionPerformed

    private void jbUpdateTickerSpeedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbUpdateTickerSpeedActionPerformed
        String tickerSpeed = (String) jcbTickerSpeed.getSelectedItem();
        switch (tickerSpeed) {
		case "Low":
			proxy.sendPropertyUpdate(ConstantsServer.PROP_TICKER_SPEED, ConstantsServer.DEFAULT_LOW_TICKER_SPEED);
			break;
		case "Medium":
			proxy.sendPropertyUpdate(ConstantsServer.PROP_TICKER_SPEED, ConstantsServer.DEFAULT_MEDIUM_TICKER_SPEED);
			break;
		case "High":
			proxy.sendPropertyUpdate(ConstantsServer.PROP_TICKER_SPEED, ConstantsServer.DEFAULT_HIGH_TICKER_SPEED);
			break;
		default:
			JOptionPane.showMessageDialog(this, "Error reading tickerspeed", "Ticker Speed", JOptionPane.ERROR_MESSAGE);
			break;
		}
    }//GEN-LAST:event_jbUpdateTickerSpeedActionPerformed

    private void jbUpdateTickerBGAlphaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbUpdateTickerBGAlphaActionPerformed
        float alphaToSet = ((float) jslTickerBackgroundAlpha.getValue()) / 100;
        log.debug("Read alpha in the client preferences before update: " + alphaToSet);
        proxy.sendPropertyUpdate(ConstantsServer.PROP_TICKER_BACKGROUND_ALPHA, Float.toString(alphaToSet));
    }//GEN-LAST:event_jbUpdateTickerBGAlphaActionPerformed

	private void jbClosePrefsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbClosePrefsActionPerformed
		dispose();
	}// GEN-LAST:event_jbClosePrefsActionPerformed

	private void jbAddPrioActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbAddPrioActionPerformed
		new AddPriorityDialog(this, true, proxy).setVisible(true);
		updatePriorityPanel();
	}// GEN-LAST:event_jbAddPrioActionPerformed

	private void jbRemovePrioActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbRemovePrioActionPerformed
		int selected = jliPriorities.getSelectedIndex();
		if (selected >= 0 && prefs.priorityCount() > 0 && selected < prefs.priorityCount()) {
			try {
				Priority selectedPrio = prefs.getPriorityAt(selected);
				if (selectedPrio.isDefaultPriority()) {
					JOptionPane.showMessageDialog(this, "You cannot remove the default priority.", "Remove Priority", JOptionPane.ERROR_MESSAGE);
					return;
				}
				proxy.sendRemovePriority(selectedPrio.getId());
			} catch (PriorityDoesNotExistException e) {
				proxy.errorRequestFullSync(e);
			}
			updatePriorityPanel();
		}

	}// GEN-LAST:event_jbRemovePrioActionPerformed

	private void jbRemoveThemeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbRemoveThemeActionPerformed
		int selected = jliThemes.getSelectedIndex();
		if (selected >= 0 && prefs.themeCount() > 0 && selected < prefs.themeCount()) {
			try {
				proxy.sendRemoveTheme(prefs.getThemeAt(selected).getId());
			} catch (ThemeDoesNotExistException e) {
				proxy.errorRequestFullSync(e);
			}
			updateThemePanel();
		}

	}// GEN-LAST:event_jbRemoveThemeActionPerformed

	private void jbAddNewThemeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbAddNewThemeActionPerformed
		new AddThemeDialog(this, true, proxy).setVisible(true);
		updateThemePanel();
	}// GEN-LAST:event_jbAddNewThemeActionPerformed

	private void jbStartTickerActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbStartTickerActionPerformed
		proxy.sendStartLiveTicker();
	}// GEN-LAST:event_jbStartTickerActionPerformed

	private void jbStopLiveTickerActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbStopLiveTickerActionPerformed
		proxy.sendStopLiveTicker();
	}// GEN-LAST:event_jbStopLiveTickerActionPerformed

	private void jbEnableFullscreenActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbEnableFullscreenActionPerformed
		proxy.sendEnterFullscreen();
	}// GEN-LAST:event_jbEnableFullscreenActionPerformed

	private void jbExitFullscreenActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbExitFullscreenActionPerformed
		proxy.sendExitFullscreen();
	}// GEN-LAST:event_jbExitFullscreenActionPerformed

	private void jbServerShutdownActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbServerShutdownActionPerformed
		int result = JOptionPane.showConfirmDialog(this, "Do you really want the server to shutdown?\nThis will cause a disconnect of all clients.", "Server Shutdown", JOptionPane.YES_NO_OPTION);

		if (result == JOptionPane.YES_OPTION) {
			proxy.sendRequestServerShutdown();
		}

	}// GEN-LAST:event_jbServerShutdownActionPerformed

	private void jbUpdateTickerSeparatorActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbUpdateTickerSeparatorActionPerformed
		String newSeparator = jtfTickerSeparator.getText();
		if (newSeparator != null && !newSeparator.equals("")) {
			proxy.sendPropertyUpdate(ConstantsServer.PROP_TICKER_SEPERATOR, newSeparator);
		} else {
			JOptionPane.showMessageDialog(this, "The ticker separator cannot be empty.", 
					"Illegal Value", JOptionPane.ERROR_MESSAGE);
		}
	}// GEN-LAST:event_jbUpdateTickerSeparatorActionPerformed

	@Override
	protected void processWindowEvent(WindowEvent e) {

		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			dispose();
		} else {
			super.processWindowEvent(e);
		}
	}

	private void updateThemePanel() {
		int row = jliThemes.getSelectedIndex();
		if (row >= 0) {
			Theme selected;
			try {
				selected = prefs.getThemeAt(row);
				jlThemeNameVar.setText(selected.getName());
				jlThemeBackgroundPreview.setText("");

				ImageIcon scaledPreview = Misc.getScaledImageIcon(selected.getBackgroundImage(), jlThemeBackgroundPreview.getWidth());

				jlThemeBackgroundPreview.setIcon(scaledPreview);
			} catch (ThemeDoesNotExistException e) {
				proxy.errorRequestFullSync(e);
			} catch (FileNotFoundException e) {
				log.error("Could not find theme image background.", e);
			} catch (IOException e) {
				log.error("Could not read theme image background.", e);
			}
		}
	}

	private void updatePriorityPanel() {
		int row = jliPriorities.getSelectedIndex();
		if (row >= 0) {
			Priority selected;
			try {
				selected = prefs.getPriorityAt(row);
				jlPrioNameVar.setText(selected.getName());
				jlTimeToShowVar.setText(Integer.toString(selected.getMinutesToShow()));
				jlDefaultPrioVar.setText(prefs.getDefaultPriority().getName());
			} catch (PriorityDoesNotExistException e) {
				proxy.errorRequestFullSync(e);
			}

		}
	}

	private void updateLiveTickerState() {
		boolean liveTickerEnabled = prefs.isLiveTickerRunning();
		if (liveTickerEnabled) {
			jbStartTicker.setEnabled(false);
			jbStopLiveTicker.setEnabled(true);
		} else {
			jbStartTicker.setEnabled(true);
			jbStopLiveTicker.setEnabled(false);
		}
	}

	private void updateFullscreenState() {
		boolean fullscreenEnabled = prefs.isFullscreen();
		if (fullscreenEnabled) {
			jbEnableFullscreen.setEnabled(false);
			jbExitFullscreen.setEnabled(true);
		} else {
			jbEnableFullscreen.setEnabled(true);
			jbExitFullscreen.setEnabled(false);
		}
	}
	
    /**
     * Opens a new {@link ColorPickerDialog} and assigns the correct ticker text color.
     */
	private Color openColorPickerTicker(JButton chooseColorButton) {
		ColorPickerDialog colorPicker = new ColorPickerDialog(this, true);
		
		colorPicker.setVisible(true);
		
		if(colorPicker != null) {
			if(colorPicker.getChoosenColor() != null) {
				Color bg =  new Color(colorPicker.getChoosenColor().getRed(), colorPicker.getChoosenColor().getGreen(), colorPicker.getChoosenColor().getBlue());
		        
				chooseColorButton.setBackground(bg);
				chooseColorButton.setForeground(bg);

				return bg;
			}
			
		}
		return null;
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
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(PreferencesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(PreferencesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(PreferencesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(PreferencesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new PreferencesFrame(null, null).setVisible(true);
			}
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JButton jbAddNewTheme;
    private javax.swing.JButton jbAddPrio;
    private javax.swing.JButton jbChooseCountdownFontColor;
    private javax.swing.JButton jbChooseTickerBGColor;
    private javax.swing.JButton jbChooseTickerFontColor;
    private javax.swing.JButton jbClosePrefs;
    private javax.swing.JButton jbCreatorChooseFontColor;
    private javax.swing.JButton jbEnableFullscreen;
    private javax.swing.JButton jbExitFullscreen;
    private javax.swing.JButton jbRemovePrio;
    private javax.swing.JButton jbRemoveTheme;
    private javax.swing.JButton jbServerShutdown;
    private javax.swing.JButton jbStartTicker;
    private javax.swing.JButton jbStopLiveTicker;
    private javax.swing.JButton jbTickerFontUpdate;
    private javax.swing.JButton jbUpdateCountdownFont;
    private javax.swing.JButton jbUpdateCountdownFontColor;
    private javax.swing.JButton jbUpdateCountdownFontSize;
    private javax.swing.JButton jbUpdateTickerBGAlpha;
    private javax.swing.JButton jbUpdateTickerBGColor;
    private javax.swing.JButton jbUpdateTickerFontColor;
    private javax.swing.JButton jbUpdateTickerFontSize;
    private javax.swing.JButton jbUpdateTickerSeparator;
    private javax.swing.JButton jbUpdateTickerSpeed;
    private javax.swing.JComboBox jcbCountdownFont;
    private javax.swing.JComboBox jcbCountdownFontSize;
    private javax.swing.JComboBox jcbCreatorFont;
    private javax.swing.JComboBox jcbCreatorFontSize;
    private javax.swing.JComboBox jcbTickerFont;
    private javax.swing.JComboBox jcbTickerFontSize;
    private javax.swing.JComboBox jcbTickerSpeed;
    private javax.swing.JLabel jlCountdownFont;
    private javax.swing.JLabel jlCountdownFontColor;
    private javax.swing.JLabel jlCountdownFontSize;
    private javax.swing.JLabel jlCountdownLookAFeel;
    private javax.swing.JLabel jlCreatorFontColor;
    private javax.swing.JLabel jlCreatorFontFamily;
    private javax.swing.JLabel jlCreatorMargin;
    private javax.swing.JLabel jlCreatorMarginLeft;
    private javax.swing.JLabel jlCreatorMarginTop;
    private javax.swing.JLabel jlDefaultPrioVar;
    private javax.swing.JLabel jlFontAttrCreator;
    private javax.swing.JLabel jlFontSizeCreator;
    private javax.swing.JLabel jlFullscreen;
    private javax.swing.JLabel jlGeneralServerControl;
    private javax.swing.JLabel jlPrioDefault;
    private javax.swing.JLabel jlPrioName;
    private javax.swing.JLabel jlPrioNameVar;
    private javax.swing.JLabel jlPrioTime;
    private javax.swing.JLabel jlSeperator;
    private javax.swing.JLabel jlThemeBackgroundPreview;
    private javax.swing.JLabel jlThemeName;
    private javax.swing.JLabel jlThemeNameVar;
    private javax.swing.JLabel jlTickerBGColor;
    private javax.swing.JLabel jlTickerBackgroundAlpha;
    private javax.swing.JLabel jlTickerFont;
    private javax.swing.JLabel jlTickerFontColor;
    private javax.swing.JLabel jlTickerFontSize;
    private javax.swing.JLabel jlTickerLookAFeel;
    private javax.swing.JLabel jlTickerSpeed;
    private javax.swing.JLabel jlTickerStatus;
    private javax.swing.JLabel jlTimeToShowVar;
    private javax.swing.JList jliPriorities;
    private javax.swing.JList jliThemes;
    private javax.swing.JPanel jpCountdown;
    private javax.swing.JPanel jpGeneral;
    private javax.swing.JPanel jpPriorities;
    private javax.swing.JPanel jpThemes;
    private javax.swing.JPanel jpThemeslideCreator;
    private javax.swing.JPanel jpTicker;
    private javax.swing.JSlider jslTickerBackgroundAlpha;
    private javax.swing.JScrollPane jspPrioList;
    private javax.swing.JScrollPane jspThemeList;
    private javax.swing.JTextField jtfCreatorMarginLeft;
    private javax.swing.JTextField jtfCreatorMarginTop;
    private javax.swing.JTextField jtfTickerSeparator;
    private javax.swing.JTabbedPane jtpPreferences;
    // End of variables declaration//GEN-END:variables
}
