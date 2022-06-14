package nnicrow.graphql.application.views.editors

import com.vaadin.flow.component.ClickEvent
import com.vaadin.flow.component.Key
import com.vaadin.flow.component.KeyNotifier
import com.vaadin.flow.component.KeyPressEvent
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import nnicrow.graphql.application.models.Slider
import nnicrow.graphql.application.repository.SliderRepository
import org.springframework.beans.factory.annotation.Autowired

@SpringComponent
@UIScope
class SliderEditor @Autowired constructor
    (private val repository: SliderRepository) :
    VerticalLayout(), KeyNotifier {
    var name = TextField("Название")
    var description = TextField("Описание")
    var path = TextField("Url")

    private var save = Button("Сохранить", VaadinIcon.CHECK.create())
    private var cancel = Button("Отмена")
    private var delete = Button("Удалить", VaadinIcon.TRASH.create())
    var actions = HorizontalLayout(save, cancel, delete)
    var binder = Binder(
        Slider::class.java
    )
    private var slider: Slider? = null
    private var changeHandler: ChangeHandler? = null
    fun delete() {
        slider?.let { repository.delete(it) }
        changeHandler!!.onChange()
    }

    fun save() {
        repository.save<Slider>(slider!!)
        changeHandler!!.onChange()
    }

    fun editSlider(brnd: Slider?) {
        if (brnd == null) {
            isVisible = false
            return
        }
        val persisted = brnd.Id != null
        slider = if (persisted) {
            brnd.Id?.let { repository.findById(it).get() }
        } else {
            brnd
        }
        cancel.isVisible = persisted
        binder.bean = slider
        isVisible = true
        name.focus()
    }

    fun setChangeHandler(h: ChangeHandler?) {
        changeHandler = h
    }

    interface ChangeHandler {
        fun onChange()
    }

    init {
        add(name, description, path, actions)
        binder.bindInstanceFields(this)
        isSpacing = true
        save.element.themeList.add("primary")
        delete.element.themeList.add("error")
        addKeyPressListener(
            Key.ENTER,
            { e: KeyPressEvent? -> save() })
        save.addClickListener{ e:
                               ClickEvent<Button?>? -> save() }
        delete.addClickListener{ e:
                                 ClickEvent<Button?>? -> delete() }
        cancel.addClickListener{ e:
                                 ClickEvent<Button?>? -> editSlider(slider) }
        isVisible = false
    }
}