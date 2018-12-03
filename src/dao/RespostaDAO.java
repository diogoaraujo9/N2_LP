package dao;

public class RespostaDAO {
	public int idPergunta;
	public int idResposta;
    private String resposta;
    private int pontuacao;

    public RespostaDAO(String resposta, int pontuacao) {
        this.resposta = resposta;
        this.pontuacao = pontuacao;
    }

    public int getIdPergunta() {
    	return idPergunta;
    }
    
    public String getResposta() {
        return resposta;
    }

    public int getPontuacao() {
        return pontuacao;
    }
}
