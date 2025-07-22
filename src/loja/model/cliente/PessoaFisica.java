package loja.model.cliente;

public class PessoaFisica extends Cliente{
    private String cpf;

    public PessoaFisica(String id , String nome, String endereco, String telefone, String cpf) {
        super(id,nome,endereco,telefone);
        this.cpf = cpf;
    }

    public String getCPF() {
        return cpf;
    }

    public void setCPF(String cpf) {
        this.cpf = cpf;
    }
    
}
