/**
 * WorkerDeRegistration.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.globus.GenericPortal.stubs.GPService_instance;

public class WorkerDeRegistration  implements java.io.Serializable {
    private java.lang.String machID;
    private boolean service;

    public WorkerDeRegistration() {
    }

    public WorkerDeRegistration(
           java.lang.String machID,
           boolean service) {
           this.machID = machID;
           this.service = service;
    }


    /**
     * Gets the machID value for this WorkerDeRegistration.
     * 
     * @return machID
     */
    public java.lang.String getMachID() {
        return machID;
    }


    /**
     * Sets the machID value for this WorkerDeRegistration.
     * 
     * @param machID
     */
    public void setMachID(java.lang.String machID) {
        this.machID = machID;
    }


    /**
     * Gets the service value for this WorkerDeRegistration.
     * 
     * @return service
     */
    public boolean isService() {
        return service;
    }


    /**
     * Sets the service value for this WorkerDeRegistration.
     * 
     * @param service
     */
    public void setService(boolean service) {
        this.service = service;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WorkerDeRegistration)) return false;
        WorkerDeRegistration other = (WorkerDeRegistration) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.machID==null && other.getMachID()==null) || 
             (this.machID!=null &&
              this.machID.equals(other.getMachID()))) &&
            this.service == other.isService();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getMachID() != null) {
            _hashCode += getMachID().hashCode();
        }
        _hashCode += (isService() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WorkerDeRegistration.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", ">WorkerDeRegistration"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("machID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "machID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("service");
        elemField.setXmlName(new javax.xml.namespace.QName("", "service"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
