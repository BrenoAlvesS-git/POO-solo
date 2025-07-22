package loja.model.nota;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.*; // Mudei para LocalDateTime
import java.time.format.DateTimeFormatter;
import java.util.*;
import loja.model.cliente.Cliente;
import loja.model.produto.Produto;


   public class Nota {
       private String id;
       private LocalDateTime dataDeEmissao;
       private Cliente cliente;
       private List<ItemNota> itens;


       //construtor, uma nota em branco, pois eu preciso gerar alkgumas coisas, o unico dai que nao vai mudar vai ser o cliente, por isso eu so vou passar o cliente comoo parametro
       public Nota(Cliente cliente) {

           // Validação para garantir que o cliente não é nulo
           if (cliente == null) {
               throw new IllegalArgumentException("O cliente não pode ser nulo.");
           }

           this.cliente = cliente;
           this.itens = new ArrayList<>(); //criando a lista vazia de itens
           this.id  = UUID.randomUUID().toString(); //gerando ID unica
           this.dataDeEmissao = LocalDateTime.now(); //hora da emissao da nota

       }

       //adicionar o iem
       public void adicionarItem(Produto produto, int quantidade){
           //verificar se a quantidade naop e nula
           BigDecimal quantidadeEmBigDecimal = BigDecimal.valueOf(quantidade);
           if(produto.getEstoque().compareTo(quantidadeEmBigDecimal) < 0){
               throw new IllegalArgumentException("A quantidade pedida e maior do que o estoque tem.");
           }
          ItemNota novoItem = new ItemNota(produto,quantidade);
           //adiciono os itens na lista vazia
           this.itens.add(novoItem);
           //atualizo o estoque
           produto.setEstoque(produto.getEstoque().subtract(quantidadeEmBigDecimal));
       }


       //calcular o subtota, em BigDecimal
       public BigDecimal calcularSubTotal(){
           BigDecimal subTotal = BigDecimal.ZERO; //BASICAMENTE ESTOU FAZENDO COM QUE A VARIAVEL RECEBA ZERO SO QUE EM BIGDECIMAL
           //vou fazer um for passando por todos os valores da lista e soma-los
           for(ItemNota itemNota : this.itens){ //for each
               subTotal = subTotal.add(itemNota.getValorDoItem());//pega o valor de cada item e soma
           }
           return subTotal;
       }

       //calcular o valor total a partir de cadad "particuliradidade"
       public BigDecimal calcularValorTotal(){
           BigDecimal total = BigDecimal.ZERO;
           for(ItemNota item : this.itens){
               //se for produto digital retorna sem frete, se for fisico retorna o frete
               BigDecimal precoFinalUnitario = item.getProduto().calcularPrecoFinal();

               //converte de int para big
               BigDecimal quantidade = BigDecimal.valueOf(item.getQuantidade());

               //valor total para esse item
               BigDecimal valorTotalDoItem = precoFinalUnitario.multiply(quantidade);

               //adiciona o valor desse item ao geral da nota
               total = total.add(valorTotalDoItem);
           }
           return total;
       }




       //getters
       public String getId() {
           return id;
       }

       public LocalDateTime getDataDeEmissao() {
           return dataDeEmissao;
       }

       public List<ItemNota> getItens() {
           return itens;
       }

       //exibir a nota

       public void exibirResumo(){
           // Cabeçalho da Nota
           System.out.println("==============================================");
           System.out.println("               NOTA DE COMPRA                 ");
           System.out.println("==============================================");
           System.out.println("ID da Nota: " + this.getId());

           // Dados do Cliente
           System.out.println("Cliente: " + this.cliente.getNome());
           System.out.println("Endereço: " + this.cliente.getEndereco());
           System.out.println("----------------------------------------------");

           // Itens da Compra
           System.out.println("ITENS DA COMPRA:");
           if (this.itens.isEmpty()) {  //verificando  se existe item
               System.out.println("Nenhum item na nota.");
           } else {
               for (ItemNota item : this.itens) {
                   String linhaDoItem = String.format(
                           "Produto: %-20s | Qtd: %d | Preço Unit.: R$ %.2f | Total Item: R$ %.2f",
                           item.getProduto().getNome(),
                           item.getQuantidade(),
                           item.getProduto().calcularPrecoFinal(),
                           item.getValorDoItem()
                   );
                   System.out.println(linhaDoItem);
               }
           }
           System.out.println("----------------------------------------------");


           DecimalFormat formatadorMoeda = new DecimalFormat("R$ #,##0.00");

           System.out.println("Subtotal: " + formatadorMoeda.format(this.calcularSubTotal()));
           System.out.println("Valor Total: " + formatadorMoeda.format(this.calcularValorTotal()));
           System.out.println("----------------------------------------------");

         //para formatar a data
           DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
           System.out.println("Data de Emissão: " + this.getDataDeEmissao().format(formatadorData));
           System.out.println("==============================================");

       }

   }
