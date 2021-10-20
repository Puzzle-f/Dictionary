package com.example.dictionary

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.adapter.MainAdapter
import com.example.dictionary.databinding.ActivityMainBinding
import com.example.dictionary.model.data.DataModel
import com.example.dictionary.view.IView
import com.example.dictionary.view.SearchDialogFragment
import com.example.dictionary.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity() : AppCompatActivity(), IView {

    private var vb: ActivityMainBinding? = null

    private var adapter: MainAdapter? = null // Адаптер для отображения списка

    private val viewModel: MainViewModel by viewModel()

    // вариантов перевода
    // Обработка нажатия элемента списка
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
            }
        }

//    @Inject
//    internal lateinit var viewModelFactory: ViewModelProvider.Factory

//    lateinit var viewModel: MainViewModel


//    by lazy {
//        Создание модели, не использующей жизненный цикл активити. С помощью рефлексии.
//        при данном варианте создания обязательно иметь дефолтный конструктор MainViewModel (или с инициилизированными данными в конструкторе)
//        ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)

//        вариант создания MainViewModel с использованием жизненного цикла
//        ViewModelProvider обращается к viewModelFactory, который предоставляет объект MainViewModel
//        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
//    }

    private val observer = Observer<AppState> { renderData(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
//        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb?.root)
//        viewModel = viewModelFactory.create(MainViewModel::class.java)


        search_fab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object :
                SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    viewModel.getData(searchWord, true)
                }
            })
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }

    // Переопределяем базовый метод
    override fun renderData(appState: AppState) {
        // В зависимости от состояния модели данных (загрузка, отображение,
        // ошибка) отображаем соответствующий экран
        when (appState) {
            is AppState.Success -> {
                var dataModel = appState.data
                if (dataModel == null || dataModel.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {
                        main_activity_recyclerview.layoutManager =
                            LinearLayoutManager(applicationContext)
                        main_activity_recyclerview.adapter =
                            MainAdapter(onListItemClickListener, dataModel)
                    } else {
                        adapter!!.setData(dataModel)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                // Задел на будущее, если понадобится отображать прогресс
                // загрузки
                if (appState.progress != null) {
                    progress_bar_horizontal.visibility = VISIBLE
                    progress_bar_round.visibility = GONE
                    progress_bar_horizontal.progress = appState.progress
                } else {
                    progress_bar_horizontal.visibility = GONE
                    progress_bar_round.visibility = VISIBLE
                }
            }
            is AppState.Error -> {
                showErrorScreen(appState.error.message)
                Toast.makeText(this@MainActivity, "Ошибка получения данных", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

//    private fun iniViewModel() {
//        check(main_activity_recyclerview.adapter == null) { "The ViewModel should be initialised first" }
//        // Теперь ViewModel инициализируется через функцию by viewModel()
//        // Это функция, предоставляемая Koin из коробки
//        var model: MainViewModel by viewModel()
//        model = viewModel
//        model.subscribe().observe(this@MainActivity, Observer<AppState> { renderData(it) })
//    }


    private fun showErrorScreen(error: String?) {
        showViewError()
        error_textview.text = error ?: getString(R.string.undefined_error)
        reload_button.setOnClickListener {
            viewModel.getData("Hi", true)
        }
    }

    private fun showViewSuccess() {
        success_linear_layout.visibility = VISIBLE
        loading_frame_layout.visibility = GONE
        error_linear_layout.visibility = GONE
    }

    private fun showViewLoading() {
        success_linear_layout.visibility = GONE
        loading_frame_layout.visibility = VISIBLE
        error_linear_layout.visibility = GONE
    }

    private fun showViewError() {
        success_linear_layout.visibility = GONE
        loading_frame_layout.visibility = GONE
        error_linear_layout.visibility = VISIBLE
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }
}
