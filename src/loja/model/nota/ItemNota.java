package loja.model.nota;

import loja.model.produto.*;
import java.math.BigDecimal;
public class ItemNota {
    private Produto produto;

    
    private int quantidade;



    public ItemNota(Produto produto, int quantidade){
        this.produto = produto;

        this.quantidade = quantidade;

    }

//metodos

//get

    public Produto getProduto() {

        return produto;

    }

    public int getQuantidade() {

        return quantidade;

    }
//Calculo do valor do item

    public BigDecimal getValorDoItem() {

        return produto.getPreco().multiply(BigDecimal.valueOf(quantidade)); //calculo do valor do item usando o preco base do produto

    }
}
