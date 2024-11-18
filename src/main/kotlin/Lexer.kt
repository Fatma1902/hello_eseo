class Lexer(private val source: String) {
    private var current = 0 // Position actuelle dans la chaîne
    private val tokens = mutableListOf<Token>() // Liste des tokens générés

    // Dictionnaire des mots-clés
    private val keywords = mapOf(
        "and" to TokenType.AND,
        "class" to TokenType.CLASS,
        "else" to TokenType.ELSE,
        "false" to TokenType.FALSE,
        "for" to TokenType.FOR,
        "fun" to TokenType.FUN,
        "if" to TokenType.IF,
        "nil" to TokenType.NIL,
        "or" to TokenType.OR,
        "print" to TokenType.PRINT,
        "return" to TokenType.RETURN,
        "super" to TokenType.SUPER,
        "this" to TokenType.THIS,
        "true" to TokenType.TRUE,
        "var" to TokenType.VAR,
        "while" to TokenType.WHILE
    )

    fun scanTokens(): List<Token> {
        while (!isAtEnd()) {
            scanToken()
        }
        tokens.add(Token(TokenType.EOF, "", null, 1)) // Ajouter un token de fin
        return tokens
    }

    private fun scanToken() {
        when (val char = advance()) {
            // Parenthèses et accolades
            '(' -> addToken(TokenType.LEFT_PAREN)
            ')' -> addToken(TokenType.RIGHT_PAREN)
            '{' -> addToken(TokenType.LEFT_BRACE)
            '}' -> addToken(TokenType.RIGHT_BRACE)

            // Opérateurs simples
            ',' -> addToken(TokenType.COMMA)
            '.' -> addToken(TokenType.DOT)
            '-' -> addToken(TokenType.MINUS)
            '+' -> addToken(TokenType.PLUS)
            ';' -> addToken(TokenType.SEMICOLON)
            '*' -> addToken(TokenType.STAR)

            // Opérateurs logiques et comparateurs
            '!' -> addToken(if (match('=')) TokenType.BANG_EQUAL else TokenType.BANG)
            '=' -> addToken(if (match('=')) TokenType.EQUAL_EQUAL else TokenType.EQUAL)
            '<' -> addToken(if (match('=')) TokenType.LESS_EQUAL else TokenType.LESS)
            '>' -> addToken(if (match('=')) TokenType.GREATER_EQUAL else TokenType.GREATER)

            // Chaînes de caractères
            '"' -> string()

            // Espaces blancs (à ignorer)
            ' ', '\r', '\t', '\n' -> Unit

            else -> {
                when {
                    char.isDigit() -> number() // Identifier un nombre
                    char.isLetter() -> identifier() // Identifier un mot-clé ou un identifiant
                    else -> error("Caractère inattendu : $char") // Gestion des erreurs
                }
            }
        }
    }

    private fun advance(): Char = source[current++] // Avance et retourne le caractère courant

    private fun isAtEnd(): Boolean = current >= source.length // Vérifie si on est à la fin

    private fun match(expected: Char): Boolean {
        if (isAtEnd() || source[current] != expected) return false
        current++
        return true
    }

    private fun peek(): Char = if (isAtEnd()) '\u0000' else source[current] // Regarder le prochain caractère

    private fun peekNext(): Char = if (current + 1 >= source.length) '\u0000' else source[current + 1] // Regarder le caractère suivant

    private fun addToken(type: TokenType, literal: Any? = null) {
        val lexeme = source.substring(current - 1, current) // Récupère le texte du token
        tokens.add(Token(type, lexeme, literal, 1)) // Ajoute un token
    }

    private fun string() {
        val start = current // Position de début de la chaîne
        while (!isAtEnd() && peek() != '"') advance() // Avance jusqu'à la fin de la chaîne
        if (isAtEnd()) error("Chaîne non terminée")
        advance() // Consommer le guillemet fermant
        val value = source.substring(start, current - 1) // Contenu de la chaîne sans guillemets
        tokens.add(Token(TokenType.STRING, value, value, 1))
    }

    private fun number() {
        val start = current - 1
        while (!isAtEnd() && peek().isDigit()) advance()

        if (!isAtEnd() && peek() == '.' && peekNext().isDigit()) {
            advance() // Consommer le "."
            while (!isAtEnd() && peek().isDigit()) advance()
        }

        val lexeme = source.substring(start, current)
        addToken(TokenType.NUMBER, lexeme.toDouble())
    }

    private fun identifier() {
        val start = current - 1
        while (!isAtEnd() && peek().isLetterOrDigit()) advance()
        val lexeme = source.substring(start, current)
        val type = keywords[lexeme] ?: TokenType.IDENTIFIER
        tokens.add(Token(type, lexeme, lexeme, 1))
    }
}
