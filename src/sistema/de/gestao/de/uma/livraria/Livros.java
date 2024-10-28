/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.de.gestao.de.uma.livraria;

import Modulos.ModuloConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.dbutils.DbUtils;



/**
 *
 * @author Elcidio De Sousa
 */
public class Livros extends javax.swing.JFrame {
        Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form Livros
     */
    public Livros() {
        initComponents();
        
        conexao = ModuloConexao.conector();
           preencherTabela();
           preencherIdLivro();
           preencherComboBoxFornecedores();
    }
    
    private String gerarIdLivro() {
    // Gera um número aleatório entre 1000 e 9999
    int numeroAleatorio = (int) (Math.random() * 9000) + 1000;
    // Formata o ID do livro, você pode ajustar conforme necessário
    String idLivro = "LIV" + numeroAleatorio;
    return idLivro;
}

private void preencherIdLivro() {
    String idGerado = gerarIdLivro();
    txtIdLivro.setText(idGerado); // Supondo que txtIdLivro seja o campo de texto para o ID do livro
}

    
 private void cadastrarLivro() {
    String sql = "INSERT INTO livros(id_livro, titulo, autor, genero, isbn, editora, ano_publicacao, preco, quantidade_estoque, fornecedor) VALUES(?,?,?,?,?,?,?,?,?,?)";

    try {
        // Verifique se a conexão foi inicializada corretamente
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Erro: Conexão com o banco de dados não foi estabelecida.");
            return;
        }

        pst = conexao.prepareStatement(sql);

        // Verificação para garantir que os campos de entrada não sejam null
        if (txtIdLivro == null || txttitulo == null || txtautor == null || txtisbn == null || txtpreco == null || txtestoque == null) {
            JOptionPane.showMessageDialog(null, "Erro: Campos de entrada não foram inicializados corretamente.");
            return;
        }

        pst.setString(1, txtIdLivro.getText());
        pst.setString(2, txttitulo.getText());
        pst.setString(3, txtautor.getText());
        pst.setString(4, txtgenero.getText());
        pst.setString(5, txtisbn.getText());
        pst.setString(6, txteditora.getText());
        pst.setInt(7, Integer.parseInt(txtAno.getText()));
        pst.setDouble(8, Double.parseDouble(txtpreco.getText()));
        pst.setInt(9, Integer.parseInt(txtestoque.getText()));
       pst.setString(10, jComboBoxFornecedor.getSelectedItem().toString());
        

        // Verifique se os campos obrigatórios foram preenchidos
        if (txtIdLivro.getText().isEmpty() || txttitulo.getText().isEmpty() || txtautor.getText().isEmpty() || txtisbn.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
        } else {
            int adicionado = pst.executeUpdate();
            if (adicionado > 0) {
                JOptionPane.showMessageDialog(null, "Livro cadastrado com sucesso!");
                preencherTabela();
                limpar();
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Erro de formatação de número: " + e.getMessage());
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
    }
}

 
 public void setar_campos() {
    int setar = TabelaLivros.getSelectedRow();
    
    // Definindo os valores para os campos de texto correspondentes aos atributos de Livro
    txtIdLivro.setText(TabelaLivros.getModel().getValueAt(setar, 0).toString());          // id_livro
    txttitulo.setText(TabelaLivros.getModel().getValueAt(setar, 1).toString());           // título
    txtautor.setText(TabelaLivros.getModel().getValueAt(setar, 2).toString());            // autor
    txtgenero.setText(TabelaLivros.getModel().getValueAt(setar, 3).toString());           // gênero
    txtisbn.setText(TabelaLivros.getModel().getValueAt(setar, 4).toString());             // ISBN
    txteditora.setText(TabelaLivros.getModel().getValueAt(setar, 5).toString());          // editora
    txtAno.setText(TabelaLivros.getModel().getValueAt(setar, 6).toString());    // ano_publicacao
    txtpreco.setText(TabelaLivros.getModel().getValueAt(setar, 7).toString());            // preço
    txtestoque.setText(TabelaLivros.getModel().getValueAt(setar, 8).toString()); // quantidade_estoque
  //  txtFornecedorId.setText(TabelaLivros.getModel().getValueAt(setar, 9).toString());     // fornecedor_id
  //  txtDataCadastro.setText(TabelaLivros.getModel().getValueAt(setar, 10).toString());    // data_cadastro
}

private void preencherTabela() {
    String sql = "SELECT * FROM livros"; // Consulta para selecionar todos os livros

    try {
        pst = conexao.prepareStatement(sql);
        rs = pst.executeQuery();

        // Obter o modelo da tabela
        DefaultTableModel modelo = (DefaultTableModel) TabelaLivros.getModel();
        modelo.setRowCount(0); // Limpar a tabela antes de preencher

        // Percorrer o ResultSet e adicionar as linhas à tabela
        while (rs.next()) {
            Object[] linha = {
                rs.getString("id_Livro"),
                rs.getString("titulo"),
                rs.getString("autor"),
                rs.getString("genero"),
                rs.getString("isbn"),
                rs.getString("editora"),
                rs.getInt("ano_Publicacao"),
                rs.getDouble("preco"),
                rs.getInt("quantidade_Estoque"),
             //   rs.getString("fornecedor_id")  // ou o campo correspondente para o fornecedor
            };
            modelo.addRow(linha); // Adicionar a linha ao modelo
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao preencher tabela: " + e.getMessage());
    }
}



private void Consultar() {
    String sql = "SELECT * FROM livros WHERE titulo=? OR id_livro=?";
    try {
        pst = conexao.prepareStatement(sql);
        pst.setString(1, txttitulo.getText());  // Usar o campo de texto da pesquisa para buscar o ID do livro
         pst.setString(2, txtIdLivro.getText()); 
        rs = pst.executeQuery();

        if (rs.next()) {
            // Preenchendo os campos com os dados do livro encontrado
            txtIdLivro.setText(rs.getString("id_Livro"));
            txttitulo.setText(rs.getString("titulo"));
            txtautor.setText(rs.getString("autor"));
            txtgenero.setText(rs.getString("genero"));
            txtisbn.setText(rs.getString("isbn"));
            txteditora.setText(rs.getString("editora"));
            txtAno.setText(rs.getString("ano_Publicacao"));
            txtpreco.setText(rs.getString("preco"));
            txtestoque.setText(rs.getString("quantidade_Estoque"));
            jComboBoxFornecedor.setSelectedItem(rs.getString("fornecedor"));

        } else {
            JOptionPane.showMessageDialog(null, "Livro não encontrado!");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao consultar: " + e.getMessage());
    }
}


private void actualizar() {
    String sql = "UPDATE livros SET autor=?, genero=?, isbn=?, editora=?, ano_publicacao=?, preco=?, quantidade_estoque=?, fornecedor=? WHERE titulo=?";

    try {
        pst = conexao.prepareStatement(sql);
        
        // Configurando os parâmetros da atualização com base nos campos da interface
        pst.setString(1, txtautor.getText());
        pst.setString(2, txtgenero.getText());
        pst.setString(3, txtisbn.getText());
        pst.setString(4, txteditora.getText());
        pst.setInt(5, Integer.parseInt(txtAno.getText()));
        pst.setDouble(6, Double.parseDouble(txtpreco.getText()));
        pst.setInt(7, Integer.parseInt(txtestoque.getText()));
        pst.setString(10, jComboBoxFornecedor.getSelectedItem().toString());

        // Define o título do livro como critério de atualização
        pst.setString(8, txttitulo.getText());  // Nome do livro a ser atualizado

        // Executando a atualização
        int adicionado = pst.executeUpdate();
        if (adicionado > 0) {
            JOptionPane.showMessageDialog(null, "Livro atualizado com sucesso!");
            preencherTabela();  // Atualizar a tabela na interface
            limpar();
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao atualizar livro: " + e.getMessage());
    }
}


private void deletar() {
    int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este livro?", "Atenção", JOptionPane.YES_NO_OPTION);

    if (confirma == JOptionPane.YES_OPTION) {
        String sql = "DELETE FROM livros WHERE titulo=?"; // Ou use um identificador único como idLivro

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txttitulo.getText()); // O título do livro a ser removido
            pst.executeUpdate();

            DefaultTableModel model = (DefaultTableModel) TabelaLivros.getModel();
            int linhaSelecionada = TabelaLivros.getSelectedRow();
            if (linhaSelecionada != -1) {
                model.removeRow(linhaSelecionada); // Remover a linha selecionada da tabela
            }

           limpar(); // Limpar os campos após a remoção
            JOptionPane.showMessageDialog(null, "Livro removido com sucesso!");
            preencherTabela(); // Atualizar a tabela na interface
          limpar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover livro: " + e.getMessage());
        }
    }
}

private void preencherComboBoxFornecedores() {
    String sql = "SELECT nome FROM fornecedores"; // Consulta para selecionar o nome dos fornecedores

    try {
        pst = conexao.prepareStatement(sql);
        rs = pst.executeQuery();

        // Primeiro, limpar o ComboBox antes de adicionar novos itens
        jComboBoxFornecedor.removeAllItems();
        jComboBoxFornecedor.addItem("Selecione um fornecedor"); // Opcional, pode adicionar um item padrão

        // Percorrer o ResultSet e adicionar os nomes dos fornecedores ao ComboBox
        while (rs.next()) {
            String nomeFornecedor = rs.getString("nome");
            jComboBoxFornecedor.addItem(nomeFornecedor);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao preencher ComboBox de fornecedores: " + e.getMessage());
    }
}


private void limpar() {
    txtIdLivro.setText("");
    txttitulo.setText("");
    txtautor.setText("");
    txtgenero.setText("");
    txtisbn.setText("");
    txteditora.setText("");
    txtAno.setText("");
    txtpreco.setText("");
    txtestoque.setText("");
  //  txtFornecedor.setText("");
}



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        JCadastrar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnPesquisar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtIdLivro = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txttitulo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtautor = new javax.swing.JTextField();
        txtgenero = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtisbn = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txteditora = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtAno = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtpreco = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtestoque = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaLivros = new javax.swing.JTable();
        jComboBoxFornecedor = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

        JCadastrar.setBackground(new java.awt.Color(51, 51, 51));
        JCadastrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        JCadastrar.setForeground(new java.awt.Color(255, 255, 255));
        JCadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/GuardarTodo.png"))); // NOI18N
        JCadastrar.setText("CADASTRAR");
        JCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JCadastrarActionPerformed(evt);
            }
        });

