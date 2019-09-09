package com.employee.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProducerServiceImp implements ProducerService {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value( "${rabbitmq.exchange}" )
    private String exchange;

    @Value( "${rabbitmq.routingkey}" )
    private String routingkey;

    @Override
    public Object sendMsg(Long proId) throws Exception {
        System.out.println(proId);
        Object response = amqpTemplate.convertSendAndReceive(exchange, routingkey, proId);
        String[] res = getKeysAndValues(response.toString());
        String[] keys = res[0].split(":");
        String[] values = res[1].split("=");
        System.out.println(Arrays.toString(keys));
        System.out.println(Arrays.toString(values));
        //get specific field value
        System.out.println(getField(keys,values,"email"));
        return getObj(keys,values);
    }

    public String[] getKeysAndValues(String st) {
        String keys="";
        String values="";
        int i=1;
        for(i=1;i<st.length()-1;i++){
            if(st.charAt(i)=='^'){
                break;
            }else{
                keys+=st.charAt(i);
            }
        }
        for(int j=i+1;j<st.length()-1;j++)
        {
            values+=st.charAt(j);
        }
        String[] ret={keys,values};
        return ret;
    }

    public String getField(String[] keys,String[] values,String field) {
        String ret="";
        for(int i=0;i<keys.length;i++) {
            if(field.equalsIgnoreCase(keys[i])) {
                ret = values[i];
            }
        }
        return ret;
    }

    public Object getObj(String[] keys , String[] values) {
        Map<String,String> map=new HashMap<String,String>();
        for(int i=0;i<keys.length;i++){
            map.put(keys[i],values[i]);
        }
        return map;
    }
}
