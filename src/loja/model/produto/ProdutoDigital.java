package loja.model.produto;
import java.math.BigDecimal;

public class ProdutoDigital extends Produto{

    private String linkDoProduto; // link do produto

    public ProdutoDigital(String codigo, String nome, BigDecimal preco,BigDecimal estoque, String linkDoProduto){
        super(codigo, nome,preco,estoque);
        this.linkDoProduto = linkDoProduto;
    }

    //metodos
    public String getLinkDoProduto(){
        return linkDoProduto;
    }

    public void setLinkDoProduto(String linkDoProduto){
        if(linkDoProduto==null|| linkDoProduto.trim().isEmpty()){
            throw new IllegalArgumentException("O link do produto não pode ser nulo ou vazio."); //sinaliza que houve erro
        }
        this.linkDoProduto = linkDoProduto;
    }
}
