/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.de.gestao.de.uma.livraria;

import Modulos.ModuloConexao;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Elcidio De Sousa
 */
public class Vendas extends javax.swing.JFrame {
        Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    /**
     * Creates new form Livros
     */
    public Vendas() {
        initComponents();
         conexao = ModuloConexao.conector();
         preencherId();
         preencherComboBoxClientes();
         preencherComboBoxLivros();
         preencherTabelaVendas();
         calcularValorFinal();
         calcularIva();
         
         
    DocumentListener listener = new DocumentListener() {
        public void insertUpdate(DocumentEvent e) { calcularValorFinal(); }
        public void removeUpdate(DocumentEvent e) { calcularValorFinal(); }
        public void changedUpdate(DocumentEvent e) { calcularValorFinal(); }
    };

    txtQuantidade.getDocument().addDocumentListener(listener);
    txtIva.getDocument().addDocumentListener(listener);
    txtDesconto.getDocument().addDocumentListener(listener);
    txtValor.getDocument().addDocumentListener(listener);
    
     DocumentListener listeneEr = new DocumentListener() {
        public void insertUpdate(DocumentEvent e) {  atualizarCampo2(); }
        public void removeUpdate(DocumentEvent e) { atualizarCampo2(); }
        public void changedUpdate(DocumentEvent e) { atualizarCampo2(); }
    };
     txtDesconto.getDocument().addDocumentListener(listeneEr);
     
     
     
        DocumentListener listeneeer = new DocumentListener() {
        public void insertUpdate(DocumentEvent e) {  calcularIva(); }
        public void removeUpdate(DocumentEvent e) { calcularIva(); }
        public void changedUpdate(DocumentEvent e) { calcularIva(); }
    };
     txtValor.getDocument().addDocumentListener(listeneeer);
    }

    
     private String gerarId() {
    // Gera um número aleatório entre 1000 e 9999
    int numeroAleatorio = (int) (Math.random() * 9000) + 1000;
    // Formata o ID do livro, você pode ajustar conforme necessário
    String idLivro = "COMP" + numeroAleatorio;
    return idLivro;
}

private void preencherId() {
    String idGerado = gerarId();
    txtID.setText(idGerado); // Supondo que txtIdLivro seja o campo de texto para o ID do livro
}

    
    
    
    private void atualizarCampo2() {
    jTextDESC.setText(txtDesconto.getText() +"%");
}
 private void preencherComboBoxClientes() {
    String sql = "SELECT nome, tipo_cliente FROM clientes"; // Consulta para selecionar o nome e tipo dos clientes

    try {
        pst = conexao.prepareStatement(sql);
        rs = pst.executeQuery();

        Vector<String> clientes = new Vector<>();
        Vector<Integer> precos = new Vector<>(); // Para armazenar os preços correspondentes

        while (rs.next()) {
            String nomeCliente = rs.getString("nome");
            String tipoCliente = rs.getString("tipo_cliente"); // Obtendo o tipo do cliente

            // Adiciona o cliente ao vetor de clientes
            clientes.add(nomeCliente);

            // Define o preço com base no tipo de cliente
            if ("VIP".equalsIgnoreCase(tipoCliente)) {
                precos.add(20); // Preço para cliente VIP
            } else {
                precos.add(0); // Preço para cliente Normal
            }
        }

        jComboBoxNomes.setModel(new DefaultComboBoxModel<>(clientes));

        // Adiciona ActionListener ao ComboBox
        jComboBoxNomes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = jComboBoxNomes.getSelectedIndex();
                if (selectedIndex >= 0 && selectedIndex < precos.size()) {
                    int precoSelecionado = precos.get(selectedIndex);
                    txtDesconto.setText(String.valueOf(precoSelecionado)); // Preenche o campo de texto com o preço
                }
            }
        });
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao preencher ComboBox de clientes: " + e.getMessage());
    }
}

    
   private void preencherComboBoxLivros() {
    String sql = "SELECT * FROM livros;";

    try {
        pst = conexao.prepareStatement(sql);
        rs = pst.executeQuery();

        Vector<String> livros = new Vector<>();
        Vector<Double> precos = new Vector<>(); // Para armazenar os preços dos livros

        while (rs.next()) {
            String tituloLivro = rs.getString("titulo");
            double precoLivro = rs.getDouble("preco");
            livros.add(tituloLivro);
            precos.add(precoLivro); // Adiciona o preço correspondente ao vetor
        }

        jComboBoxLivros.setModel(new DefaultComboBoxModel<>(livros));

        jComboBoxLivros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = jComboBoxLivros.getSelectedIndex();
                if (selectedIndex >= 0 && selectedIndex < precos.size()) {
                    double precoSelecionado = precos.get(selectedIndex);
                    txtValor.setText(String.valueOf(precoSelecionado)); // Preenche o campo de texto com o preço
                }
            }
        });
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao preencher ComboBox de livros: " + e.getMessage());
    }
}

