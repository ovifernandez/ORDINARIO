package org.vaadin.example;

import com.google.gson.Gson;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.dialog.Dialog;

import java.util.ArrayList;
import java.util.List;

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
    ArrayList<Turismo> listaTurismos = new ArrayList<>();
    public MainView(@Autowired FrontService service) {
        Grid<Turismo> grid = new Grid<>(Turismo.class, false);

        // Configurar las columnas del Grid

        grid.addColumn(turismo -> turismo.getOrigen().getComunidad()).setHeader("Comunidad Origen");
        grid.addColumn(turismo -> turismo.getDestino().getComunidad()).setHeader("Comunidad Destino");
        grid.addColumn(turismo -> turismo.getPeriodo().getFecha_inicio()).setHeader("Fecha Inicio");
        grid.addColumn(turismo -> turismo.getPeriodo().getFecha_fin()).setHeader("Fecha Fin");
        grid.setWidth("1800px");
        grid.setItems(listaTurismos);
        grid.addClassName("grid-turismos");
        //Si dobleclikamos sobre un elemento de la lista:
        grid.addItemDoubleClickListener(e->{
            Turismo turismo = e.getItem();
            DialogView dialogEdit = new DialogView(service);
            dialogEdit.generateEditDialog(turismo).open();
        });

        HorizontalLayout botonesMain = new HorizontalLayout();
        Button cargarElems = new Button("Cargar Elems", e->{
            listaTurismos = service.getTurismos();
            grid.setItems(listaTurismos);
            grid.getDataProvider().refreshAll();
        });

        Button newElem = new Button("Nuevo elemento", e->{
            DialogView dialogNew = new DialogView(service);
            dialogNew.generateCreateDialog().open();
        });
        botonesMain.add(cargarElems, newElem);
        // Añadir el Grid al layout principal
        add(grid, botonesMain);

        List<Turismo> opcionesComunidades = new ArrayList<>();
        

    }
}
