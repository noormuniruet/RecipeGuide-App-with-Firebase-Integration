package noorcom.example.fyp4
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
        Log.d("MainActivity", "onCreate: Activity started")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recipeList = mutableListOf()
        adapter = RecipeAdapter(recipeList) { recipe ->
            Log.d("MainActivity", "onCreate: Recipe clicked - ${recipe.recipeName}")
            val intent = Intent(this, RecipeDetailActivity::class.java)
            intent.putExtra("recipe", recipe)
            startActivity(intent)
        }

        // Setup RecyclerView
        Log.d("MainActivity", "onCreate: Setting up RecyclerView")
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Firebase database reference
        Log.d("MainActivity", "onCreate: Initializing Firebase database reference")
        database = FirebaseDatabase.getInstance().getReference("recipes")

        // Fetch recipes from Firebase
        Log.d("MainActivity", "onCreate: Adding ValueEventListener to database")
        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("MainActivity", "onDataChange: Data snapshot received")
                recipeList.clear()
                Log.d("MainActivity", "onDataChange: Cleared recipeList")
                for (recipeSnapshot in snapshot.children) {
                    Log.d("MainActivity", "onDataChange: Processing snapshot key ${recipeSnapshot.key}")
                    val recipe = recipeSnapshot.getValue(Recipe::class.java)
                    if (recipe != null) {
                        recipeList.add(recipe)
                        Log.d("MainActivity", "onDataChange: Recipe added - ${recipe.recipeName}")
                    } else {
                        Log.d("MainActivity", "onDataChange: Recipe is null for key ${recipeSnapshot.key}")
                    }
                }
                adapter.notifyDataSetChanged()
                Log.d("MainActivity", "onDataChange: Adapter notified")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainActivity", "onCancelled: Error fetching data - ${error.message}")
                Toast.makeText(
                    applicationContext,
                    "Error: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        // Search functionality
        Log.d("MainActivity", "onCreate: Setting up SearchView listener")
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("MainActivity", "onQueryTextChange: Query changed to $newText")
                val filteredList = recipeList.filter {
                    it.recipeName.contains(newText ?: "", ignoreCase = true)
                }
                adapter.updateList(filteredList)
                Log.d("MainActivity", "onQueryTextChange: Filtered list updated")
                return true
            }
        })




    }
}
