# Lista de Tarefas

## 5 de novembro de 2023

* **[X] Tarefa 1 - Classe**
    * A classe `ResizeAnimation` deve funcionar em um ouvinte que, ao verificar que o `apMap` mudou a largura, redimensiona a largura do mapa.
    * 07/11/2023 - Resolvido.
        * Retirada a classe `ResizeAnimation`, criada a classe `ResizeMap`.

## 7 de novembro de 2023

* **[X] Tarefa 1 - DocumentoController - CSS**
    * Adicionar `on focus`, cor secund�ria ao combobox.
    
#06/02/2025
* **[X] Endereço na Parecer - Não está trazendo endereço no parecer de direito de uso. No de outorga prévia está trazendo.
* **[X] Unidade e Bacia - Adicionar no parecer e despachos se necessário.
* **[X] Parecer - Tabela 1 - Formatar número de vazão, ex: 38272 para 38.272,00 Demanda (L/dia) e Demanda Total (L/dia)d

* **[X] Tarefa 2 - DocumentoController - CSS**
   * O Combobox está desalinhado com os textfields.
    	30/11/2023 - Resolvido isto bem como todo css do combobox.

## 13 de novembro de 2023

* **[X] Tarefa 1 - Adicionar atualização da visualização do documento com o item selecionado pelo usu�rio.**
	30/11/2023 - Resolvido com a mudan�a da biblioteca Gojs para E-chart.js.

## 19 de novembro de 2023

* **[X] Tarefa 1**
    * Avaliar o erro "Connection refused". Talvez mostrar somente o mapa, sem a tela de cadastro e um tooltip de erro de conex�o.
    		Ou seja, o programa abrir com suas telas mesmo sem conex�o.
    		30/12/2023 - A primeira conex�o � com o login, ent�o se n�o h� login n�o � preciso abrir mais nada.

## 28 de novembro de 2023

* **[X] Tarefa 1 - Criar tooltips para todos os componentes da tela documentos**

* **[X] Tarefa 2 - Adicionar e-chart substituindo Gojs nos diagramas.**

* **[ ] Tarefa 3 - Cadastrar documento sem alguns dados, como SEI. N�o está funcionando na edição, por exemplo.**

## 30 de novembro de 2023

* **[ ] Tarefa 1 - Poder inserir processo novo com apenas um número.**
	* Se digitar 3 n�o aparece este número na lista do combobox, mas apenas aqueles processos que tem o número 3 em algum lugar do número completo. Desta forma n�o posso inserir um processo novo digitando apenas um número.

## 01 de dezembro de 2023
* **[ ] Tarefa 1 - Número Sei - Tela Documentos**
	* Quando temos um documento sem o número sei e outro com o número sei, ao escolher na table list o documento sem o número sei o número que aparece no textfield � o do documento com número. N�o deveria aparecer nada, mas continua o número do documento anterior, que tem o número.
	
* **[ ] Tarefa 2 - Pesquisa número sei**
	* A pesquisa s� está sendo pelo número completo do sei, deveria ser qualquer parte do número. Converter ent�o o docSei para string e pesquisar semelhan�as, parte do número etc.
	
## 18 de dezembro de 2023

* **[ ] Tarefa 1 - Tipo de Documento**
	* O Combobox tipo de documento está desalinhado com os outros componentes.
* **[ ] Tarefa 2 - Lista de Documentos**
	* Os valores na linha da tabela estáo mais alto do que os valores no combobox Açães, na lista de documentos.
	
* **[ ] Tarefa 3 - De alguma forma o sistema está salvando um documento sem Endereço selecionado como endId = 1.

## 19 de Dezembro de 2023
* **[ ] Tarefa 1 - Gmaps API Key**
	* Adicionar no banco de dados para que seja f�cil modificar.
	
## 23 de Fevereiro de 2024
* **[ ] Tarefa 1 - Mudar bando e Tela de Cadastro de Documentos**
	* Mudar a conex�o com novo banco e mudar a tela da cadastro de documentos
		* Para isso foi criado o `DocumentController2` e `Documents2.fxml`. S�o nomes provis�rios, depois serê deletado os arquivos `DocumentController` e `Documents.fxml`.
		* Tamb�m foi criado o servi�o `j-water-grants` com novas tabelas e novos relacionamentos. Por exemplo, um documento tem v�rios Endereços e um Endereço tem v�rios documentos.
		
## 03 de Junho de 2024
* **[x] Git pull para adicionar Leaflet Maps.

## 04 de junho de 2024
* ** [X] Verificar o objeto selecionado no mapa.
	Criar um listener para visualizar o objeto selecionado no mapa.
		26/06/2024, Obs: foi criado um listener para receber a coordenada do mapa e tamb�m mostrar a coordenada escrita pelo usu�rio no mapa.
	
