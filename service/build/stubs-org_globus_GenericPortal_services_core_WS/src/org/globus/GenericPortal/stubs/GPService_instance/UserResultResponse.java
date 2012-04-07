/**
 * UserResultResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.globus.GenericPortal.stubs.GPService_instance;

public class UserResultResponse  implements java.io.Serializable {
    private boolean valid;
    private int numTasks;
    private org.globus.GenericPortal.stubs.GPService_instance.Task[] tasks;

    public UserResultResponse() {
    }

    public UserResultResponse(
           int numTasks,
           org.globus.GenericPortal.stubs.GPService_instance.Task[] tasks,
           boolean valid) {
           this.valid = valid;
           this.numTasks = numTasks;
           this.tasks = tasks;
    }


    /**
     * Gets the valid value for this UserResultResponse.
     * 
     * @return valid
     */
    public boolean isValid() {
        return valid;
    }


    /**
     * Sets the valid value for this UserResultResponse.
     * 
     * @param valid
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }


    /**
     * Gets the numTasks value for this UserResultResponse.
     * 
     * @return numTasks
     */
    public int getNumTasks() {
        return numTasks;
    }


    /**
     * Sets the numTasks value for this UserResultResponse.
     * 
     * @param numTasks
     */
    public void setNumTasks(int numTasks) {
        this.numTasks = numTasks;
    }


    /**
     * Gets the tasks value for this UserResultResponse.
     * 
     * @return tasks
     */
    public org.globus.GenericPortal.stubs.GPService_instance.Task[] getTasks() {
        return tasks;
    }


    /**
     * Sets the tasks value for this UserResultResponse.
     * 
     * @param tasks
     */
    public void setTasks(org.globus.GenericPortal.stubs.GPService_instance.Task[] tasks) {
        this.tasks = tasks;
    }

    public org.globus.GenericPortal.stubs.GPService_instance.Task getTasks(int i) {
        return this.tasks[i];
    }

    public void setTasks(int i, org.globus.GenericPortal.stubs.GPService_instance.Task _value) {
        this.tasks[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UserResultResponse)) return false;
        UserResultResponse other = (UserResultResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.valid == other.isValid() &&
            this.numTasks == other.getNumTasks() &&
            ((this.tasks==null && other.getTasks()==null) || 
             (this.tasks!=null &&
              java.util.Arrays.equals(this.tasks, other.getTasks())));
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
        _hashCode += (isValid() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += getNumTasks();
        if (getTasks() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTasks());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTasks(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UserResultResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", ">UserResultResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numTasks");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numTasks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tasks");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tasks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", "Task"));
        elemField.setMinOccurs(0);
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
