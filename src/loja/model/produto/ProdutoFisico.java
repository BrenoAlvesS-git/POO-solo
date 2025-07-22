package loja.model.produto;
import java.math.BigDecimal;

public class ProdutoFisico extends Produto {
    private BigDecimal frete;

    public ProdutoFisico(String codigo, String nome, BigDecimal preco, BigDecimal estoque, BigDecimal frete) {
        super(codigo,nome,preco,estoque);
        this.frete = frete;
    }

    //metodos get e set
    public BigDecimal getFrete(){
        return frete;
    }

    public void setFrete(BigDecimal frete){
        this.frete = frete;
    }

    //funcao de adicionar frete ao valor do produto
    @Override
    public BigDecimal calcularPrecoFinal(){ //do produto //add para somar BigDecimal
        BigDecimal valorfrete = getFrete();
        return super.getPreco().add(valorfrete); //aqui eu peguei do produto o valor dele e somei com o valor do frete
    }

}