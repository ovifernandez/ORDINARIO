package org.vaadin.example;

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
            Dialog dialog = new Dialog();

            //Creamos los elementos que se añadiran al modal
            TextField comunidadOrigen = new TextField("Comunidad Origen: ");
            TextField provinciaOrigen = new TextField("Provincia Origen: ");
            TextField comunidadDestino = new TextField("Comunidad Destino: ");
            TextField provinciaDestino = new TextField("Provincia Destino: ");
            TextField fechaInicio = new TextField("Fecha de Inicio: ");
            TextField fechaFin = new TextField("Fecha de Fin: ");
            IntegerField total = new IntegerField("Total: ");

            dialog.setWidth("450px");
            dialog.setCloseOnEsc(false);
            dialog.setCloseOnOutsideClick(false);
            String id = turismo.get_id();
            dialog.setHeaderTitle("Detalles del Viaje");

            comunidadOrigen.setValue(turismo.getOrigen().getComunidad());
            provinciaOrigen.setValue(turismo.getOrigen().getProvincia());
            comunidadDestino.setValue(turismo.getOrigen().getComunidad());
            provinciaDestino.setValue(turismo.getDestino().getProvincia());
            fechaInicio.setValue(turismo.getPeriodo().getFecha_inicio());
            fechaFin.setValue(turismo.getPeriodo().getFecha_fin());
            total.setValue(turismo.getTotal());
            HorizontalLayout origenLayout = new HorizontalLayout(comunidadOrigen, provinciaOrigen);
            HorizontalLayout destinoLayout = new HorizontalLayout(comunidadDestino, provinciaDestino);
            HorizontalLayout fechasLayout = new HorizontalLayout(fechaInicio, fechaFin);
            VerticalLayout dialogLayout = new VerticalLayout(origenLayout, destinoLayout, fechasLayout, total);
            dialogLayout.setPadding(false);
            dialogLayout.setSpacing(false);
            dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
            dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

            dialog.add(dialogLayout);
            dialog.open();
        });
        Button cargarElems = new Button("Cargar Elems", e->{
            listaTurismos = service.getTurismos();
            grid.setItems(listaTurismos);
            grid.getDataProvider().refreshAll();
        });

        // Añadir el Grid al layout principal
        add(grid, cargarElems);



    }
}
