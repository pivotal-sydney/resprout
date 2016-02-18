package io.pivotal;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;

public class YamlMessageConverter<T> extends AbstractHttpMessageConverter<T>
{
 public YamlMessageConverter()
 {
          super(new MediaType("application","yaml"));
 }
  
 @Override
 protected T readInternal(Class<? extends T> arg0, HttpInputMessage arg1)
   throws IOException, HttpMessageNotReadableException
 {
   Yaml yaml = new Yaml(new Constructor(arg0));
   T object = (T)yaml.load(arg1.getBody());
   return object;
 }
 
 @Override
 protected boolean supports(Class<?> arg0) {
  return true;
 }
  
 @Override
 protected void writeInternal(T arg0, HttpOutputMessage arg1)
   throws IOException, HttpMessageNotWritableException
 {
  Yaml yaml = new Yaml();
  String result = yaml.dump(arg0);  
  arg1.getBody().write(result.getBytes());
 }
}
