/**
 * DataCache.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.globus.GenericPortal.stubs.GPService_instance;

public class DataCache  implements java.io.Serializable {
    private int numCaches;
    private java.lang.String[] cacheLocation;

    public DataCache() {
    }

    public DataCache(
           java.lang.String[] cacheLocation,
           int numCaches) {
           this.numCaches = numCaches;
           this.cacheLocation = cacheLocation;
    }


    /**
     * Gets the numCaches value for this DataCache.
     * 
     * @return numCaches
     */
    public int getNumCaches() {
        return numCaches;
    }


    /**
     * Sets the numCaches value for this DataCache.
     * 
     * @param numCaches
     */
    public void setNumCaches(int numCaches) {
        this.numCaches = numCaches;
    }


    /**
     * Gets the cacheLocation value for this DataCache.
     * 
     * @return cacheLocation
     */
    public java.lang.String[] getCacheLocation() {
        return cacheLocation;
    }


    /**
     * Sets the cacheLocation value for this DataCache.
     * 
     * @param cacheLocation
     */
    public void setCacheLocation(java.lang.String[] cacheLocation) {
        this.cacheLocation = cacheLocation;
    }

    public java.lang.String getCacheLocation(int i) {
        return this.cacheLocation[i];
    }

    public void setCacheLocation(int i, java.lang.String _value) {
        this.cacheLocation[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DataCache)) return false;
        DataCache other = (DataCache) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.numCaches == other.getNumCaches() &&
            ((this.cacheLocation==null && other.getCacheLocation()==null) || 
             (this.cacheLocation!=null &&
              java.util.Arrays.equals(this.cacheLocation, other.getCacheLocation())));
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
        _hashCode += getNumCaches();
        if (getCacheLocation() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCacheLocation());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCacheLocation(), i);
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
        new org.apache.axis.description.TypeDesc(DataCache.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.globus.org/namespaces/GenericPortal/GPService_instance", "DataCache"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numCaches");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numCaches"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cacheLocation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cacheLocation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
