package com.example.dictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.adapter.MainAdapter
import com.example.dictionary.databinding.ActivityMainBinding
import com.example.dictionary.presenter.IPresenter
import com.example.dictionary.presenter.MainPresenterImpl
import com.example.dictionary.view.IView
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.view.main.SearchDialogFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity(): AppCompatActivity() {

    private var vb: ActivityMainBinding? = null

    private val presenter = createPresenter()

    private var adapter: MainAdapter? = null // Адаптер для отображения списка
    // вариантов перевода
    // Обработка нажатия элемента списка
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
            object : MainAdapter.OnListItemClickListener {
                override fun onItemClick(data: DataModel) {
                    Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
                }
            }
    // Создаём презентер и храним его в базовой Activity
    private fun createPresenter(): IPresenter<AppState, IView> {
        return MainPresenterImpl()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb?.root)
        search_fab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object : SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                            presenter
                    .getData(searchWord, true)
                }
            })
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }
    // Переопределяем базовый метод
    fun renderData(appState: AppState) {
        // В зависимости от состояния модели данных (загрузка, отображение,
        // ошибка) отображаем соответствующий экран
        when (appState) {
            is AppState.Success -> {
                val dataModel = appState.data
                if (dataModel == null || dataModel.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {
                        main_activity_recyclerview.layoutManager = LinearLayoutManager(applicationContext)
                        main_activity_recyclerview.adapter = MainAdapter(onListItemClickListener, dataModel)
                    } else {
                        adapter!!.setData(dataModel)
                    }
                }
            }
//            is AppState.Loading -> {
//                showViewLoading()
//                // Задел на будущее, если понадобится отображать прогресс
//                // загрузки
//                if (dataModel.progress != null) {
//                    progress_bar_horizontal.visibility = VISIBLE
//                    progress_bar_round.visibility = GONE
//                    progress_bar_horizontal.progress = dataModel.progress
//                } else {
//                    progress_bar_horizontal.visibility = GONE
//                    progress_bar_round.visibility = VISIBLE
//                }
//            }
            is AppState.Error -> {
//                showErrorScreen(dataModel.error.message)
                Toast.makeText(this@MainActivity, "Ошибка получения данных", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        error_textview.text = error ?: getString(R.string.undefined_error)
        reload_button.setOnClickListener {
            presenter.getData("hi", true)
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
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }
}
