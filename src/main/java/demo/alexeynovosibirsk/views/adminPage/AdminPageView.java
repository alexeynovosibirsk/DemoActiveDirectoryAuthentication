package demo.alexeynovosibirsk.views.adminPage;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import demo.alexeynovosibirsk.security.SecurityConfigFileHandler;
import demo.alexeynovosibirsk.views.main.MainView;
import java.util.Set;

@Route(value = "adminpage", layout = MainView.class)
@PageTitle("Admins")
@CssImport("./styles/views/about/about-view.css")
@Secured("ROLE_Admin")
public class AdminPageView extends HorizontalLayout {

    /**
     * This is example of admins page
     */

    public AdminPageView() {

        Logger logger = LoggerFactory.getLogger(AdminPageView.class);

       // ReadFileConfiguration readFileConfiguration = new ReadFileConfiguration();

        setId("about-view");
        Label l = new Label("  ");
        l.setMinWidth("200px");
        add(l);
        add(new Label("Редактирование списка Администраторов портала:"));
        add(new Hr());

        Set<String> setAdmins = SecurityConfigFileHandler.readConfig();

        Grid<String> grid = new Grid<>();
        grid.setItems(setAdmins);
        grid.addColumn(String::valueOf).setHeader("Active Directory Login:");
        grid.addColumn(
                new NativeButtonRenderer<String>("Удалить",
                clickItem -> {
                    SecurityConfigFileHandler.removeAdmin("" + clickItem);
                    setAdmins.remove(clickItem);
                    grid.getDataProvider().refreshAll();
                }));

          TextField textField = new TextField("Добавить учетную запись в администраторы:");
          textField.setValueChangeMode(ValueChangeMode.ON_CHANGE);
          textField.setClearButtonVisible(true);
          textField.addValueChangeListener(n -> {
              if (!textField.getValue().isEmpty()) {

                  SecurityConfigFileHandler.addAdmin(textField.getValue());
                  setAdmins.add(textField.getValue());
                  grid.getDataProvider().refreshAll();

              }});

       add(textField);
       add(grid);
    }
}
