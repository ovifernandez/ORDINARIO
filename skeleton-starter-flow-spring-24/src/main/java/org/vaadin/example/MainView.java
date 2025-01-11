package org.vaadin.example;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.models.Turismo;
import org.vaadin.example.services.FrontService;

import java.util.ArrayList;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and use @Route
 * annotation to announce it in a URL as a Spring managed bean.
 * <p>
 * A new instance of this class is created for every new user and every browser
 * tab/window.
 * <p>
 * The main view contains a text field for getting the username and a button
 * that shows a greeting message in a notification.
 */
@Route
public class MainView extends VerticalLayout {

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service
     *            The message service. Automatically injected Spring managed bean.
     */
    public MainView(@Autowired FrontService service) {
        FrontService frontService;
        ArrayList<Turismo> listaTurismos = new ArrayList<>();
        Grid<Turismo> grid = new Grid<>(Turismo.class,false);
        grid.addColumn(turismo -> turismo.getOrigen().getComunidad()).setHeader("Comunidad");
        grid.addColumn(turismo -> turismo.getDestino().getComunidad()).setHeader("Destino");
        grid.addColumn(turismo -> turismo.getPeriodo().getFecha_inicio()).setHeader("Fecha Inicio");
        grid.addColumn(turismo -> turismo.getPeriodo().getFecha_fin()).setHeader("Fecha Fin");

        grid.setWidth("800px");

        grid.setItems(listaTurismos);
        grid.addClassName("grid-turismo");
        grid.addItemDoubleClickListener(e ->{
            Turismo turismo = e.getItem();

        });
    }
}
