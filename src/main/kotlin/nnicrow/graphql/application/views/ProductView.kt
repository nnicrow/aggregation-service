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
import nnicrow.graphql.application.views.editors.ProductEditor
import nnicrow.graphql.application.models.Product
import nnicrow.graphql.application.repository.BrandRepository
import nnicrow.graphql.application.repository.CategoryRepository
import nnicrow.graphql.application.repository.ProductRepository
import org.springframework.util.StringUtils

@Route(value="product", layout = MainLayout::class )
@PageTitle("Product")
class ProductView(
    private val repo: ProductRepository,
    private val brandRepo: BrandRepository,
    private val categoryRepo: CategoryRepository,
    private val editor: ProductEditor
): VerticalLayout() {
    private val grid: Grid<Product?> = Grid(Product::class.java)
    private val filter: TextField = TextField()
    private val addNewBtn: Button = Button("Новый Продукт", VaadinIcon.PLUS.create())

    private fun listProduct(filterText: String?) {
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
        grid.addColumn("brand.name").setHeader("Производитель")
        grid.addColumn("category.name").setHeader("Категория")
        grid.getColumnByKey("id").setWidth("60px")
        flexGrow = 0.0
        filter.placeholder = "Поиск"
        filter.valueChangeMode = ValueChangeMode.EAGER
        filter.addValueChangeListener{ e:
                                       AbstractField.ComponentValueChangeEvent<TextField?, String?> ->
            listProduct(e.value)
        }

        grid.asSingleSelect()
            .addValueChangeListener { e: AbstractField.ComponentValueChangeEvent<Grid<Product?>?,
                    Product?> ->
                editor.editProduct(e.value)
            }

        addNewBtn.addClickListener { e: ClickEvent<Button?>? ->
            editor.editProduct(
                Product("")
            )
        }

        editor.setChangeHandler(object : ProductEditor.ChangeHandler {
            override fun onChange() {
                editor.isVisible = false
                listProduct(filter.value)
            }
        })
        listProduct(null)
    }
}