package loja.ui;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import loja.model.produto.*;
import loja.model.cliente.*;
import loja.model.nota.Nota;



public class ConsoleMenu {
    private List<Produto> produtosCadastrados = new ArrayList<>();
    private List<Cliente> clientesCadastrados = new ArrayList<>();
    private List<Nota> notasEmitidas = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void iniciar(){
        int opcao;
        do {
            exibirmenu();
            System.out.println("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarProduto();
                    break;
                case 2:
                    cadastrarCliente();
                    break;
                case 3:
                    alterarCliente();
                    break;
                case 4:
                    criarNota();
                    break;
                case 5:
                    listarProdutos();
                    break;
                case 6:
                    listarClientes();
                    break;
                case 7:
                    alterarProduto();
                    break;
                case 8: 
                    listarNotas();
                    break;
                case 0:
                    System.out.println("Adeus");
                    break;
                default:
                    break;
            }

        } while (opcao != 0);
    }

    public void exibirmenu(){
        System.out.println("---------MENU---------");
        System.out.println("1-Cadastrar Produto");
        System.out.println("2-Cadastrar Cliente");
        System.out.println("3-Alterar Cliente");
        System.out.println("4-Criar nota de compras");
        System.out.println("5-Listar produtos");
        System.out.println("6-Listar Clientes");
        System.out.println("7-Alterar Produto");
        System.out.println("8-Exibir notas");
        System.out.println("0-Sair"); 
        System.out.println("---------FIM----------");
    }

    public void cadastrarCliente(){

        Cliente novoCliente = null;
        //String id       = InputUtils.lerString("Digite o ID: ");
        String nome     = InputUtils.lerString("Digite o nome: ");
        String endereco = InputUtils.lerString("Digite o endereço: ");
        String telefone = InputUtils.lerString("Digite o telefone: ");
        String id = UUID.randomUUID().toString();
        
        System.out.println("Qual tipo de cliente deseja cadastrar?\n");
        System.out.println("1-Pessoa Física\n2-Pessoa Jurídica");
        int escolha = scanner.nextInt();
        scanner.nextLine();
        if(escolha == 1){
            System.out.print("Digite o CPF: ");
            String cpf = scanner.nextLine();
            novoCliente = new PessoaFisica(id, nome, endereco, telefone, cpf);
        }else if(escolha == 2){
            System.out.print("Digite o CNPJ: ");
            String cnpj = scanner.nextLine();
            novoCliente = new PessoaJuridica(id, nome, endereco, telefone, cnpj);
        }else{
            System.out.println("Escolha invalida");
            return;
        }
        this.clientesCadastrados.add(novoCliente);
        System.out.println("Cliente Cadastrado");
        System.out.println("Nome: "+ nome +" Endereço: "+endereco+" Telefone: "+ telefone);
        
    }


