package controllers.views;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalInt;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.IntStream;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import controllers.DocumentController;
import dto.AnexoDTO;
import enums.ToastType;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Anexo;
import models.ApiResponse;
import models.Processo;
import models.Usuario;
import services.AnexoService;
import services.ProcessoService;
import services.ServiceResponse;
import utilities.URLUtility;

public class AddAttachmentController implements Initializable {

	@FXML
	private JFXButton btnClose;

	@FXML
	private JFXComboBox<Anexo> cbAttachment;
	@FXML
	private JFXComboBox<Processo> cbProcess;
	@FXML
	private JFXComboBox<Usuario> cbUser;

	@FXML
	private JFXTextField tfSearch, tfAttachment;

	@FXML
	private JFXButton btnSearch;

	@FXML
	private TableView<AnexoDTO> tableView;
	ObservableList<AnexoDTO> tableViewObsList = FXCollections.observableArrayList();

	@FXML
	private TableColumn<AnexoDTO, String> tcProcess;

	@FXML
	private TableColumn<AnexoDTO, String> tcUser;

	@FXML
	private TableColumn<AnexoDTO, String> tcAttachment;

	@FXML
	private JFXButton btnNew;

	@FXML
	private JFXButton btnSave;

	@FXML
	private JFXButton btnEdit;

	@FXML
	private JFXButton btnDelete;

	private String urlService;
	private TranslateTransition ttClose;
	JFXComboBox<Anexo> docCbAttachment;
	ObservableList<Anexo> obsAttachemnt;
	Anexo attachmentModel;
	AnexoDTO attachmentDTO;

	DocumentController documentController;
	ProcessComboBoxController processComboBoxController;
	UserComboBoxController userComboBoxController;

