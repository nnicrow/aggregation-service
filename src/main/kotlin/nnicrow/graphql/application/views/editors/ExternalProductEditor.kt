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
import com.vaadin.flow.data.converter.StringToLongConverter
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import nnicrow.graphql.application.models.*
import nnicrow.graphql.application.repository.*
import org.springframework.beans.factory.annotation.Autowired

@SpringComponent
@UIScope
class ExternalProductEditor @Autowired constructor
    (private val repository: ExternalProductRepository,
     private val externalBrandRepository: ExternalBrandRepository,
     private val externalCategoryRepository: ExternalCategoryRepository,
     private val productRepository: ProductRepository) :
    VerticalLayout(), KeyNotifier {

    var name = TextField("Название")
    var nameTranslit = TextField("Название Транслитом")
    var modelName = TextField("Название Модели")
    var description = TextField("Описание")
    var availability = Checkbox("Доступность")
    var url = TextField("URL")
    var price = IntegerField("Цена")
    var externalId = TextField("Внешний ID")
    var brand: ComboBox<ExternalBrand?> = ComboBox()
    var category: ComboBox<ExternalCategory?> = ComboBox()
    var product: ComboBox<Product?> = ComboBox()

    private var save = Button("Сохранить", VaadinIcon.CHECK.create())
    private var cancel = Button("Отмена")
    private var delete = Button("Удалить", VaadinIcon.TRASH.create())
    var actions = HorizontalLayout(save, cancel, delete)
    var binder = Binder(
        ExternalProduct::class.java
    )
    private var externalProduct: ExternalProduct? = null
    private var changeHandler: ChangeHandler? = null
    fun delete() {
        externalProduct?.let { repository.delete(it) }
        changeHandler!!.onChange()
    }

    fun save() {
        repository.save<ExternalProduct>(externalProduct!!)
        changeHandler!!.onChange()
    }

    fun editExternalProduct(brnd: ExternalProduct?) {
        if (brnd == null) {
            isVisible = false
            return
        }
        val persisted = brnd.Id != null
        externalProduct = if (persisted) {
            brnd.Id?.let { repository.findById(it).get() }
        } else {
            brnd
        }
        cancel.isVisible = persisted
        binder.bean = externalProduct
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
        binder.forField(externalId).withNullRepresentation("")
            .withConverter(StringToLongConverter("externalId error")).bind("externalId");

        brand.label = "Внешний Производитель"
        brand.isAllowCustomValue = false
        brand.placeholder = "Выберите производителя";
        brand.setItems(externalBrandRepository.findAll())
        brand.setItemLabelGenerator { item -> item?.name }

        category.label = "Внешняя Категория"
        category.isAllowCustomValue = false
        category.placeholder = "Выберите категорию";
        category.setItems(externalCategoryRepository.findAll())
        category.setItemLabelGenerator { item -> item?.name }

        product.label = "Продукт"
        product.isAllowCustomValue = false
        product.placeholder = "Выберите продукт";
        product.setItems(productRepository.findAll())
        product.setItemLabelGenerator { item -> item?.name }

        add(name, nameTranslit, modelName, description, availability, url, price, externalId, brand, category,
            product, actions)
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
                                 ClickEvent<Button?>? -> editExternalProduct(externalProduct) }
        isVisible = false
    }
}