package models;

public class Processo {
	private int proc_id;
	private String proc_descricao;

	public Processo() {
		super();

	}

	public Processo(int proc_id, String proc_descricao) {
		super();
		this.proc_id = proc_id;
		this.proc_descricao = proc_descricao;
	}

	public int getProc_id() {
		return proc_id;
	}

	public void setProc_id(int proc_id) {
		this.proc_id = proc_id;
	}

	public String getProc_descricao() {
		return proc_descricao;
	}

	public void setProc_descricao(String proc_descricao) {
		this.proc_descricao = proc_descricao;
	}


}
