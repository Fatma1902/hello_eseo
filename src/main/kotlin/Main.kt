import java.io.File

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Erreur : Aucun fichier spécifié.")
        println("Utilisation : <programme> <chemin_du_fichier>")
        return
    }

    val fileName = args[0] // Premier argument : le chemin du fichier
    val file = File(fileName)

    if (!file.exists()) {
        println("Erreur : Le fichier '$fileName' n'existe pas.")
        return
    }

    try {
        val lines = file.readLines()

        println("Traitement des expressions du fichier :")
        lines.forEachIndexed { index, line ->
            if (line.isBlank()) return@forEachIndexed // Ignorer les lignes vides

            println("Expression ${index + 1} : $line")
            try {
                // Tokenisation
                val lexer = Lexer(line)
                val tokens = lexer.scanTokens()
                println("  Tokens : $tokens")

                // Parsing
                val parser = Parser(tokens)
                val ast = parser.parse()
                println("  AST : $ast")

                // Évaluation
                val evaluator = Evaluator()
                val result = ast?.let { evaluator.evaluate(it) }
                println("  Résultat : $result")
            } catch (e: Exception) {
                println("  Erreur : ${e.message}")
            }
        }
    } catch (e: Exception) {
        println("Erreur lors de la lecture du fichier : ${e.message}")
    }
}


