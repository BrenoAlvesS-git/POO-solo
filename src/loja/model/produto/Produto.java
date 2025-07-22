package loja.model.produto;
import java.math.BigDecimal;


    public class Produto {

        private String codigo; //unico
        private String nome;
        private BigDecimal preco;
        private BigDecimal estoque;

        public Produto(String codigo, String nome, BigDecimal preco, BigDecimal estoque) {
            this.codigo = codigo;
            this.nome = nome;
            this.preco = preco;
            this.estoque = estoque;
        }

//metodos get e set

        public String getCodigo(){
            return codigo;
        }

        public String getNome() {
            return nome;
        }

        public BigDecimal getPreco(){
            return preco;
        }

        public BigDecimal getEstoque() {
            return estoque;
        }

        //sets atualizam
        public void setCodigo(String codigo){ //
            if(codigo==null||codigo.trim().isEmpty()){
                throw new IllegalArgumentException("O codigo do produto não pode ser nulo ou vazio.");
            }
            this.codigo = codigo;
            
        }

        public void setNome(String nome){ //prevenir strings vazias
            if(nome == null|| nome.trim().isEmpty()){
                throw new IllegalArgumentException("O nome do produto não pode ser nulo ou vazio."); //sinaliza que houve erro
            }
            this.nome = nome;
        }

        public void setPreco(BigDecimal preco){
            if (preco.compareTo(BigDecimal.ZERO) < 0){
                throw new IllegalArgumentException("o preco nao pode ser negativo");
            }
            this.preco = preco;
        }

        public void setEstoque(BigDecimal estoque){
            this.estoque = estoque;
        }


        //metodo de calcular o preco final
        public BigDecimal calcularPrecoFinal(){ //do produto
            return this.getPreco();
        }

        //metodo para listar produtos


    }


