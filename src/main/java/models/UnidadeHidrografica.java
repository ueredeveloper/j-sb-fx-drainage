package models;

public class UnidadeHidrografica {

	private Long objectid;
	private String uhNome;
	private String uhLabel;
	private String baciaCodi;
	private Long uhCodigo;
	
	public UnidadeHidrografica() {
		super();
	}
	
	public UnidadeHidrografica(Long objectid) {
		super();
		this.objectid = objectid;
	}

	public Long getObjectid() {
		return objectid;
	}
	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}
	public String getUhNome() {
		return uhNome;
	}
	public void setUhNome(String uhNome) {
		this.uhNome = uhNome;
	}
	public String getUhLabel() {
		return uhLabel;
	}
	public void setUhLabel(String uhLabel) {
		this.uhLabel = uhLabel;
	}
	public String getBaciaCodi() {
		return baciaCodi;
	}
	public void setBaciaCodi(String baciaCodi) {
		this.baciaCodi = baciaCodi;
	}
	public Long getUhCodigo() {
		return uhCodigo;
	}
	public void setUhCodigo(Long uhCodigo) {
		this.uhCodigo = uhCodigo;
	}
	
	@Override
	public String toString() {
		return uhNome;
	}
	

}
