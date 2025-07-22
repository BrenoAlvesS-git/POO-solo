package loja.ui;

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
import loja.model.cliente.*;


public class TelaListarClientes {
    public void display(List<Cliente> clientes){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Lista de clientes cadastrados");
        window.setMinWidth(600);

        TableView<Cliente> tabela = new TableView<>();

        TableColumn<Cliente, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setMinWidth(150);
        //pra pegar o valor do nome
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
                TableColumn<Cliente, String> colunaEndereco = new TableColumn<>("Endereço");
        colunaEndereco.setMinWidth(200);
        colunaEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));

        TableColumn<Cliente, String> colunaTelefone = new TableColumn<>("Telefone");
        colunaTelefone.setMinWidth(100);
        colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        // 4. Cria uma coluna especial para CPF/CNPJ
        TableColumn<Cliente, String> colunaDocumento = new TableColumn<>("CPF/CNPJ");
        colunaDocumento.setMinWidth(150);
        colunaDocumento.setCellValueFactory(cellData->{
            Cliente cliente = cellData.getValue();
            String documento = "";
            if(cliente instanceof PessoaFisica pf){
                documento = pf.getCPF();
            }else if(cliente instanceof PessoaJuridica pj){
                documento = pj.getCNPJ();
            }
            return new SimpleStringProperty(documento);
        });

        //adicionar os dados na tabela
        tabela.setItems(FXCollections.observableArrayList(clientes));
        tabela.getColumns().addAll(colunaNome,colunaEndereco,colunaTelefone,colunaDocumento);
         VBox layout = new VBox(tabela);
         Scene scene = new Scene(layout);
         window.setScene(scene);
         window.showAndWait();

    }
}
