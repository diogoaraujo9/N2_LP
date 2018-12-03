package dao;

public class PerguntaDAO {
	private String descricao;
    private String dataCriacao;
    private int id;

    public PerguntaDAO(String descricao, String dataCriacao, int id) {
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }
    
    public int getId()
    {
    	return id;
    }
}
