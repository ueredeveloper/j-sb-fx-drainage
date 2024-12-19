# Lista de Tarefas

## 5 de novembro de 2023

* **[X] Tarefa 1 - Classe**
    * A classe `ResizeAnimation` deve funcionar em um ouvinte que, ao verificar que o `apMap` mudou a largura, redimensiona a largura do mapa.
    * 07/11/2023 - Resolvido.
        * Retirada a classe `ResizeAnimation`, criada a classe `ResizeMap`.

## 7 de novembro de 2023

* **[X] Tarefa 1 - DocumentoController - CSS**
    * Adicionar `on focus`, cor secundï¿½ria ao combobox.

* **[X] Tarefa 2 - DocumentoController - CSS**
    * O Combobox estï¿½ desalinhado com os textfields.
    	30/11/2023 - Resolvido isto bem como todo css do combobox.

## 13 de novembro de 2023

* **[X] Tarefa 1 - Adicionar atualizaï¿½ï¿½o da visualizaï¿½ï¿½o do documento com o item selecionado pelo usuï¿½rio.**
	30/11/2023 - Resolvido com a mudanï¿½a da biblioteca Gojs para E-chart.js.

## 19 de novembro de 2023

* **[X] Tarefa 1**
    * Avaliar o erro "Connection refused". Talvez mostrar somente o mapa, sem a tela de cadastro e um tooltip de erro de conexï¿½o.
    		Ou seja, o programa abrir com suas telas mesmo sem conexï¿½o.
    		30/12/2023 - A primeira conexï¿½o ï¿½ com o login, entï¿½o se nï¿½o hï¿½ login nï¿½o ï¿½ preciso abrir mais nada.

## 28 de novembro de 2023

* **[X] Tarefa 1 - Criar tooltips para todos os componentes da tela documentos**

* **[X] Tarefa 2 - Adicionar e-chart substituindo Gojs nos diagramas.**

* **[ ] Tarefa 3 - Cadastrar documento sem alguns dados, como SEI. Nï¿½o estï¿½ funcionando na ediï¿½ï¿½o, por exemplo.**

## 30 de novembro de 2023

* **[ ] Tarefa 1 - Poder inserir processo novo com apenas um nï¿½mero.**
	* Se digitar 3 nï¿½o aparece este nï¿½mero na lista do combobox, mas apenas aqueles processos que tem o nï¿½mero 3 em algum lugar do nï¿½mero completo. Desta forma nï¿½o posso inserir um processo novo digitando apenas um nï¿½mero.

## 01 de dezembro de 2023
* **[ ] Tarefa 1 - Nï¿½mero Sei - Tela Documentos**
	* Quando temos um documento sem o nï¿½mero sei e outro com o nï¿½mero sei, ao escolher na table list o documento sem o nï¿½mero sei o nï¿½mero que aparece no textfield ï¿½ o do documento com nï¿½mero. Nï¿½o deveria aparecer nada, mas continua o nï¿½mero do documento anterior, que tem o nï¿½mero.
	
* **[ ] Tarefa 2 - Pesquisa nï¿½mero sei**
	* A pesquisa sï¿½ estï¿½ sendo pelo nï¿½mero completo do sei, deveria ser qualquer parte do nï¿½mero. Converter entï¿½o o docSei para string e pesquisar semelhanï¿½as, parte do nï¿½mero etc.
	
## 18 de dezembro de 2023

* **[ ] Tarefa 1 - Tipo de Documento**
	* O Combobox tipo de documento estï¿½ desalinhado com os outros componentes.
* **[ ] Tarefa 2 - Lista de Documentos**
	* Os valores na linha da tabela estï¿½o mais alto do que os valores no combobox Aï¿½ï¿½es, na lista de documentos.
	
* **[ ] Tarefa 3 - De alguma forma o sistema estï¿½ salvando um documento sem endereï¿½o selecionado como endId = 1.

## 19 de Dezembro de 2023
* **[ ] Tarefa 1 - Gmaps API Key**
	* Adicionar no banco de dados para que seja fï¿½cil modificar.
	
## 23 de Fevereiro de 2024
* **[ ] Tarefa 1 - Mudar bando e Tela de Cadastro de Documentos**
	* Mudar a conexï¿½o com novo banco e mudar a tela da cadastro de documentos
		* Para isso foi criado o `DocumentController2` e `Documents2.fxml`. Sï¿½o nomes provisï¿½rios, depois serï¿½ deletado os arquivos `DocumentController` e `Documents.fxml`.
		* Tambï¿½m foi criado o serviï¿½o `j-water-grants` com novas tabelas e novos relacionamentos. Por exemplo, um documento tem vï¿½rios endereï¿½os e um endereï¿½o tem vï¿½rios documentos.
		
## 03 de Junho de 2024
* **[x] Git pull para adicionar Leaflet Maps.

