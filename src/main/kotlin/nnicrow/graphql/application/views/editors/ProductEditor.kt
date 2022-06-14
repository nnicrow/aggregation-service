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
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import nnicrow.graphql.application.models.Brand
import nnicrow.graphql.application.models.Category
import nnicrow.graphql.application.models.Product
import nnicrow.graphql.application.models.WebShop
import nnicrow.graphql.application.repository.BrandRepository
import nnicrow.graphql.application.repository.CategoryRepository
import nnicrow.graphql.application.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired

@SpringComponent
@UIScope
class ProductEditor @Autowired constructor
    (private val repository: ProductRepository,
     private val categoryRepository: CategoryRepository,
     private val brandRepository: BrandRepository,
) :
    VerticalLayout(), KeyNotifier {
    var name = TextField("Название")
    var nameTranslit = TextField("Название Транслитом")
    var modelName = TextField("Название Модели")
    var description = TextField("Описание")
    var availability = Checkbox("Доступность")
    var brand: ComboBox<Brand?> = ComboBox()
    var category: ComboBox<Category?> = ComboBox()

    private var save = Button("Сохранить", VaadinIcon.CHECK.create())
    private var cancel = Button("Отмена")
    private var delete = Button("Удалить", VaadinIcon.TRASH.create())
    var actions = HorizontalLayout(save, cancel, delete)
    var binder = Binder(
        Product::class.java
    )
    private var product: Product? = null
    private var changeHandler: ChangeHandler? = null
    fun delete() {
        product?.let { repository.delete(it) }
        changeHandler!!.onChange()
    }

    fun save() {
        repository.save<Product>(product!!)
        changeHandler!!.onChange()
    }

    fun editProduct(prod: Product?) {
        if (prod == null) {
            isVisible = false
            return
        }
        val persisted = prod.Id != null
        product = if (persisted) {
            prod.Id?.let { repository.findById(it).get() }
        } else {
            prod
        }
        cancel.isVisible = persisted
        binder.bean = product
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
        brand.label = "Производитель"
        brand.isAllowCustomValue = false
        brand.placeholder = "Выберите производителя";
        brand.setItems(brandRepository.findAll())
        brand.setItemLabelGenerator { item -> item?.name }

        category.label = "Категория"
        category.isAllowCustomValue = false
        category.placeholder = "Выберите категорию";
        category.setItems(categoryRepository.findAll())
        category.setItemLabelGenerator { item -> item?.name }

        add(name, nameTranslit, modelName, description, availability, brand, category, actions)
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
                                 ClickEvent<Button?>? -> editProduct(product) }
        isVisible = false
    }
}