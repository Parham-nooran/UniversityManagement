package ir.ac.kntu.universityManagement.models.repositories.calendar;

import ir.ac.kntu.universityManagement.models.calendar.CalendarEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarEntryRepository extends JpaRepository<CalendarEntry, Long> {

}
