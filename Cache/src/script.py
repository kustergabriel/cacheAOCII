import struct

# Cenário para a configuração L1 (8 conjuntos, bloco 4B, assoc 2).
# - Offset: log2(4) = 2 bits
# - Índice: log2(8) = 3 bits
# - Um endereço tem o formato: [TAG | 3-bit ÍNDICE | 2-bit OFFSET]
# - O índice de um endereço é calculado por: (endereco >> 2) & 0x7 (0x7 é 7 em decimal)

enderecos = []

# --- Parte 1: Teste de Conflito e Substituição Randômica ---
# Os endereços 4, 36, e 68 todos mapeiam para o índice 1.
# Índice de 4: (4 >> 2) & 7 = 1 & 7 = 1
# Índice de 36: (36 >> 2) & 7 = 9 & 7 = 1
# Índice de 68: (68 >> 2) & 7 = 17 & 7 = 1

print("--- Gerando Parte 1: Teste de Conflito/Substituição ---")
enderecos.extend([
    4,      # Bloco 1, Índice 1. -> MISS COMP. (Ocupa a 1ª via do conjunto 1)
    8,      # Bloco 2, Índice 2. -> MISS COMP.
    36,     # Bloco 9, Índice 1. -> MISS COMP. (Ocupa a 2ª via do conjunto 1)
    4,      # Bloco 1, Índice 1. -> DEVE SER HIT! (Ambas as vias do conj. 1 estão ocupadas)
    68,     # Bloco 17, Índice 1. -> MISS DE CONFLITO. Substitui aleatoriamente 4 ou 36.
    4,      # Bloco 1, Índice 1. -> PODE SER HIT OU MISS, dependendo da substituição randômica.
    36,     # Bloco 9, Índice 1. -> PODE SER HIT OU MISS, dependendo da substituição randômica.
])


# --- Parte 2: Teste de Miss de Capacidade ---
# A cache tem capacidade para 16 blocos (64 bytes / 4 bytes/bloco).
# Vamos acessar 17 blocos únicos e que não conflitam entre si para encher a cache.
print("--- Gerando Parte 2: Teste de Capacidade ---")
for i in range(17):
    # Endereços 1024, 1028, 1032 ... não conflitam entre si nos 8 conjuntos iniciais.
    # Bloco (256+i), Índice (i % 8)
    enderecos.append(1024 + (i * 4)) # -> 17 MISSES COMPULSÓRIOS

# Agora, o primeiro bloco desta sequência (bloco de 1024) já deve ter sido expulso.
enderecos.append(1024) # -> DEVE SER MISS DE CAPACIDADE


# --- Gravando o arquivo binário ---
with open('trace2.bin', 'wb') as f:
    for end in enderecos:
        # '>' força a ordem Big-Endian, compatível com o DataInputStream do Java
        f.write(struct.pack('>I', end))

print(f"\nArquivo 'trace2.bin' criado com sucesso com {len(enderecos)} endereços.")