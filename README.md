# Desafio_GoDev
 Projeto de software do programa Desafio_GoDev 2021 entre a Senior e a ProWay.

	1. Descrição 
 
 1.1. Visão geral: O sistema presente nesse repositório serve para gerenciar um treinamento realizado em duas etapas em diversas salas com lotação variável. 
 1.2. O problema: Nessa situação, haverá um treinamento em duas etapas onde as pessoas serão dividias em várias salas com lotação variável. Também terão dois
      intervalos durante o treinamento.

	2. O projeto 
	
 2.1. Visão geral: nenhuma bilbioteca foi importada para o projeto além das padrões do Java e ele possui 6 classes dividias em três pacotes. 
 2.2 Estrutura: o projeto está dividido em três grandes módulos: um módulo para a interface, outro para os objetos e outro para a classe que o inicia. Essa estrutura 
     foi escolhida para que todas as classes fiquem bem dividas e organizadas, deixando assim, o projeto mais apresentável e com as tarefas melhor distribuidas.

	3. Testes 
	
 3.1. Abordagem: Os testes realizados consistiam em cadastrar diversas pessoas e salas (de evento e café) para verificar se tanto o cadastro, quanto a troca de pessoas 
      entre as salas estavam funcionando conforme o esperado. 
 3.2. Resultados: Após a executação dos testes, viu-se que tanto o cadastro quanto a troca de pessoas estava funcionando corretamente. Outro ponto notado foi que o 
      requisito de que a diferença de pessoas entre as salas seja de no máximo uma pessoa foi atendido.

	4. Como funciona: 
	
  O software apresenta cinco telas, sendo duas para cadastro, uma para consulta, outra para gerenciamento das salas e outra para gerenciamento do treinamento. No cadastro de 
 pessoas, basta informar o nome e o sobrenome. No cadastro de salas, deve-ser informar o tipo da sala (evento ou café) e então nomeá-la. Para realizar a busca de uma
 pessoa, basta informar o primeiro nome que será mostrado seu nome completo e as salas em que ficará. Na consulta de salas, informe o tipo e o nome que trará todos as pessoas 
 que ela receberá durante o treinamento. Na aba de gerenciamento de salas, você poderá adicionar ou remover pessoas à sala que informar pela combobox. Na tela de gerenciamento 
 do evento, você verá o status atual do treinamento e também poderá iniciar ele, ir para o intervalo. 
 
 Observação: 1. ao ir para o intervalo, metade das pessoas terão uma de suas sala de evento alterada para que haja maior interação. 
             2. o evento só será iniciado caso a diferença de pessoas em todas as salas seja de no máximo uma.
