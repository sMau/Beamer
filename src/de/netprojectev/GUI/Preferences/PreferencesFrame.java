/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.netprojectev.GUI.Preferences;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.netprojectev.GUI.Dialogs.ColorPickerDialog;
import de.netprojectev.GUI.Display.DisplayMainFrame;
import de.netprojectev.GUI.Manager.ManagerFrame;
import de.netprojectev.Media.Priority;
import de.netprojectev.Media.Theme;
import de.netprojectev.MediaHandler.DisplayDispatcher;
import de.netprojectev.MediaHandler.MediaHandler;
import de.netprojectev.Misc.Constants;
import de.netprojectev.Misc.ImageFileFilter;
import de.netprojectev.Misc.Misc;
import de.netprojectev.Preferences.PreferencesHandler;

/**
 * GUI main class of preferences.
 * handles all user changes on the preferences
 * @author samu
 */
public class PreferencesFrame extends javax.swing.JFrame {

	private static final Logger log = Logger.getLogger(PreferencesFrame.class.getName());
	
	private static final long serialVersionUID = -5332162076303310401L;
	
	private PreferencesHandler preferencesHandler;
	private Properties props;
	private Priority selectedPrio;
	private Theme selectedTheme;
	private ManagerFrame managerFrame;
	private File selectedImage;
	
	private int previewWidth;
	private int fullscreenNumber;
	
	private int tickerSpeed;
	private String tickerFontType;
	private String tickerFontSize;
	private String tickerFontColor;
	
	private String themeslideCreatorFontType;
	private String themeslideCreatorFontSize;
	private String themeslideCreatorFontColor;
	
	private int themeslideCreatorMarginLeft;
	private int themeslideCreatorMarginTop;
	
	
	private transient DisplayMainFrame display;
	

	/**
	 * 
	 * @param managerFrame the ManagerFrame it operates with.
	 */
    public PreferencesFrame(ManagerFrame managerFrame) {
    	
    	try {
			log.addHandler(Misc.getLogFileHandlerAll());
			log.addHandler(Misc.getLogFileHandlerError());
		} catch (SecurityException e) {
		} catch (IOException e) {
		}
    	
    	
    	this.managerFrame = managerFrame;
    	this.preferencesHandler = PreferencesHandler.getInstance(); 
    	preferencesHandler.setPreferencesFrame(this);
    	preferencesHandler.setManagerFrame(managerFrame);
    	this.display = 	DisplayDispatcher.getInstance().getDisplayFrame();
    	
    	props = preferencesHandler.getProperties();
    	
        initComponents();
        
        initValues();

        setLocation(Misc.currentMousePosition());
    }

