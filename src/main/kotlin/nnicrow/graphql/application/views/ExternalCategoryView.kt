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
import nnicrow.graphql.application.views.editors.ExternalCategoryEditor
import nnicrow.graphql.application.models.ExternalCategory
import nnicrow.graphql.application.repository.ExternalCategoryRepository
import org.springframework.util.StringUtils

@Route(value="externalCategory", layout = MainLayout::class )
@PageTitle("External Category")
class ExternalCategoryView(
    private val repo: ExternalCategoryRepository,
    private val editor: ExternalCategoryEditor
): VerticalLayout() {
    private val grid: Grid<ExternalCategory?> = Grid(ExternalCategory::class.java)
    private val filter: TextField = TextField()
    private val addNewBtn: Button = Button("Новая Внешняя Категория", VaadinIcon.PLUS.create())

    private fun listExternalCategory(filterText: String?) {
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
        grid.addColumn("availability").setHeader("Доступность")
        grid.addColumn("externalId").setHeader("Внешний ID")
        grid.addColumn("webShop.name").setHeader("Интернет магазин")
        grid.addColumn("category.name").setHeader("Категория")

        flexGrow = 0.0
        filter.placeholder = "Поиск"
        filter.valueChangeMode = ValueChangeMode.EAGER
        filter.addValueChangeListener{ e:
                                       AbstractField.ComponentValueChangeEvent<TextField?, String?> ->
            listExternalCategory(e.value)
        }

        grid.asSingleSelect()
            .addValueChangeListener { e: AbstractField.ComponentValueChangeEvent<Grid<ExternalCategory?>?,
                    ExternalCategory?> ->
                editor.editExternalCategory(e.value)
            }

        addNewBtn.addClickListener { e: ClickEvent<Button?>? ->
            editor.editExternalCategory(
                ExternalCategory(
                    ""
                )
            )
        }

        editor.setChangeHandler(object : ExternalCategoryEditor.ChangeHandler {
            override fun onChange() {
                editor.isVisible = false
                listExternalCategory(filter.value)
            }
        })
        listExternalCategory(null)
    }
}