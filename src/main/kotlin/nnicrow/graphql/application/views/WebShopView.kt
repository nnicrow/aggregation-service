package nnicrow.graphql.application.views

import com.github.mvysny.karibudsl.v10.flexGrow
import com.vaadin.flow.component.AbstractField
import com.vaadin.flow.component.ClickEvent
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.value.ValueChangeMode
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import nnicrow.graphql.application.views.editors.WebShopEditor
import nnicrow.graphql.application.models.WebShop
import nnicrow.graphql.application.repository.WebShopRepository
import org.springframework.util.StringUtils

@Route(value="webShop", layout = MainLayout::class )
@PageTitle("Web Shop")
class WebShopView(
    private val repo: WebShopRepository,
    private val editor: WebShopEditor
): VerticalLayout() {
    private val grid: Grid<WebShop?> = Grid(WebShop::class.java)
    private val filter: TextField = TextField()
    private val addNewBtn: Button = Button("Новый Интернет Магазин", VaadinIcon.PLUS.create())

    private fun listWebShop(filterText: String?) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll())
        } else {
            grid.setItems(
                filterText?.let { repo.findByName(it) }
            )
        }
    }

    init {
        val actions = HorizontalLayout(filter, addNewBtn)
        add(actions, grid, editor)
        grid.height = "300px"
        grid.setColumns("id")
        grid.addColumn("name").setHeader("Название")
        grid.addColumn("description").setHeader("Описание")
        grid.addColumn("link").setHeader("URL")
        grid.addColumn("availability").setHeader("Доступность")
        grid.getColumnByKey("id").setWidth("60px")
        flexGrow = 0.0
        filter.placeholder = "Поиск"
        filter.valueChangeMode = ValueChangeMode.EAGER
        filter.addValueChangeListener{ e:
                                       AbstractField.ComponentValueChangeEvent<TextField?, String?> ->
            listWebShop(e.value)
        }

        grid.asSingleSelect()
            .addValueChangeListener { e: AbstractField.ComponentValueChangeEvent<Grid<WebShop?>?,
                    WebShop?> ->
                editor.editWebShop(e.value)
            }

        addNewBtn.addClickListener { e: ClickEvent<Button?>? ->
            editor.editWebShop(
                WebShop("")
            )
        }

        editor.setChangeHandler(object : WebShopEditor.ChangeHandler {
            override fun onChange() {
                editor.isVisible = false
                listWebShop(filter.value)
            }
        })
        listWebShop(null)
    }
}