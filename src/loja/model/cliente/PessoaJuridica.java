package loja.model.cliente;

public class PessoaJuridica extends Cliente{

    private String cnpj;

    public PessoaJuridica(String id, String nome, String endereco, String telefone, String cnpj) {
        super(id,nome,endereco,telefone);
        this.cnpj = cnpj;
    }

    public String getCNPJ(){
        return cnpj;
    }

    public void setCNPJ(String cnpj){
        this.cnpj = cnpj;
    }
    
}
