package enums;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Estado;
import models.TipoInterferencia;
import services.EstadoService;
import services.TipoInterferenciaService;
import utilities.URLUtility;

public enum StaticData {
	INSTANCE;

	private String localUrl;

	private StaticData() {
		this.localUrl = URLUtility.getURLService();
	}

	private ObservableList<TipoInterferencia> obsListTipoInterferencia;
	private ObservableList<Estado> obsListEstado;

	public List<TipoInterferencia> fetchTipoInterferencia() {

		try {

			TipoInterferenciaService service = new TipoInterferenciaService(localUrl);

			List<TipoInterferencia> list = service.fetchAll();

			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//
	public ObservableList<TipoInterferencia> getTipoInterferencia() {
		// Se já houver solicitado uma vez não precisa solicitar mais.
		if (obsListTipoInterferencia == null) {

			obsListTipoInterferencia = FXCollections.observableArrayList(fetchTipoInterferencia());

			return obsListTipoInterferencia;
		}
		return obsListTipoInterferencia;
	}

	public List<Estado> fetchEstado() {

		try {

			EstadoService service = new EstadoService(localUrl);

			List<Estado> list = service.fetchAll();

			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//
	public ObservableList<Estado> getEstados() {
		// Se já houver solicitado uma vez não precisa solicitar mais.
		if (obsListTipoInterferencia == null) {

			obsListEstado = FXCollections.observableArrayList(fetchEstado());

			return obsListEstado;
		}
		return obsListEstado;
	}

}
