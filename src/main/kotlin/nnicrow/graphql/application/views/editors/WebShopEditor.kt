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
import nnicrow.graphql.application.models.WebShop
import nnicrow.graphql.application.repository.WebShopRepository
import org.springframework.beans.factory.annotation.Autowired

@SpringComponent
@UIScope
class WebShopEditor @Autowired constructor
    (private val repository: WebShopRepository) :
    VerticalLayout(), KeyNotifier {
    var name = TextField("Название")
    var description = TextField("Описание")
    var availability = Checkbox("Доступность")
    var link = TextField("Url")

    private var save = Button("Сохранить", VaadinIcon.CHECK.create())
    private var cancel = Button("Отмена")
    private var delete = Button("Удалить", VaadinIcon.TRASH.create())
    var actions = HorizontalLayout(save, cancel, delete)
    var binder = Binder(
        WebShop::class.java
    )
    private var webShop: WebShop? = null
    private var changeHandler: ChangeHandler? = null
    fun delete() {
        webShop?.let { repository.delete(it) }
        changeHandler!!.onChange()
    }

    fun save() {
        repository.save<WebShop>(webShop!!)
        changeHandler!!.onChange()
    }

    fun editWebShop(wbShop: WebShop?) {
        if (wbShop == null) {
            isVisible = false
            return
        }
        val persisted = wbShop.Id != null
        webShop = if (persisted) {
            wbShop.Id?.let { repository.findById(it).get() }
        } else {
            wbShop
        }
        cancel.isVisible = persisted
        binder.bean = webShop
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
        add(name, description, availability, link, actions)
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
                                 ClickEvent<Button?>? -> editWebShop(webShop) }
        isVisible = false
    }
}