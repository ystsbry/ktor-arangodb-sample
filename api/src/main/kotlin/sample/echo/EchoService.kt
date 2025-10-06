package sample.echo

object EchoService {
    fun getEchoMessage(msg: String): String = "you said: ${msg}"
}