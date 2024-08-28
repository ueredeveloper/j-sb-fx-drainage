package enums;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Dominio;
import models.Estado;
import models.TipoAto;
import models.TipoInterferencia;
import models.TipoOutorga;
import services.DominioService;
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
	private ObservableList<TipoOutorga> obsListTipoOutorga;
	private ObservableList<TipoAto> obsListTipoAto;
	private ObservableList<Estado> obsListEstado;

	private Dominio dominio;

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

	public Dominio fetchAllDomainsTables() {

		try {

			DominioService service = new DominioService(localUrl);

			Dominio domain = service.fetchAllDomainsTables();

			return domain;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//
	public ObservableList<TipoInterferencia> getTipoInterferencia() {

		// Se já houver solicitado uma vez não precisa solicitar mais.
		if (obsListTipoInterferencia == null) {

			if (dominio == null) {
				dominio = fetchAllDomainsTables();
			}

			List<TipoInterferencia> list = new ArrayList<TipoInterferencia>(dominio.getTipoInterferencia().values());
			obsListTipoInterferencia = FXCollections.observableArrayList(list);

			return obsListTipoInterferencia;
		}
		return obsListTipoInterferencia;
	}

	public ObservableList<TipoOutorga> getTipoOutorga() {

		// Se já houver solicitado uma vez não precisa solicitar mais.
		if (obsListTipoOutorga == null) {

			if (dominio == null) {
				dominio = fetchAllDomainsTables();
			}
			List<TipoOutorga> list = new ArrayList<TipoOutorga>(dominio.getTipoOutorga().values());
			obsListTipoOutorga = FXCollections.observableArrayList(list);

			return obsListTipoOutorga;
		}
		return obsListTipoOutorga;
	}

	public ObservableList<TipoAto> getTipoAto() {

		// Se já houver solicitado uma vez não precisa solicitar mais.
		if (obsListTipoAto == null) {

			if (dominio == null) {
				dominio = fetchAllDomainsTables();
			}
			List<TipoAto> list = new ArrayList<TipoAto>(dominio.getTipoAto().values());
			obsListTipoAto = FXCollections.observableArrayList(list);

			return obsListTipoAto;
		}
		return obsListTipoAto;
	}

	public ObservableList<Estado> getEstado () {

		// Se já houver solicitado uma vez não precisa solicitar mais.
		if (obsListEstado == null) {

			if (dominio == null) {
				dominio = fetchAllDomainsTables();
			}
			List<Estado> list = new ArrayList<Estado>(dominio.getEstado().values());
			obsListEstado = FXCollections.observableArrayList(list);

			return obsListEstado;
		}
		return obsListEstado;
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
