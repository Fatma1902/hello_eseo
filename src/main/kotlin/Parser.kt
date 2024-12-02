class Parser(private val tokens: List<Token>) {
    private var current = 0 // Indice pour suivre le token courant

    fun parse(): Expression? {
        return try {
            expression()
        } catch (e: ParseError) {
            null
        }
    }

    private fun expression(): Expression {
        return binary()
    }

    private fun binary(): Expression {
        var expr = unary()

        while (match(
                TokenType.PLUS, TokenType.MINUS, TokenType.STAR, TokenType.SLASH,
                TokenType.EQUAL_EQUAL, TokenType.BANG_EQUAL, TokenType.LESS,
                TokenType.LESS_EQUAL, TokenType.GREATER, TokenType.GREATER_EQUAL
            )) {
            val operator = previous()
            val right = unary()
            expr = Expression.Binary(expr, operator, right)
        }

        return expr
    }

    private fun unary(): Expression {
        if (match(TokenType.MINUS, TokenType.BANG)) {
            val operator = previous()
            val right = unary()
            return Expression.Unary(operator, right)
        }
        return primary()
    }

    private fun primary(): Expression {
        when {
            match(TokenType.FALSE) -> return Expression.Literal(false)
            match(TokenType.TRUE) -> return Expression.Literal(true)
            match(TokenType.NIL) -> return Expression.Literal(null)
            match(TokenType.NUMBER, TokenType.STRING) -> return Expression.Literal(previous().literal)
            match(TokenType.LEFT_PAREN) -> {
                val expr = expression()
                consume(TokenType.RIGHT_PAREN, "Expected ')' after expression.")
                return Expression.Grouping(expr)
            }
        }

        throw error(peek(), "Expected expression.")
    }

    private fun match(vararg types: TokenType): Boolean {
        for (type in types) {
            if (check(type)) {
                advance()
                return true
            }
        }
        return false
    }

    private fun check(type: TokenType): Boolean {
        return if (isAtEnd()) false else peek().tokenType == type
    }

    private fun advance(): Token {
        if (!isAtEnd()) current++
        return previous()
    }

    private fun isAtEnd(): Boolean {
        return peek().tokenType == TokenType.EOF
    }

    private fun peek(): Token {
        return tokens[current]
    }

    private fun previous(): Token {
        return tokens[current - 1]
    }

    private fun consume(type: TokenType, message: String): Token {
        if (check(type)) return advance()
        throw error(peek(), message)
    }

    private fun error(token: Token, message: String): ParseError {
        println("Error at '${token.lexeme}': $message")
        return ParseError()
    }

    class ParseError : RuntimeException()
}

