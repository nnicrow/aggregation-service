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
import nnicrow.graphql.application.views.editors.BrandEditor
import nnicrow.graphql.application.models.Brand
import nnicrow.graphql.application.repository.BrandRepository
import org.springframework.util.StringUtils

@Route(value="brand", layout = MainLayout::class )
@PageTitle("Brand")
class BrandView(
    private val repo: BrandRepository,
    private val editor: BrandEditor
): VerticalLayout() {
    private val grid: Grid<Brand?> = Grid(Brand::class.java)
    private val filter: TextField = TextField()
    private val addNewBtn: Button = Button("Новый Производитель", VaadinIcon.PLUS.create())

    private fun listBrand(filterText: String?) {
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
        grid.addColumn("brandVisible").setHeader("Видимость")
        grid.getColumnByKey("id").setWidth("60px")
//        grid.getColumnByKey("brandNameTranslit").setHeader("Name Translit")
        flexGrow = 0.0
        filter.placeholder = "Поиск"
        filter.valueChangeMode = ValueChangeMode.EAGER
        filter.addValueChangeListener{ e:
                                       AbstractField.ComponentValueChangeEvent<TextField?, String?> ->
            listBrand(e.value)
        }

        grid.asSingleSelect()
            .addValueChangeListener { e: AbstractField.ComponentValueChangeEvent<Grid<Brand?>?,
                    Brand?> ->
                editor.editBrand(e.value)
            }

        addNewBtn.addClickListener { e: ClickEvent<Button?>? ->
            editor.editBrand(
                Brand("", "", "",
            "", true)
            )
        }

        editor.setChangeHandler(object : BrandEditor.ChangeHandler {
            override fun onChange() {
                editor.isVisible = false
                listBrand(filter.value)
            }
        })
        listBrand(null)
    }
}