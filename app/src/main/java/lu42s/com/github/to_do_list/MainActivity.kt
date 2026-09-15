package lu42s.com.github.to_do_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import lu42s.com.github.to_do_list.navigation.AppNavigation
import lu42s.com.github.to_do_list.ui.theme.TodolistTheme
import lu42s.com.github.to_do_list.viewModel.TarefaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodolistTheme {
                val viewModel: TarefaViewModel = viewModel(
                    factory = TarefaViewModel.factory(applicationContext)
                )
                AppNavigation(viewModel = viewModel)
            }
        }
    }
}
