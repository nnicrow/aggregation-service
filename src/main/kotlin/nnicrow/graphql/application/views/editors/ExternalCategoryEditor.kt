package nnicrow.graphql.application.views.editors

import com.vaadin.flow.component.ClickEvent
import com.vaadin.flow.component.Key
import com.vaadin.flow.component.KeyNotifier
import com.vaadin.flow.component.KeyPressEvent
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.checkbox.Checkbox
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.NumberField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import nnicrow.graphql.application.models.Category
import nnicrow.graphql.application.models.ExternalCategory
import nnicrow.graphql.application.models.WebShop
import nnicrow.graphql.application.repository.CategoryRepository
import nnicrow.graphql.application.repository.ExternalCategoryRepository
import nnicrow.graphql.application.repository.WebShopRepository
import org.springframework.beans.factory.annotation.Autowired

@SpringComponent
@UIScope
class ExternalCategoryEditor @Autowired constructor
    (private val repository: ExternalCategoryRepository,
     private val webShopRepository: WebShopRepository,
     private val categoryRepository: CategoryRepository
) :
    VerticalLayout(), KeyNotifier {

    var name = TextField("Название")
    var description = TextField("Описание")
    var externalId = IntegerField("Внешний ID")
    var availability = Checkbox("Доступность")
    var webShop: ComboBox<WebShop?> = ComboBox()
    var category: ComboBox<Category?> = ComboBox()

    private var save = Button("Сохранить", VaadinIcon.CHECK.create())
    private var cancel = Button("Отмена")
    private var delete = Button("Удалить", VaadinIcon.TRASH.create())
    var actions = HorizontalLayout(save, cancel, delete)
    var binder = Binder(
        ExternalCategory::class.java
    )
    private var externalCategory: ExternalCategory? = null
    private var changeHandler: ChangeHandler? = null
    fun delete() {
        externalCategory?.let { repository.delete(it) }
        changeHandler!!.onChange()
    }

    fun save() {
        externalCategory?.webShop = webShop.value
        repository.save<ExternalCategory>(externalCategory!!)
        changeHandler!!.onChange()
    }

    fun editExternalCategory(extCategory: ExternalCategory?) {
        if (extCategory == null) {
            isVisible = false
            return
        }
        val persisted = extCategory.Id != null
        externalCategory = if (persisted) {
            extCategory.Id?.let { repository.findById(it).get() }
        } else {
            extCategory
        }
        cancel.isVisible = persisted
        binder.bean = externalCategory
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
        webShop.label = "Интернет магазин"
        webShop.isAllowCustomValue = false
        webShop.placeholder = "Выберите магазин";
        webShop.setItems(webShopRepository.findAll())
        webShop.setItemLabelGenerator { item -> item?.name }

        category.label = "Категория"
        category.isAllowCustomValue = false
        category.placeholder = "Выберите категорию";
        category.setItems(categoryRepository.findAll())
        category.setItemLabelGenerator { item -> item?.name }

        add(name, description, externalId, availability, webShop, category, actions)
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
                                 ClickEvent<Button?>? -> editExternalCategory(externalCategory) }
        isVisible = false
    }
}