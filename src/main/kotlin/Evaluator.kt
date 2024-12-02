class Evaluator {

    fun evaluate(expression: Expression): Any? {
        return when (expression) {
            is Expression.Literal -> expression.value // Retourne la valeur littérale
            is Expression.Grouping -> evaluate(expression.expression) // Évalue l'expression groupée
            is Expression.Unary -> {
                val right = evaluate(expression.right)
                when (expression.operator.tokenType) {
                    TokenType.MINUS -> -(right as Double) // Opération unaire négative
                    TokenType.BANG -> !(right as Boolean) // Opération logique NOT
                    else -> throw IllegalArgumentException("Opérateur unaire inconnu : ${expression.operator}")
                }
            }
            is Expression.Binary -> {
                val left = evaluate(expression.left)
                val right = evaluate(expression.right)

                when (expression.operator.tokenType) {
                    TokenType.PLUS -> (left as Double) + (right as Double) // Addition
                    TokenType.MINUS -> (left as Double) - (right as Double) // Soustraction
                    TokenType.STAR -> (left as Double) * (right as Double) // Multiplication
                    TokenType.SLASH -> (left as Double) / (right as Double) // Division
                    TokenType.EQUAL_EQUAL -> left == right // Comparaison d'égalité
                    TokenType.BANG_EQUAL -> left != right // Comparaison d'inégalité
                    TokenType.GREATER -> (left as Double) > (right as Double) // Plus grand
                    TokenType.GREATER_EQUAL -> (left as Double) >= (right as Double) // Plus grand ou égal
                    TokenType.LESS -> (left as Double) < (right as Double) // Plus petit
                    TokenType.LESS_EQUAL -> (left as Double) <= (right as Double) // Plus petit ou égal
                    else -> throw IllegalArgumentException("Opérateur binaire inconnu : ${expression.operator}")
                }
            }
        }
    }
}
