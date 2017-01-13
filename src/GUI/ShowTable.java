/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import hci_project.ExamInfo;
import hci_project.Studentinfo;
import hci_project.management;
import static hci_project.management.getStudentinfo;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import static sun.awt.X11.XConstants.buttons;

/**
 *
 * @author moroclash
 */
public class ShowTable extends javax.swing.JPanel {

    /**
     * Creates new form ShowTable
     */
    private String subjectname;
    private ExamInfo examinfo;
    public ShowTable(String path,int n,ExamInfo examinfo,String Subjectname) {
        initComponents();
        this.subjectname =Subjectname;
        this.examinfo = examinfo;
        ArrayList<Studentinfo> a= management.getStudentinfo(path);
        Object data[][] = new Object[n][6];
        String s[]={"Student Name","ID","Exam model","Question right","Question wrong","Success"};
        Studentinfo info;
        int num = Integer.parseInt(management.ExmeQuestionNumper(Subjectname, examinfo.getExamname()));
        int numper = 0;
        if(num%2!=0)
        {
            numper = (num/2)+1;
        }
        else
        {
            numper = num/2;
        }
        for(int i=0;i<n;i++)
        {
            info = a.get(i);
            data[i][0]= info.getStudentName();
            data[i][1]= info.getStudentID();
            data[i][2]= info.getExamModel();
            data[i][3]= info.getCorrectAnswerNumber();
            data[i][4]= num-info.getCorrectAnswerNumber(); 
            if(info.getCorrectAnswerNumber()>=numper)
                data[i][5]= "Pass" ;
            else
                data[i][5]= "fail" ;
        }
        jTable1.setModel(new DefaultTableModel(data, s));    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();

        setBackground(new java.awt.Color(186, 242, 172));
        setPreferredSize(new java.awt.Dimension(1024, 600));

        jTable1.setBackground(new java.awt.Color(58, 9, 9));
        jTable1.setForeground(new java.awt.Color(193, 203, 207));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student Name", "ID", "Exam model", "Questions right", "Question rong", "Success"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
        }

        jButton2.setText("Backe");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1033, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ExameFrame show  = new ExameFrame(examinfo, subjectname);
        show.setBounds(0, 0,1048, 610);
        this.setVisible(false);
        this.removeAll();
        this.add(show);
        this.repaint();
        this.setVisible(true);
        show.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}