/**
 * UserJobResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.globus.GenericPortal.stubs.GPService_instance;

public class UserJobResponse  implements java.io.Serializable {
    private boolean valid;
    private boolean service;
    private org.globus.GenericPortal.stubs.GPService_instance.ServiceState serviceState;

    public UserJobResponse() {
    }

    public UserJobResponse(
           boolean service,
           org.globus.GenericPortal.stubs.GPService_instance.ServiceState serviceState,
           boolean valid) {
           this.valid = valid;
           this.service = service;
           this.serviceState = serviceState;
    }


    /**
     * Gets the valid value for this UserJobResponse.
     * 
     * @return valid
     */
    public boolean isValid() {
        return valid;
    }


    /**
     * Sets the valid value for this UserJobResponse.
     * 
     * @param valid
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }


    /**
     * Gets the service value for this UserJobResponse.
     * 
     * @return service
     */
    public boolean isService() {
        return service;
    }


    /**
     * Sets the service value for this UserJobResponse.
     * 
     * @param service
     */
    public void setService(boolean service) {
        this.service = service;
    }


    /**
     * Gets the serviceState value for this UserJobResponse.
     * 
     * @return serviceState
     */
    public org.globus.GenericPortal.stubs.GPService_instance.ServiceState getServiceState() {
        return serviceState;
    }


    /**
     * Sets the serviceState value for this UserJobResponse.
     * 
     * @param serviceState
     */
    public void setServiceState(org.globus.GenericPortal.stubs.GPService_instance.ServiceState serviceState) {
        this.serviceState = serviceState;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UserJobResponse)) return false;
        UserJobResponse other = (UserJobResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.valid == other.isValid() &&
            this.service == other.isService() &&
            ((this.serviceState==null && other.getServiceState()==null) || 
             (this.serviceState!=null &&
              this.serviceState.equals(other.getServiceState())));
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
        _hashCode += (isService() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getServiceState() != null) {
            _hashCode += getServiceState().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UserJobResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", ">UserJobResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("service");
        elemField.setXmlName(new javax.xml.namespace.QName("", "service"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceState");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serviceState"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", "ServiceState"));
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
