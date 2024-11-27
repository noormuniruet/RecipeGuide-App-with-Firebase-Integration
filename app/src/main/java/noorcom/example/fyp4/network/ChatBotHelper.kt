//package noorcom.example.fyp4.network
//
//class BotRepository(private val api: BotpressApi) {
//    suspend fun sendMessageToBot(message: String): String {
//        val response = api.sendMessageToBot(message)
//        if (response.isSuccessful) {
//            return "No response from the bot"
//        } else {
//            throw Exception("Error: ${response.code()} - ${response.message()}")
//        }
//    }
//}
//
//
//
//
//
//
package noorcom.example.fyp4.network

// Define the BotMessage and Message classes to match the API
data class BotMessage(val type: String = "text", val text: String)
data class Message(val type: String, val text: String)
data class BotResponse(val messages: List<Message>)

class BotRepository(private val api: BotpressApi) {

    // This function now correctly sends a structured BotMessage to Botpress
    suspend fun sendMessageToBot(userMessage: String, botId: String, userId: String): List<Message>? {
        val botMessage = BotMessage(text = userMessage) // Create BotMessage from user input

        // Sending the message to Botpress API
        val response = api.sendMessageToBot(botId, userId, botMessage)  // Assuming the correct endpoint exists in BotpressApi

        // If the API response is successful, return the list of messages
        if (response.isSuccessful) {
            return response.body()?.messages // Return the bot's messages
        } else {
            throw Exception("Error: ${response.code()} - ${response.message()}")  // Handle errors
        }
    }
}

