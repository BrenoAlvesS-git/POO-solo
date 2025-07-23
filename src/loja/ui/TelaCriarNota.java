package loja.ui;

import java.math.BigDecimal;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import loja.model.cliente.Cliente;
import loja.model.nota.ItemNota;
import loja.model.nota.Nota;
import loja.model.produto.Produto;

public class TelaCriarNota {

    private List<Cliente> clientes;
    private List<Produto> produtos;
    private List<Nota> notas;

    public TelaCriarNota(List<Cliente> clientes, List<Produto> produtos, List<Nota> notas) {
        this.clientes = clientes;
        this.produtos = produtos;
        this.notas = notas;
    }

    public void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Criar Nova Nota de Compra");

        VBox layoutPrincipal = new VBox(15);
        layoutPrincipal.setPadding(new Insets(15));

        Label clienteLabel = new Label("Selecione o Cliente:");
        ComboBox<Cliente> clienteComboBox = new ComboBox<>();
        clienteComboBox.setItems(FXCollections.observableArrayList(clientes));

        // --- Seção para Adicionar Itens
        HBox secaoAdicionarItem = new HBox(10);
        ComboBox<Produto> produtoComboBox = new ComboBox<>();
        produtoComboBox.setItems(FXCollections.observableArrayList(produtos));
        produtoComboBox.setPromptText("Selecione um Produto");
        
        Spinner<Integer> quantidadeSpinner = new Spinner<>(1, 100, 1); // Quantidade de 1 a 100
        quantidadeSpinner.setEditable(true);
        
        Button btnAdicionarItem = new Button("Adicionar Item");
        secaoAdicionarItem.getChildren().addAll(new Label("Produto:"), produtoComboBox, new Label("Qtd:"), quantidadeSpinner, btnAdicionarItem);

        // --- Tabela para exibir os itens adicionados 
        TableView<ItemNota> tabelaItens = new TableView<>();
        TableColumn<ItemNota, String> colunaProdutoNome = new TableColumn<>("Produto");
        colunaProdutoNome.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProduto().getNome()));
        
        TableColumn<ItemNota, Integer> colunaQuantidade = new TableColumn<>("Quantidade");
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        
        TableColumn<ItemNota, BigDecimal> colunaValorItem = new TableColumn<>("Valor do Item");
        colunaValorItem.setCellValueFactory(new PropertyValueFactory<>("valorDoItem"));
        
        tabelaItens.getColumns().addAll(colunaProdutoNome, colunaQuantidade, colunaValorItem);

        // --- Rótulos para Subtotal e Total 
        Label subtotalLabel = new Label("Subtotal: R$ 0.00");
        Label totalLabel = new Label("Total: R$ 0.00");

        // --- Botão para Salvar a Nota
        Button btnSalvarNota = new Button("Salvar Nota");
        btnSalvarNota.setDisable(true); // Começa desabilitado

        
        final Nota[] notaAtual = {null}; // Usamos um array para poder modificar dentro da lambda

        // Ação quando um cliente é selecionado
        clienteComboBox.setOnAction(e -> {
            Cliente clienteSelecionado = clienteComboBox.getValue();
            if (clienteSelecionado != null) {
                notaAtual[0] = new Nota(clienteSelecionado);
                tabelaItens.setItems(FXCollections.observableArrayList(notaAtual[0].getItens()));
                btnSalvarNota.setDisable(false); 
            }
        });

        // Ação para o botão "Adicionar Item"
        btnAdicionarItem.setOnAction(e -> {
            Produto produtoSelecionado = produtoComboBox.getValue();
            int quantidade = quantidadeSpinner.getValue();

            if (produtoSelecionado != null && notaAtual[0] != null) {
                try {
                    notaAtual[0].adicionarItem(produtoSelecionado, quantidade);
                    // Atualiza a tabela e os totais
                    tabelaItens.setItems(FXCollections.observableArrayList(notaAtual[0].getItens()));
                    subtotalLabel.setText(String.format("Subtotal: R$ %.2f", notaAtual[0].calcularSubTotal()));
                    totalLabel.setText(String.format("Total: R$ %.2f", notaAtual[0].calcularValorTotal()));
                } catch (IllegalArgumentException ex) {
                    // Mostra um alerta de erro para o usuário
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro de Estoque");
                    alert.setHeaderText(null);
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                }
            }
        });

        // Ação para o botão "Salvar Nota"
        btnSalvarNota.setOnAction(e -> {
            if (notaAtual[0] != null) {
                this.notas.add(notaAtual[0]);
                System.out.println("Nota salva com sucesso! ID: " + notaAtual[0].getId());
                window.close();
            }
        });
        
        // --- Montagem Final da Tela ---
        layoutPrincipal.getChildren().addAll(clienteLabel, clienteComboBox, new Separator(), secaoAdicionarItem, tabelaItens, subtotalLabel, totalLabel, new Separator(), btnSalvarNota);
        
        Scene scene = new Scene(layoutPrincipal, 600, 500);
        window.setScene(scene);
        window.showAndWait();
    }
}