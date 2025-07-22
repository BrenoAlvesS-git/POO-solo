package loja;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import loja.model.cliente.*;
import loja.ui.TelaCadastrarCliente;
import loja.ui.TelaListarClientes;


public class MainFX extends Application{  
    private List<Cliente> clientesCadastrados = new ArrayList<>();

    private void criarDados(){
        clientesCadastrados.add(new PessoaFisica("1","Ana","Rua A","111234","111.222.333.444-55"));
    }
    public void start(Stage primaryStage){
        criarDados();
        VBox layoutPrincipal = new VBox();
        //Layout principal 
        layoutPrincipal.setSpacing(10);
        layoutPrincipal.setPadding(new Insets(15));
        layoutPrincipal.setAlignment(Pos.CENTER);
        //Cria os botões para o "swich"
        Button btnCadastrarCliente = new Button("Cadastrar Cliente");
        Button btnCadastrarProduto = new Button("Cadastrar Produto");
        Button btnListarClientes = new Button("Listar Clientes");
        Button btnListarProdutos = new Button("Listar Produtos");
        Button btnCriarNota = new Button("Criar Nota");
        //definir a forma dos botões pra ser uniformes
        double larguraBotao = 200;
        btnCadastrarCliente.setPrefWidth(larguraBotao);
        btnCadastrarProduto.setPrefWidth(larguraBotao);
        btnListarClientes.setPrefWidth(larguraBotao);
        btnListarProdutos.setPrefWidth(larguraBotao);
        btnCriarNota.setPrefWidth(larguraBotao);
        //definir as ações dos botões
        btnCadastrarCliente.setOnAction(e -> {
            TelaCadastrarCliente telaCadastro = new TelaCadastrarCliente(clientesCadastrados);
            telaCadastro.display();
        });
        btnCadastrarProduto.setOnAction(e -> {
            System.out.println("Cadastrar Produto");
        });
        btnListarClientes.setOnAction(e -> {
            TelaListarClientes tela = new TelaListarClientes();
            tela.display(clientesCadastrados);
        });
        btnListarProdutos.setOnAction(e -> {
            System.out.println("Listar produtos");
        });
        btnCriarNota.setOnAction(e -> {
            System.out.println("Criar notas");
        });
        //Adicionando botões no layout
        layoutPrincipal.getChildren().addAll(
            btnCadastrarCliente,
            btnCadastrarProduto,
            btnListarClientes,
            btnListarProdutos,
            btnCriarNota
        );
        
        //Configurando a janela
        Scene scene = new Scene(layoutPrincipal, 400, 350);
        primaryStage.setTitle("Sistema da loja");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);

    }

}