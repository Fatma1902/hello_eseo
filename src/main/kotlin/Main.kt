import java.io.File

fun main(args: Array<String>) {
    when (args.size) {
        0 -> {
            // Aucun argument, boucle pour attendre l'entrée utilisateur
            while (true) {
                print("Entrez quelque chose (ou tapez 'exit' pour quitter) : ")
                val input = readLine()
                if (input.equals("exit", ignoreCase = true)) {
                    println("Au revoir !")
                    break
                }
                println("Vous avez entré : $input")
            }
        }
        1 -> {
            // Un seul argument, lire le fichier spécifié
            val fileName = args[0]
            try {
                val content = File(fileName).readText()
                println("Contenu du fichier \"$fileName\" :")
                println(content)
            } catch (e: Exception) {
                println("Erreur : Impossible de lire le fichier \"$fileName\".")
                println("Détails : ${e.message}")
            }
        }
        else -> {
            // Plus d'un argument, afficher l'aide
            afficherAide()
        }
    }
}

fun afficherAide() {
    println("Usage :")
    println("  - Sans argument : le programme attend des entrées utilisateur, les affiche et recommence.")
    println("  - Avec un seul argument : le programme lit le fichier spécifié et affiche son contenu.")
    println("  - Avec plusieurs arguments : affiche cette aide.")
}
