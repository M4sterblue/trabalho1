const val MAX_ATTEMPTS = 6
const val WRONG = '_'
const val CORRECT = '+'
const val SWAPPED = '#'

fun main() {
    val secret = words.random() // words.filter{ noRepeats(it) }.random() (fun noRepeats()).
    println("Adivinhar uma palavra com ${secret.length} letras em $MAX_ATTEMPTS tentativas.")
    var attempts = 1
    do {
        println("${attempts}ª tentativa")
        val guess: String = readGuess(secret.length)
        val result: String = getResult(secret, guess)
        println(guess)
        println(result)
        if (allCorrect(result)) break
        attempts++
    } while (attempts <= MAX_ATTEMPTS)

    val status = if (attempts > MAX_ATTEMPTS) "LOSE" else "WIN"
    println("YOU $status -> Word=$secret")
}

val words = listOf(
    "ARRAY", "CICLO", "DADOS", "DEBUG", "ERROS", "FLUXO", "LISTA", "PILHA",
    "TEXTO", "VALOR", "VETOR", "CHAVE", "CAMPO", "FONTE", "FORMA", "LINHA",
    "MUNDO", "PORTA", "PRAIA", "LIVRO", "CASAR", "PEDRA", "NUVEM", "SONHO",
    "TEMPO", "VERDE", "BANCO", "BEBER", "BRAVO", "CARRO", "COISA", "FOLHA",
    "GENTE", "GRUPO", "JOVEM", "MASSA", "NOITE", "PAPEL", "RAPAZ", "TERRA"
)

/*
fun noRepeats(s: String): Boolean {
    for (i in 0..<s.length) {

        for (j in (i + 1)..<s.length) {
            if (s[i] == s[j]) {
                return false
            }
        }
    }

    return true
}
*/

fun allCorrect(result: String): Boolean {
    for (i in 0..<result.length) {
        if (result[i] != CORRECT) {
            return false
        }
    }
    return true
}

fun readGuess(length: Int): String {
    val currentguess: String = readln().uppercase()
    return if (currentguess.length == length) {
        currentguess
    } else
        "Palavra inválida"
}

fun getResult(secret: String, guess: String): String {
    var result = ""

    for (i in 0..<guess.length) {
        val letra = guess[i]

        // Verifica se a letra na posição atual (i) da tentativa é igual à letra na mesma posição (i) da palavra secreta. Se for, adiciona '+' ao resultado

        if (letra == secret[i]) {
            result += CORRECT
        } else {

            // Conta quantas vezes a letra atual da tentativa existe dentro da palavra secret

            var totalNaSecreta = 0
            for (j in 0..<secret.length) {
                if (secret[j] == letra) {
                    totalNaSecreta++
                }
            }

            // Conta quantas vezes a letra atual da tentativa já acertou na posição exata ao longo da palavra

            var totalCorretas = 0
            for (j in 0..<secret.length) {
                if (guess[j] == secret[j] && guess[j] == letra) {
                    totalCorretas++
                }
            }

            // Conta quantas vezes a letra atual já apareceu em posições ANTERIORES da nossa tentativa, e que não estavam certas)

            var totalAnteriores = 0
            for (j in 0..<i) {
                if (guess[j] != secret[j] && guess[j] == letra) {
                    totalAnteriores++
                }
            }

            // Se ainda sobrarem ocorrências da letra na palavra secreta que não foram gastas, atribui SWAPPED, caso contrário, atribui WRONG

            if (totalNaSecreta > totalCorretas + totalAnteriores) {
                result += SWAPPED
            } else {
                result += WRONG
            }
        }
    }

    return result
}
