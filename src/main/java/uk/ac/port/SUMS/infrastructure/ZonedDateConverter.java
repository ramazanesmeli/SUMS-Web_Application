package uk.ac.port.SUMS.infrastructure;
import javax.persistence.*;
import java.time.*;

/**
As for ZonedDateTimeConverter, but will only store the date component,
truncating the time component.
@see ZonedDateTimeConverter
@author Reciprocal
*/
@Converter(autoApply=false)
public class ZonedDateConverter implements AttributeConverter<ZonedDateTime,java.sql.Date>{
 public ZonedDateConverter(){}
 
 public @Override java.sql.Date convertToDatabaseColumn(ZonedDateTime value){
  if(value==null){return null;}
  return new java.sql.Date(value.toInstant().toEpochMilli());
 }

 public @Override ZonedDateTime convertToEntityAttribute(java.sql.Date value){
  if(value==null){return null;}
  return ZonedDateTime.ofInstant(Instant.ofEpochMilli(value.getTime()),ZoneId.of("Z"));
 }
}