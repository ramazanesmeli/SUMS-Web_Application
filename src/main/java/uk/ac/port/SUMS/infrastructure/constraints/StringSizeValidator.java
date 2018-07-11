package uk.ac.port.SUMS.infrastructure.constraints;
import java.util.regex.*;
import javax.validation.*;

/**
@author Reciprocal
*/
public class StringSizeValidator implements ConstraintValidator<StringSize,String>{ 
 private int Minimum;
 private int Maximum;
 public @Override void initialize(StringSize Annotation){
  if(Annotation.Minimum()<0){
   throw new IllegalArgumentException("Negative minimum value on StringSize constraint");
  }
  if(Annotation.Maximum()>=0&&Annotation.Minimum()>Annotation.Maximum()){
   throw new IllegalArgumentException("StringSize constraint minimum is greater than maximum");
  }
  this.Minimum=Annotation.Minimum();
  this.Maximum=Annotation.Maximum();
 }
 
 public @Override boolean isValid(String value,ConstraintValidatorContext context){
  if(value==null){return true;}
  int Length=GlyphLength(value);
  if(Length<Minimum){
   return false;
  }
  if(Maximum>=0&&Length>Maximum){
   return false;
  }
  return true;
 }
 
 protected int GlyphLength(String value){
  int CharacterLength=value.codePointCount(0,value.length());
  int NonGlyphCharacters=0;
  for(int character : value.codePoints().toArray()){
   int charactertype=Character.getType(character);
   if(
    charactertype==Character.NON_SPACING_MARK
    ||charactertype==Character.COMBINING_SPACING_MARK
    ||charactertype==Character.ENCLOSING_MARK
    ||charactertype==Character.SURROGATE
   ){
    ++NonGlyphCharacters;
   }
  }
  return CharacterLength-NonGlyphCharacters;
 }
}