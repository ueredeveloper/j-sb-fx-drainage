package models;

public class BaciaHidrografica {

	private Long objectid;

	private Double shapeLeng;

	private Long baciaCod;

	private String baciaNome;

	private String gdbGeomattrData;

	private String shape;
	
	public BaciaHidrografica() {
		super();
	}
	
	public BaciaHidrografica(Long objectid) {
		super();
		this.objectid = objectid;
	}

	public BaciaHidrografica(Long objectid, Double shapeLeng, Long baciaCod, String baciaNome, String gdbGeomattrData,
			String shape) {
		super();
		this.objectid = objectid;
		this.shapeLeng = shapeLeng;
		this.baciaCod = baciaCod;
		this.baciaNome = baciaNome;
		this.gdbGeomattrData = gdbGeomattrData;
		this.shape = shape;
	}

	public Long getObjectid() {
		return objectid;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}

	public Double getShapeLeng() {
		return shapeLeng;
	}

	public void setShapeLeng(Double shapeLeng) {
		this.shapeLeng = shapeLeng;
	}

	public Long getBaciaCod() {
		return baciaCod;
	}

	public void setBaciaCod(Long baciaCod) {
		this.baciaCod = baciaCod;
	}

	public String getBaciaNome() {
		return baciaNome;
	}

	public void setBaciaNome(String baciaNome) {
		this.baciaNome = baciaNome;
	}

	public String getGdbGeomattrData() {
		return gdbGeomattrData;
	}

	public void setGdbGeomattrData(String gdbGeomattrData) {
		this.gdbGeomattrData = gdbGeomattrData;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	@Override
	public String toString() {
		return baciaNome;
	}
	
	

	
}