	public AddAttachmentController(DocumentController documentController, Anexo attachmentModel, String urlService,
			TranslateTransition ttClose) {
		this.documentController = documentController;
		this.attachmentModel = attachmentModel;
		this.urlService = URLUtility.getURLService();
		this.ttClose = ttClose;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		userComboBoxController = new UserComboBoxController(urlService, cbUser);
		processComboBoxController = new ProcessComboBoxController(urlService, cbProcess);

		tcAttachment.setCellValueFactory(new PropertyValueFactory<>("anNumero"));
		tcProcess.setCellValueFactory(new PropertyValueFactory<>("procNumero"));
		tcUser.setCellValueFactory(new PropertyValueFactory<>("usNome"));

		tableView.setItems(tableViewObsList);

		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

			if (newSelection != null) {

				this.attachmentDTO = newSelection;

				// Preencher o text field para edição se precisar
				tfAttachment.setText(newSelection.getAnNumero());

				// Limpa o combobox processo e combobox usuario
				// cbProcess.getSelectionModel().clearSelection();
				// cbProcess.setValue(null);
				processComboBoxController.fillAndSelectComboBoxProcess(
						new Processo(newSelection.getProcId(), newSelection.getProcNumero()));

				userComboBoxController
						.fillAndSelectComboBox(new Usuario(newSelection.getUsId(), newSelection.getUsNome()));

			} else {
				clearAllComponents();
			}
		});

		btnClose.setOnAction(e -> {
			ttClose.play();

			// Preencher a tela de cadastro de documentos com o processo persistido
			// (salvamento ou edição)
			if (attachmentModel != null) {
				this.documentController.fillAndSelectComboBoxAttachment(attachmentModel);

			}

		});

		btnSearch.setOnAction(event -> {

			String keyword = tfSearch.getText();

			try {
				AnexoService service = new AnexoService(urlService);
				Set<Anexo> objects = service.fecthByKeyword(keyword);

				List<AnexoDTO> objectsDTO = new ArrayList<AnexoDTO>();
				// Cria classe DTO e preenche com valores do anexo, processo e usuário, caso
				// haja relacionamentos
				objects.forEach(att -> {

					System.out.println("tamanho processos " + att.getProcessos().size());

					// Se houver mais de um processo relacionado
					if (att.getProcessos().size() > 1) {

						att.getProcessos().forEach(proc -> {

							AnexoDTO anexo = new AnexoDTO(att.getId(), att.getNumero());

							System.out.println("número do processo " + proc.getNumero());

							anexo.setProcId(proc.getId() != null ? proc.getId() : null);
							anexo.setProcNumero(proc.getNumero() != null ? proc.getNumero() : null);
							anexo.setUsId(proc.getUsuario().getId() != null ? proc.getUsuario().getId() : null);
							anexo.setUsNome(proc.getUsuario().getNome() != null ? proc.getUsuario().getNome() : null);

							objectsDTO.add(anexo);

						});

					} else {

						AnexoDTO anexo = new AnexoDTO(att.getId(), att.getNumero());

						objectsDTO.add(anexo);

					}

				});

				tableViewObsList.clear();

				System.out.println("adicionados: " + objectsDTO.size());

				tableViewObsList.addAll(objectsDTO);

			} catch (Exception e) {
				// Trate exceções adequadamente
				e.printStackTrace();
			}

		});

		btnNew.setOnAction(event -> {
			clearAllComponents();
		});

		btnSave.setOnAction(event -> {
			save(event);
		});
		btnEdit.setOnAction(event -> {
			update(event);
		});

		btnDelete.setOnAction(event -> {
			deleteAttachment(event);
		});
	}

	public void save(ActionEvent event) {

		try {

			// Verifica se algo foi digitado com número de processo principal (anexo)
			if (tfAttachment.getText().isEmpty() || tfAttachment.getText() == null) {
				// this.process.setUsuario(obsUsList0);

				// Informa salvamento com sucesso
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Digite o número do processo principal (anexo)!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

				return;
			}

			if (this.attachmentModel == null) {
				this.attachmentModel = new Anexo();
				this.attachmentModel.setNumero(tfAttachment.getText());
			} else {
				this.attachmentModel.setNumero(tfAttachment.getText());
			}

			// Capturar as seleções dos outros atributos nos comboboxes
			Processo selectedProcess = processComboBoxController.getSelectedObject();

			Usuario selectedUser = userComboBoxController.getSelectedObject();

			// Anexar o processo ao anexo (processo principal)
			if (selectedProcess == null) {

				// this.process.setUsuario(obsUsList0);

				// Informa salvamento com sucesso
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Selecione um processo!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

				return;
			}

			// Anexar o um usuário ao processo e processo ao processo principal (anexo)
			if (selectedUser == null) {

				// this.process.setUsuario(obsUsList0);

				// Informa salvamento com sucesso
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Selecione um usuário!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

				return;

				// Não pode haver cpf nulo, é preciso selecionar um usuário existente.
			} else if (selectedUser.getCpfCnpj() == null) {
				// this.process.setUsuario(obsUsList0);

				// Informa salvamento com sucesso
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Usuário não encontrado. Cadastro o usuário primeiro!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

				return;

			}

			// Cria o objeto completo de persistência
			selectedProcess.setUsuario(selectedUser);

			this.attachmentModel.getProcessos().add(selectedProcess);

			// DocumentService documentService = new DocumentService(localUrl);
			AnexoService service = new AnexoService(urlService);

			ServiceResponse<?> serviceResponse = service.save(this.attachmentModel);

			// Caputura a mensagem do banco de dados e converte para o objeto solicitado, no
			// caso Usuario, além de status e mensagem.
			ApiResponse<Anexo> serviceResponseFromJava = ApiResponse
					.fromJson(serviceResponse.getResponseBody().toString(), Anexo.class);

			if (!serviceResponseFromJava.getStatus().equals("erro")) {

				// Adiciona resposta na tabela
				Anexo responseObject = serviceResponseFromJava.getObject();

				// Adiciona com primeiro na lista

				ObservableList<AnexoDTO> items = tableView.getItems();
				// Remove o objeto da lista
				OptionalInt indexOpt = IntStream.range(0, items.size())
						.filter(i -> items.get(i).getAnId().equals(responseObject.getId())).findFirst();

				if (indexOpt.isPresent()) {

					Set<AnexoDTO> objectsDTO = new HashSet<AnexoDTO>();

					responseObject.getProcessos().forEach(proc -> {
						objectsDTO.add(new AnexoDTO(responseObject.getId(), responseObject.getNumero(),
								proc != null ? proc.getId() : null, proc != null ? proc.getNumero() : "",
								proc.getUsuario() != null ? proc.getUsuario().getId() : null,
								proc.getUsuario() != null ? proc.getUsuario().getNome() : ""));
					});

					// Se o objeto existe, substituí-lo pelo novo objeto

					objectsDTO.forEach(obj -> {
						items.set(indexOpt.getAsInt(), obj);
					});

				} else {

					Set<AnexoDTO> objectsDTO = new HashSet<AnexoDTO>();

					responseObject.getProcessos().forEach(proc -> {
						objectsDTO.add(new AnexoDTO(responseObject.getId(), responseObject.getNumero(),
								proc != null ? proc.getId() : null, proc != null ? proc.getNumero() : "",
								proc.getUsuario() != null ? proc.getUsuario().getId() : null,
								proc.getUsuario() != null ? proc.getUsuario().getNome() : ""));
					});

					objectsDTO.forEach(obj -> {
						items.add(0, obj);

						tableView.getSelectionModel().select(obj);
					});
					// Caso contrário, adicioná-lo no início da lista e selecioná-lo

				}

				// Informa salvamento com sucesso
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = serviceResponseFromJava.getMensagem();
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);

			} else {
				// adiconar alerta (Toast) de erro

				// Informa salvamento com sucesso
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = serviceResponseFromJava.getMensagem();
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
			}

		} catch (Exception e) {
			// adicionar Toast de erro
			e.printStackTrace();
		}

	}

	public void update(ActionEvent event) {

		this.attachmentDTO = tableView.getSelectionModel().getSelectedItem();

		// Anexar o um usuário ao processo e processo ao processo principal (anexo)
		if (this.attachmentDTO == null) {

			// this.process.setUsuario(obsUsList0);

			// Informa salvamento com sucesso
			Node source = (Node) event.getSource();
			Stage ownerStage = (Stage) source.getScene().getWindow();
			String toastMsg = "Selecione um processo principal!";
			utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

			return;
		}

		try {

			// Capturar as seleções dos outros atributos nos comboboxes
			Processo selectedProcess = processComboBoxController.getSelectedObject();

			Usuario selectedUser = userComboBoxController.getSelectedObject();

			// Anexar o processo ao anexo (processo principal)
			if (selectedProcess == null) {

				// this.process.setUsuario(obsUsList0);

				// Informa salvamento com sucesso
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Selecione um processo!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

				return;
			}

			// Anexar o um usuário ao processo e processo ao processo principal (anexo)
			if (selectedUser == null) {

				// this.process.setUsuario(obsUsList0);

				// Informa salvamento com sucesso
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = "Selecione um usuário!";
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);

				return;
			}

			// Preenche objeto de persistência com o anexo selecionado na table view.

			if (this.attachmentModel == null) {
				this.attachmentModel = new Anexo();
			}

			this.attachmentModel.setId(this.attachmentDTO.getAnId());
			this.attachmentModel.setNumero(tfAttachment.getText());
			// Relaciona objetos selecionados pelo usuário nos comboboxes
			selectedProcess.setUsuario(selectedUser);
			this.attachmentModel.getProcessos().add(selectedProcess);

			// DocumentService documentService = new DocumentService(localUrl);
			AnexoService service = new AnexoService(urlService);

			ServiceResponse<?> serviceResponse = service.update(this.attachmentModel);

			// Caputura a mensagem do banco de dados e converte para o objeto solicitado, no
			// caso Usuario, além de status e mensagem.
			ApiResponse<Anexo> serviceResponseFromJava = ApiResponse
					.fromJson(serviceResponse.getResponseBody().toString(), Anexo.class);

			if (!serviceResponseFromJava.getStatus().equals("erro")) {

				// Informa salvamento com sucesso
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = serviceResponseFromJava.getMensagem();
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);
				// Adiciona resposta na tabela
				Anexo responseObject = serviceResponseFromJava.getObject();
				// Adiciona com primeiro na lista

				ObservableList<AnexoDTO> items = tableView.getItems();
				// Remove o objeto da lista
				OptionalInt indexOpt = IntStream.range(0, items.size())
						.filter(i -> items.get(i).getAnId().equals(responseObject.getId())).findFirst();

				if (indexOpt.isPresent()) {

					Set<AnexoDTO> objectsDTO = new HashSet<AnexoDTO>();

					responseObject.getProcessos().forEach(proc -> {
						objectsDTO.add(new AnexoDTO(responseObject.getId(), responseObject.getNumero(),
								proc != null ? proc.getId() : null, proc != null ? proc.getNumero() : "",
								proc.getUsuario() != null ? proc.getUsuario().getId() : null,
								proc.getUsuario() != null ? proc.getUsuario().getNome() : ""));
					});

					// Se o objeto existe, substituí-lo pelo novo objeto

					objectsDTO.forEach(obj -> {
						items.set(indexOpt.getAsInt(), obj);
					});

				} else {

					Set<AnexoDTO> objectsDTO = new HashSet<AnexoDTO>();

					responseObject.getProcessos().forEach(proc -> {
						objectsDTO.add(new AnexoDTO(responseObject.getId(), responseObject.getNumero(),
								proc != null ? proc.getId() : null, proc != null ? proc.getNumero() : "",
								proc.getUsuario() != null ? proc.getUsuario().getId() : null,
								proc.getUsuario() != null ? proc.getUsuario().getNome() : ""));
					});

					objectsDTO.forEach(obj -> {
						items.add(0, obj);

						tableView.getSelectionModel().select(obj);
					});
					// Caso contrário, adicioná-lo no início da lista e selecioná-lo

				}

			} else {
				// adiconar alerta (Toast) de erro

				// Informa salvamento com sucesso
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = serviceResponseFromJava.getMensagem();
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
			}

		} catch (Exception e) {
			// adicionar Toast de erro
			e.printStackTrace();
		}

	}

	public void deleteAttachment(ActionEvent event) {

		AnexoDTO selectedObject = tableView.getSelectionModel().getSelectedItem();

		try {
			AnexoService service = new AnexoService(urlService);

			ServiceResponse<?> serviceResponse = service.deleteById(selectedObject.getAnId());

			ApiResponse<Anexo> serviceResponseFromJava = ApiResponse
					.fromJson(serviceResponse.getResponseBody().toString(), Anexo.class);

			if (!serviceResponseFromJava.getStatus().equals("erro")) {

				// Informa sucesso em deletar
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = serviceResponseFromJava.getMensagem();
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.SUCCESS);

				// retira objecto da tabela de documentos tvDocs
				tableView.getItems().remove(selectedObject);

			} else {
				// Informa erro em deletar
				Node source = (Node) event.getSource();
				Stage ownerStage = (Stage) source.getScene().getWindow();
				String toastMsg = serviceResponseFromJava.getMensagem();
				utilities.Toast.makeText(ownerStage, toastMsg, ToastType.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Método para buscar processos e preencher o ComboBox
	public Set<Processo> fetchProcessesByKeyword(String keyword) {

		try {
			ProcessoService service = new ProcessoService(urlService);

			Set<Processo> objects = service.fetchByKeyword(keyword);

			return objects;

		} catch (Exception e) {

		}
		return null;
	}

	public Set<Anexo> fethcAttachmentsByKeyword(String keyword) {

		try {
			AnexoService service = new AnexoService(urlService);

			Set<Anexo> objects = service.fecthByKeyword(keyword);
			Set<Anexo> objectsByNumberOfProcess = service.fecthByKeyword(keyword);

			objects.forEach(att -> {
				att.getProcessos().forEach(proc -> {
					Set<Processo> procs = new HashSet<Processo>();
					procs.add(proc);
					objectsByNumberOfProcess.add(new Anexo(att.getId(), att.getNumero(), procs));
				});
			});

			return objectsByNumberOfProcess;

		} catch (Exception e) {

		}
		return null;
	}

	public void fillAndSelectComboBoxProcess(Processo process) {
		ObservableList<Processo> newObs = FXCollections.observableArrayList();
		cbProcess.setItems(newObs);

		newObs.add(0, process);

		// Atualizando o ComboBox para refletir a mudança
		// cbProcess.setItems(null);
		cbProcess.setItems(newObs);

		// Selecionando o novo item no ComboBox
		cbProcess.getSelectionModel().select(0);
	}

	public void clearAllComponents() {

		tfAttachment.clear();
		// Limpa o id do processo para que seja salvo novo processo
		this.attachmentModel = new Anexo();

		cbProcess.getSelectionModel().clearSelection();
		cbProcess.setValue(null);

		cbUser.getSelectionModel().clearSelection();
		cbUser.setValue(null);

	}

}
