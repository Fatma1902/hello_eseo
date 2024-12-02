class lexer_fonc {
    fun scanTokens(source: String): List<Token> {
        val keywords = mapOf(
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

        fun scan(current: Int = 0, tokens: List<Token> = emptyList()): List<Token> {
            if (current >= source.length) {
                return tokens + Token(TokenType.EOF, "", null, 1)
            }

            val char = source[current]

            val (newTokens, nextIndex) = when (char) {
                '(' -> tokens + Token(TokenType.LEFT_PAREN, "(", null, 1) to current + 1
                ')' -> tokens + Token(TokenType.RIGHT_PAREN, ")", null, 1) to current + 1
                '{' -> tokens + Token(TokenType.LEFT_BRACE, "{", null, 1) to current + 1
                '}' -> tokens + Token(TokenType.RIGHT_BRACE, "}", null, 1) to current + 1
                ',' -> tokens + Token(TokenType.COMMA, ",", null, 1) to current + 1
                '.' -> tokens + Token(TokenType.DOT, ".", null, 1) to current + 1
                '-' -> tokens + Token(TokenType.MINUS, "-", null, 1) to current + 1
                '+' -> tokens + Token(TokenType.PLUS, "+", null, 1) to current + 1
                ';' -> tokens + Token(TokenType.SEMICOLON, ";", null, 1) to current + 1
                '*' -> tokens + Token(TokenType.STAR, "*", null, 1) to current + 1
                '!' -> tokens + Token(
                    if (source.getOrNull(current + 1) == '=') TokenType.BANG_EQUAL else TokenType.BANG,
                    if (source.getOrNull(current + 1) == '=') "!=" else "!",
                    null,
                    1
                ) to (if (source.getOrNull(current + 1) == '=') current + 2 else current + 1)
                '=' -> tokens + Token(
                    if (source.getOrNull(current + 1) == '=') TokenType.EQUAL_EQUAL else TokenType.EQUAL,
                    if (source.getOrNull(current + 1) == '=') "==" else "=",
                    null,
                    1
                ) to (if (source.getOrNull(current + 1) == '=') current + 2 else current + 1)
                '<' -> tokens + Token(
                    if (source.getOrNull(current + 1) == '=') TokenType.LESS_EQUAL else TokenType.LESS,
                    if (source.getOrNull(current + 1) == '=') "<=" else "<",
                    null,
                    1
                ) to (if (source.getOrNull(current + 1) == '=') current + 2 else current + 1)
                '>' -> tokens + Token(
                    if (source.getOrNull(current + 1) == '=') TokenType.GREATER_EQUAL else TokenType.GREATER,
                    if (source.getOrNull(current + 1) == '=') ">=" else ">",
                    null,
                    1
                ) to (if (source.getOrNull(current + 1) == '=') current + 2 else current + 1)
                '"' -> {
                    val (value, endIndex) = extractString(source, current)
                    tokens + Token(TokenType.STRING, value, value, 1) to endIndex
                }
                ' ', '\r', '\t', '\n' -> tokens to current + 1 // Ignorer les espaces
                else -> when {
                    char.isDigit() -> {
                        val (number, end) = extractNumber(source, current)
                        tokens + Token(TokenType.NUMBER, number, number.toDouble(), 1) to end
                    }
                    char.isLetter() -> {
                        val (identifier, end) = extractIdentifier(source, current)
                        val type = keywords[identifier] ?: TokenType.IDENTIFIER
                        tokens + Token(type, identifier, identifier, 1) to end
                    }
                    else -> tokens to current + 1 // Ignorer les caractères inconnus
                }
            }

            return scan(nextIndex, newTokens)
        }

        return scan()
    }

    fun extractString(source: String, start: Int): Pair<String, Int> {
        val end = source.indexOf('"', start + 1)
        if (end == -1) throw IllegalArgumentException("Unterminated string")
        return source.substring(start + 1, end) to end + 1
    }

    fun extractNumber(source: String, start: Int): Pair<String, Int> {
        val regex = Regex("^\\d+(\\.\\d+)?")
        val match = regex.find(source.substring(start)) ?: throw IllegalArgumentException("Invalid number format")
        return match.value to start + match.value.length
    }

    fun extractIdentifier(source: String, start: Int): Pair<String, Int> {
        val regex = Regex("^\\w+")
        val match = regex.find(source.substring(start)) ?: throw IllegalArgumentException("Invalid identifier")
        return match.value to start + match.value.length
    }

}