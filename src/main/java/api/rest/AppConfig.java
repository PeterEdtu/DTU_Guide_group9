package api.rest;

import api.soap.ConsoleAppEndpoint;
import api.soap.ConsoleAppInterface;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.xml.ws.Endpoint;

@ApplicationPath("/REST")
public class AppConfig extends Application {
    static ConsoleAppInterface soapEndpoint;

    static{
        soapEndpoint = new ConsoleAppEndpoint();
        Endpoint.publish("http://0.0.0.0:9901/SOAP", soapEndpoint);
    }
}
