package nnicrow.graphql.application.views.editors

import com.vaadin.flow.component.ClickEvent
import com.vaadin.flow.component.Key
import com.vaadin.flow.component.KeyNotifier
import com.vaadin.flow.component.KeyPressEvent
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.data.converter.StringToLongConverter
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import nnicrow.graphql.application.models.Brand
import nnicrow.graphql.application.models.ExternalBrand
import nnicrow.graphql.application.models.WebShop
import nnicrow.graphql.application.repository.BrandRepository
import nnicrow.graphql.application.repository.ExternalBrandRepository
import nnicrow.graphql.application.repository.WebShopRepository
import org.springframework.beans.factory.annotation.Autowired

@SpringComponent
@UIScope
class ExternalBrandEditor @Autowired constructor
    (private val repository: ExternalBrandRepository,
     private val webShopRepository: WebShopRepository,
     private val brandRepository: BrandRepository) :
    VerticalLayout(), KeyNotifier {

    var name = TextField("Название")
    var brandNameTranslit = TextField("Название Транслитом")
    var brandUrl = TextField("Url")
    var brandLogoUrl = TextField("Url Логотипа")
    var externalId = TextField("Внешний ID")
    var webShop: ComboBox<WebShop?> = ComboBox()
    var brand: ComboBox<Brand?> = ComboBox()

    private var save = Button("Сохранить", VaadinIcon.CHECK.create())
    private var cancel = Button("Отмена")
    private var delete = Button("Удалить", VaadinIcon.TRASH.create())
    var actions = HorizontalLayout(save, cancel, delete)
    var binder = Binder(
        ExternalBrand::class.java
    )
    private var externalBrand: ExternalBrand? = null
    private var changeHandler: ChangeHandler? = null
    fun delete() {
        externalBrand?.let { repository.delete(it) }
        changeHandler!!.onChange()
    }

    fun save() {
        repository.save<ExternalBrand>(externalBrand!!)
        changeHandler!!.onChange()
    }

    fun editExternalBrand(brnd: ExternalBrand?) {
        if (brnd == null) {
            isVisible = false
            return
        }
        val persisted = brnd.Id != null
        externalBrand = if (persisted) {
            brnd.Id?.let { repository.findById(it).get() }
        } else {
            brnd
        }
        cancel.isVisible = persisted
        binder.bean = externalBrand
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

        webShop.label = "Интернет магазин"
        webShop.isAllowCustomValue = false
        webShop.placeholder = "Выберите магазин";
        webShop.setItems(webShopRepository.findAll())
        webShop.setItemLabelGenerator { item -> item?.name }

        brand.label = "Категория"
        brand.isAllowCustomValue = false
        brand.placeholder = "Выберите категорию";
        brand.setItems(brandRepository.findAll())
        brand.setItemLabelGenerator { item -> item?.name }

        add(name, brandNameTranslit, brandUrl, brandLogoUrl, externalId, webShop, brand, actions)
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
                                 ClickEvent<Button?>? -> editExternalBrand(externalBrand) }
        isVisible = false
    }
}