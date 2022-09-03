package nnicrow.graphql.application.views.editors

import com.vaadin.flow.component.ClickEvent
import com.vaadin.flow.component.Key
import com.vaadin.flow.component.KeyNotifier
import com.vaadin.flow.component.KeyPressEvent
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.checkbox.Checkbox
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import nnicrow.graphql.application.models.Brand
import nnicrow.graphql.application.repository.BrandRepository
import org.springframework.beans.factory.annotation.Autowired

@SpringComponent
@UIScope
class BrandEditor @Autowired constructor
    (private val repository: BrandRepository) :
    VerticalLayout(), KeyNotifier {
    var name = TextField("Название")
    var brandNameTranslit = TextField("Название Транслитом")
    var brandUrl = TextField("Url")
    var brandLogoUrl = TextField("Url Логотипа")
    var isBrandVisible = Checkbox("Видимость")

    private var save = Button("Сохранить", VaadinIcon.CHECK.create())
    private var cancel = Button("Отмена")
    private var delete = Button("Удалить", VaadinIcon.TRASH.create())
    var actions = HorizontalLayout(save, cancel, delete)
    var binder = Binder(
        Brand::class.java
    )
    private var brand: Brand? = null
    private var changeHandler: ChangeHandler? = null
    fun delete() {
        brand?.let { repository.delete(it) }
        changeHandler!!.onChange()
    }

    fun save() {
        repository.save<Brand>(brand!!)
        changeHandler!!.onChange()
    }

    fun editBrand(brnd: Brand?) {
        if (brnd == null) {
            isVisible = false
            return
        }
        val persisted = brnd.Id != null
        brand = if (persisted) {
            brnd.Id?.let { repository.findById(it).get() }
        } else {
            brnd
        }
        cancel.isVisible = persisted
        binder.bean = brand
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
        add(name, brandNameTranslit, brandUrl, brandLogoUrl, isBrandVisible, actions)
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
                                 ClickEvent<Button?>? -> editBrand(brand) }
        isVisible = false
    }
}