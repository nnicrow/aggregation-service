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
import nnicrow.graphql.application.views.editors.ExternalProductEditor
import nnicrow.graphql.application.models.ExternalProduct
import nnicrow.graphql.application.repository.ExternalProductRepository
import org.springframework.util.StringUtils

@Route(value="ExternalProduct", layout = MainLayout::class )
@PageTitle("External Product")
class ExternalProductView(
    private val repo: ExternalProductRepository,
    private val editor: ExternalProductEditor
): VerticalLayout() {
    private val grid: Grid<ExternalProduct?> = Grid(ExternalProduct::class.java)
    private val filter: TextField = TextField()
    private val addNewBtn: Button = Button("Новый Внешний Продукт", VaadinIcon.PLUS.create())

    private fun listExternalProduct(filterText: String?) {
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
        grid.addColumn("nameTranslit").setHeader("Название Транслитом")
        grid.addColumn("modelName").setHeader("Название Модели")
        grid.addColumn("description").setHeader("Описание")
        grid.addColumn("availability").setHeader("Доступность")
        grid.addColumn("url").setHeader("URL")
        grid.addColumn("price").setHeader("Цена")
        grid.addColumn("externalId").setHeader("Внешний ID")
        grid.addColumn("brand.name").setHeader("Внешний Производитель")
        grid.addColumn("category.name").setHeader("Внешняя Категория")
        grid.addColumn("product.name").setHeader("Продукт")
        grid.getColumnByKey("id").setWidth("60px")
        flexGrow = 0.0
        filter.placeholder = "Поиск"
        filter.valueChangeMode = ValueChangeMode.EAGER
        filter.addValueChangeListener{ e:
                                       AbstractField.ComponentValueChangeEvent<TextField?, String?> ->
            listExternalProduct(e.value)
        }

        grid.asSingleSelect()
            .addValueChangeListener { e: AbstractField.ComponentValueChangeEvent<Grid<ExternalProduct?>?,
                    ExternalProduct?> ->
                editor.editExternalProduct(e.value)
            }

        addNewBtn.addClickListener { e: ClickEvent<Button?>? ->
            editor.editExternalProduct(
                ExternalProduct("")
            )
        }

        editor.setChangeHandler(object : ExternalProductEditor.ChangeHandler {
            override fun onChange() {
                editor.isVisible = false
                listExternalProduct(filter.value)
            }
        })
        listExternalProduct(null)
    }
}