package api.soap;

import javax.xml.ws.Endpoint;

public class SoapTest {

    public static void main(String[] args){
        ConsoleAppInterface cai = new ConsoleAppEndpoint();
        System.out.println("Starting soap interface");
        Endpoint.publish("http://localhost:9901/SOAP", cai);
    }
}
