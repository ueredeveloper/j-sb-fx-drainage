# Lista de Tarefas

## 5 de novembro de 2023

* **[X] Tarefa 1 - Classe**
    * A classe `ResizeAnimation` deve funcionar em um ouvinte que, ao verificar que o `apMap` mudou a largura, redimensiona a largura do mapa.
    * 07/11/2023 - Resolvido.
        * Retirada a classe `ResizeAnimation`, criada a classe `ResizeMap`.

## 7 de novembro de 2023

* **[X] Tarefa 1 - DocumentoController - CSS**
    * Adicionar `on focus`, cor secundária ao combobox.

* **[X] Tarefa 2 - DocumentoController - CSS**
    * O Combobox está desalinhado com os textfields.
    	30/11/2023 - Resolvido isto bem como todo css do combobox.

## 13 de novembro de 2023

* **[X] Tarefa 1 - Adicionar atualização da visualização do documento com o item selecionado pelo usuário.**
	30/11/2023 - Resolvido com a mudança da biblioteca Gojs para E-chart.js.

## 19 de novembro de 2023

* **[X] Tarefa 1**
    * Avaliar o erro "Connection refused". Talvez mostrar somente o mapa, sem a tela de cadastro e um tooltip de erro de conexão.
    		Ou seja, o programa abrir com suas telas mesmo sem conexão.
    		30/12/2023 - A primeira conexão é com o login, então se não há login não é preciso abrir mais nada.

## 28 de novembro de 2023

* **[X] Tarefa 1 - Criar tooltips para todos os componentes da tela documentos**

* **[X] Tarefa 2 - Adicionar e-chart substituindo Gojs nos diagramas.**

* **[ ] Tarefa 3 - Cadastrar documento sem alguns dados, como SEI. Não está funcionando na edição, por exemplo.**

## 30 de novembro de 2023

* **[ ] Tarefa 1 - Poder inserir processo novo com apenas um número.**
	* Se digitar 3 não aparece este número na lista do combobox, mas apenas aqueles processos que tem o número 3 em algum lugar do número completo. Desta forma não posso inserir um processo novo digitando apenas um número.

## 01 de dezembro de 2023
* **[ ] Tarefa 1 - Número Sei - Tela Documentos**
	* Quando temos um documento sem o número sei e outro com o número sei, ao escolher na table list o documento sem o número sei o número que aparece no textfield é o do documento com número. Não deveria aparecer nada, mas continua o número do documento anterior, que tem o número.
	
* **[ ] Tarefa 2 - Pesquisa número sei**
	* A pesquisa só está sendo pelo número completo do sei, deveria ser qualquer parte do número. Converter então o docSei para string e pesquisar semelhanças, parte do número etc.
	
## 18 de dezembro de 2023

* **[ ] Tarefa 1 - Tipo de Documento**
	* O Combobox tipo de documento está desalinhado com os outros componentes.
* **[ ] Tarefa 2 - Lista de Documentos**
	* Os valores na linha da tabela estão mais alto do que os valores no combobox Ações, na lista de documentos.
	
* **[ ] Tarefa 3 - De alguma forma o sistema está salvando um documento sem endereço selecionado como endId = 1.

## 19 de Dezembro de 2023
* **[ ] Tarefa 1 - Gmaps API Key**
	* Adicionar no banco de dados para que seja fácil modificar.
	
## 23 de Fevereiro de 2024
* **[ ] Tarefa 1 - Mudar bando e Tela de Cadastro de Documentos**
	* Mudar a conexão com novo banco e mudar a tela da cadastro de documentos
		* Para isso foi criado o `DocumentController2` e `Documents2.fxml`. São nomes provisórios, depois será deletado os arquivos `DocumentController` e `Documents.fxml`.
		* Também foi criado o serviço `j-water-grants` com novas tabelas e novos relacionamentos. Por exemplo, um documento tem vários endereços e um endereço tem vários documentos.
		
