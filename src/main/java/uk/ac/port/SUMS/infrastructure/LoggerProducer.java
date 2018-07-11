package uk.ac.port.SUMS.infrastructure;
import javax.enterprise.inject.*;
import javax.enterprise.inject.spi.*;
import java.util.logging.*;
import javax.enterprise.context.*;

/**
CDI Injection Producer allowing injection of instances of Logger;
with this, a Logger instance can be injected with @Inject,
which by default will have the name of the package of the class it is being injected into.
To manually specify a name, annotate the same member with @LoggerName.
@author Reciprocal
*/
@Dependent //A Bean-Defining Annotation must be provided if this class is to be discovered and used by the container
class LoggerProducer{
 public LoggerProducer(){}
 
 @Produces
 public Logger ProduceLogger(InjectionPoint InjectingTo){
  LoggerName DefinedName=InjectingTo.getAnnotated().getAnnotation(LoggerName.class);
  if(DefinedName!=null){
   return Logger.getLogger(DefinedName.value());
  }else{
   return Logger.getLogger(InjectingTo.getMember().getDeclaringClass().getPackage().getName());
  }
 }
 
 public void DisposeLogger(@Disposes Logger Dispose){
 }
}