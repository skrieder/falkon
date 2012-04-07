/**
 * FactoryServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.globus.GenericPortal.stubs.Factory.service;

public class FactoryServiceLocator extends org.apache.axis.client.Service implements org.globus.GenericPortal.stubs.Factory.service.FactoryService {

    public FactoryServiceLocator() {
    }


    public FactoryServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public FactoryServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for FactoryPortTypePort
    private java.lang.String FactoryPortTypePort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getFactoryPortTypePortAddress() {
        return FactoryPortTypePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String FactoryPortTypePortWSDDServiceName = "FactoryPortTypePort";

    public java.lang.String getFactoryPortTypePortWSDDServiceName() {
        return FactoryPortTypePortWSDDServiceName;
    }

    public void setFactoryPortTypePortWSDDServiceName(java.lang.String name) {
        FactoryPortTypePortWSDDServiceName = name;
    }

    public org.globus.GenericPortal.stubs.Factory.FactoryPortType getFactoryPortTypePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(FactoryPortTypePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getFactoryPortTypePort(endpoint);
    }

    public org.globus.GenericPortal.stubs.Factory.FactoryPortType getFactoryPortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.globus.GenericPortal.stubs.Factory.bindings.FactoryPortTypeSOAPBindingStub _stub = new org.globus.GenericPortal.stubs.Factory.bindings.FactoryPortTypeSOAPBindingStub(portAddress, this);
            _stub.setPortName(getFactoryPortTypePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setFactoryPortTypePortEndpointAddress(java.lang.String address) {
        FactoryPortTypePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.globus.GenericPortal.stubs.Factory.FactoryPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                org.globus.GenericPortal.stubs.Factory.bindings.FactoryPortTypeSOAPBindingStub _stub = new org.globus.GenericPortal.stubs.Factory.bindings.FactoryPortTypeSOAPBindingStub(new java.net.URL(FactoryPortTypePort_address), this);
                _stub.setPortName(getFactoryPortTypePortWSDDServiceName());
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
        if ("FactoryPortTypePort".equals(inputPortName)) {
            return getFactoryPortTypePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/FactoryService/service", "FactoryService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/FactoryService/service", "FactoryPortTypePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("FactoryPortTypePort".equals(portName)) {
            setFactoryPortTypePortEndpointAddress(address);
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
