//package noorcom.example.fyp4.network
//
//import retrofit2.Response
//import retrofit2.http.Body
//import retrofit2.http.POST
//
//interface BotpressApi {
//    // Replace "bot/{botId}/converse/user/{userId}" with the actual endpoint
//    @POST("bot/{botId}/converse/user/{userId}")
//    suspend fun sendMessageToBot(@Body message: String): Response<BotResponse>
//}
//
//// Models for request and response
//data class BotMessage(val type: String = "text", val text: String)
//data class BotResponse(val messages: List<Message>)
//
//data class Message(val type: String, val text: String)
package noorcom.example.fyp4.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface BotpressApi {

    // This endpoint assumes you're sending a message to a specific bot and user
    @POST("bot/{botId}/converse/user/{userId}")
    suspend fun sendMessageToBot(
        @Path("botId") botId: String,  // Bot ID as a path parameter
        @Path("userId") userId: String, // User ID as a path parameter
        @Body message: BotMessage  // Send the BotMessage as the body
    ): Response<BotResponse> // Response contains the list of messages from the bot
}