        btnActualizar.setBackground(new java.awt.Color(51, 51, 51));
        btnActualizar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Actualizar (2).png"))); // NOI18N
        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnPesquisar.setBackground(new java.awt.Color(51, 51, 51));
        btnPesquisar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnPesquisar.setForeground(new java.awt.Color(255, 255, 255));
        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/FormatFactorySearch-32.png"))); // NOI18N
        btnPesquisar.setText("PESQUISAR");
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(51, 51, 51));
        btnEliminar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        btnEliminar.setText("EXCLUIR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

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

        jLabel4.setBackground(new java.awt.Color(255, 204, 0));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/morri.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnActualizar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSair, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 11, Short.MAX_VALUE)
                        .addComponent(JCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91)
                .addComponent(JCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(btnSair)
                .addContainerGap(313, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/log.png"))); // NOI18N

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        jLabel5.setText("SISTEMA DE GESTÃO DE UMA LIVRARIA");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1142, 1142, 1142)
                        .addComponent(jLabel1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(415, 415, 415)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(238, 238, 238)
                        .addComponent(jLabel5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(64, 64, 64)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(0, 204, 204));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("LIVROS");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(453, 453, 453)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText(" DADOS DO LIVRO");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("ID");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("AUTOR");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("TITULO");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("ANO DE PUBLICACAO");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("ISBN");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("EDITORA");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("GENERO");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("PRECO");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("ESTOQUE");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("FORNECEDOR");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("VISUALIZACAO");

        TabelaLivros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "TITULO", "AUTOR", "GENERO", "PRECO", "ESTOQUE"
            }
        ));
        jScrollPane1.setViewportView(TabelaLivros);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1581, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtIdLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel13))
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addGap(27, 27, 27)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel9)
                                                    .addComponent(txttitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel8)
                                                    .addComponent(txtautor, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addGap(75, 75, 75)
                                                .addComponent(jLabel11))))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(744, 744, 744)
                                        .addComponent(jLabel12))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(txtgenero, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtisbn, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txteditora, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel10))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(jLabel14)
                                                .addGap(169, 169, 169)
                                                .addComponent(jLabel15))
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(txtpreco, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtestoque, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(23, 23, 23)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel16)
                                            .addComponent(jComboBoxFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 1581, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdLivro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtautor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtgenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtisbn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txteditora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtpreco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtestoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JCadastrarActionPerformed
        // TODO add your handling code here:
        cadastrarLivro();
   
    }//GEN-LAST:event_JCadastrarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        actualizar();
     
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        // TODO add your handling code here:
       Consultar();
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        deletar();
       
    }//GEN-LAST:event_btnEliminarActionPerformed

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
            java.util.logging.Logger.getLogger(Livros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Livros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Livros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Livros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Livros().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JCadastrar;
    private javax.swing.JTable TabelaLivros;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox jComboBoxFornecedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField txtAno;
    private javax.swing.JTextField txtIdLivro;
    private javax.swing.JTextField txtautor;
    private javax.swing.JTextField txteditora;
    private javax.swing.JTextField txtestoque;
    private javax.swing.JTextField txtgenero;
    private javax.swing.JTextField txtisbn;
    private javax.swing.JTextField txtpreco;
    private javax.swing.JTextField txttitulo;
    // End of variables declaration//GEN-END:variables
}
