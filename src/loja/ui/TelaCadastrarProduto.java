package loja.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import loja.model.produto.*;

public class TelaCadastrarProduto {
    private List<Produto> produtos;

    public TelaCadastrarProduto(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Cadastrar Novo Produto");
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // --- Componentes Comuns ---
        Label nomeLabel = new Label("Nome:");
        TextField nomeInput = new TextField();
        Label codigoLabel = new Label("Código:");
        TextField codigoInput = new TextField();
        Label precoLabel = new Label("Preço:");
        TextField precoInput = new TextField();
        Label estoqueLabel = new Label("Estoque:");
        TextField estoqueInput = new TextField();

        // --- Seleção de Tipo ---
        ToggleGroup tipoProdutoGroup = new ToggleGroup();
        RadioButton rbFisico = new RadioButton("Produto Físico");
        rbFisico.setToggleGroup(tipoProdutoGroup);
        rbFisico.setSelected(true);
        RadioButton rbDigital = new RadioButton("Produto Digital");
        rbDigital.setToggleGroup(tipoProdutoGroup);
        RadioButton rbPerecivel = new RadioButton("Produto Perecível");
        rbPerecivel.setToggleGroup(tipoProdutoGroup);

        // --- Componentes Específicos ---
        Label freteLabel = new Label("Frete:");
        TextField freteInput = new TextField();
        Label linkLabel = new Label("Link:");
        TextField linkInput = new TextField();
        linkLabel.setVisible(false);
        freteInput.setVisible(true);
        Label validadeLabel = new Label("Validade (dd/MM/yyyy):");
        TextField validadeInput = new TextField();
        validadeLabel.setVisible(false);
        validadeInput.setVisible(false);
        

        tipoProdutoGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            freteLabel.setVisible(false);
            freteInput.setVisible(false);
            linkLabel.setVisible(false);
            linkInput.setVisible(false);
            validadeLabel.setVisible(false);
            validadeInput.setVisible(false);

            if (newToggle == rbFisico) {
                freteLabel.setVisible(true);
                freteInput.setVisible(true);
            } else if (newToggle == rbDigital) {
                linkLabel.setVisible(true);
                linkInput.setVisible(true);
            } else if (newToggle == rbPerecivel) {
                validadeLabel.setVisible(true);
                validadeInput.setVisible(true);
            }
        });

        
        grid.addRow(0, nomeLabel, nomeInput);
        grid.addRow(1, codigoLabel, codigoInput);
        grid.addRow(2, precoLabel, precoInput);
        grid.addRow(3, estoqueLabel, estoqueInput);
        grid.addRow(4, rbFisico, rbDigital, rbPerecivel);
        // Campos específicos na mesma linha para alternar
        grid.addRow(5, freteLabel, freteInput);
        grid.addRow(5, linkLabel, linkInput);
        grid.addRow(5, validadeLabel, validadeInput);

        // --- Botão Salvar ---
        Button salvarButton = new Button("Salvar Produto");
        grid.add(salvarButton, 1, 6);

        grid.getChildren().addAll(); 
        
        
        salvarButton.setOnAction(e -> {
            try {
                String nome = nomeInput.getText();
                String codigo = codigoInput.getText();
                BigDecimal preco = new BigDecimal(precoInput.getText());
                BigDecimal estoque = new BigDecimal(estoqueInput.getText());
                
                Produto novoProduto = null;

                if (rbFisico.isSelected()) {
                    BigDecimal frete = new BigDecimal(freteInput.getText());
                    novoProduto = new ProdutoFisico(codigo, nome, preco, estoque, frete);
                } else if (rbDigital.isSelected()) {
                    String link = linkInput.getText();
                    novoProduto = new ProdutoDigital(codigo, nome, preco, estoque, link);
                } else if (rbPerecivel.isSelected()) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate data = LocalDate.parse(validadeInput.getText(), formatter);
                    novoProduto = new ProdutoPerecivel(codigo, nome, preco, estoque, data);
                }

                if (novoProduto != null) {
                    this.produtos.add(novoProduto);
                    System.out.println("Produto salvo: " + novoProduto.getNome());
                    window.close();
                }

            } catch (NumberFormatException ex) {
                System.out.println("Erro: Preço, estoque e frete devem ser números válidos.");
            
            } catch (Exception ex) {
                System.out.println("Ocorreu um erro ao salvar: " + ex.getMessage());
            }
        });

        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.showAndWait();
    }
}