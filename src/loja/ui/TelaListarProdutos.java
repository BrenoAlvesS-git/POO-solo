package loja.ui;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import loja.model.produto.*;;


public class TelaListarProdutos {
    public void display(List<Produto> produtos){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Lista de Produtos cadastrados");
        window.setMinWidth(600);

        TableView<Produto> tabela = new TableView<>();

        TableColumn<Produto, String> colunaCodigo = new TableColumn<>("Código");
        colunaCodigo.setMinWidth(150);
        //pra pegar o valor do nome
        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
                TableColumn<Produto, String> colunaNome = new TableColumn<>("nome");
        colunaNome.setMinWidth(200);
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Produto, String> colunaPreco = new TableColumn<>("Preco");
        colunaPreco.setMinWidth(100);
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        TableColumn<Produto, String> colunaEstoque = new TableColumn<>("Estoque");
        colunaEstoque.setMinWidth(50);
        colunaEstoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));

        // 4. Cria uma coluna especial para CPF/CNPJ
        TableColumn<Produto, String> colunaTipo = new TableColumn<>("Detalhe especifico");
        colunaTipo.setMinWidth(150);
        colunaTipo.setCellValueFactory(cellData->{
            Produto produto = cellData.getValue();
            String detalhe = "";
            if(produto instanceof ProdutoFisico pf){
                detalhe = String.format("Frete: R$ %.2f", pf.getFrete());
            }else if(produto instanceof ProdutoDigital pd){
                detalhe = "Link: " + pd.getLinkDoProduto();
            }else if(produto instanceof ProdutoPerecivel pp){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                detalhe = "Validade" + pp.getDataDeValidade().format(formatter);
            }
            return new SimpleStringProperty(detalhe);
        });

        //adicionar os dados na tabela
        tabela.setItems(FXCollections.observableArrayList(produtos));
        tabela.getColumns().addAll(colunaCodigo,colunaNome,colunaPreco,colunaEstoque,colunaTipo);
        VBox layout = new VBox(tabela);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }
}
