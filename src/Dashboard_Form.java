
import Sensors.mainSensors;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import Sensors.serverConnection;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import javax.swing.JOptionPane;
import org.apache.commons.net.ftp.FTPClient;
import static java.lang.System.console;
import Sensors.serverConnection;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.net.ftp.FTPClient;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import nwstcrpi.clsPreferences;
import nwstcrpi.Factory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fantoli
 */
public class Dashboard_Form extends javax.swing.JFrame {

    /**
     * Creates new form Dashboard_Form
     */
    
        Factory Factory = new Factory();
       
        clsPreferences _preferences = Factory.GetPreferences();
    
     //default border for menu items
         Border default_border = BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(46, 49, 49));
        
        //yellow border for menu items
         Border yellow_border = BorderFactory.createMatteBorder(2, 0, 2, 0, Color.yellow);
         
        // create an array of jlabels
        JLabel[] menuLabels = new JLabel[4];
    
        // create an array of jpanels
        JPanel[] panels = new JPanel[4];
         
    public Dashboard_Form() {
        initComponents();
        
        //center this form
        this.setLocationRelativeTo(null);

        //set borders
        //panel logo border
        Border panelBorder = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.lightGray);
        jPanel_logoANDname.setBorder(panelBorder);
        
        //populate the MenuLabels array
        menuLabels[0] = jLabel_menuPreferences;
        menuLabels[1] = jLabel_menuSensors;
        menuLabels[2] = jLabel_menuSensorFTP;
        menuLabels[3] = jLabel_menuReadings;
        
        //populate the panels array
        panels[0] = jPanel_preferences;
        panels[1] = jPanel_sensors;
        panels[2] = jPanel_server;
        panels[3] = jPanel_read;
        
        //dashboard remains selected when opening the app
        showPanel(jPanel_preferences);
        jLabel_menuPreferences.setBackground(Color.white); 
        jLabel_menuPreferences.setForeground(Color.black);
        
        addActionToMenuLabels();
        
        
        if (_preferences.getDHT22_Enabled()){
           _preferences.setDHT22_Enabled(false);
           System.out.println(_preferences.getDHT22_Enabled());
        }
        else{
           _preferences.setDHT22_Enabled(true);
           System.out.println(_preferences.getDHT22_Enabled());
        }
        
        
        try {
            this.txtPresent.setText(String.valueOf(_preferences.getDHT22_Present()));
            this.txtTemperature.setText(String.valueOf(_preferences.getDHT22_SensorNumber_Temperature()));
            this.txtHumidity.setText(String.valueOf(_preferences.getDHT22_SensorNumber_Humidity()));
            this.txtSensor.setText(String.valueOf(_preferences.getDHT22_SensorPin()));
            this.txtDuration.setText(String.valueOf(_preferences.getReadTimePeriod()));
            this.txtMQ2present.setText(String.valueOf(_preferences.getMQ2_Present()));
            this.txtMQ2LPG.setText(String.valueOf(_preferences.getMQ2_SensorNumber_LPG()));
            this.txtMQ2CO.setText(String.valueOf(_preferences.getMQ2_SensorNumber_CO()));
            this.txtMQ2SMOKE.setText(String.valueOf(_preferences.getMQ2_SensorNumber_SMOKE()));
            this.txtSensorport.setText(String.valueOf(_preferences.getMQ2_SensorPort()));
            this.txtBoardResistance.setText(String.valueOf(_preferences.getMQ2_SensorResistance()));
            this.txtSensorResistance.setText(String.valueOf(_preferences.getMQ2_SensorResistance()));
            
            this.txtMQ2Enabled.setText(String.valueOf(_preferences.getMQ2_Enabled()));
            this.txtEnabled.setText(String.valueOf(_preferences.getDHT22_Enabled()));
            
   
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), this.getTitle(), JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        
        
        this.setLocationRelativeTo(null);
    }
    
    public void setLabelBackground(JLabel label) 
    {
        for (JLabel menuItem : menuLabels)
        {
            menuItem.setBackground(new Color(46,46,49));
            menuItem.setForeground(Color.white); 
        }
        
        label.setBackground(Color.white);
        label.setForeground(Color.black);
    }
    
    
    //funtion to show the selected panel
    public void showPanel(JPanel panel)
    {
        //hide panels
        for(JPanel pnl : panels){
            pnl.setVisible(false);
        }
        
        //show only this panel
        panel.setVisible(true);
    }
    
    public void addActionToMenuLabels(){
        // get labels in the jpanel menu
        Component[] components = jPanel_menu.getComponents();
        
        for (Component component : components) {
            if(component instanceof JLabel)
            {
                JLabel label = (JLabel) component;
                
                 // add action to the labels
                label.addMouseListener(new MouseListener() {
                    
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        
                        setLabelBackground(label);
                        
                        switch (label.getText().trim()){
                            case "Preferences":
                                showPanel(jPanel_preferences);
                                break;
                                
                            case "Sensors":
                                showPanel(jPanel_sensors);
                                break;
                                
                            case "Server FTP":
                                showPanel(jPanel_server);
                                break;
                                
                             case "Readings":
                                showPanel(jPanel_read);
                                break;
                        }
                      }

                    @Override
                    public void mousePressed(MouseEvent e) {
                     }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                      }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                     
                        label.setBorder(yellow_border);
                        
                      }

                    @Override
                    public void mouseExited(MouseEvent e) {
                  
                        label.setBorder(default_border);
                        
                      }

                });
                
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel19 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel_menu = new javax.swing.JPanel();
        jPanel_logoANDname = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel_menuSensorFTP = new javax.swing.JLabel();
        jLabel_menuPreferences = new javax.swing.JLabel();
        jLabel_menuReadings = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel_menuSensors = new javax.swing.JLabel();
        jPanel_preferences = new javax.swing.JPanel();
        label2 = new java.awt.Label();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtEnabled = new javax.swing.JTextField();
        txtPresent = new javax.swing.JTextField();
        txtSensor = new javax.swing.JTextField();
        chkPresent = new javax.swing.JCheckBox();
        chkEnabled = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTemperature = new javax.swing.JTextField();
        txtHumidity = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtMQ2SMOKE = new javax.swing.JTextField();
        txtMQ2CO = new javax.swing.JTextField();
        txtMQ2LPG = new javax.swing.JTextField();
        txtMQ2present = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        txtMQ2Enabled = new javax.swing.JTextField();
        txtSensorResistance = new javax.swing.JTextField();
        txtBoardResistance = new javax.swing.JTextField();
        txtSensorport = new javax.swing.JTextField();
        btnSaveReadings = new javax.swing.JButton();
        jPanel_sensors = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnClose1 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jPanel_server = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();
        label1 = new java.awt.Label();
        chkEnable = new javax.swing.JCheckBox();
        txtIP = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtPassive = new javax.swing.JTextField();
        chkAnonimous = new javax.swing.JCheckBox();
        txtUser = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        chkShowPassword = new javax.swing.JCheckBox();
        txtDuration = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        btnClear = new javax.swing.JButton();
        btnConnect = new javax.swing.JButton();
        jPanel_read = new javax.swing.JPanel();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        btnClose2 = new javax.swing.JButton();

        jLabel19.setText("jLabel19");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(700, 1068));

        jPanel_menu.setBackground(new java.awt.Color(46, 46, 49));

        jPanel_logoANDname.setBackground(new java.awt.Color(46, 46, 49));

        jLabel10.setFont(new java.awt.Font("Leelawadee UI", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel_logoANDnameLayout = new javax.swing.GroupLayout(jPanel_logoANDname);
        jPanel_logoANDname.setLayout(jPanel_logoANDnameLayout);
        jPanel_logoANDnameLayout.setHorizontalGroup(
            jPanel_logoANDnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_logoANDnameLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_logoANDnameLayout.setVerticalGroup(
            jPanel_logoANDnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_logoANDnameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel_menuSensorFTP.setBackground(new java.awt.Color(46, 46, 49));
        jLabel_menuSensorFTP.setFont(new java.awt.Font("Leelawadee UI", 1, 14)); // NOI18N
        jLabel_menuSensorFTP.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_menuSensorFTP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_menuSensorFTP.setText("Server FTP");
        jLabel_menuSensorFTP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_menuSensorFTP.setOpaque(true);

        jLabel_menuPreferences.setBackground(new java.awt.Color(46, 46, 49));
        jLabel_menuPreferences.setFont(new java.awt.Font("Leelawadee UI", 1, 14)); // NOI18N
        jLabel_menuPreferences.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_menuPreferences.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_menuPreferences.setText("Preferences");
        jLabel_menuPreferences.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_menuPreferences.setOpaque(true);

        jLabel_menuReadings.setBackground(new java.awt.Color(46, 46, 49));
        jLabel_menuReadings.setFont(new java.awt.Font("Leelawadee UI", 1, 14)); // NOI18N
        jLabel_menuReadings.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_menuReadings.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_menuReadings.setText("Readings");
        jLabel_menuReadings.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_menuReadings.setOpaque(true);

        jPanel3.setBackground(new java.awt.Color(46, 46, 49));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 242, Short.MAX_VALUE)
        );

        jLabel_menuSensors.setBackground(new java.awt.Color(46, 46, 49));
        jLabel_menuSensors.setFont(new java.awt.Font("Leelawadee UI", 1, 14)); // NOI18N
        jLabel_menuSensors.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_menuSensors.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_menuSensors.setText("Sensors");
        jLabel_menuSensors.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_menuSensors.setOpaque(true);

        javax.swing.GroupLayout jPanel_menuLayout = new javax.swing.GroupLayout(jPanel_menu);
        jPanel_menu.setLayout(jPanel_menuLayout);
        jPanel_menuLayout.setHorizontalGroup(
            jPanel_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_logoANDname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel_menuSensorFTP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel_menuPreferences, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel_menuReadings, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
            .addGroup(jPanel_menuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_menuSensors, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel_menuLayout.setVerticalGroup(
            jPanel_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_menuLayout.createSequentialGroup()
                .addComponent(jPanel_logoANDname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel_menuPreferences, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_menuSensorFTP, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel_menuReadings, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel_menuSensors, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel_preferences.setBackground(new java.awt.Color(255, 255, 255));

        label2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label2.setForeground(new java.awt.Color(0, 0, 0));
        label2.setText("Sensor propieties");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Sensor Pin:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Present:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Enabled:");

        txtSensor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSensorActionPerformed(evt);
            }
        });

        chkPresent.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chkPresent.setText("present");
        chkPresent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPresentActionPerformed(evt);
            }
        });

        chkEnabled.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chkEnabled.setText("enabled");
        chkEnabled.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEnabledActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Humidity:");
        jLabel8.setToolTipText("");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Temperature:");

        txtTemperature.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTemperatureActionPerformed(evt);
            }
        });

        txtHumidity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHumidityActionPerformed(evt);
            }
        });

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("MQ2 propieties");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("MQ2 Present:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("MQ2 Sensor Number LPG:");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setText("MQ2 Sensor Number CO:");

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setText("MQ2 Sensor Number SMOKE:");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setText("MQ2 Sensorport:");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setText("MQ2 Board Resistance:");

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setText("MQ2 Sensor Resistance:");

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel34.setText("MQ2 Enabled:");

        btnSaveReadings.setText("Save");
        btnSaveReadings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveReadingsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_preferencesLayout = new javax.swing.GroupLayout(jPanel_preferences);
        jPanel_preferences.setLayout(jPanel_preferencesLayout);
        jPanel_preferencesLayout.setHorizontalGroup(
            jPanel_preferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_preferencesLayout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addGroup(jPanel_preferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_preferencesLayout.createSequentialGroup()
                        .addGroup(jPanel_preferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11)
                            .addComponent(jLabel13))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_preferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_preferencesLayout.createSequentialGroup()
                                .addGroup(jPanel_preferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEnabled, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPresent, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel_preferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(chkPresent)
                                    .addComponent(chkEnabled)))
                            .addComponent(txtSensor, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(63, 63, 63)
                        .addGroup(jPanel_preferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_preferencesLayout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(201, 201, 201))
                            .addGroup(jPanel_preferencesLayout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(201, 201, 201))))
                    .addGroup(jPanel_preferencesLayout.createSequentialGroup()
                        .addGroup(jPanel_preferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel_preferencesLayout.createSequentialGroup()
                                .addGroup(jPanel_preferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel29)
                                    .addComponent(jLabel30))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel_preferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMQ2present, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel_preferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtMQ2LPG)
                                        .addComponent(txtMQ2CO, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtMQ2SMOKE, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(56, 56, 56)
                                .addGroup(jPanel_preferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_preferencesLayout.createSequentialGroup()
                                        .addComponent(jLabel32)
                                        .addGap(10, 10, 10))
                                    .addGroup(jPanel_preferencesLayout.createSequentialGroup()
                                        .addGroup(jPanel_preferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel33)
                                            .addComponent(jLabel31)
                                            .addComponent(jLabel34))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel_preferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtSensorport, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtBoardResistance, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_preferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtSensorResistance)
                                        .addComponent(txtMQ2Enabled, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_preferencesLayout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addComponent(btnSaveReadings))
                                    .addComponent(txtTemperature, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtHumidity, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel12))
                        .addGap(200, 200, 200))))
        );
        jPanel_preferencesLayout.setVerticalGroup(
            jPanel_preferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_preferencesLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel_preferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTemperature, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel3)
                    .addComponent(txtSensor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_preferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtHumidity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(chkPresent)
                    .addComponent(txtPresent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_preferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEnabled, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_preferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(chkEnabled)))
                .addGap(21, 21, 21)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_preferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtMQ2present, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(txtSensorport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_preferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtMQ2LPG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(txtBoardResistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_preferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMQ2CO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel33)
                    .addComponent(txtSensorResistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_preferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(txtMQ2SMOKE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34)
                    .addComponent(txtMQ2Enabled, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSaveReadings)
                .addContainerGap(122, Short.MAX_VALUE))
        );

        jPanel_sensors.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Sensors");

        btnClose1.setText("Close");
        btnClose1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClose1ActionPerformed(evt);
            }
        });

        jLabel14.setText("Nombre:");

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jLabel15.setText("Pin:");

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jLabel16.setText("INFO");

        jLabel17.setText("Número de sensores:");

        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_sensorsLayout = new javax.swing.GroupLayout(jPanel_sensors);
        jPanel_sensors.setLayout(jPanel_sensorsLayout);
        jPanel_sensorsLayout.setHorizontalGroup(
            jPanel_sensorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_sensorsLayout.createSequentialGroup()
                .addGap(355, 355, 355)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(384, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_sensorsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnClose1)
                .addContainerGap())
            .addGroup(jPanel_sensorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_sensorsLayout.createSequentialGroup()
                    .addGap(277, 277, 277)
                    .addGroup(jPanel_sensorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel_sensorsLayout.createSequentialGroup()
                            .addComponent(jLabel15)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_sensorsLayout.createSequentialGroup()
                            .addComponent(jLabel14)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_sensorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addGroup(jPanel_sensorsLayout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(356, Short.MAX_VALUE)))
        );
        jPanel_sensorsLayout.setVerticalGroup(
            jPanel_sensorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_sensorsLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 410, Short.MAX_VALUE)
                .addComponent(btnClose1)
                .addGap(20, 20, 20))
            .addGroup(jPanel_sensorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_sensorsLayout.createSequentialGroup()
                    .addGap(134, 134, 134)
                    .addComponent(jLabel16)
                    .addGap(29, 29, 29)
                    .addGroup(jPanel_sensorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel_sensorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel_sensorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(268, Short.MAX_VALUE)))
        );

        jPanel_server.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_server.setPreferredSize(new java.awt.Dimension(907, 500));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Server FTP");

        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        label1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        label1.setForeground(new java.awt.Color(0, 0, 0));
        label1.setText("Server connection");

        chkEnable.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chkEnable.setSelected(true);
        chkEnable.setText("Enable");
        chkEnable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEnableActionPerformed(evt);
            }
        });

        txtIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIPActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("Server:");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("Passive:");

        chkAnonimous.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chkAnonimous.setText("Anonimous");
        chkAnonimous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAnonimousActionPerformed(evt);
            }
        });

        txtUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setText("User:");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setText("Password:");

        chkShowPassword.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chkShowPassword.setText("Show/Hide password");
        chkShowPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkShowPasswordActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setText("Duration (s)");

        btnClear.setText("CLEAR");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnConnect.setText("CONNECT");
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_serverLayout = new javax.swing.GroupLayout(jPanel_server);
        jPanel_server.setLayout(jPanel_serverLayout);
        jPanel_serverLayout.setHorizontalGroup(
            jPanel_serverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_serverLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnClose)
                .addContainerGap())
            .addGroup(jPanel_serverLayout.createSequentialGroup()
                .addGap(349, 349, 349)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 383, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_serverLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel_serverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_serverLayout.createSequentialGroup()
                        .addGroup(jPanel_serverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_serverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_serverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtPassive, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(chkAnonimous, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtIP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel_serverLayout.createSequentialGroup()
                                .addGroup(jPanel_serverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel27))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel_serverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtUser)
                                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(chkShowPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkEnable)
                    .addGroup(jPanel_serverLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addGap(18, 18, 18)
                        .addComponent(txtDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(182, 182, 182))
        );
        jPanel_serverLayout.setVerticalGroup(
            jPanel_serverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_serverLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel_serverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel_serverLayout.createSequentialGroup()
                        .addGap(381, 381, 381)
                        .addComponent(btnClose)
                        .addGap(20, 20, 20))
                    .addGroup(jPanel_serverLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkEnable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_serverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_serverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(txtPassive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chkAnonimous)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_serverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel_serverLayout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel27)
                                .addGap(27, 27, 27))
                            .addGroup(jPanel_serverLayout.createSequentialGroup()
                                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel_serverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(chkShowPassword))
                                .addGap(28, 28, 28)))
                        .addGroup(jPanel_serverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(txtDuration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel_serverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnClear)
                            .addComponent(btnConnect))
                        .addGap(35, 35, 35))))
        );

        jPanel_read.setBackground(new java.awt.Color(255, 255, 255));

        jLabel20.setText("Temp.");

        jLabel21.setText("Humidity");

        jLabel22.setText("Pressure");

        jLabel23.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Readings");

        btnClose2.setText("Close");
        btnClose2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClose2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_readLayout = new javax.swing.GroupLayout(jPanel_read);
        jPanel_read.setLayout(jPanel_readLayout);
        jPanel_readLayout.setHorizontalGroup(
            jPanel_readLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_readLayout.createSequentialGroup()
                .addGap(343, 343, 343)
                .addGroup(jPanel_readLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_readLayout.createSequentialGroup()
                        .addGroup(jPanel_readLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel20)
                            .addComponent(jLabel22))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_readLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField7)
                            .addComponent(jTextField8)
                            .addComponent(jTextField9, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)))
                    .addGroup(jPanel_readLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(360, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_readLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnClose2)
                .addContainerGap())
        );
        jPanel_readLayout.setVerticalGroup(
            jPanel_readLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_readLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addGroup(jPanel_readLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(23, 23, 23)
                .addGroup(jPanel_readLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel_readLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 209, Short.MAX_VALUE)
                .addComponent(btnClose2)
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel_menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_preferences, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(0, 204, Short.MAX_VALUE)
                    .addComponent(jPanel_sensors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(0, 201, Short.MAX_VALUE)
                    .addComponent(jPanel_server, javax.swing.GroupLayout.PREFERRED_SIZE, 908, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(0, 203, Short.MAX_VALUE)
                    .addComponent(jPanel_read, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel_preferences, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(599, 599, 599))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel_menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jPanel_sensors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 662, Short.MAX_VALUE)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jPanel_server, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 589, Short.MAX_VALUE)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jPanel_read, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 593, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1109, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnClose2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClose2ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnClose2ActionPerformed

    private void chkEnableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEnableActionPerformed
        chkEnable.setEnabled(true);
        chkEnable.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {

                if(e.getStateChange() == ItemEvent.SELECTED) {
                    txtDuration.setEnabled(true);
                    txtIP.setEnabled(true);
                    txtPassive.setEnabled(true);
                    txtUser.setEnabled(true);
                    txtPass.setEnabled(true);
                    chkAnonimous.setEnabled(true);
                    chkShowPassword.setEnabled(true);

                }
                else if(e.getStateChange() == ItemEvent.DESELECTED){
                    txtDuration.setEnabled(false);
                    txtIP.setEnabled(false);
                    txtPassive.setEnabled(false);
                    txtUser.setEnabled(false);
                    txtPass.setEnabled(false);
                    chkAnonimous.setEnabled(false);
                    chkShowPassword.setEnabled(false);
                }

            }
        });
    }//GEN-LAST:event_chkEnableActionPerformed

    private void txtIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIPActionPerformed

    }//GEN-LAST:event_txtIPActionPerformed

    private void chkAnonimousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAnonimousActionPerformed
        chkAnonimous.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {

                if(e.getStateChange() == ItemEvent.SELECTED) {

                    txtUser.setEnabled(false);
                    txtPass.setEnabled(false);
                    chkShowPassword.setEnabled(false);

                }
                else if(e.getStateChange() == ItemEvent.DESELECTED){

                    txtUser.setEnabled(true);
                    txtPass.setEnabled(true);
                    chkShowPassword.setEnabled(true);

                }

            }
        });
    }//GEN-LAST:event_chkAnonimousActionPerformed

    private void txtUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserActionPerformed

    private void chkShowPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkShowPasswordActionPerformed
        if(chkShowPassword.isSelected()){
            txtPass.setEchoChar((char)0);
        }
        else{
            txtPass.setEchoChar('*');
        }
    }//GEN-LAST:event_chkShowPasswordActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed

        txtIP.setText("");
        txtUser.setText("");
        txtPass.setText("");
        txtPassive.setText("");
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
        // Codigo para conectarse con servidor FTP
        FTPClient client = new FTPClient();

        String ftp = txtIP.getText(); // También puede ir la IP
        String user = txtUser.getText();
        String password = new String(txtPass.getPassword()); //Password encriptada

        try {
            // Conactando al servidor
            client.connect(ftp);

            // Logueado un usuario (true = pudo conectarse, false = no pudo
                // conectarse)
            boolean login = client.login(user, password);

            // Cerrando sesión
            client.logout();

            // Desconectandose con el servidor
            client.disconnect();

            JOptionPane.showMessageDialog(null, "Conexión realizada con éxito");
            JOptionPane.showMessageDialog(btnConnect, password);
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "No pudo conectarse con el servidor con los datos: IP = " + ftp + "; User = " + user + "; Pass = " + password);
        }
    }//GEN-LAST:event_btnConnectActionPerformed

    private void txtSensorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSensorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSensorActionPerformed

    private void chkPresentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPresentActionPerformed
        if (_preferences.getDHT22_Present()){
            chkPresent.setSelected(true);
        }
        else{
            chkPresent.setSelected(false);
        }
    }//GEN-LAST:event_chkPresentActionPerformed

    private void chkEnabledActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEnabledActionPerformed
        if (_preferences.getDHT22_Enabled()){
            chkEnabled.setSelected(true);
        }
        else{
            chkEnabled.setSelected(false);
        }
    }//GEN-LAST:event_chkEnabledActionPerformed

    private void txtTemperatureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTemperatureActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTemperatureActionPerformed

    private void txtHumidityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHumidityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHumidityActionPerformed

    public void SavePreferences() throws Exception{
        
       clsPreferences _preferences; 
        
       Factory Factory;
       Factory = new Factory();
       
       _preferences = Factory.GetPreferences();
       
       _preferences.setDHT22_SensorNumber_Temperature(Integer.parseInt(txtTemperature.getText()));
       _preferences.setDHT22_SensorNumber_Humidity(Integer.parseInt(txtHumidity.getText()));
       _preferences.setDHT22_SensorPin(Integer.parseInt(txtSensor.getText()));
       _preferences.setDHT22_Present(Boolean.parseBoolean(txtPresent.getText()));
       _preferences.setDHT22_Enabled(Boolean.parseBoolean(txtEnabled.getText()));
        
          
       Boolean isSelected = chkPresent.isSelected();
       
//       if(isSelected){
//           chkPresent.setSelected(_preferences.setDHT22_Enabled());
//       } else {
//           chkPresent.setSelected(_preferences.setDHT22_Enabled(Boolean.getBoolean(isSelected)));
//       }
       
       
        try {
            _preferences.Save();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(mainSensors.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        System.out.println(_preferences.getDHT22_SensorNumber_Temperature());
        
    }
    
    
    private void btnSaveReadingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveReadingsActionPerformed
        try {
            SavePreferences();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(mainSensors.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        try {
            //Con estas instrucciones se obtiene en memoria el contenido completo del fichero XML
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            //Elemento raíz
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("root");
            doc.appendChild(rootElement);
            //Primer elemento
            Element elemento1 = doc.createElement("configuration");
            rootElement.appendChild(elemento1);
            //Hay métodos de la clase Document que permiten la creación de cada uno
            //de los posibles tipos de nodos admitidos por XML como CreateElement o CreateAttribute
            //Se agrega un atributo al nodo frequency y su valor
            Attr attr = doc.createAttribute("id");
            attr.setValue("config");
            elemento1.setAttributeNode(attr);

            //Con frequency
            Element temperature = doc.createElement("temperature");
            temperature.setTextContent(txtTemperature.getText());
            rootElement.appendChild(temperature);
            //Con duration
            Element humidity = doc.createElement("humidity");
            humidity.setTextContent(txtHumidity.getText());
            rootElement.appendChild(humidity);
            //Con sensor pin
            Element pin = doc.createElement("sensorPin");
            pin.setTextContent(txtSensor.getText());
            rootElement.appendChild(pin);
            //Con present
            Element present = doc.createElement("DHT22_present");
            present.setTextContent(txtPresent.getText());
            rootElement.appendChild(humidity);
            //Con enabled
            Element enabled = doc.createElement("enabled");
            enabled.setTextContent(txtEnabled.getText());
            rootElement.appendChild(enabled);
            //Con MQ2Present
            Element mq2present = doc.createElement("MQ2Present");
            mq2present.setTextContent(txtMQ2present.getText());
            rootElement.appendChild(mq2present);
            //Con MQ2SensorNumberLPG
            Element mq2SensorLPG = doc.createElement("MQ2SensorLPG");
            mq2SensorLPG.setTextContent(txtMQ2LPG.getText());
            rootElement.appendChild(mq2SensorLPG);
            //Con MQ2SensorNumberCO
            Element mq2SensorCO = doc.createElement("MQ2SensorCO");
            mq2SensorCO.setTextContent(txtMQ2CO.getText());
            rootElement.appendChild(mq2SensorCO);
            //Con MQ2SensorNumberSMOKE
            Element mq2SensorSMOKE = doc.createElement("MQ2SensorSMOKE");
            mq2SensorSMOKE.setTextContent(txtMQ2SMOKE.getText());
            rootElement.appendChild(mq2SensorSMOKE);
            //Con MQ2SensorPort
            Element mq2SensorPort = doc.createElement("MQ2SensorPort");
            mq2SensorPort.setTextContent(txtSensorport.getText());
            rootElement.appendChild(mq2SensorPort);
            //Con MQ2BoardResistance
            Element mq2BoardResistance = doc.createElement("MQ2BoardResistance");
            mq2BoardResistance.setTextContent(txtBoardResistance.getText());
            rootElement.appendChild(mq2BoardResistance);
            //Con MQ2SensorResistance
            Element mq2SensorResistance = doc.createElement("MQ2SensorResistance");
            mq2SensorResistance.setTextContent(txtSensorResistance.getText());
            rootElement.appendChild(mq2SensorResistance);
            //Con MQ2Enabled
            Element mq2Enabled = doc.createElement("MQ2Enabled");
            mq2Enabled.setTextContent(txtMQ2Enabled.getText());
            rootElement.appendChild(mq2Enabled);

            //Se escribe el contenido del XML en un archivo
            //Para realizar cualquiera de esas operaciones, es necesario crear previamente un
            //transformador al que se le indique el documento y el destino que se le va a dar.
            //La clase Transformer para generará un archivo de texto con el contenido del XML.
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            //Construcción de la ruta

            //            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
            //            LocalDate localDate = LocalDate.now();
            //
            //            DateTimeFormatter dtt = DateTimeFormatter.ofPattern("HH-mm-ss");
            //            LocalTime localTime = LocalTime.now();
            //
            //            String nombreArchivo = txtNombre.getText();
            String username = System.getProperty("user.name");
            String Ruta = "C:\\Users\\" + username + "\\results\\";
            String XML = "Settings_results.xml";
            String strRuta = Ruta + XML;
            StreamResult result = new StreamResult(new File(strRuta));

            transformer.transform(source, result);
            JOptionPane.showMessageDialog(null, "XML guardado en la ruta: " + strRuta, "Guardado", JOptionPane.INFORMATION_MESSAGE);
        }catch(ParserConfigurationException pce) {
            pce.printStackTrace();
        }catch(TransformerException tfe) {

            //            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
            //            LocalDate localDate = LocalDate.now();
            //
            //            DateTimeFormatter dtt = DateTimeFormatter.ofPattern("HH-mm-ss");
            //            LocalTime localTime = LocalTime.now();
            //            String nombreArchivo = txtNombre.getText();
            String XML = "Settings_results.xml";
            String username = System.getProperty("user.name");
            String Ruta = "C:\\Users\\" + username + "\\results\\";
            String strRuta = Ruta + XML;
            tfe.printStackTrace();

            int warning = JOptionPane.showConfirmDialog(null, "Error al guardar el XML, no existe la ruta. ¿Desea crear la ruta " + strRuta + " ?", null, JOptionPane.YES_NO_OPTION);

            if(warning == JOptionPane.YES_OPTION)
            {
                File directorio = new File(Ruta);
                directorio.mkdirs();
                File archivo = new File(Ruta,XML);

                try {
                    //Mismo codigo que en btnSave Action Performance
                    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

                    Document doc = docBuilder.newDocument();
                    Element rootElement = doc.createElement("root");
                    doc.appendChild(rootElement);

                    Element elemento1 = doc.createElement("configuration");
                    rootElement.appendChild(elemento1);

                    Attr attr = doc.createAttribute("id");
                    attr.setValue("config");
                    elemento1.setAttributeNode(attr);

                    //Con frequency
                    Element temperature = doc.createElement("temperature");
                    temperature.setTextContent(txtTemperature.getText());
                    rootElement.appendChild(temperature);
                    //Con duration
                    Element humidity = doc.createElement("humidity");
                    humidity.setTextContent(txtHumidity.getText());
                    rootElement.appendChild(humidity);
                    //Con sensor pin
                    Element pin = doc.createElement("sensorPin");
                    pin.setTextContent(txtSensor.getText());
                    rootElement.appendChild(pin);
                    //Con present
                    Element present = doc.createElement("DHT22_present");
                    present.setTextContent(txtPresent.getText());
                    rootElement.appendChild(humidity);
                    //Con enabled
                    Element enabled = doc.createElement("enabled");
                    enabled.setTextContent(txtEnabled.getText());
                    rootElement.appendChild(enabled);
                    //Con MQ2Present
                    Element mq2present = doc.createElement("MQ2Present");
                    mq2present.setTextContent(txtMQ2present.getText());
                    rootElement.appendChild(mq2present);
                    //Con MQ2SensorNumberLPG
                    Element mq2SensorLPG = doc.createElement("MQ2SensorLPG");
                    mq2SensorLPG.setTextContent(txtMQ2LPG.getText());
                    rootElement.appendChild(mq2SensorLPG);
                    //Con MQ2SensorNumberCO
                    Element mq2SensorCO = doc.createElement("MQ2SensorCO");
                    mq2SensorCO.setTextContent(txtMQ2CO.getText());
                    rootElement.appendChild(mq2SensorCO);
                    //Con MQ2SensorNumberSMOKE
                    Element mq2SensorSMOKE = doc.createElement("MQ2SensorSMOKE");
                    mq2SensorSMOKE.setTextContent(txtMQ2SMOKE.getText());
                    rootElement.appendChild(mq2SensorSMOKE);
                    //Con MQ2SensorPort
                    Element mq2SensorPort = doc.createElement("MQ2SensorPort");
                    mq2SensorPort.setTextContent(txtSensorport.getText());
                    rootElement.appendChild(mq2SensorPort);
                    //Con MQ2BoardResistance
                    Element mq2BoardResistance = doc.createElement("MQ2BoardResistance");
                    mq2BoardResistance.setTextContent(txtBoardResistance.getText());
                    rootElement.appendChild(mq2BoardResistance);
                    //Con MQ2SensorResistance
                    Element mq2SensorResistance = doc.createElement("MQ2SensorResistance");
                    mq2SensorResistance.setTextContent(txtSensorResistance.getText());
                    rootElement.appendChild(mq2SensorResistance);
                    //Con MQ2Enabled
                    Element mq2Enabled = doc.createElement("MQ2Enabled");
                    mq2Enabled.setTextContent(txtMQ2Enabled.getText());
                    rootElement.appendChild(mq2Enabled);

                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(doc);
                    StreamResult result = new StreamResult(new File(strRuta));

                    transformer.transform(source, result);
                    JOptionPane.showMessageDialog(null, "XML guardado en la ruta: " + strRuta, "Guardado", JOptionPane.INFORMATION_MESSAGE);

                } catch (ParserConfigurationException ex) {
                    java.util.logging.Logger.getLogger(serverConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (TransformerConfigurationException ex) {
                    java.util.logging.Logger.getLogger(serverConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (TransformerException ex) {
                    java.util.logging.Logger.getLogger(serverConnection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_btnSaveReadingsActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void btnClose1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClose1ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnClose1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dashboard_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard_Form().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnClose1;
    private javax.swing.JButton btnClose2;
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnSaveReadings;
    private javax.swing.JCheckBox chkAnonimous;
    private javax.swing.JCheckBox chkEnable;
    private javax.swing.JCheckBox chkEnabled;
    private javax.swing.JCheckBox chkPresent;
    private javax.swing.JCheckBox chkShowPassword;
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
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_menuPreferences;
    private javax.swing.JLabel jLabel_menuReadings;
    private javax.swing.JLabel jLabel_menuSensorFTP;
    private javax.swing.JLabel jLabel_menuSensors;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel_logoANDname;
    private javax.swing.JPanel jPanel_menu;
    private javax.swing.JPanel jPanel_preferences;
    private javax.swing.JPanel jPanel_read;
    private javax.swing.JPanel jPanel_sensors;
    private javax.swing.JPanel jPanel_server;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private javax.swing.JTextField txtBoardResistance;
    private javax.swing.JTextField txtDuration;
    private javax.swing.JTextField txtEnabled;
    private javax.swing.JTextField txtHumidity;
    private javax.swing.JTextField txtIP;
    private javax.swing.JTextField txtMQ2CO;
    private javax.swing.JTextField txtMQ2Enabled;
    private javax.swing.JTextField txtMQ2LPG;
    private javax.swing.JTextField txtMQ2SMOKE;
    private javax.swing.JTextField txtMQ2present;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtPassive;
    private javax.swing.JTextField txtPresent;
    private javax.swing.JTextField txtSensor;
    private javax.swing.JTextField txtSensorResistance;
    private javax.swing.JTextField txtSensorport;
    private javax.swing.JTextField txtTemperature;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
