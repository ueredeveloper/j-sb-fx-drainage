package enums;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Dominio;
import models.Estado;
import models.SituacaoProcesso;
import models.SubtipoOutorga;
import models.TipoAto;
import models.TipoInterferencia;
import models.TipoOutorga;
import services.DominioService;
import utilities.URLUtility;

public enum StaticData {
	INSTANCE;

	private String localUrl;

	private StaticData() {
		this.localUrl = URLUtility.getURLService();
	}

	private ObservableList<TipoInterferencia> obsTypesOfInterferences;
	private ObservableList<TipoOutorga> obsTypeOfGrants;
	private ObservableList<SubtipoOutorga> obsSubtypesOfGrants;
	private ObservableList<SituacaoProcesso> obsProcessesSituations;
	private ObservableList<TipoAto> obsTypesOfActs;
	private ObservableList<Estado> obsStates;

	private Dominio dominio;

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
	public ObservableList<TipoInterferencia> getTypesOfInterferences() {

		// Se já houver solicitado uma vez não precisa solicitar mais.
		if (obsTypesOfInterferences == null) {

			if (dominio == null) {
				dominio = fetchAllDomainsTables();
			}

			List<TipoInterferencia> list = new ArrayList<TipoInterferencia>(
					dominio.getTypesOfInterferencesMap().values());
			obsTypesOfInterferences = FXCollections.observableArrayList(list);

			return obsTypesOfInterferences;
		}
		return obsTypesOfInterferences;
	}

	public ObservableList<TipoOutorga> getTypesOfGrants() {

		// Se já houver solicitado uma vez não precisa solicitar mais.
		if (obsTypeOfGrants == null) {

			if (dominio == null) {
				dominio = fetchAllDomainsTables();
			}
			List<TipoOutorga> list = new ArrayList<TipoOutorga>(dominio.getTypesOfGrantsMap().values());
			obsTypeOfGrants = FXCollections.observableArrayList(list);

			return obsTypeOfGrants;
		}
		return obsTypeOfGrants;
	}

	public ObservableList<SubtipoOutorga> getSubtypesOfGrants() {

		// Se já houver solicitado uma vez não precisa solicitar mais.
		if (obsSubtypesOfGrants == null) {

			if (dominio == null) {
				dominio = fetchAllDomainsTables();
			}
			List<SubtipoOutorga> list = new ArrayList<SubtipoOutorga>(dominio.getSubtypesOfGrantsMap().values());
			obsSubtypesOfGrants = FXCollections.observableArrayList(list);

			return obsSubtypesOfGrants;
		}
		return obsSubtypesOfGrants;
	}

	public ObservableList<TipoAto> getTypesOfActs() {

		// Se já houver solicitado uma vez não precisa solicitar mais.
		if (obsTypesOfActs == null) {

			if (dominio == null) {
				dominio = fetchAllDomainsTables();
			}
			List<TipoAto> list = new ArrayList<TipoAto>(dominio.getTypesOfActsMap().values());
			obsTypesOfActs = FXCollections.observableArrayList(list);

			return obsTypesOfActs;
		}
		return obsTypesOfActs;
	}

	public ObservableList<SituacaoProcesso> getProcessesSituations() {

		// Se já houver solicitado uma vez não precisa solicitar mais.
		if (obsProcessesSituations == null) {

			if (dominio == null) {
				dominio = fetchAllDomainsTables();
			}
			List<SituacaoProcesso> list = new ArrayList<SituacaoProcesso>(dominio.getProcessesSituationsMap().values());
			obsProcessesSituations = FXCollections.observableArrayList(list);

			return obsProcessesSituations;
		}
		return obsProcessesSituations;
	}

	public ObservableList<Estado> getStates() {

		// Se já houver solicitado uma vez não precisa solicitar mais.
		if (obsStates == null) {

			if (dominio == null) {
				dominio = fetchAllDomainsTables();
			}
			List<Estado> list = new ArrayList<Estado>(dominio.getStatesMap().values());
			obsStates = FXCollections.observableArrayList(list);

			return obsStates;
		}
		return obsStates;
	}

}
