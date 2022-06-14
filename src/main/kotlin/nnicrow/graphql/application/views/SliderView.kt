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
import nnicrow.graphql.application.views.editors.SliderEditor
import nnicrow.graphql.application.models.Slider
import nnicrow.graphql.application.repository.SliderRepository
import org.springframework.util.StringUtils

@Route(value="Slider", layout = MainLayout::class )
@PageTitle("Slider")
class SliderView(
    private val repo: SliderRepository,
    private val editor: SliderEditor
): VerticalLayout() {
    private val grid: Grid<Slider?> = Grid(Slider::class.java)
    private val filter: TextField = TextField()
    private val addNewBtn: Button = Button("Новый Слайд", VaadinIcon.PLUS.create())

    private fun listSlider(filterText: String?) {
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
        grid.addColumn("path").setHeader("URL")
        grid.getColumnByKey("id").setWidth("60px")
//        grid.getColumnByKey("SliderNameTranslit").setHeader("Name Translit")
        flexGrow = 0.0
        filter.placeholder = "Поиск"
        filter.valueChangeMode = ValueChangeMode.EAGER
        filter.addValueChangeListener{ e:
                                       AbstractField.ComponentValueChangeEvent<TextField?, String?> ->
            listSlider(e.value)
        }

        grid.asSingleSelect()
            .addValueChangeListener { e: AbstractField.ComponentValueChangeEvent<Grid<Slider?>?,
                    Slider?> ->
                editor.editSlider(e.value)
            }

        addNewBtn.addClickListener { e: ClickEvent<Button?>? ->
            editor.editSlider(
                Slider("", "", "")
            )
        }

        editor.setChangeHandler(object : SliderEditor.ChangeHandler {
            override fun onChange() {
                editor.isVisible = false
                listSlider(filter.value)
            }
        })
        listSlider(null)
    }
}