	private void initValues() {
		
		/*
		 * GENERAL TAB
		 *	
		 */
		
		System.out.println("Prev scaled Prop: " + props.getProperty(Constants.PROP_PREVIEW_SCALE_WIDTH));
		jTextFieldPreviewWidth.setText(props.getProperty(Constants.PROP_PREVIEW_SCALE_WIDTH));
		System.out.println("Screnn number Prop: " + Constants.PROP_SCREEN_NUMBER_FULLSCREEN);
		jTextFieldScreenNumber.setText(props.getProperty(Constants.PROP_SCREEN_NUMBER_FULLSCREEN));
		
		/*
         * 
         * LIVE TICKER TAB
         */
        String[] font = Constants.FONT_FAMILIES;
        for(int i = 0; i < font.length; i++) {
        	jComboBoxFontTypeTicker.addItem(font[i]);
        }
        jComboBoxFontTypeTicker.setSelectedItem(props.getProperty(Constants.PROP_TICKER_FONTTYPE));
        jComboBoxFontSizeTicker.setSelectedItem(props.getProperty(Constants.PROP_TICKER_FONTSIZE));
        

        Color bg;
		try {
			bg = new Color(Integer.parseInt(props.getProperty(Constants.PROP_TICKER_FONTCOLOR)));
		} catch (NumberFormatException e1) {
			bg = new Color(Constants.DEFAULT_TICKER_FONTCOLOR);
		}
        jButtonTickerColorPicker.setBackground(bg);
        jButtonTickerColorPicker.setForeground(bg);
  
		jTextFieldTickerSpeed.setText(props.getProperty(Constants.PROP_TICKER_SPEED));
        
        /*
         * 
         * THEMESLIDECREATOR TAB
         */
        
        for(int i = 0; i < font.length; i++) {
        	jComboBoxThemeSlideFontType.addItem(font[i]);
        }
        jComboBoxThemeSlideFontType.setSelectedItem(props.getProperty(Constants.PROP_THEMESLIDECREATOR_PRESETTINGS_FONTTYPE));
        
        String[] sizes = Constants.FONT_SIZES;
        for(int i = 0; i < sizes.length; i++) {
        	jComboBoxThemeslideFontSize.addItem(sizes[i]);
        }
        jComboBoxThemeslideFontSize.setSelectedItem(props.getProperty(Constants.PROP_THEMESLIDECREATOR_PRESETTINGS_FONTSIZE));

        jTextFieldMarginLeft.setText(props.getProperty(Constants.PROP_THEMESLIDECREATOR_PRESETTINGS_MARGINLEFT));
        jTextFieldMarginTop.setText(props.getProperty(Constants.PROP_THEMESLIDECREATOR_PRESETTINGS_MARGINTOP));
	}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jSeparator10 = new javax.swing.JSeparator();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelTabMain = new javax.swing.JPanel();
        jButtonReset = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldPreviewWidth = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jButtonEnterFullscreen = new javax.swing.JButton();
        jButtonExitFullscreen = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldScreenNumber = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanelLiveTicker = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldTickerSpeed = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jComboBoxFontSizeTicker = new javax.swing.JComboBox();
        jComboBoxFontTypeTicker = new javax.swing.JComboBox();
        jButtonTickerColorPicker = new javax.swing.JButton();
        jPanelThemeslideCreator = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jComboBoxThemeslideFontSize = new javax.swing.JComboBox();
        jComboBoxThemeSlideFontType = new javax.swing.JComboBox();
        jButtonThemeslideDefaultColor = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTextFieldMarginLeft = new javax.swing.JTextField();
        jTextFieldMarginTop = new javax.swing.JTextField();
        jPanelTabTheme = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListTheme = new javax.swing.JList();
        jLabelThemeName = new javax.swing.JLabel();
        jLabelThemeBg = new javax.swing.JLabel();
        jTextFieldThemeName = new javax.swing.JTextField();
        jTextFieldThemeBgImg = new javax.swing.JTextField();
        btnSaveTheme = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        btnAddTheme = new javax.swing.JButton();
        btnRemoveTheme = new javax.swing.JButton();
        jButtonChooseBgImage = new javax.swing.JButton();
        jPanelTabPrio = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListPrio = new javax.swing.JList();
        jLabelPrioName = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldPrioName = new javax.swing.JTextField();
        jTextFieldPrioMin = new javax.swing.JTextField();
        jButtonPrioSave = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnAddPrio = new javax.swing.JButton();
        btnRemovePrio = new javax.swing.JButton();
        jButtonApplyPrefs = new javax.swing.JButton();

