fun main() {
    // Exemple de tokens générés
    val tokens = listOf(
        Token(TokenType.NUMBER, "2", 2.0, 1),
        Token(TokenType.PLUS, "+", null, 1),
        Token(TokenType.NUMBER, "3", 3.0, 1),
        Token(TokenType.MINUS, "-", 3.0, 1),
        Token(TokenType.EOF, "", null, 1)
    )


    // Initialisation du parser avec les tokens
    val parser = Parser(tokens)

    // Analyse des tokens pour générer l'arbre syntaxique
    val expression = parser.parse()

    // Affichage de l'AST (arbre syntaxique abstrait)
    println(expression)
}
