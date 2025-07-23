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
import loja.model.produto.Produto;
import loja.model.produto.ProdutoFisico;
import loja.ui.TelaCadastrarCliente;
import loja.ui.TelaCadastrarProduto;
import loja.ui.TelaCriarNota;
import loja.ui.TelaListarClientes;
import loja.model.nota.Nota;
import loja.ui.TelaListarProdutos;


public class MainFX extends Application{  
    private List<Cliente> clientesCadastrados = new ArrayList<>();
    private List<Produto> produtosCadastrados = new ArrayList<>();
    private List<Nota> notasCadastradas = new ArrayList<>();
    private void criarDados(){
        clientesCadastrados.add(new PessoaFisica("1","Exemplo","Rua A","111234","111.222.333.444-55"));
        //produtosCadastrados.add(new ProdutoFisico("1","Exemplo", 00 , 0));
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
            TelaCadastrarProduto telaCadastro = new TelaCadastrarProduto(produtosCadastrados);
            telaCadastro.display();
        });
        btnListarClientes.setOnAction(e -> {
            TelaListarClientes tela = new TelaListarClientes();
            tela.display(clientesCadastrados);
        });
        btnListarProdutos.setOnAction(e -> {
            TelaListarProdutos telaProdutos = new TelaListarProdutos();
            telaProdutos.display(produtosCadastrados);
        });
        btnCriarNota.setOnAction(e -> {
        // Cria a instância da tela, passando todas as listas necessárias
        TelaCriarNota tela = new TelaCriarNota(clientesCadastrados, produtosCadastrados, notasCadastradas);
        tela.display(); 
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