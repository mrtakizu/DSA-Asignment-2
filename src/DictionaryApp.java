
import com.sun.xml.internal.ws.util.StringUtils;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author tnnh
 */
public class DictionaryApp extends javax.swing.JFrame {

    private final String engFile = "English.txt";
    private final String vietFile = "Vietnamese.txt";
    private final String searchMessage = "Enter word here to search!";
    WordList vietList, engList;
    DefaultListModel vietListModel;
    DefaultListModel engListModel;
    Word engWord, vietWord;
    boolean isChanged = false;
    JFrame addNewPanel;
    boolean isSearching = false;

    /**
     * Creates new form DictionaryApp
     */
    public DictionaryApp() {
    }

    public DictionaryApp(String title) {
        initComponents();
        this.setTitle(title);
        engListModel = new DefaultListModel();
        vietListModel = new DefaultListModel();
        engList = new WordList();
        vietList = new WordList();
        tabPanel.setTitleAt(0, "English - Tiếng Việt");
        tabPanel.setTitleAt(1, "Tiếng Việt - English");
        LoadEngData();
        LoadVietData();
        this.txtSearch.setText(searchMessage);

    }

    private Word LookUp(Word root, String name) {
        if (root != null) {
            if (root.getName().compareTo(name) > 0) {
                return LookUp(root.getLeft(), name);
            }
            if (root.getName().equals(name)) {
                return root;
            }
            if (root.getName().compareTo(name) < 0) {
                return LookUp(root.getRight(), name);
            }
        }
        return null;
    }

    private void Update(Word word, String lang) {
        if (lang.equalsIgnoreCase("English")) {
            if (word != null) {
                Update(word.getLeft(), lang);
                engListModel.addElement(word.getName().trim());
                Update(word.getRight(), lang);
            }
        } else {
            if (word != null) {
                Update(word.getLeft(), lang);
                vietListModel.addElement(word.getName().trim());
                Update(word.getRight(), lang);
            }
        }

    }

    private void LoadEngData() {
        try {
            FileReader fr = new FileReader(engFile);
            BufferedReader br = new BufferedReader(fr);
            String s = "";
            StringTokenizer stk;
            while ((s = br.readLine()) != null) {
                List<String> mean = new LinkedList();
                s = s.trim();
                stk = new StringTokenizer(s, ":");
                String word = stk.nextToken().trim();
                String meaning = stk.nextToken().trim();
                stk = new StringTokenizer(meaning, ",");
                while (stk.hasMoreTokens()) {
                    mean.add(stk.nextToken().trim());
                }
                engList.Add(word, mean);
            }
            fr.close();
            br.close();
            engWord = engList.getRoot();
            Update(engWord, "English");
            this.engWordList.setModel(engListModel);

        } catch (Exception e) {
//            JOptionPane.showMessageDialog(rootPane, e);
        }
    }

    private void LoadVietData() {
        try {
            FileReader fr = new FileReader(vietFile);
            BufferedReader br = new BufferedReader(fr);
            String s = "";
            StringTokenizer stk;

            while ((s = br.readLine()) != null) {
                List<String> mean = new LinkedList();
                s = s.trim();
                stk = new StringTokenizer(s, ":");
                String word = stk.nextToken().trim();
                String meaning = stk.nextToken().trim();
                stk = new StringTokenizer(meaning, ",");
                while (stk.hasMoreTokens()) {
                    mean.add(stk.nextToken().trim());
                }
                vietList.Add(word, mean);
            }
            fr.close();
            br.close();
            vietWord = vietList.getRoot();
            Update(vietWord, "Vietnamese");
            this.vietWordList.setModel(vietListModel);
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(rootPane, e);
        }
    }

    private String SaveFile(Word word, String lang, FileWriter fw) {
        String means = "";
        String words = "";
        try {

            if (lang.equalsIgnoreCase("English")) {
                if (word != null) {
                    words += SaveFile(word.getLeft(), lang, fw);
                    for (int i = 0; i < word.getMeaning().size(); i++) {
                        if (i != (word.getMeaning().size()) - 1) {
                            means += word.getMeaning().get(i) + ",";
                        } else {
                            means += word.getMeaning().get(i);
                        }
                    }
                    words += word.getName() + ":" + means + "\n";
                    words += SaveFile(word.getRight(), lang, fw);
                }
            } else {
                if (word != null) {
                    words += SaveFile(word.getLeft(), lang, fw);
                    for (int i = 0; i < word.getMeaning().size(); i++) {
                        if (i != (word.getMeaning().size()) - 1) {
                            means += word.getMeaning().get(i) + ",";
                        } else {
                            means += word.getMeaning().get(i);
                        }
                    }
                    words += word.getName() + ":" + means + "\n";
                    words += SaveFile(word.getRight(), lang, fw);
                }
            }
        } catch (Exception ex) {

        }
        return words;

    }

