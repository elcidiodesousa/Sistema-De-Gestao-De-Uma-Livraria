/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sistema.de.gestao.de.uma.livraria;

import Modulos.ModuloConexao;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Elcidio De Sousa
 */

public class Dashboard extends javax.swing.JFrame {
  Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form EstudanteForm
     */
    public Dashboard() {
        initComponents();
 conexao = ModuloConexao.conector();
 contarLivros();
       contarVendas();
       contarCli();
        contarF();
        contarFU();
        contarFUNDOS();
        verificarEstoque();
    }

      private void contarLivros() {
        String sql = "SELECT COUNT(*) AS total FROM livros";
        
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                int totalLivros = rs.getInt("total");
                txttotalLivros.setText(String.valueOf(totalLivros)); // Seta o total no JTextField
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao contar livros: " + e.getMessage());
        }
    }
      
      
       private void contarFUNDOS() {
        String sql = "SELECT SUM(total) AS total FROM vendas";
        
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                int totalFUNDOS = rs.getInt("total");
                txttotalFUNDOS.setText(String.valueOf(totalFUNDOS +"$")); // Seta o total no JTextField
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao contar livros: " + e.getMessage());
        }
    }
      
      
        private void contarFU() {
        String sql = "SELECT COUNT(*) AS total FROM funcionarios";
        
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                int totalFU = rs.getInt("total");
                txttotalFU.setText(String.valueOf(totalFU)); // Seta o total no JTextField
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao contar livros: " + e.getMessage());
        }
    }
      
      
      
      
       private void contarF() {
        String sql = "SELECT COUNT(*) AS total FROM fornecedores";
        
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                int totalF = rs.getInt("total");
                txttotalF.setText(String.valueOf(totalF)); // Seta o total no JTextField
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao contar livros: " + e.getMessage());
        }
    }
      
      
      
      
       private void contarVendas() {
        String sql = "SELECT COUNT(*) AS total FROM  vendas";
        
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                int totalVendas = rs.getInt("total");
                txttotalVendas.setText(String.valueOf(totalVendas)); // Seta o total no JTextField
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao contar livros: " + e.getMessage());
        }
    }
       
         private void contarCli() {
        String sql = "SELECT COUNT(*) AS total FROM clientes";
        
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                int totalCli = rs.getInt("total");
                txttotalCli.setText(String.valueOf(totalCli)); // Seta o total no JTextField
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao contar livros: " + e.getMessage());
        }
    }
      
         
         private void verificarEstoque() {
    String sql = "SELECT titulo, quantidade_estoque FROM livros";
    
    try (PreparedStatement pst = conexao.prepareStatement(sql); 
         ResultSet rs = pst.executeQuery()) {
        
        StringBuilder alertas = new StringBuilder();
        
        while (rs.next()) {
            String tituloLivro = rs.getString("titulo");
            int quantidadeEstoque = rs.getInt("quantidade_estoque");
            
            if (quantidadeEstoque < 30) {
                alertas.append("Alerta \n O estoque de ").append(tituloLivro).append(" está esgotando!\n");
            }
        }
        
        // Exibe os alertas na JLabel
        if (alertas.length() > 0) {
            labelAlertaEsgotamento.setText("<html>" + alertas.toString().replace("\n", "<br/>") + "</html>");
            labelAlertaEsgotamento.setForeground(Color.RED); // Muda a cor do texto para vermelho
        } else {
            labelAlertaEsgotamento.setText(""); // Limpa a mensagem se não houver alertas
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao verificar estoque: " + e.getMessage());
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

        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txttotalLivros = new javax.swing.JTextField();
        txttotalVendas = new javax.swing.JTextField();
        txttotalCli = new javax.swing.JTextField();
        txttotalF = new javax.swing.JTextField();
        txttotalFU = new javax.swing.JTextField();
        txttotalFUNDOS = new javax.swing.JTextField();
        btnSair = new javax.swing.JButton();
        labelAlertaEsgotamento = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        jPanel5.setBackground(new java.awt.Color(0, 204, 204));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("DASBOARD");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(633, 633, 633)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        txttotalLivros.setEditable(false);
        txttotalLivros.setBackground(new java.awt.Color(255, 153, 0));
        txttotalLivros.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txttotalLivros.setForeground(new java.awt.Color(255, 255, 255));
        txttotalLivros.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txttotalLivros.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "LIVROS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(255, 255, 255))); // NOI18N

        txttotalVendas.setEditable(false);
        txttotalVendas.setBackground(new java.awt.Color(255, 0, 51));
        txttotalVendas.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txttotalVendas.setForeground(new java.awt.Color(255, 255, 255));
        txttotalVendas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txttotalVendas.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "VENDAS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(255, 255, 255))); // NOI18N

        txttotalCli.setEditable(false);
        txttotalCli.setBackground(new java.awt.Color(153, 0, 153));
        txttotalCli.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txttotalCli.setForeground(new java.awt.Color(255, 255, 255));
        txttotalCli.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txttotalCli.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CLIENTES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(255, 255, 255))); // NOI18N

        txttotalF.setEditable(false);
        txttotalF.setBackground(new java.awt.Color(0, 255, 51));
        txttotalF.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txttotalF.setForeground(new java.awt.Color(255, 255, 255));
        txttotalF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txttotalF.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "FORNECEDORES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(255, 255, 255))); // NOI18N

        txttotalFU.setEditable(false);
        txttotalFU.setBackground(new java.awt.Color(0, 153, 255));
        txttotalFU.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txttotalFU.setForeground(new java.awt.Color(255, 255, 255));
        txttotalFU.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txttotalFU.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "FUNCIONARIOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(255, 255, 255))); // NOI18N

        txttotalFUNDOS.setEditable(false);
        txttotalFUNDOS.setBackground(new java.awt.Color(255, 102, 255));
        txttotalFUNDOS.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txttotalFUNDOS.setForeground(new java.awt.Color(255, 255, 255));
        txttotalFUNDOS.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txttotalFUNDOS.setText("$");
        txttotalFUNDOS.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "FUNDOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(255, 255, 255))); // NOI18N

        btnSair.setBackground(new java.awt.Color(204, 0, 0));
        btnSair.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSair.setForeground(new java.awt.Color(255, 255, 255));
        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/off.png"))); // NOI18N
        btnSair.setText("SAIR");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        labelAlertaEsgotamento.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        labelAlertaEsgotamento.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(labelAlertaEsgotamento, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(211, 211, 211))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(224, 224, 224)
                        .addComponent(txttotalF, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(txttotalFU, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txttotalFUNDOS, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(226, 226, 226)
                        .addComponent(txttotalLivros, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(txttotalVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(txttotalCli, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(397, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttotalCli, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttotalVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttotalLivros, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txttotalF, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttotalFU, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttotalFUNDOS, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSair)
                        .addGap(49, 49, 49))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelAlertaEsgotamento, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(32, Short.MAX_VALUE))))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 1490, 510));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/log.png"))); // NOI18N

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabel5.setText("SISTEMA DE GESTÃO DE UMA LIVRARIA");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(329, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(594, 594, 594))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(297, 297, 297))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(32, 32, 32))
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1490, 220));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        // TODO add your handling code here:
        MenuPrincipal menu = new MenuPrincipal();
        menu.setVisible(true);
    }//GEN-LAST:event_btnSairActionPerformed

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
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSair;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel labelAlertaEsgotamento;
    private javax.swing.JTextField txttotalCli;
    private javax.swing.JTextField txttotalF;
    private javax.swing.JTextField txttotalFU;
    private javax.swing.JTextField txttotalFUNDOS;
    private javax.swing.JTextField txttotalLivros;
    private javax.swing.JTextField txttotalVendas;
    // End of variables declaration//GEN-END:variables

}
