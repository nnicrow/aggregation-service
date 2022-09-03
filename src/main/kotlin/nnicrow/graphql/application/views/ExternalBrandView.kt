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
import nnicrow.graphql.application.views.editors.ExternalBrandEditor
import nnicrow.graphql.application.models.ExternalBrand
import nnicrow.graphql.application.repository.ExternalBrandRepository
import org.springframework.util.StringUtils

@Route(value="ExternalBrand", layout = MainLayout::class )
@PageTitle("External Brand")
class ExternalBrandView(
    private val repo: ExternalBrandRepository,
    private val editor: ExternalBrandEditor
): VerticalLayout() {
    private val grid: Grid<ExternalBrand?> = Grid(ExternalBrand::class.java)
    private val filter: TextField = TextField()
    private val addNewBtn: Button = Button("Новый Внешний Производитель", VaadinIcon.PLUS.create())

    private fun listExternalBrand(filterText: String?) {
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
        grid.addColumn("brandNameTranslit").setHeader("Название Транслитом")
        grid.addColumn("brandUrl").setHeader("URL")
        grid.addColumn("brandLogoUrl").setHeader("URL Логотипа")
        grid.addColumn("externalId").setHeader("Внешний ID")
        grid.addColumn("webShop.name").setHeader("Интернет Магазин")
        grid.addColumn("brand.name").setHeader("Производитель")
        grid.getColumnByKey("id").setWidth("60px")
        flexGrow = 0.0
        filter.placeholder = "Поиск"
        filter.valueChangeMode = ValueChangeMode.EAGER
        filter.addValueChangeListener{ e:
                                       AbstractField.ComponentValueChangeEvent<TextField?, String?> ->
            listExternalBrand(e.value)
        }

        grid.asSingleSelect()
            .addValueChangeListener { e: AbstractField.ComponentValueChangeEvent<Grid<ExternalBrand?>?,
                    ExternalBrand?> ->
                editor.editExternalBrand(e.value)
            }

        addNewBtn.addClickListener { e: ClickEvent<Button?>? ->
            editor.editExternalBrand(
                ExternalBrand("")
            )
        }

        editor.setChangeHandler(object : ExternalBrandEditor.ChangeHandler {
            override fun onChange() {
                editor.isVisible = false
                listExternalBrand(filter.value)
            }
        })
        listExternalBrand(null)
    }
}