private void calcularValorFinal() {
    SwingUtilities.invokeLater(() -> {
        try {
            // Obtendo os valores dos campos de texto
            int quantidade = Integer.parseInt(txtQuantidade.getText().trim());
            double iva = Double.parseDouble(txtIva.getText().trim()) / 100; // Convertendo percentual para decimal
            double desconto = Double.parseDouble(txtDesconto.getText().trim()) / 100; // Convertendo percentual para decimal
            double precoUnitario = Double.parseDouble(txtValor.getText().trim()); // Supondo que você tenha o preço unitário

            // Calculando o valor total antes do IVA e do desconto
            double valorTotalAntes = quantidade * precoUnitario;

            // Aplicando o IVA
            double valorComIva = valorTotalAntes + (valorTotalAntes * iva);

            // Aplicando o desconto
            double valorFinal = valorComIva - (valorComIva * desconto);

            // Definindo o valor final no campo de texto
            txtTOTAL.setText(String.format("%.2f MZN", valorFinal)); // Formata para 2 casas decimais
        } catch (NumberFormatException e) {
            txtTOTAL.setText("0.00 MZN"); // Caso ocorra erro, define total como 0.00
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    });
}


private void calcularIva() {
    try {
        // Obtendo o preço do livro
        double precoLivro = Double.parseDouble(txtValor.getText());

        // Lógica para determinar o IVA com base no preço do livro
        double iva;
        if (precoLivro > 3000) {
            iva = 3; // IVA de 3%
        } else if (precoLivro > 2000) {
            iva = 2.5; // IVA de 2.5%
        } else {
            iva = 1.2; // IVA de 1.2%
        }

        // Definindo o IVA no campo de texto
        txtIva.setText(String.valueOf(iva)); // Converte o valor de IVA para String e define no JTextField
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Erro: Insira um valor válido para o preço do livro!");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
    }
}



private void cadastrarVenda() {
    String sqlVenda = "INSERT INTO vendas(id, cliente, livro, quantidade, preco, pagamento, desconto, iva, total) VALUES(?,?,?,?,?,?,?,?,?)";
    String sqlAtualizaEstoque = "UPDATE livros SET quantidade_estoque = quantidade_estoque - ? WHERE titulo = ?";
    String sqlVerificaEstoque = "SELECT quantidade_estoque FROM livros WHERE titulo = ?";

    try {
        // Verifique se a conexão foi inicializada corretamente
        if (conexao == null) {
            JOptionPane.showMessageDialog(null, "Erro: Conexão com o banco de dados não foi estabelecida.");
            return;
        }

        // Verifica a quantidade em estoque
        String tituloLivro = jComboBoxLivros.getSelectedItem().toString();
        int quantidadeVenda = Integer.parseInt(txtQuantidade.getText());
        int quantidadeEstoque = 0;

        // Consulta para verificar o estoque disponível
        try (PreparedStatement pstVerificaEstoque = conexao.prepareStatement(sqlVerificaEstoque)) {
            pstVerificaEstoque.setString(1, tituloLivro);
            ResultSet rs = pstVerificaEstoque.executeQuery();

            if (rs.next()) {
                quantidadeEstoque = rs.getInt("quantidade_estoque");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar estoque: " + e.getMessage());
            return;
        }

        // Verifica se a quantidade da venda é maior que o estoque disponível
        if (quantidadeVenda > quantidadeEstoque) {
            JOptionPane.showMessageDialog(null, "A quantidade da venda é maior do que o estoque disponível (" + quantidadeEstoque + ").");
            return;
        }

        // Insere a venda
        pst = conexao.prepareStatement(sqlVenda);
        pst.setString(1, txtID.getText());
        pst.setString(2, jComboBoxNomes.getSelectedItem().toString());
        pst.setString(3, tituloLivro);
        pst.setInt(4, quantidadeVenda);
        pst.setDouble(5, Double.parseDouble(txtValor.getText()));
        pst.setString(6, jComboBoxPagamento.getSelectedItem().toString());
        pst.setDouble(7, Double.parseDouble(txtDesconto.getText()));
        pst.setDouble(8, Double.parseDouble(txtIva.getText()));
        pst.setString(9, txtTOTAL.getText());

        // Verifique se os campos obrigatórios foram preenchidos
        if (jComboBoxNomes.getSelectedItem() == null || jComboBoxLivros.getSelectedItem() == null ||
            txtQuantidade.getText().isEmpty() || txtValor.getText().isEmpty() ||
            jComboBoxPagamento.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            return;
        }

        // Execute a inserção da venda
        int adicionado = pst.executeUpdate();
        if (adicionado > 0) {
            // Atualizar quantidade em estoque
            try (PreparedStatement pstAtualizaEstoque = conexao.prepareStatement(sqlAtualizaEstoque)) {
                pstAtualizaEstoque.setInt(1, quantidadeVenda);
                pstAtualizaEstoque.setString(2, tituloLivro);

                // Execute a atualização
                int linhasAfetadas = pstAtualizaEstoque.executeUpdate();
                if (linhasAfetadas > 0) {
                    JOptionPane.showMessageDialog(null, "Estoque atualizado com sucesso!");
                     atualizarContadorVendas(jComboBoxNomes.getSelectedItem().toString());
                } else {
                    JOptionPane.showMessageDialog(null, "Estoque não atualizado. O livro pode não existir.");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao atualizar estoque: " + e.getMessage());
            }

            JOptionPane.showMessageDialog(null, "Venda realizada com sucesso!");

            // Gerar e abrir PDF após a venda
            gerarPdfVenda(
                txtID.getText(),
                jComboBoxNomes.getSelectedItem().toString(),
                tituloLivro,
                quantidadeVenda,
                Double.parseDouble(txtValor.getText()),
                jComboBoxPagamento.getSelectedItem().toString(),
                Double.parseDouble(txtDesconto.getText()),
                Double.parseDouble(txtIva.getText()),
                txtTOTAL.getText()
            );

            preencherTabelaVendas(); // Método para atualizar a tabela de vendas
            limpar(); // Método para limpar os campos
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Erro de formatação de número: " + e.getMessage());
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
    }
}

// Método para atualizar o contador de vendas e o tipo de cliente
private void atualizarContadorVendas(String nomeCliente) {
    String sql = "UPDATE clientes SET vendas = vendas + 1, tipo_cliente = CASE WHEN vendas + 1 >= 5 THEN 'VIP' ELSE 'Normal' END WHERE nome = ?";
    
    try (PreparedStatement pst = conexao.prepareStatement(sql)) {
        pst.setString(1, nomeCliente);
        pst.executeUpdate();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao atualizar vendas: " + e.getMessage());
    }
}

private void gerarPdfVenda(String id, String cliente, String livro, int quantidade, double preco, 
                           String pagamento, double desconto, double iva, String total) {
    Document document = new Document();
    String caminhoArquivo = "Venda_" + id + ".pdf";  // Nome do arquivo PDF

    try {
        PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo));
        document.open();

        // Configurações de fontes
        Font tituloFonte = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.BLACK);
        Font cabecalhoFonte = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
        Font corpoFonte = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
        Font negritoFonte = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);

        // Adicionando o título da fatura centralizado
        Paragraph titulo = new Paragraph("Fatura de Venda", tituloFonte);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20);
        document.add(titulo);

        // Adicionando o cabeçalho da fatura
        Paragraph cabecalho = new Paragraph("LIVRARIA\nEndereço: Eduardo Mondlane, Cidade de Maputo\nTelefone: +(258) 86 018 2671\n\n", cabecalhoFonte);
        cabecalho.setAlignment(Element.ALIGN_CENTER);
        document.add(cabecalho);

        // Adicionando o ID da venda e o cliente
        Paragraph detalhesVenda = new Paragraph("Detalhes da Venda", negritoFonte);
        detalhesVenda.setSpacingBefore(10);
        detalhesVenda.setSpacingAfter(10);
        document.add(detalhesVenda);

        // Tabela para organizar as informações da venda
        PdfPTable tabela = new PdfPTable(2); // 2 colunas
        tabela.setWidthPercentage(100); // Largura total da página
        tabela.setSpacingBefore(10f);
        tabela.setSpacingAfter(10f);

        // Células da tabela
        PdfPCell celula;

        // Linha 1: ID da venda
        celula = new PdfPCell(new Phrase("ID da Venda:", negritoFonte));
        tabela.addCell(celula);
        tabela.addCell(new PdfPCell(new Phrase(id, corpoFonte)));

        // Linha 2: Cliente
        celula = new PdfPCell(new Phrase("Cliente:", negritoFonte));
        tabela.addCell(celula);
        tabela.addCell(new PdfPCell(new Phrase(cliente, corpoFonte)));

        // Linha 3: Livro
        celula = new PdfPCell(new Phrase("Livro:", negritoFonte));
        tabela.addCell(celula);
        tabela.addCell(new PdfPCell(new Phrase(livro, corpoFonte)));

        // Linha 4: Quantidade
        celula = new PdfPCell(new Phrase("Quantidade:", negritoFonte));
        tabela.addCell(celula);
        tabela.addCell(new PdfPCell(new Phrase(String.valueOf(quantidade), corpoFonte)));

        // Linha 5: Preço Unitário
        celula = new PdfPCell(new Phrase("Preço Unitário:", negritoFonte));
        tabela.addCell(celula);
        tabela.addCell(new PdfPCell(new Phrase(String.format("MZN %.2f", preco), corpoFonte)));

        // Linha 6: Método de Pagamento
        celula = new PdfPCell(new Phrase("Método de Pagamento:", negritoFonte));
        tabela.addCell(celula);
        tabela.addCell(new PdfPCell(new Phrase(pagamento, corpoFonte)));

        // Linha 7: Desconto
        celula = new PdfPCell(new Phrase("Desconto:", negritoFonte));
        tabela.addCell(celula);
       tabela.addCell(new PdfPCell(new Phrase(String.format("%.2f%%", desconto), corpoFonte)));

        // Linha 8: IVA
        celula = new PdfPCell(new Phrase("IVA:", negritoFonte));
        tabela.addCell(celula);
        tabela.addCell(new PdfPCell(new Phrase(String.format("%.2f%%", iva), corpoFonte)));

        // Linha 9: Total
        celula = new PdfPCell(new Phrase("Total:", negritoFonte));
        celula.setBackgroundColor(BaseColor.LIGHT_GRAY); // Destacando o total com uma cor de fundo
        tabela.addCell(celula);
        PdfPCell totalCell = new PdfPCell(new Phrase(String.format("%s", total), negritoFonte));
        totalCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        tabela.addCell(totalCell);

        document.add(tabela);

        // Adicionando agradecimento e detalhes de rodapé
        Paragraph agradecimento = new Paragraph("Obrigado pela preferencia!\n", corpoFonte);
        agradecimento.setSpacingBefore(20);
        agradecimento.setAlignment(Element.ALIGN_CENTER);
        document.add(agradecimento);

        Paragraph rodape = new Paragraph("Volte Sempre.\nPara mais informações, entre em contato conosco.", corpoFonte);
        rodape.setAlignment(Element.ALIGN_CENTER);
        rodape.setSpacingBefore(10);
        document.add(rodape);

        // Fechar o documento
        document.close();
        JOptionPane.showMessageDialog(null, "PDF gerado com sucesso!");

        // Abrir o PDF automaticamente
        abrirPdf(caminhoArquivo);

    } catch (DocumentException | FileNotFoundException e) {
        JOptionPane.showMessageDialog(null, "Erro ao gerar PDF: " + e.getMessage());
    }
}

