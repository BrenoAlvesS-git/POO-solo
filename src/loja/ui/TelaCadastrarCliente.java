package loja.ui;
import java.util.List;
import java.util.UUID;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import loja.model.cliente.Cliente;
import loja.model.cliente.PessoaFisica;
import loja.model.cliente.PessoaJuridica;

public class TelaCadastrarCliente {
    private List<Cliente> clientes;

    public TelaCadastrarCliente(List<Cliente> clientes){
        this.clientes = clientes;
    }


    public void display(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Cadastrar novo Cliente");
        //Criar layout em grade
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);
        //Criar o formulario

        Label nomeLabel = new Label("Nome: ");
        TextField nomeInput = new TextField();
        nomeInput.setPromptText("Nome Completo");

        Label enderecoLabel = new Label("Endereço: ");
        TextField enderecoInput = new TextField();
        enderecoInput.setPromptText("Endereço");

        Label telefoneLabel = new Label("Telefone: ");
        TextField telefoneInput = new TextField();
        telefoneInput.setPromptText("Telefone");

        //Seleção de tipo
        ToggleGroup tipoClientGroup = new ToggleGroup();
        RadioButton pfRadio = new RadioButton("Pessoa Física");
        pfRadio.setToggleGroup(tipoClientGroup);
        pfRadio.setSelected(true);

        RadioButton pjRadio = new RadioButton("Pessoa Jurídica");
        pjRadio.setToggleGroup(tipoClientGroup);

        //campos cpf/cnpj
        Label cpfLabel = new Label("CPF:");
        TextField cpfInput = new TextField();

        Label cnpjLabel = new Label("CNPJ:");
        TextField cnpjInput = new TextField();
        cnpjInput.setVisible(false);

        tipoClientGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) ->{
            cpfInput.setVisible(false);
            cnpjInput.setVisible(false);

            if (newToggle == pfRadio){
                cpfInput.setVisible(true);
                //cnpjInput.setVisible(false);
            }else if(newToggle == pjRadio){
                //cpfInput.setVisible(false);
                cnpjInput.setVisible(true);
            }
        });

        //botão pra salvar

        Button salvarButton = new Button("Salvar Cliente");
        GridPane.setConstraints(nomeLabel, 0, 0);
        GridPane.setConstraints(nomeInput, 1, 0);
        GridPane.setConstraints(enderecoLabel, 0, 1);
        GridPane.setConstraints(enderecoInput, 1, 1);
        GridPane.setConstraints(telefoneLabel, 0, 2);
        GridPane.setConstraints(telefoneInput, 1, 2);
        GridPane.setConstraints(pfRadio, 0, 3);
        GridPane.setConstraints(pjRadio, 1, 3);
        GridPane.setConstraints(cpfLabel, 0, 4);
        GridPane.setConstraints(cpfInput, 1, 4);
        GridPane.setConstraints(cnpjLabel, 0, 4); 
        GridPane.setConstraints(cnpjInput, 1, 4); 
        GridPane.setConstraints(salvarButton, 1, 5);

        grid.getChildren().addAll(nomeLabel,nomeInput, enderecoLabel,enderecoInput,telefoneLabel,telefoneInput,
                                pfRadio,pjRadio,cpfLabel,cpfInput,cnpjLabel,cnpjInput,salvarButton);
        
        salvarButton.setOnAction(e->{
            String id = UUID.randomUUID().toString();
            String nome = nomeInput.getText();
            String endereco = enderecoInput.getText();
            String telefone = telefoneInput.getText();

            Cliente novoCliente;
            if(pfRadio.isSelected()){
                String cpf = cpfInput.getText();
                novoCliente = new PessoaFisica(id,nome,endereco,telefone,cpf);
            }else{
                String cnpj = cnpjInput.getText();
                novoCliente = new PessoaJuridica(id, nome, endereco, telefone, cnpj);
            }

            this.clientes.add(novoCliente);
            System.out.println("Cliente salvo: "+ novoCliente.getNome());


        });           
        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.showAndWait();
        window.close();
    }

}
