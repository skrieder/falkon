/**
 * Status.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.globus.GenericPortal.stubs.GPService_instance;

public class Status  implements java.io.Serializable {
    private java.lang.String message;
    private java.lang.String drpAllocation;
    private int drpMinResc;
    private int drpMaxResc;
    private int drpMinTime;
    private int drpMaxTime;
    private int drpIdle;
    private int resourceAllocated;

    public Status() {
    }

    public Status(
           java.lang.String drpAllocation,
           int drpIdle,
           int drpMaxResc,
           int drpMaxTime,
           int drpMinResc,
           int drpMinTime,
           java.lang.String message,
           int resourceAllocated) {
           this.message = message;
           this.drpAllocation = drpAllocation;
           this.drpMinResc = drpMinResc;
           this.drpMaxResc = drpMaxResc;
           this.drpMinTime = drpMinTime;
           this.drpMaxTime = drpMaxTime;
           this.drpIdle = drpIdle;
           this.resourceAllocated = resourceAllocated;
    }


    /**
     * Gets the message value for this Status.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this Status.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the drpAllocation value for this Status.
     * 
     * @return drpAllocation
     */
    public java.lang.String getDrpAllocation() {
        return drpAllocation;
    }


    /**
     * Sets the drpAllocation value for this Status.
     * 
     * @param drpAllocation
     */
    public void setDrpAllocation(java.lang.String drpAllocation) {
        this.drpAllocation = drpAllocation;
    }


    /**
     * Gets the drpMinResc value for this Status.
     * 
     * @return drpMinResc
     */
    public int getDrpMinResc() {
        return drpMinResc;
    }


    /**
     * Sets the drpMinResc value for this Status.
     * 
     * @param drpMinResc
     */
    public void setDrpMinResc(int drpMinResc) {
        this.drpMinResc = drpMinResc;
    }


    /**
     * Gets the drpMaxResc value for this Status.
     * 
     * @return drpMaxResc
     */
    public int getDrpMaxResc() {
        return drpMaxResc;
    }


    /**
     * Sets the drpMaxResc value for this Status.
     * 
     * @param drpMaxResc
     */
    public void setDrpMaxResc(int drpMaxResc) {
        this.drpMaxResc = drpMaxResc;
    }


    /**
     * Gets the drpMinTime value for this Status.
     * 
     * @return drpMinTime
     */
    public int getDrpMinTime() {
        return drpMinTime;
    }


    /**
     * Sets the drpMinTime value for this Status.
     * 
     * @param drpMinTime
     */
    public void setDrpMinTime(int drpMinTime) {
        this.drpMinTime = drpMinTime;
    }


    /**
     * Gets the drpMaxTime value for this Status.
     * 
     * @return drpMaxTime
     */
    public int getDrpMaxTime() {
        return drpMaxTime;
    }


    /**
     * Sets the drpMaxTime value for this Status.
     * 
     * @param drpMaxTime
     */
    public void setDrpMaxTime(int drpMaxTime) {
        this.drpMaxTime = drpMaxTime;
    }


    /**
     * Gets the drpIdle value for this Status.
     * 
     * @return drpIdle
     */
    public int getDrpIdle() {
        return drpIdle;
    }


    /**
     * Sets the drpIdle value for this Status.
     * 
     * @param drpIdle
     */
    public void setDrpIdle(int drpIdle) {
        this.drpIdle = drpIdle;
    }


    /**
     * Gets the resourceAllocated value for this Status.
     * 
     * @return resourceAllocated
     */
    public int getResourceAllocated() {
        return resourceAllocated;
    }


    /**
     * Sets the resourceAllocated value for this Status.
     * 
     * @param resourceAllocated
     */
    public void setResourceAllocated(int resourceAllocated) {
        this.resourceAllocated = resourceAllocated;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Status)) return false;
        Status other = (Status) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage()))) &&
            ((this.drpAllocation==null && other.getDrpAllocation()==null) || 
             (this.drpAllocation!=null &&
              this.drpAllocation.equals(other.getDrpAllocation()))) &&
            this.drpMinResc == other.getDrpMinResc() &&
            this.drpMaxResc == other.getDrpMaxResc() &&
            this.drpMinTime == other.getDrpMinTime() &&
            this.drpMaxTime == other.getDrpMaxTime() &&
            this.drpIdle == other.getDrpIdle() &&
            this.resourceAllocated == other.getResourceAllocated();
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
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        if (getDrpAllocation() != null) {
            _hashCode += getDrpAllocation().hashCode();
        }
        _hashCode += getDrpMinResc();
        _hashCode += getDrpMaxResc();
        _hashCode += getDrpMinTime();
        _hashCode += getDrpMaxTime();
        _hashCode += getDrpIdle();
        _hashCode += getResourceAllocated();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Status.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", ">Status"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("", "message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("drpAllocation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "drpAllocation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("drpMinResc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "drpMinResc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("drpMaxResc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "drpMaxResc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("drpMinTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "drpMinTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("drpMaxTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "drpMaxTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("drpIdle");
        elemField.setXmlName(new javax.xml.namespace.QName("", "drpIdle"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resourceAllocated");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resourceAllocated"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
