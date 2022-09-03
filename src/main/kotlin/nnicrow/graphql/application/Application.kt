package nnicrow.graphql.application

import com.vaadin.flow.component.dependency.NpmPackage
import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.server.PWA
import com.vaadin.flow.theme.Theme
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer


@SpringBootApplication
@Theme(value = "graphqlapp")
@PWA(name = "GraphQl APP", shortName = "GraphQl APP", offlineResources = [])
@NpmPackage(value = "line-awesome", version = "1.3.0")
class Application : SpringBootServletInitializer(), AppShellConfigurator

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}