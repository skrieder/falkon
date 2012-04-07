/**
 * StatusUserResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.globus.GenericPortal.stubs.GPService_instance;

public class StatusUserResponse  implements java.io.Serializable {
    private int queueLength;
    private int activeTasks;
    private int queuedTask;
    private int activeTask;
    private int numWorkers;
    private int numWorkerResults;
    private boolean valid;

    public StatusUserResponse() {
    }

    public StatusUserResponse(
           int activeTask,
           int activeTasks,
           int numWorkerResults,
           int numWorkers,
           int queueLength,
           int queuedTask,
           boolean valid) {
           this.queueLength = queueLength;
           this.activeTasks = activeTasks;
           this.queuedTask = queuedTask;
           this.activeTask = activeTask;
           this.numWorkers = numWorkers;
           this.numWorkerResults = numWorkerResults;
           this.valid = valid;
    }


    /**
     * Gets the queueLength value for this StatusUserResponse.
     * 
     * @return queueLength
     */
    public int getQueueLength() {
        return queueLength;
    }


    /**
     * Sets the queueLength value for this StatusUserResponse.
     * 
     * @param queueLength
     */
    public void setQueueLength(int queueLength) {
        this.queueLength = queueLength;
    }


    /**
     * Gets the activeTasks value for this StatusUserResponse.
     * 
     * @return activeTasks
     */
    public int getActiveTasks() {
        return activeTasks;
    }


    /**
     * Sets the activeTasks value for this StatusUserResponse.
     * 
     * @param activeTasks
     */
    public void setActiveTasks(int activeTasks) {
        this.activeTasks = activeTasks;
    }


    /**
     * Gets the queuedTask value for this StatusUserResponse.
     * 
     * @return queuedTask
     */
    public int getQueuedTask() {
        return queuedTask;
    }


    /**
     * Sets the queuedTask value for this StatusUserResponse.
     * 
     * @param queuedTask
     */
    public void setQueuedTask(int queuedTask) {
        this.queuedTask = queuedTask;
    }


    /**
     * Gets the activeTask value for this StatusUserResponse.
     * 
     * @return activeTask
     */
    public int getActiveTask() {
        return activeTask;
    }


    /**
     * Sets the activeTask value for this StatusUserResponse.
     * 
     * @param activeTask
     */
    public void setActiveTask(int activeTask) {
        this.activeTask = activeTask;
    }


    /**
     * Gets the numWorkers value for this StatusUserResponse.
     * 
     * @return numWorkers
     */
    public int getNumWorkers() {
        return numWorkers;
    }


    /**
     * Sets the numWorkers value for this StatusUserResponse.
     * 
     * @param numWorkers
     */
    public void setNumWorkers(int numWorkers) {
        this.numWorkers = numWorkers;
    }


    /**
     * Gets the numWorkerResults value for this StatusUserResponse.
     * 
     * @return numWorkerResults
     */
    public int getNumWorkerResults() {
        return numWorkerResults;
    }


    /**
     * Sets the numWorkerResults value for this StatusUserResponse.
     * 
     * @param numWorkerResults
     */
    public void setNumWorkerResults(int numWorkerResults) {
        this.numWorkerResults = numWorkerResults;
    }


    /**
     * Gets the valid value for this StatusUserResponse.
     * 
     * @return valid
     */
    public boolean isValid() {
        return valid;
    }


    /**
     * Sets the valid value for this StatusUserResponse.
     * 
     * @param valid
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StatusUserResponse)) return false;
        StatusUserResponse other = (StatusUserResponse) obj;
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
            this.queuedTask == other.getQueuedTask() &&
            this.activeTask == other.getActiveTask() &&
            this.numWorkers == other.getNumWorkers() &&
            this.numWorkerResults == other.getNumWorkerResults() &&
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
        _hashCode += getQueuedTask();
        _hashCode += getActiveTask();
        _hashCode += getNumWorkers();
        _hashCode += getNumWorkerResults();
        _hashCode += (isValid() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StatusUserResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", ">StatusUserResponse"));
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
        elemField.setFieldName("queuedTask");
        elemField.setXmlName(new javax.xml.namespace.QName("", "queuedTask"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activeTask");
        elemField.setXmlName(new javax.xml.namespace.QName("", "activeTask"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numWorkers");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numWorkers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numWorkerResults");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numWorkerResults"));
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
