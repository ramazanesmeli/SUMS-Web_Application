package uk.ac.port.SUMS.infrastructure;
import java.text.*;
import javax.persistence.*;
import java.time.*;
import java.util.*;
import java.util.logging.*;

/**
The new date and time classes of the java.time package are new to Java 8,
and so not yet supported by JPA;
this class provides JPA with a conversion mechanism,
so that these new classes can be used.
To use this converter on an ORM-mapped member of type ZonedDateTime, annotate it with @javax.persistence.Convert,
specifying this class.
This converter stores both date and time information, and will store values in UTC.
@author Reciprocal
*/
@Converter(autoApply=false)
public class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime,java.sql.Timestamp>{
 protected final Logger Log=Logger.getLogger(this.getClass().getPackage().getName());
 public ZonedDateTimeConverter(){}
 
 public @Override java.sql.Timestamp convertToDatabaseColumn(ZonedDateTime value){
  /*
  See comment in convertToEntityAttribute.
  */
  if(value==null){return null;}
  //First convert to UTC if not already
  value=value.withZoneSameInstant(ZoneId.of("Z"));
  ZonedDateTime valueTimezoneReplaced=value.withZoneSameLocal(ZoneId.systemDefault());
  java.sql.Timestamp Result=new java.sql.Timestamp(valueTimezoneReplaced.toInstant().toEpochMilli());
  Log.fine(MessageFormat.format(
   "Converting to SQL Timestamp:"
   +" From — {0} ({1}); Re-interpretted in system timezone — {2} ({3}); Timestamp — {4} ({5})",
   value,value.toInstant().toEpochMilli(),valueTimezoneReplaced,valueTimezoneReplaced.toInstant().toEpochMilli(),Result,Result.getTime()
  ));
  return Result;
 }

 public @Override ZonedDateTime convertToEntityAttribute(java.sql.Timestamp value){
  /*
  JPA / JPA Database Drivers will, arguably erroneously,
  interpret date–time database fields according to the system's timezone.
  Following good practise, our date–time fields are stored in UTC.
  Therefore, we must extract the interpretted date–time value from the Timestamp,
  removing its timezone,
  and re-interpret it with the correct timezone of UTC,
  before returning this correct value to the caller.
  */
  if(value==null){return null;}
  Instant valueInstant=Instant.ofEpochMilli(value.getTime());
  LocalDateTime valueTimezoneRemoved=LocalDateTime.ofInstant(valueInstant,ZoneId.systemDefault());
  ZonedDateTime valueTimezoneReplaced=ZonedDateTime.ofStrict(valueTimezoneRemoved,ZoneOffset.UTC,ZoneId.of("Z"));
  Log.fine(MessageFormat.format(
   "Converting from SQL Timestamp:"
   +" Timestamp — {0} ({1}); Timestamp Instant — {2}; Interpretted as system timezone Date–Time — {3}; Re-interpretted in UTC timezone — {4} ({5})",
   value,value.getTime(),valueInstant,valueTimezoneRemoved,valueTimezoneReplaced,valueTimezoneReplaced.toInstant().toEpochMilli()
  ));
  return valueTimezoneReplaced;
 }
}