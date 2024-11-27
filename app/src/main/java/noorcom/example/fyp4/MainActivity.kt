
package noorcom.example.fyp4

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import noorcom.example.fyp4.adapters.RecipeAdapter
import noorcom.example.fyp4.databinding.ActivityMainBinding
import noorcom.example.fyp4.models.Recipe
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference
    private lateinit var recipeList: MutableList<Recipe>
    private lateinit var adapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recipeList = mutableListOf()
        adapter = RecipeAdapter(recipeList) { recipe ->
            val intent = Intent(this, RecipeDetailActivity::class.java)
            intent.putExtra("recipe", recipe)
            startActivity(intent)

//            binding.talkToBotIcon.setOnClickListener {
//                val intent = Intent(this, ChatbotActivity::class.java)
//                startActivity(intent)
//            }

            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.adapter = adapter

            // Firebase database reference
            database = FirebaseDatabase.getInstance().getReference("recipes")


            // Fetch recipes from Firebase
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    recipeList.clear()
                    for (recipeSnapshot in snapshot.children) {
                        val recipe = recipeSnapshot.getValue(Recipe::class.java)
                        if (recipe != null) {
                            recipeList.add(recipe)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        applicationContext,
                        "Error: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

            // Implement Search functionality
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = false

                override fun onQueryTextChange(newText: String?): Boolean {
                    val filteredList = recipeList.filter {
                        it.recipeName.contains(newText ?: "", ignoreCase = true)
                    }
                    adapter.updateList(filteredList)
                    return true
                }
            })
        }
        binding.talkToBotIcon.setOnClickListener {
            // Navigate to ChatbotActivity
            val intent = Intent(this, ChatbotActivity::class.java)
            startActivity(intent)
        }
    }
}