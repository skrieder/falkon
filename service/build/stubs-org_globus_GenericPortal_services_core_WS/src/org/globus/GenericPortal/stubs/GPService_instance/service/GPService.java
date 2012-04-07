/**
 * GPService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.globus.GenericPortal.stubs.GPService_instance.service;

public interface GPService extends javax.xml.rpc.Service {
    public java.lang.String getGPPortTypePortAddress();

    public org.globus.GenericPortal.stubs.GPService_instance.GPPortType getGPPortTypePort() throws javax.xml.rpc.ServiceException;

    public org.globus.GenericPortal.stubs.GPService_instance.GPPortType getGPPortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
