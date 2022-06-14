package nnicrow.graphql.application.views

import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.applayout.DrawerToggle
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.HighlightConditions
import com.vaadin.flow.router.RouterLink
import nnicrow.graphql.application.models.ExternalProduct
import nnicrow.graphql.application.models.Slider


class MainLayout: AppLayout() {
    init {
        createHeader()
        createDrawer()
    }

    private fun createHeader() {
        val logo = H1("Nnicrow App")
        logo.addClassNames("text-l", "m-m")
        val header = HorizontalLayout(
            DrawerToggle(),
            logo
        )
        header.defaultVerticalComponentAlignment = FlexComponent.Alignment.CENTER
        header.width = "100%"
        header.addClassNames("py-0", "px-m")
        addToNavbar(header)
    }

    private fun createDrawer() {
        val homeLink = RouterLink("Главная страница",  MainView::class.java)
        homeLink.highlightCondition = HighlightConditions.sameLocation()
        addToDrawer(
            VerticalLayout(
                homeLink,
                RouterLink("Категории",  CategoryView::class.java),
                RouterLink("Производители",  BrandView::class.java),
                RouterLink("Продукты",  ProductView::class.java),
                RouterLink("Интернет Магазины",  WebShopView::class.java),
                RouterLink("Внешние Категории",  ExternalCategoryView::class.java),
                RouterLink("Внешние Производители",  ExternalBrandView::class.java),
                RouterLink("Внешние Продукты",  ExternalProductView::class.java),
                RouterLink("Слайдер",  SliderView::class.java),
            )
        )
    }
}