    public void cadastrarProduto(){
        System.out.println("Qual tipo de produto deseja cadastrar?\n");
        System.out.println("1- Produto Fisico");
        System.out.println("2- Produto Digital");
        System.out.println("3- Produto perecível");
        System.out.print("Escolha a opção: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        String nomeProduto = InputUtils.lerString("Digite o nome do produto: ");
        String codigo      = InputUtils.lerString("Digite o codigo do produto: ");
        BigDecimal preco   = InputUtils.lerPreco("Digite o valor do produto: ");
        BigDecimal estoque = InputUtils.lerPreco("Digite o estoque desse produto: ");
        Produto novoProduto = null;
        switch (tipo) {
            case 1:
                BigDecimal frete = InputUtils.lerPreco("Digite o valor do frete: ");
                novoProduto = new ProdutoFisico(codigo, nomeProduto, preco, estoque, frete);
                break;
            case 2:
                String link = InputUtils.lerString("Digite o link do produto: ");
                novoProduto = new ProdutoDigital(codigo, nomeProduto, preco, estoque, link);
                break;
            case 3:
                LocalDate data = InputUtils.lerData("Digite a data");
                novoProduto = new ProdutoPerecivel(codigo, nomeProduto, preco, estoque, data);
                break;
            default:
                System.out.println("Tipo invalido");
                break;
        }
        if(novoProduto != null){
        this.produtosCadastrados.add(novoProduto);
        System.out.println("Produto adicionado!\n\n");
        }
    }


    public void listarClientes(){
        System.out.println("\n==========-Clientes cadastrados-==========\n");
        if(clientesCadastrados.isEmpty()){
            System.out.println("Nenhum cliente cadastrado");
        }else {
            int i=1;
            for(Cliente cliente : clientesCadastrados){
                System.out.println("\n\nCliente "+ i+" -===================");
                System.out.println("ID: " + cliente.getId());
                System.out.println("Nome: " + cliente.getNome());
                System.out.println("Endereço: " + cliente.getEndereco());
                System.out.println("Telefone: " + cliente.getTelefone());
                i++;
                if(cliente instanceof PessoaFisica pf){
                    System.out.println("CPF: "+ pf.getCPF());
                }else if(cliente instanceof PessoaJuridica pj){
                    System.out.println("CNPJ: " + pj.getCNPJ());
                }
            }
        }
        System.out.println("\n==========-FIM DOS CLIENTES-==========\n");
        
    }

    public void listarProdutos(){
        System.out.println("\n==========-Produtos cadastrados-==========\n");
        if(produtosCadastrados.isEmpty()){
            System.out.println("Nenhum produto cadastrado");
        }else{
            DecimalFormat formatoMoeda = new DecimalFormat("R$ #,##0.00");
            int i =1;
            for(Produto produto : produtosCadastrados){
                System.out.println("Produto "+ i+" -==================");
                System.out.println("Nome: " + produto.getNome() );
                System.out.println("Codigo: " + produto.getCodigo());
                System.out.println("Preço base: "+ formatoMoeda.format(produto.getPreco()));
                System.out.println("Estoque: "+produto.getEstoque());
                i++;
                if(produto instanceof ProdutoFisico pf){
                    System.out.println("Frente; " + formatoMoeda.format(pf.getFrete()));
                }else if(produto instanceof ProdutoDigital pd){
                    System.out.println("Link: " + pd.getLinkDoProduto());
                }else if(produto instanceof ProdutoPerecivel pp){
                    DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    System.out.println("Validade: "+ pp.getDataDeValidade().format(formatadorData));
                }
            }
        }
        
    }
    public void alterarCliente(){
        System.out.println("Alteração de dados do cliente");
        if(clientesCadastrados.isEmpty()){
            System.out.println("Não há clientes para alterar");
            return;  
        }
        listarClientes();
        System.out.println("Digite o número do cliente: ");
        int escCliente = scanner.nextInt()-1;
        if(escCliente < 0 || escCliente >= clientesCadastrados.size()){
            System.out.println("ERRO SELEÇÃO INVÁLIDA");
            return;
        }
        Cliente clienteSelecionado = clientesCadastrados.get(escCliente);

        System.out.println("O que deseja altera?");
        System.out.println("1- Nome");
        System.out.println("2- Endereço");
        System.out.println("3- Telefone");
        System.out.print("Escolha uma opção: ");
        int opcaoC = scanner.nextInt();
        scanner.nextLine();
        switch (opcaoC) {
            case 1:
                String novoNome = InputUtils.lerString("Digite o novo nome: ");
                clienteSelecionado.setNome(novoNome);
                break;
            case 2:
                String novoEnd = InputUtils.lerString("Digite o novo Endereço: ");
                clienteSelecionado.setEndereco(novoEnd);
                break;
            case 3:
                String novoTel = InputUtils.lerString("Digite o novo telefone: ");
                clienteSelecionado.setTelefone(novoTel);
                break;
        
            default:
            System.out.println("Opção inválida");
            return;            
                
        }
        System.out.println("Cliente alterado com sucesso!");
        InputUtils.pausarParaContinuar();
    }

    public void criarNota(){
        System.out.println("Criando nota de compra");
        if(clientesCadastrados.isEmpty() || produtosCadastrados.isEmpty()){
            System.out.println("É necessario ter pelo menos um cliente e um produto");
            return;
        }
        listarClientes();
        
        System.out.print("Digite o número do cliente: ");
        int indCliente = scanner.nextInt() - 1;
        scanner.nextLine();
        if(indCliente < 0 || indCliente >= clientesCadastrados.size()){
            System.out.println("Erro de seleção");
            return;
        }
        Cliente clienteDaNota = clientesCadastrados.get(indCliente);
        Nota novaNota = new Nota(clienteDaNota);
        String finaliza = "";
        do {
            System.out.println("Adicionando a nota");
            listarProdutos();

            System.out.print("Digite o numero do produto que deseja adcionar: ");
            int indProduto = scanner.nextInt() - 1;
            scanner.nextLine();
            if(indProduto < 0 || indProduto >= produtosCadastrados.size()){
                System.out.println("Seleção invalida");
                continue;
            }
            Produto produtoDoCliente = produtosCadastrados.get(indProduto);

            System.out.println("Digite a quantidade: ");
            int quant = scanner.nextInt();
            scanner.nextLine();
            try {
                novaNota.adicionarItem(produtoDoCliente, quant);
                System.out.println("Produto adicinado");
            } catch (IllegalArgumentException e) {
                System.out.println("Erro ao adiconar item " + e.getMessage());
            }
            System.out.println("Deseja adicionar outro produto? (S/N)");
            finaliza = scanner.nextLine();

        } while (finaliza.equalsIgnoreCase("S"));

        this.notasEmitidas.add(novaNota);
        System.out.println("Nota criada com sucesso");

        novaNota.exibirResumo();
        InputUtils.pausarParaContinuar();
    }
    public void alterarProduto(){
        System.out.println("----------Alteração de produto----------");
        if(produtosCadastrados.isEmpty()){
            System.out.println("Não há produtos para alterar");
            return;
        }
        listarProdutos();
        InputUtils.pausarParaContinuar();
        System.out.print("Digite o número do produto que deseja alterar: ");
        int escolha = scanner.nextInt() -1;
        if(escolha < 0 || escolha >= produtosCadastrados.size()){
            System.out.println("Erro de seleção");
            return;
        }
        Produto produtoselecionado = produtosCadastrados.get(escolha);

        System.out.println("O que deseja alterar?");
        System.out.println("1-Codigo");
        System.out.println("2-Nome");
        System.out.println("3-Preço");
        System.out.println("4-Estoque");
        if(produtoselecionado instanceof ProdutoFisico pf){
            System.out.println("5-Alterar Frete");
        }else if(produtoselecionado instanceof ProdutoDigital pd){
            System.out.println("5-Alterar link");
        }else if(produtoselecionado instanceof ProdutoPerecivel pp){
            System.out.println("5-Alterar validade");
        }
        System.out.print("Digite o número: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                String novoCodigo = InputUtils.lerString("Digite o novo codigo: ");
                produtoselecionado.setCodigo(novoCodigo);
                break;
            case 2:
                
                String novoNome = InputUtils.lerString("Digite o novo nome: ");
                produtoselecionado.setNome(novoNome);
                break;
            case 3:
                BigDecimal novoPreco = InputUtils.lerPreco("Digite o novo preço: ");
                produtoselecionado.setPreco(novoPreco);
                break;
            case 4:
                BigDecimal novoEstoque = InputUtils.lerPreco("Digite o novo estoque: ");
                produtoselecionado.setEstoque(novoEstoque);
                break;
            case 5:
                if(produtoselecionado instanceof ProdutoFisico pf){
                BigDecimal novoFrete = InputUtils.lerPreco("Digite o valor do novo Frete: ");
                pf.setFrete(novoFrete); 
                }else if(produtoselecionado instanceof ProdutoDigital pd){
                String novoLink = InputUtils.lerString("Digite o novo link: ");
                pd.setLinkDoProduto(novoLink);
                }else if(produtoselecionado instanceof ProdutoPerecivel pp){
                LocalDate novaData = InputUtils.lerData("Digite a nova data: ");
                pp.setdataDeValidade(novaData);
                }
                break;
            default:
                System.out.println("Opção invalida");
                break;
        }
        System.out.println("Produto alterado com sucesso!");
        InputUtils.pausarParaContinuar();
    }
    public void listarNotas(){
        System.out.println("----------NOTAS EMITIDAS----------");
        if(notasEmitidas.isEmpty()){
            System.out.println("Nenhuma nota foi emitida ainda");
        }else{
            for(Nota nota: notasEmitidas){
                nota.exibirResumo();
            }
        }
        InputUtils.pausarParaContinuar();
    }
}
   