    private void UpdateUI() {

        engListModel.clear();
        engWord = engList.getRoot();
        Update(engWord, "English");
        this.engWordList.setModel(engListModel);

        vietListModel.clear();
        vietWord = vietList.getRoot();
        Update(vietWord, "Vietnamese");
        this.vietWordList.setModel(vietListModel);

    }

    public void addNew(int lang) {
        StringTokenizer stk;
        try {
            List<String> means = new LinkedList<>();
            List<String> newNameMean;

            if (lang == 0) {
                String word, mean;
                word = StringUtils.capitalize(txtNewEngName.getText().toLowerCase().trim());
                mean = txtNewEngMean.getText().trim();
                if (LookUp(engWord, word) != null) {
                    JOptionPane.showMessageDialog(rootPane, "This word is existed!");
                    return;
                }
                if (word != null && mean != null) {
                    stk = new StringTokenizer(mean, ",");
                    while (stk.hasMoreTokens()) {
                        means.add(StringUtils.capitalize(stk.nextToken().toLowerCase().trim()));
                    }
                    engList.Add(word, means);
                    newNameMean = new LinkedList<>();
                    newNameMean.add(word);
                    String newName;
                    for (int i = 0; i < means.size(); i++) {
                        newName = means.get(i);
                        Word newVietWord = LookUp(vietWord, newName);
                        if (newVietWord == null) {
                            vietList.Add(newName, newNameMean);
                        }
                        if (newVietWord != null) {
                            newNameMean = newVietWord.getMeaning();
                            newNameMean.add(word);
                            newVietWord.setMeaning(newNameMean);
                        }
                    }
                    isChanged = true;
                    btnDelete.setEnabled(true);
                    btnUpdate.setEnabled(true);
                    UpdateUI();
                    engWordList.setSelectedIndex(engWordList.getModel().getSize() - 1);
                    engWordListMouseClicked(null);
                }
            }
            if (lang == 1) {
                String word, mean;
                word = StringUtils.capitalize(txtNewVietName.getText().toLowerCase().trim());
                mean = txtNewVietMean.getText().trim();
                System.out.println(word);
                if (LookUp(vietWord, word) != null) {
                    JOptionPane.showMessageDialog(rootPane, "This word is existed!");
                    return;
                }
                if (word != null && mean != null) {
                    stk = new StringTokenizer(mean, ",");
                    while (stk.hasMoreTokens()) {
                        means.add(StringUtils.capitalize(stk.nextToken().toLowerCase().trim()));
                    }
                    vietList.Add(word, means);
                    newNameMean = new LinkedList<>();
                    newNameMean.add(word);
                    String newName;
                    for (int i = 0; i < means.size(); i++) {
                        newName = means.get(i);
                        Word newEngWord = LookUp(engWord, newName);
                        if (newEngWord == null) {
                            engList.Add(newName, newNameMean);
                        }
                        if (newEngWord != null) {
                            newNameMean = newEngWord.getMeaning();
                            newNameMean.add(word);
                            newEngWord.setMeaning(newNameMean);
                        }
                    }
                    isChanged = true;
                    btnDelete.setEnabled(true);
                    btnUpdate.setEnabled(true);
                    UpdateUI();
                    vietWordList.setSelectedIndex(vietWordList.getModel().getSize() - 1);
                    vietWordListMouseClicked(null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchEngList(Word root, String searchKey) {
        if (root != null) {
            if (root.getName().compareTo(searchKey) > 0) {
                if (root.getName().contains(searchKey)) {
                    engListModel.addElement(root.getName());
                }
                System.out.println(root.getName());
                searchEngList(root.getLeft(), searchKey);
            }
            if (root.getName().equals(searchKey)) {
                if (root.getName().contains(searchKey)) {
                    engListModel.addElement(root.getName());
                }
            }
            if (root.getName().compareTo(searchKey) < 0) {
                if (root.getName().contains(searchKey)) {
                    engListModel.addElement(root.getName());
                }
                searchEngList(root.getRight(), searchKey);
            }
        }
    }

    private void searchVietList(Word root, String searchKey) {
        if (root != null) {
            if (root.getName().compareTo(searchKey) > 0) {
                if (root.getName().contains(searchKey)) {
                    vietListModel.addElement(root.getName());
                }
                System.out.println(root.getName());
                searchEngList(root.getLeft(), searchKey);
            }
            if (root.getName().equals(searchKey)) {
                if (root.getName().contains(searchKey)) {
                    vietListModel.addElement(root.getName());
                }
            }
            if (root.getName().compareTo(searchKey) < 0) {
                if (root.getName().contains(searchKey)) {
                    vietListModel.addElement(root.getName());
                }
                searchEngList(root.getRight(), searchKey);
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

        newEngFrame = new javax.swing.JFrame();
        jPanel1 = new javax.swing.JPanel();
        wordPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtNewEngName = new javax.swing.JTextField();
        meanPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtNewEngMean = new javax.swing.JTextArea();
        buttonPanel = new javax.swing.JPanel();
        btnAddEng = new javax.swing.JButton();
        btnCancelEng = new javax.swing.JButton();
        newVietFrame = new javax.swing.JFrame();
        jPanel2 = new javax.swing.JPanel();
        wordPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtNewVietName = new javax.swing.JTextField();
        meanPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtNewVietMean = new javax.swing.JTextArea();
        buttonPanel1 = new javax.swing.JPanel();
        btnAddViet = new javax.swing.JButton();
        btnCancelViet = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        topPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        searchPanel = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        tabPanel = new javax.swing.JTabbedPane();
        engSplitPane = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        vietMeanArea = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        engWordList = new javax.swing.JList();
        vietSplitPane = new javax.swing.JSplitPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        engMeanArea = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        vietWordList = new javax.swing.JList();

        newEngFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        newEngFrame.setTitle("Enter a new English word!");
        newEngFrame.setLocation(new java.awt.Point(0, 0));
        newEngFrame.setResizable(false);
        newEngFrame.setSize(new java.awt.Dimension(400, 300));

        jPanel1.setMinimumSize(new java.awt.Dimension(370, 300));
        jPanel1.setPreferredSize(new java.awt.Dimension(370, 265));
        jPanel1.setLayout(new java.awt.BorderLayout());

        wordPanel.setPreferredSize(new java.awt.Dimension(370, 40));

        jLabel2.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        jLabel2.setText("Word:");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel2.setPreferredSize(new java.awt.Dimension(60, 30));
        wordPanel.add(jLabel2);

        txtNewEngName.setPreferredSize(new java.awt.Dimension(300, 30));
        wordPanel.add(txtNewEngName);

        jPanel1.add(wordPanel, java.awt.BorderLayout.NORTH);

        meanPanel.setPreferredSize(new java.awt.Dimension(370, 150));

        jLabel3.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        jLabel3.setText("Meaning:");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel3.setPreferredSize(new java.awt.Dimension(360, 30));
        meanPanel.add(jLabel3);

        jScrollPane3.setPreferredSize(new java.awt.Dimension(370, 100));

        txtNewEngMean.setColumns(20);
        txtNewEngMean.setRows(5);
        txtNewEngMean.setPreferredSize(new java.awt.Dimension(360, 80));
        jScrollPane3.setViewportView(txtNewEngMean);

        meanPanel.add(jScrollPane3);

        jPanel1.add(meanPanel, java.awt.BorderLayout.CENTER);

        buttonPanel.setPreferredSize(new java.awt.Dimension(370, 60));
        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnAddEng.setText("Add");
        btnAddEng.setPreferredSize(new java.awt.Dimension(100, 40));
        btnAddEng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddEngActionPerformed(evt);
            }
        });
        buttonPanel.add(btnAddEng);

        btnCancelEng.setText("Cancel");
        btnCancelEng.setPreferredSize(new java.awt.Dimension(100, 40));
        btnCancelEng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelEngActionPerformed(evt);
            }
        });
        buttonPanel.add(btnCancelEng);

        jPanel1.add(buttonPanel, java.awt.BorderLayout.SOUTH);

        newEngFrame.getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
        jPanel1.getAccessibleContext().setAccessibleParent(this);

        newVietFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        newVietFrame.setTitle("Enter a new Vietnamese word!");
        newVietFrame.setResizable(false);
        newVietFrame.setSize(new java.awt.Dimension(400, 300));

        jPanel2.setMinimumSize(new java.awt.Dimension(370, 300));
        jPanel2.setPreferredSize(new java.awt.Dimension(370, 265));
        jPanel2.setLayout(new java.awt.BorderLayout());

        wordPanel1.setPreferredSize(new java.awt.Dimension(370, 40));

        jLabel4.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        jLabel4.setText("Word:");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel4.setPreferredSize(new java.awt.Dimension(60, 30));
        wordPanel1.add(jLabel4);

        txtNewVietName.setPreferredSize(new java.awt.Dimension(300, 30));
        wordPanel1.add(txtNewVietName);

        jPanel2.add(wordPanel1, java.awt.BorderLayout.NORTH);

        meanPanel1.setPreferredSize(new java.awt.Dimension(370, 150));

        jLabel5.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        jLabel5.setText("Meaning:");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel5.setPreferredSize(new java.awt.Dimension(360, 30));
        meanPanel1.add(jLabel5);

        jScrollPane6.setPreferredSize(new java.awt.Dimension(370, 100));

        txtNewVietMean.setColumns(20);
        txtNewVietMean.setRows(5);
        txtNewVietMean.setPreferredSize(new java.awt.Dimension(360, 80));
        jScrollPane6.setViewportView(txtNewVietMean);

        meanPanel1.add(jScrollPane6);

        jPanel2.add(meanPanel1, java.awt.BorderLayout.CENTER);

        buttonPanel1.setPreferredSize(new java.awt.Dimension(370, 60));
        buttonPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnAddViet.setText("Add");
        btnAddViet.setPreferredSize(new java.awt.Dimension(100, 40));
        btnAddViet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddVietActionPerformed(evt);
            }
        });
        buttonPanel1.add(btnAddViet);

        btnCancelViet.setText("Cancel");
        btnCancelViet.setPreferredSize(new java.awt.Dimension(100, 40));
        btnCancelViet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelVietActionPerformed(evt);
            }
        });
        buttonPanel1.add(btnCancelViet);

        jPanel2.add(buttonPanel1, java.awt.BorderLayout.SOUTH);

        newVietFrame.getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dictionary Application");
        setBackground(new java.awt.Color(204, 255, 255));
        setLocation(new java.awt.Point(250, 200));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setSize(new java.awt.Dimension(800, 600));

        jPanel3.setLayout(new java.awt.BorderLayout());

        topPanel.setBackground(new java.awt.Color(51, 102, 255));
        topPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topPanel.setPreferredSize(new java.awt.Dimension(674, 80));

        jLabel1.setFont(new java.awt.Font("NSimSun", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 255, 255));
        jLabel1.setText("Dictionary");
        jLabel1.setAlignmentY(0.0F);

        btnNew.setBackground(new java.awt.Color(153, 255, 153));
        btnNew.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        btnNew.setForeground(new java.awt.Color(255, 102, 51));
        btnNew.setText("New Word");
        btnNew.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnNew.setBorderPainted(false);
        btnNew.setPreferredSize(new java.awt.Dimension(83, 21));
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnSave.setBackground(new java.awt.Color(153, 255, 153));
        btnSave.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 102, 0));
        btnSave.setText("Save");
        btnSave.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSave.setBorderPainted(false);
        btnSave.setPreferredSize(new java.awt.Dimension(83, 23));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnExit.setBackground(new java.awt.Color(153, 255, 153));
        btnExit.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        btnExit.setForeground(new java.awt.Color(255, 102, 0));
        btnExit.setText("Exit");
        btnExit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnExit.setBorderPainted(false);
        btnExit.setPreferredSize(new java.awt.Dimension(83, 23));
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(153, 255, 153));
        btnDelete.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 102, 51));
        btnDelete.setText("Delete");
        btnDelete.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDelete.setBorderPainted(false);
        btnDelete.setPreferredSize(new java.awt.Dimension(83, 21));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(153, 255, 153));
        btnUpdate.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 102, 51));
        btnUpdate.setText("Update");
        btnUpdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUpdate.setBorderPainted(false);
        btnUpdate.setPreferredSize(new java.awt.Dimension(83, 21));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel3.add(topPanel, java.awt.BorderLayout.NORTH);
        topPanel.getAccessibleContext().setAccessibleParent(jPanel3);

        searchPanel.setPreferredSize(new java.awt.Dimension(720, 30));
        searchPanel.setLayout(new java.awt.BorderLayout());

        txtSearch.setText("Search");
        txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchFocusLost(evt);
            }
        });
        txtSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchMouseClicked(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });
        searchPanel.add(txtSearch, java.awt.BorderLayout.CENTER);

        jPanel3.add(searchPanel, java.awt.BorderLayout.CENTER);

        tabPanel.setBackground(new java.awt.Color(204, 255, 255));
        tabPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tabPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabPanel.setName(""); // NOI18N
        tabPanel.setPreferredSize(new java.awt.Dimension(437, 450));

        engSplitPane.setDividerLocation(150);
        engSplitPane.setDividerSize(2);

        vietMeanArea.setEditable(false);
        vietMeanArea.setColumns(20);
        vietMeanArea.setRows(5);
        jScrollPane2.setViewportView(vietMeanArea);

        engSplitPane.setRightComponent(jScrollPane2);

        engWordList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                engWordListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(engWordList);

        engSplitPane.setLeftComponent(jScrollPane1);

        tabPanel.addTab("tab1", engSplitPane);

        vietSplitPane.setDividerLocation(150);
        vietSplitPane.setDividerSize(2);

        engMeanArea.setColumns(20);
        engMeanArea.setRows(5);
        jScrollPane4.setViewportView(engMeanArea);

        vietSplitPane.setRightComponent(jScrollPane4);

        jScrollPane5.setPreferredSize(new java.awt.Dimension(50, 130));

        vietWordList.setPreferredSize(new java.awt.Dimension(50, 80));
        vietWordList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vietWordListMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(vietWordList);

        vietSplitPane.setLeftComponent(jScrollPane5);

        tabPanel.addTab("tab2", vietSplitPane);

        jPanel3.add(tabPanel, java.awt.BorderLayout.SOUTH);
        tabPanel.getAccessibleContext().setAccessibleName("tab");

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void engWordListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_engWordListMouseClicked
        // TODO add your handling code here:
        try {
            if (engListModel.isEmpty()) {
                vietMeanArea.setText("");
                return;
            }
            String engWordSelecting = String.valueOf(engWordList.getSelectedValue());
            vietMeanArea.setText(LookUp(engWord, engWordSelecting).getMeaningString());
            System.out.println(engListModel.getSize());
            System.out.println(engWordList.getSelectedIndex());

        } catch (Exception e) {
            System.out.println(e);
        }


    }//GEN-LAST:event_engWordListMouseClicked

    private void vietWordListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vietWordListMouseClicked
        // TODO add your handling code here:
        try {
            if (vietListModel.isEmpty()) {
                engMeanArea.setText("");
                return;
            }
            String vietWordSelecting = String.valueOf(vietWordList.getSelectedValue());
            engMeanArea.setText(LookUp(vietWord, vietWordSelecting).getMeaningString());
        } catch (Exception e) {
            System.out.println(e);
        }

    }//GEN-LAST:event_vietWordListMouseClicked

    private void DeleteInMeaning(Word wordToFind, String wordSelecting, int lang) {
        List<String> temp;
        if (wordToFind != null) {
            DeleteInMeaning(wordToFind.getLeft(), wordSelecting, lang);
            temp = wordToFind.getMeaning();
            for (int i = 0; i < wordToFind.getMeaning().size(); i++) {
                if (wordSelecting.equalsIgnoreCase(wordToFind.getMeaning().get(i))) {
                    temp.remove(i);
                    wordToFind.setMeaning(temp);
                }
            }
            if (wordToFind.getMeaning().isEmpty() && lang == 0) {
                vietWord = vietList.Delete(vietWord, wordToFind.getName());
            } else if (wordToFind.getMeaning().isEmpty() && lang == 1) {
                engWord = engList.Delete(engWord, wordToFind.getName());
            }
            DeleteInMeaning(wordToFind.getRight(), wordSelecting, lang);
        }
    }

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        if (vietListModel.isEmpty() && engListModel.isEmpty()) {
            return;
        }
        if (tabPanel.getSelectedIndex() == 0 && !engListModel.isEmpty()) {
            String engWordSelecting = String.valueOf(engWordList.getSelectedValue());
            DeleteInMeaning(vietWord, engWordSelecting, 0);
            engWord = engList.Delete(engWord, engWordSelecting);
        }
        if (tabPanel.getSelectedIndex() == 1 && !vietListModel.isEmpty()) {
            String vietWordSelecting = String.valueOf(vietWordList.getSelectedValue());
            DeleteInMeaning(engWord, vietWordSelecting, 1);
            vietWord = vietList.Delete(vietWord, vietWordSelecting);
        }
        isChanged = true;
        UpdateUI();
        engWordList.setSelectedIndex(engWordList.getModel().getSize() - 1);
        engWordListMouseClicked(null);
        vietWordList.setSelectedIndex(vietWordList.getModel().getSize() - 1);
        vietWordListMouseClicked(null);
        if (vietListModel.isEmpty() && engListModel.isEmpty()) {
            btnDelete.setEnabled(false);
            btnUpdate.setEnabled(false);
        }

    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        if (isChanged) {
            int choice = JOptionPane.showConfirmDialog(rootPane, "Do you want to save?");
            if (choice == JOptionPane.OK_OPTION) {
                btnSaveActionPerformed(null);
                System.exit(0);
            } else if (choice == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        try {
            engWord = engList.getRoot();
            vietWord = vietList.getRoot();
            FileWriter efw = new FileWriter(engFile);
            String eng = SaveFile(engWord, "English", efw);
            efw.write(eng);
            FileWriter vfw = new FileWriter(vietFile);
            String viet = SaveFile(vietWord, "Vietnamese", vfw);
            vfw.write(viet);
            JOptionPane.showMessageDialog(rootPane, "File Saved!");
            efw.close();
            vfw.close();
            isChanged = false;
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        engWord = engList.getRoot();
        vietWord = vietList.getRoot();
        if (tabPanel.getSelectedIndex() == 0) {
            newEngFrame.setLocationRelativeTo(this);
            newEngFrame.setVisible(true);
        }
        if (tabPanel.getSelectedIndex() == 1) {
            newVietFrame.setLocationRelativeTo(this);
            newVietFrame.setVisible(true);
        }
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        StringTokenizer stk;
        String mean = null;
        try {
            mean = JOptionPane.showInputDialog("Enter new meaning!(Split means with comma)").trim();
        } catch (Exception e) {
        }
        List<String> means = new LinkedList<>();
        List<String> newNameMean;

        if (mean != null) {
            stk = new StringTokenizer(mean, ",");
            while (stk.hasMoreTokens()) {
                means.add(stk.nextToken().trim());
            }
            String vietWordSelecting = String.valueOf(vietWordList.getSelectedValue());
            String engWordSelecting = String.valueOf(engWordList.getSelectedValue());
            if (tabPanel.getSelectedIndex() == 0) {
                engWord = engList.getRoot();
                LookUp(engWord, engWordSelecting).setMeaning(means);
                newNameMean = new LinkedList<>();
                newNameMean.add(engWordSelecting);
                String newName;
                for (int i = 0; i < means.size(); i++) {
                    newName = means.get(i);
                    Word newVietWord = LookUp(vietWord, newName);
                    if (newVietWord == null) {
                        vietList.Add(newName, newNameMean);
                    }
                }
                isChanged = true;
                UpdateUI();
                engWordList.setSelectedIndex(engWordList.getModel().getSize() - 1);
                engWordListMouseClicked(null);

            }
            if (tabPanel.getSelectedIndex() == 1) {
                vietWord = vietList.getRoot();
                LookUp(vietWord, vietWordSelecting).setMeaning(means);
                newNameMean = new LinkedList<>();
                newNameMean.add(vietWordSelecting);
                String newName;
                for (int i = 0; i < means.size(); i++) {
                    newName = means.get(i);
                    Word newEngWord = LookUp(engWord, newName);
                    if (newEngWord == null) {
                        engList.Add(newName, newNameMean);
                    }
                }
                isChanged = true;
                UpdateUI();
                vietWordList.setSelectedIndex(vietWordList.getModel().getSize() - 1);
                vietWordListMouseClicked(null);
            }

        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnAddEngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEngActionPerformed
        // TODO add your handling code here:
        addNew(0);
        newEngFrame.dispose();
    }//GEN-LAST:event_btnAddEngActionPerformed

    private void btnCancelEngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelEngActionPerformed
        // TODO add your handling code here:
        newEngFrame.dispose();

    }//GEN-LAST:event_btnCancelEngActionPerformed

    private void btnAddVietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddVietActionPerformed
        // TODO add your handling code here
        addNew(1);
        newVietFrame.dispose();
    }//GEN-LAST:event_btnAddVietActionPerformed

    private void btnCancelVietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelVietActionPerformed
        // TODO add your handling code here:
        newVietFrame.dispose();

    }//GEN-LAST:event_btnCancelVietActionPerformed

    private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseClicked
        // TODO add your handling code here:
        if (this.txtSearch.getText().equals(searchMessage)) {
            this.txtSearch.setText("");
        }
    }//GEN-LAST:event_txtSearchMouseClicked

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
        if (this.txtSearch.getText().isEmpty()) {
            if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                UpdateUI();
                if (engWordList.getModel().getSize() > 0) {
                    engWordList.setSelectedIndex(0);
                    engWordListMouseClicked(null);
                }
                if (vietWordList.getModel().getSize() > 0) {
                    vietWordList.setSelectedIndex(vietWordList.getModel().getSize() - 1);
                    vietWordListMouseClicked(null);
                }
            } else {
                this.txtSearch.setText(searchMessage);
                UpdateUI();
                if (engWordList.getModel().getSize() > 0) {
                    engWordList.setSelectedIndex(0);
                    engWordListMouseClicked(null);
                }
                if (vietWordList.getModel().getSize() > 0) {
                    vietWordList.setSelectedIndex(0);
                    vietWordListMouseClicked(null);
                }

            }

        } else {
            String searchWord = StringUtils.capitalize(this.txtSearch.getText().trim().toLowerCase());
            if (tabPanel.getSelectedIndex() == 0) {
                engListModel.clear();
                Word current = engList.getRoot();
                searchEngList(current, searchWord);
                engWordList.setModel(engListModel);
                if (engWordList.getModel().getSize() > 0) {
                    engWordList.setSelectedIndex(0);
                    engWordListMouseClicked(null);
                }

            }
            if (tabPanel.getSelectedIndex() == 1) {
                vietListModel.clear();
                Word current = vietList.getRoot();
                searchEngList(current, searchWord);
                vietWordList.setModel(vietListModel);
                if (vietWordList.getModel().getSize() > 0) {
                    vietWordList.setSelectedIndex(0);
                    vietWordListMouseClicked(null);
                }
            }

        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        // TODO add your handling code here:
        if (this.txtSearch.getText().equals(searchMessage)) {
            this.txtSearch.setText("");
        }

    }//GEN-LAST:event_txtSearchKeyPressed

    private void txtSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchFocusLost
        // TODO add your handling code here:
        if (this.txtSearch.getText().isEmpty()) {
            this.txtSearch.setText(searchMessage);
        }
    }//GEN-LAST:event_txtSearchFocusLost

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
            java.util.logging.Logger.getLogger(DictionaryApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DictionaryApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DictionaryApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DictionaryApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DictionaryApp("Dictionary Application").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddEng;
    private javax.swing.JButton btnAddViet;
    private javax.swing.JButton btnCancelEng;
    private javax.swing.JButton btnCancelViet;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JPanel buttonPanel1;
    private javax.swing.JTextArea engMeanArea;
    private javax.swing.JSplitPane engSplitPane;
    private javax.swing.JList engWordList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPanel meanPanel;
    private javax.swing.JPanel meanPanel1;
    private javax.swing.JFrame newEngFrame;
    private javax.swing.JFrame newVietFrame;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JTabbedPane tabPanel;
    private javax.swing.JPanel topPanel;
    private javax.swing.JTextArea txtNewEngMean;
    private javax.swing.JTextField txtNewEngName;
    private javax.swing.JTextArea txtNewVietMean;
    private javax.swing.JTextField txtNewVietName;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextArea vietMeanArea;
    private javax.swing.JSplitPane vietSplitPane;
    private javax.swing.JList vietWordList;
    private javax.swing.JPanel wordPanel;
    private javax.swing.JPanel wordPanel1;
    // End of variables declaration//GEN-END:variables
}
