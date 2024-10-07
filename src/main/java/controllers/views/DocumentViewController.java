package controllers.views;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.text.html.HTML;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import models.Documento;
import models.Interferencia;
import models.Template;
import models.Usuario;
import services.InterferenciaService;
import services.TemplateService;
import services.UsuarioService;
import utilities.URLUtility;
import utilities.WebViewContentLoader;

public class DocumentViewController implements Initializable {

	private static DocumentViewController instance;

	public static DocumentViewController getInstance() {
		return instance;
	}

	private Documento selectedDocument;

	public DocumentViewController(Documento selectedDocument) {
		this.selectedDocument = selectedDocument;
		instance = this; // Setting the static instance
	}

	@FXML
	private Button btnSelectAndCopy;

	@FXML
	private WebView webViewChart, webViewDocument;

	@FXML
	private AnchorPane apContainer, apHtmlEditor;

	@FXML
	private JFXTextField tfDocument;

	@FXML
	private JFXTextField tfAddress;

	@FXML
	private JFXComboBox<Interferencia> cbInterferencies;

	@FXML
	private JFXComboBox<String> cbTemplates;

	@FXML
	private JFXComboBox<Usuario> cbUsers;

	@FXML
	private HTMLEditor htmlEditor;

	@FXML
	private FontAwesomeIconView iconCopyDocument;

	@FXML
	private TabPane tabPane;

	@FXML
	private Tab editorTab;

	ObservableList<Interferencia> obsListInterferencies = FXCollections.observableArrayList();
	ObservableList<Usuario> obsListUsers = FXCollections.observableArrayList();
	ObservableList<String> obsListTemplates = FXCollections.observableArrayList();

	WebViewContentLoader webViewContentLoader;

	Set<Template> templates = new HashSet<>();
	List<String> descricaoList = new ArrayList<String>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		WebViewChart webViewChartInstance = new WebViewChart(webViewChart, selectedDocument);

		iconCopyDocument.setOnMouseClicked(event -> {

			// Initialize WebViewDocument with the WebView component
			WebViewDocument webViewDocumentInstance = new WebViewDocument(webViewDocument, htmlEditor);

			webViewDocumentInstance.getHtmlContent(htmlContent -> {
				if (htmlContent != null) {
					Clipboard clipboard = Clipboard.getSystemClipboard();
					ClipboardContent content = new ClipboardContent();
					content.putHtml(htmlContent); // Copy the retrieved HTML content to the clipboard
					
					System.out.println(htmlContent);
					clipboard.setContent(content);
				} else {
					System.err.println("Copy HTML. Failed to retrieve HTML content.");
				}
			});
		});

		// Add a listener to the TabPane to detect when the "Editor" tab is selected
		/*tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
			if (newTab == editorTab) {
				WebViewDocument webViewDocumentInstance = new WebViewDocument(webViewDocument);
				String htmlContent = webViewDocumentInstance.getHtmlContentForHtmlEditor(this.selectedDocument);
				
				htmlEditor.setHtmlText(htmlContent);
			


			}
		});*/

		tfDocument.setText(
				"Número: " + this.selectedDocument.getNumero() + " | Sei: " + this.selectedDocument.getNumeroSei());
		tfAddress.setText(this.selectedDocument.getEnderecoLogradouro());

		String logradouro = this.selectedDocument.getEnderecoLogradouro();

		Set<Interferencia> interferencies = listInterferenciesByLogradouro(logradouro);
		obsListInterferencies.addAll(interferencies);
		cbInterferencies.setItems(obsListInterferencies);

		Set<Usuario> users = listUsersByDocumentId(this.selectedDocument.getId());
		obsListUsers.addAll(users);
		cbUsers.setItems(obsListUsers);

		String typeOfDocument = this.selectedDocument.getTipoDocumento().getDescricao();

		cbInterferencies.setOnAction(e -> {

			Interferencia selectedInterference = cbInterferencies.getSelectionModel().getSelectedItem();
			// Atualiza documento seleciona com a interferência que será utilizada na
			// criação do ato (Parecer, Despacho etc)
			if (selectedInterference != null) {
				this.selectedDocument.getEndereco().getInterferencias().clear();
				this.selectedDocument.getEndereco().getInterferencias().add(selectedInterference);
			}

		});