## 04 de junho de 2024
* ** [X] Verificar o objeto selecionado no mapa.
	Criar um listener para visualizar o objeto selecionado no mapa.
		26/06/2024, Obs: foi criado um listener para receber a coordenada do mapa e tambï¿½m mostrar a coordenada escrita pelo usuï¿½rio no mapa.
	
## 26 de junho de 2024
* ** [ ] Lista de Bairros, Cidade e Cep.
	Adicionar lista de bairros de acordo com o que o usuï¿½rio preenche. Por exemplo, a pessoa cadastra o bairro Taguatina Norte, isso jï¿½ pode ser sugerido da prï¿½xima vez.
	[ ] Tabelas Acessï¿½rias da Interferï¿½ncia
		Criar as tabelas acessï¿½rias como tipo de interferï¿½ncia, tipo de outorga, subtipo de outorga etc...
## 28 de Junho de 2024
* ** [ ] AddInterferenceControler
		A clicar no mapa ï¿½ atualizado tanto este controller quanto o InterferenceTextFieldsController, mas quando abre o AddInterferenceController, ao clicar no mapa nï¿½o atualiza a coordenadas mais.
* ** [ ] ï¿½cones Tela Documento
		O css nï¿½o estï¿½ funcionando, mas funciona na tela AddInterferenceController. Nesta tela o css, classe icons funciona o hover que aumenta o ï¿½cone ao passar o mouse. Deveria funcionar na tela Documentos.
		
		
## 30 de Julho de 2024
* **[X] Seleï¿½ï¿½o de Interferï¿½ncias
	Ao selecionar uma interferï¿½ncia estï¿½ sendo feito selects no banco desnecessï¿½rios que alï¿½m disso fazem o programa ficar lento.
		A soluï¿½ï¿½o ï¿½ adicionar o item selecionado na lista, no caso, que foi feito no controlador dos processos, `dbObjects`. ï¿½ igual para todos os Combobox editï¿½veis.

## 31 de Julho de 2024
* ** [ ] Tabelas Acessï¿½rias da Interferï¿½ncia	
 			TIPO_INTERFERENCIA
 				(1, N'Superficial')
				(2, N'Subterrï¿½nea')
				(3, N'LanÃ§amento de ï¿½guas Pluviais')
				(4, N'Canal')
				(5, N'Caminhï¿½o Pipa')
				(6, N'LanÃ§amento de Efluentes')
				(7, N'Barragem')
			Tipo_Outorga
				(1, N'Outorga')
				(2, N'Outorga Prï¿½via')
				(3, N'Registro')
			Subtipo_Outorga
				(1, N'Renovaï¿½ï¿½o')
				(2, N'Modificaï¿½ï¿½o')
				(3, N'Transferï¿½ncia')
				(4, N'Suspensï¿½o/Revogaï¿½ï¿½o')
				(5, N'')
			Situacao_Processo
				(1, N'Arquivado')
				(2, N'Em Anï¿½lise')
				(3, N'Outorgado')
				(4, N'Vencida')
				(5, N'Arquivado (CNRH 16)')
				(6, N'Pendï¿½ncia')
				(7, N'Indeferido')
				(8, N'Revogado')
			Tipo_Ato
				 (1, N'Despacho')
				 (2, N'Portaria')
				 (3, N'Registro')
				 (4, N'Resoluï¿½ï¿½o')
				 (5, N'Resoluï¿½ï¿½o ANA')
				 (6, N'Portaria DNAEE')
			Bacia Hidrogrï¿½fica
			UNIDADE_HIDROGRAFICA
		
* ** [ ] Tabelas Acessï¿½rias Subterrï¿½neo
			SubSistema
			Tipo Poï¿½o
				(1, N'Manual')
				(2, N'Tubular')
			Forma_Captacao
				(1, N'Bombeamento')
				(2, N'Gravidade')
			Local_Captacao
				(1, N'Nascente')
				(2, N'Rio')
				(3, N'Reservatï¿½rio')
				(4, N'Canal')
				(5, N'Lago Natural')

* ** [ ] Tabelas Acessï¿½rias Superficial
			Metodo_Irrigacao
				(1, N'Aspersï¿½o')
				(2, N'Gotejamento')
				(3, N'Pivï¿½')
				(4, N'Manual')
				(5, N'Aspersï¿½o/gotejamento')
			
* ** [ ] Tabelas Acessï¿½rias Endereï¿½o
			Regiï¿½o Administrativa
## 26 de Setembro de 2024
- [] - Adicionar nome do ponto
	Adicione nome do ponto, por exemplo, Ponto 1, Ponto 2.
	
## 11 de Outubro de 2024
- [] - Seleção de Interferência e Finalidades
	Quando seleciona uma interferência e ela não  tem nenhuma finalidade requerida, o espaço de preencher as finalidades está ficando vazio. Adicionar
	possibilidade de inserir finalidade ao selecionar uma interferência sem finalidade requerida ou autorizada.
	
## 19 de Dezembro de 2024
- [] - Usuario
	Adicionar pesquisa por nome com ou sem acento
