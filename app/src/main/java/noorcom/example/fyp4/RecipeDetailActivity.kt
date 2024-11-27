package noorcom.example.fyp4
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import noorcom.example.fyp4.databinding.ActivityRecipeDetailBinding
import noorcom.example.fyp4.models.Recipe

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipe = intent.getParcelableExtra<Recipe>("recipe")

        // Display recipe details
        recipe?.let {
            binding.recipeName.text = it.recipeName
            binding.recipeIngredients.text = formatIngredients(it.ingredients)
            binding.recipeInstructions.text = formatInstructions(it.instructions)
            binding.recipeTotalTime.text = it.totalTime
            binding.recipeCal.text = it.calories
            binding.recipePreTime.text = it.preTime
            binding.recipeServings.text = it.servings
            binding.recipeYield.text = it.yield
            binding.recipeCookTime.text = it.cookTime
        }
        // Inside RecipeDetailActivity's onCreate method
        binding.talkToBotIconRecipeDetail.setOnClickListener {
            // Navigate to ChatbotActivity
            val intent = Intent(this, ChatbotActivity::class.java)
            startActivity(intent)
        }

    }

    // Utility methods (unchanged)
    private fun formatIngredients(ingredients: String): String {
        val ingredientsList = ingredients.split("\n", ",")
        val formattedIngredients = StringBuilder()
        for (ingredient in ingredientsList) {
            val cleanIngredient = ingredient.replace(Regex("\\d+="), "")
                .replace(Regex("[{}]"), "").trim()
            if (cleanIngredient.isNotEmpty()) {
                formattedIngredients.append("• ").append(cleanIngredient).append("\n")
            }
        }
        return formattedIngredients.toString().trim()
    }

    private fun formatInstructions(instructions: String): String {
        val cleanedInstructions = instructions.replace(Regex("\\d+=|[{}]"), "").trim()
        val instructionsList = cleanedInstructions.split(Regex("[\\n\\.]"))
        val formattedInstructions = StringBuilder()
        for ((index, instruction) in instructionsList.withIndex()) {
            val cleanInstruction = instruction.trim().removePrefix(",").trim()
            if (cleanInstruction.isNotEmpty()) {
                formattedInstructions.append("Step ${index + 1}\n")
                    .append(cleanInstruction)
                    .append("\n\n")
            }
        }
        return formattedInstructions.toString().trim()
    }
}