## 26 de junho de 2024
* ** [ ] Lista de Bairros, Cidade e Cep.
	Adicionar lista de bairros de acordo com o que o usu�rio preenche. Por exemplo, a pessoa cadastra o bairro Taguatina Norte, isso j� pode ser sugerido da prêxima vez.
	[ ] Tabelas Acessórias da Interferência
		Criar as tabelas Acessórias como tipo de interferência, tipo de outorga, subtipo de outorga etc...
## 28 de Junho de 2024
* ** [ ] AddInterferenceControler
		A clicar no mapa � atualizado tanto este controller quanto o InterferenceTextFieldsController, mas quando abre o AddInterferenceController, ao clicar no mapa n�o atualiza a coordenadas mais.
* ** [ ] �cones Tela Documento
		O css n�o está funcionando, mas funciona na tela AddInterferenceController. Nesta tela o css, classe icons funciona o hover que aumenta o �cone ao passar o mouse. Deveria funcionar na tela Documentos.
		
		
## 30 de Julho de 2024
* **[X] Seleção de Interferências
	Ao selecionar uma interferência está sendo feito selects no banco desnecess�rios que al�m disso fazem o programa ficar lento.
		A solução � adicionar o item selecionado na lista, no caso, que foi feito no controlador dos processos, `dbObjects`. � igual para todos os Combobox edit�veis.

## 31 de Julho de 2024
* ** [ ] Tabelas Acessórias da Interferência	
 			TIPO_INTERFERENCIA
 				(1, N'Superficial')
				(2, N'Subterrênea')
				(3, N'Lançamento de �guas Pluviais')
				(4, N'Canal')
				(5, N'Caminh�o Pipa')
				(6, N'Lançamento de Efluentes')
				(7, N'Barragem')
			Tipo_Outorga
				(1, N'Outorga')
				(2, N'Outorga Prêvia')
				(3, N'Registro')
			Subtipo_Outorga
				(1, N'Renovação')
				(2, N'Modificação')
				(3, N'Transferência')
				(4, N'Suspens�o/Revogação')
				(5, N'')
			Situacao_Processo
				(1, N'Arquivado')
				(2, N'Em An�lise')
				(3, N'Outorgado')
				(4, N'Vencida')
				(5, N'Arquivado (CNRH 16)')
				(6, N'Pend�ncia')
				(7, N'Indeferido')
				(8, N'Revogado')
			Tipo_Ato
				 (1, N'Despacho')
				 (2, N'Portaria')
				 (3, N'Registro')
				 (4, N'Resolução')
				 (5, N'Resolução ANA')
				 (6, N'Portaria DNAEE')
			Bacia Hidrogrêfica
			UNIDADE_HIDROGRAFICA
		
* ** [ ] Tabelas Acessórias Subterrêneo
			SubSistema
			Tipo Po�o
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
				(1, N'Aspers�o')
				(2, N'Gotejamento')
				(3, N'Piv�')
				(4, N'Manual')
				(5, N'Aspers�o/gotejamento')
			
* ** [ ] Tabelas Acessórias Endereço
			Regi�o Administrativa
## 26 de Setembro de 2024
* **[X] - Adicionar nome do ponto
	Adicione nome do ponto, por exemplo, Ponto 1, Ponto 2.
	
## 11 de Outubro de 2024
* **[X] - Seleção de Interferência e Finalidades
	Quando seleciona uma interferência e ela n�o  tem nenhuma finalidade requerida, o espa�o de preencher as finalidades está ficando vazio. Adicionar
	possibilidade de inserir finalidade ao selecionar uma interferência sem finalidade requerida ou autorizada.
	
## 19 de Dezembro de 2024
* **[X] - Usuario
	Adicionar pesquisa por nome com ou sem acento

## 23 de Dezembro de 2024
* **[X] - Tela Adicionar Usuário
	Adicionar busca por documentos
	
* **[X] - Tela Adicionar Usuário
	 * **[X] - Listar Endereços
	 		Foi adicionado lista de documentos relacionados com o usuário
	 
* **[X] - Finalidade Requerida - Cópia
	 * **[X] Adicionar botão de cópia da finalidade requerida na tela finalidade autorizada.

## 27 de Dezembro de 2024
* **[X] Para compilar sem testes e sem gerar documentação
	mvn clean install -DskipTests -Dmaven.javadoc.skip=true

* **[X] Adicioanr conversor de coordenadas
	 
* **[X] Verificar edição de processo e anexo na tela documento

* **[X] Ao salvar o documento não está relacionando o usuário selecionado
	
## 20/01/2025
* **[X] Preencher o total de vazao solicitada nas finalidades autorizadas.
	
* **[X] A vazão requerida nas demandas não está sendo copiada para as vazões autorizadas
	- Resolvido em 21/01/2025
	
## 21/01/2025
* **[X] Adicionar na tela de edição de usuário a possíbilidade de editar relacionamentos com documentos, só assim é possível
deletar um usuário ou documento, deletando o relacionamento usuario_documento

* **[X] Se possível adicionar procura por processo também pelo nome do usuário

## 05/02/2025

