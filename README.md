## Simulador de uma memoria cache feito em Java
#### A arquitetura do simulador de memória cache em Java foi projetada de forma modular, dividida em quatro classes principais que colaboram para modelar o comportamento de uma hierarquia de memória. A estrutura é organizada da seguinte forma:
#### A classe LinhaCache é a unidade atômica da estrutura da cache. Cada instância desta classe representa uma única linha de cache e encapsula seus atributos fundamentais:
* A tag de endereço, utilizada para identificar unicamente o bloco de memória principal mapeado.
* O bit de validade, que indica se os dados na linha são válidos ou se a linha está livre.
#### A classe ConjuntoCache funciona como um agregador, gerenciando uma coleção de objetos LinhaCache. A quantidade de linhas em cada conjunto é definida pelo nível de associatividade da cache. Esta classe é responsável por implementar a lógica de busca de uma tag dentro do conjunto e, tipicamente, pela política de substituição (No nosso caso RANDOM) quando um novo bloco precisa ser alocado em um conjunto cheio.
#### A classe Cache, esta é a classe mais robusta, responsável por modelar um nível completo de cache (L1, L2, etc.). Ela orquestra o funcionamento dos ConjuntoCache e armazena os principais parâmetros de configuração:
* Número de Conjuntos (nsets): Define a quantidade total de conjuntos na cache.
* Tamanho do Bloco (bsize): Especifica o tamanho, em bytes, de cada bloco de dados.
* Associatividade (assoc): Determina o número de linhas por conjunto.
#### Finalmente, a classe Simulador é o ponto de entrada (entry point) e o componente que integra toda a simulação. Suas responsabilidades incluem:
* Inicializar a hierarquia de memória, instanciando os níveis de cache com base nos parâmetros fornecidos.
* Processar um arquivo de traços (trace) de acessos à memória.
* Delegar cada acesso ao nível apropriado de cache.
* Coletar e reportar as métricas de desempenho, como taxas de hit e miss.
#### Em suma, o Simulador une todos os componentes, transformando as estruturas de dados em um sistema funcional e coeso.

###### Projeto feito por: Gabriel Kuster e Junior Prediger
