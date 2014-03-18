args <- commandArgs(TRUE)

arq <- read.table(args[1], header=FALSE)

#customers
for (c in 1:100) {
	cust <- subset(arq, arq[1] == c)

	result <- c
	for(col in 2:11) {
#		avg <- round( mean(cust[col]), 2)
		result <- paste(result, colMeans(cust[col]))
	}

	print(result, quote = FALSE)
}

#dados <- subset(arq, arq$gerador == "PSEUDO")


# Maior solucao Pseudo com as casas decimais ajustadas
#solMaxPseudo <- round( max(dadosPseudo$sol), 2)

# Media solucao Pseudo com as casas decimais ajustadas
#solMediaPseudo <- round( mean(dadosPseudo$sol), 2)

# DP solucao Pseudo com as casas decimais ajustadas
#solDPPseudo <- round( sd(dadosPseudo$sol), 2)


# Teste Wilcox
#test <- wilcox.test(dadosPseudoRound, dadosMdRound)
#pvalue <- round(test$p.value, 2)


# Calculo do Effect Size
#rx = sum(rank(c(dadosPseudoRound, dadosMdRound))[seq_along(dadosPseudoRound)]);
#es = (rx / length(dadosPseudoRound) - (length(dadosPseudoRound) + 1) / 2) / length(dadosMdRound);
#es <- round(es, 2)


# Formando a linha de saida do script
#saida <- paste(inst, solMaxPseudo)
#saida <- paste(saida, solMediaPseudo)
#saida <- paste(saida, solDPPseudo)


#print(saida, quote = FALSE)
