package enums;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.InterferenciaTipo;
import services.InterferenciaTipoService;
import utilities.URLUtility;

public enum StaticData {
	INSTANCE;

	private String localUrl;

	private StaticData() {
		this.localUrl = URLUtility.getURLService();
	}

	private ObservableList<InterferenciaTipo> obsListInterferenciaTipo;

	public List<InterferenciaTipo> fetchInterferenciaTipo() {
		
		try {

			InterferenciaTipoService service = new InterferenciaTipoService(localUrl);

			List<InterferenciaTipo> list = service.fetchAll();

			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 
	public ObservableList<InterferenciaTipo> getInterferenciaTipo() {
		// Se já houver solicitado uma vez não precisa solicitar mais.
		if (obsListInterferenciaTipo == null) {
			
			obsListInterferenciaTipo = FXCollections.observableArrayList(fetchInterferenciaTipo());
			 
			return obsListInterferenciaTipo;
		}
		return obsListInterferenciaTipo;
	}

}
