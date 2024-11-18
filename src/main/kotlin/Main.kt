import java.io.File

fun convertirEnListe(chaine: String, separateur: String = " "): List<String> {
    if (chaine.isBlank()) {
        return emptyList()
    }
    return chaine.split(separateur).filter { it.isNotEmpty() }
}

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
                val liste = convertirEnListe(input ?: "")
                println("Liste obtenue : $liste")
            }
        }
        1 -> {
            // Un seul argument, lire le fichier spécifié
            val filePath = args[0]
            try {
                val file = File(filePath)
                if (!file.exists()) {
                    println("Erreur : Le fichier \"$filePath\" n'existe pas.")
                    return
                }
                val contenu = file.readText()
                println("Contenu du fichier \"$filePath\" :")
                println(contenu)
                val liste = convertirEnListe(contenu)
                println("Liste obtenue à partir du contenu : $liste")
            } catch (e: Exception) {
                println("Erreur : Impossible de lire le fichier \"$filePath\".")
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