## 03 de Junho de 2024
* **[x] Git pull para adicionar Leaflet Maps.

## 04 de junho de 2024
* ** [X] Verificar o objeto selecionado no mapa.
	Criar um listener para visualizar o objeto selecionado no mapa.
		26/06/2024, Obs: foi criado um listener para receber a coordenada do mapa e também mostrar a coordenada escrita pelo usuário no mapa.
	
## 26 de junho de 2024
* ** [ ] Lista de Bairros, Cidade e Cep.
	Adicionar lista de bairros de acordo com o que o usuário preenche. Por exemplo, a pessoa cadastra o bairro Taguatina Norte, isso já pode ser sugerido da próxima vez.
	[ ] Tabelas Acessórias da Interferência
		Criar as tabelas acessórias como tipo de interferência, tipo de outorga, subtipo de outorga etc...
## 28 de Junho de 2024
* ** [ ] AddInterferenceControler
		A clicar no mapa é atualizado tanto este controller quanto o InterferenceTextFieldsController, mas quando abre o AddInterferenceController, ao clicar no mapa não atualiza a coordenadas mais.
* ** [ ] Ícones Tela Documento
		O css não está funcionando, mas funciona na tela AddInterferenceController. Nesta tela o css, classe icons funciona o hover que aumenta o ícone ao passar o mouse. Deveria funcionar na tela Documentos.
		
		
## 30 de Julho de 2024
* **[X] Seleção de Interferências
	Ao selecionar uma interferência está sendo feito selects no banco desnecessários que além disso fazem o programa ficar lento.
		A solução é adicionar o item selecionado na lista, no caso, que foi feito no controlador dos processos, `dbObjects`. É igual para todos os Combobox editáveis.

## 31 de Julho de 2024
* ** [ ] Tabelas Acessórias da Interferência	
 			TIPO_INTERFERENCIA
 				(1, N'Superficial')
				(2, N'Subterrânea')
				(3, N'LanÃ§amento de Águas Pluviais')
				(4, N'Canal')
				(5, N'Caminhão Pipa')
				(6, N'LanÃ§amento de Efluentes')
				(7, N'Barragem')
			Tipo_Outorga
				(1, N'Outorga')
				(2, N'Outorga Prévia')
				(3, N'Registro')
			Subtipo_Outorga
				(1, N'Renovação')
				(2, N'Modificação')
				(3, N'Transferência')
				(4, N'Suspensão/Revogação')
				(5, N'')
			Situacao_Processo
				(1, N'Arquivado')
				(2, N'Em Análise')
				(3, N'Outorgado')
				(4, N'Vencida')
				(5, N'Arquivado (CNRH 16)')
				(6, N'Pendência')
				(7, N'Indeferido')
				(8, N'Revogado')
			Tipo_Ato
				 (1, N'Despacho')
				 (2, N'Portaria')
				 (3, N'Registro')
				 (4, N'Resolução')
				 (5, N'Resolução ANA')
				 (6, N'Portaria DNAEE')
			Bacia Hidrográfica
			UNIDADE_HIDROGRAFICA
		
* ** [ ] Tabelas Acessórias Subterrâneo
			SubSistema
			Tipo Poço
				(1, N'Manual')
				(2, N'Tubular')
			Forma_Captacao
				(1, N'Bombeamento')
				(2, N'Gravidade')
			Local_Captacao
				(1, N'Nascente')
				(2, N'Rio')
				(3, N'Reservatório')
				(4, N'Canal')
				(5, N'Lago Natural')

* ** [ ] Tabelas Acessórias Superficial
			Metodo_Irrigacao
				(1, N'Aspersão')
				(2, N'Gotejamento')
				(3, N'Pivô')
				(4, N'Manual')
				(5, N'Aspersão/gotejamento')
			
* ** [ ] Tabelas Acessórias Endereço
			Região Administrativa