private void abrirPdf(String caminhoArquivo) {
    try {
        File arquivo = new File(caminhoArquivo);
        if (arquivo.exists()) {
            Desktop.getDesktop().open(arquivo); // Abre o arquivo PDF
        } else {
            JOptionPane.showMessageDialog(null, "Arquivo PDF não encontrado!");
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Erro ao abrir PDF: " + e.getMessage());
    }
}
private void preencherTabelaVendas() {
    String sql = "SELECT * FROM vendas"; // Consulta para selecionar todos os registros de vendas

    try {
        pst = conexao.prepareStatement(sql);
        rs = pst.executeQuery();

        // Obter o modelo da tabela
        DefaultTableModel modelo = (DefaultTableModel) TabelaVendas.getModel();
        modelo.setRowCount(0); // Limpar a tabela antes de preencher

        // Percorrer o ResultSet e adicionar as linhas à tabela
        while (rs.next()) {
            Object[] linha = {
                rs.getString("id"),              // ID da venda
                rs.getString("cliente"),      // Nome do cliente
                rs.getString("livro"),        // Título do livro
                rs.getInt("quantidade"),      // Quantidade vendida
                rs.getDouble("preco"),        // Preço do livro
                rs.getString("total"),    // Método de pagamento
                rs.getDouble("desconto"),     // Desconto aplicado
                rs.getDouble("iva")           // IVA aplicado
            };
            modelo.addRow(linha); // Adicionar a linha ao modelo
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao preencher tabela: " + e.getMessage());
    }
}


private void limpar() {
    // Limpar campos de texto
    txtID.setText("");       // Campo para o nome do cliente
    txtQuantidade.setText("");    // Campo para a quantidade
    txtValor.setText("");         // Campo para o preço
    txtDesconto.setText("");      // Campo para o desconto
    txtIva.setText("");           // Campo para o IVA

    // Se você tiver um JComboBox para selecionar cliente e livro
    jComboBoxNomes.setSelectedIndex(0); // Reseta o ComboBox de clientes
    jComboBoxLivros.setSelectedIndex(0);   // Reseta o ComboBox de livros

    // Se houver outros campos ou componentes que precisam ser limpos, faça isso aqui
}




 // Método para setar os campos quando uma linha da tabela é clicada
private void setar_campos() {
    // Pegar a linha selecionada
    int linha = TabelaVendas.getSelectedRow();

    // Verifica se a linha é válida (dentro do intervalo)
    if (linha >= 0 && linha < TabelaVendas.getRowCount()) {
        // Verifica se as colunas também estão dentro do intervalo correto
        if (TabelaVendas.getColumnCount() >= 6) {
            // Obtendo os valores da tabela e setando nos campos de texto, por exemplo
            txtID.setText(TabelaVendas.getModel().getValueAt(linha, 0).toString());       // ID da venda
        
        // Seleciona o nome do cliente no ComboBox
        jComboBoxNomes.setSelectedItem(TabelaVendas.getModel().getValueAt(linha, 1).toString());  // Cliente
        
        // Seleciona o nome do livro no ComboBox
        jComboBoxLivros.setSelectedItem(TabelaVendas.getModel().getValueAt(linha, 2).toString());    // Livro
        
        txtQuantidade.setText(TabelaVendas.getModel().getValueAt(linha, 3).toString());    // Quantidade de livros
        txtValor.setText(TabelaVendas.getModel().getValueAt(linha, 4).toString());         // Preço do livro

        // Seleciona o método de pagamento no ComboBox
        jComboBoxPagamento.setSelectedItem(TabelaVendas.getModel().getValueAt(linha, 5).toString()); // Pagamento
        
        txtDesconto.setText(TabelaVendas.getModel().getValueAt(linha, 6).toString());      // Desconto aplicado
        txtIva.setText(TabelaVendas.getModel().getValueAt(linha, 7).toString()); 
        } else {
            // Tratamento caso haja um problema com o número de colunas
            System.out.println("Erro: A tabela não possui colunas suficientes.");
        }
    } else {
        // Tratamento caso a linha seja inválida
        System.out.println("Erro: Linha selecionada está fora do intervalo válido.");
    }
}


 public void criarEabrirPDF(String caminhoArquivo) {
        // Criar um documento PDF
        Document document = new Document();

        try {
            // Criar o arquivo PDF
            PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo));

            // Abrir o documento para adicionar conteúdo
            document.open();

            // Verifica se uma linha da tabela foi selecionada
            int linhaSelecionada = 0;
            if ( linhaSelecionada >= 0) {
                // Obter dados da tabela
                String id = TabelaVendas.getModel().getValueAt(linhaSelecionada, 0).toString(); // ID
                String cliente = TabelaVendas.getModel().getValueAt(linhaSelecionada, 1).toString(); // Cliente
                String livro = TabelaVendas.getModel().getValueAt(linhaSelecionada, 2).toString(); // Livro
                String quantidade = TabelaVendas.getModel().getValueAt(linhaSelecionada, 3).toString(); // Quantidade
                String preco = TabelaVendas.getModel().getValueAt(linhaSelecionada, 4).toString(); // Preço
                String pagamento = TabelaVendas.getModel().getValueAt(linhaSelecionada, 5).toString(); // Pagamento
                String desconto = TabelaVendas.getModel().getValueAt(linhaSelecionada, 6).toString(); // Desconto
                String iva = TabelaVendas.getModel().getValueAt(linhaSelecionada, 7).toString(); // IVA

                // Definindo fontes
                Font fonte = new Font(Font.FontFamily.TIMES_ROMAN, 18);
                Font negrito = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

                // Adicionar conteúdo ao PDF
                document.add(new Paragraph("                           RELATÓRIO DE VENDA", negrito));
                document.add(new Paragraph("\n"));
                document.add(new Paragraph("ID da Venda: " + id, fonte));
                document.add(new Paragraph("Cliente: " + cliente, fonte));
                document.add(new Paragraph("Livro: " + livro, fonte));
                document.add(new Paragraph("Quantidade: " + quantidade, fonte));
                document.add(new Paragraph("Preço: " + preco + " MZN", fonte));
                document.add(new Paragraph("Método de Pagamento: " + pagamento, fonte));
                document.add(new Paragraph("Desconto: " + desconto + " %", fonte));
                document.add(new Paragraph("IVA: " + iva + " %", fonte));

                document.add(new Paragraph("\n"));
                document.add(new Paragraph("Nota:", fonte));
                document.add(new Paragraph("1. Obrigado pela sua compra!", fonte));
            } else {
                JOptionPane.showMessageDialog(null, "Selecione uma venda na tabela.");
                return;
            }

            // Fechar o documento
            document.close();

            System.out.println("PDF criado com sucesso!");

            // Abrir o PDF automaticamente
            abrirPDF(caminhoArquivo);

        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }

    public void abrirPDF(String caminhoArquivo) {
        try {
            // Cria um objeto File para o PDF
            File pdfFile = new File(caminhoArquivo);

            // Verifica se o Desktop é suportado
            if (Desktop.isDesktopSupported()) {
                // Usa o Desktop para abrir o PDF
                Desktop.getDesktop().open(pdfFile);
            } else {
                System.out.println("Desktop não suportado. Não foi possível abrir o PDF.");
            }
        } catch (IOException e) {
            e.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        JCadastrar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtTOTAL = new javax.swing.JTextField();
        jTextDESC = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtQuantidade = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtDesconto = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaVendas = new javax.swing.JTable();
        jComboBoxPagamento = new javax.swing.JComboBox();
        jComboBoxNomes = new javax.swing.JComboBox();
        jComboBoxLivros = new javax.swing.JComboBox();
        txtIva = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

        JCadastrar.setBackground(new java.awt.Color(51, 51, 51));
        JCadastrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        JCadastrar.setForeground(new java.awt.Color(255, 255, 255));
        JCadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/GuardarTodo.png"))); // NOI18N
        JCadastrar.setText("REALIZAR VENDA");
        JCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JCadastrarActionPerformed(evt);
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
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/essaaquiii.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(jLabel4))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(JCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 1, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(147, 147, 147)
                .addComponent(JCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97)
                .addComponent(btnSair)
                .addContainerGap(313, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(0, 204, 204));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("DESCONTO");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel5.setText("TOTAL");

        txtTOTAL.setEditable(false);
        txtTOTAL.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txtTOTAL.setForeground(new java.awt.Color(255, 0, 0));
        txtTOTAL.setText("0000  MZN");
        txtTOTAL.setBorder(null);

        jTextDESC.setEditable(false);
        jTextDESC.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextDESC.setForeground(new java.awt.Color(0, 153, 51));
        jTextDESC.setText("0,000  %");
        jTextDESC.setBorder(null);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2))
                .addGap(40, 40, 40)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTOTAL, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextDESC, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(236, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextDESC))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTOTAL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46))
        );

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        jLabel6.setText("SISTEMA DE GESTÃO DE UMA LIVRARIA");

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/log.png"))); // NOI18N

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
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(187, 187, 187)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(22, 22, 22)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(57, 57, 57)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText(" DADOS DE VENDA");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("ID");

        txtID.setEditable(false);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("LIVROS");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("CLIENTE");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("IVA");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("VALOR UNITARIO");

        txtValor.setEditable(false);
        txtValor.setText("0");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("FORMA DE PAGAMENTO");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("QUANTIDADE");

        txtDesconto.setEditable(false);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("VISUALIZACAO");

        TabelaVendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CLIENTE", "LIVRO", "QUANTIDADE", "PRECO", "VALOR TOTAL"
            }
        ));
        TabelaVendas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelaVendasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TabelaVendas);

        jComboBoxPagamento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CASH", "CARTAO ", "CARTEIRA MOVEL" }));

        txtIva.setEditable(false);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("DESCONTO");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1581, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel13))
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(75, 75, 75)
                                        .addComponent(jLabel11))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(27, 27, 27)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jComboBoxNomes, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(jComboBoxLivros, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(744, 744, 744)
                                .addComponent(jLabel12))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBoxPagamento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15))
                                .addGap(42, 42, 42)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 1581, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(284, Short.MAX_VALUE))
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
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxNomes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxLivros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(240, Short.MAX_VALUE))
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
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        cadastrarVenda();
        limpar();
   
    }//GEN-LAST:event_JCadastrarActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        // TODO add your handling code here:
             MenuPrincipal menu = new MenuPrincipal();
       menu.setVisible(true);
    }//GEN-LAST:event_btnSairActionPerformed

    private void TabelaVendasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaVendasMouseClicked
        // TODO add your handling code here:
        setar_campos();
    }//GEN-LAST:event_TabelaVendasMouseClicked

    
  
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
            java.util.logging.Logger.getLogger(Vendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Vendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Vendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Vendas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Vendas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JCadastrar;
    private javax.swing.JTable TabelaVendas;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox jComboBoxLivros;
    private javax.swing.JComboBox jComboBoxNomes;
    private javax.swing.JComboBox jComboBoxPagamento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextDESC;
    private javax.swing.JTextField txtDesconto;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtQuantidade;
    private javax.swing.JTextField txtTOTAL;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
