package controlador;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Vector;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import application.EntradaCalendario;
import javafx.application.Platform;
import javafx.scene.control.Control;

public class ManejadorCalendario {
	
	private Vector<EntradaCalendario> entradas;
	private String _dniPacienteCalendario;
	private CalendarSource calendarSource;
	
	public Control CrearCalendario(String dniPacienteCalendario, boolean readOnly) {
		_dniPacienteCalendario = dniPacienteCalendario;
		
		Calendar calendar = new Calendar("Calendario");
		calendar.setReadOnly(readOnly);
		calendarSource = new CalendarSource("Calendarios");
		calendarSource.getCalendars().add(calendar);
		
		CalendarView calendarView = new CalendarView();
		calendarView.getCalendarSources().clear();
		calendarView.getCalendarSources().add(calendarSource);
		calendarView.setShowAddCalendarButton(false);
		calendarView.setRequestedTime(LocalTime.now());
         
        entradas = ControladorBBDD.getCalendarioPaciente(_dniPacienteCalendario);
        for(EntradaCalendario entradaCalendario : entradas) {
        	Entry entry = new Entry(entradaCalendario.getTitle());
        	entry.setId(Integer.toString(entradaCalendario.getId()));
        	Interval interval = new Interval(entradaCalendario.getStartDate(), entradaCalendario.getStartTime(), 
        									 entradaCalendario.getEndDate(), entradaCalendario.getEndTime());
        	entry.setInterval(interval);
        	calendar.addEntry(entry);
        }
		calendar.addEventHandler(evt -> calendarEvent(evt));
		
		Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                    while (true) {
                            Platform.runLater(() -> {
                                    calendarView.setToday(LocalDate.now());
                                    calendarView.setTime(LocalTime.now());
                            });
                            try {
                                    sleep(10000); // update every 10 seconds
                            } catch (InterruptedException e) {
                                    e.printStackTrace();
                            }
                    }
            };
		};
        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
        return calendarView;
	}
	
	// Se añade, elimina o modifica un evento.
	private void calendarEvent(CalendarEvent evt) {
		Entry entry = evt.getEntry();		if (evt.isEntryAdded()) {
			EntradaCalendario entradaCalendario = entryToEntradaCalendario(entry);
	        entradaCalendario.addDB();
	        entry.setId(Integer.toString(entradaCalendario.getId()));
		}
		else if (evt.isEntryRemoved()) {
			EntradaCalendario.removeDB(Integer.parseInt(entry.getId()));
		}
		else {
			EntradaCalendario entradaCalendario = entryToEntradaCalendario(entry);
			entradaCalendario.updateDB();
		}
	}
	
	
	// Se pasa el objeto entry a una entradaCalendario.
	private EntradaCalendario entryToEntradaCalendario(Entry entry) {
        EntradaCalendario entradaCalendario = new EntradaCalendario();
        entradaCalendario.setId(Integer.parseInt(entry.getId()));
        entradaCalendario.setTitle(entry.getTitle());
        entradaCalendario.setStartDate(entry.getStartDate());
        entradaCalendario.setStartTime(entry.getStartTime());
        entradaCalendario.setEndDate(entry.getEndDate());
        entradaCalendario.setEndTime(entry.getEndTime());
        entradaCalendario.setdniPaciente(_dniPacienteCalendario);
        return entradaCalendario;
	}
}