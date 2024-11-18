enum class TokenType {
        // Parenthèses et accolades
        LEFT_PAREN, RIGHT_PAREN, // (, )
        LEFT_BRACE, RIGHT_BRACE, // {, }

        // Opérateurs
        COMMA, DOT, MINUS, PLUS, SEMICOLON, SLASH, STAR, // , . - + ; / *

        // Opérateurs logiques et comparateurs
        BANG, BANG_EQUAL,       // !, !=
        EQUAL, EQUAL_EQUAL,     // =, ==
        GREATER, GREATER_EQUAL, // >, >=
        LESS, LESS_EQUAL,       // <, <=

        // Littéraux
        IDENTIFIER, STRING, NUMBER, // variable, "texte", 123

        // keywords
        AND, CLASS, ELSE, FALSE, FUN, FOR, IF, NIL, OR, PRINT, RETURN, SUPER, THIS, TRUE, VAR, WHILE,

        // Fin de fichier
        EOF




}