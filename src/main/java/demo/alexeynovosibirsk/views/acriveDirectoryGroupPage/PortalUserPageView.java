package demo.alexeynovosibirsk.views.acriveDirectoryGroupPage;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;
import demo.alexeynovosibirsk.security.CustomLdapAuthoritiesPopulator;
import demo.alexeynovosibirsk.views.main.MainView;

@Route(value = "Secondpage", layout = MainView.class)
@PageTitle("Portal users")
@CssImport("./styles/views/about/about-view.css")
@Secured("ROLE_Portaluser")
public class PortalUserPageView extends Div {
    /**
     * Example of page for portal users
     */

    public PortalUserPageView() {
        Div content = new Div();
        CustomLdapAuthoritiesPopulator c = new CustomLdapAuthoritiesPopulator();
        content.getElement().setProperty(
                "innerHTML",
                "<div align = center>" +
                        "<p><b>Здравствуйте, пользователь портала!</b></p>" +
                        "Вы видите эту страницу потому, что Ваша учетная запись включена в группу</br>" +
                        "SomeGroup");
        add(content);
    }
}
