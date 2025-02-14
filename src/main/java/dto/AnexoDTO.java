package dto;

public class AnexoDTO {

	private Long anId;
	private String anNumero;
	private Long procId;
	private String procNumero;
	private Long usId;
	private String usNome;

	public AnexoDTO() {
		super();
	}

	public AnexoDTO(Long anId, String anNumero, Long procId, String procNumero, Long usId, String usNome) {
		super();
		this.anId = anId;
		this.anNumero = anNumero;
		this.procId = procId;
		this.procNumero = procNumero;
		this.usId = usId;
		this.usNome = usNome;
	}

	public Long getAnId() {
		return anId;
	}

	public void setAnId(Long anId) {
		this.anId = anId;
	}

	public String getAnNumero() {
		return anNumero;
	}

	public void setAnNumero(String anNumero) {
		this.anNumero = anNumero;
	}

	public Long getProcId() {
		return procId;
	}

	public void setProcId(Long procId) {
		this.procId = procId;
	}

	public String getProcNumero() {
		return procNumero;
	}

	public void setProcNumero(String procNumero) {
		this.procNumero = procNumero;
	}

	public Long getUsId() {
		return usId;
	}

	public void setUsId(Long usId) {
		this.usId = usId;
	}

	public String getUsNome() {
		return usNome;
	}

	public void setUsNome(String usNome) {
		this.usNome = usNome;
	}

}
