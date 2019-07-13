
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
    WordList vietList, engList;
    DefaultListModel vietListModel;
    DefaultListModel engListModel;
    Word engWord, vietWord;

    /**
     * Creates new form DictionaryApp
     */
    public DictionaryApp() {
        initComponents();
        engListModel = new DefaultListModel();
        vietListModel = new DefaultListModel();
        engList = new WordList();
        vietList = new WordList();
        tabPanel.setTitleAt(0, "English - Tiếng Việt");
        tabPanel.setTitleAt(1, "Tiếng Việt - English");
        LoadEngData();
        LoadVietData();

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
            JOptionPane.showMessageDialog(rootPane, e);
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
            JOptionPane.showMessageDialog(rootPane, e);
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

    private void UpdateUI(int lang) {
        if (lang == 0) {
            engListModel.clear();
            engWord = engList.getRoot();
            Update(engWord, "English");
            this.engWordList.setModel(engListModel);
        }
        if (lang == 1) {
            vietListModel.clear();
            vietWord = vietList.getRoot();
            Update(vietWord, "Vietnamese");
            this.vietWordList.setModel(vietListModel);
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

        jPanel3 = new javax.swing.JPanel();
        topPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dictionary Application");
        setBackground(new java.awt.Color(204, 255, 255));
        setLocation(new java.awt.Point(150, 150));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setSize(new java.awt.Dimension(800, 600));

        jPanel3.setLayout(new java.awt.BorderLayout());

        topPanel.setBackground(new java.awt.Color(204, 255, 255));
        topPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topPanel.setPreferredSize(new java.awt.Dimension(674, 80));

        jLabel1.setFont(new java.awt.Font("NSimSun", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 255));
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
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

        tabPanel.setBackground(new java.awt.Color(204, 255, 255));
        tabPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tabPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabPanel.setName(""); // NOI18N
        tabPanel.setPreferredSize(null);

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

        jPanel3.add(tabPanel, java.awt.BorderLayout.CENTER);
        tabPanel.getAccessibleContext().setAccessibleName("tab");

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);
        jPanel3.getAccessibleContext().setAccessibleParent(null);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void engWordListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_engWordListMouseClicked
        // TODO add your handling code here:
        try {
            String engWordSelecting = String.valueOf(engWordList.getSelectedValue());
            vietMeanArea.setText(LookUp(engWord, engWordSelecting).getMeaningString());

        } catch (Exception e) {
        }


    }//GEN-LAST:event_engWordListMouseClicked

    private void vietWordListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vietWordListMouseClicked
        // TODO add your handling code here:
        try {
            String vietWordSelecting = String.valueOf(vietWordList.getSelectedValue());
            engMeanArea.setText(LookUp(vietWord, vietWordSelecting).getMeaningString());
        } catch (Exception e) {
        }

    }//GEN-LAST:event_vietWordListMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        String vietWordSelecting = String.valueOf(vietWordList.getSelectedValue());
        String engWordSelecting = String.valueOf(engWordList.getSelectedValue());
        if (tabPanel.getSelectedIndex() == 0) {
            engWord = engList.Delete(engWord, engWordSelecting);
            UpdateUI(0);
            engWordList.setSelectedIndex(engWordList.getModel().getSize() - 1);
            engWordListMouseClicked(null);

        }
        if (tabPanel.getSelectedIndex() == 1) {
            vietWord = vietList.Delete(vietWord, vietWordSelecting);
            UpdateUI(1);
            vietWordList.setSelectedIndex(vietWordList.getModel().getSize() - 1);
            vietWordListMouseClicked(null);
        }


    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:

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
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        try {
            List<String> means = new LinkedList<>();
            if (tabPanel.getSelectedIndex() == 0) {
                String word, mean;
                word = JOptionPane.showInputDialog("Enter a new English word!");
                mean = JOptionPane.showInputDialog("Enter meaning!");
                engWord = engList.getRoot();
                if (LookUp(engWord, word) != null) {
                    JOptionPane.showMessageDialog(rootPane, "This word is existed!");
                    return;
                }
                if (word != null && mean != null) {
                    means.add(mean.trim());
                    engList.Add(word, means);
                    UpdateUI(0);
                    engWordList.setSelectedIndex(engWordList.getModel().getSize() - 1);
                    engWordListMouseClicked(null);
                }
            }
            if (tabPanel.getSelectedIndex() == 1) {
                String word, mean;
                word = JOptionPane.showInputDialog("Enter a new Vietnamese word!");
                mean = JOptionPane.showInputDialog("Enter meaning!");
                vietWord = vietList.getRoot();
                if (LookUp(vietWord, word) != null) {
                    JOptionPane.showMessageDialog(rootPane, "This word is existed!");
                    return;
                }
                if (word != null && mean != null) {
                    means.add(mean.trim());
                    vietList.Add(word, means);
                    UpdateUI(1);
                    vietWordList.setSelectedIndex(vietWordList.getModel().getSize() - 1);
                    vietWordListMouseClicked(null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnNewActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        String mean = JOptionPane.showInputDialog("Enter new meaning!(Split means with comma)");
        List<String> means = new LinkedList<>();
        if (mean != null) {
            means.add(mean.trim());

            String vietWordSelecting = String.valueOf(vietWordList.getSelectedValue());
            String engWordSelecting = String.valueOf(engWordList.getSelectedValue());
            if (tabPanel.getSelectedIndex() == 0) {
                engWord = engList.getRoot();
                LookUp(engWord, engWordSelecting).setMeaning(means);
                UpdateUI(0);
                engWordList.setSelectedIndex(engWordList.getModel().getSize() - 1);
                engWordListMouseClicked(null);

            }
            if (tabPanel.getSelectedIndex() == 1) {
                vietWord = vietList.getRoot();
                LookUp(vietWord, vietWordSelecting).setMeaning(means);
                UpdateUI(1);
                vietWordList.setSelectedIndex(vietWordList.getModel().getSize() - 1);
                vietWordListMouseClicked(null);
            }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

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
                new DictionaryApp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JTextArea engMeanArea;
    private javax.swing.JSplitPane engSplitPane;
    private javax.swing.JList engWordList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane tabPanel;
    private javax.swing.JPanel topPanel;
    private javax.swing.JTextArea vietMeanArea;
    private javax.swing.JSplitPane vietSplitPane;
    private javax.swing.JList vietWordList;
    // End of variables declaration//GEN-END:variables
}