        jFileChooser1.setApproveButtonText("Open");
        jFileChooser1.setDialogTitle("Choose background");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Preferences");
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));

        jButtonReset.setText("Reset all data");
        jButtonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetActionPerformed(evt);
            }
        });

        jLabel1.setText("Preview width in Pixel");
        jLabel1.setToolTipText("Set the width of the images for the previewing in the main frame. The height is set automatically.");

        jTextFieldPreviewWidth.setText(preferencesHandler.getProperties().getProperty(Constants.PROP_PREVIEW_SCALE_WIDTH));
        jTextFieldPreviewWidth.setToolTipText("Set the width of the images for the previewing in the main frame. The height is set automatically.");

        jButtonEnterFullscreen.setText("Enter");
        jButtonEnterFullscreen.setToolTipText("This makes the display frame fullscreen");
        jButtonEnterFullscreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEnterFullscreenActionPerformed(evt);
            }
        });

        jButtonExitFullscreen.setText("Exit");
        jButtonExitFullscreen.setToolTipText("This lets the display frame extiting the fullscreen");
        jButtonExitFullscreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExitFullscreenActionPerformed(evt);
            }
        });

        jLabel4.setText("Fullscreen Settings");

        jLabel5.setText("Screen Number");
        jLabel5.setToolTipText("Number of the screen the display frame should enter the fullscreen on. To switch screens you first have to exit the current fullscreen.");

        jTextFieldScreenNumber.setText("0");
        jTextFieldScreenNumber.setToolTipText("Number of the screen the display frame should enter the fullscreen on. To switch screens you first have to exit the current fullscreen.");

        jLabel6.setText("Reset");

        jLabel3.setText("Previewing");

        jLabel7.setText("(Attention: Be careful with this option, as everything will be deleted)");

        jLabel23.setText("(The height is adjusted automatically)");

        javax.swing.GroupLayout jPanelTabMainLayout = new javax.swing.GroupLayout(jPanelTabMain);
        jPanelTabMain.setLayout(jPanelTabMainLayout);
        jPanelTabMainLayout.setHorizontalGroup(
            jPanelTabMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTabMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTabMainLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanelTabMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelTabMainLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldScreenNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelTabMainLayout.createSequentialGroup()
                                .addComponent(jButtonEnterFullscreen, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonExitFullscreen, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelTabMainLayout.createSequentialGroup()
                        .addGroup(jPanelTabMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelTabMainLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabMainLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator4))
                            .addGroup(jPanelTabMainLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator5))
                            .addGroup(jPanelTabMainLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanelTabMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jButtonReset)
                                    .addGroup(jPanelTabMainLayout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldPreviewWidth, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel23)))
                                .addGap(0, 97, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanelTabMainLayout.setVerticalGroup(
            jPanelTabMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTabMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelTabMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPreviewWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel23))
                .addGap(18, 23, Short.MAX_VALUE)
                .addGroup(jPanelTabMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabMainLayout.createSequentialGroup()
                        .addGroup(jPanelTabMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelTabMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextFieldScreenNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelTabMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonEnterFullscreen)
                            .addComponent(jButtonExitFullscreen))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonReset)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("General", jPanelTabMain);

        jLabel8.setText("Speed");

        jLabel9.setText("Update Interval in ms");

        jTextFieldTickerSpeed.setText("50");

        jLabel10.setText("(default: 50ms, min: 1ms, max: 1000)");

        jLabel11.setText("Font Attributes");

        jLabel12.setText("Size");

        jLabel13.setText("Type");

        jLabel14.setText("Color");

        jLabel15.setText("Background");

        jComboBoxFontSizeTicker.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "16", "20", "24", "28", "32", "36", "40", "44", "48" }));
        jComboBoxFontSizeTicker.setSelectedIndex(2);
        jComboBoxFontSizeTicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxFontSizeTickerActionPerformed(evt);
            }
        });

        jComboBoxFontTypeTicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxFontTypeTickerActionPerformed(evt);
            }
        });

        jButtonTickerColorPicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTickerColorPickerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelLiveTickerLayout = new javax.swing.GroupLayout(jPanelLiveTicker);
        jPanelLiveTicker.setLayout(jPanelLiveTickerLayout);
        jPanelLiveTickerLayout.setHorizontalGroup(
            jPanelLiveTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLiveTickerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLiveTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLiveTickerLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxFontSizeTicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxFontTypeTicker, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonTickerColorPicker, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelLiveTickerLayout.createSequentialGroup()
                        .addGroup(jPanelLiveTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelLiveTickerLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldTickerSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)
                                .addGap(0, 109, Short.MAX_VALUE))
                            .addGroup(jPanelLiveTickerLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator6))
                            .addGroup(jPanelLiveTickerLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator7))
                            .addGroup(jPanelLiveTickerLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator8)))
                        .addContainerGap())))
        );
        jPanelLiveTickerLayout.setVerticalGroup(
            jPanelLiveTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLiveTickerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLiveTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelLiveTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextFieldTickerSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanelLiveTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelLiveTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jComboBoxFontSizeTicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxFontTypeTicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jButtonTickerColorPicker, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelLiveTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(146, Short.MAX_VALUE))
        );

        jTextFieldTickerSpeed.getDocument().addDocumentListener(new DocumentListener() {@Override
            public void insertUpdate(DocumentEvent e) {
                tickerSpeedChanged();

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                tickerSpeedChanged();

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                tickerSpeedChanged();

            }});

            jTabbedPane1.addTab("Live Ticker", jPanelLiveTicker);

            jLabel16.setText("Default Font Attributes");

            jLabel17.setText("Size");

            jLabel18.setText("Type");

            jLabel19.setText("Color");

            jButtonThemeslideDefaultColor.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButtonThemeslideDefaultColorActionPerformed(evt);
                }
            });

            jLabel20.setText("Margin");

            jLabel21.setText("Left");

            jLabel22.setText("Top");

            javax.swing.GroupLayout jPanelThemeslideCreatorLayout = new javax.swing.GroupLayout(jPanelThemeslideCreator);
            jPanelThemeslideCreator.setLayout(jPanelThemeslideCreatorLayout);
            jPanelThemeslideCreatorLayout.setHorizontalGroup(
                jPanelThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelThemeslideCreatorLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanelThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelThemeslideCreatorLayout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(jLabel21)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldMarginLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(31, 31, 31)
                            .addComponent(jLabel22)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextFieldMarginTop, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanelThemeslideCreatorLayout.createSequentialGroup()
                            .addGroup(jPanelThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanelThemeslideCreatorLayout.createSequentialGroup()
                                    .addComponent(jLabel16)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jSeparator9))
                                .addGroup(jPanelThemeslideCreatorLayout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel17)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jComboBoxThemeslideFontSize, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(30, 30, 30)
                                    .addComponent(jLabel18)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jComboBoxThemeSlideFontType, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(30, 30, 30)
                                    .addComponent(jLabel19)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButtonThemeslideDefaultColor, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 134, Short.MAX_VALUE))
                                .addGroup(jPanelThemeslideCreatorLayout.createSequentialGroup()
                                    .addComponent(jLabel20)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jSeparator11)))
                            .addContainerGap())))
            );
            jPanelThemeslideCreatorLayout.setVerticalGroup(
                jPanelThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelThemeslideCreatorLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanelThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanelThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(jLabel18)
                        .addComponent(jLabel19)
                        .addComponent(jComboBoxThemeslideFontSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBoxThemeSlideFontType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonThemeslideDefaultColor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanelThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel20)
                        .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanelThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel21)
                        .addComponent(jLabel22)
                        .addComponent(jTextFieldMarginLeft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldMarginTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(180, Short.MAX_VALUE))
            );

            jTabbedPane1.addTab("Themeslide Creator", jPanelThemeslideCreator);

            jListTheme.setModel(new ThemeListModel());
            jListTheme.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        listThemeSelectionChanged();
                    }
                }
            );
            jScrollPane2.setViewportView(jListTheme);

            jLabelThemeName.setText("Name");

            jLabelThemeBg.setText("Background");

            jTextFieldThemeName.setMaximumSize(new java.awt.Dimension(10, 26));

            jTextFieldThemeBgImg.setMaximumSize(new java.awt.Dimension(10, 26));

            btnSaveTheme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/GFX/save.png"))); // NOI18N
            btnSaveTheme.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnSaveThemeActionPerformed(evt);
                }
            });

            btnAddTheme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/GFX/plus_2.png"))); // NOI18N
            btnAddTheme.setToolTipText("Add new theme.");
            btnAddTheme.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnAddThemeActionPerformed(evt);
                }
            });

            btnRemoveTheme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/GFX/delete_2.png"))); // NOI18N
            btnRemoveTheme.setToolTipText("Delete selected themes.");
            btnRemoveTheme.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnRemoveThemeActionPerformed(evt);
                }
            });

            jButtonChooseBgImage.setText("Choose");
            jButtonChooseBgImage.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButtonChooseBgImageActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout jPanelTabThemeLayout = new javax.swing.GroupLayout(jPanelTabTheme);
            jPanelTabTheme.setLayout(jPanelTabThemeLayout);
            jPanelTabThemeLayout.setHorizontalGroup(
                jPanelTabThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelTabThemeLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(jPanelTabThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator2)
                        .addGroup(jPanelTabThemeLayout.createSequentialGroup()
                            .addGroup(jPanelTabThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanelTabThemeLayout.createSequentialGroup()
                                    .addGroup(jPanelTabThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabelThemeName)
                                        .addComponent(jLabelThemeBg))
                                    .addGap(23, 23, 23)
                                    .addGroup(jPanelTabThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextFieldThemeBgImg, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                                        .addComponent(jTextFieldThemeName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButtonChooseBgImage))
                                .addGroup(jPanelTabThemeLayout.createSequentialGroup()
                                    .addComponent(btnAddTheme)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnSaveTheme)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnRemoveTheme)))
                            .addContainerGap(164, Short.MAX_VALUE))))
            );
            jPanelTabThemeLayout.setVerticalGroup(
                jPanelTabThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelTabThemeLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanelTabThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelTabThemeLayout.createSequentialGroup()
                            .addGroup(jPanelTabThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelThemeName)
                                .addComponent(jTextFieldThemeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanelTabThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelThemeBg)
                                .addComponent(jTextFieldThemeBgImg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButtonChooseBgImage))
                            .addGap(18, 18, 18)
                            .addGroup(jPanelTabThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnSaveTheme)
                                .addComponent(btnAddTheme)
                                .addComponent(btnRemoveTheme))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 152, Short.MAX_VALUE))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE))
                    .addContainerGap())
            );

            jTabbedPane1.addTab("Theme", jPanelTabTheme);

            jListPrio.setModel(new PriorityListModel());
            jListPrio.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        listPrioritySelectionChanged();
                    }
                }
            );
            jScrollPane1.setViewportView(jListPrio);

            jLabelPrioName.setText("Name");

            jLabel2.setText("Minutes");

            jButtonPrioSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/GFX/save.png"))); // NOI18N
            jButtonPrioSave.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButtonPrioSaveActionPerformed(evt);
                }
            });

            btnAddPrio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/GFX/plus_2.png"))); // NOI18N
            btnAddPrio.setToolTipText("Add new Priority.");
            btnAddPrio.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnAddPrioActionPerformed(evt);
                }
            });

            btnRemovePrio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/GFX/delete_2.png"))); // NOI18N
            btnRemovePrio.setToolTipText("Delete selected priorities.");
            btnRemovePrio.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnRemovePrioActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout jPanelTabPrioLayout = new javax.swing.GroupLayout(jPanelTabPrio);
            jPanelTabPrio.setLayout(jPanelTabPrioLayout);
            jPanelTabPrioLayout.setHorizontalGroup(
                jPanelTabPrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelTabPrioLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(jPanelTabPrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                        .addGroup(jPanelTabPrioLayout.createSequentialGroup()
                            .addGroup(jPanelTabPrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanelTabPrioLayout.createSequentialGroup()
                                    .addComponent(btnAddPrio)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButtonPrioSave)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnRemovePrio, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanelTabPrioLayout.createSequentialGroup()
                                    .addGroup(jPanelTabPrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabelPrioName, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addGap(42, 42, 42)
                                    .addGroup(jPanelTabPrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextFieldPrioName, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldPrioMin, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGap(0, 227, Short.MAX_VALUE)))
                    .addContainerGap())
            );
            jPanelTabPrioLayout.setVerticalGroup(
                jPanelTabPrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelTabPrioLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanelTabPrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelTabPrioLayout.createSequentialGroup()
                            .addGroup(jPanelTabPrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelPrioName)
                                .addComponent(jTextFieldPrioName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanelTabPrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jTextFieldPrioMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanelTabPrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnAddPrio)
                                .addComponent(btnRemovePrio)
                                .addComponent(jButtonPrioSave))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 154, Short.MAX_VALUE))
                        .addComponent(jScrollPane1))
                    .addContainerGap())
            );

            jTabbedPane1.addTab("Priority", jPanelTabPrio);

            jButtonApplyPrefs.setText("Close");
            jButtonApplyPrefs.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButtonApplyPrefsActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane1)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonApplyPrefs, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButtonApplyPrefs)
                    .addGap(0, 12, Short.MAX_VALUE))
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    
    /**
     * preparing the fields to add a new priority and clearing list selection
     * @param evt
     */
    private void btnAddPrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPrioActionPerformed
    	
    	selectedPrio = null;
    	jTextFieldPrioName.setText("New Priority");
    	jTextFieldPrioMin.setText("5");	
    	jListPrio.clearSelection();
    	
    }//GEN-LAST:event_btnAddPrioActionPerformed

    /**
     * preparing the fields to add a new priority and clearing list selection
     * @param evt
     */
    private void btnAddThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddThemeActionPerformed
    	selectedTheme = null;
    	jTextFieldThemeName.setText("New Theme");
    	jTextFieldThemeBgImg.setText("-");
    	jListTheme.clearSelection();
    }//GEN-LAST:event_btnAddThemeActionPerformed

    /**
     * handling the editing/saving of the current selected or a new priority
     * @param evt
     */
    private void jButtonPrioSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrioSaveActionPerformed
        
    	Boolean alreadyExists = false;
    	
    	if(selectedPrio == null) { //Creation of new Priority
    		if(!jTextFieldPrioName.getText().isEmpty() && !jTextFieldPrioMin.getText().isEmpty()) {
        		
        		for(int i = 0; i < preferencesHandler.getListOfPriorities().size(); i++) {
        			
        			if(preferencesHandler.getListOfPriorities().get(i).getName().equals(jTextFieldPrioName.getText())) {
        				alreadyExists = true;
        			}

        		}
        		
        		if(!alreadyExists) {
        			Priority newPriority = new Priority(jTextFieldPrioName.getText(), Integer.parseInt(jTextFieldPrioMin.getText()));
                	preferencesHandler.addPriority(newPriority);
                	jListPrio.setSelectedIndex(preferencesHandler.getListOfPriorities().size() - 1);
        		} else {
        			JOptionPane.showMessageDialog(this, "A priority with this name already exists.", "Error", JOptionPane.ERROR_MESSAGE);
        		}
        		
        	}
    	} else { //Editing the selected Priority
    		if(!selectedPrio.getName().equals(preferencesHandler.getListOfPriorities().get(0).getName())) {
    			selectedPrio.setName(jTextFieldPrioName.getText());
    			try {
        			if(!jTextFieldPrioMin.getText().isEmpty()) {
        				selectedPrio.setMinutesToShow(Integer.parseInt(jTextFieldPrioMin.getText()));
        			}
    			} catch (NumberFormatException e) {
    			}
    		}
    		preferencesHandler.refreshPrioListModel();
    	}
    	
    	managerFrame.refreshComboBoxCellEditor();
    	
    }//GEN-LAST:event_jButtonPrioSaveActionPerformed

    /**
     * removes the selected priorities from the list and the preferences handler
     * @param evt
     */
    private void btnRemovePrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemovePrioActionPerformed
        
    	int[] selectedIndices = jListPrio.getSelectedIndices();
    	if(selectedIndices.length > 0) {
    		preferencesHandler.removePriorities(Misc.indexListToPriorities(selectedIndices));
    	}
    	
    }//GEN-LAST:event_btnRemovePrioActionPerformed

    /**
     * 
     * @param evt
     */
    private void jButtonApplyPrefsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonApplyPrefsActionPerformed

    	try {
			previewWidth = Integer.parseInt(jTextFieldPreviewWidth.getText());
		} catch (NumberFormatException e) {
		}
    	
    	if(!(tickerSpeed > 0 && tickerSpeed < 1001)) {
    		tickerSpeed = display.getLiveTickerTimer().getDelay();
    	}

    	tickerFontColor = "" + display.getTickerComponent().getForeground().getRGB();
    	tickerFontSize = (String) jComboBoxFontSizeTicker.getSelectedItem();
    	tickerFontType = (String) jComboBoxFontTypeTicker.getSelectedItem();
    	
    	themeslideCreatorFontColor = "" + jButtonThemeslideDefaultColor.getBackground().getRGB();
    	themeslideCreatorFontSize = (String) jComboBoxThemeslideFontSize.getSelectedItem();
    	themeslideCreatorFontType = (String) jComboBoxThemeSlideFontType.getSelectedItem();
    	
    	try {
			fullscreenNumber = Integer.parseInt(jTextFieldScreenNumber.getText());
		} catch (NumberFormatException e) {}
    	
    	try {
			themeslideCreatorMarginLeft = Integer.parseInt(jTextFieldMarginLeft.getText());
			themeslideCreatorMarginTop = Integer.parseInt(jTextFieldMarginTop.getText());
		} catch (NumberFormatException e) {}

    	try {
			preferencesHandler.updatePropertiesFromPreferencesFrame();
		} catch (IOException e) {
			log.log(Level.SEVERE, "Error during updating properties", e);
		}
    	
    	if(previewWidth != Integer.parseInt(preferencesHandler.getProperties().getProperty(Constants.PROP_PREVIEW_SCALE_WIDTH))) {
    		MediaHandler.getInstance().generateNewScaledPreviews();
    	}
    	
    	dispose();    	

    }//GEN-LAST:event_jButtonApplyPrefsActionPerformed

    /**
     * opens a file dialog to let the user choose a file from disk as background image for the selected/new theme
     * @param evt
     */
    private void jButtonChooseBgImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChooseBgImageActionPerformed

    	
    	jFileChooser1.setFileFilter(new ImageFileFilter());
    	int returnVal = jFileChooser1.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            selectedImage = jFileChooser1.getSelectedFile();
    		jTextFieldThemeBgImg.setText(selectedImage.getAbsolutePath());
        } 
    }//GEN-LAST:event_jButtonChooseBgImageActionPerformed

    /**
     * handling the editing/saving of the current selected or a new theme
     * @param evt
     */
    private void btnSaveThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveThemeActionPerformed
    	    	
    	Boolean alreadyExists = false;
    	selectedImage = new File(jTextFieldThemeBgImg.getText());
    	
    	
    	if(selectedTheme == null) { //Creation of new Priority
    		if(!jTextFieldThemeName.getText().isEmpty() && selectedImage.exists()) {
        		
    			
	        	for(int i = 0; i < preferencesHandler.getListOfThemes().size(); i++) {
	        		
	        		if(preferencesHandler.getListOfThemes().get(i).getName().equals(jTextFieldThemeName.getText())) {
	        			System.out.println("if reached");
	        			alreadyExists = true;
	        		}
	
    			}
        		
        		if(!alreadyExists) {
        			Theme newTheme = new Theme(jTextFieldThemeName.getText(), selectedImage);
                	preferencesHandler.addTheme(newTheme);
                	jListTheme.setSelectedIndex(preferencesHandler.getListOfThemes().size() - 1);
        		} else {
        			JOptionPane.showMessageDialog(this, "A theme with this name already exists.", "Error", JOptionPane.ERROR_MESSAGE);        		}
        		
        	}
    	} else { //Editing the selected Theme
    		
    		selectedTheme.setName(jTextFieldThemeName.getText());
    		if(selectedImage.exists()) {
    			selectedTheme.setBackgroundImage(selectedImage);	
    		} else {
    			JOptionPane.showMessageDialog(this, "File cannot be found on the hard disk.", "Error", JOptionPane.ERROR_MESSAGE);
    		}
    		preferencesHandler.refreshThemeListModel();
    	}
    	
    }//GEN-LAST:event_btnSaveThemeActionPerformed

    /**
     * removes selected themes from list and preferences handler
     * @param evt
     */
    private void btnRemoveThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveThemeActionPerformed
    	
    	int[] selectedIndices = jListTheme.getSelectedIndices(); 	
    	if(selectedIndices.length > 0) {
    		preferencesHandler.removeThemes(Misc.indexListToThemes(selectedIndices));
    	}
    }//GEN-LAST:event_btnRemoveThemeActionPerformed

    private void jButtonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetActionPerformed
    	 int exit = JOptionPane.showConfirmDialog(this, "Are you sure you want to reset all data?", "Reset", JOptionPane.YES_NO_OPTION);
         if (exit == JOptionPane.YES_OPTION) {	
         	
     		File[] allFiles = new File(Constants.SAVE_PATH).listFiles();
     		if(allFiles != null) {
     			for(int i = 0; i < allFiles.length; i++) {
     				if(!allFiles[i].isDirectory()) {
     					allFiles[i].delete();
     				}
         		}
     			 int exit2 = JOptionPane.showConfirmDialog(this, "All data have been reset sucessfully.\nThe program have to be restarted. Do you want to restart now?", "Data reset", JOptionPane.YES_NO_OPTION);
     			 if(exit2 == JOptionPane.YES_OPTION) {
     				 try {
						Misc.restartApplication();
					} catch (URISyntaxException e) {
						
						e.printStackTrace();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
     			 }
     		} else {
     			JOptionPane.showMessageDialog(this,
     				    "Error while deleting files.",
     				    "Error",
     				    JOptionPane.ERROR_MESSAGE);
     		}
     		
         }

    }//GEN-LAST:event_jButtonResetActionPerformed

    private void jButtonEnterFullscreenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEnterFullscreenActionPerformed
        enterFullscreen();
    }//GEN-LAST:event_jButtonEnterFullscreenActionPerformed

	private void jButtonExitFullscreenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExitFullscreenActionPerformed
        exitFullscreen();
    }//GEN-LAST:event_jButtonExitFullscreenActionPerformed

    private void jComboBoxFontSizeTickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFontSizeTickerActionPerformed
        tickerFontSizeChanged();
    }//GEN-LAST:event_jComboBoxFontSizeTickerActionPerformed

	private void jComboBoxFontTypeTickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFontTypeTickerActionPerformed
    	tickerFontTypeChanged();
    }//GEN-LAST:event_jComboBoxFontTypeTickerActionPerformed

	private void jButtonTickerColorPickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTickerColorPickerActionPerformed
        openColorPickerTicker();
    }//GEN-LAST:event_jButtonTickerColorPickerActionPerformed

    private void jButtonThemeslideDefaultColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonThemeslideDefaultColorActionPerformed
        openColorPickerThemeslideCreator();
    }//GEN-LAST:event_jButtonThemeslideDefaultColorActionPerformed

    
    /**
     * Opens a new {@link ColorPickerDialog} and assigns the correct ticker text color.
     */
    private void openColorPickerThemeslideCreator() {

		ColorPickerDialog colorPicker = new ColorPickerDialog(this, true);
		colorPicker.setVisible(true);
		
		if(colorPicker != null) {
			if(colorPicker.getChoosenColor() != null) {
				Color bg =  new Color(colorPicker.getChoosenColor().getRed(), colorPicker.getChoosenColor().getGreen(), colorPicker.getChoosenColor().getBlue());
		        
		        jButtonThemeslideDefaultColor.setBackground(bg);
		        jButtonThemeslideDefaultColor.setForeground(bg);
				
			}
			
		}

	}

	private void tickerSpeedChanged() {
    	tickerSpeed = -1;
    	try {
			tickerSpeed = Integer.parseInt(jTextFieldTickerSpeed.getText());
		} catch (NumberFormatException e) {	}
    	if(tickerSpeed > 0 && tickerSpeed < 1001) {
    		display.getLiveTickerTimer().setDelay(tickerSpeed);
    	}
	}

	private void tickerFontTypeChanged() {
		Font fontToSet = new Font((String) jComboBoxFontTypeTicker.getSelectedItem(), display.getTickerComponent().getFont().getStyle(), display.getTickerComponent().getFont().getSize());
    	display.getTickerComponent().setFont(fontToSet);
	}

    private void tickerFontSizeChanged() {
    	Font fontToSet;
		try {
			fontToSet = new Font(display.getTickerComponent().getFont().getName(), display.getTickerComponent().getFont().getStyle(), Integer.parseInt((String) jComboBoxFontSizeTicker.getSelectedItem()));
			display.getTickerComponent().setFont(fontToSet);
		} catch (NumberFormatException e) {
			log.log(Level.WARNING, "Error during converting string to integer", e);
		}

	}
	
	
    /**
     * Opens a new {@link ColorPickerDialog} and assigns the correct ticker text color.
     */
	private void openColorPickerTicker() {
		
		ColorPickerDialog colorPicker = new ColorPickerDialog(this, true);
		colorPicker.setVisible(true);
		
		if(colorPicker != null) {
			if(colorPicker.getChoosenColor() != null) {
				Color bg =  new Color(colorPicker.getChoosenColor().getRed(), colorPicker.getChoosenColor().getGreen(), colorPicker.getChoosenColor().getBlue());
		        
		        jButtonTickerColorPicker.setBackground(bg);
		        jButtonTickerColorPicker.setForeground(bg);
		        
		        display.getTickerComponent().setForeground(bg);
				
			}
			
		}
		
	}

	/**
     * on selection change, updates the name and background image fields
     */
	private void listThemeSelectionChanged() {
		int viewRow = jListTheme.getSelectedIndex();
		if(viewRow >= 0) {
			selectedTheme = preferencesHandler.getListOfThemes().get(viewRow);
			jTextFieldThemeName.setText(selectedTheme.getName());
			jTextFieldThemeBgImg.setText(selectedTheme.getBackgroundImage().getAbsolutePath());
		}
	}

	/**
	 * on selection change, updates the name and minutes fields
	 */
	private void listPrioritySelectionChanged() {
		int viewRow = jListPrio.getSelectedIndex();
		if(viewRow >= 0) {
		    selectedPrio = preferencesHandler.getListOfPriorities().get(viewRow);
		    jTextFieldPrioName.setText(selectedPrio.getName());
		    jTextFieldPrioMin.setText(Integer.toString(selectedPrio.getMinutesToShow()));
		}
	}
	
	/**
	 * entering the fullscreen
	 */
    private void exitFullscreen() {
		display.exitFullscreen();
	}
    
    /**
     * exiting the fullscreen
     */
    private void enterFullscreen() {
    	int screenNumber = 0;
    	try {
			screenNumber = Integer.parseInt(jTextFieldScreenNumber.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
    	display.enterFullscreen(screenNumber);
		
	}


    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
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
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new PreferencesFrame(null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddPrio;
    private javax.swing.JButton btnAddTheme;
    private javax.swing.JButton btnRemovePrio;
    private javax.swing.JButton btnRemoveTheme;
    private javax.swing.JButton btnSaveTheme;
    private javax.swing.JButton jButtonApplyPrefs;
    private javax.swing.JButton jButtonChooseBgImage;
    private javax.swing.JButton jButtonEnterFullscreen;
    private javax.swing.JButton jButtonExitFullscreen;
    private javax.swing.JButton jButtonPrioSave;
    private javax.swing.JButton jButtonReset;
    private javax.swing.JButton jButtonThemeslideDefaultColor;
    private javax.swing.JButton jButtonTickerColorPicker;
    private javax.swing.JComboBox jComboBoxFontSizeTicker;
    private javax.swing.JComboBox jComboBoxFontTypeTicker;
    private javax.swing.JComboBox jComboBoxThemeSlideFontType;
    private javax.swing.JComboBox jComboBoxThemeslideFontSize;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelPrioName;
    private javax.swing.JLabel jLabelThemeBg;
    private javax.swing.JLabel jLabelThemeName;
    private javax.swing.JList jListPrio;
    private javax.swing.JList jListTheme;
    private javax.swing.JPanel jPanelLiveTicker;
    private javax.swing.JPanel jPanelTabMain;
    private javax.swing.JPanel jPanelTabPrio;
    private javax.swing.JPanel jPanelTabTheme;
    private javax.swing.JPanel jPanelThemeslideCreator;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextFieldMarginLeft;
    private javax.swing.JTextField jTextFieldMarginTop;
    private javax.swing.JTextField jTextFieldPreviewWidth;
    private javax.swing.JTextField jTextFieldPrioMin;
    private javax.swing.JTextField jTextFieldPrioName;
    private javax.swing.JTextField jTextFieldScreenNumber;
    private javax.swing.JTextField jTextFieldThemeBgImg;
    private javax.swing.JTextField jTextFieldThemeName;
    private javax.swing.JTextField jTextFieldTickerSpeed;
    // End of variables declaration//GEN-END:variables

	public javax.swing.JList getjListPrio() {
		return jListPrio;
	}

	public void setjListPrio(javax.swing.JList jListPrio) {
		this.jListPrio = jListPrio;
	}

	public ManagerFrame getManagerFrame() {
		return managerFrame;
	}

	public void setManagerFrame(ManagerFrame managerFrame) {
		this.managerFrame = managerFrame;
	}
	
    public File getSelectedImage() {
		return selectedImage;
	}

	public void setSelectedImage(File selectedImage) {
		this.selectedImage = selectedImage;
	}

	public javax.swing.JList getjList2() {
		return jListTheme;
	}

	public void setjList2(javax.swing.JList jList2) {
		this.jListTheme = jList2;
	}

	public int getTickerSpeed() {
		return tickerSpeed;
	}

	public String getTickerFontType() {
		return tickerFontType;
	}

	public String getTickerFontSize() {
		return tickerFontSize;
	}

	public String getTickerFontColor() {
		return tickerFontColor;
	}

	public String getThemeslideCreatorFontType() {
		return themeslideCreatorFontType;
	}

	public String getThemeslideCreatorFontSize() {
		return themeslideCreatorFontSize;
	}

	public String getThemeslideCreatorFontColor() {
		return themeslideCreatorFontColor;
	}

	public int getPreviewWidth() {
		return previewWidth;
	}

	public int getFullscreenNumber() {
		return fullscreenNumber;
	}

	public int getThemeslideCreatorMarginLeft() {
		return themeslideCreatorMarginLeft;
	}

	public int getThemeslideCreatorMarginTop() {
		return themeslideCreatorMarginTop;
	}

}
