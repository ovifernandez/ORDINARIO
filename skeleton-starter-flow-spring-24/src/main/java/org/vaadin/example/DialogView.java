package org.vaadin.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.IOException;
import java.net.URISyntaxException;

public class DialogView {
    TextField comunidadOrigen = new TextField("Comunidad Origen: ");
    TextField provinciaOrigen = new TextField("Provincia Origen: ");
    TextField comunidadDestino = new TextField("Comunidad Destino: ");
    TextField provinciaDestino = new TextField("Provincia Destino: ");
    TextField fechaInicio = new TextField("Fecha de Inicio: ");
    TextField fechaFin = new TextField("Fecha de Fin: ");
    IntegerField total = new IntegerField("Total: ");
    FrontService service;

    public DialogView(@Autowired FrontService service) {
        this.service = service;
    }

    public Dialog generateEditDialog(Turismo turismo) {
        Dialog dialog = new Dialog();
        dialog.setWidth("450px");
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);
        String id = turismo.get_id();
        dialog.setHeaderTitle("Detalles del Viaje");
        dialog.add(createEditDialogLayout(id));

        Button cancelButton = new Button("Cancel", e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        Button saveButton = new Button("Save", e -> {
            Origen origen = new Origen(comunidadOrigen.getValue(), provinciaOrigen.getValue());
            Destino destino = new Destino(comunidadDestino.getValue(), provinciaDestino.getValue());
            Periodo periodo = new Periodo(fechaInicio.getValue(), fechaFin.getValue());
            Turismo turismoEditado = new Turismo(origen, destino, periodo, total.getValue(), id);
            this.service.editarTurismo(id, turismoEditado);
            dialog.close();
        });
        dialog.getFooter().add(saveButton);

        return dialog;
    }


    private VerticalLayout createEditDialogLayout(String id) {

        try {
            Turismo turismo = this.service.getTurismo(id);

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

            return dialogLayout;
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }

    }

    public Dialog generateCreateDialog() {
        Dialog dialog = new Dialog();
        dialog.setWidth("450px");
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);
        dialog.setHeaderTitle("Crear Nuevo Viaje");

        VerticalLayout dialogLayout = createDialogLayout();
        dialog.add(dialogLayout);

        // Botón para cancelar
        Button cancelButton = new Button("Cancelar", e -> dialog.close());
        dialog.getFooter().add(cancelButton);

        // Botón para guardar
        Button saveButton = new Button("Guardar", e -> {
            Origen origen = new Origen(comunidadOrigen.getValue(), provinciaOrigen.getValue());
            Destino destino = new Destino(comunidadDestino.getValue(), provinciaDestino.getValue());
            Periodo periodo = new Periodo(fechaInicio.getValue(), fechaFin.getValue());
            Turismo nuevoTurismo = new Turismo(origen, destino, periodo, total.getValue());

            // Llama al servicio para agregar el nuevo elemento
            this.service.agregarTurismo(nuevoTurismo);

            dialog.close();
        });
        dialog.getFooter().add(saveButton);

        return dialog;
    }

    private VerticalLayout createDialogLayout() {
        comunidadOrigen.clear();
        provinciaOrigen.clear();
        comunidadDestino.clear();
        provinciaDestino.clear();
        fechaInicio.clear();
        fechaFin.clear();
        total.clear();

        HorizontalLayout origenLayout = new HorizontalLayout(comunidadOrigen, provinciaOrigen);
        HorizontalLayout destinoLayout = new HorizontalLayout(comunidadDestino, provinciaDestino);
        HorizontalLayout fechasLayout = new HorizontalLayout(fechaInicio, fechaFin);
        VerticalLayout dialogLayout = new VerticalLayout(origenLayout, destinoLayout, fechasLayout, total);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return dialogLayout;
    }
}