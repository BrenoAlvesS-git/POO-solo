package loja.model.produto;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ProdutoPerecivel extends Produto {
    private LocalDate dataDeValidade; //diferenca


    //criei o construtor
    public ProdutoPerecivel(String codigo, String nome, BigDecimal preco, BigDecimal estoque, LocalDate dataDeValidade) {
        super(codigo, nome, preco, estoque);
        this.dataDeValidade =  dataDeValidade;
    }

    //get e set
    public LocalDate getDataDeValidade() {
        return dataDeValidade;
    }

    public void setdataDeValidade(LocalDate dataDeValidade){
        this.dataDeValidade = dataDeValidade;
    }

}
//CLASSE PRODTUOPERECIVEL