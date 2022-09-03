package nnicrow.graphql.application.views

import com.vaadin.flow.component.ClickEvent
import com.vaadin.flow.component.Key
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.checkbox.Checkbox
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route


@Route(value="", layout = MainLayout::class )
@PageTitle("Admin Panel")
class MainView : VerticalLayout() {
    init {
        add(
            H1("Панель Администратора")
        )
    }
}