* **[X] Endereço - Adicionar DF como seleção inicial no combobox de edição de endereço
* **[X] Endereço - Adicionar todos os estados no banco para mostrá-los no combobox de Estados

* **[X] Combobox e Css - Rever para que o prompt text apareça acima de todos os comobox ao selecionar.
* **[X] Adicionar no preenchimento automático de subsistema a vazão do subsistema.
* **[X] Adicionar conversor de coordenadas
* **[X] Rever tipo de número nas finalidades, se double ou integer e ao preencher utilizar o padrão do banco de dados.
		* **[X] Aqui é melhor colocar máscara, ver depois. Másca para trocar vírgula ao salvar por ponto, ex: 12,2 para 10.2
* **[X] Endereço - aumentar quantidade de caracteres
	O endereço está com 500 carateres, o suficiente.

## 07/02/2025	
* **[X] Adicionar lista de região administrativa, lista de finalidades e subfinalidades
* **[X] Aumentar quantidade de caracteres nas finalidades e subfinalidades	
* **[X] Parecer de Direito
		Rever assunto, vírgula desnecessária: ... construção civil, e abastecimento humano.
		-[X] Cnpj desformatado: CPF/CNPJ: 1585584000138
		-[X] Tipo de Poço: ...de 01 (um) poço (tubular ou manual)
		-[X] Endereço: está sem endereço
		-[X] Finalidade: para fins de irrigação paisagística - (0,2 ha - paisagismo) 
* **[X] Finalidade
		Ao copiar as finalidades requeridas para as autorizadas está aumentando uma vazia na autorizada.
		Resolvido.
* **[X] Bacia Hidrográfica e Unidade - Adicionar no parecer
	Resolvido.
* **[X] Processo
		Ao salvar o processo, adicioanr cnpj no usuário.
		Resolvido: ao salvar o processo só é preciso o relacionamento com o usuário, sem necessidade
			de informar o cpf/cnpj.
			
* **[X] Finalidades
		Adicionar quantidade maior de caracteres.
		Resolvido: aumentei para 70 caracteres na escrita da finalidade e subfinalidade
* **[X] Planílha de Cálculo de Finalidade
		Adicionar planilha de cálculo de finalidade
		10/02/2025 - Não será feito agora. No momento apenas foi aberto o cadastro das finalidades para o técnico
	colocar os valores que quiser.
* **[X] Parecer
		Adicionar nível estático e dinâmico, vazão de teste
	
## 10 de fevereiro de 2025

* **[X] Domínio
	Trazer dados do poço e do domínio no parecer
	
	
* **[X] Compile
	mvn clean package -DskipTests -Dmaven.javadoc.skip=true
		
## 11 de fevereiro de 2025

* **[X] Usuário não cadastrado
	Adicionar aviso de usuário não cadastrado ao cadastrar documento. Ver também de avisar de interferência não cadastrada.
	
## 12 de fevereiro de 2025	
* **[X] Adicionar bairro no endereço dos documentos.

## 12 de fevereiro de 2025
* **[X] Salvar Interferência
	Colocar advertência de bacia hidrográfica
* **[X] Calculos de Vazão Mês a Mes
	O preenchimento em meses de chuva não está funcionando
* **[X] Verificar a possiblidade de editar o tipo de poço e subsistemas
* **[X] ver processo 571/2014, não está saindo o subsistema, só o sitema.
* **[] criar funçãos para outorgas subterraneas, find point inside system (fraturado e poroso)
	18/02/2025 - Estas funções estão no banco supabase (njs-drainage). Devem ser passadas para o novo banco.
	
## 18 de fevereiro de 2025
* **[X] Adicionar tela de disponibilidade

## 20 de fevereiro de 2025
* **[X] Conversor de coordenadas adicionado


## 31/03/2025
* ** [X] Http Server e Mapa
	Testar se melhora a velociadade de leitura do mapa se for utilizado o httpserver.
	Não vou utilizar
	
## 10/04/2025
* ** [X] Mapa 
	Mudança para MapJFX
	
## 22/04/2025
* ** [X] Adicionar Versão Maven no state (setTitle)
	
* ** [X] Rever ordem de ítems no parecer de outorga prévia.

* ** [X] Adicionar no parecer a vazão de abril, para não ficar vazia
	Tempo de captação máximo de...
	
## 23/04/2025
* ** [] Adicionar pesquisa de interferência por coordenada em string. Assim dá pra encontar uma interferência com endereço errado.	



* ** [] Editar Nome e Cpf
	Vefificar se é possível editar o nome relacionado com o cpf ou cnpj
* ** [] Buscar usuário por nome ou cpf/cnpj

* ** [] Centralizar texto nas colunas das tabelas

## 05/05/2025
* ** [] Endereço e Bairro
	Quando salva um documento novo, com endereço novo e abre a visualização não está indo o bairro.
	
	

mvn clean package -DskipTests -Dmaven.javadoc.skip=true
 