		cbUsers.setOnAction(e -> {
			Usuario user = cbUsers.getSelectionModel().getSelectedItem();
			
			System.out.println(user.getNome());

			// Atualiza documento com o usuário que será utilizado na criação do ato
			// (Parecer, Despacho etc)
			if (user != null) {
				this.selectedDocument.getUsuarios().clear();
				this.selectedDocument.getUsuarios().add(user);
			}

			Interferencia selectedInterference = cbInterferencies.getSelectionModel().getSelectedItem();

			String typeOfGrant = "";
			String subtypeOfGrant = "";
			if (selectedInterference != null) {
				typeOfGrant = selectedInterference.getTipoOutorga().getDescricao();
				subtypeOfGrant = selectedInterference.getSubtipoOutorga().getDescricao();
			}

			// Busca os templates que atendem aos requisitos tipo de documento e tipo e
			// subtipo de outorga.
			templates = listTemplatesByParams(typeOfDocument, typeOfGrant, subtypeOfGrant);

			if (!templates.isEmpty() && templates != null) {
				descricaoList = templates.stream()
						// Seleciona por descrição, mas remove os arquivos das pastas compartilhadas
						// (models, utils, actions)
						.filter(template -> !"utils".equals(template.getPasta()))
						.filter(template -> !"models".equals(template.getPasta()))
						.filter(template -> !"actions".equals(template.getPasta())).map(Template::getDescricao) // Extract
																												// the
																												// 'descricao'
																												// attribute
						.distinct() // Ensure only unique descriptions
						.collect(Collectors.toList()); // Collect to a List

				if (descricaoList != null) {
					obsListTemplates.clear();
					obsListTemplates.addAll(descricaoList);
					cbTemplates.setItems(obsListTemplates);
				}

			}

		});

		cbTemplates.setOnAction(e -> {
			Interferencia selectedInterference = cbInterferencies.getSelectionModel().getSelectedItem();

			System.out.println("cbTemplates, selectedInteference !=null " + selectedInterference != null);

			if (selectedInterference != null) {

				this.selectedDocument.getEndereco().getInterferencias().clear();
				this.selectedDocument.getEndereco().getInterferencias().add(selectedInterference);

				WebContent webContent = new WebContent();

				// Get the selected description from the ComboBox
				String description = cbTemplates.getSelectionModel().getSelectedItem();

				System.out.println("descriptionList != null " + !descricaoList.isEmpty());

				if (!descricaoList.isEmpty()) {

					List<Template> filteredTemplates = templates.stream()
							.filter(t -> t.getDescricao().equals(description) // Filter by the selected description
									|| "models".equals(t.getPasta()) // Include templates where 'pasta' is 'models'
									|| "utils".equals(t.getPasta()) // Include templates where 'pasta' is 'utils'
									|| "actions".equals(t.getPasta()) // Include templates where 'pasta' is 'actions'
					).distinct() // Ensure unique templates
							.collect(Collectors.toList());

					// Adiciona primeiro o index.html
					filteredTemplates.forEach(t -> {
						if (t.getNome().equals("index.html")) {
							webContent.setWebContent(t.getConteudo());
						}
					});

					// Adiciona depois os outros arquivos
					filteredTemplates.forEach(t -> {
						String str = webContent.getWebContent();

						if (!t.getNome().equals("index.html")) {
							str += "<script>" + t.getConteudo() + "</script>";
						}
						webContent.setWebContent(str);

					});
					
					// Initialize WebViewDocument with the WebView component
					WebViewDocument webViewDocumentInstance = new WebViewDocument(webViewDocument, htmlEditor);

					// Atualiza o WebView com o conteúdo atualizado
					webViewDocumentInstance.loadContent(webContent.getWebContent());

					webViewDocumentInstance.changeContent(this.selectedDocument);

				}

			}

		});

	}

	public Set<Template> listTemplatesByParams(String typeOfDocument, String typeOfGrant, String subtypeOfGrant) {

		Set<Template> objects = null;

		try {

			String urlService = URLUtility.getURLService();

			TemplateService service = new TemplateService(urlService);

			objects = service.listTemplatesByParams(typeOfDocument, typeOfGrant, subtypeOfGrant);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objects;
	}

	public Set<Usuario> listUsersByDocumentId(Long id) {

		Set<Usuario> objects = null;

		try {

			String urlService = URLUtility.getURLService();

			UsuarioService service = new UsuarioService(urlService);

			objects = service.listUsersByDocumentId(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objects;
	}

	public Set<Interferencia> listInterferenciesByLogradouro(String logradouro) {

		Set<Interferencia> objects = null;

		try {

			String urlService = URLUtility.getURLService();

			InterferenciaService service = new InterferenciaService(urlService);

			objects = service.fetchByKeyword(logradouro);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objects;
	}

	class WebContent {
		String webContent = "";

		public String getWebContent() {
			return webContent;
		}

		public void setWebContent(String webContent) {
			this.webContent = webContent;
		}

	}

}