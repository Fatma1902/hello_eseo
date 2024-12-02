fun main() {
    // Exemple de texte à analyser
    val texte = """
        var x = 42;
        if (x > 10) {
            print "x est grand";
        }
    """.trimIndent()

    // Création d'une instance de `lexer_fonc` et analyse des tokens
    val lexer = lexer_fonc()
    val tokens = lexer.scanTokens(texte)

    // Affichage des tokens générés
    println("Tokens générés :")
    tokens.forEach { println(it) }
}
