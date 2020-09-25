package demo.alexeynovosibirsk.views.startPage;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import demo.alexeynovosibirsk.views.main.MainView;

@Route(value = "", layout = MainView.class)
@PageTitle("Just Portal")
@CssImport("./styles/views/about/about-view.css")
public class StartPageView extends Div {
    /**
     * This is an example of page which available for all
     * Active Directory users...
     */

    private static String name;

    public String setName(String name1) {
        return name = name1;
    }

    public StartPageView() {

        Div content = new Div();
        content.getElement().setProperty(
                "innerHTML",
                "<div align = center>" +
                        "<p><b>Здравствуйте " + name + "!</b></p>" + // in this string will be name of user from AD
                        "Вы находитесь на Just Portal</br>" +
                        "если на панели слева нет пунктов меню, это значит, что у Вас нет доступа.</br>" +
                        "Для получения доступа необходимо завести заявку</br>" +
                        "<a href='https://'>" +
                        "Поддержка службы каталогов Active Directory</a></br>" +
                        "Тема заявки: добавить Вашу учетную запись в группу SomeGroup"
        );
        add(content);
    }
}
