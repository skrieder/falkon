/**
 * StatusResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.globus.GenericPortal.stubs.GPService_instance;

public class StatusResponse  implements java.io.Serializable {
    private int queueLength;
    private int activeTasks;
    private int numWorkers;
    private int busyWorkers;
    private int newWorkers;
    private int deregisteredWorkers;
    private boolean valid;

    public StatusResponse() {
    }

    public StatusResponse(
           int activeTasks,
           int busyWorkers,
           int deregisteredWorkers,
           int newWorkers,
           int numWorkers,
           int queueLength,
           boolean valid) {
           this.queueLength = queueLength;
           this.activeTasks = activeTasks;
           this.numWorkers = numWorkers;
           this.busyWorkers = busyWorkers;
           this.newWorkers = newWorkers;
           this.deregisteredWorkers = deregisteredWorkers;
           this.valid = valid;
    }


    /**
     * Gets the queueLength value for this StatusResponse.
     * 
     * @return queueLength
     */
    public int getQueueLength() {
        return queueLength;
    }


    /**
     * Sets the queueLength value for this StatusResponse.
     * 
     * @param queueLength
     */
    public void setQueueLength(int queueLength) {
        this.queueLength = queueLength;
    }


    /**
     * Gets the activeTasks value for this StatusResponse.
     * 
     * @return activeTasks
     */
    public int getActiveTasks() {
        return activeTasks;
    }


    /**
     * Sets the activeTasks value for this StatusResponse.
     * 
     * @param activeTasks
     */
    public void setActiveTasks(int activeTasks) {
        this.activeTasks = activeTasks;
    }


    /**
     * Gets the numWorkers value for this StatusResponse.
     * 
     * @return numWorkers
     */
    public int getNumWorkers() {
        return numWorkers;
    }


    /**
     * Sets the numWorkers value for this StatusResponse.
     * 
     * @param numWorkers
     */
    public void setNumWorkers(int numWorkers) {
        this.numWorkers = numWorkers;
    }


    /**
     * Gets the busyWorkers value for this StatusResponse.
     * 
     * @return busyWorkers
     */
    public int getBusyWorkers() {
        return busyWorkers;
    }


    /**
     * Sets the busyWorkers value for this StatusResponse.
     * 
     * @param busyWorkers
     */
    public void setBusyWorkers(int busyWorkers) {
        this.busyWorkers = busyWorkers;
    }


    /**
     * Gets the newWorkers value for this StatusResponse.
     * 
     * @return newWorkers
     */
    public int getNewWorkers() {
        return newWorkers;
    }


    /**
     * Sets the newWorkers value for this StatusResponse.
     * 
     * @param newWorkers
     */
    public void setNewWorkers(int newWorkers) {
        this.newWorkers = newWorkers;
    }


    /**
     * Gets the deregisteredWorkers value for this StatusResponse.
     * 
     * @return deregisteredWorkers
     */
    public int getDeregisteredWorkers() {
        return deregisteredWorkers;
    }


    /**
     * Sets the deregisteredWorkers value for this StatusResponse.
     * 
     * @param deregisteredWorkers
     */
    public void setDeregisteredWorkers(int deregisteredWorkers) {
        this.deregisteredWorkers = deregisteredWorkers;
    }


    /**
     * Gets the valid value for this StatusResponse.
     * 
     * @return valid
     */
    public boolean isValid() {
        return valid;
    }


    /**
     * Sets the valid value for this StatusResponse.
     * 
     * @param valid
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StatusResponse)) return false;
        StatusResponse other = (StatusResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.queueLength == other.getQueueLength() &&
            this.activeTasks == other.getActiveTasks() &&
            this.numWorkers == other.getNumWorkers() &&
            this.busyWorkers == other.getBusyWorkers() &&
            this.newWorkers == other.getNewWorkers() &&
            this.deregisteredWorkers == other.getDeregisteredWorkers() &&
            this.valid == other.isValid();
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
        _hashCode += getQueueLength();
        _hashCode += getActiveTasks();
        _hashCode += getNumWorkers();
        _hashCode += getBusyWorkers();
        _hashCode += getNewWorkers();
        _hashCode += getDeregisteredWorkers();
        _hashCode += (isValid() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StatusResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", ">StatusResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("queueLength");
        elemField.setXmlName(new javax.xml.namespace.QName("", "queueLength"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activeTasks");
        elemField.setXmlName(new javax.xml.namespace.QName("", "activeTasks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numWorkers");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numWorkers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("busyWorkers");
        elemField.setXmlName(new javax.xml.namespace.QName("", "busyWorkers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("newWorkers");
        elemField.setXmlName(new javax.xml.namespace.QName("", "newWorkers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deregisteredWorkers");
        elemField.setXmlName(new javax.xml.namespace.QName("", "deregisteredWorkers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valid"));
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
