package org.vaadin.example;

import com.google.gson.Gson;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.dialog.Dialog;

import javax.swing.text.TabSet;
import java.lang.reflect.Array;
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
        TabSheet tabs = new TabSheet();
        VerticalLayout datosGeneralesLayout = new VerticalLayout();
        datosGeneralesLayout.setSizeFull(); // Ajusta el tamaño del layout a la pantalla
        datosGeneralesLayout.setWidthFull(); // Ajusta el ancho del layout a la pantalla

        Grid<Turismo> grid = new Grid<>(Turismo.class, false);
        grid.setSizeFull();
        grid.setWidthFull();
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

        datosGeneralesLayout.add(grid, botonesMain);


        Grid<Turismo> gridCG = new Grid<>(Turismo.class, false);
        gridCG.addColumn(turismo -> turismo.getOrigen().getComunidad()).setHeader("Comunidad Origen");
        gridCG.addColumn(turismo -> turismo.getDestino().getComunidad()).setHeader("Comunidad Destino");
        gridCG.addColumn(turismo -> turismo.getPeriodo().getFecha_inicio()).setHeader("Fecha Inicio");
        gridCG.addColumn(turismo -> turismo.getPeriodo().getFecha_fin()).setHeader("Fecha Fin");
        gridCG.setWidth("1800px");
        listaTurismos.clear();
        gridCG.setItems(listaTurismos);
        gridCG.addClassName("grid-turismos");

        List<String> opcionesComunidades;
        opcionesComunidades = service.getComunidades();

        ComboBox<String> select =
                new ComboBox<>("Selecciona una Comunidad de Destino");
        select.setItems(opcionesComunidades);
        select.addValueChangeListener(e->{
            String comunidadSeleccionada = e.getValue();
            if (comunidadSeleccionada != null) {
                ArrayList<Turismo> datosComunidadSelected = service.getTurismoByComunidad(comunidadSeleccionada);
                gridCG.setItems(new ListDataProvider<>(datosComunidadSelected));
            }
        });
        VerticalLayout layoutAgruparTurismos = new VerticalLayout();
        layoutAgruparTurismos.setWidth("100%");
        layoutAgruparTurismos.setHeight("100%");
        layoutAgruparTurismos.setPadding(false);
        layoutAgruparTurismos.setSpacing(false);
        layoutAgruparTurismos.setAlignItems(FlexComponent.Alignment.CENTER);
        layoutAgruparTurismos.add(select, gridCG);



        addClassName("centered-content");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        tabs.setSizeFull();
        tabs.setWidth("1000px");
        tabs.add("Datos generales", datosGeneralesLayout);
        tabs.add("Datos agrupados", layoutAgruparTurismos);
        add(tabs);
        setSizeFull();
        setWidthFull();
    }
}
