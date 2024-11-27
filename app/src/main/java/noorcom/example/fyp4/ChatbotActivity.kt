package noorcom.example.fyp4

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import noorcom.example.fyp4.adapters.ChatAdapter
import noorcom.example.fyp4.network.BotRepository
import noorcom.example.fyp4.network.BotpressApi
import noorcom.example.fyp4.network.RetrofitClient
import noorcom.example.fyp4.network.Message

class ChatbotActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var chatMessageInput: EditText
    private lateinit var sendButton: Button

    private val messageList = mutableListOf<String>()
    private val adapter = ChatAdapter(messageList)
    private val botpressApi = RetrofitClient.instance.create(BotpressApi::class.java)
    private val botRepository = BotRepository(botpressApi)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)

        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        chatMessageInput = findViewById(R.id.chatMessageInput)
        sendButton = findViewById(R.id.sendButton)

        // Set up RecyclerView
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = adapter

        // Handle send button click
        sendButton.setOnClickListener {
            val message = chatMessageInput.text.toString().trim()
            if (message.isNotEmpty()) {
                sendMessageToBot(message)
            }
        }
    }

    private fun sendMessageToBot(userMessage: String) {
        // Add the user's message to the chat
        messageList.add("You: $userMessage")
        adapter.notifyItemInserted(messageList.size - 1)
        chatRecyclerView.scrollToPosition(messageList.size - 1) // Scroll to the latest message

        // Clear the input field after sending
        chatMessageInput.text.clear()

        // Make network request to Botpress
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Send message to Botpress and get the response
                //val botResponse = botRepository.sendMessageToBot(userMessage, "RecipeGuideBot", "noorfatimagrw965@gmail.com")
                val botResponse = botRepository.sendMessageToBot(userMessage, "recipeguidebot", "test-user")


                // Update the UI with the bot's response on the main thread
                runOnUiThread {
                    if (botResponse != null && botResponse.isNotEmpty()) {
                        // Iterate over the response messages
                        for (message in botResponse) {
                            messageList.add("Bot: ${message.text}")
                            adapter.notifyItemInserted(messageList.size - 1)
                        }
                    } else {
                        messageList.add("Bot: Sorry, I couldn't understand that.")
                        adapter.notifyItemInserted(messageList.size - 1)
                    }
                    chatRecyclerView.scrollToPosition(messageList.size - 1)
                }
            } catch (e: Exception) {
                runOnUiThread {
                    // Handle network errors
                    messageList.add("Bot: Something went wrong. Please try again later.")
                    adapter.notifyItemInserted(messageList.size - 1)
                    chatRecyclerView.scrollToPosition(messageList.size - 1)
                }
            }
        }
    }
}
