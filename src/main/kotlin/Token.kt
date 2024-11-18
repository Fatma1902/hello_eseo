class Token (
    val tokenType: TokenType, // Type de token défini comme une classe ou un type spécifique
    val lexeme: String,       // La représentation textuelle du token
    val literal: Any?,        // La valeur associée au token, pouvant être de n'importe quel type
    val line: Int             // Numéro de la ligne où se trouve le token
    ) {
        override fun toString(): String {
            return "Token(tokenType=$tokenType, lexeme='$lexeme', literal=$literal, line=$line)"
        }
}