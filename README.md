## Simulador de uma memoria cache feito em Java
##### Este simulador segue a seguinte estrutura, temos 4 Classes: Cache, ConjuntoCache, LinhaCache e Simulador. Vou comecar explicando de baixo para cima (do mais interno ao mais externo). 
##### Na classe LinhaCache nos temos o que vai ser responsavel por armazenar a tag da cache e o bit de validade para poder classificar os misses.
##### A classe ConjuntoCache sera responsavel por criar cada conjunto da nossa cache de acordo com a associativade passada pelo parametro.
##### Na classe Cache nos temos a classe mais robusta, a que é responsavel por armazenar os parametros de nsets, block size e a associativade, e também ela é capaz de fazer a ligação com os outros niveis de cache.
##### E finalmente a classe Simulador que é a classe que vai ligar todos essas outras classes para que de fato a memória cache seja funcional, nela chamamos todos os outros construtores e instanciamos os niveis de cache. 

###### Projeto feito por: Gabriel Kuster e Junior Prediger
