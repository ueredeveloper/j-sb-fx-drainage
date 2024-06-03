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
    		30/12/2023 - A primeira conexão é com o login, então se não há login não é precido abrir mais nada.

## 28 de novembro de 2023

* **[X] Tarefa 1 - Criar tooltips para todos os componentes da tela documentos**

* **[X] Tarefa 2 - Adicionar e-chart substituindo Gojs nos diagramas.**

* **[ ] Tarefa 3 - Cadastrar documento sem alguns dados, como SEI. Não está funcionando na edição, por exemplo.**

## 30 de novembro de 2023

* **[ ] Tarefa 1 - Poder inserir processo novo com apenas um número.**
	* Se digitar 3 não aparece este número na lista do combobox, mas apenas aqueles processos que tem o número 3 em algum lugar do número completo. Desta forma não posso inserir um processo novo digitando apenas um número.

## 01 de dezembro de 2023
* **[] Tarefa 1 - Número Sei - Tela Documentos
	* Quando temos um documento sem o número sei e outro com o número sei, ao escolher na table list o documento sem o número sei o número que aparece no textfield é o do documento com número. Não deveria aparecer nada, mas continua o número do documento anterior, que tem o número.
	
* ** [] Tarefa 2 - Pesquisa número sei
	* A pesquisa só está sendo pelo número completo do sei, deveria ser  qualquer parte do número. Converter então o docSei para string e pesquisar semelhanças, parte do número etc.
	

## 18 de dezembro de 2023

* **[] Tarefa 1 - Tipo de Documento
	* O Combobox tipo de documento está desalinhado com os outros componentes.
* **[] Tarefa 2 - Lista de Documentos
	* Os valores na linha da tabela está mais alto do que os valores no combobox Ações, na lista de documentos.
	
* **[] Tarefa 3 - De alguma forma o sistema está salvando um documento sem endereço selcionado como endId = 1.

## 19 de Dezembro de 2023
* **[] Tarefa 1 - Gmaps API Key
	* Adicionar no banco de dados para que seja fácil modificar.
	
## 23 de Fevereiro de 2024
* **[] Tarefa 1 - Mudar bando e Tela de Cadastro de Documentos
	* Mudar a conexão  com novo banco e mudar a tela da cadastro de documentos
		* Para isso foi criado o `DocumentController2` e `Documents2.fxml`. São nomes provisórios, depois será deletado os arquivos `DocumentController` e `Documents.fxml`.
		* Também foi criado o serviço `j-water-grants` com novas tabelas e novos relacionamentos. Por exemplo, um documento tem vários endereços e um endereço tem vários documentos.
		
		



