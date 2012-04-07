/**
 * GPServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.globus.GenericPortal.stubs.GPService_instance.service;

public class GPServiceLocator extends org.apache.axis.client.Service implements org.globus.GenericPortal.stubs.GPService_instance.service.GPService {

    public GPServiceLocator() {
    }


    public GPServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public GPServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for GPPortTypePort
    private java.lang.String GPPortTypePort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getGPPortTypePortAddress() {
        return GPPortTypePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String GPPortTypePortWSDDServiceName = "GPPortTypePort";

    public java.lang.String getGPPortTypePortWSDDServiceName() {
        return GPPortTypePortWSDDServiceName;
    }

    public void setGPPortTypePortWSDDServiceName(java.lang.String name) {
        GPPortTypePortWSDDServiceName = name;
    }

    public org.globus.GenericPortal.stubs.GPService_instance.GPPortType getGPPortTypePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(GPPortTypePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getGPPortTypePort(endpoint);
    }

    public org.globus.GenericPortal.stubs.GPService_instance.GPPortType getGPPortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.globus.GenericPortal.stubs.GPService_instance.bindings.GPPortTypeSOAPBindingStub _stub = new org.globus.GenericPortal.stubs.GPService_instance.bindings.GPPortTypeSOAPBindingStub(portAddress, this);
            _stub.setPortName(getGPPortTypePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setGPPortTypePortEndpointAddress(java.lang.String address) {
        GPPortTypePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.globus.GenericPortal.stubs.GPService_instance.GPPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                org.globus.GenericPortal.stubs.GPService_instance.bindings.GPPortTypeSOAPBindingStub _stub = new org.globus.GenericPortal.stubs.GPService_instance.bindings.GPPortTypeSOAPBindingStub(new java.net.URL(GPPortTypePort_address), this);
                _stub.setPortName(getGPPortTypePortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("GPPortTypePort".equals(inputPortName)) {
            return getGPPortTypePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance/service", "GPService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance/service", "GPPortTypePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("GPPortTypePort".equals(portName)) {
            setGPPortTypePortEndpointAddress(address);
        }
        else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
